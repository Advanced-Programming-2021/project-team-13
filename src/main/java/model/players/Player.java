package model.players;

import model.Board;
import model.Deck;
import model.Graveyard;
import model.cards.Card;
import model.cards.Monster;

import java.util.ArrayList;

public class Player {
    private Graveyard playersGraveyard;
    private int lifeInGame;
    private User user;
    private Player rivalPlayer;
    private Card currentCard;
    private Deck deck;
    private Board board;
    private boolean canActivateTrap = true;
    private boolean canAttack = true;
    private Card selectedCard = null;
    private ArrayList<Card> selectedCardsForTribute = new ArrayList<>();

    Player(User user) {
        this.user = user;
    }

    public void play() {

    }

    public Graveyard getGraveyard() {
        return playersGraveyard;
    }

    public int getLifeInGame() {
        return lifeInGame;
    }

    public User getUser() {
        return user;
    }

    public Player getRivalPlayer() {
        return rivalPlayer;
    }

    public Deck getDeck() {
        return deck;
    }

    public void increaseHealth(int amount) {
        lifeInGame += amount;
    }

    public void decreaseHealth(int amount) {       //thers an error somewhere that I didnt put a minus , dont know where but I will find out !!!!!!!!
        lifeInGame -= amount;
    }

    public void addCardInGame(Card card) {
    }


    public void setRivalPlayer(Player rivalPlayer) {
        this.rivalPlayer = rivalPlayer;
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

    public Card getCurrentCard() {
        return currentCard;
    }

    public boolean isCanActivateTrap() {
        return canActivateTrap;
    }

    public void setCanActivateTrap(boolean canActivateTrap) {
        this.canActivateTrap = canActivateTrap;
    }

    public Card getSelectedCard() {
        return selectedCard;
    }

    public void setSelectedCard(Card selectedCard) {
        this.selectedCard = selectedCard;
    }

    public ArrayList<Card> getSelectedCardsForTribute() {
        return selectedCardsForTribute;
    }

    public void setSelectedCardsForTribute(ArrayList<Card> selectedCardsForTribute) {
        this.selectedCardsForTribute = selectedCardsForTribute;
    }

    public boolean willingToSacrifice() { // /////////////////////////////////////////////////needs to be completed , player needs to give health!!!!!!
        ///prototype
        return false; // to be completed
    }
}
