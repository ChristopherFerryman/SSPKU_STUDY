package javaChapter_1;

public class Practice_1_8 {
	
	final static double PI=3.1415926535;

	public static void main(String[] args) {
		double area=calArea(5.5);
		System.out.println(area);
		double peri=calPeri(5.5);
		System.out.println(peri);
	}
	
	static double calArea(double r) {
		return PI*r*r;
	}
	
	static double calPeri(double r) {
		return 2*PI*r;
	}

}
