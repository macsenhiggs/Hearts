import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
public class Main {

    public static LinkedList<Card> makeDeck() {
        LinkedList<Card> deck = new LinkedList<>();
        String[] suite = new String[] {"Clubs","Diamonds","Hearts","Spades"};
        int[] ranks= new int[] {2,3,4,5,6,7,8,9,10,11,12,13,14};
        for(int i=0; i<4; i++) {
            for(int j=0; j<13; j++) {
                Card myCard = new Card(suite[i],ranks[j]);
                deck.add(myCard);
            }
        }
        return deck;
    }

    public static boolean shootMoonCheck(LinkedList<Card> scoringPile) {
        String[] shootMoon = {"2 of Hearts", "3 of Hearts", "4 of Hearts", "5 of Hearts", "7 of Hearts", "8 of Hearts",
                "9 of Hearts", "10 of Hearts", "11 of Hearts", "12 of Hearts", "13 of Hearts", "14 of Hearts", "12 of Spades"};
        String[] playerHand = new String[13];
        for (int i = 0; i < scoringPile.size(); i++) {
            playerHand[i] = scoringPile.get(i).toString();
        }
        Arrays.sort(playerHand);
        Arrays.sort(shootMoon);
        return Arrays.equals(shootMoon, playerHand);
    }

    public static boolean isLeadingCard (Card myCard, LinkedList<Card> pile) {
        Card leadingCard = pile.get(0);
        String pileSuit = leadingCard.getSuit();
        for (Card c : pile) {
            if (c.getSuit().equals(pileSuit) && c.getRank() > leadingCard.getRank()) {
                leadingCard = c;
            }
        }
        return leadingCard == myCard;
    }

    public static void printScores(Player[] players) {
        for (Player myPlayer : players) {
            System.out.println("Player " + myPlayer.ID + " score : " + myPlayer.score);
        }
    }


    public static void main(String[] args) {
        LinkedList<Card> deck;
        deck = makeDeck();
        Collections.shuffle(deck);
        int playerCount = 4;
        LinkedList<LinkedList<Card>> startingHands = new LinkedList<>();
        for (int i = 0; i < playerCount; i++) {
            startingHands.add(new LinkedList<>()); // Add an empty hand for each player
        }

        int cardNum = 0;
        while (!deck.isEmpty()) {
            int dealTurn = cardNum % playerCount;
            //startingHands.get(dealTurn).add(deck.remove(0));
            Card nextCard = deck.get(0);
            deck.remove(0);
            startingHands.get(dealTurn).add(nextCard);
            cardNum++;
        }
        System.out.println("Deck created");

        Player[] players = new Player[4];
        for (int i = 0; i < playerCount; i++) {
            players[i] = new Player(startingHands.get(i));
        }
        System.out.println("Players created");

        //Game loop starts here
        boolean twoClubFound = false;
        int count = 0;
        Player currentPlayer;
        while (!twoClubFound) {
            currentPlayer = players[count % 4];
            if (currentPlayer.contains(2, "clubs")) {
                twoClubFound = true;
            } else {
                count++;
            }
        }
        int round = 1;
        String startingSuit = "Clubs";
        System.out.println("Starting game");
        currentPlayer = players[count % 4];

        while (round <= 13) { //game loop
            LinkedList<Card> pile = new LinkedList<>();
            Player leadingPlayer = currentPlayer;

            while (pile.size() < 4) { //round loop
                pile.add(currentPlayer.takeTurn(pile,startingSuit,playerCount));
                if (isLeadingCard(pile.getLast(),pile)) {
                    leadingPlayer = currentPlayer;
                }
                count++;
            } //end of round
            System.out.println("This round's pile: " + pile);
            startingSuit = null;
            Player shooter = null;

            for (Player myPlayer : players) {
                if (shootMoonCheck(myPlayer.getHand())) {
                    shooter = myPlayer;
                    break;
                }
            }

            if (shooter != null) {
                for (Player myPlayer : players) {
                    if (myPlayer != shooter) {
                        myPlayer.score += 13;
                    }
                }
            } else {
                for (Card myCard : pile) {
                    if (myCard.getSuit().equals("Hearts")) {
                        leadingPlayer.score++;
                    } else if (myCard.getSuit().equals("Spades") && myCard.getRank() == 12) {
                        leadingPlayer.score += 13;
                    }
                }
            }
            System.out.println("End of round " + round);
            round++;
            printScores(players);

        } //end of game





    }
}