package model;

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

    public Cell[][] getBoard() {
        return board;
    }

    public Deck getDeck() {
        return deck;
    }

    public void increaseHealth(int amount) {
        lifeInGame += amount;
    }

    public void addCardInGame(Card card) {
    }

    public Player getRivalPlayer() {
        return rivalPlayer;
    }

    public void setRivalPlayer(Player rivalPlayer) {
        this.rivalPlayer = rivalPlayer;
    }

    public Cell[][] getBoard() {
        return board;
    }

    public void setBoard(Cell[][] board) {
        this.board = board;
    }

    public ArrayList<Monster> getMonsterOnBoard() {
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
}
