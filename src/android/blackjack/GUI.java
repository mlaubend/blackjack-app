package android.blackjack;

import blackjack.Dealer;
import blackjack.Game;
import blackjack.Player;
import DeckOfCards.Card;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GUI extends Activity implements OnClickListener{
	
	public static final String TAG = "System.out";
	public Node playerCards = new Node();
	public Node computerCards = new Node();
	public Node dealerCards = new Node();
	
	Game game = new Game(this);
	Button hit, stay, split, doubledown, odds, nextHand;
	TextView playerHandValue, playerChipCount, computerHandValue, dealerHandValue;
	View[] views;
	int id = 1;	//I need a unique id when I programmatically create ImageViews
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ImageView card1 = (ImageView)findViewById(R.id.imageView1);	//these are the anchors for the rest of the imageviews
		playerCards.iArray[0] = card1;
		ImageView cCard1 = (ImageView)findViewById(R.id.card1);
		computerCards.iArray[0] = cCard1;
		ImageView dCard1 = (ImageView)findViewById(R.id.dealercard1);
		dealerCards.iArray[0] = dCard1;
		
		views = new View[] {
			hit = (Button)findViewById(R.id.hit),
			stay = (Button)findViewById(R.id.stay),
			split = (Button)findViewById(R.id.split),
			doubledown = (Button)findViewById(R.id.doubledown),
			odds = (Button)findViewById(R.id.odds),
		};		
		nextHand = (Button)findViewById(R.id.nexthand);	//I don't want this disabled when I turn off the other buttons in views array
		playerHandValue = (TextView)findViewById(R.id.playerhandvalue);
		playerChipCount = (TextView)findViewById(R.id.playerChipCount);
		computerHandValue = (TextView)findViewById(R.id.computerhandvalue);
		dealerHandValue = (TextView)findViewById(R.id.dealerhandvalue);
		
		for (View v : views){
			v.setOnClickListener(this);
		}
		nextHand.setOnClickListener(this);
		nextHand.setVisibility(View.GONE);

		//start game
		game.playerName();
		Dealer.getDealer().shuffle();
		
		Dialog keypad = new KeypadDialog(this, this, game);
		keypad.show();
		
		game.startGame();
		setStartingHandViews();		
	}//end onCreate
	
	//TODO: make recursive so that the proper order is shown 
	public void setStartingHandViews(){
		Player player = game.first;

		while (player != null){
			ImageView[] cards = getCorrectNode(player.pType).iArray;
			Card card = player.getHand().topCard;
			
			setCardImage(card, cards[0]);
			card = card.next;
			addCardView(player.pType, card);
			setPlayerHandValue(player.pType);
			player = player.next;
		}
		playerChipCount.setText("Chip Count: " + game.first.getChips());
		
		Card card = Dealer.getDealer().getHand().topCard;
		dealerCards.iArray[0].setImageResource(getResources().getIdentifier("android.blackjack:drawable/back102", null, null));
		card = card.next;
		addCardView("Dealer", card);	
	}
	
	public void setCardImage(Card card, ImageView cardImage){
		int id = getResources().getIdentifier("android.blackjack:drawable/" + matchCardToImage(card), null, null);
		cardImage.setImageResource(id);
	}
	
	public void addCardView(String pType, Card card){
		ImageView[] cards = getCorrectNode(pType).iArray;
		
		int i = 0;
		while (cards[i] != null)
			i++;

		cards[i] = new ImageView(this);
		RelativeLayout layout = (RelativeLayout)findViewById(R.id.playerhand);//getResources().getIdentifier(lType, "id", "android.blackjack"));
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.ALIGN_LEFT, cards[0].getId());
		params.addRule(RelativeLayout.ALIGN_TOP, cards[0].getId());
		cards[i].setRotation(cards[0].getRotation());
		params.leftMargin=20 * i;		//must be offset 20dp per card in hand
		cards[i].setLayoutParams(params);
			
		setCardImage(card, cards[i]);
		cards[i].bringToFront();	
		layout.addView(cards[i]);
		
		if (!pType.equals("Dealer"))
			setPlayerHandValue(pType);
	}
	
	@Override
	public void onClick(View v) {		
	/*	if (game.first.getHand().handValue == 21){
			System.out.println("BLACKJACK!");
			game.first.getHand().inPlay = false;	//TODO: move this to a command in controller later
			checkFinished();
		}
*/
		switch (v.getId()) {
		
			case R.id.hit:
				game.first.commands.hitMe(game.first.pType);
				checkFinished();
				break;
				
			case R.id.stay:
				game.first.commands.stay();
				checkFinished();
				break;
				
			case R.id.split:
				game.first.commands.split();
				checkFinished();
				break;
				
			case R.id.doubledown:
				game.first.commands.doubleDown(game.first.pType);
				checkFinished();
				break;
			
			case R.id.odds:
				game.first.commands.odds();
				checkFinished();
				break;
				
			case R.id.nexthand:
				game.endGame();
				clearCardView();
				Dialog keypad = new KeypadDialog(this, this, game); keypad.show();

				game.startGame();
				toggleNextHandButton();
				setStartingHandViews();
				dealerHandValue.setText("");
				break;
			}
	}
	
	public void checkFinished(){
		setPlayerHandValue("Player");
		if (game.first.commands.playerFinished()){
			game.startIntelligence();
			flipDealerDownCard();
			setPlayerHandValue("Dealer");
			toggleNextHandButton();
			game.winable.determineWinner();
			playerChipCount.setText("Chip Count: " + game.first.getChips());
		}
	}
	
	public void flipDealerDownCard(){
		Card card = Dealer.getDealer().getHand().topCard;
		
		while (card.next.next != null)
			card = card.next;
		
		setCardImage(card, dealerCards.iArray[0]);
	}
	
	public void setSplitCards(Player player){
		Node splitCards = new Node();
		Node cards = getCorrectNode(player.pType);
		

		//remove second card from first hand view
		ImageView[] iArray = cards.iArray;
		iArray[1].setImageResource(Color.TRANSPARENT);
		iArray[1] = null;
		
		//find correct layout and set alignment 
 		RelativeLayout layout = (RelativeLayout)findViewById(R.id.playerhand);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.ALIGN_LEFT, iArray[0].getId());
		params.addRule(RelativeLayout.ABOVE, iArray[0].getId());
		params.bottomMargin = 15;
		
		//create cardview for the second hand
		ImageView card1 = new ImageView(this);
		card1.setId(id); id++;
		card1.setRotation(iArray[0].getRotation());
		setCardImage(player.getHand().next.topCard, card1);
		card1.setLayoutParams(params);
		layout.addView(card1);
		splitCards.iArray[0] = card1;
		
		//add new node to respective node LL
		if (cards.next == null){
			cards.next = splitCards;
			splitCards.previous = cards;
		}
			
		else {
			splitCards.next = cards.next.next;
			cards.next = splitCards;
			splitCards.previous = cards;
		}
		
		setPlayerHandValue(player.pType);
	}

	public void clearCardView(){
		clearNode(playerCards);
		clearNode(computerCards);
		clearNode(dealerCards);
		
		//rolling back LL for the next hand.  Remember, the anchor ImageView is in the starting Node
		while (playerCards.previous != null){
			playerCards = playerCards.previous;
		} 
		
		while (computerCards.previous != null){
			computerCards = computerCards.previous;
		} 
		id = 1;	//don't want this getting too big
	}
	
	public void clearNode(Node node){
		while (node.previous != null){
			clearArray(node.iArray, 0);
			node = node.previous;
		}
		clearArray(node.iArray, 1);
	}
	
	public void clearArray(ImageView[] array, int count){
		while (array[count] != null){
			array[count].setImageResource(Color.TRANSPARENT);
			array[count] = null;
			count++;
		}
	}
	
	public void toggleNextHandButton(){
		if (nextHand.getVisibility() == View.VISIBLE){
			nextHand.setVisibility(View.GONE);
			enableButtons();
		}
		else {
			nextHand.setVisibility(View.VISIBLE);
			disableButtons();
		}
	}	

	public String getArrayName(String pType){
		switch (pType) {
		case "Computer":
			return "computerhand";
		case "Player":
			return "playerhand";
		case "Dealer":
			return "dealerhand";
		}
		return null;
	}
		
	public String matchCardToImage(Card card){

		if (card.suit.equals("Spades")){
			switch (card.showCard()){
				case "ACE of Spades":
					return "acespade";
				case "KING of Spades":
					return "kingspade";
				case "QUEEN of Spades":
					return "queenspade";
				case "JACK of Spades":
					return "jackspade";
				case "10 of Spades":
					return "tenspade";
				case "9 of Spades":
					return "ninespade";
				case "8 of Spades":
					return "eightspade";
				case "7 of Spades":
					return "sevenspade";
				case "6 of Spades":
					return "sixspade";
				case "5 of Spades":
					return "fivespade";
				case "4 of Spades":
					return "fourspade";
				case "3 of Spades":
					return "threespade";
				case "2 of Spades":
					return "twospade";
			}
		}
		
	
		else if (card.suit.equals("Clubs")){
			switch (card.showCard()){
				case "ACE of Clubs":
					return "aceclub";
				case "KING of Clubs":
					return "kingclub";
				case "QUEEN of Clubs":
					return "queenclub";
				case "JACK of Clubs":
					return "jackclub";
				case "10 of Clubs":
					return "tenclub";
				case "9 of Clubs":
					return "nineclub";
				case "8 of Clubs":
					return "eightclub";
				case "7 of Clubs":
					return "sevenclub";
				case "6 of Clubs":
					return "sixclub";
				case "5 of Clubs":
					return "fiveclub";
				case "4 of Clubs":
					return "fourclub";
				case "3 of Clubs":
					return "threeclub";
				case "2 of Clubs":
					return "twoclub";
			}
		}
		
		else if (card.suit.equals("Hearts")){
			switch (card.showCard()){
				case "ACE of Hearts":
					return "aceheart";
				case "KING of Hearts":
					return "kingheart";
				case "QUEEN of Hearts":
					return "queenheart";
				case "JACK of Hearts":
					return "jackheart";
				case "10 of Hearts":
					return "tenheart";
				case "9 of Hearts":
					return "nineheart";
				case "8 of Hearts":
					return "eightheart";
				case "7 of Hearts":
					return "sevenheart";
				case "6 of Hearts":
					return "sixheart";
				case "5 of Hearts":
					return "fiveheart";
				case "4 of Hearts":
					return "fourheart";
				case "3 of Hearts":
					return "threeheart";
				case "2 of Hearts":
					return "twoheart";
			}
		}
		
		else if (card.suit.equals("Diamonds")){
			switch (card.showCard()){
				case "ACE of Diamonds":
					return "acediamond";
				case "KING of Diamonds":
					return "kingdiamond";
				case "QUEEN of Diamonds":
					return "queendiamond";
				case "JACK of Diamonds":
					return "jackdiamond";
				case "10 of Diamonds":
					return "tendiamond";
				case "9 of Diamonds":
					return "ninediamond";
				case "8 of Diamonds":
					return "eightdiamond";
				case "7 of Diamonds":
					return "sevendiamond";
				case "6 of Diamonds":
					return "sixdiamond";
				case "5 of Diamonds":
					return "fivediamond";
				case "4 of Diamonds":
					return "fourdiamond";
				case "3 of Diamonds":
					return "threediamond";
				case "2 of Diamonds":
					return "twodiamond";
			}
		}
		return "";
	}//end matchCardToImage
	
	public void disableButtons(){
		for (View v : views)
			v.setEnabled(false);
	}
	
	public void enableButtons(){
		for (View v : views)
			v.setEnabled(true);
	}
	
	public Node getCorrectNode(String pType){
		switch (pType) {
		case "Computer":
			return computerCards;
		case "Player":
			return playerCards;
		case "Dealer":
			return dealerCards;
		}
		return null;
	}
	
	public void nextNode(String pType){
		switch (pType){
		case "Computer":
			computerCards = computerCards.next;
			break;
		case "Player":
			playerCards = playerCards.next;
			break;
		case "Dealer":
			dealerCards = dealerCards.next;
			break;
		}
	}
	
	public void setPlayerHandValue(String pType){
		if (pType.equals("Player"))
			playerHandValue.setText("Hand Value: " + game.first.getHand().handValue);
		else if (pType.equals("Computer"))
			computerHandValue.setText("Hand Value: " + game.first.next.getHand().handValue);
		else
			dealerHandValue.setText("Hand Value: " + Dealer.getDealer().getHand().handValue);
		
	}
}//end GUI





