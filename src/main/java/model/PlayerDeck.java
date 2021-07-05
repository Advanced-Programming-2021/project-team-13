package model;

import model.cards.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;


public class PlayerDeck {

    private ArrayList<Card> allCards;
    private ArrayList<Card> allCardsInMainDeck;
    private ArrayList<Card> allCardsInSideDeck;

    public PlayerDeck(UserDeck userDeck) {
        this.allCards = new ArrayList<>();
        this.allCardsInMainDeck = new ArrayList<>();
        this.allCardsInSideDeck = new ArrayList<>();
        HashMap<String, Integer> mainDeck = userDeck.getCardNameToNumberInMain();
        Set<String> mainCard = mainDeck.keySet();
        HashMap<String, Integer> sideDeck = userDeck.getCardNameToNumberInSide();
        Set<String> sideCard = sideDeck.keySet();
        for (String name : mainCard) {
            for (int i = 0; i < mainDeck.get(name); i++) {
                Card card = Card.findCardFromCsv(name);
                allCardsInMainDeck.add(card);
                allCards.add(card);
            }
        }
        for (String name : sideCard) {
            for (int i = 0; i < sideDeck.get(name); i++) {
                Card card = Card.findCardFromCsv(name);
                allCardsInSideDeck.add(card);
                allCards.add(card);
            }
        }
    }

    public ArrayList<Card> getAllCards() {
        return allCards;
    }

    public ArrayList<Card> getAllCardsInMainDeck() {
        return allCardsInMainDeck;
    }

    public ArrayList<Card> getAllCardsInSideDeck() {
        return allCardsInSideDeck;
    }

    public void setAllCardsInMainDeck(ArrayList<Card> allCardsInMainDeck) {
        this.allCardsInMainDeck = allCardsInMainDeck;
    }

    public void setAllCardsInSideDeck(ArrayList<Card> allCardsInSideDeck) {
        this.allCardsInSideDeck = allCardsInSideDeck;
    }

    public void setAllCards(ArrayList<Card> allCards) {
        this.allCards = allCards;
    }

    public void shuffleMainDeck() {
        Collections.shuffle(allCardsInMainDeck);
    }

}

