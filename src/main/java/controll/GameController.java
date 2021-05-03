package controll;

import model.players.Player;
import view.allmenu.GameView;

public class GameController {
    private final GameView gameView;
    private Player firstPlayer;
    private Player secondPlayer;
    private Player currentPlayer;

    public GameController(GameView gameView) {
        this.gameView = gameView;

    }

    public Player getRivalPlayer() {
        if (currentPlayer == firstPlayer)
            return secondPlayer;
        return firstPlayer;
    }

    public void selectPlayerMonster(int cardAddress) {

    }

    public void selectPlayerSpellOrTrap(int cardAddress) {

    }

    public void selectOpponentMonster(int cardAddress) {

    }

    public void selectOpponentSpellOrTrap(int cardAddress) {

    }

    public void selectPlayerFieldCard() {

    }

    public void selectOpponentFieldCard() {

    }

    public void selectPlayerHandCard(int cardAddress) {

    }

    public void deselectCard(){

    }
}
