package intelligence;

import DeckOfCards.Hand;
import blackjack.Dealer;
import blackjack.Player;

public class ComputerIntelligence implements Intelligence{
	Player player;
	
	public ComputerIntelligence(Player player){
		this.player = player;
	}
	
	@Override
	public void startAI() {
		System.out.println(player.name + "'s turn:");
		Hand hand = player.getHand();
		
		while (player.getHand() != null){
			while (player.getHand().inPlay)
				doCommand(index());
			player.setHand(player.getHand().next);
		}
		
		while (hand.previous != null)
			hand = hand.previous;
		player.setHand(hand);
	}
	
	/*
	 * 1 = HIT
	 * 2 = DOUBLEDOWN
	 * 3 = STAY
	 * 4 = SPLIT
	 */
					//	 2,3,4,5,6,7,8,9,X,A	
	int[][] hardArray= {{1,1,1,1,1,1,1,1,1,1},//2
						{1,1,1,1,1,1,1,1,1,1},//3
						{1,1,1,1,1,1,1,1,1,1},//4
						{1,1,1,1,1,1,1,1,1,1},//5
						{1,1,1,1,1,1,1,1,1,1},//6
						{1,1,1,1,1,1,1,1,1,1},//7
						{1,1,1,1,1,1,1,1,1,1},//8
						{1,2,2,2,2,1,1,1,1,1},//9
						{2,2,2,2,2,2,2,2,1,1},//10
						{2,2,2,2,2,2,2,2,2,2},//11
						{1,1,3,3,3,1,1,1,1,1},//12
						{3,3,3,3,3,1,1,1,1,1},//13
						{3,3,3,3,3,1,1,1,1,1},//14
						{3,3,3,3,3,1,1,1,1,1},//15
						{3,3,3,3,3,1,1,1,1,1},//16
						{3,3,3,3,3,3,3,3,3,3},//17
						{3,3,3,3,3,3,3,3,3,3},//18
						{3,3,3,3,3,3,3,3,3,3},//19
						{3,3,3,3,3,3,3,3,3,3},//20
						{3,3,3,3,3,3,3,3,3,3},//21
	};
					//	 2,3,4,5,6,7,8,9,X,A
	int[][] softArray= {{1,1,2,2,2,1,1,1,1,1},//A-2
						{1,1,2,2,2,1,1,1,1,1},//A-3
						{1,1,2,2,2,1,1,1,1,1},//A-4
						{1,1,2,2,2,1,1,1,1,1},//A-5
						{1,1,2,2,2,1,1,1,1,1},//A-6
						{3,3,2,2,2,3,3,1,1,1},//A-7
						{3,3,3,3,3,3,3,3,3,3},//A-8
						{3,3,3,3,3,3,3,3,3,3},//A-9
	};
					//	 2,3,4,5,6,7,8,9,X,A
	int[][] splitArray={{1,1,4,4,4,4,1,1,1,1},//2-2
						{1,1,4,4,4,4,1,1,1,1},//3-3
						{1,1,1,1,1,1,1,1,1,1},//4-4
						{2,2,2,2,2,2,2,2,1,1},//5-5
						{4,4,4,4,4,1,1,1,1,1},//6-6
						{4,4,4,4,4,4,1,1,1,1},//7-7
						{4,4,4,4,4,4,4,4,4,4},//8-8
						{4,4,4,4,4,4,4,4,3,3},//9-9
						{3,3,3,3,3,3,3,3,3,3},//10-10
						{4,4,4,4,4,4,4,4,4,4},//A-A
	};
	
	/**
	 * interface for computer controlled player to initiate their next move
	 * @param command next move to make as chosen by the computer player
	 */
	public void doCommand(int command){
		player.peekHand();
		switch (command){
			case 1:
				System.out.println(player.name + " hits");
				player.commands.hitMe();
				break;
			case 2:
				System.out.println(player.name + " doubles down");
				player.commands.doubleDown();
				break;
			case 3:
				System.out.println(player.name + " stays");
				player.commands.stay();
				break;
			case 4:
				System.out.println(player.name + " splits");
				player.commands.split();
				break;
				
		}
	}
	
	/**
	 * index into the appropriate array to find the optimal move
	 * @return integer describing the optimal move
	 */
	public int index(){
		if (player.getHand().topCard.name.equals("ACE") && player.getHand().topCard.next.name.equals("ACE"))
			return splitIndex();
		else if (player.getHand().hasAce() && player.getHand().isAceValue && player.getHand().handValue != 21){
			return softIndex();
		}
		else if (player.getHand().topCard.next != null && player.getHand().topCard.name.equals(player.getHand().topCard.next.name))
				return splitIndex();
		else return hardIndex();
	}
	
	/*
	 * array rows offset by 2
	 * array columns offset by 13
	 * 
	 */
	public int hardIndex(){
		int row = player.getHand().handValue - 2;
		int column = Dealer.getDealer().topCard.value - 2;
		return hardArray[row][column];
	}
	
	public int softIndex(){
		int row = player.getHand().handValue - (11 + 2);//ace value plus offset 
		int column = Dealer.getDealer().topCard.value - 2;
		return softArray[row][column];
	}
	
	public int splitIndex(){
		int row = player.getHand().topCard.next.value - 2; //next.value for double ace case
		int column = Dealer.getDealer().topCard.value - 2;
		return splitArray[row][column];
	}

	public void playOdds(){
		int handValue = player.getHand().handValue;
		
		if (handValue <= 8)
			Dealer.dealCard(player);		
	}

}
