package intelligence;

import blackjack.Dealer;
import blackjack.Player;

public class DealerIntelligence implements Intelligence{
	Player player;
	
	public DealerIntelligence(Player player){
		this.player = player;
	}
	
	@Override
	public void startAI() {
		while (mustHit());		
	}
	
	/**
	 * dealer must hit until his handValue is greater than 16
	 * @return true if his hand value is 16 or smaller
	 */
	public boolean mustHit(){
		if (Dealer.getDealer().getHand().handValue <= 16){
			System.out.println("Dealer hits");
			Dealer.getDealer().commands.hitMe(player.pType);
		}
		if (Dealer.getDealer().getHand().handValue <= 16){
			return true;
		}
		return false;
	}
}
