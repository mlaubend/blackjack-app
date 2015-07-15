package blackjack;

import intelligence.DealerIntelligence;
import DeckOfCards.Card;
import DeckOfCards.Deck;

public class Dealer extends Player{
	private static Dealer dealer = null; //singleton
	public static Deck deck = new Deck();
	public static Deck usedDeck = null;
	public Card topCard = null;
	
	public Dealer(String name) {
		super("Dealer");
		intelligence = new DealerIntelligence();
	}
	
	public static Dealer getDealer(){
		if (dealer == null)
			dealer = new Dealer("Dealer"); dealer.ante = true;
		return dealer;
	}
		
	public void shuffle(){
		deck.shuffle();
	}
	
	public void setTopCard(){
		topCard = getHand().topCard;
	}
	
	public void showTopCard(){
		System.out.println("Dealer is showing " + topCard.showCard());
	}
	
	/**
	 * Dealer deals 2 rounds of cards to each player
	 * @param first first player in player list
	 */
	public void deal(Player first){
		for (int i = 0; i < 2; i++){
			Player current = first;
			while(current != null){
				if (current.ante){
					current.getCard(deck.pop());
				}
				current = current.next;
			}
			Dealer.dealCard(dealer);
		}
		setTopCard();
	}
	
	/**
	 * Dealer deals a single card to current player
	 * @param first the player being dealt the card
	 */
	public static void dealCard(Player first){
		Player current = first;
		if (!current.getHand().isBusted()){
			current.getCard(deck.pop());
		if (current.getHand().handValue > 21) System.out.println("BUST!");
		}
	}
	
	/**
	 * if deck is less than 1/4 full, Dealer reshuffles entire deck
	 */
	public static void checkReshuffle(){
		if (deck.checkRemainingCards() <= 13){
			System.out.println("reshuflling cards");
			System.out.println(" ");
			deck = new Deck();
			deck.shuffle();
		}
	}
}
