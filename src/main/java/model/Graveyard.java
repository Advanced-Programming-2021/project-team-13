package model;

import controll.GameController;
import enums.Zone;
import model.cards.Card;
import model.cards.Monster;
import model.players.Player;

import java.util.ArrayList;

public class Graveyard {
    private ArrayList<Card> allCards;
    private Player owner;

    public Graveyard(Player player) {
        owner = player;
        allCards = new ArrayList<>();
    }

    public Monster getMonsterFromGraveyard(String name) {
        for (Card card : allCards) {
            if (card instanceof Monster && card.getCardName().equals(name))
                return (Monster) card;
        }
        return null;
    }

    public void addCard(Card card) {
        if (GameController.checkForDeathAction(card))
            return;
        if (allCards.contains(card))
            return;
        card.setZone(Zone.GRAVEYARD);
//        card.setActivated(false);
        if (card instanceof Monster) {
            for (Cell monster : owner.getBoard().getMonsters()) {
                if (monster.getCard() == card) {
                    monster.setCard(null);
                    break;
                }
            }
        } else if (card == owner.getBoard().getFieldSpell().getCard())
            owner.getBoard().getFieldSpell().setCard(null);
        else
            for (Cell cell : owner.getBoard().getSpellOrTrap()) {
                if (cell.getCard() == card) {
                    cell.setCard(null);
                    break;
                }
            }
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
