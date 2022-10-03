package exampleLearning;

import java.util.*;

public class Example_5_14_Palindrome {//判断回文串

	public static void main(String[] args) {
		Scanner in=new Scanner(System.in);
		System.out.print("Enter a string: ");
		String s=in.nextLine();
		in.close();
		int low=0;
		int high=s.length()-1;
		boolean isPalindrome=true;
		while(low<high) {
			if(s.charAt(low)!=s.charAt(high)) {
				isPalindrome=false;
				break;
			}
			low++;
			high--;
		}
		if(isPalindrome) {
			System.out.println(s+" is a palindrome");
		}else {
			System.out.println(s+" is not a palindrome");
		}
	}

}
