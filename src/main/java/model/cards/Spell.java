package model.cards;

import enums.CardType;
import enums.Face;
import enums.SpellEffect;

public class Spell extends Card {
    //private final SpellEffect spellEffect;  // beware that its final!!!!!!
    private boolean isActive = false;
    private Monster equippedMonster;


    public Spell(String name, CardType cardType, String description, Face face, int price) {
        super(name, cardType, description, face, price);
        this.type = type;
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

    public SpellEffect getSpellEffect() {
        return spellEffect;
    }

    public void setSpellEffect(SpellEffect spellEffect) {
        this.spellEffect = spellEffect;
    }

    public void setEquippedMonster(Monster equippedMonster) {
        this.equippedMonster = equippedMonster;
    }

    public void setActive(boolean status) {
        isActive = status;
    }

    public boolean isActive() {
        return isActive;
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
