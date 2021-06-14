package model.cards;

import bullshit.TrapAction;
import enums.CardType;
import enums.Face;

public class Trap extends Card {
    private TrapAction trapAction;
    private Card EffectedCard;
    private String type;
    private boolean isSetInThisTurn = false;

    public Trap(String name, CardType cardType, String description, Face face,
                int price, String type) {
        super(name, cardType, description, face, price);
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isSetInThisTurn() {
        return isSetInThisTurn;
    }

    public void setSetInThisTurn(boolean setInThisTurn) {
        isSetInThisTurn = setInThisTurn;
    }

    public Card getEffectedCard() {
        return EffectedCard;
    }

    public TrapAction getTrapAction() {
        return trapAction;
    }

    public void setEffectedCard(Card effectedCard) {
        EffectedCard = effectedCard;
    }

    public void setTrapAction(TrapAction trapAction) {
        this.trapAction = trapAction;
    }

    @Override
    public String toString() {
        return "Name: " + cardName + "Trap"
                + "\nType: " + type + "Description: " + cardDescription;
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

}
