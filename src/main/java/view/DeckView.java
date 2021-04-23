package view;

import model.Deck;
import controll.DeckController;

import java.util.ArrayList;
import java.util.regex.Matcher;


public class DeckView {
    private DeckController deckController;

    public DeckView() {
        deckController = new DeckController(this);
    }

    public void deckCreated() {
        System.out.println("deck created successfully!");
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

    public void printInvalidCard(String cardName) {

    }

    public void deckIsFull(String mainOrSide) {
        System.out.println(mainOrSide + " deck is full");
    }

    public void printInvalidNumberOfCards(String cardName) {

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
            System.out.println(deck);
        }
    }

    public void printDeck(String deck) {

    }

    public void run(String command) {
        if (command.matches(Regex.CREATE_DECK))
            createDeck(Regex.getInputMatcher(command, Regex.CREATE_DECK));
        else if (command.matches(Regex.DELETE_DECK))
            deleteDeck(Regex.getInputMatcher(command, Regex.DELETE_DECK));
        else if (command.matches(Regex.ACTIVE_DECK))
            activeDeck(Regex.getInputMatcher(command, Regex.ACTIVE_DECK));
        else if (command.matches(Regex.ADD_CARD_TO_DECK))
            addCard(command);
        else if (command.matches(Regex.REMOVE_CARD_FROM_DECK))
            removeCard(command);
        else if (command.matches(Regex.SHOW_DECKS))
            deckController.showDecks();
        else if (command.matches(Regex.DECK_SHOW_CARDS))
            deckController.showCards();

    }

    private void removeCard(String command) {
        String cardName = Regex.getCardName(command);
        String deckName = Regex.getDeckName(command);
        boolean isSide = command.contains("--side");
        deckController.removeCard(cardName, deckName, isSide);
    }

    private void addCard(String command) {
        String cardName = Regex.getCardName(command);
        String deckName = Regex.getDeckName(command);
        boolean isSide = command.contains("--side");
        deckController.addCard(cardName, deckName, isSide);
    }

    private void activeDeck(Matcher inputMatcher) {
        inputMatcher.find();
        String deckName = inputMatcher.group("deckName");
        deckController.activeDeck(deckName);
    }

    private void deleteDeck(Matcher inputMatcher) {
        inputMatcher.find();
        String deckName = inputMatcher.group("deckName");
        deckController.deleteDeck(deckName);
    }

    private void createDeck(Matcher inputMatcher) {
        inputMatcher.find();
        String deckName = inputMatcher.group("deckName");
        deckController.createDeck(deckName);
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

    public void printDeckListOnlyHaveActiveDeck(Deck activeDeck) {
        System.out.println("Decks:\n" +
                "Active deck:");
        System.out.println(activeDeck);
        System.out.println("Other decks:");
    }

    public void printCard(String cardName, String description) {
        System.out.println(cardName + ":" + description);
    }
}
