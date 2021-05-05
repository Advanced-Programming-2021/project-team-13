package controll;

import enums.AttackOrDefense;
import enums.Phase;
import model.cards.Card;
import model.cards.Monster;
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

    public void selectPlayerMonster(int cardAddress) {// no regex error!!! // are these handled or not!!??
        if (monsterSelectionCheck(cardAddress, currentPlayer)) {
            currentPlayer.setSelectedCard(currentPlayer.getBoard().getMonsterByAddress(cardAddress));
            gameView.printCardSelected();
        }
    }

    public void selectPlayerSpellOrTrap(int cardAddress) {
        if (spellSelectionCheck(cardAddress, currentPlayer)) {
            currentPlayer.setSelectedCard(currentPlayer.getBoard().getSpellOrTrapByAddress(cardAddress));
            gameView.printCardSelected();
        }
    }

    public void selectOpponentMonster(int cardAddress) {
        if (monsterSelectionCheck(cardAddress, currentPlayer.getRivalPlayer())) {
            currentPlayer.setSelectedCard
                    (currentPlayer.getRivalPlayer().getBoard().getMonsterByAddress(cardAddress));
            gameView.printCardSelected();
        }
    }


    public void selectOpponentSpellOrTrap(int cardAddress) {
        if (spellSelectionCheck(cardAddress, currentPlayer.getRivalPlayer())) {
            currentPlayer.setSelectedCard
                    (currentPlayer.getRivalPlayer().getBoard().getSpellOrTrapByAddress(cardAddress));
            gameView.printCardSelected();
        }
    }


    public void selectPlayerFieldCard() {
        if (fieldSpellCheck(currentPlayer)) {
            currentPlayer.setSelectedCard(currentPlayer.getBoard().getFieldSpell().getCard());
            gameView.printCardSelected();
        }
    }

    public void selectOpponentFieldCard() {
        if (fieldSpellCheck(currentPlayer.getRivalPlayer())) {
            currentPlayer.setSelectedCard
                    (currentPlayer.getRivalPlayer().getBoard().getFieldSpell().getCard());
            gameView.printCardSelected();
        }
    }

    public void selectPlayerHandCard(int cardAddress) {
        if (handCardCheck(cardAddress)) {
            currentPlayer.setSelectedCard(currentPlayer.getCardsInHand().get(cardAddress - 1));
            gameView.printCardSelected();
        }
    }


    public void deselectCard() {
        if (currentPlayer.getSelectedCard() == null) {
            gameView.printNoCardSelected();
            return;
        }
        currentPlayer.setSelectedCard(null);
        gameView.printCardDeselected();
    }

    public void attack(int monsterNumber) {
        if (checkAttack(monsterNumber)) {
            Monster rivalMonster = (Monster) currentPlayer.getRivalPlayer()
                    .getBoard().getMonsterByAddress(monsterNumber);
            Monster ourMonster = (Monster) currentPlayer.getSelectedCard();
            if (rivalMonster.getAttackOrDefense() == AttackOrDefense.ATTACK) {
                int attackDifference =
                        Math.abs(rivalMonster.getAttackNum() - ourMonster.getAttackNum());
                if (ourMonster.getAttackNum() < rivalMonster.getAttackNum()) {
                    currentPlayer.getRivalPlayer().decreaseHealth(attackDifference);
                    currentPlayer.getRivalPlayer().getGraveyard().addCard(rivalMonster);

                }
            } else {

            }
        }
    }

    private boolean monsterSelectionCheck(int cardAddress, Player player) {
        if (cardAddress <= 0 || cardAddress > 5) {
            gameView.printInvalidSelection();
            return false;
        }
        if (player.getBoard().getMonsterByAddress(cardAddress) == null) {
            gameView.printNotFoundCard();
            return false;
        }
        return true;
    }

    private boolean spellSelectionCheck(int cardAddress, Player player) {
        if (cardAddress <= 0 || cardAddress > 4) {
            gameView.printInvalidSelection();
            return false;
        }
        if (player.getBoard().getSpellOrTrapByAddress(cardAddress) == null) {
            gameView.printNotFoundCard();
            return false;
        }
        return true;
    }

    private boolean fieldSpellCheck(Player player) {
        if (player.getBoard().getFieldSpell().getCard() == null) {
            gameView.printNotFoundCard();
            return false;
        }
        return true;
    }

    private boolean handCardCheck(int cardAddress) {
        if (cardAddress <= 0 || cardAddress > 6) {
            gameView.printInvalidSelection();
            return false;
        }
        if (currentPlayer.getCardsInHand().get(cardAddress - 1) == null) {
            gameView.printNotFoundCard();
            return false;
        }
        return true;
    }


    private boolean checkAttack(int rivalMonsterNum) {
        if (currentPlayer.getSelectedCard() == null) {
            gameView.printNoCardSelected();
            return false;
        }
        if (!currentPlayer.getBoard().isMonsterOnBoard(currentPlayer.getSelectedCard())) {
            gameView.printCantAttack();
            return false;
        }
        if (GameView.getCurrentPhase() != Phase.BATTLE_PHASE) {
            gameView.printWrongPhase();
            return false;
        }
        if (hasCardAttacked(currentPlayer.getSelectedCard())) {
            gameView.printAlreadyAttacked();
            return false;
        }
        if (currentPlayer.getRivalPlayer().getBoard()
                .getMonsterByAddress(rivalMonsterNum) == null) {
            gameView.printNoCardToAttack();
            return false;
        }
        return true;
    }

    private boolean hasCardAttacked(Card selectedCard) {  ////// this need to be completed , maybe a boolean???
        return true;
    }
}
