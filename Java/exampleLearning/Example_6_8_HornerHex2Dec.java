package exampleLearning;

import java.util.*;

public class Example_6_8_HornerHex2Dec {//霍纳算法递归，十六进制转十进制

	public static void main(String[] args) {
		Scanner in=new Scanner(System.in);
		System.out.print("Enter a hex number: ");
		String hex=in.nextLine();
		in.close();
		System.out.println("The decimal value for hex number "+hex+" is "+hexToDecimal(hex.toUpperCase()));
	}
	
	public static int hexToDecimal(String hex) {
		int decimalValue=0;
		for(int i=0;i<hex.length();i++) {
			char hexChar=hex.charAt(i);
			decimalValue=decimalValue*16+hexCharToDecimal(hexChar);
		}
		return decimalValue;
	}
	
	public static int hexCharToDecimal(char ch) {
		if(ch>='A'&&ch<='F') {
			return 10+ch-'A';
		}else {//ch is '0','1',...,'9'
			return ch-'0';
		}
	}

}
