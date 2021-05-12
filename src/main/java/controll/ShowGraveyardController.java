package controll;

import model.cards.Card;
import model.players.Player;
import view.allmenu.ShowGraveyardMenu;

public class ShowGraveyardController {
    private final ShowGraveyardMenu showGraveyardMenu;
    private final Player currentPlayer;

    public ShowGraveyardController(ShowGraveyardMenu showGraveyardMenu, Player currentPlayer) {
        this.showGraveyardMenu = showGraveyardMenu;
        this.currentPlayer = currentPlayer;
    }

    public void     showGraveyard() {
        if (currentPlayer.getBoard().getGraveyard().getAllCards().size() == 0) {
            showGraveyardMenu.printGraveyardEmpty();
            return;
        }
        for (int i = 0; i < currentPlayer.getBoard().getGraveyard().getAllCards().size(); i++) {
            Card card = currentPlayer.getBoard().getGraveyard().getAllCards().get(i);
            showGraveyardMenu.printCard(card, i);
        }
    }
}
