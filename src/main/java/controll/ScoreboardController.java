package controll;

import model.User;
import view.ScoreboardView;

import java.util.ArrayList;
import java.util.Collections;

public class ScoreboardController {
    private final ScoreboardView scoreboardView = new ScoreboardView();

    public void sortAllUsers(ArrayList<User> allUsers) {
        Collections.sort(allUsers);
        int rank = 1;
        for (int i = 0; i < allUsers.size(); i++) {
            scoreboardView.printScoreBoard(allUsers.get(i), rank);
            if (i < allUsers.size() - 1
                    && allUsers.get(i).getScore() == allUsers.get(i + 1).getScore()) continue;
            rank++;
        }
    }

}
