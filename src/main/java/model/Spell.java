package model;

import enums.CardType;
import enums.Face;
import enums.SpellEffect;

public class Spell extends Card{
    private SpellEffect spellEffect;

    Spell(String name, CardType cardType, SpellEffect spellEffect,
          String description, Face face, int price, int cardNum) {
        super(name, cardType, description, face, price, cardNum);
        this.spellEffect = spellEffect;
    }
}
