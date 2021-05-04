package controll;

import model.players.Player;
import view.allmenu.GameView;

public class GameController {
    private final GameView gameView;
    private Player currentPlayer;       // we only need current player since opponent has its rival
//    private Player secondPlayer;


    public GameController(GameView gameView) {
        this.gameView = gameView;

    }

//    public Player getRivalPlayer() {
//        if (currentPlayer == firstPlayer)
//            return secondPlayer;
//        return firstPlayer;
//    }

    public void selectPlayerMonster(int cardAddress) {
        currentPlayer.setSelectedCard(currentPlayer.getBoard().getMonsterByAddress(cardAddress));
    }

    public void selectPlayerSpellOrTrap(int cardAddress) {
        currentPlayer.setSelectedCard(currentPlayer.getBoard().getMonsterByAddress(cardAddress));
    }

    public void selectOpponentMonster(int cardAddress) {
        currentPlayer.setSelectedCard
                (currentPlayer.getRivalPlayer().getBoard().getMonsterByAddress(cardAddress));
    }

    public void selectOpponentSpellOrTrap(int cardAddress) {
        currentPlayer.setCurrentCard
                (currentPlayer.getRivalPlayer().getBoard().getSpellOrTrapByAddress(cardAddress));
    }

    public void selectPlayerFieldCard() {
        currentPlayer.setSelectedCard(currentPlayer.getBoard().getFieldSpell().getCard());
    }

    public void selectOpponentFieldCard() {
        currentPlayer.setSelectedCard
                (currentPlayer.getRivalPlayer().getBoard().getFieldSpell().getCard());
    }

    public void selectPlayerHandCard(int cardAddress) {
        currentPlayer.setSelectedCard(currentPlayer.getCardsInHand().get(cardAddress - 1));
    }

    public void deselectCard(){
        currentPlayer.setSelectedCard(null);
    }
}
