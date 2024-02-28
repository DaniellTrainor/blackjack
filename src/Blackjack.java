import java.util.ArrayList;
import java.util.Scanner;

public class Blackjack {

    private Deck deck;
    private ArrayList<Card> player;
    private ArrayList<Card> dealer;
    private int playerChips;

    Scanner kb;

    public Blackjack() {
        deck = new Deck();
        player = new ArrayList<>();
        dealer = new ArrayList<>();
        kb = new Scanner(System.in);
        playerChips = 100; // Initial number of chips for the player
        deck.shuffle(); // Shuffle the deck after initialization
    }

    public static void main(String[] args) {
        Blackjack game = new Blackjack();
        game.run();
    }

    private void run() {
        while (true) {
            System.out.println("\nRemaining chips: " + playerChips);

            // Check if the player has enough chips to continue playing
            if (playerChips <= 0) {
                System.out.println("You're out of chips. Game over!");
                break;
            }

            // Deal initial cards
            dealCards();

            // Display dealer's hand with the first card hidden
            System.out.println("Dealer's hand:\t" + dealer.get(0) + " (Hidden)");
            System.out.println("Player's hand:\t" + getPlayerHand());

            // Check for Blackjack
            if (calculateHandValue(player) == 21) {
                System.out.println("Blackjack! You win!");
                playerChips += 15; // Payout for Blackjack (adjust as needed)
            } else {
                // Player's turn
                playerTurn();
                // Dealer's turn
                dealerTurn();
                // Determine the winner and adjust chips
                determineWinner();
            }

            // Ask if the player wants to play again
            System.out.println("\nDo you want to play again? (yes/no)");
            String playAgain = kb.nextLine().toLowerCase();
            if (!playAgain.equals("yes")) {
                System.out.println("Thanks for playing!");
                break;
            }
        }
    }

    private void dealCards() {
        // Reset hands
        player.clear();
        dealer.clear();

        // Deal initial cards
        player.add(deck.getCard());
        dealer.add(deck.getCard());
        player.add(deck.getCard());
        dealer.add(deck.getCard());
    }

    private void playerTurn() {
        while (true) {
            System.out.println("\nWould you like to hit or stand?");
            String response = kb.nextLine();

            if (response.toLowerCase().equals("hit")) {
                player.add(deck.getCard());
                System.out.println("Player's hand:\t" + getPlayerHand());

                // Check for Blackjack after hitting
                if (calculateHandValue(player) == 21) {
                    System.out.println("Blackjack! You win!");
                    playerChips += 15; // Payout for Blackjack (adjust as needed)
                    break;
                }
            } else if (response.toLowerCase().equals("stand")) {
                break;
            }
        }
    }

    private void dealerTurn() {
        // Reveal the hidden card
        System.out.println("\nDealer's hand:\t" + getDealerHand());

        // Dealer hits until hand value is at least 17
        while (calculateHandValue(dealer) < 17) {
            dealer.add(deck.getCard());
        }

        System.out.println("Dealer's hand:\t" + getDealerHand());
    }

    private void determineWinner() {
        int playerValue = calculateHandValue(player);
        int dealerValue = calculateHandValue(dealer);

        System.out.println("\nFinal Hands:");
        System.out.println("Dealer's hand:\t" + getDealerHand());
        System.out.println("Player's hand:\t" + getPlayerHand());

        if (playerValue > 21 || (dealerValue <= 21 && dealerValue >= playerValue)) {
            System.out.println("Dealer wins. You lose.");
            playerChips -= 10; // Adjust the chip amount as needed
        } else if (playerValue == dealerValue) {
            System.out.println("It's a push! No chips are lost or gained.");
        } else {
            System.out.println("You win!");
            playerChips += 10; // Adjust the chip amount as needed
        }
    }

    private int calculateHandValue(ArrayList<Card> hand) {
        int value = 0;
        int numAces = 0;

        for (Card card : hand) {
            switch (card.getVal()) {
                case 11, 12, 13 -> value += 10;
                case 14 -> {
                    value += 11;
                    numAces++;
                }
                default -> value += card.getVal();
            }
        }

        // Adjust for aces
        while (value > 21 && numAces > 0) {
            value -= 10;
            numAces--;
        }

        return value;
    }


    private String getPlayerHand() {
        StringBuilder handString = new StringBuilder();
        for (Card card : player) {
            handString.append(card).append(" ");
        }
        return handString.toString();
    }

    private String getDealerHand() {
        StringBuilder handString = new StringBuilder();
        boolean firstCard = true;

        for (Card card : dealer) {
            if (firstCard) {
                handString.append(card).append(" ");
                firstCard = false;
            } else {
                handString.append(card).append(" ");
            }
        }

        return handString.toString();
    }

}
