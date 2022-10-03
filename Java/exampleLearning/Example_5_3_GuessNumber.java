package exampleLearning;

import java.util.*;

public class Example_5_3_GuessNumber {//猜数字

	public static void main(String[] args) {
		int n=(int)(Math.random()*101);
		Scanner in=new Scanner(System.in);
		in.close();
		System.out.println("Guess a number between 0 and 100: ");
		int g=-1;
		while(g!=n) {
			System.out.println("Enter your guess:");
			g=in.nextInt();
			if(g==n) {
				System.out.println("Yes, the number is "+n);
				return;
			}else if(g>n) {
				System.out.println("Your guess is too high");
			}else {
				System.out.println("Your guess is too low");
			}
		}
	}

}
