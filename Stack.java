package data_structure_homework_2;
public interface Stack {//堆栈
	public void push(Object obj) throws Exception;//入栈
	public Object pop() throws Exception;//出栈
	public Object getTop() throws Exception;//取栈顶元素
	public boolean notEmpty();//非空否
}