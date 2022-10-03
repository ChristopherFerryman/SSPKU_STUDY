package exampleLearning;

import java.util.*;

public class Example_5_11_Dec2Hex {//十进制转十六进制

	public static void main(String[] args) {
		Scanner in=new Scanner(System.in);
		System.out.print("Enter a decimal number: ");
		int decimal=in.nextInt();
		in.close();
		String hex="";
		while(decimal!=0) {
			int hexValue=decimal%16;
			char hexDigit=(hexValue<=9&&hexValue>=0)?(char)(hexValue+'0'):(char)(hexValue-10+'A');
			hex=hexDigit+hex;
			decimal/=16;
		}
		System.out.println("The hex number is "+hex);
	}

}
