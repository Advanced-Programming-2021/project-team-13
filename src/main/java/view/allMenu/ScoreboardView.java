package view.allmenu;

import controll.ScoreboardController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.players.User;

import java.util.Objects;

public class ScoreboardView extends Application {
    private static Stage stage;
    private final Button backButton = new Button("Back");
    private final Image normalButtonImage =
            new Image(Objects.requireNonNull(getClass().getResource("/scoreboardImage/back.png")).toExternalForm());
    private final Image clickedButton =
            new Image(Objects.requireNonNull(getClass().getResource("/scoreboardImage/clicked.png")).toExternalForm());
    private final ImageView buttonImage = new ImageView();
    private final ScoreboardController scoreboardController;
    private final AnchorPane anchorPane = new AnchorPane();
    private final ScoreboardLabel scoreboardLabel = new ScoreboardLabel();
    private final ImageView imageView =
            new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/scoreboardImage/tower.png")).toExternalForm()));
    private int counter = 1;

    public ScoreboardView() {
        scoreboardController = new ScoreboardController(this);
    }

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        anchorPane.setPrefWidth(1280);
        anchorPane.setPrefHeight(720);
        imageView.setFitWidth(1280);
        imageView.setFitHeight(720);
        anchorPane.getChildren().add(imageView);
        scoreboardController.sortAllUsers();
        createButton();
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(anchorPane, 1280, 720));
        primaryStage.show();
    }

    private void createButton() {
        buttonImage.setImage(normalButtonImage);
        buttonImage.setFitWidth(100);
        buttonImage.setFitHeight(40);
        buttonImage.setTranslateX(250);
        buttonImage.setTranslateY(65 * 10);
        buttonImage.setOnMouseClicked(event -> {
            counter = 1;
        });
        buttonImage.setOnMouseEntered(event -> buttonImage.setImage(clickedButton));
        buttonImage.setOnMouseExited(event -> buttonImage.setImage(normalButtonImage));
        anchorPane.getChildren().add(buttonImage);
    }

    public void printScoreBoard(User user, int rank) {
        AnchorPane anchorPane = scoreboardLabel.getLabel(user.getNickname(), new Image(Objects.requireNonNull(getClass().getResource("/scoreboardImage/tas.png")).toExternalForm()), user.getScore(), rank);
        anchorPane.setTranslateY(counter * 50);
        anchorPane.setTranslateX(100);
        this.anchorPane.getChildren().add(anchorPane);
        counter++;
    }

}
