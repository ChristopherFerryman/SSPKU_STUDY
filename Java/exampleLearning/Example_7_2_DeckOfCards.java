package exampleLearning;

public class Example_7_2_DeckOfCards {

	public static void main(String[] args) {//一副牌
		int[] deck=new int[52];
		String[] suits= {"Spades","Hearts","Diamonds","Clubs"};
		String[] ranks= {"Ace","2","3","4","5","6","7","8","9","10","Jack","Queen","King"};
		for(int i=0;i<deck.length;i++) {//初始化牌堆
			deck[i]=i;
		}
		for(int i=0;i<deck.length;i++) {//打乱牌堆，即随机选取数组中的两个元素并调换它们的值
			int index=(int)(Math.random()*deck.length);
			int temp=deck[i];
			deck[i]=deck[index];
			deck[index]=temp;
		}

		for(int i=0;i<4;i++){//展示前四张牌
			String suit=suits[deck[i]/13];
			String rank=ranks[deck[i]%13];
			System.out.println("Card number "+deck[i]+": "+rank+" of "+suit);
		}
	}

}
