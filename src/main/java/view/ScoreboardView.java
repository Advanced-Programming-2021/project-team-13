package view;

import model.User;

import java.util.ArrayList;

public class ScoreboardView {
    /*public ScoreboardView() {
        scoreboardController = new ScoreboardController(this);
    }*/

    public void printScoreBoard(User user, int rank) {
        System.out.println(rank + "" + user);
    }

}
