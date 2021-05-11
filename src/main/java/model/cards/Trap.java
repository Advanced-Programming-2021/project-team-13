package model.cards;

import enums.CardType;
import enums.Face;
import enums.TrapEffect;

public class Trap extends Card {
    //    private TrapEffect trapEffect;
    String type;

    public Trap(String name, CardType cardType, String description, Face face,
                int price, String type) {
        super(name, cardType, description, face, price);
        this.type = type;
    }
 /*   Trap(String name, CardType cardType, String description, Face face,
         int price, int cardNum, TrapEffect trapEffect) {
        super(name, cardType, description, face, price, cardNum);
        this.trapEffect = trapEffect;
    }*/
/*
    public boolean isTrapDestroyer() {///////////////////////////////////////////this needs to be completed
        return false;
    }*/

    @Override
    public String toString() {
        return "Name: " + cardName + "Trap"
                + "\nType: " + type + "Description: " + cardDescription;
    }
}
