package exampleLearning;

import java.util.*;

public class Example_4_1_ComputeAngles {//计算三角形的角度

	public static void main(String[] args) {
		Scanner in=new Scanner(System.in);
		double x1=in.nextDouble();
		double y1=in.nextDouble();
		double x2=in.nextDouble();
		double y2=in.nextDouble();
		double x3=in.nextDouble();
		double y3=in.nextDouble();
		in.close();
		calAngles(x1,y1,x2,y2,x3,y3);
	}
	
	static void calAngles(double x1,double y1,double x2,double y2,double x3,double y3) {
		double a=Math.sqrt(Math.pow(x2-x3,2.0)+Math.pow(y2-y3,2.0));
		double b=Math.sqrt(Math.pow(x1-x2,2.0)+Math.pow(y1-y2,2.0));
		double c=Math.sqrt(Math.pow(x1-x3,2.0)+Math.pow(y1-y3,2.0));
		if(a+b<=c||a+c<=b||b+c<=a) {
			System.out.println("data error");
			return;
		}
		double A=Math.toDegrees(Math.acos((a*a-b*b-c*c)/(-2*b*c)));
		double B=Math.toDegrees(Math.acos((b*b-a*a-c*c)/(-2*a*c)));
		double C=Math.toDegrees(Math.acos((c*c-b*b-a*a)/(-2*b*a)));
		System.out.println(Math.round(A*100)/100.0+" "+Math.round(B*100)/100.0+" "+Math.round(C*100)/100.0);
	}

}
