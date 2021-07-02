package view.allmenu;

import controll.ScoreboardController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import view.Menu;
import model.players.User;
import view.Regex;
import view.ViewMaster;

import java.util.Objects;

public class ScoreboardView extends Application {
    private final ScoreboardController scoreboardController;

    public ScoreboardView() {
        scoreboardController = new ScoreboardController(this);
        createLabel();
    }

    private void createLabel() {
        AnchorPane label = new AnchorPane();

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefWidth(1280);
        anchorPane.setPrefHeight(720);
        primaryStage.setScene(new Scene(anchorPane,1280, 720));
        primaryStage.show();
    }


    public void printInvalidCommand() {
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
        else printInvalidCommand();
    }
}
