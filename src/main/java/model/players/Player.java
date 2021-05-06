package model.players;

import model.Board;
import model.Deck;
import model.Graveyard;
import model.cards.Card;
import model.cards.Monster;

import java.util.ArrayList;

public class Player {

    private final User user;
    private Deck deck;
    private int lifePoint;
    private Board board;
    private int rounds;
    private Card selectedCard;
    private final ArrayList<Card> cardsInHand;
    private boolean isSetOrSummonInThisTurn = false;


    public Player(User user , int rounds) {
        this.user = user;
        this.deck = user.getActiveDeck();
        this.lifePoint = 8000;
        this.board = new Board(deck,new Graveyard(this));
        this.rounds = rounds;
        cardsInHand = new ArrayList<>();
    }


    public void play() {

    }

    public boolean isSetOrSummonInThisTurn() {
        return isSetOrSummonInThisTurn;
    }

    public void setSetOrSummonInThisTurn(boolean setOrSummonInThisTurn) {
        isSetOrSummonInThisTurn = setOrSummonInThisTurn;
    }

    public ArrayList<Card> getCardsInHand() {
        return cardsInHand;
    }

    public void setLifePoint(int lifePoint) {
        this.lifePoint = lifePoint;
    }

//    public void setCurrentCard(Card currentCard) {
//        this.currentCard = currentCard;
//    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public int getLifePoint() {
        return lifePoint;
    }

    public Deck getDeck() {
        return deck;
    }

    public void increaseHealth(int amount) {
        lifePoint += amount;
    }

    public void decreaseHealth(int amount) {       //thers an error somewhere that I didnt put a minus , dont know where but I will find out !!!!!!!!
        lifePoint -= amount;
    }

    public void addCardInGame(Card card) {
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Board getBoard() {
        return board;
    }

    public ArrayList<Monster> getMonsterOnBoard() {       //I dont think this works!!!!!
        ArrayList<Monster> monsters = new ArrayList<>();
        return monsters;
    }

//    public Card getCurrentCard() {
//        return currentCard;
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


/*    private ArrayList<Card> selectedCardsForTribute = new ArrayList<>();
    private boolean canActivateTrap = true;
    private boolean canAttack = true;*/
/*
    public ArrayList<Card> getSelectedCardsForTribute() {
        return selectedCardsForTribute;
    }

    public void setSelectedCardsForTribute(ArrayList<Card> selectedCardsForTribute) {
        this.selectedCardsForTribute = selectedCardsForTribute;
    }
*/