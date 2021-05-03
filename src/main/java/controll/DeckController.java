package controll;

import CSV.MonsterCSV;
import CSV.SpellTrapCSV;
import bullshit.Interfaces.Effects;
import model.cards.Card;
import model.Deck;
import view.allmenu.DeckView;
import view.ViewMaster;

import java.io.FileNotFoundException;
import java.util.*;

public class DeckController {
    private DeckView deckView;
    private MonsterCSV monsterCSV = new MonsterCSV();
    private SpellTrapCSV spellTrapCSV = new SpellTrapCSV();
    public static HashMap<String , Effects> effects = new HashMap<>();

    public DeckController(DeckView deckView) {
        this.deckView = deckView;
    }

    public void createDeck(String deckName) {

        if (ViewMaster.getUser().getDeckByName(deckName) != null)
            deckView.printDeckExists(deckName);
        else {
            ViewMaster.getUser().getAllDecks().add(new Deck(deckName));
            deckView.deckCreated();
        }
    }

    public void deleteDeck(String deckName) {
        Deck deck = ViewMaster.getUser().getDeckByName(deckName);
        if (deck == null)
            deckView.printDeckDoesntExists(deckName);
        else {
            ViewMaster.getUser().getAllDecks().remove(deck);
            deckView.deckDeleted();
        }
    }

    public void activeDeck(String deckName) {
        Deck deck = ViewMaster.getUser().getDeckByName(deckName);
        if (deck == null)
            deckView.printDeckDoesntExists(deckName);
        else {
            deckView.printDeckActivated();
            deck.setActive(true);
        }
    }

    public void addCard(String cardName, String deckName, boolean isSide) {
        if (cardName == null
                || deckName == null) {
            deckView.printInvalidCommand();
            return;
        }
        if (doesHaveCard(cardName)) {
            Deck deck = ViewMaster.getUser().getDeckByName(deckName);
            if (deck == null)
                deckView.printCardDoesntExist(deckName);
            else {
                if (isDeckFull(deck, isSide))
                    deckView.deckIsFull(isSide ? "side" : "main");
                else {
                    if (areThereThree(deck, cardName))
                        deckView.printThereAreThree(cardName, deck.getName());
                    else {
                        deck.addNewCard(findCard(cardName), isSide);
                        deckView.printAddCardSuccessfully();
                    }
                }
            }
        } else
            deckView.printCardDoesntExist(cardName);
    }

    private void addToHashMap(String name){

    }

    private Card findCard(String cardName) {
        MonsterCSV monster;
        SpellTrapCSV spellOrTrap;
        try {
            monster = monsterCSV.findMonster(cardName);
            spellOrTrap = spellTrapCSV.findSpellTrap(cardName);
        } catch (FileNotFoundException e) {
        }
//        if (monster == null) return new Monster(,effects.get(cardName));
        else

    }

    private boolean areThereThree(Deck deck, String cardName) {
        int counter = 0;
        for (Card card : deck.getAllCardsInMainDeck())
            if (card.getCardName().equals(cardName))
                counter++;
        for (Card card : deck.getAllCardsInSideDeck())
            if (card.getCardName().equals(cardName))
                counter++;
        return (counter == 3);
    }

    private boolean isDeckFull(Deck deck, boolean isSide) {
        if (isSide)
            return (deck.getAllCardsInSideDeck().size() == 15);
        else
            return (deck.getAllCardsInMainDeck().size() == 60);
    }

    private boolean doesHaveCard(String cardName) {
        return ViewMaster.getUser().getAllCards().containsKey(cardName);
    }

    public void removeCard(String cardName, String deckName, boolean isSide) {
        if (cardName == null
                || deckName == null) {
            deckView.printInvalidCommand();
            return;
        }
        Deck deck = ViewMaster.getUser().getDeckByName(deckName);
        if (deck == null)
            deckView.printDeckDoesntExists(deckName);
        else {
            Card card = deck.getCardByName(cardName, isSide);
            if (card != null) {
                deck.removeCard(card, isSide);
                deckView.printCardRemoved();
            } else
                deckView.printCardDoesntExistInDeck(cardName, isSide ? "side" : "main");
        }
    }

    public void showDecks() {
        ArrayList<Deck> decks = ViewMaster.getUser().getAllDecks();
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

    public void showCards() {
        MonsterCSV monsterCSV = null;
        SpellTrapCSV spellTrapCSV = null;
        TreeMap<String, Integer> userCards = new TreeMap<>(ViewMaster.getUser().getAllCards());
        for (String cardName : userCards.keySet()) {
            try {
                monsterCSV = MonsterCSV.findMonster(cardName);
                spellTrapCSV = SpellTrapCSV.findSpellTrap(cardName);
            } catch (FileNotFoundException e) {
            }
            if (monsterCSV == null)
                deckView.printCard(cardName, spellTrapCSV.getDescription());
            else
                deckView.printCard(cardName, monsterCSV.getDescription());
            monsterCSV = null;
            spellTrapCSV = null;
        }
    }
}
