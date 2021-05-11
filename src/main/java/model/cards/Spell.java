package model.cards;

import enums.CardType;
import enums.Face;
import enums.SpellEffect;

public class Spell extends Card {
    //private final SpellEffect spellEffect;  // beware that its final!!!!!!
    private Monster equippedMonster;
    String type;

    public Spell(String name, CardType cardType, String description, Face face, int price, String type) {
        super(name, cardType, description, face, price);
        this.type = type;
    }

    @Override
    public String toString() {
        return "Name: " + cardName + "Spell"
                + "\nType: " + type + "Description: " + cardDescription;
    }

    public Monster getEquippedMonster() {
        return equippedMonster;
    }

    public void setEquippedMonster(Monster equippedMonster) {
        this.equippedMonster = equippedMonster;
    }
/*
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
