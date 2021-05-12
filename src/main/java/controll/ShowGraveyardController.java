package controll;

import model.cards.Card;
import model.cards.Monster;
import model.players.Player;
import view.allmenu.ShowGraveyardView;

public class ShowGraveyardController {
    private final ShowGraveyardView showGraveyardView;
    private final Player currentPlayer;

    public ShowGraveyardController(ShowGraveyardView showGraveyardView, Player currentPlayer) {
        this.showGraveyardView = showGraveyardView;
        this.currentPlayer = currentPlayer;
    }

    public int showGraveyard() {
        int counter = 0;
        for (int i = 0; i < currentPlayer.getBoard().getGraveyard().getAllCards().size(); i++) {
            Card card = currentPlayer.getBoard().getGraveyard().getAllCards().get(i);
            if (card instanceof Monster) {
                counter++;
                showGraveyardView.printCard(card, counter);
            }
        }
        if (counter == 0)
            showGraveyardView.printGraveyardEmpty();
        return counter;
    }

    public void selectCardFromGraveyard(int number) {
        for (Card card : currentPlayer.getBoard().getGraveyard().getAllCards()) {
            if (card instanceof Monster) {
                number--;
                if (number == 0) {
                    currentPlayer.setSelectedCard(card);
                }
            }
        }
    }
}
