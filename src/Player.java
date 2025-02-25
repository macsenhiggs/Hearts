import java.util.Arrays;
import java.util.LinkedList;

public class Player {
    private LinkedList<Card> hand;

    public static int IDCount = 1;
    public int ID;

    public int score;

    public Player(LinkedList<Card> hand) {
        this.hand = hand;
        ID = IDCount++;
        this.score = 0;
    }

    public LinkedList<Card> getHand() {
        return hand;
    }

    public boolean contains(int rank, String suit) {
        for (Card myCard : hand) {
            if (myCard.getSuit().equalsIgnoreCase(suit) && myCard.getRank() == rank) {
                return true;
            }
        }
        return false;
    }

    private String suitCounts(LinkedList<Card> hand, String option) {
        int[] suits = new int[4];
        String[] suitNames = {"Clubs", "Diamonds", "Hearts", "Spades"};
        for (Card myCard : hand) {
            switch (myCard.getSuit()) {
                case "Clubs" -> suits[0]++;
                case "Diamonds" -> suits[1]++;
                case "Hearts" -> suits[2]++;
                case "Spades" -> suits[3]++;
            }
        }
        Integer[] indices = {0, 1, 2, 3};
        Arrays.sort(indices, (a, b) -> Integer.compare(suits[b], suits[a])); // Sort in descending order
        if (option.equalsIgnoreCase("Most")) {
            return suitNames[indices[0]];
        } else if (option.equalsIgnoreCase("Least")) {
            return suitNames[indices[3]];
        } else {
            return "Error!";
        }
    }

    private int[] rankCounts() {
        int[] ranks = new int[13];
        // suit order: Clubs, Diamonds, Hearts, Spades
        for (Card myCard : hand) {
            switch (myCard.getSuit()) {
                case "2" -> ranks[0]++;
                case "3" -> ranks[1]++;
                case "4" -> ranks[2]++;
                case "5" -> ranks[3]++;
                case "6" -> ranks[4]++;
                case "7" -> ranks[5]++;
                case "8" -> ranks[6]++;
                case "9" -> ranks[7]++;
                case "10" -> ranks[8]++;
                case "J" -> ranks[9]++;
                case "Q" -> ranks[10]++;
                case "K" -> ranks[11]++;
                case "A" -> ranks[12]++;
            }
        }
        return ranks;
    }

    private boolean haveSuit(String suit) {
        for (Card myCard : hand) {
            if (myCard.getSuit().equals(suit)) { // Assuming Card has a getSuit() method
                return true; // Found a card of the given suit
            }
        }
        return false; // No cards of the given suit
    }

    private LinkedList<Card> subSuit (LinkedList<Card> hand, String suit) {
        LinkedList<Card> subHand = new LinkedList<>();
        for (Card myCard : hand) {
            if (myCard.getSuit().equals(suit)) {
                subHand.add(myCard);
            }
        }
        return subHand;
    }

    private Card getMax(LinkedList<Card> hand) {
        Card highCard = new Card();
        for (Card myCard : hand) {
            if (myCard.getRank() > highCard.getRank()) {
                highCard = myCard;
            }
        }
        return highCard;
    }

    private Card getMin(LinkedList<Card> hand) {
        Card lowCard = new Card(null,15);
        for (Card myCard : hand) {
            if (myCard.getRank() < lowCard.getRank()) {
                lowCard = myCard;
            }
        }
        return lowCard;
    }

    public int score (LinkedList<Card> pile) {
        int myScore = 0;
        for (Card myCard : pile) {
            if (myCard.getSuit().equals("Hearts")) {
                myScore++;
            } else if (myCard.getSuit().equals("Spades") && myCard.getRank() == 12) {
                myScore += 13;
            }
        }
        return myScore;
    }

    public Card takeTurn(LinkedList<Card> pile, String startingSuit, int playerCount) {
        LinkedList<Card> subHand;
        Card playedCard;

        if (startingSuit == null) { //you're starting the pile, choice of suit
            String mostSuit = suitCounts(hand,"Most");
            subHand = subSuit(hand, mostSuit);
            playedCard = getMin(subHand);
            hand.remove(playedCard);
            return playedCard;
        }
        else if (pile.size() == 0) { //you're starting the pile, must play clubs
            subHand = subSuit(hand, "Clubs");
            playedCard = getMin(subHand);
            hand.remove(playedCard);
            return playedCard;
        }
        else {
            if (haveSuit(startingSuit)) {
                subHand = subSuit(hand, startingSuit);
                if (score(pile) == 0 && pile.size() < playerCount - 1) {
                    playedCard = getMax(subHand);
                } else {
                    playedCard = getMin(subHand);
                }
            } else {
                if (haveSuit("Hearts")) {
                    subHand = subSuit(hand, "Hearts");
                    playedCard = getMax(subHand);
                } else {
                    String leastSuit = suitCounts(hand, "Least");
                    subHand = subSuit(hand, leastSuit);
                    playedCard = getMax(subHand);
                }
            }
            hand.remove(playedCard);
            return playedCard;
        }
    }

}
