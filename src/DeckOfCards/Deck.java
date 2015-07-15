package DeckOfCards;

import java.util.Random;

//simple stack
public class Deck {
	
	Card top = null;
	Card shuffler;
	int count = 0;
	
	public Deck(){
		for (int i = 0; i < 13 ; i++){
			Card card = new Card(i, "Spades");
			push(card);
		}
			
		for (int i = 13; i < 26; i++){
			Card card = new Card(i, "Hearts");
			push(card);
		}
		for (int i = 26; i < 39; i++){
			Card card = new Card(i, "Clubs");
			push(card);
		}
		for (int i = 39; i < 52; i++){
			Card card = new Card(i, "Diamonds");
			push(card);
		}
		shuffler = top;
	}
	
	public Card pop(){
		if (top != null){
			Card card = top;
			top = top.next;
			count--;
			return card;
		}
		return null;
	}
	
	public void push(Card card){
		if (top == null)
			top = card;
		else{
			card.next = top;
			top = card;
		}
		count++;
	}
	
	public boolean isEmpty(){
		if (top == null)
			return true;
		return false;
	}
	
	public String showCard(){
		Card card = pop();
		return card.name + " of " + card.suit;
	}
	
	/**
	 * swaps a randomly chosen card wit topCard 10,000 times to ensure randomness
	 */
	public void shuffle(){
		Random rnd = new Random();
		
		for (int i = 0; i < 10000; i++){ 
			shuffler = top;
			int index = rnd.nextInt(52);
			
			Card topper = new Card(1, "Spades"); //instantiate placeholder
			topper.name = top.name;
			topper.suit = top.suit;
			topper.value = top.value;
			
			for (int j = 0; j < index; j++){
				shuffler = shuffler.next;
			}
			
			top.name = shuffler.name;
			top.suit = shuffler.suit;
			top.value = shuffler.value;
			
			shuffler.name = topper.name;
			shuffler.suit = topper.suit;
			shuffler.value = topper.value;
			
		}
	}
	
	public int checkRemainingCards(){
		return count;
	}
	
}//end Deck
