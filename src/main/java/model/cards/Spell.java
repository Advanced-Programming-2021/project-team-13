package model.cards;

import enums.CardType;
import enums.Face;

public class Spell extends Card {
    //private final SpellEffect spellEffect;  // beware that its final!!!!!!
    private Monster equippedMonster;


    public Spell(String name, CardType cardType, String description, Face face, int price) {
        super(name, cardType, description, face, price);
    }

    public Monster getEquippedMonster() {
        return equippedMonster;
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
