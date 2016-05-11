package filter;

// 오해하지 마셈 이건 stationary & moving 의 준말
public class SM {
	private int len;
	public SM(int len){
		this.len = len;
	}
	public double sd(double[] arr){
		double mean=0;
		double sd = 0;
		//할당할 곳 = 비교문? 참일때값 : 거짓일때 값
		for(int i=0;i<len;i++) mean += arr[i];
		mean /= len;
		for(int i=0;i<len;i++) sd += (arr[i]-mean)*(arr[i]-mean);
		sd = Math.sqrt(sd/len);
		return sd;
	}
}
