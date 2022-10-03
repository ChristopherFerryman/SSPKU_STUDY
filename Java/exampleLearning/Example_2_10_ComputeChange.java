package exampleLearning;

import java.util.*;

public class Example_2_10_ComputeChange {//整钱兑零

	public static void main(String[] args) {
		System.out.println("Enter an amount in double: ");
		Scanner in=new Scanner(System.in);
		double a=in.nextDouble();
		in.close();
		int totalPennies=(int)(a*100.0);
		int dollars=totalPennies/100;
		int remain=totalPennies%100;
		int quarters=remain/25;
		remain=remain%25;
		int dimes=remain/10;
		remain=remain%10;
		int nickels=remain/5;
		int pennies=remain%5;
		System.out.println("The amount you entered consists of");
		System.out.println(dollars+" dollars");
		System.out.println(quarters+" quaretrs");
		System.out.println(dimes+" dimes");
		System.out.println(nickels+" nickels");
		System.out.println(pennies+" pennies");
	}

}
