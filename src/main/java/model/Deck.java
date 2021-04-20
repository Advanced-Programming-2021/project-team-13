package model;

import java.util.ArrayList;

public class Deck extends User {
    private int MIN_CARDS_IN_MAIN_DECK;
    private int MAX_CARDS_IN_MAIN_DECK;
    private String name;
    private ArrayList<Card> allCards = new ArrayList<>();
    private ArrayList<Card> allCardsInMainDeck = new ArrayList<>();
    private ArrayList<Card> allCardsInSideDeck = new ArrayList<>();
    private ArrayList<Deck> allPlayersDecks = new ArrayList<>();
    private boolean isValid;
    private boolean isActive;
    private int numberOfCards;

    Deck(String name) {
        this.name = name;
    }

    public Deck getDeckByName(String name) {
        for (Deck allPlayersDeck : allPlayersDecks) {
            if (allPlayersDeck.name.equals(name))
                return allPlayersDeck;
        }
        return null;
    }

    public boolean isDeckActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public void addNewCard(Card card) {
        allCards.add(card);
    }

    public void removeCard(Card card) {     // from where??????????????????????????????????????????????????
        allCards.remove(card);
        allCardsInMainDeck.remove(card);
        allCardsInSideDeck.remove(card);
    }

}

