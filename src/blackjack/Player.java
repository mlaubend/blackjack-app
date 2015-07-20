package blackjack;

import intelligence.ComputerIntelligence;
import intelligence.Intelligence;
import DeckOfCards.Card;
import DeckOfCards.Hand;

public class Player {
	public String name;
	private int chips;
	public boolean ante = true;
	public Player next = null;
	private Hand hand = null;
	public Commands commands;
	public Intelligence intelligence;
	
	public Player(String name){
		this.name = name;
		chips = 100;
		commands = new Commands();
		commands.player = this;
		this.hand = new Hand();
		intelligence = new ComputerIntelligence(this);
	}
	
	public void peekHand(){
		Card top = hand.topCard;
		while (top != null){
			System.out.println(top.showCard());
			top = top.next;
		}
		System.out.println("");
	}
	
	/**
	 * player receives a single card and inserts it into the current hand list
	 * @param card the card to be inserted into the current hand
	 */
	public void getCard(Card card){
		if (hand.topCard == null){
			hand.topCard = card;
			hand.handValue += hand.topCard.value;
			hand.topCard.next = null;
		}
		else{
			card.next = hand.topCard;
			hand.topCard = card;
			hand.handValue += hand.topCard.value;
			hand.isBusted();//double ace case
		}
		getHand().hasAce();
		getHand().count++;
	}
		
	/**
	 * 
	 * @param bet the bet the current player is trying to make
	 * @return true if the player has enough chips to make the bet
	 */
	public boolean enoughChips(int bet){
		if (bet > chips){
			System.out.println("You don't have enough chips");
			return false;
		}
		return true;
	}
	
	public Hand getHand(){
		return hand;
	}
	
	public void setHand(Hand hand){
		this.hand = hand;
	}
	
	public int getChips(){
		return chips;
	}
	
	public void decrementChips(int i){
		chips -= i;
	}
	
	public void incrementChips(int i){
		chips += i;
	}
	
	
}
