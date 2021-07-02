package controll.gameController;

import model.Deck;
import model.players.Player;

public class ChangeDeckController {

    private final Player player;
    private final Deck deck;
    private final int startingDeckSize;

    public ChangeDeckController(Player player, Deck deck) {
        this.player = player;
        this.deck = deck;
        startingDeckSize = deck.getAllCardsInMainDeck().size();
    }


    public void addCard(String cardName) {

    }

    public void removeCard(String group) {
    }

    public void checkExit() {

    }
}
