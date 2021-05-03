package model;

import model.cards.Card;
import model.cards.Monster;
import model.players.Player;

import java.util.ArrayList;

public class Graveyard {
    private ArrayList<Card> allCards;
    private Player owner;

    Graveyard() {

    }

    public Monster getMonsterFromGraveyard(String name) {
        for (Card card : allCards) {
            if (card instanceof Monster && card.getCardName().equals(name))
                return (Monster) card;
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

//    public Monster getMonsterFromGraveyard(Monster monster) {
//        for (int i = 0; i < allCards.size(); i++) {
//            if (allCards.get(i) instanceof Monster) {
//                Monster monster1 = (Monster) allCards.get(i);
//            }
//
//        }
//        return null;
//    }
}
