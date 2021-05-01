package model;

public class Player {
    private Graveyard playersGraveyard;
    private int lifeInGame;
    private User user;
    private Player rivalPlayer;
    private Cell[][] board;
    private Deck deck;
    //  Player(String username) {
//
    //  }

    public void play() {

    }

    public Graveyard getGraveyard() {
        return playersGraveyard;
    }

    public void increaseHealth(int amount) {
        lifeInGame += amount;
    }

    public void addCardInGame(Card card) {
    }
}
