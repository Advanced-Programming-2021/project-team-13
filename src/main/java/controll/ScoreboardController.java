package controll;

import model.players.User;
import view.allmenu.ScoreboardView;

import java.util.ArrayList;
import java.util.Collections;

public class ScoreboardController {
    private final ScoreboardView scoreboardView;

    public ScoreboardController(ScoreboardView scoreboardView) {
        this.scoreboardView = scoreboardView;
    }

    public void sortAllUsers() {
        ArrayList<User> allUsers = User.getAllUsers();
        Collections.sort(allUsers);
        int rank = 1;
        int counter = 1;
        for (int i = 0; i < (Math.min(allUsers.size(), 20)); i++) {
            scoreboardView.printScoreBoard(allUsers.get(i), rank);
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
