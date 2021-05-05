package controll;

import enums.AttackOrDefense;
import enums.Face;
import enums.MonsterCardType;
import enums.Phase;
import model.cards.Card;
import model.cards.Monster;
import model.players.Player;
import view.allmenu.GameView;

import java.util.ArrayList;

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
        if (checkAttack() && checkRivalMonster(monsterNumber)) {
            Monster rivalMonster = (Monster) currentPlayer.getRivalPlayer()
                    .getBoard().getMonsterByAddress(monsterNumber);
            Monster ourMonster = (Monster) currentPlayer.getSelectedCard();
            String rivalMonsterName = rivalMonster.getCardName();
            if (rivalMonster.getAttackOrDefense() == AttackOrDefense.ATTACK) {
                int attackDifference =
                        Math.abs(rivalMonster.getAttackNum() - ourMonster.getAttackNum());
                if (ourMonster.getAttackNum() < rivalMonster.getAttackNum()) {
                    currentPlayer.getRivalPlayer().decreaseHealth(attackDifference);
                    currentPlayer.getRivalPlayer().getGraveyard().addCard(rivalMonster);
                    currentPlayer.getRivalPlayer().getBoard().removeMonsterFromBoard(rivalMonster);
                    gameView.printOpponentMonsterDestroyed(attackDifference);
                } else if (ourMonster.getAttackNum() == rivalMonster.getAttackNum()) {
                    currentPlayer.getBoard().removeMonsterFromBoard(ourMonster);
                    currentPlayer.getRivalPlayer().getBoard().removeMonsterFromBoard(rivalMonster);
                    currentPlayer.getGraveyard().addCard(ourMonster);
                    currentPlayer.getRivalPlayer().getGraveyard().addCard(rivalMonster);
                    gameView.printBothMonstersDestroyed();
                } else {
                    currentPlayer.getBoard().removeMonsterFromBoard(ourMonster);
                    currentPlayer.decreaseHealth(attackDifference);
                    currentPlayer.getGraveyard().addCard(ourMonster);
                    gameView.printYourCardIsDestroyed(attackDifference);
                }
            } else {
                int attackDifference =
                        Math.abs(rivalMonster.getDefenseNum() - ourMonster.getAttackNum());
                if (ourMonster.getAttackNum() > rivalMonster.getDefenseNum()) {
                    currentPlayer.getRivalPlayer().getGraveyard().addCard(rivalMonster);
                    currentPlayer.getBoard().removeMonsterFromBoard(rivalMonster);
                    if (rivalMonster.getFace() == Face.UP)
                        gameView.printDefensePositionDestroyed();
                    else {
                        gameView.printDefensePositionDestroyedHidden(rivalMonsterName);  // if card was hidden theres another thing to show
                    }
                } else if (ourMonster.getAttackNum() == rivalMonster.getDefenseNum()) {
                    if (rivalMonster.getFace() == Face.UP)
                        gameView.printNoCardDestroyed();
                    else {                                                          // does card turn after being attacked????????????
                        gameView.printNoCardDestroyedHidden(rivalMonsterName);
                    }
                } else {
                    currentPlayer.decreaseHealth(attackDifference);
                    if (rivalMonster.getFace() == Face.UP)
                        gameView.printNoCardDestroyedYouReceivedDamage(attackDifference);
                    else {
                        gameView.printNoCardDestroyedYouReceivedDamageHidden(attackDifference, rivalMonsterName);

                    }
                }
            }
            rivalMonster.setFace(Face.UP);
        }
    }

    public void directAttack() {  // somehow same as attack only diff is rival card number!!!
        if (checkAttack()) {
            Monster ourMonster = (Monster) currentPlayer.getSelectedCard();
            currentPlayer.getRivalPlayer().decreaseHealth(ourMonster.getAttackNum());
            gameView.printYourOpponentReceivesDamage(ourMonster.getAttackNum());
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


    private boolean checkAttack() {
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

        return true;
    }

    private boolean checkRivalMonster(int rivalMonsterNum) {
        if (currentPlayer.getRivalPlayer().getBoard()
                .getMonsterByAddress(rivalMonsterNum) == null) {
            gameView.printNoCardToAttack();
            return false;
        }
        return true;
    }

    private boolean hasCardAttacked(Card selectedCard) {  ////// this need to be completed , maybe a boolean???////////////////////////////////////////////////////////////
        return true;
    }


    public void summon() { //need some change, SOME EFFECTIVE MONSTER CAN NOT NORMAL SUMMON!
        if (currentPlayer.getSelectedCard() == null)
            gameView.printNoCardSelected();
        else {
            if (currentPlayer.getCardsInHand().contains(currentPlayer.getSelectedCard())
                    && currentPlayer.getSelectedCard() instanceof Monster
                    && !((Monster) currentPlayer.getSelectedCard()).getMonsterType().equalsIgnoreCase("ritual")) {
                if (GameView.getCurrentPhase() != Phase.MAIN_PHASE_1
                        && GameView.getCurrentPhase() != Phase.MAIN_PHASE_2)
                    gameView.printNotInMainPhase();
                else {
                    if (currentPlayer.getBoard().isThereEmptyPlaceMonsterZone()) {
                        if (currentPlayer.isSetOrSummonInThisTurn())
                            gameView.printAlreadySetOrSummon();
                        else
                            summonAndSpecifyTribute();
                    } else
                        gameView.printMonsterZoneFull();
                }
            } else
                gameView.printCantSummon();

        }
    }

    private void summonAndSpecifyTribute() {
        Monster monster = (Monster) currentPlayer.getSelectedCard();
        int numberOfTribute = monster.howManyTributeNeed();
        if (numberOfTribute == 0) {
            gameView.printSummonSuccessfully();
        } else
            getTribute(numberOfTribute);
        monster.setSetInThisTurn(true);
        currentPlayer.setSetOrSummonInThisTurn(true);
        currentPlayer.setSelectedCard(null);
    }

    private void getTribute(int numberOfTribute) { /// need send tribute to graveyard.
        ArrayList<Monster> tributeMonster = new ArrayList<>();
        int numberOfMonsterInOurBoard = currentPlayer.getBoard().getNumberOfMonsterInBoard();
        if (numberOfMonsterInOurBoard < numberOfTribute)
            gameView.printThereArentEnoughMonsterForTribute();
        else {
            for (int i = 0; i < numberOfTribute; i++) {
                gameView.getTribute();
                if (currentPlayer.getSelectedCard() instanceof Monster)
                    tributeMonster.add((Monster) currentPlayer.getSelectedCard());
                if (i == numberOfTribute - 1) {
                    if (numberOfTribute == 1 && tributeMonster.size() == 0) {
                        i--;
                        gameView.printNoMonsterOnThisAddress();
                    }
                    if (numberOfTribute == 2 && tributeMonster.size() < 2) {
                        gameView.printNoMonsterInOneOfThisAddress();
                        if (tributeMonster.size() == 1)
                            i--;
                        else
                            i -= 2;
                    }
                }
            }
            for (Monster monster : tributeMonster) {
                currentPlayer.getGraveyard().addCard(monster);
            }
            gameView.printSummonSuccessfully();
        }
    }

}
