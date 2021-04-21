package controll;

import model.User;
import view.ScoreboardView;

import java.util.ArrayList;
import java.util.Collections;

public class ScoreboardController {
    private final ScoreboardView scoreboardView = new ScoreboardView();

    public void sortAllUsers(ArrayList<User> allUsers) {
        Collections.sort(allUsers);
        scoreboardView.printScoreBoard(allUsers);
    }

}
