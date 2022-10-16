package data_structure_homework_2;

import data_structure_homework.Queue;

public class SeqQueue implements Queue{//顺序循环队列类
	
	static final int defaultSize=10;//队列默认长度
	int front;//队头
	int rear;//队尾
	int count;//元素个数计数器
	int maxSize;//最大数据元素个数
	Object[] data;//保存队列元素的数组
	
	public SeqQueue() {//无参构造方法
		initiate(defaultSize);
	}
	
	public SeqQueue(int sz) {//带参构造方法
		initiate(sz);
	}
	
	private void initiate(int sz) {//初始化
		maxSize=sz;
		front=rear=0;
		count=0;
		data=new Object[sz];
	}
	
	public void append(Object obj) throws Exception{//入队列
		if(count>0&&front==rear) {
			throw new Exception("The sequenced queue is full!");
		}
		data[rear]=obj;
		rear=(rear+1)%maxSize;//加1求模
		count++;
	}
	
	public Object delete() throws Exception {//出队列
		if(count==0) {
			throw new Exception("The sequenced queue is empty!");
		}
		Object temp=data[front];
		front=(front+1)%maxSize;//加1求模
		count--;
		return temp;
	}
	
	public Object getFront() throws Exception{//取队头数据元素
		if(count==0) {
			throw new Exception("The sequenced queue is empty!");
		}
		return data[front];//返回队头元素(未出队列)
	}
	
	public boolean notEmpty() {//非空否
		return count!=0;
	}

}