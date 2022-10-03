package javaChapter_1;

public class Practice_1_12 {

	public static void main(String[] args) {
		double avg=calAvg(24.0,1,40);
		System.out.print(avg);
	}
	
	static double calAvg(double len,int hour,int min) {
		double time=calTime(hour,min);
		double mile=mileToKm(len);
		return mile/time;
	}
	
	static double calTime(int hour,int min) {
		return hour+min/60.0;
	}
	
	static double mileToKm(double len) {
		return len*1.6;
	}

}
