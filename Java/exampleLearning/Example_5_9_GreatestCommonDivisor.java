package exampleLearning;

import java.util.*;

public class Example_5_9_GreatestCommonDivisor {//求最大公约数

	public static void main(String[] args) {
		Scanner in=new Scanner(System.in);
		System.out.print("Enter the first integer: ");
		int n1=in.nextInt();
		System.out.print("Enter the second integer: ");
		int n2=in.nextInt();
		in.close();
		int min=Math.min(n1,n2);
		int gcd=1;
		int k=2;
		while(k<=min) {
			if(n1%k==0&&n2%k==0) {
				gcd=k;
			}
			k++;
		}
		System.out.println("The greatest common divisor for "+n1+" and "+n2+" is "+gcd);
		return;
	}

}
