package exampleLearning;

import java.util.*;

public class Example_3_7_LeapYear {//判定闰年

	public static void main(String[] args) {
		Scanner in=new Scanner(System.in);
		System.out.println("Enter a year: ");
		int year=in.nextInt();
		in.close();
		boolean isLeapYear=(year%4==0&&year%100!=0)||(year%400==0);
		if(isLeapYear==true) {
			System.out.println(year+" is a leap year.");
		}else {
			System.out.println(year+" is not a leap year.");
		}
	}

}
