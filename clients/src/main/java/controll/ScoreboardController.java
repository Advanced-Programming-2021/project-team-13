package controll;

import view.ViewMaster;
import view.allmenu.ScoreboardView;

public class ScoreboardController {
    private final ScoreboardView scoreboardView;

    public ScoreboardController(ScoreboardView scoreboardView) {
        this.scoreboardView = scoreboardView;
    }

    public void sortAllUsers() {
        try {
            ViewMaster.dataOutputStream.writeUTF("scoreboard");
            ViewMaster.dataOutputStream.flush();
            String[] users = ViewMaster.dataInputStream.readUTF().split("\\|");
            for (String user : users) {
                String[] userRank = user.split(" ");
                if (userRank.length == 3)
                    scoreboardView.printScoreBoard(userRank[0], userRank[1], userRank[2]);
            }
        } catch (Exception ignored) {
        }
       /* ArrayList<User> allUsers = User.getAllUsers();
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
            }*/
    }
}


