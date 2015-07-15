package blackjack;

import intelligence.ComputerIntelligence;
import intelligence.PlayerIntelligence;

import java.util.Scanner;

import DeckOfCards.Hand;

public class Game {

	Dealer dealer = Dealer.getDealer();
	Player first;
	Winable winable;
	
	public void playerName(){
		System.out.print("Please enter your name: ");
		Scanner scanner = new Scanner(System.in); //can't close scanner because it will also close System.in stream for other scanners
		addPlayer("Emily", "Computer");
		addPlayer(scanner.next(), "Player");
		printPlayers();
	}
	/**
	 * add a player to the player list
	 * @param name the name of the player being added
	 * @param type the type of player being added e.g. "Player"/"Computer"
	 */
	public void addPlayer(String name, String type){
		Player player = new Player(name);
		
		if(type.equals("Player"))
			player.intelligence = new PlayerIntelligence(player);
		else if (type.equals("Computer"))
			player.intelligence = new ComputerIntelligence(player);
			
		if (first == null)
			first = player;
		else{
			player.next = first;
			first = player;
		}
		winable = new Winable(first);
	}
	
	public void printPlayers(){
		Player current = first;
		while (current != null){
			System.out.println(current.name);
			current = current.next;
		}	
	}
	
	public void startGame(){
		while(!anteUp());
		Dealer.getDealer().deal(first);
		first.peekHand();
		Dealer.getDealer().showTopCard();
	}
	
	public boolean anteUp(){
		Player current = first;
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter your ante: ");
		int ante = scanner.nextInt();
		
		if (!current.enoughChips(ante)){
			System.out.println(current.name + " chips are: $" + current.getChips());
			return false;
		}
		
		current.getHand().setBet(ante);			
		if (ante >= 5)
			current.ante = true;
		
		//decrement chips for each player
		current.decrementChips(current.getHand().getBet());
		current = current.next;
		while (current != null){
			current.getHand().setBet(5);
			current.decrementChips(current.getHand().getBet());
			current = current.next;
		}
		return true;
	}
	
	public void startIntelligence(){
		Player player = first;
		
		while (player != null){
			player.intelligence.startAI();
			player = player.next;
		}
		Dealer.getDealer().intelligence.startAI();
	}
	
	/**
	 * each player in player list receives a blank hand and the Dealer checks to reshuffle
	 */
	public void endGame(){
		Player current = first;
		while (current != null){
			current.setHand(new Hand());
			current = current.next;
		}
		Dealer.getDealer().setHand(new Hand());
		Dealer.checkReshuffle();
	}
	
	public static void main(String[] args){
		Game game = new Game();
		game.playerName();
		Dealer.getDealer().shuffle();
		
		while(true){
			game.startGame();
			game.startIntelligence();
			game.winable.determineWinner();
			game.endGame();
			}
	}
}
