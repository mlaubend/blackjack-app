# BlackJack

Card:
The standard node in a singly linked list, contains an int value, string suit, string name, and Card next value.  The constructor takes in two parameters: one describing the value and one describing the name of the card.  Value is modulated by 13.

Deck:
Standard stack containing 52 cards.  Constructor creates 13 cards of each value per suit. The shuffle function swaps a randomly chosen card with the topCard 10,000 times to ensure proper randomization. Cards are pushed onto the deck and popped off the deck.

Hand:
A node in a doubly linked list.  Contains a reference to the next hand in the list , and a reference to the previous hand in the list.  The booleans busted, inPlay, isAceValue and the ints bet and count were moved here from player due to the split function in the Commands class.  If a player is holding multiple hands then each hand must hold their own variables. 

Player:
A node in a singly linked list.   Each player contains an Intelligence object, as well as a hand and a reference to the next player in the list.  Contains the functions peekHand(), getCard(card), nextMove(string), and getters/setters for private variables.

Dealer:
Singleton, responsible for dealing the first round of cards to each player via deal(Player), dealing out the next card to the respective player based on the given command via dealCard(Player), and shuffling and reshuffling the deck via shuffle() and checkReshuffle().

Intelligence:
Interface containing the startAI() function.  An interface was implemented for all Intelligence objects in case the human player wanted to swap out player controlled moves for computer controlled moves (e.g. strategy pattern).

ComputerIntelligence:
Intelligence for computer player.  Has 3 2D arrays containing every optimal move given the computer player's handValue and the Dealers' topCard shown.  startAI() initiates a loop through the computer player's hands, and initiates the doCommand() function based on the optimal move given in the arrays.  Also contains functions for indexing into the proper array to find the optimal move. 

DealerIntelligence.
Intelligencce for the dealer player.  Intelligence MUST hit untl handValue is greater than 16. startAI() function initiates a mustHit() loop. mustHit() returns false when the dealer's handValue is greater than 16.

PlayerIntelligence:
Hands over controls to the human player.  startAI() initiates bettingLoop(), which reads in the players next move from System.in for each hand until busted, or until the player stays. 

Commands:
every Player object contains a commands object.  Contains every next move command for each player.  hitMe() checks if hand is busted before and after receiving card;  Stay() flips hand.inPlay to false;  doubleDown() doubles the bet and the player receives one card only before flipping hand.inPlay to false; split was a pain in the ass
split:
split first checks to see if the player is holding a pair, if not an error message is printed and the command prompt is shown again.  It then creates a newHand, takes the topCard from current hand and copies it as newHand topCard, then iterates down the current hand list to make the current hand second card the new current hand topCard.  It also checks if the player split on double aces, in which case it resets the newHand.topCard.value to the original 11 value. newHand topCard.next value is nulled out as well as current hand.topCard.next value.  current hand handvalue is decremented by the lost card amount, current hand count is decremented by 1, and finally the newHand is added to the top of the Hand list.

Game:
all game logic.  Contains a Winable object, instantiates the Dealer singleton, and has a reference to the first Player in the list.  Also contains main() and main game loop.  During main() a new game is instantiated, the human player is asked to input their name via playerName() and the player list is populated by addPlayer(), and the deck is shuffled by the dealer.  then the main game loop is started via startGame() - human player is asked to input the ante until he inputs a valid ante (no greater than their chip amount), the dealer deals the first round of cards, and the human player hand is shown as well as the dealers topcard.  The main loop then goes through a loop initiating all players' Intelligence objects using startAI() through the startIntelligence() function.  the game loop then initiates the determineWinner() function in the winable object. te game loop ends the game through endGame(), where each players' hands' are replaced with a new, empty hand.

Winable:
All logic based on betting and winning the game.  determineWinner() first checks if the dealer has busted, if so each player is given their bet*2 per unbusted hand.  It then enters a nested loop of players and players hands, checks if their busted, checks if their hand beats the dealers hand, then pays the winner its winnings only after all the current players' hands have been checked.  If push, only the bet is returned to player. 



