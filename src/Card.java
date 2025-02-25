public class Card {
    private final String suit;
    private final int rank;

    public Card (String suit, int rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public Card() {
        this.suit = null;
        this.rank = 0;
    }

    public String getSuit() {
        return suit;
    }

    public int getRank() {
        return rank;
    }

    @Override
    public String toString(){
        return rank + " of " + suit;
    }
}
