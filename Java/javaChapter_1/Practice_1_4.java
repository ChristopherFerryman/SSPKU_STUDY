package javaChapter_1;

public class Practice_1_4 {

	public static void main(String[] args) {
		System.out.printf("%-4s %-4s %-4s","a","a^2","a^3");//格式化输出函数，%-4s的-代表左对齐，4代表输出间隔为4，s代表输出格式为字符串
		System.out.println();//换行
		for(int i=1;i<=4;i++) {
			System.out.printf("%-4d %-4d %-4d",i,(int)Math.pow(i,2),(int)Math.pow(i,3));
			System.out.println();
		}
	}

}
