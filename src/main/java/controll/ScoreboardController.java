package controll;

import model.players.User;
import view.allMenu.ScoreboardView;

import java.util.ArrayList;
import java.util.Collections;

public class ScoreboardController {
    private final ScoreboardView scoreboardView;

    public ScoreboardController(ScoreboardView scoreboardView) {
        this.scoreboardView = scoreboardView;
    }

    public void sortAllUsers(ArrayList<User> allUsers) {
        Collections.sort(allUsers);
        int rank = 1;
        int counter = 1;
        for (int i = 0; i < allUsers.size(); i++) {
            //scoreboardView.printScoreBoard(allUsers.get(i), rank);
            if (i < allUsers.size() - 1
                    && allUsers.get(i).getScore() == allUsers.get(i + 1).getScore()) {
                counter++;
            } else {
                rank += counter;
                counter = 1;
            }
        }
    }

}
