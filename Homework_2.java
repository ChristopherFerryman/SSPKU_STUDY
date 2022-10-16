package data_structure_homework_2;

public class Homework_2 {//用顺序栈和顺序循环队列进行回文判断
	
	public static void isPalindromic(String str) throws Exception{//判断str是否为回文数
		int n=str.length();
		SeqStack myStack=new SeqStack(n);
		SeqQueue myQueue=new SeqQueue(n);
		for(int i=0;i<n;i++) {
			myStack.push(str.substring(i,i+1));//将str的第i个字符压栈
			myQueue.append(str.substring(i,i+1));//将str的第i个字符入队列
		}
		while(myStack.notEmpty()&&myQueue.notEmpty()) {//若栈与队列均非空
			if(!myQueue.delete().equals(myStack.pop())) {//比较当前出队列的元素与弹栈的元素
				System.out.println(str+" is not palindromic.");//若不相同，则str不是回文数
				return;
			}
		}
		System.out.println(str+" is palindromic.");//若栈的各弹栈元素与队列的各出队列元素均相同，则str是回文数
		return;
	}

	public static void main(String[] args) {//测试
		String s1="ABCDEDCBA";//测试样例1
		String s2="ABCDEDCAB";//测试样例2
		String s3="123454321";//测试样例3
		try {
			isPalindromic(s1);//测试1
			isPalindromic(s2);//测试2
			isPalindromic(s3);//测试3
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}

}