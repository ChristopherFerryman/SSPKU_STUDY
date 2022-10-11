package exampleLearning;

import java.util.Scanner;

public class Example_3_5_ComputeTax {
	
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("(0-sing1e filer,1-married jointly or" +
                "qualifying widow(er),2-married separately,3-head of" +
                "househo1d) Enter the filing status:");
        int status = input.nextInt();
        System.out.print("Enter the taxable income:");
        double income = input.nextDouble();
        input.close();
        double tax=0;
        if (status == 0) {
            if (income <= 8350)
                tax = income * 0.10;
            else if (income <= 33950)
                tax = 8350 * 0.10 + (income - 8350) * 0.15;
            else if (income <= 82250)
                tax = 8350 * 0.10 + (33950 - 8350) * 0.15 +
                        (income - 33950) * 0.25;
            else if (income <= 171550)
                tax = 8350 * 0.10 + (33950 - 8350) * 0.15 +
                        (82250 - 33950) * 0.25 + (income - 82250) * 0.28;
            else if (income <= 372950)
                tax = 8350 * 0.10 + (33950 - 8350) * 0.15 +
                        (82250 - 33950) * 0.25 + (171550 - 82250) * 0.28 +
                        (income - 171550) * 0.33;
            else
                tax = 8350 * 0.10 + (33950 - 8350) * 0.15 +
                        (82250 - 33950) * 0.25 + (171550 - 82250) * 0.28 +
                        (372950 - 171550) * 0.33 + (income - 372950) * 0.35;
        }
        else if (status == 1) {//请参照教材填充代码
        	
        }
        else if (status == 2) {//请参照教材填充代码
        	
        }
        else if (status == 3) {//请参照教材填充代码
        	
        }
        else {
            System.out.println("Error:invalid status");
            System.exit(1);
        }
        System.out.println("Taxis" + (int) (tax * 100) / 100.0);
    }
    
}