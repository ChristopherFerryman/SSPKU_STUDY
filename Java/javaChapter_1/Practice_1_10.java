package javaChapter_1;

public class Practice_1_10 {

	public static void main(String[] args) {
		double avg=calAvg(14.0,45,30);
		System.out.print(avg);
	}
	
	static double calAvg(double len,int min,int sec) {
		double hour=calHour(min,sec);
		double mile=kmToMile(len);
		return mile/hour;
	}
	
	static double calHour(int min,int sec) {
		return min/60.0+sec/3600.0;
	}
	
	static double kmToMile(double len) {
		return len/1.6;
	}

}
