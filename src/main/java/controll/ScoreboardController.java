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
        User user = new User("shahin", "1", "shahin");
        user.addScore(5000);
        user = new User("Hooman", "1", "WhoMan");
        user.addScore(6000);
        user = new User("amir", "1", "Amrossein");
        user.addScore(12);
        user = new User("hovakhshatara", "1", "hovakhshatara");
        user.addScore(5998);
        user = new User("nima", "1", "NIMA");
        user.addScore(5999);
        user = new User("ebrahim_1379", "1", "ebrahim_1379");
        user.addScore(3000);
        user = new User("the-ultimate-mahD", "1", "the-ultimate-mahD");
        user.addScore(3000);
        user = new User("rostam-dastan", "1", "rostam-dastan");
        user.addScore(1000);
        user = new User("rostam-dastan", "1", "rzostam-dastan");
        user.addScore(1000);
        user = new User("rostam-dastan", "1", "rzzstam-dastan");
        user.addScore(1000);
        user = new User("rostam-dastan", "1", "rzzztam-dastan");
        user.addScore(1000);
        user = new User("rostam-dastan", "1", "ok");
        user.addScore(500);
        ArrayList<User> allUsers = User.getAllUsers();
        Collections.sort(allUsers);
        int rank = 1;
        int counter = 1;
        for (int i = 0; i < allUsers.size(); i++) {
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
