package view.allmenu;

import controll.DeckController;
import controll.ImageLoader;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.UserDeck;
import model.cards.Card;
import model.players.User;
import view.SceneController;
import view.ViewMaster;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class DeckView implements Initializable {
    @FXML
    private VBox leftVBox;
    @FXML
    private AnchorPane mainDeckBack;
    @FXML
    private AnchorPane sideDeckBack;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private Label deckName;
    @FXML
    private AnchorPane yesNoBack;
    @FXML
    private VBox yesNoVBox;
    @FXML
    private BorderPane back;
    @FXML
    private TextArea textArea;
    @FXML
    private ImageView cardImage;
    @FXML
    private VBox cardVBox;
    @FXML
    private AnchorPane cardShowZone;
    @FXML
    private GridPane sideDeck;
    @FXML
    private GridPane mainDeck;

    private Image backImage;
    private final DeckController deckController;
    private User user;
    private UserDeck currentPlayerDeck;

    public DeckView() {
        deckController = new DeckController();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainDeck.setGridLinesVisible(true);
        sideDeck.setGridLinesVisible(true);
        cardVBox.setSpacing(20);
        backImage = ImageLoader.getCardImageByName("Unknown");
        cardImage.setImage(backImage);
        user = ViewMaster.getUser();
        if (user.getAllDecks().size() != 0) {
            currentPlayerDeck = user.getAllDecks().get(0);
            deckName.setText(currentPlayerDeck.getName());
        } else {
            deckName.setText("Please Create New Deck");
        }
        addCardToScrollPane();
    }

    private void addCardToScrollPane() {
        for (Card card : user.getAllCards()) {
            Image image = ImageLoader.getCardImageByName(card.getCardName());
            card.setImage(image);
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(42.1 * 2);
            imageView.setFitHeight(61.4 * 2);
            cardVBox.getChildren().add(imageView);
            imageView.setOnMouseEntered(event -> showCard(card, imageView));
            imageView.setOnMouseExited(event -> stopShowCard(imageView));
        }
    }

    private void stopShowCard(ImageView imageView) {
        imageView.getScene().setCursor(Cursor.DEFAULT);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(3000), event -> {
            addCardTransition(backImage);
            addText(null);
        });
        Timeline timeline = new Timeline(keyFrame);
        timeline.play();
    }

    private void showCard(Card card, ImageView imageView) {
        imageView.getScene().setCursor(Cursor.HAND);
        addCardTransition(imageView.getImage());
        addText(card.getCardDescription());
    }

    private void addCardTransition(Image secondImage) {
        RotateTransition rotator = new RotateTransition(Duration.millis(1000), cardImage);
        rotator.setAxis(Rotate.Y_AXIS);
        rotator.setFromAngle(0);
        rotator.setToAngle(90);
        rotator.setInterpolator(Interpolator.LINEAR);
        rotator.setCycleCount(1);
        rotator.play();
        rotator.setDelay(Duration.millis(1000));
        cardImage.setImage(secondImage);
        rotator = new RotateTransition(Duration.millis(1000), cardImage);
        rotator.setAxis(Rotate.Y_AXIS);
        rotator.setFromAngle(90);
        rotator.setToAngle(0);
        rotator.setInterpolator(Interpolator.LINEAR);
        rotator.setCycleCount(1);
        rotator.play();
    }

    private void addText(String text) {
        textArea.prefHeight(254);
        textArea.setText(text);
    }

    private void blurBackground() {
        leftVBox.setEffect(new GaussianBlur(15));
        mainDeckBack.setEffect(new GaussianBlur(15));
        sideDeckBack.setEffect(new GaussianBlur(15));
        scrollPane.setEffect(new GaussianBlur(15));
        scrollPane.setDisable(true);
        mainDeckBack.setDisable(true);
        sideDeckBack.setDisable(true);
        leftVBox.setDisable(true);
        yesNoBack.setVisible(true);
    }

    private void undoBlurBackground() {
        leftVBox.setEffect(null);
        mainDeckBack.setEffect(null);
        sideDeckBack.setEffect(null);
        scrollPane.setEffect(null);
        scrollPane.setDisable(false);
        mainDeckBack.setDisable(false);
        sideDeckBack.setDisable(false);
        leftVBox.setDisable(false);
        yesNoBack.setVisible(false);
    }

    public void renameDeck(ActionEvent actionEvent) {
    }

    @FXML
    private void createNewDeck() {
        blurBackground();
        yesNoVBox.getChildren().clear();
        yesNoVBox.setAlignment(Pos.CENTER);
        Label label = new Label("Enter Deck Name :");
        TextField textField = new TextField();
        textField.setPromptText("Deck Name");
        textField.setAlignment(Pos.CENTER);
        textField.setStyle("-fx-background-color: rgba(0 ,0 , 0 ,0.5); -fx-font-family: 'Comic Sans MS'; -fx-font-size: 18px");
        MyButton myButton = new MyButton("Create");
        Label text = new Label();
        text.setStyle("-fx-text-fill: red");
        yesNoVBox.setAlignment(Pos.CENTER);
        yesNoVBox.setSpacing(30);
        yesNoVBox.getChildren().addAll(label, textField, myButton, text);
        myButton.setOnAction(event -> createDeck(textField, text));
    }

    private void createDeck(TextField textField, Label text) {
        String ans = deckController.createDeck(user, textField.getText());
        if (ans.equals("invalidDeckName")) {
            text.setText("Enter a valid Deck Name!");
        } else if (ans.equals("hasDeck")) {
            text.setText("deck with name " + textField.getText() + " already exists");
        } else if (ans.equals("created")) {
            yesNoVBox.getChildren().clear();
            yesNoVBox.getChildren().add(text);
            text.setText("deck created successfully!");
            KeyFrame keyFrame = new KeyFrame(Duration.millis(3000), e -> undoBlurBackground());
            Timeline timeline = new Timeline(keyFrame);
            timeline.play();
            currentPlayerDeck = user.getDeckByName(textField.getText().trim());
            deckName.setText(currentPlayerDeck.getName());
        }
    }

    public void saveDeck(ActionEvent actionEvent) {
    }

    public void loadDeck(ActionEvent actionEvent) {
    }

    public void activeDeck(ActionEvent actionEvent) {
    }

    @FXML
    private void deleteDeck(ActionEvent actionEvent) {
        blurBackground();
        yesNoVBox.getChildren().clear();
        yesNoVBox.setAlignment(Pos.CENTER);
        yesNoVBox.setSpacing(30);
        if (currentPlayerDeck == null) {
            Label label = new Label("Create A Deck At First!");
            HBox hBox = new HBox();
            MyButton button = new MyButton("Create Deck");
            button.setOnAction(event -> createNewDeck());
            MyButton button1 = new MyButton("Close");
            button1.setOnAction(event -> undoBlurBackground());
            hBox.setAlignment(Pos.CENTER);
            hBox.getChildren().addAll(button, button1);
            hBox.setSpacing(50);
            yesNoVBox.getChildren().addAll(label, hBox);
        } else {
            Label label = new Label("Are you Sure You Want To Delete This Deck?");
            HBox hBox = new HBox();
            MyButton myButton = new MyButton("Yes");
            MyButton myButton1 = new MyButton("No");
            myButton.setOnAction(event -> deleteCurrentDeck());
            myButton1.setOnAction(event -> undoBlurBackground());
            hBox.setAlignment(Pos.CENTER);
            hBox.getChildren().addAll(label, myButton, myButton1);
            hBox.setSpacing(50);
            yesNoVBox.getChildren().addAll(label, hBox);
        }
    }

    private void deleteCurrentDeck() {
    }

    @FXML
    private void back() throws IOException {
        Stage stage = ((Stage) textArea.getScene().getWindow());
        SceneController.startMainMenu(stage);
    }
}

class MyButton extends Button {
    public MyButton(String text) {
        super(text);
        this.setFont(Font.font("Comic Sans MS", 18));
        this.setPrefWidth(134);
        this.setPrefHeight(56);
    }
}