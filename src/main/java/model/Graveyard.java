package model;

import java.util.ArrayList;

public class Graveyard {
    private ArrayList<Card> allCards;
    private String ownerName;

    Graveyard(){

    }

    public Card getCardFromGraveyard(String name) {
        for (Card allCard : allCards) {
            if (allCard.cardName.equals(name))
                return allCard;
        }
        return null;
    }

    public void addCard(Card card) {
        allCards.add(card);
    }

    public ArrayList<Card> getAllCards() {
        return allCards;
    }

    public void removeCard(Card card) {
        allCards.remove(card);
    }
}
