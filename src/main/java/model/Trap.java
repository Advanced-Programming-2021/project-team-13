package model;

import enums.CardType;
import enums.Face;
import enums.TrapEffect;

public class Trap extends Card {
    private TrapEffect trapEffect;

    Trap(String name, CardType cardType, String description, Face face,
         int price, int cardNum, TrapEffect trapEffect) {
        super(name, cardType, description, face, price, cardNum);
        this.trapEffect = trapEffect;
    }

    public boolean isTrapDestroyer() {///////////////////////////////////////////this needs to be completed
        return false;
    }
}
