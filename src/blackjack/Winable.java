package blackjack;

public class Winable {
	Player first;
	
	public Winable(Player first){
		this.first = first;
	}
	
	public void determineWinner(){
		Player current = first;
		int currentWinnings = 0;
		
		if (dealerBust()){
			System.out.println("Dealer hand value is " + Dealer.getDealer().getHand().handValue);
			return;
		}
	
		while (current != null){
			while (current.getHand() != null){
				if (!current.getHand().busted){
					currentWinnings += playerWin(current);
					playerPush(current);
				}
				System.out.println( current.name + " hand value is " + current.getHand().handValue);
				current.setHand(current.getHand().next);
			}
			payWinner(currentWinnings, current);
			System.out.println(current.name + " chips are: " + current.getChips());
			
			current = current.next;
			currentWinnings = 0;
		}
		System.out.println("Dealer hand value is " + Dealer.getDealer().getHand().handValue);
	}
	
	public int playerWin(Player current){
		if (current.getHand().handValue > Dealer.getDealer().getHand().handValue){
			return current.getHand().getBet()*2;
		}
		return 0;
	}
	
	public int playerPush(Player current){
		if (current.getHand().handValue == Dealer.getDealer().getHand().handValue){
			System.out.println("Push!");
			current.incrementChips(current.getHand().getBet());
			return current.getHand().getBet();
		}
		return 0;
	}
	
	public boolean dealerBust(){
		Player current = first;
		
		if (Dealer.getDealer().getHand().isBusted()){
			System.out.println("Dealer BUSTS!");
		
			while (current != null){
				while (current.getHand() != null){
					System.out.println( current.name + " hand value is " + current.getHand().handValue);
					if (!current.getHand().isBusted()){
						payWinner(current.getHand().getBet()*2, current);
					}
					current.setHand(current.getHand().next);
				}
				System.out.println(current.name + " chips are: " + current.getChips());
				current = current.next;
			}
			return true;
		}
		return false;
	}
	
	public void payWinner(int winnings, Player player){
		System.out.println(player.name + " wins $" + winnings/2);
		player.incrementChips(winnings);
	}
}
