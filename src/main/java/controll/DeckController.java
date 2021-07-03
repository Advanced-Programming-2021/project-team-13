package controll;


import enums.CardType;
import enums.Face;
import model.Deck;
import model.cards.Card;
import model.cards.Monster;
import model.cards.Spell;
import model.cards.Trap;
import model.csv.MonsterCSV;
import model.csv.SpellTrapCSV;
import view.ViewMaster;
import view.allmenu.DeckView;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeMap;

public class DeckController {
    private final DeckView deckView;

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
            Deck deck = ViewMaster.getUser().getDeckByName(deckName.trim());
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

    public Card findCard(String cardName) { ////// Spell,Trap,Monster Constructor Work Just For noEffect BRANCH
        MonsterCSV monster = null;
        SpellTrapCSV spellOrTrap = null;
        try {
            monster = MonsterCSV.findMonster(cardName.trim());
        } catch (FileNotFoundException ignored) {
        }
        try {
            spellOrTrap = SpellTrapCSV.findSpellTrap(cardName.trim());
        } catch (FileNotFoundException ignored) {
        }
        if (monster != null)
            return new Monster(monster.getName().replace("_", "-"), CardType.MONSTER, Face.DOWN,
                    monster.getPrice(), monster.getDescription()
                    , monster.getMonsterType(), monster.getCardType(), monster.getAttribute()
                    , monster.getAttack(), monster.getDefence(), monster.getLevel());
        else {
            if (spellOrTrap.getType() == CardType.SPELL)
                return new Spell(spellOrTrap.getName(), CardType.SPELL, spellOrTrap.getDescription(),
                        Face.DOWN, spellOrTrap.getPrice(), spellOrTrap.getIcon());
            else
                return new Trap(spellOrTrap.getName(), CardType.TRAP, spellOrTrap.getDescription(),
                        Face.DOWN, spellOrTrap.getPrice(), spellOrTrap.getIcon());
        }

    }

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

    private boolean isDeckFull(Deck deck, boolean isSide) {
        if (isSide)
            return (deck.getAllCardsInSideDeck().size() == 15);
        else
            return (deck.getAllCardsInMainDeck().size() == 60);
    }

    private boolean doesHaveCard(String cardName) {
        for (String card : ViewMaster.getUser().getAllCards().keySet()) {
            if (card.equalsIgnoreCase(cardName.replace("_", "-")))
                return true;
        }
        return false;
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
            } catch (FileNotFoundException ignored) {
            }
            for (int i = 0; i < userCards.get(cardName); i++) {
                if (monsterCSV == null)
                    deckView.printCard(cardName, spellTrapCSV.getDescription());
                else
                    deckView.printCard(cardName, monsterCSV.getDescription());
            }
            monsterCSV = null;
            spellTrapCSV = null;
        }
    }

    public void showSpecificDeck(String deckName, boolean isSide) {
        if (deckName == null) {
            deckView.printInvalidCommand();
            return;
        }
        Deck deck = ViewMaster.getUser().getDeckByName(deckName);
        if (deck == null) {
            deckView.printDeckDoesntExists(deckName);
            return;
        }
        ArrayList<Card> cards;
        if (isSide)
            cards = deck.getAllCardsInSideDeck();
        else
            cards = deck.getAllCardsInMainDeck();
        Collections.sort(cards);
        deckView.printBeforeMonster(deckName, isSide);
        for (Card card : cards) {
            if (card instanceof Monster)
                deckView.printMonster(card.getCardName(), card.getCardDescription());
        }
        deckView.printBeforeNonMonster();
        for (Card card : cards) {
            if (!(card instanceof Monster))
                deckView.printMonster(card.getCardName(), card.getCardDescription());

        }
    }
}
