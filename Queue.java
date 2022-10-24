package data_structure_homework_2;
public interface Queue {//队列
	public void append(Object obj) throws Exception;//入队列
	public Object delete() throws Exception;//出队列
	public Object getFront() throws Exception;//取队头数据元素
	public boolean notEmpty();//非空否
}