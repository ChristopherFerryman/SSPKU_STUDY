package javaChapter_1;

public class Practice_1_11 {
	
	static final double NOW=312032486.0;

	public static void main(String[] args) {
		for(int i=1;i<=5;i++) {
			double pop=calPop(i);
			System.out.print("year "+i+":");
			System.out.println(pop);
		}
	}
	
	static double calPop(int j) {
		double sec=j*365.0*24.0*3600.0;
		double pop=NOW+sec/7.0-sec/13.0+sec/45.0;
		return pop;
	}

}
