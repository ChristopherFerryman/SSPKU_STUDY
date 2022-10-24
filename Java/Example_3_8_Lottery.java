package exampleLearning;

import java.util.*;

public class Example_3_8_Lottery {

	public static void main(String[] args) {
		while(true) {
			int lottery=(int)(Math.random()*90)+10;
			System.out.println("Enter the guess number(two digits): ");
			Scanner in=new Scanner(System.in);
			int guess=in.nextInt();
			System.out.println("The lottery number is "+lottery);
			if(guess<10||guess>99) {
				System.out.println("The number you entered is of wrong format.");
				continue;
			}
			if(guess==lottery) {
				System.out.println("Exact match! You win $10000!");
				continue;
			}else {
				int guessDigit1=guess%10;
				int guessDigit2=guess/10;
				int lotteryDigit1=lottery%10;
				int lotteryDigit2=lottery/10;
				if(guessDigit1==lotteryDigit2&&guessDigit2==lotteryDigit1) {
					System.out.println("Match both digits! You win $3000");
					continue;
				}else if(guessDigit1==lotteryDigit1||guessDigit1==lotteryDigit2||guessDigit2==lotteryDigit1||guessDigit2==lotteryDigit2) {
					System.out.println("Match one digit! You win $1000");
					continue;
				}else {
					System.out.println("Sorry, no match!");
					continue;
				}
			}
		}

	}

}
