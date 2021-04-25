package view;

import controll.ScoreboardController;
import enums.Menu;
import model.User;

public class ScoreboardView {
    private final ScoreboardController scoreboardController;
    public ScoreboardView() {
        scoreboardController = new ScoreboardController(this);
    }

    public void printMenuNavigationImpossible(){
        System.out.println("menu navigation is not possible");
    }

    public void printInvalidCommand(){
        System.out.println("invalid command");
    }

    public void printScoreBoard(User user, int rank) {
        System.out.println(rank + "" + user);
    }

    public void run(String command) {
        if (command.matches("scoreboard show"))
            scoreboardController.sortAllUsers(User.getAllUsers());
        else if (command.matches(Regex.EXIT_MENU))
            ViewMaster.setCurrentMenu(Menu.MAIN_MENU);
        else if (command.startsWith("menu enter"))
            printMenuNavigationImpossible();
        else printInvalidCommand();
    }
}
