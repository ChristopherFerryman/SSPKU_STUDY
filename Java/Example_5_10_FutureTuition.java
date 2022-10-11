package exampleLearning;

public class Example_5_10_FutureTuition {
	
    public static void main(String[] args) {
        double tuition = 10000;
        int year = 0;
        while (tuition < 20000) {
            tuition = tuition * 1.07;
            year++;
        }
            System.out.println("Tuition wi11 be doubled in" + year + "years");
            System.out.printf("Tuition wi1l be $%.2f in %1d years" , tuition, year);
    }
    
}