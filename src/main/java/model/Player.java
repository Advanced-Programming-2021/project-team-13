package model;

public class Player {
    private Graveyard playersGraveyard;
    private int lifeInGame;

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
}
