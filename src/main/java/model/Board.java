package model;

import model.cards.Card;
import model.cards.Monster;
import model.cards.Spell;
import model.cards.Trap;

public class Board {


    private final Cell[] spellOrTrap;
    private final Cell[] monsterCells;
    private final Cell fieldSpell;
    private Deck deck;
    private Graveyard graveyard;


    public Board(Deck deck, Graveyard graveyard) {
        this.deck = deck;
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

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public void setGraveyard(Graveyard graveyard) {
        this.graveyard = graveyard;
    }

    public Cell[] getSpellOrTrap() {
        return spellOrTrap;
    }

    public Cell[] getMonsters() { // y cant I do this ???
//        ArrayList<Cell> cells = new ArrayList<>();
//        for (Cell monsterCell : monsterCells) {
//            if (monsterCell.getCard() != null)
//                cells.add(monsterCell);
//        }
//        return (Cell[]) cells.toArray();
        return monsterCells;
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
            if (monsterCells[i].getCard() == selectedCard)     // We can do this because of pointer!!!!
                return true;
        }
        return false;
    }

    public void removeMonsterFromBoard(Monster monster) {  // could we remove(this is monster and original has card)
        for (int i = 0; i < 5; i++) {
            if (this.monsterCells[i].getCard() == monster)// is this ok???????????????????//
                this.monsterCells[i] = null;
        }
    }

    public void removeSpellOrTrapFromBoard(Spell spell) {
        for (int i = 0; i < 5; i++) {
            if (spellOrTrap[i].getCard() == spell)
                spellOrTrap[i] = null;
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
        if (selectedCard instanceof Trap)
            putTrap((Trap) selectedCard);
        else
            putSpell((Spell) selectedCard);

    }

    private void putSpell(Spell selectedCard) {
        if (selectedCard.getType().equalsIgnoreCase("field")){
            fieldSpell.setCard(selectedCard);
        } else {
            for (Cell cell : spellOrTrap) {
                if (cell.getCard() == null) {
                    cell.setCard(selectedCard);
                    return;
                }
            }
        }
    }

    private void putTrap(Trap selectedCard) {
        for (Cell cell : spellOrTrap) {
            if (cell.getCard() == null) {
                cell.setCard(selectedCard);
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
