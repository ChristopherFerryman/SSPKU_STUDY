package javaChapter_1;

public class Practice_1_7 {

	public static void main(String[] args) {
		double a=calPi(11);
		System.out.println(a);
		double b=calPi(13);
		System.out.println(b);
	}
	
	static double calPi(int k) {
		double pi=0.0D;
		for(int m=1;m<=k;m+=2) {
			double mod=((m-1.0)/2.0)%2.0;
			pi+=Math.pow(-1.0,mod)*1.0/m;
		}
		return pi*4.0;
	}

}
