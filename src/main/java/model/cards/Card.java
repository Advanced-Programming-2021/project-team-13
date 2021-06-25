package model.cards;

import enums.CardType;
import enums.Face;
import enums.Zone;
import model.Cell;
import model.players.Player;

import java.util.ArrayList;
import java.util.Calendar;

public class Card implements Comparable<Card> {
    protected String cardName;
    protected CardType cardType;
    protected String cardDescription;
    protected Face face;
    protected Player cardOwner;
    protected Zone zone;
    protected int price;
    protected boolean activated = false;


    public Card(String name, CardType cardType, String description, Face face, int price) {
        this.cardName = name;
        this.cardType = cardType;
        this.cardDescription = description;
        this.face = face;
        this.price = price;
        this.zone = Zone.DECK_ZONE;
    }

    public Card (Card that){
        this.cardName = that.cardName;
        this.cardType = that.cardType;
        this.cardDescription = that.cardDescription;
        this.face = that.face;
        this.price = that.price;
        this.zone = Zone.DECK_ZONE;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public CardType getCardType() { ///// need the value of enum
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public String getCardDescription() {
        return cardDescription;
    }

    public void setFace(Face face) {
        this.face = face;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public Face getFace() {
        return face;
    }

    public Player getCardOwner() {
        return cardOwner;
    }

    public void setCardOwner(Player cardOwner) {
        this.cardOwner = cardOwner;
    }

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    @Override
    public int compareTo(Card o) {
        if (o.getCardName().compareTo(cardName) < 0) return 1;
        return -1;
    }

    @Override
    public String toString() {
        return this.cardName + " : " + this.cardDescription;
    }
}
