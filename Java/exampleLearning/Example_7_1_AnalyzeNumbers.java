package exampleLearning;

import java.util.*;

public class Example_7_1_AnalyzeNumbers {//分析数字

	public static void main(String[] args) {
		Scanner in=new Scanner(System.in);
		System.out.print("Enter the number of items: ");
		int n=in.nextInt();
		double[] numbers=new double[n];
		double sum=0.0D;
		System.out.print("Enter the numbers:");
		for(int i=0;i<n;i++) {
			numbers[i]=in.nextDouble();
			sum+=numbers[i];
		}
		in.close();
		double avg=sum/n;
		int count=0;
		for(int i=0;i<n;i++) {
			if(numbers[i]>avg) {
				count++;
			}
		}
		System.out.println("The average is "+avg);
		System.out.println("The number of elements above the average is "+count);
	}

}
