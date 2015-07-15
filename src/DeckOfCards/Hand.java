package DeckOfCards;

public class Hand {
	public Card topCard = null;
	public Hand next = null;
	public Hand previous = null;
	public int handValue = 0;
	public boolean busted = false;
	public boolean inPlay = true;
	public boolean isAceValue = false;
	private int bet = 0;
	public int count = 0;
	
	public int getBet(){
		return bet;
	}
	
	public void setBet(int bet){
		this.bet = bet;
	}
	
	public boolean isBusted(){
		if (handValue > 21){
			if (!hasAce()){
				busted = true;
				inPlay = false;
				return true;
			}
			else changeAceValue();
		}
		return false;
	}
	
	public boolean hasAce(){
		Card card = topCard;
		
		while (card != null){
			if (card.value == 11){
				isAceValue = true;
				return true;
			}
			card = card.next;
		}
		return false;
	}
	
	/**
	 * changes the value of a given ace to 1 and decrements the given hand to show the change 
	 */
	public void changeAceValue(){
		Card card = topCard;
		
		while (card != null){
			if (card.value == 11){
				handValue -= 10;
				card.value = 1;
				isAceValue = false;
				return;
			}
			card = card.next;
		}
	}
	
	public void printBusted(){
		System.out.println("BUST!");
	}
}
