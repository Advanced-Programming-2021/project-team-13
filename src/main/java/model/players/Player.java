package model.players;

import enums.Zone;
import model.Board;
import model.Deck;
import model.Graveyard;
import model.cards.Card;

import java.util.ArrayList;

public class Player {

    protected final User user;
    protected Player rivalPlayer;
    protected int lifePoint;
    protected int maxLifePoint;
    protected int wonRounds;
    protected Board board;
    protected Card selectedCard;
    protected ArrayList<Card> cardsInHand;
    protected boolean isSetOrSummonInThisTurn = false;
    protected boolean canActiveTrap = true;
    protected boolean isAttacking;
    protected boolean isAI;

    public Player(User user) {
        this.wonRounds = 0;
        this.user = user;
        this.lifePoint = 8000;
        this.maxLifePoint = 0;
        isAI = false;
        this.board = new Board(new Deck(user.getActiveDeck()), new Graveyard(this));
        // this.board = new Board(new Deck(user.getActiveDeck()), new Graveyard(this));
        cardsInHand = new ArrayList<>();
    }

    public Player(Deck deck) {
        this.wonRounds = 0;
        this.lifePoint = 8000;
        this.maxLifePoint = 0;
        isAI = true;
        user = null;
        this.board = new Board(deck, new Graveyard(this));
        cardsInHand = new ArrayList<>();
    }

    public void setRivalPlayer(Player rivalPlayer) {
        this.rivalPlayer = rivalPlayer;
    }

    public Player getRivalPlayer() {
        return rivalPlayer;
    }

    public void setCardsInHand(ArrayList<Card> cardsInHand) {
        this.cardsInHand = cardsInHand;
    }

    public void renewPlayer() {
        this.cardsInHand = new ArrayList<>();
        this.lifePoint = 8000;
        this.isAttacking = false;
        this.board = new Board(new Deck(user.getActiveDeck()), new Graveyard(this));
        this.selectedCard = null;
        this.cardsInHand = new ArrayList<>();
        this.isAttacking = false;
        this.isSetOrSummonInThisTurn = false;
    }

    public void setAttacking(boolean attacking) {
        isAttacking = attacking;
    }

    public boolean isAttacking() {
        return isAttacking;
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

    public void setWonRounds(int wonRounds) {
        this.wonRounds = wonRounds;
    }

    public int getWonRounds() {
        return wonRounds;
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

    public Card addCardToHand() {
        Card card = this.getBoard().getDeck().getAllCardsInMainDeck().get(0);
        this.getBoard().getDeck().getAllCardsInMainDeck().remove(0);
        this.getBoard().getDeck().getAllCards().remove(card);
        card.setZone(Zone.IN_HAND);
        this.cardsInHand.add(card);
        return card;
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

    public Card getSelectedCard() {
        return selectedCard;
    }

    public void setSelectedCard(Card selectedCard) {
        this.selectedCard = selectedCard;
    }

    public void addWonRounds(int number) {
        this.wonRounds += number;
    }

}
