package view;

import model.User;

public class ScoreboardView {
    /*public ScoreboardView() {
        scoreboardController = new ScoreboardController(this);
    }*/

    public void printScoreBoard(User user, int rank) {
        System.out.println(rank + "" + user);
    }

}
