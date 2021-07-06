package view.allmenu;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.Bloom;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.Arrays;

public class FookingTest extends Application {
    public GridPane gridPane;
    public BorderPane motherPane;
    public StackPane centerPane;
    public StackPane rivalGraveyard;
    public StackPane ourField;
    public StackPane rivalField;
    public StackPane ourGraveyard;
    public AnchorPane leftPane;
    public GridPane leftGrid;
    public ImageView selectedCard;
    public Label cardDamage;
    public Label cardDescription;
    public Label cardName;
    public ImageView rivalSelectedCard;
    public Label rivalCardName;
    public Label rivalCardDamage;
    public Label rivalCardDescription;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(LoginView.class.getResource("/fxml/Game.fxml"));
        primaryStage.setResizable(false);
        primaryStage.setTitle("you-gey-oh?:D");
        primaryStage.setScene(new Scene(loader.load()));
        primaryStage.show();
    }

    public void initialize() {
        Arrays.stream(new Label[]{cardDamage, cardDescription, cardName,
                rivalCardDamage, rivalCardName, rivalCardDescription})
                .forEach(e -> e.setVisible(false));
        centerPane.setAlignment(Pos.CENTER);
        Image background = new Image("/shopImage/Monsters/AxeRaider.jpg");
        Image background2 = new Image("/shopImage/Monsters/Bitron.jpg");
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 4; j++) {
                StackPane stackPane = new StackPane();
                stackPane.setPrefWidth(140);
                stackPane.setPrefHeight(190);
                ImageView cardImages = new ImageView(background);
                setEffectsCardImages(cardImages);
                cardImages.setFitWidth(140);
                cardImages.setFitHeight(190);
                stackPane.getChildren().add(cardImages);
                if (j < 2) {
                    stackPane.rotateProperty().set(180);
                }
                gridPane.add(stackPane, i, j);
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                StackPane stackPane = new StackPane();
                stackPane.setPrefWidth(140);
                stackPane.setPrefHeight(190);
                ImageView cardImages = new ImageView(background2);
                setEffectsCardImages(cardImages);
                cardImages.setFitWidth(140);
                cardImages.setFitHeight(190);
                stackPane.getChildren().add(cardImages);
                leftGrid.add(stackPane, i, j);
            }
        }
        leftGrid.setGridLinesVisible(true);
        leftGrid.setVgap(5);
        leftGrid.setHgap(5);
        fourOtherCards(background);
        gridPane.setTranslateX(20);
        gridPane.setTranslateY(50);
        gridPane.setHgap(10);
        gridPane.setVgap(30);
        centerPane.setStyle("-fx-background-image:url('/gamePics/a.jpg'); -fx-background-size: cover,auto;");
    }

    private void fourOtherCards(Image background) {
        ourGraveyard = new StackPane();
        rivalGraveyard = new StackPane();
        ourField = new StackPane();
        rivalField = new StackPane();
        rivalField.rotateProperty().set(180);
        rivalGraveyard.rotateProperty().set(180);
        VBox our = new VBox(30, rivalGraveyard, ourField);
        VBox rivals = new VBox(30, rivalField, ourGraveyard);
        Arrays.stream(new VBox[]{our, rivals}).forEach(e -> {
            e.setPrefWidth(140);
            e.setMaxHeight(190);
            e.setMaxWidth(140);
            e.setPrefHeight(190);
        });
        Arrays.stream(new StackPane[]{ourField, rivalField, ourGraveyard, rivalGraveyard}).forEach(x -> {
            x.setPrefWidth(140);
            x.setPrefHeight(190);
            ImageView cardImages = new ImageView(background);
            setEffectsCardImages(cardImages);
            cardImages.setFitWidth(140);
            cardImages.setFitHeight(190);
            x.getChildren().add(cardImages);
        });
        our.setTranslateX(-450);
        our.setTranslateY(40);
        rivals.setTranslateX(470);
        rivals.setTranslateY(60);
        centerPane.getChildren().addAll(our, rivals);
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
        view.setOnMouseClicked(e-> {
            if(view.getParent().getRotate()!=180) {
                selectedCard.setImage(view.getImage());
                Arrays.stream(new Label[]{cardDamage, cardDescription, cardName}).forEach(a -> a.setVisible(true));
            }
            else {
                rivalSelectedCard.setImage(view.getImage());
                Arrays.stream(new Label[]{rivalCardDescription,
                        rivalCardDamage ,rivalCardName}).forEach(a -> a.setVisible(true));
            }
        });
    }
}
