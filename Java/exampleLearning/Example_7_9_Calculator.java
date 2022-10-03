package exampleLearning;

public class Example_7_9_Calculator {//计算器，需要使用命令行输入args参数

	public static void main(String[] args) {
		if(args.length!=3) {//若输入的操作数/操作符的个数不等于3，结束程序
			System.out.println("Usage: java Calculator operand1 operator operand2");
			System.exit(0);
		}
		int result=0;
		switch(args[1].charAt(0)) {//确定操作符
			case '+':
				result=Integer.parseInt(args[0])+Integer.parseInt(args[2]);
				break;
			case '-':
				result=Integer.parseInt(args[0])-Integer.parseInt(args[2]);
				break;
			case '*':
				result=Integer.parseInt(args[0])*Integer.parseInt(args[2]);
				break;
			case '/':
				result=Integer.parseInt(args[0])/Integer.parseInt(args[2]);
				break;
		}
		System.out.println(args[0]+" "+args[1]+" "+args[2]+" = "+result);
	}

}
