package exampleLearning;

import java.util.*;

public class Example_5_15_PrimeNumber {//显示素数

	public static void main(String[] args) {
		final int NUMBER_OF_PRIMES_PER_LINE=10;
		System.out.println("Enter the numbers of primes you want: ");
		Scanner in=new Scanner(System.in);
		int max=in.nextInt();
		in.close();
		int count=0;
		int number=2;
		System.out.println("The first "+max+" primes are:\n");
		while(count<max) {
			boolean isPrime=true;
			for(int divisor=2;divisor<=number/2;divisor++) {
				if(number%divisor==0) {
					isPrime=false;
					break;
				}
			}
			if(isPrime) {
				count++;
				if(count%NUMBER_OF_PRIMES_PER_LINE==0) {
					System.out.println(number);
				}else {
					System.out.print(number+" ");
				}
			}
			number++;
		}
	}
}
