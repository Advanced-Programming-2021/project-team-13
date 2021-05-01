package model;

import enums.CardType;
import enums.Face;
import enums.SpellEffect;

public class Spell extends Card {
    private final SpellEffect spellEffect;  // beware that its final!!!!!!

    Spell(String name, CardType cardType, SpellEffect spellEffect,
          String description, Face face, int price, int cardNum) {
        super(name, cardType, description, face, price, cardNum);
        this.spellEffect = spellEffect;
    }

    public SpellEffect getSpellEffect() {
        return spellEffect;
    }
}
