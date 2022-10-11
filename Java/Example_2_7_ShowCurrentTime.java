package exampleLearning;

public class Example_2_7_ShowCurrentTime {//显示当前的格林尼治标准时间

	public static void main(String[] args) {
		long totalMilliseconds=System.currentTimeMillis();
		long totalSeconds=totalMilliseconds/1000;
		long currentSecond=totalSeconds%60;
		long totalMinutes=totalSeconds/60;
		long currentMinute=totalMinutes%60;
		long totalHours=totalMinutes/60;
		long currentHour=totalHours%24;
		System.out.println("Current time is "+currentHour+":"+currentMinute+":"+currentSecond+" GMT");
	}

}
