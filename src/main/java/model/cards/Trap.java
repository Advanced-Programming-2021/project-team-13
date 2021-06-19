package model.cards;

import controll.gameController.TrapAction;
import enums.CardType;
import enums.Face;

public class Trap extends Card {
    private TrapAction trapAction;
    private Card EffectedCard;
    private String type;
    private int setTurn;

    public Trap(String name, CardType cardType, String description, Face face,
                int price, String type) {
        super(name, cardType, description, face, price);
        this.type = type;
        setTurn = -1;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public void setSetTurn(int setTurn) {
        this.setTurn = setTurn;
    }

    public int getSetTurn() {
        return setTurn;
    }

    @Override
    public String toString() {
        return "Name: " + cardName + "Trap"
                + "\nType: " + type + "Description: " + cardDescription;
    }

}
