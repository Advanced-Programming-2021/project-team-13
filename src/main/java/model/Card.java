package model;

import bullshit.Interfaces.Effects;
import enums.CardType;
import enums.Face;
import enums.Zone;

import java.util.ArrayList;

public class Card {
    /*
        private static final ArrayList<Card> allCards;
        static {
            allCards = new ArrayList<>();
        }

    */
    protected String cardName;
    protected int cardNumber;
    protected String cardIcon;
    protected CardType cardType;
    protected String cardDescription;
    protected Cell currentPosition;
    protected Face face;
    protected Player cardOwner;
    protected Effects effects;
    protected Zone zone;
    //    protected ArrayList<Card> allCardsOfThisType;
    protected int price;


    public Card(String name, CardType cardType, String description, Face face, int price, int cardNum) {
        this.cardName = name;
        this.cardType = cardType;
        this.cardDescription = description;
        this.face = face;
        this.price = price;
        this.cardNumber = cardNum;
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

    public Player getPlayer() {
        return cardOwner;
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
}
