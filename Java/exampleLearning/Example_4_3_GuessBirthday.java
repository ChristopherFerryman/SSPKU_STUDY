package exampleLearning;

import java.util.*;

public class Example_4_3_GuessBirthday {//猜生日

	public static void main(String[] args) {
		String[] set= {" 1  3  5  7\n"+" 9 11 13 15\n"+"17 19 21 23\n"+"25 27 29 31",
				" 2  3  6  7\n"+"10 11 14 15\n"+"18 19 22 23\n"+"26 27 30 31",
				" 4  5  6  7\n"+"12 13 14 15\n"+"20 21 22 23\n"+"28 29 30 31",
				" 8  9 10 11\n"+"12 13 14 15\n"+"24 25 26 27\n"+"28 29 30 31",
				"16 17 18 19\n"+"20 21 22 23\n"+"24 25 26 27\n"+"28 29 30 31"};
		int[] addDay= {1,2,4,8,16};
		int day=0;
		Scanner in=new Scanner(System.in);
		for(int i=1;i<=5;i++) {
			System.out.println("Is your birthday in set"+i+"?");
			System.out.println(set[i-1]);
			System.out.println("Enter 0 for No or 1 for Yes: ");
			int answer=in.nextInt();
			if(answer==1) {
				day+=addDay[i-1];
			}
		}
		in.close();
		System.out.println("Your birthday is "+day+".");
	}

}
