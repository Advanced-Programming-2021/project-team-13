package model;

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
    protected String cardType;
    protected String cardDescription;
    protected Cell currentPosition;
    protected String faceDownOrUp;
//    protected ArrayList<Card> allCardsOfThisType;
    protected int price;

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

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
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

    public void setFace(String face) {

    }

//    public String getFace() {
//
//    }

    public void addToAllCardsOfThisType(Card card) {

    }
}
