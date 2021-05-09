package model.players;

import model.Board;
import model.Deck;
import model.Graveyard;
import model.cards.Card;
import model.cards.Monster;

import java.util.ArrayList;

public class Player {

    private final User user;
    private int lifePoint;
    private int maxLifePoint;
    private final int wonRounds;
    private Board board;
    private Card selectedCard;
    private ArrayList<Card> cardsInHand;
    private boolean isSetOrSummonInThisTurn = false;
    private boolean canActiveTrap = true;

    public Player(User user) {
        this.wonRounds = 0;
        this.user = user;
        this.lifePoint = 8000;
        this.maxLifePoint = 0;
        try {
            this.board = new Board(user.getActiveDeck().clone(), new Graveyard(this));
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        cardsInHand = new ArrayList<>();
    }


    public void play() {

    }

    public boolean isCanActiveTrap() {
        return canActiveTrap;
    }

    public void setCanActiveTrap(boolean canActiveTrap) {
        this.canActiveTrap = canActiveTrap;
    }

    public boolean isSetOrSummonInThisTurn() {
        return isSetOrSummonInThisTurn;
    }

    public void setSetOrSummonInThisTurn(boolean setOrSummonInThisTurn) {
        this.isSetOrSummonInThisTurn = setOrSummonInThisTurn;
    }

    public ArrayList<Card> getCardsInHand() {
        return cardsInHand;
    }

    public void setLifePoint(int lifePoint) {
        this.lifePoint = lifePoint;
    }

    public void setMaxLifePoint(int maxLifePoint) {
        this.maxLifePoint = maxLifePoint;
    }

    public int getMaxLifePoint() {
        return maxLifePoint;
    }

    //    public void setCurrentCard(Card currentCard) {
//        this.currentCard = currentCard;
//    }

    public int getLifePoint() {
        return lifePoint;
    }

    public void increaseHealth(int amount) {
        lifePoint += amount;
    }

    public void decreaseHealth(int amount) {       //thers an error somewhere that I didnt put a minus , dont know where but I will find out !!!!!!!!
        lifePoint -= amount;
    }

    public void addCardInGame(Card card) {
    }

    public void addCardToHand() {
        Card card = this.getBoard().getDeck().getAllCardsInMainDeck().get(0);
        this.getBoard().getDeck().removeCard(card ,false);
        this.cardsInHand.add(card);
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Board getBoard() {
        return board;
    }

    public User getUser() {
        return user;
    }

//    public ArrayList<Monster> getMonsterOnBoard() {       //I dont think this works!!!!!
//        ArrayList<Monster> monsters = new ArrayList<>();
//        return monsters;
//    }


    public Card getSelectedCard() {
        return selectedCard;
    }

    public void setSelectedCard(Card selectedCard) {
        this.selectedCard = selectedCard;
    }

    public boolean willingToSacrifice() { // /////////////////////////////////////////////////needs to be completed , player needs to give health!!!!!!
        ///prototype
        return false; // to be completed
    }

}
