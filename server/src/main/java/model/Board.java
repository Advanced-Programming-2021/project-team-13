package model;

import enums.Face;
import model.cards.Card;
import model.cards.Monster;
import model.cards.Spell;
import model.cards.Trap;
import model.players.Player;

public class Board {


    private final Cell[] spellOrTrap;
    private final Cell[] monsterCells;
    private final Cell fieldSpell;
    private PlayerDeck playerDeck;
    private Graveyard graveyard;


    public Board(PlayerDeck playerDeck, Graveyard graveyard, Player player) {
        this.playerDeck = playerDeck;
        this.graveyard = graveyard;
        spellOrTrap = new Cell[5];
        monsterCells = new Cell[5];
        for (int i = 0; i < 5; i++) {
            spellOrTrap[i] = new Cell(null);
            monsterCells[i] = new Cell(null);
        }
        fieldSpell = new Cell(null);
    }

    public void setFieldSpell(Card fieldSpell) {
        this.fieldSpell.setCard(fieldSpell);
    }

    public void setDeck(PlayerDeck playerDeck) {
        this.playerDeck = playerDeck;
    }

    public void setGraveyard(Graveyard graveyard) {
        this.graveyard = graveyard;
    }

    public Cell[] getSpellOrTrap() {
        return spellOrTrap;
    }

    public Cell[] getMonsters() {
        return monsterCells;
    }

    public Cell getFieldSpell() {
        return fieldSpell;
    }

    public PlayerDeck getDeck() {
        return playerDeck;
    }

    public Graveyard getGraveyard() {
        return graveyard;
    }

    public Card getMonsterByAddress(int cardAddress) {     // get The monster card ,, the numbers in array is based on DOC
        return getCard(cardAddress, monsterCells);
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
        if (selectedCard == null)
            return false;
        for (int i = 0; i < 5; i++) {
            if (monsterCells[i].getCard() == selectedCard)
                return true;
        }
        return false;
    }

    public void removeMonsterFromBoard(Monster monster) {
        for (int i = 0; i < 5; i++) {
            if (this.monsterCells[i].getCard() == monster)
                this.monsterCells[i] = null;
        }
    }

    public boolean isThereEmptyPlaceMonsterZone() {
        for (Cell cell : monsterCells) {
            if (cell.getCard() == null)
                return true;
        }
        return false;
    }

    public int getNumberOfMonsterInBoard() {
        int counter = 0;
        for (Cell cell : monsterCells) {
            if (cell.getCard() != null)
                counter++;
        }
        return counter;
    }

    public void putMonsterInBoard(Monster monster) {
        for (Cell monsterCell : monsterCells) {
            if (monsterCell.getCard() == null) {
                monsterCell.setCard(monster);
                /*monsterCell.setPicture(monster.getImage(), monster.getFace(), monster.getAttackOrDefense());*/
                return;
            }
        }
    }


    public int getNumberOFSpellAndTrapInBoard() {
        int counter = 0;
        for (Cell cell : spellOrTrap) {
            if (cell.getCard() != null)
                counter++;
        }
        return counter;
    }

    public void putSpellAndTrapInBoard(Card selectedCard) {
   /*     if (selectedCard instanceof Trap)
            putTrap((Trap) selectedCard);
        else
            putSpell((Spell) selectedCard);
*/
    }

    private void putSpell(Spell selectedCard) {
        if (selectedCard.getType().equalsIgnoreCase("field")) {
            fieldSpell.setCard(selectedCard);
/*            fieldSpell.setPicture(selectedCard.getImage(), Face.UP, null);*/
        } else {
            for (Cell cell : spellOrTrap) {
                if (cell.getCard() == null) {
     /*               cell.setCard(selectedCard);
                    cell.setPicture(selectedCard.getImage(), selectedCard.getFace(), null);*/
                    return;
                }
            }
        }
    }

    private void putTrap(Trap selectedCard) {
        for (Cell cell : spellOrTrap) {
            if (cell.getCard() == null) {
           /*     cell.setPicture(selectedCard.getImage(), selectedCard.getFace(), null);
                cell.setCard(selectedCard);*/
                return;
            }
        }
    }

    public int getMonsterCellNumber(Monster monster) {
        for (int i = 0; i < monsterCells.length; i++) {
            if (monster == monsterCells[i].getCard())
                return i;
        }
        return -1;
    }
}