package view.allmenu;

import controll.DeckController;
import controll.ImageLoader;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import model.Deck;
import model.cards.Card;
import model.players.User;
import view.Menu;
import view.Regex;
import view.ViewMaster;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Matcher;


public class DeckView implements Initializable {
    @FXML
    private AnchorPane textBack;
    @FXML
    private ImageView cardImage;
    @FXML
    private VBox cardVBox;
    @FXML
    private VBox yesNoVBox;
    @FXML
    private AnchorPane cardShowZone;
    @FXML
    private GridPane sideDeck;
    @FXML
    private GridPane mainDeck;

    private Image backImage;
    private DeckController deckController;
    private User user;

    public DeckView() {
        deckController = new DeckController(this);
    }

    public void deckCreated() {
        System.out.println("deck created successfully!");
    }

    public DeckController getDeckController() {
        return deckController;
    }

    public void printDeckExists(String deckName) {
        System.out.println("deck with name " + deckName + " already exists");
    }

    public void deckDeleted() {
        System.out.println("deck deleted successfully");
    }

    public void printDeckDoesntExists(String deckName) {
        System.out.println("deck with name " + deckName + " does not exist");
    }

    public void printDeckActivated() {
        System.out.println("deck activated successfully");
    }

    public void printAddCardSuccessfully() {
        System.out.println("card added to deck successfully");
    }

    public void deckIsFull(String mainOrSide) {
        System.out.println(mainOrSide + " deck is full");
    }

    public void printCardRemoved() {
        System.out.println("card removed form deck successfully");
    }

    public void printAllUserDecks(ArrayList<Deck> decks, Deck activeDeck) {
        System.out.println("Decks:\n" +
                "Active deck:");
        System.out.println(activeDeck);
        System.out.println("Other decks:");
        for (Deck deck : decks) {
            if (deck != activeDeck)
                System.out.println(deck);
        }
    }

    public void run(String command) {
        if (command.matches(Regex.CREATE_DECK))
            createDeck(Regex.getInputMatcher(command, Regex.CREATE_DECK));
        else if (command.matches(Regex.DELETE_DECK))
            deleteDeck(Regex.getInputMatcher(command, Regex.DELETE_DECK));
        else if (command.matches(Regex.ACTIVE_DECK))
            activeDeck(Regex.getInputMatcher(command, Regex.ACTIVE_DECK));
        else if (command.matches(Regex.ADD_CARD_TO_DECK1)
                || command.matches(Regex.ADD_CARD_TO_DECK2)
                || command.matches(Regex.ADD_CARD_TO_DECK3))
            addCard(command);
        else if (command.matches(Regex.REMOVE_CARD_FROM_DECK)
                || command.matches(Regex.REMOVE_CARD_FROM_DECK2)
                || command.matches(Regex.REMOVE_CARD_FROM_DECK1))
            removeCard(command);
//        else if (command.matches(Regex.SHOW_DECKS))
//            deckController.showDecks();
        else if (command.matches(Regex.SHOW_ONE_DECK)
                || command.matches(Regex.SHOW_ONE_DECK2))
            showSpecificDeck(command);
//        else if (command.matches(Regex.DECK_SHOW_CARDS))
//            deckController.showCards();
        else if (command.matches(Regex.EXIT_MENU))
            ViewMaster.setCurrentMenu(Menu.MAIN_MENU);
        else
            System.out.println("invalid command");

    }

    private void showSpecificDeck(String command) {
        String deckName = Regex.getDeckNameForSpecificShow(command);
        boolean isSide = command.contains("--side");
//        deckController.showSpecificDeck(deckName, isSide);
    }

    private void removeCard(String command) {
        String cardName = Regex.getCardName(command);
        String deckName = Regex.getDeckName(command);
        boolean isSide = command.contains("--side");
//        deckController.removeCard(cardName, deckName, isSide);
    }

    private void addCard(String command) {
        String cardName = Regex.getCardName(command);
        String deckName = Regex.getDeckName(command);
        boolean isSide = command.contains("--side");
//        deckController.addCard(cardName, deckName, isSide);
    }

    private void activeDeck(Matcher inputMatcher) {
        inputMatcher.find();
        String deckName = inputMatcher.group("deckName");
//        deckController.activeDeck(deckName);
    }

    private void deleteDeck(Matcher inputMatcher) {
        inputMatcher.find();
        String deckName = inputMatcher.group("deckName");
//        deckController.deleteDeck(deckName);
    }

    private void createDeck(Matcher inputMatcher) {
        inputMatcher.find();
        String deckName = inputMatcher.group("deckName");
//        deckController.createDeck(deckName);
    }

    public void printInvalidCommand() {
        System.out.println("invalid command");
    }

    public void printCardDoesntExist(String cardName) {
        System.out.println("card with name " + cardName + " does not exist");
    }

    public void printThereAreThree(String cardName, String deckName) {
        System.out.println("there are already three cards with name " + cardName + " in deck " + deckName);
    }

    public void printCardDoesntExistInDeck(String cardName, String mainOrSide) {
        System.out.println("card with name " + cardName + " does not exist in " + mainOrSide + " deck");
    }

    public void printEmptyDecksList() {
        System.out.println("Decks:\n" +
                "Active deck:\n" +
                "Other decks:");
    }

    public void printAllUserDeckWithoutActiveDeck(ArrayList<Deck> decks) {
        System.out.println("Decks:\n" +
                "Active deck:\n" +
                "Other decks:");
        for (Deck deck : decks) {
            System.out.println(deck);
        }
    }

    public void printBeforeNonMonster() {
        System.out.println("Spell and Traps:");
    }

    public void printDeckListOnlyHaveActiveDeck(Deck activeDeck) {
        System.out.println("Decks:\n" +
                "Active deck:");
        System.out.println(activeDeck);
        System.out.println("Other decks:");
    }

    public void printCard(String cardName, String description) {
        System.out.println(cardName + ":" + description);
    }

    public void printMonster(String cardName, String cardDescription) {
        System.out.println(cardName + ": " + cardDescription);
    }

    public void printBeforeMonster(String deckName, boolean isSide) {
        System.out.println("Deck: " + deckName);
        System.out.println(isSide ? "Side" : "Main" + "deck:");
        System.out.println("Monsters:");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainDeck.setGridLinesVisible(true);
        sideDeck.setGridLinesVisible(true);
        cardVBox.setSpacing(20);
        backImage = ImageLoader.getCardImageByName("Unknown");
        cardImage.setImage(backImage);
        user = ViewMaster.getUser();
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
            imageView.setOnMouseExited(event -> stopShowCard(card, imageView));
        }
    }

    private void stopShowCard(Card card, ImageView imageView) {
        imageView.getScene().setCursor(Cursor.DEFAULT);
        addCardTransition(backImage);

    }

    private void showCard(Card card, ImageView imageView) {
        imageView.getScene().setCursor(Cursor.HAND);
        addCardTransition(imageView.getImage());
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
}
