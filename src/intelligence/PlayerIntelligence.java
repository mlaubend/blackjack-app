package intelligence;

import java.util.Scanner;
import blackjack.Player;
import DeckOfCards.Hand;

public class PlayerIntelligence implements Intelligence{
	Player player;
	
	public PlayerIntelligence(Player player){
		this.player = player;
	}
	
	@Override
	public void startAI() {
		bettingLoop();
	}
	
	/**
	 * takes in the human controlled player's next move until busted or stays
	 */
	public void bettingLoop(){
		Scanner scanner = new Scanner(System.in);
		Hand hand = player.getHand();
		if (player.getHand().handValue == 21){
			System.out.println("BLACKJACK!");
			return;
		}
	
		while (player.getHand() != null){
			while (player.getHand().inPlay){
				//System.out.println("hit/doubledown/split/stay/odds?");
				//nextMove(scanner.next());
			}
			player.setHand(player.getHand().next);
			
			if (player.getHand() != null){
				System.out.println("Next hand"); 
				player.peekHand();
			}
		}

		while(hand.previous != null)
			hand = hand.previous;
		player.setHand(hand);
	}
	
	/**
	 * Interface for player to initiate their next move
	 * @param nextMove the chosen next move
	 */
	public void nextMove(String nextMove){
		switch (nextMove){
			case "hit":
				player.commands.hitMe(player.pType);
				break;
			case "doubledown":
				player.commands.doubleDown(player.pType);
				break;
			case "split":
				player.commands.split();
				break;
			case "stay":
				player.commands.stay();
				break;
			case "odds":
				player.commands.odds();
		}
	}


}//end PlayerIntelligence
