package filter;

import java.util.Queue;
import filter.Matrices;
import filter.Integral;
import org.ejml.simple.SimpleMatrix;

public class Kalman{
	private int sdIdx, sdCount;
	private double[] curAcc;
	private double[] sd_x, sd_y;
	private double[] sdNmean;
	private Matrices mat;
	private SM sm;
	Integral vel[], disp[];
	
	public Kalman (Matrices mat, int sdCount, double[] sdNmean, double sec) {
		this.mat = mat;
		this.sdNmean = sdNmean;
		this.sdCount = sdCount;
		curAcc = new double[2];
		sd_x = new double[sdCount];
		sd_y = new double[sdCount];
		sm = new SM(sdCount);
		for(int i=1; i<sdCount; i++){
			sd_x[i] = sdNmean[2];
			sd_y[i] = sdNmean[3];
		}
		sdIdx = 0;
		
		/* integrated values of velocity and displacement */
		vel = new Integral[2];
		disp = new Integral[2];
		vel[0]= new Integral(sec);
		vel[1]= new Integral(sec);
		disp[0] = new Integral(sec);
		disp[1] = new Integral(sec);
	}
	
	public void prediction(String accX, String accY){
		sd_x[sdIdx] = curAcc[0] = Double.valueOf(accX);
		sd_y[sdIdx] = curAcc[1] = Double.valueOf(accY);
		sdIdx = (sdIdx+1)%sdCount;
		// A = I and there are nothing to control  
		// X = AX + Bu 	= X
		mat.sP(mat.gP().plus(mat.gQ())); 	// P = AP + Q = P + Q
		
//		System.out.println("P "+mat.gP().toString());
	}
	public void correction(){
		
		mat.sK(mat.gP().elementDiv(mat.gP().plus(mat.gR())));	// K = P / ( P + R )
		
		mat.gK().set(0, 1, 0);		// compensate NA -> 0
		mat.gK().set(1, 0, 0);		// compensate NA -> 0

// 		X = X + K ( Z - K )
		mat.sX(mat.gX().plus(mat.gK().mult(new SimpleMatrix(2,1,true,new double[]{curAcc[0],curAcc[1]}).minus(mat.gX()))));
		
		if(sm.sd(sd_x) <sdNmean[0]){
			mat.gX().set(0, 0, 0);
			vel[0].setZ();
			disp[0].setZ();
		}
		if(sm.sd(sd_y) <sdNmean[1]){
			mat.gX().set(1, 0, 0);
			vel[1].setZ();
			disp[1].setZ();
		}
		vel[0].accumulate(mat.gX().get(0, 0));
		disp[0].accumulate(vel[0].getAccum());
		vel[1].accumulate(mat.gX().get(1, 0));
		disp[1].accumulate(vel[1].getAccum());

		mat.sP(mat.gAmK().mult(mat.gP()));		// P = ( A - K ) * P
	}
	public double getDispX(){ return disp[0].getIntv(); }
	public double getDispY(){ return disp[1].getIntv(); }
}
