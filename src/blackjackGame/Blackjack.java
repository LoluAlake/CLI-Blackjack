package blackjackGame;
import java.util.ArrayList;
import java.util.Scanner;
public class Blackjack {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
        String playerName;
        String user;

     //    ask for Username
        System.out.println("Enter your name?");
        user = input.nextLine();
        playerName = user;

        // Loop
        do {

            // WELCOME MESSAGE
            System.out.print("\n");
            System.out.println("Welcome to Blackjack!!");
            System.out.print("\n");

           
            Player player = new Player(playerName);
            Player dealer = new Player("Dealer");
            Deck deck = new Deck();
            deck.shuffle(0);
            boolean endGame = false;

            // player gets 2 cards
            player.addCard(deck.draw());
            player.addCard(deck.draw());
            System.out.println(player.getHandAsString(false));

            // dealer gets 2 cards
            dealer.addCard(deck.draw());
            dealer.addCard(deck.draw());
            System.out.println(dealer.getHandAsString(true));

            // Asks Player to Hit or Stand
            do {
                System.out.println("Would " + player.getname() + " like to Hit or Stand? 'Hit/Stand'");
                do {
                    user = input.nextLine();
                } while (!user.equalsIgnoreCase("Hit") && !user.equalsIgnoreCase("Stand"));

                // Player chooses to Hit
                if (user.equalsIgnoreCase("Hit")) {
                    player.addCard(deck.draw());
                    System.out.println(player.getname() + " drew a card.");
                    System.out.println();
                    System.out.println(player.getHandAsString(false));
                    if (player.getHandTotal() > 21) {
                        System.out.println(
                                "Busted and total is " + player.getHandTotal() + ". Dealer wins!");
                        endGame = true;
                    }
                }
                // Player chooses to Stand
                if (user.equalsIgnoreCase("Stand")) {
                    System.out.println("You have chosen to stand. Player's hand: " + player.getHandTotal());
                }

            } while (user.equalsIgnoreCase("Hit") && !endGame);

            // dealer
            if (!endGame) {
                System.out.println();
                System.out.println("- Dealer's game -");
                System.out.println();
                System.out.println(dealer.getHandAsString(false));
            }

            while (!endGame) {

                if (dealer.getHandTotal() <= 17) {
                    // new card
                    dealer.addCard(deck.draw());
                    System.out.println(dealer.getname() + " drew another card");
                    System.out.println();
                    System.out.println(dealer.getHandAsString(false));
                    if (dealer.getHandTotal() == 17) {
                        System.out.println("Blackjack! Dealer wins.");
                        endGame = true;
                    }
                    if (dealer.getHandTotal() > 21) {
                        System.out.println("Dealer's busted and total is " + dealer.getHandTotal() + ". "
                                + player.getname() + " wins!");
                        endGame = true;
                    }

                } else {
                    // STAY
                    System.out.println("Dealer choses to stand!");
                    System.out.println();
                    int dealerCardTotal = dealer.getHandTotal();
                    int playerCardTotal = player.getHandTotal();

                    if (dealerCardTotal > playerCardTotal) {
                        System.out.println("Both players chose to stand. The winner is " + dealer.getname()
                                + " with a total of " + dealerCardTotal + ".");
                    } else {
                        System.out.println("Both players chose to stand. The winner is " + player.getname()
                                + " with a total of " + playerCardTotal + ".");
                    }
                    endGame = true;
                }

            }

            // ask for new game
            System.out.println();
            System.out.println("Would you like to begin a new game?  'Yes/No' :");
            do {
                user = input.nextLine();
            } while (!user.equalsIgnoreCase("Yes") && !user.equalsIgnoreCase("No"));

        } while (user.equalsIgnoreCase("Yes"));

        
        input.close();
    
}

}

class Player {

     String playerName;

     ArrayList<Card> hand;

    public Player(String playerName) {
        this.playerName = playerName;
        this.hand = new ArrayList<Card>();
    }

    public String getname() {
        return playerName;
    }

    public void addCard(Card card) {
        hand.add(card);
    }

    public int getHandTotal() {
        int handTotal = 0;
        for (Card card : hand) {
            handTotal += card.getRank().getValue();
        }
        return handTotal;
    }

    public String getHandAsString(boolean hideCard) {
        StringBuilder sb = new StringBuilder();
        sb.append(playerName + "\'s current hand:");
        sb.append('\n');
        for (int i = 0; i < hand.size(); i++) {
            if (i == 0 && hideCard) {
                sb.append("[Hidden card]");
                sb.append('\n');
            } else {
                sb.append(hand.get(i));
                sb.append('\n');
            }
        }
        return sb.toString();
    }

}
class Deck {
 int numOfCards = 52;
  Card[] deck;
 int currentCard;
 
 public Deck() {
	 
	 deck = new Card [numOfCards];
	 int i = 0;
	 for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
            	deck[i++] = (new Card(rank, suit));
            	
            	currentCard = 0;
            }
	 }
	 
 }
	 public void shuffle(int s) {
		 
		 int i = 0, j = 0, k;
		 
		 for ( k = 0; k < s; k++) {
			i = (int) (Math.random() * numOfCards);
			Card temp = deck[i];
			deck[i] = deck[j];
			deck[j] = temp;
		 } 
		 currentCard = 0;
	 }
	 
	 public Card deal() {
		 if (currentCard < numOfCards) {
			 return (deck[currentCard++]);
		 }
		 else { 
			 return null;
		 }
		 
	 }
	 Card draw(){
	        if (numOfCards < 0)
	              return null;
	          return deck[--numOfCards];
	       }

	 public String toString()
     {
  	 String Str = "";
  	 int k;
   
  	 k = 0;
  	 for ( int i = 0; i < 4; i++ )
  	 {
  	    for ( int j = 1; j <= 13; j++ )
  		Str += (deck[k++] + " ");
   
  	    Str += "\n";
  	 }
  	 return (Str);
     } 

}

 class Card {

    private Rank rank;

    private Suit suit;

    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public Rank getRank() {
        return rank;
    }

    public Suit getSuit() {
        return suit;
    }

    @Override
    public String toString() {
        return rank + " of " + suit;
    }

}
