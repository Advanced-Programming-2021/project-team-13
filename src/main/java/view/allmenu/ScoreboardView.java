package view.allmenu;

import controll.ScoreboardController;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import model.players.User;

import java.io.IOException;
import java.util.Objects;

public class ScoreboardView {
    private static Stage stage;
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
    private final ScrollPane scrollPane = new ScrollPane();
    private final TilePane tilePane = new TilePane();

    public ScoreboardView() {
        scoreboardController = new ScoreboardController(this);
    }

    public void start(Stage primaryStage) {
        stage = primaryStage;
        anchorPane.setPrefWidth(1280);
        anchorPane.setPrefHeight(720);
        imageView.setFitWidth(1280);
        imageView.setFitHeight(720);
        anchorPane.getChildren().add(imageView);
        anchorPane.getChildren().add(scrollPane);
        tilePane.setVgap(15);
        tilePane.setPadding(new Insets(20, 0, 20, 50));
        tilePane.setPrefColumns(1);
        tilePane.setPrefTileHeight(35);
        scoreboardController.sortAllUsers();
        createButton();
        primaryStage.setResizable(false);
        Scene scene = new Scene(anchorPane, 1280, 720);
        scene.setUserAgentStylesheet(getClass().getResource("/fxml/shopCss.css").toExternalForm());
        scrollPane.setContent(tilePane);
        scrollPane.setPrefHeight(720);
        scrollPane.setPrefWidth(600);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void createButton() {
        buttonImage.setImage(normalButtonImage);
        buttonImage.setFitWidth(100);
        buttonImage.setFitHeight(40);
        buttonImage.setOnMouseClicked(event -> {
            try {
                goToMainMenu();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        buttonImage.setOnMouseEntered(event -> buttonImage.setImage(clickedButton));
        buttonImage.setOnMouseExited(event -> buttonImage.setImage(normalButtonImage));
        tilePane.getChildren().add(buttonImage);
    }

    private void goToMainMenu() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainMenu.fxml"));
        ((Stage) buttonImage.getScene().getWindow()).setScene(new Scene(root));
    }

    public void printScoreBoard(User user, int rank) {
        AnchorPane anchorPane = scoreboardLabel.getLabel(user.getNickname(), new Image(Objects.requireNonNull(getClass().getResource("/scoreboardImage/tas.png")).toExternalForm()), user.getScore(), rank);
        tilePane.getChildren().add(anchorPane);
    }

}
