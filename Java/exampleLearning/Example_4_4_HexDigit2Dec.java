package exampleLearning;

import java.util.*;

public class Example_4_4_HexDigit2Dec {//十六进制数转十进制数

	public static void main(String[] args) {
			Scanner in=new Scanner(System.in);
			System.out.println("Enter a hex digit: ");
			String h=in.nextLine();
			in.close();
			if(h.length()!=1) {
				System.out.println("You must enter exactly one character!");
				System.exit(1);
			}else {
				hex2Dec(h);
			}
	}
	
	static void hex2Dec(String h) {
		char ch=Character.toUpperCase(h.charAt(0));
		if(ch>='A'&&ch<='F') {
			int value=ch-'A'+10;
			System.out.println("The decimal value for hex digit "+ch+" is "+value);
		}else {
			System.out.println(ch+" is an invalid input");
		}
	}

}
