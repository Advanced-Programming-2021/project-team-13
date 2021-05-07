package controll;

import enums.AttackOrDefense;
import enums.Face;
import enums.Phase;
import enums.Zone;
import model.Cell;
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
        currentPlayer = gameView.getCurrentPlayer();

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
            ourMonster.setAttackedInThisTurn(true);
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
        if (gameView.getCurrentPhase() != Phase.BATTLE_PHASE) {
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
        Monster monster = (Monster) selectedCard;
        return monster.isAttackedInThisTurn();
    }


    public void summon() { //need some change, SOME EFFECTIVE MONSTER CAN NOT NORMAL SUMMON!
        if (currentPlayer.getSelectedCard() == null)
            gameView.printNoCardSelected();
        else {
            if (currentPlayer.getCardsInHand().contains(currentPlayer.getSelectedCard())
                    && currentPlayer.getSelectedCard() instanceof Monster
                    && !((Monster) currentPlayer.getSelectedCard()).getMonsterType().equalsIgnoreCase("ritual")) {
                if (gameView.getCurrentPhase() != Phase.MAIN_PHASE_1
                        && gameView.getCurrentPhase() != Phase.MAIN_PHASE_2)
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

    private void commandKnightSummon(Monster monster) {
        monster.setAttackable(false);   // we should use this in attack function!!!!
        ArrayList<Monster> monsters = new ArrayList<>();
        for (Cell cell : currentPlayer.getBoard().getMonsters()) {
            Monster friendMonster = (Monster) cell.getCard();
            friendMonster.setAttackPointInGame(friendMonster.getAttackPointInGame() + 400); //we should decrease this in Attack function After Death.
            monsters.add(friendMonster);
        }
        monster.setMonsters(monsters);
    }

    private void summonAndSpecifyTribute() {
        Monster monster = (Monster) currentPlayer.getSelectedCard();
        if (currentPlayer.getSelectedCard().getCardName().equalsIgnoreCase("Command knight"))
            commandKnightSummon(monster);
        else {
            int numberOfTribute = monster.howManyTributeNeed();
            if (numberOfTribute != 0) {
                int numberOfMonsterInOurBoard = currentPlayer.getBoard().getNumberOfMonsterInBoard();
                if (numberOfMonsterInOurBoard < numberOfTribute) {
                    currentPlayer.setSelectedCard(null);
                    gameView.printThereArentEnoughMonsterForTribute();
                    return;
                }
                if (getTribute(numberOfTribute))
                    return;
            }
        }
        gameView.printSummonSuccessfully();
        monster.setSetInThisTurn(true);
        monster.setZone(Zone.MONSTER_ZONE);
        monster.setFace(Face.UP);
        monster.setAttackOrDefense(AttackOrDefense.ATTACK);
        currentPlayer.setSetOrNormalSummonInThisTurn(true);
        currentPlayer.setSelectedCard(null);
    }

    private boolean getTribute(int numberOfTribute) {
        ArrayList<Monster> tributeMonster = new ArrayList<>();
        for (int i = 0; i < numberOfTribute; i++) {
            gameView.getTribute();
            Monster monster = (Monster) currentPlayer.getSelectedCard();
            if (currentPlayer.getSelectedCard() instanceof Monster
                    && !tributeMonster.contains(monster))
                tributeMonster.add(monster);
        }
        if (tributeMonster.size() < numberOfTribute) {
            if (numberOfTribute == 1)
                gameView.printNoMonsterOnThisAddress();
            else
                gameView.printNoMonsterInOneOfThisAddress();
            return true;
        }
        for (Monster monster : tributeMonster) {
            currentPlayer.getGraveyard().addCard(monster);
        }
        return false;
    }

    public void set() {
        if (currentPlayer.getSelectedCard() == null)
            gameView.printNoCardSelected();
        else {
            if (currentPlayer.getSelectedCard().getZone() == Zone.IN_HAND) {
                if (currentPlayer.getSelectedCard() instanceof Monster)
                    setMonster((Monster) currentPlayer.getSelectedCard());
                else
                    setSpellAndTrap();
            } else
                gameView.printCantSet();
        }
    }

    private void setSpellAndTrap() {
    }

    private void setMonster(Monster selectedCard) {
        if (gameView.getCurrentPhase() == Phase.MAIN_PHASE_1 || gameView.getCurrentPhase() == Phase.MAIN_PHASE_2) {
            if (currentPlayer.getBoard().isThereEmptyPlaceMonsterZone()) {
                if (currentPlayer.isSetOrSummonInThisTurn())
                    gameView.printAlreadySetOrSummon();
                else {
                    selectedCard.setZone(Zone.MONSTER_ZONE);
                    selectedCard.setFace(Face.DOWN);
                    selectedCard.setAttackOrDefense(AttackOrDefense.DEFENSE);
                    currentPlayer.setSetOrNormalSummonInThisTurn(true);
                    currentPlayer.setSelectedCard(null);
                    gameView.printSetSuccessfully();
                }
            } else
                gameView.printMonsterZoneFull();
        } else
            gameView.printNotInMainPhase();
    }

    public void changeSet(String position) {
        if (currentPlayer.getSelectedCard() == null)
            gameView.printNoCardSelected();
        else {
            if (currentPlayer.getSelectedCard() instanceof Monster) {
                if (gameView.getCurrentPhase() == Phase.MAIN_PHASE_1 || gameView.getCurrentPhase() == Phase.MAIN_PHASE_2) {
                    Monster monster = (Monster) currentPlayer.getSelectedCard();
                    if (monster.getFace() == Face.UP && monster.getAttackOrDefense() == AttackOrDefense.ATTACK
                            && position.equals("attack"))
                        gameView.printAlreadyInWantedPosition();
                    else if ((monster.getFace() == Face.UP && monster.getAttackOrDefense() == AttackOrDefense.DEFENSE
                            && position.equals("defense")))
                        gameView.printAlreadyInWantedPosition();
                    else {
                        if (monster.getHaveChangePositionInThisTurn())
                            gameView.printAlreadyChangePositionInThisTurn();
                        else {
                            monster.setHaveChangePositionInThisTurn(true);
                            if (position.equals("attack")) monster.setAttackOrDefense(AttackOrDefense.ATTACK);
                            else monster.setAttackOrDefense(AttackOrDefense.DEFENSE);
                            gameView.printChangeSetSuccessfully();
                            currentPlayer.setSelectedCard(null);
                        }
                    }

                } else
                    gameView.printNotInMainPhase();
            } else
                gameView.printCantChangePosition();
        }
    }

    public void flipSummon() {
        if (currentPlayer.getSelectedCard() == null)
            gameView.printNoCardSelected();
        else {
            if (currentPlayer.getSelectedCard().getZone() == Zone.MONSTER_ZONE) {
                if (gameView.getCurrentPhase() == Phase.MAIN_PHASE_1 || gameView.getCurrentPhase() == Phase.MAIN_PHASE_2) {
                    Monster monster = (Monster) currentPlayer.getSelectedCard();
                    if (monster.isSetInThisTurn() ||
                            !(monster.getFace() == Face.DOWN && monster.getAttackOrDefense() == AttackOrDefense.DEFENSE))
                        gameView.printCantFlipSummon();
                    else {
                        currentPlayer.setSelectedCard(null);
                        if (monster.getCardName().equalsIgnoreCase("command knight"))
                            commandKnightSummon(monster);
                        else if (monster.getCardName().equalsIgnoreCase("man-eater bug"))
                            manEaterBugFlipSummon();
                        monster.setFace(Face.UP);
                        monster.setAttackOrDefense(AttackOrDefense.ATTACK);
                        gameView.printFlipSummonSuccessfully();
                    }
                } else
                    gameView.printNotInMainPhase();
            } else
                gameView.printCantChangePosition();
        }
    }

    private void manEaterBugFlipSummon() {
        while (currentPlayer.getSelectedCard() == null) {
            gameView.getOpponentMonsterForKill();
        }
        Monster opponentMonster = (Monster) currentPlayer.getSelectedCard();
        currentPlayer.getRivalPlayer().getGraveyard().addCard(opponentMonster);
    }

    public void checkEnded() {

    }
}
