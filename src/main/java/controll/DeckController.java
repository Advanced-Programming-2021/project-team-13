package controll;


import model.Deck;
import model.cards.Card;
import model.players.User;
import view.ViewMaster;
import view.allmenu.DeckView;

import java.util.ArrayList;
import java.util.Collections;

public class DeckController {
    private final DeckView deckView;

    public DeckController(DeckView deckView) {
        this.deckView = deckView;
    }

    public boolean createDeck(User user, String deckName) {
        if (user.getDeckByName(deckName) != null)
            return false;
        else {
            user.getAllDecks().add(new Deck(deckName));
            return true;
        }
    }

    public boolean deleteDeck(User user, String deckName) {
        Deck deck = user.getDeckByName(deckName);
        if (deck == null)
            return false;
        else {
            ViewMaster.getUser().getAllDecks().remove(deck);
            return true;
        }
    }

    public boolean activeDeck(User user, String deckName) {
        Deck deck = user.getDeckByName(deckName);
        if (deck == null)
            return false;
        else {
            for (Deck deck1 : user.getAllDecks())
                if (!deck1.getName().equals(deckName))
                    deck1.setActive(false);
            deck.setActive(true);
            return true;
        }
    }

    public String addCard(User user, String cardName, String deckName, boolean isSide) {
        if (doesHaveCard(user, cardName)) {
            Deck deck = user.getDeckByName(deckName.trim());
            if (deck == null)
                return "noDeckExists";
            else {
                if (isDeckFull(deck, isSide))
                    return "deckIsFull";
                else {
                    if (areThereThree(deck, cardName))
                        return "threeCardExists";
                    else {
                        deck.addNewCard(Card.findCardFromCsv(cardName), isSide);
                        return "added";
                    }
                }
            }
        } else return "noCardExists";
    }

    //should change
    private boolean areThereThree(Deck deck, String cardName) {
        int counter = 0;
        for (Card card : deck.getAllCardsInMainDeck())
            if (card.getCardName().equalsIgnoreCase(cardName))
                counter++;
        for (Card card : deck.getAllCardsInSideDeck())
            if (card.getCardName().equalsIgnoreCase(cardName))
                counter++;
        return (counter == 3);
    }

    //should change
    private boolean isDeckFull(Deck deck, boolean isSide) {
        if (isSide)
            return (deck.getAllCardsInSideDeck().size() == 12);
        else
            return (deck.getAllCardsInMainDeck().size() == 60);
    }

    private boolean doesHaveCard(User user, String cardName) {
        for (String card : user.getCardNameToNumber().keySet()) {
            if (card.equalsIgnoreCase(cardName.replace("_", "-")))
                return true;
        }
        return false;
    }

    public String removeCard(User user, String cardName, String deckName, boolean isSide) {
        Deck deck = user.getDeckByName(deckName);
        if (deck == null)
            return "noDeckExists";
        else {
            Card card = deck.getCardByName(cardName, isSide);
            if (card != null) {
                deck.removeCard(card, isSide);
                return "removed";
            } else return "noCardExists";
        }
    }

    public void showDecks(User user) {
        ArrayList<Deck> decks = user.getAllDecks();
        Deck activeDeck = null;
        for (Deck deck : decks) {
            if (deck.isActive()) {
                activeDeck = deck;
                break;
            }
        }
        Collections.sort(decks);
        if (decks.size() == 0)
            deckView.printEmptyDecksList();
        else if (activeDeck == null)
            deckView.printAllUserDeckWithoutActiveDeck(decks);
        else if (decks.size() == 1)
            deckView.printDeckListOnlyHaveActiveDeck(activeDeck);
        else
            deckView.printAllUserDecks(decks, activeDeck);
    }
}
