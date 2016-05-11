package filter;

public class Integral {
	public double accum, sec, post, intv;
	public Integral(double sec){
		post = intv = accum = 0;
		this.sec = sec;
	}
	public void setZ(){
		accum=0;
		post=0;
	}
	public double accumulate(double input){
		intv = sec*(post+input)/2;
		accum += intv;
		post = input;
		return accum;
	}
	public double getAccum(){
		return accum;
	}
	public double getIntv(){
		return intv;
	}
}
