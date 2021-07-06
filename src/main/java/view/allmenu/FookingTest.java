package view.allmenu;

import javafx.animation.FillTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.security.Guard;

public class FookingTest extends Application {
    public GridPane gridPane;
    public BorderPane motherPane;
    public StackPane centerPane;
    public StackPane stackPane;
    public StackPane rivalGraveyard;
    public StackPane ourField;
    public StackPane rivalField;
    public StackPane ourGraveyard;

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(LoginView.class.getResource("/fxml/Game.fxml"));
        primaryStage.setResizable(false);
        primaryStage.setTitle("you-gey-oh?:D");
        primaryStage.setScene(new Scene( loader.load()));
        primaryStage.show();
    }
    public void initialize() {
        centerPane.setAlignment(Pos.CENTER);
        Image background = new Image("/shopImage/Monsters/AxeRaider.jpg");
        for (int i = 0; i <5 ; i++) {
            for (int j = 0; j < 4; j++) {
                StackPane stackPane = new StackPane();
                stackPane.setPrefWidth(140);
                stackPane.setPrefHeight(190);
                ImageView cardImages = new ImageView(background);
                setEffectsCardImages(cardImages);
                cardImages.setFitWidth(140);
                cardImages.setFitHeight(190);
                stackPane.getChildren().add(cardImages);
                gridPane.add(stackPane,i,j);
            }
        }
        gridPane.setTranslateX(20);
        gridPane.setTranslateY(50);
        gridPane.setHgap(10);
        gridPane.setVgap(30);
        centerPane.setStyle("-fx-background-image:url('/gamePics/a.jpg'); -fx-background-size: cover,auto;");
    }

    private void setEffectsCardImages(ImageView view) {
        Bloom glow = new Bloom();
        view.setOnMouseEntered(event -> {
            glow.setThreshold(0.75);
            view.setEffect(glow);
        });
        view.setOnMouseExited(event -> {
            view.setEffect(null);
        });
        view.setOnMouseClicked(e->{
        });
    }
}
