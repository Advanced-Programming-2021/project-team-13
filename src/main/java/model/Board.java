package model;

import model.cards.Card;
import model.cards.Monster;
import model.cards.Spell;

public class Board {


    private Cell[] spellOrTrap;
    private Cell[] monster;
    private Cell fieldSpell;
    private Deck deck;
    private Graveyard graveyard;


    public Board(Deck deck, Graveyard graveyard) {
        this.deck = deck;
        this.graveyard = graveyard;
        spellOrTrap = new Cell[5];
        monster = new Cell[5];
        for (int i = 0; i < 5; i++) {
            spellOrTrap[i] = new Cell(null);
            monster[i] = new Cell(null);
        }
        fieldSpell = new Cell(null);
    }

    public void setSpellOrTrap(Cell[] spellOrTrap) {
        this.spellOrTrap = spellOrTrap;
    }

    public void setMonster(Cell[] monster) {
        this.monster = monster;
    }

    public void setFieldSpell(Cell fieldSpell) {
        this.fieldSpell = fieldSpell;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public void setGraveyard(Graveyard graveyard) {
        this.graveyard = graveyard;
    }

    public Cell[] getSpellOrTrap() {
        return spellOrTrap;
    }

    public Cell[] getMonsters() {
        return monster;
    }

    public Cell getFieldSpell() {
        return fieldSpell;
    }

    public Deck getDeck() {
        return deck;
    }

    public Graveyard getGraveyard() {
        return graveyard;
    }

    public Card getMonsterByAddress(int cardAddress) {     // get The monster card ,, the numbers in array is based on DOC
        return getCard(cardAddress, monster);
    }

    public Card getSpellOrTrapByAddress(int cardAddress) {     // not the sure on the array nums!!!!!
        return getCard(cardAddress, spellOrTrap);
    }

    private Card getCard(int cardAddress, Cell[] cardGroup) {
        if (cardAddress == 1) return cardGroup[2].getCard();
        if (cardAddress == 2) return cardGroup[3].getCard();
        if (cardAddress == 3) return cardGroup[1].getCard();
        if (cardAddress == 4) return cardGroup[4].getCard();
        if (cardAddress == 5) return cardGroup[0].getCard();
        return null;
    }

    public boolean isMonsterOnBoard(Card selectedCard) {
        for (int i = 0; i < 6; i++) {
            if (monster[i].getCard() == selectedCard)     // We can do this because of pointer!!!!
                return true;
        }
        return false;
    }

    public void removeMonsterFromBoard(Monster monster) {  // could we remove(this is monster and original has card)
        for (int i = 0; i < 5; i++) {
            if (this.monster[i].getCard() == monster)// is this ok???????????????????//
                this.monster[i] = null;
        }
    }

    public void removeSpellOrTrapFromBoard(Spell spell) {
        for (int i = 0; i < 4; i++) {
            if (spellOrTrap[i].getCard() == spell)
                spellOrTrap[i] = null;
        }
    }

    public boolean isThereEmptyPlaceMonsterZone() {
        for (Cell cell : monster) {
            if (cell == null)
                return true;
        }
        return false;
    }

    public int getNumberOfMonsterInBoard() {
        int counter = 0;
        for (Cell cell : monster) {
            if (cell == null)
                counter++;
        }
        return counter;
    }
}
