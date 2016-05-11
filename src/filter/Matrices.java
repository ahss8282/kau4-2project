package filter;

import org.ejml.simple.SimpleMatrix;

public class Matrices {
	private SimpleMatrix Q, R, P;
	private SimpleMatrix X, K, A;
	
	public Matrices(double pe, double oe, double no){
		P = new SimpleMatrix(2,2,true,new double[]{pe,0,0,pe});
		R = new SimpleMatrix(2,2,true,new double[]{oe,0,0,oe});
		Q = new SimpleMatrix(2,2,true,new double[]{no,0,0,no});
		X = new SimpleMatrix(2,1);
		K = new SimpleMatrix(2,2);
		A = new SimpleMatrix(2,2,true,new double[]{1,0,0,1});
	}
	public SimpleMatrix gP(){
		return P;
	}
	public SimpleMatrix gR(){
		return R;
	}
	public SimpleMatrix gQ(){
		return Q;
	}
	public SimpleMatrix gX(){
		return X;
	}
	public SimpleMatrix gK(){
		return K;
	}
	public SimpleMatrix gA(){
		return K;
	}
	public SimpleMatrix gAmK(){
		return A.minus(K);
	}
	public void sP(SimpleMatrix p){
		P = p;
	}
	public void sR(SimpleMatrix r){
		R = r;
	}
	public void sQ(SimpleMatrix q){
		Q = q;
	}
	public void sX(SimpleMatrix x){
		X = x;
	}
	public void sK(SimpleMatrix k){
		K = k;
	}
}
