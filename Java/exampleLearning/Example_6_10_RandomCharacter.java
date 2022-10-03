package exampleLearning;

public class Example_6_10_RandomCharacter {//生成随机字符
	
	public static char getRandomCharacter(char ch1,char ch2) {//生成ch1与ch2之间的一个随机字符(ch1<ch2)
		return (char)(ch1+Math.random()*(ch2-ch1+1));
	}
	
	public static char getRandomLowerCaseLetter() {//生成一个随机的小写字母
		return getRandomCharacter('a','z');
	}
	
	public static char getRandomUpperCaseLetter() {//生成一个随机的大写字母
		return getRandomCharacter('A','Z');
	}
	
	public static char getRandomDigitCharacter() {//生成一个随机数字
		return getRandomCharacter('0','9');
	}
	
	public static char getRandomCharacter() {//生成一个随机字符
		return getRandomCharacter('\u0000','\uFFFF');
	}

	public static void main(String[] args) {//显示175个随机的小写字母
		final int NUMBER_OF_CHARS=175;
		final int CHARS_PER_LINE=25;
		for(int i=0;i<NUMBER_OF_CHARS;i++) {
			char ch=getRandomLowerCaseLetter();
			if((i+1)%CHARS_PER_LINE==0) {
				System.out.println(ch);
			}else {
				System.out.print(ch);
			}
		}
	}

}
