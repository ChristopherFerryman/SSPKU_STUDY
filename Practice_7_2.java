package java_assignment_5;

import java.util.*;

public class Practice_7_2 {
    public static void main(String[] args) {
        MyClock1 clock1,clock2;
        //输入一个时间：时 分 秒
        Scanner sc=new Scanner(System.in);
        int hour,minute,second;
        System.out.println("输入一个时间：（时 分 秒用空格分隔）");
        hour=sc.nextInt();
        minute=sc.nextInt();
        second=sc.nextInt();
        sc.close();
        //用输入的时间初始化时间对象clock1
        clock1=new MyClock1(hour,minute,second);
        //用clock1初始化clock2
        clock2=new MyClock1(clock1);
        //显示两个时间对象
        clock1.display();
        clock2.display();
    }
}

class MyClock1 {
	
    int hour,min,sec;//成员变量
    
    MyClock1(){//无参构造方法，初始化时钟为0:0:0
    	hour=0;
    	min=0;
    	sec=0;
    }
    
    MyClock1(int hour,int min,int sec){//带参构造方法
    	this.hour=hour;
    	this.min=min;
    	this.sec=sec;
    }
    
    MyClock1(MyClock1 clock) {//带参构造方法(用对象作为参数)
        this.hour=clock.hour;
        this.min=clock.min;
        this.sec=clock.sec;
    }
    
    public void display() {//显示时间
    	if(hour<10) {
    		System.out.print(" "+hour+":");
    	}else {
    		System.out.print(hour+":");
    	}
    	if(min<10) {
    		System.out.print(" "+min+":");
    	}else {
    		System.out.print(min+":");
    	}
    	if(sec<10) {
    		System.out.print(" "+sec);
    	}else {
    		System.out.print(sec);
    	}
        System.out.println();
    }
    
}