package exampleLearning;

import java.util.*;

public class Example_4_5_LotteryUsingStrings {//使用字符串修改彩票程序

	public static void main(String[] args) {
		String lottery=""+(int)(Math.random()*10)+(int)(Math.random()*10);
		Scanner in=new Scanner(System.in);
		System.out.print("Enter your lottery pick(two digits): ");
		String guess=in.nextLine();
		in.close();
		char lotteryDigit1=lottery.charAt(0);
		char lotteryDigit2=lottery.charAt(1);
		char guessDigit1=guess.charAt(0);
		char guessDigit2=guess.charAt(1);
		System.out.println("The lottery number is "+lottery);
		if(guess.equals(lottery)) {
			System.out.println("Exact match: you win $10000");
		}else if(guessDigit2==lotteryDigit1&&guessDigit1==lotteryDigit2) {
			System.out.println("Match all digits: you win $3000");
		}else if(guessDigit1==lotteryDigit1||guessDigit1==lotteryDigit2||guessDigit2==lotteryDigit1||guessDigit2==lotteryDigit2) {
			System.out.println("Match one digit: you win $1000");
		}else {
			System.out.println("Sorry, no match");
		}
	}

}
