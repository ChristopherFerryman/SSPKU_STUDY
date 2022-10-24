package exampleLearning;

import java.util.*;

public class Example_3_4_ComputeAndInterpretBMI {

	public static void main(String[] args) {
		Scanner in=new Scanner(System.in);
		System.out.println("Enter the weight in pounds: ");
		double weight_pd=in.nextDouble();
		System.out.println("Enter the height in inches: ");
		double height_ic=in.nextDouble();
		in.close();
		double weight_kg=weight_pd*0.45359237;
		double height_m=height_ic*0.0254;
		double bmi=weight_kg/(Math.pow(height_m,2.0));
		if(bmi<18.5) {
			System.out.println("Undeiweight!");
		}else if(bmi<25.0) {
			System.out.println("Normal!");
		}else if(bmi<30.0) {
			System.out.println("Overweight!");
		}else {
			System.out.println("Obese!");
		}
	}

}
