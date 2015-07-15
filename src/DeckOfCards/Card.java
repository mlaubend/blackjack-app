package DeckOfCards;

public class Card {

	public int value;
	public String suit;
	public String name;
	public Card next = null;
	
	public String showCard(){
		return name + " of " + suit;
	}
	
	public Card(int i, String suit){
		
		switch (i%13){
			case 0:
				name = "ACE";
				value = 11;
				break;
			case 1:
				name = "2";
				value = 2;
				break;
			case 2:
				name = "3";
				value = 3;
				break;
			case 3:
				name ="4";
				value = 4;
				break;
			case 4:
				name = "5";
				value = 5;
				break;
			case 5:
				name = "6";
				value = 6;
				break;
			case 6:
				name = "7";
				value = 7;
				break;
			case 7:
				name = "8";
				value = 8;
				break;
			case 8:
				name = "9";
				value = 9;
				break;
			case 9:
				name = "10";
				value = 10;
				break;
			case 10:
				name = "JACK";
				value = 10;
				break;
			case 11:
				name = "QUEEN";
				value = 10;
				break;
			case 12:
				name = "KING";
				value = 10;
				break;
		}
		this.suit = suit;
	}
}
