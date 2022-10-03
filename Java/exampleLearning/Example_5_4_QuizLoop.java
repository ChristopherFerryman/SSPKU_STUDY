package exampleLearning;

import java.util.*;

public class Example_5_4_QuizLoop {//四则运算小测

	public static void main(String[] args) {
		System.out.print("Please enter the numbers of quizes you want to take: ");
		Scanner in=new Scanner(System.in);
		int max=in.nextInt();
		int count=0;
		int correctCount=0;
		long startTime=System.currentTimeMillis();
		while(count<max) {
			double select=Math.random();
			if(select>=0.0&&select<0.25) {
				boolean bl1=addQuiz();
				correctCount+=isCorrect(bl1);
			}else if(select>=0.25&&select<0.5) {
				boolean bl2=subtractQuiz();
				correctCount+=isCorrect(bl2);
			}else if(select>=0.5&&select<0.75) {
				boolean bl3=multiplyQuiz();
				correctCount+=isCorrect(bl3);
			}else {
				boolean bl4=divideQuiz();
				correctCount+=isCorrect(bl4);
			}
			count++;
		}
		in.close();
		long endTime=System.currentTimeMillis();
		long quizTime=endTime-startTime;
		System.out.println("You have taken "+max+" quizes, among which the correct count is "+correctCount+" and the wrong count is "+(max-correctCount));
		System.out.println("The test time is "+(quizTime/1000)+" s");
	}
	
	static int isCorrect(boolean bl) {
		if(bl==true) {
			System.out.println("Your answer is correct.");
			return 1;
		}else {
			System.out.println("Your answer is wrong.");
			return 0;
		}
	}
	
	static boolean addQuiz() {
		int n1=(int)(Math.random()*100);
		int n2=(int)(Math.random()*100);
		System.out.print(n1+"+"+n2+"=");
		Scanner in1=new Scanner(System.in);
		int answer=in1.nextInt();
		if(answer==n1+n2) {
			return true;
		}else {
			return false;
		}
	}
	
	static boolean subtractQuiz() {
		int n1=(int)(Math.random()*100);
		int n2=(int)(Math.random()*100);
		System.out.print(n1+"-"+n2+"=");
		Scanner in2=new Scanner(System.in);
		int answer=in2.nextInt();
		if(answer==n1-n2) {
			return true;
		}else {
			return false;
		}
	}
	
	static boolean multiplyQuiz() {
		int n1=(int)(Math.random()*100);
		int n2=(int)(Math.random()*100);
		System.out.print(n1+"*"+n2+"=");
		Scanner in3=new Scanner(System.in);
		int answer=in3.nextInt();
		if(answer==n1*n2) {
			return true;
		}else {
			return false;
		}
	}
	
	static boolean divideQuiz() {
		int n1=(int)(Math.random()*100+1);
		int n2=(int)(Math.random()*100+1);
		System.out.print(n1+"/"+n2+"=(just remain the integer part)");
		Scanner in4=new Scanner(System.in);
		int answer=in4.nextInt();
		if(answer==n1/n2) {
			return true;
		}else {
			return false;
		}
	}

}
