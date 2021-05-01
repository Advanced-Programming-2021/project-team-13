package model;

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

    public Cell[] getMonster() {
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
}
