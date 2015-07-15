package blackjack;

import intelligence.ComputerIntelligence;
import intelligence.PlayerIntelligence;
import DeckOfCards.Hand;

public class Commands {
	Player player;

	public void hitMe(){
		if (player.getHand().isBusted()){
			player.getHand().printBusted();
			player.getHand().inPlay = false;
			return;
		}
		Dealer.dealCard(player);
		player.getHand().isBusted();
		player.peekHand();
	}
	
	public void stay(){
		player.getHand().inPlay = false;
		System.out.println(player.name + "'s hand value is " + player.getHand().handValue);
		System.out.println(" ");
	}
	
	public void doubleDown(){
		if (player.getHand().count != 2){
			System.out.println("You may only double down on your original hand");
			return;
		}
		player.decrementChips(player.getHand().getBet());
		player.getHand().setBet(player.getHand().getBet()*2);
		Dealer.dealCard(player);
		player.peekHand();
		player.getHand().inPlay = false;
		System.out.println(player.name +"'s hand value is " + player.getHand().handValue);
	}
	/*
	 * splits the current player's hand into two hands. newhand is attached above the current hand.
	 * TODO bug with a triple split - will not pay out winner for the first hand on original split.  May be an issue
	 * with shallow copying the previous hands previous reference 
	 */
	public void split(){
		if (!player.getHand().topCard.name.equals(player.getHand().topCard.next.name)
				&& player.getHand().count == 2)
			System.out.println("You must hold a pair to split");

		else{
			Hand newHand = new Hand();
			player.decrementChips(player.getHand().getBet());
			
			//splitting up current hand into two hands
			newHand.topCard = player.getHand().topCard;
			if (newHand.topCard.value == 1)//double aces case
				newHand.topCard.value = 11;
			newHand.handValue += newHand.topCard.value;
			newHand.setBet(player.getHand().getBet());
			player.getHand().topCard = player.getHand().topCard.next;
			player.getHand().count--;
			newHand.count++;
			
			//nulling out old hand to completely separate
			player.getHand().topCard.next = null;
			newHand.topCard.next = null;
			player.getHand().handValue -= newHand.topCard.value;
			newHand.previous = player.getHand().previous;
			player.getHand().previous = newHand;

			//adding to LL
			newHand.next = player.getHand();
			player.setHand(newHand);
			
			System.out.println(" ");
			System.out.println("first hand");
			player.peekHand();
		}
	}//end split
	
	/**
	 * swaps out human control for computer controlled, then back to human control via strategy pattern.
	 */
	public void odds(){
		player.intelligence = new ComputerIntelligence(player);
		player.intelligence.startAI();
		player.intelligence = new PlayerIntelligence(player);
	}
		
	
	
}//end Commands
