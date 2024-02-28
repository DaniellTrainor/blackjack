public class Card {
    private int val;
    private int suit;

    public Card(int suit, int val) {
        this.suit = suit;
        this.val = val;

    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public int getSuit() {
        return suit;
    }

    public void setSuit(int suit) {
        this.suit = suit;
    }

    public String toString() {
        String[] suits = {"♥", "♦", "♣", "♠"};
        String[] values = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack of", "Queen of", "King of", "Ace of"};
        return values[this.val-2] + " " + suits[this.suit];
    }

    public String getRank() {
        String[] values = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack of", "Queen of", "King of", "Ace of"};
        return values[this.val - 2];
    }

}
