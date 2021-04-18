package view;

import model.Deck;
import controll.DeckController;
import java.util.ArrayList;


public class DeckView {
    private DeckController deckController;
    private String command;

    DeckView(){
        deckController = new DeckController();
    }

    public void deckCreated() {

    }

    public void printDeckExists(String deckName) {

    }

    public void deckDeleted() {

    }

    public void printDeckDoesntExists(String deckName) {

    }

    public void printDeckActivated() {

    }

    public void printAddCardSuccessfully() {

    }

    public void printInvalidCard(String cardName) {

    }

    public void deckIsFull(String mainOrSide) {

    }

    public void printInvalidNumberOfCards(String cardName) {

    }

    public void printCardRemoved(String cardName) {

    }

    public void printAllUserDecks(ArrayList<Deck> allDecks) {

    }

    public void printDeck(String deck) {

    }

    public void run(String command) {
    }
}
