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
import javafx.scene.input.*;
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
        mainDeck.setOnDragOver(this::handleDragOver);
        mainDeck.setOnDragDropped(this::handleDragDropped);
        sideDeck.setOnDragOver(this::handleDragOver);
        sideDeck.setOnDragDropped(this::handleDragDropped);
        scrollPane.setOnDragOver(this::handleDragOver);
        scrollPane.setOnDragDropped(this::handleDragOver);
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
            imageView.setOnDragDetected(event -> handleDragDetection(event, imageView, card));
            imageView.setOnDragDone(event -> setDragDone(event, imageView));
        }
    }

    private void setDragDone(DragEvent event, ImageView imageView) {
        if (mainDeck.getChildren().contains(imageView)) {

        } else if (sideDeck.getChildren().contains(imageView)) {

        } else if (cardVBox.getChildren().contains(imageView)) {
            System.out.println("hello");
        }
    }

    private void handleDragDropped(DragEvent event) {
        String cardName = event.getDragboard().getString();
        ImageView imageView = new ImageView();
        imageView.setImage(ImageLoader.getCardImageByName(cardName));
        if (event.getTarget().equals(mainDeck)) {
            System.out.println("olala");
            if (currentPlayerDeck != null) {
                String answer = deckController.addCard(user, cardName, currentPlayerDeck.getName(), false);
                if (answer.equals("added")) {
                    addCardToDeck(mainDeck , imageView , false , cardName);
                    //control card disable in scroll
                } else if (answer.equals("")) {

                }
            } else createNewDeck();
        } else if (event.getTarget().equals(sideDeck)) {
            System.out.println("giligili");
            if (currentPlayerDeck != null) {
                String answer = deckController.addCard(user , cardName , currentPlayerDeck.getName() , true);
                if (answer.equals("added")) {
                    addCardToDeck(sideDeck , imageView , true , cardName);
                } else if (answer.equals("threeCardExists")) {

                }
            } else createNewDeck();
        }
    }

    private void addCardToDeck(GridPane gridPane, ImageView imageView, boolean isSide, String cardName) {
        imageView.setFitWidth(42.1 * 1.5);
        imageView.setFitHeight(61.4 * 1.5);
        Card card = user.getCardByName(cardName);
        imageView.setOnMouseEntered(e -> showCard(card, imageView));
        imageView.setOnMouseExited(e -> stopShowCard(imageView));
        imageView.setOnDragDetected(e -> handleDragDetection(e, imageView, card));
        imageView.setOnDragDone(e -> setDragDone(e, imageView));
        int cardNum = currentPlayerDeck.getNumberOfCardsInMain();
        if (isSide)
            cardNum = currentPlayerDeck.getNumberOfCardsInSide();
        int columnIndex = cardNum % gridPane.getColumnConstraints().size();
        int rowIndex = (cardNum - columnIndex) / gridPane.getColumnConstraints().size();
        currentPlayerDeck.setNumberOfCardsInMain(cardNum + 1);
        gridPane.add(imageView, columnIndex, rowIndex);
    }

    private void handleDragOver(DragEvent event) {
        if (event.getDragboard().hasString()) {
            event.acceptTransferModes(TransferMode.ANY);
        }
    }

    private void handleDragDetection(MouseEvent event, ImageView imageView, Card card) {
        Dragboard dragboard = imageView.startDragAndDrop(TransferMode.ANY);
        ClipboardContent clipboardContent = new ClipboardContent();
        clipboardContent.putString(card.getCardName());
        dragboard.setContent(clipboardContent);
        event.consume();
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

    @FXML
    private void renameDeck() {
        blurBackground();
        yesNoVBox.getChildren().clear();
        yesNoVBox.setAlignment(Pos.CENTER);
        Label label = new Label("Enter New Deck Name :");
        TextField textField = new TextField();
        textField.setPromptText("Deck Name");
        textField.setAlignment(Pos.CENTER);
        textField.setStyle("-fx-background-color: rgba(0 ,0 , 0 ,0.5); -fx-font-family: 'Comic Sans MS'; -fx-font-size: 18px");
        MyButton myButton = new MyButton("Change");
        Label text = new Label();
        text.setStyle("-fx-text-fill: red");
        yesNoVBox.setSpacing(30);
        yesNoVBox.getChildren().addAll(label, textField, myButton, text);
        myButton.setOnAction(event -> renameCurrentDeck(textField, text));
    }

    private void renameCurrentDeck(TextField textField, Label text) {
        String answer = deckController.renameDeck(user, textField.getText());
        if (answer.equals("invalidDeckName")) {
            text.setText("Invalid Deck Name , Enter Another Name!");
        } else if (answer.equals("repetitive")) {
            text.setText("Repetitive Deck Name ! Enter Another Name");
        } else if (answer.equals("renamed")) {
            text.setText("Renamed Deck Name!");
            KeyFrame keyFrame = new KeyFrame(Duration.millis(3000), e -> undoBlurBackground());
            Timeline timeline = new Timeline(keyFrame);
            timeline.play();
            currentPlayerDeck = user.getDeckByName(textField.getText().trim());
            deckName.setText(currentPlayerDeck.getName());
        }
    }

    @FXML
    private void createNewDeck() {
        blurBackground();
        yesNoVBox.getChildren().clear();
        yesNoVBox.setAlignment(Pos.CENTER);
        Label label2 = new Label("Creating A New Deck");
        Label label = new Label("Please Enter Deck Name :");
        TextField textField = new TextField();
        textField.setPromptText("Deck Name");
        textField.setAlignment(Pos.CENTER);
        textField.setStyle("-fx-background-color: rgba(0 ,0 , 0 ,0.5); -fx-font-family: 'Comic Sans MS'; -fx-font-size: 18px");
        MyButton myButton = new MyButton("Create");
        Label text = new Label();
        text.setStyle("-fx-text-fill: red");
        yesNoVBox.setAlignment(Pos.CENTER);
        yesNoVBox.setSpacing(30);
        yesNoVBox.getChildren().addAll(label2 , label, textField, myButton, text);
        myButton.setOnAction(event -> createDeck(textField, text));
    }

    private void createDeck(TextField textField, Label text) {
        String ans = deckController.createDeck(user, textField.getText());
        if (ans.equals("invalidDeckName")) {
            text.setText("Enter a valid Deck Name!");
        } else if (ans.equals("hasDeck")) {
            text.setText("Deck With Name " + textField.getText() + " Already Exists");
        } else if (ans.equals("created")) {
            yesNoVBox.getChildren().clear();
            yesNoVBox.getChildren().add(text);
            text.setText("Deck Created Successfully!");
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