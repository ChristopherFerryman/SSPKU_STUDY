package javaChapter_1;

public class Practice_1_13 {

	public static void main(String[] args) {
		double[][] input={{3.4,50.2,44.5},{2.1,0.55,5.9}};
		double[] answer=cramer(input);
		System.out.println("x="+answer[0]);
		System.out.println("y="+answer[1]);
	}
	
	static double[] cramer(double[][] input) {
		double[] answer=new double[2];
		answer[0]=(input[0][2]*input[1][1]-input[0][1]*input[1][2])/(input[0][0]*input[1][1]-input[0][1]*input[1][0]);
		answer[1]=(input[0][0]*input[1][2]-input[0][2]*input[1][0])/(input[0][0]*input[1][1]-input[0][1]*input[1][0]);
		return answer;
	}

}
