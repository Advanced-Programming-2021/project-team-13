package controll;

import model.players.User;

import java.util.ArrayList;
import java.util.Collections;

public class ScoreboardController {


    public ScoreboardController() {

    }

    public String sortAllUsers() {
        ArrayList<User> allUsers = User.getAllUsers();
        StringBuilder stringBuilder = new StringBuilder();
        Collections.sort(allUsers);
        int rank = 1;
        int counter = 1;
        for (int i = 0; i < (Math.min(allUsers.size(), 20)); i++) {
            stringBuilder.append(allUsers.get(i).getNickname()).append(" ")
                    .append(rank).append(" ").append(allUsers.get(i).getScore()).append("|");
            //scoreboardView.printScoreBoard(allUsers.get(i), rank);
            if (i < allUsers.size() - 1
                    && allUsers.get(i).getScore() == allUsers.get(i + 1).getScore()) {
                counter++;
            } else {
                rank += counter;
                counter = 1;
            }
        }
        return stringBuilder.toString();
    }

}
