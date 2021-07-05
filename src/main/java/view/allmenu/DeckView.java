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
import javafx.scene.effect.ColorAdjust;
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
import java.util.HashMap;
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
    private TilePane sideDeck;
    @FXML
    private TilePane mainDeck;

    private Image backImage;
    private final DeckController deckController;
    private User user;
    private UserDeck currentPlayerDeck;

    public DeckView() {
        deckController = new DeckController();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainDeck.setHgap(2);
        mainDeck.setVgap(2);
        sideDeck.setHgap(2);
        sideDeck.setVgap(2);
        mainDeck.setOnDragOver(this::handleDragOver);
        mainDeck.setOnDragDropped(this::handleDragDropped);
        sideDeck.setOnDragOver(this::handleDragOver);
        sideDeck.setOnDragDropped(this::handleDragDropped);
        scrollPane.setOnDragOver(this::handleDragOver);
        scrollPane.setOnDragDropped(this::handleDragOver);
        yesNoVBox.setAlignment(Pos.CENTER);
        yesNoVBox.setSpacing(15);
        cardVBox.setSpacing(20);
        backImage = ImageLoader.getCardImageByName("Unknown");
        cardImage.setImage(backImage);
        user = ViewMaster.getUser();
        if (user.getAllDecks().size() != 0) {
            currentPlayerDeck = user.getAllDecks().get(0);
            deckName.setText(currentPlayerDeck.getName());
        } else {
            deckName.setText("Please Create Or Load Deck");
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
        }
    }

    private void handleDragDropped(DragEvent event) {
        String cardName = event.getDragboard().getString();
        ImageView sourceImageView;
        if (!(event.getGestureSource() instanceof ImageView))
            return;
        else sourceImageView = (ImageView) event.getGestureSource();
        ImageView imageView = new ImageView();
        imageView.setImage(sourceImageView.getImage());
        if (event.getTarget().equals(mainDeck)) {
            System.out.println("dropped in main");
            if (mainDeck.getChildren().contains(sourceImageView))
                return;
            String answer = deckController.addCard(user, cardName, currentPlayerDeck.getName(), false);
            switch (answer) {
                case "added":
                    addCardToDeck(mainDeck, imageView, cardName);
                    disableCardInScroll(currentPlayerDeck.getCardNameToNumberInMain(), event, cardName);
                    if (sideDeck.getChildren().contains(sourceImageView))
                        removeCard(sideDeck, sourceImageView, cardName, true);
                    break;
                case "threeCardExists":
                    showDeckHasThreeCard(false, cardName);
                    break;
                case "deckIsFull":
                    showDeckIsFull(false);
                    break;
                case "noDeckExists":
                    showCreateDeckFirst();
                    break;
            }
        } else if (event.getTarget().equals(sideDeck)) {
            System.out.println("dropped in side");
            if (sideDeck.getChildren().contains(sourceImageView))
                return;
            String answer = deckController.addCard(user, cardName, currentPlayerDeck.getName(), true);
            switch (answer) {
                case "added":
                    addCardToDeck(sideDeck, imageView, cardName);
                    disableCardInScroll(currentPlayerDeck.getCardNameToNumberInSide(), event, cardName);
                    if (mainDeck.getChildren().contains(sourceImageView))
                        removeCard(mainDeck, sourceImageView, cardName, false);
                    break;
                case "threeCardExists":
                    showDeckHasThreeCard(true, cardName);
                    break;
                case "deckIsFull":
                    showDeckIsFull(true);
                    break;
                case "noDeckExists":
                    showCreateDeckFirst();
                    break;
            }
        } else if (event.getTarget().equals(scrollPane)) {
            System.out.println("dropped in scroll");
            if (mainDeck.getChildren().contains(sourceImageView)) {
                removeCard(mainDeck, sourceImageView, cardName, false);
            } else if (sideDeck.getChildren().contains(sourceImageView)) {
                removeCard(sideDeck, sourceImageView, cardName, true);
            }
        }
    }

    private void removeCard(TilePane sourceGridPane, ImageView sourceImageView, String cardName, boolean isSide) {
        String answer = deckController.removeCard(user, cardName, currentPlayerDeck.getName(), isSide);
        if (answer.equals("removed")) {
            sourceGridPane.getChildren().remove(sourceImageView);
        }
    }

    private void disableCardInScroll(HashMap<String, Integer> cardNameToNumberInDeck, DragEvent event, String cardName) {
        if (event.getGestureSource() instanceof ImageView) {
            ImageView imageView1 = (ImageView) event.getGestureSource();
            if (cardVBox.getChildren().contains(imageView1)) {
                int numberInDeck = cardNameToNumberInDeck.getOrDefault(cardName, 0);
                String cardName2 = cardName.replace(" ", "");
                int shoppedNumber = user.getCardNameToNumber().getOrDefault(cardName2, 0);
                if (numberInDeck == shoppedNumber) {
                    imageView1.setDisable(true);
                    ColorAdjust colorAdjust = new ColorAdjust();
                    colorAdjust.setBrightness(-0.5);
                    imageView1.setEffect(colorAdjust);
                } else {
                    imageView1.setDisable(false);
                    imageView1.setEffect(null);
                }
            }
        }
    }

    private void showDeckHasThreeCard(boolean isSide, String cardName) {
        blurBackground();
        String main = "Main";
        if (isSide) main = "Side";
        Label label = new Label("You Already Have Three " + cardName + " Card In Your " + main + " Deck");
        addTimeLine(label);
    }

    private void showDeckIsFull(boolean isSide) {
        blurBackground();
        String main = "Main";
        if (isSide) main = "Side";
        Label label = new Label(main + " Deck Is Full");
        addTimeLine(label);
    }

    private void addTimeLine(Label label) {
        label.setStyle("-fx-text-fill: red");
        yesNoVBox.getChildren().clear();
        yesNoVBox.getChildren().add(label);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(2000), e -> undoBlurBackground());
        Timeline timeline = new Timeline(keyFrame);
        timeline.play();
    }

    private void addCardToDeck(TilePane tilePane, ImageView imageView, String cardName) {
        imageView.setFitWidth(42.1 * 1.5);
        imageView.setFitHeight(61.4 * 1.5);
        Card card = user.getCardByName(cardName);
        imageView.setOnMouseEntered(e -> showCard(card, imageView));
        imageView.setOnMouseExited(e -> stopShowCard(imageView));
        imageView.setOnDragDetected(e -> handleDragDetection(e, imageView, card));
        tilePane.getChildren().add(imageView);
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
        Label label = new Label("Enter New Deck Name :");
        TextField textField = new TextField();
        textField.setPromptText("Deck Name");
        textField.setAlignment(Pos.CENTER);
        textField.setMaxWidth(200);
        textField.setStyle("-fx-background-color: rgba(0 ,0 , 0 ,0.5); -fx-font-family: 'Comic Sans MS'; -fx-font-size: 18px; -fx-text-fill: white");
        MyButton myButton = new MyButton("Change");
        Label text = new Label();
        text.setStyle("-fx-text-fill: red");
        yesNoVBox.getChildren().addAll(label, textField, myButton, text);
        myButton.setOnAction(event -> renameCurrentDeck(textField, text));
    }

    private void renameCurrentDeck(TextField textField, Label text) {
        String answer = deckController.renameDeck(user, currentPlayerDeck.getName(), textField.getText());
        switch (answer) {
            case "invalidDeckName":
                text.setText("Invalid Deck Name , Enter Another Name!");
                break;
            case "repetitive":
                text.setText("Repetitive Deck Name , Enter Another Name!");
                break;
            case "renamed":
                text.setText("Renamed Deck Name!");
                KeyFrame keyFrame = new KeyFrame(Duration.millis(3000), e -> undoBlurBackground());
                Timeline timeline = new Timeline(keyFrame);
                timeline.play();
                deckName.setText(currentPlayerDeck.getName());
                break;
        }
    }

    @FXML
    private void createNewDeck() {
        blurBackground();
        yesNoVBox.getChildren().clear();
        Label label = new Label("Please Enter Deck Name :");
        TextField textField = new TextField();
        textField.setPromptText("Deck Name");
        textField.setAlignment(Pos.CENTER);
        textField.setStyle("-fx-background-color: rgba(0 ,0 , 0 ,0.5); -fx-font-family: 'Comic Sans MS'; -fx-font-size: 18px; -fx-text-fill: white");
        textField.setMaxWidth(200);
        MyButton myButton = new MyButton("Create");
        Label text = new Label();
        text.setStyle("-fx-text-fill: red");
        yesNoVBox.getChildren().addAll(label, textField, myButton, text);
        myButton.setOnAction(event -> createDeck(textField, text));
    }

    private void createDeck(TextField textField, Label text) {
        String ans = deckController.createDeck(user, textField.getText());
        switch (ans) {
            case "invalidDeckName":
                text.setText("Enter a valid Deck Name!");
                break;
            case "hasDeck":
                text.setText("Deck With Name " + textField.getText() + " Already Exists");
                break;
            case "created":
                yesNoVBox.getChildren().clear();
                yesNoVBox.getChildren().add(text);
                text.setText("Deck Created Successfully!");
                KeyFrame keyFrame = new KeyFrame(Duration.millis(3000), e -> undoBlurBackground());
                Timeline timeline = new Timeline(keyFrame);
                timeline.play();
                currentPlayerDeck = user.getDeckByName(textField.getText().trim());
                deckName.setText(currentPlayerDeck.getName());
                break;
        }
    }


    private void showCreateDeckFirst() {
        blurBackground();
        yesNoVBox.getChildren().clear();
        Label label = new Label("Please Create A Deck At First!");
        MyButton myButton = new MyButton("Create Deck");
        MyButton myButton1 = new MyButton("Close");
        HBox box = new HBox();
        box.setSpacing(20);
        box.getChildren().addAll(myButton, myButton1);
        myButton.setOnAction(event -> createNewDeck());
        myButton1.setOnAction(event -> undoBlurBackground());
        yesNoVBox.getChildren().addAll(label, box);
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

    private void deleteCurrentDeck() {
        boolean deleteDeck = deckController.deleteDeck(user, currentPlayerDeck.getName());
        if (deleteDeck) {
            showDeckDeleted();
        } else {
            showCreateDeckFirst();
        }
    }

    private void showDeckDeleted() {
        blurBackground();
        yesNoVBox.getChildren().clear();
        Label label = new Label("Please Create A Deck At First!");
        label.setStyle("-fx-text-fill: red");
        yesNoVBox.getChildren().addAll(label);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(3000), event -> undoBlurBackground());
        Timeline timeline = new Timeline(keyFrame);
        timeline.play();
        currentPlayerDeck = null;
        deckName.setText("Please Create Or Load Deck");
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