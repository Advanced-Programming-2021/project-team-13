package model.cards;

import enums.CardType;
import enums.Face;
import enums.Zone;
import model.Cell;
import model.players.Player;

import java.util.ArrayList;

public class Card implements Comparable<Card> {
    /*
        private static final ArrayList<Card> allCards;
        static {
            allCards = new ArrayList<>();
        }
    protected Cell currentPosition;
    protected Effects effects;
    protected String cardIcon;
    */
    protected String cardName;
    protected int cardNumber;
    protected CardType cardType;
    protected String cardDescription;
    protected Face face;
    protected Player cardOwner;
    protected Zone zone;
    //    protected ArrayList<Card> allCardsOfThisType;
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

   /* public static void addNewCard(Card card){
        allCards.add(card);
    }

    public static Card findCardByName (String cardName){
        for (Card card : allCards){
            if (card.cardName.equals(cardName)){
                return card;
            }
        }
        return null;
    }

    public static ArrayList<Card> getAllCards() {
        return allCards;
    }*/

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    public CardType getCardType() { ///// need the value of enum
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType; /////////////////////needs  the value of enum
    }

    public String getCardDescription() {
        return cardDescription;
    }

    public void setCardDescription(String cardDescription) {
        this.cardDescription = cardDescription;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setCurrentPosition(ArrayList<Cell> currentPosition) {

    }
//
//    public ArrayList<Cell> getCurrentPosition() {
//
//    }

    public void setFace(Face face) {
        this.face = face;
    }

//    public String getFace() {
//
//    }

    public void addToAllCardsOfThisType(Card card) {

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
