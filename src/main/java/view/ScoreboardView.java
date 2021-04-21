package view;

import controll.ScoreboardController;
import model.User;

import java.util.ArrayList;

public class ScoreboardView {
    private ScoreboardController scoreboardController;

    public ScoreboardView() {
        scoreboardController = new ScoreboardController(this);
    }

    public void printScoreBoard(ArrayList<User> users) {
        int counter = 1;
        for (int i = 0; i < users.size(); i++) {
            System.out.println(counter + "- " + users.get(i).getNickname() + ": " + users.get(i).getScore());
            if (i < users.size() - 1
                    && users.get(i).getScore() == users.get(i + 1).getScore()) continue;
            counter++;
        }

    }

}
