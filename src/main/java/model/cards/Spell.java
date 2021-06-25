package model.cards;

import enums.CardType;
import enums.Face;

public class Spell extends Card {
    //private final SpellEffect spellEffect;  // beware that its final!!!!!!
    private Monster equippedMonster;
    private String type;
    private boolean isSetINThisTurn = false;

    public Spell(String name, CardType cardType, String description, Face face, int price, String type) {
        super(name, cardType, description, face, price);
        this.type = type;
    }

    public Spell(Spell that){
        super(that);
        this.type = that.type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isSetINThisTurn() {
        return isSetINThisTurn;
    }

    public void setSetINThisTurn(boolean setINThisTurn) {
        isSetINThisTurn = setINThisTurn;
    }

    @Override
    public String toString() {
        return "Name: " + cardName + "Spell"
                + "\nType: " + type + "Description: " + cardDescription;
    }

    public Monster getEquippedMonster() {
        return equippedMonster;
    }

    public String getSpellEffect() {
        return type;
    }

    public void setSpellEffect(String type) {
        this.type = type;
    }

    public void setEquippedMonster(Monster equippedMonster) {
        this.equippedMonster = equippedMonster;
    }

/*
    public Spell(String name, CardType cardType, SpellEffect spellEffect,
          String description, Face face, int price, int cardNum) {
        super(name, cardType, description, face, price, cardNum);
        this.spellEffect = spellEffect;
    }
*/

    /*public SpellEffect getSpellEffect() {
        return spellEffect;
    }*/
}
