package model;

import java.util.ArrayList;

public class Deck implements Comparable<Deck> {
    private final static int MIN_CARDS_IN_MAIN_DECK = 40;
    private final static int MAX_CARDS_IN_MAIN_DECK = 60;
    private String name;
    private ArrayList<Card> allCards = new ArrayList<>();
    private ArrayList<Card> allCardsInMainDeck = new ArrayList<>();
    private ArrayList<Card> allCardsInSideDeck = new ArrayList<>();
    //private ArrayList<Deck> allPlayersDecks = new ArrayList<>();
    private boolean isValid;
    private boolean isActive;
    private int numberOfCards;

    public Deck(String name) {
        setName(name);
        setValid(false);
        setNumberOfCards(0);
        setIsActive(false);
    }


    /*public Deck getDeckByName(String name) {
        for (Deck allPlayersDeck : allPlayersDecks) {
            if (allPlayersDeck.name.equals(name))
                return allPlayersDeck;
        }
        return null;
    }
*/

    public static int getMinCardsInMainDeck() {
        return MIN_CARDS_IN_MAIN_DECK;
    }

    public static int getMaxCardsInMainDeck() {
        return MAX_CARDS_IN_MAIN_DECK;
    }

    private void setIsActive(boolean b) {
        isActive = b;
    }

    private boolean getIsActive() {
        return isActive;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Card> getAllCards() {
        return allCards;
    }

    public void setAllCards(ArrayList<Card> allCards) {
        this.allCards = allCards;
    }

    public ArrayList<Card> getAllCardsInMainDeck() {
        return allCardsInMainDeck;
    }

    public void setAllCardsInMainDeck(ArrayList<Card> allCardsInMainDeck) {
        this.allCardsInMainDeck = allCardsInMainDeck;
    }

    public ArrayList<Card> getAllCardsInSideDeck() {
        return allCardsInSideDeck;
    }

    public void setAllCardsInSideDeck(ArrayList<Card> allCardsInSideDeck) {
        this.allCardsInSideDeck = allCardsInSideDeck;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public boolean isActive() {
        return isActive;
    }

    public int getNumberOfCards() {
        return numberOfCards;
    }

    public void setNumberOfCards(int numberOfCards) {
        this.numberOfCards = numberOfCards;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public void addNewCard(Card card, boolean isSide) {
        setNumberOfCards(getNumberOfCards() + 1);
        if (isSide)
            allCardsInSideDeck.add(card);
        else
            allCardsInMainDeck.add(card);
        if (allCardsInMainDeck.size() >= 40)
            setValid(true);
    }

    public String getName() {
        return name;
    }

    public Card getCardByName(String cardName, boolean isSide) {
        if (isSide) {
            for (Card card : getAllCardsInSideDeck())
                if (card.getCardName().equals(cardName))
                    return card;
        } else
            for (Card card : getAllCardsInSideDeck()) {
                if (card.getCardName().equals(cardName))
                    return card;
            }
        return null;
    }

    public void removeCard(Card card, boolean isSide) {     // from where??????????????????????????????????????????????????
        if (isSide)
            allCardsInSideDeck.remove(card);
        else
            allCardsInMainDeck.remove(card);
        setNumberOfCards(getNumberOfCards() - 1);
    }

    @Override
    public String toString() {
        return name + ": main deck " + allCardsInMainDeck.size() +
                ", side deck " + allCardsInSideDeck.size() + ", "
                + (isValid ? "valid" : "invalid");
    }

    @Override
    public int compareTo(Deck deck) {
        if (deck.getName().compareTo(name) < 0) return 1;
        return -1;
    }
}

