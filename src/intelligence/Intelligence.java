package intelligence;

import blackjack.Player;

//interface used to implement strategy pattern
public interface Intelligence {
	Player player = null;
	
	public void startAI();
}
