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
    private static int playedRounds = 0;
    private final GameView gameView;
    private final Player firstPlayer;
    private final Player secondPlayer;
    private Player currentPlayer;
    private Phase currentPhase;
    private final int startingRounds;
    private int turnsPlayed;


    public GameController(GameView gameView, Player firstPlayer, Player secondPlayer, Player currentPlayer, int startingRounds) {
        this.gameView = gameView;
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.currentPlayer = currentPlayer;
        firstPlayer.getBoard().getDeck().shuffleMainDeck();
        secondPlayer.getBoard().getDeck().shuffleMainDeck();
        for (int i = 0; i < 6; i++) {
            firstPlayer.addCardToHand();
            secondPlayer.addCardToHand();
        }
        this.startingRounds = startingRounds;
        turnsPlayed = 0;
        currentPhase = Phase.DRAW_PHASE;
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
        if (monsterSelectionCheck(cardAddress, getRivalPlayer())) {
            currentPlayer.setSelectedCard
                    (getRivalPlayer().getBoard().getMonsterByAddress(cardAddress));
            gameView.printCardSelected();
        }
    }


    public void selectOpponentSpellOrTrap(int cardAddress) {
        if (spellSelectionCheck(cardAddress, getRivalPlayer())) {
            currentPlayer.setSelectedCard
                    (getRivalPlayer().getBoard().getSpellOrTrapByAddress(cardAddress));
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
        if (fieldSpellCheck(getRivalPlayer())) {
            currentPlayer.setSelectedCard
                    (getRivalPlayer().getBoard().getFieldSpell().getCard());
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
            Monster rivalMonster = (Monster) getRivalPlayer()
                    .getBoard().getMonsterByAddress(monsterNumber);
            Monster ourMonster = (Monster) currentPlayer.getSelectedCard();
            if (isSpecialAttack(ourMonster, rivalMonster))
                return;
            String rivalMonsterName = rivalMonster.getCardName();
            ourMonster.setAttackedInThisTurn(true);
            if (rivalMonster.getAttackOrDefense() == AttackOrDefense.ATTACK) {
                int attackDifference =
                        Math.abs(rivalMonster.getAttackNum() - ourMonster.getAttackNum());
                if (ourMonster.getAttackNum() < rivalMonster.getAttackNum()) {
                    getRivalPlayer().decreaseHealth(attackDifference);
                    getRivalPlayer().getBoard().getGraveyard().addCard(rivalMonster);
                    gameView.printOpponentMonsterDestroyed(attackDifference);
                } else if (ourMonster.getAttackNum() == rivalMonster.getAttackNum()) {
                    currentPlayer.getBoard().getGraveyard().addCard(ourMonster);
                    getRivalPlayer().getBoard().getGraveyard().addCard(rivalMonster);
                    gameView.printBothMonstersDestroyed();
                } else {
                    currentPlayer.decreaseHealth(attackDifference);
                    currentPlayer.getBoard().getGraveyard().addCard(ourMonster);
                    gameView.printYourCardIsDestroyed(attackDifference);
                }
            } else {
                int attackDifference =
                        Math.abs(rivalMonster.getDefenseNum() - ourMonster.getAttackNum());
                if (ourMonster.getAttackNum() > rivalMonster.getDefenseNum()) {
                    getRivalPlayer().getBoard().getGraveyard().addCard(rivalMonster);
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

    private boolean isSpecialAttack(Monster ourMonster, Monster rivalMonster) {
        if (rivalMonster.getCardName().equals("Marshmallon")) {
            marshmallon(ourMonster, rivalMonster);
            return true;
        } else if (true) {

        }
        return false;
    }

    private void marshmallon(Monster ourMonster, Monster rivalMonster) {
        if (rivalMonster.getAttackOrDefense() == AttackOrDefense.ATTACK) {
            int attackDiff = ourMonster.getAttackNum() - rivalMonster.getAttackNum();
            if (attackDiff > 0) {
                rivalMonster.getCardOwner().decreaseHealth(attackDiff);
            } else if (attackDiff == 0) {
                rivalMonster.getCardOwner().getBoard().getGraveyard().addCard(rivalMonster);
            } else {
                ourMonster.getCardOwner().decreaseHealth(-attackDiff);
            }
        } else {
            int attackDiff = ourMonster.getAttackNum() - rivalMonster.getDefenseNum();
            if (attackDiff > 0) {
                if (rivalMonster.getFace() == Face.DOWN)
                    gameView.printOpponentCardsName("Marshmallon");
            } else if (attackDiff == 0) {
                if (rivalMonster.getFace() == Face.DOWN)
                    gameView.printOpponentCardsName("Marshmallon");

            } else {
                if (rivalMonster.getFace() == Face.DOWN)
                    gameView.printOpponentCardsName("Marshmallon");
                ourMonster.getCardOwner().decreaseHealth(-attackDiff);
            }
            rivalMonster.setFace(Face.UP);
            ourMonster.getCardOwner().decreaseHealth(1000);
        }
    }

    public void directAttack() {  // somehow same as attack only diff is rival card number!!!
        if (checkAttack()) {
            Monster ourMonster = (Monster) currentPlayer.getSelectedCard();
            getRivalPlayer().decreaseHealth(ourMonster.getAttackNum());
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
        if (currentPhase != Phase.BATTLE_PHASE) {
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
        if (getRivalPlayer().getBoard()
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

    public void nextPhase() {//          وقتی میبینی این هنوز کامل نشده!!! :D
        if (currentPhase == Phase.END_PHASE) {
            currentPhase = Phase.DRAW_PHASE;
            gameView.printCurrentPhase();
            turnsPlayed++;
            if (turnsPlayed == 0 || turnsPlayed == 1) {
                if (currentPlayer.getBoard().getDeck().getAllCardsInMainDeck().size() != 0)
                    currentPlayer.addCardToHand();
                else {

                }
            }
        } else if (currentPhase == Phase.DRAW_PHASE) {
            currentPhase = Phase.STANDBY_PHASE;
            gameView.printCurrentPhase();
            //do some Effects!!!

        } else if (currentPhase == Phase.STANDBY_PHASE) {
            currentPhase = Phase.MAIN_PHASE_1;
            gameView.printCurrentPhase();
            gameView.printMap();//to complete
        } else if (currentPhase == Phase.MAIN_PHASE_1) {
            currentPhase = Phase.BATTLE_PHASE;
            gameView.printCurrentPhase();//to complete
        } else if (currentPhase == Phase.BATTLE_PHASE) {
            currentPhase = Phase.MAIN_PHASE_2;
            gameView.printCurrentPhase();
            //to comp
        } else {
            currentPhase = Phase.END_PHASE;
            currentPlayer.setSetOrSummonInThisTurn(false);
            changeCurrentPlayer();
            gameView.printCurrentPhase();
            gameView.printWhoseTurn();
        }
    }

    public void summon() { //need some change, SOME EFFECTIVE MONSTER CAN NOT NORMAL SUMMON!
        if (currentPlayer.getSelectedCard() == null)
            gameView.printNoCardSelected();
        else {
            if (currentPlayer.getCardsInHand().contains(currentPlayer.getSelectedCard())
                    && currentPlayer.getSelectedCard() instanceof Monster
                    && !((Monster) currentPlayer.getSelectedCard()).getMonsterType().equalsIgnoreCase("ritual")) {
                if (currentPhase != Phase.MAIN_PHASE_1 && currentPhase != Phase.MAIN_PHASE_2)
                    gameView.printNotInMainPhase();
                else {
                    if (currentPlayer.getBoard().isThereEmptyPlaceMonsterZone()) {
                        if (currentPlayer.isSetOrSummonInThisTurn())
                            gameView.printAlreadySetOrSummon();
                        else
                            summonAndSpecifyTribute();
                    } else gameView.printMonsterZoneFull();
                }
            } else gameView.printCantSummon();
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
        currentPlayer.setSetOrSummonInThisTurn(true);
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
            currentPlayer.getBoard().getGraveyard().addCard(monster);
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
        if (currentPhase == Phase.MAIN_PHASE_1 || currentPhase == Phase.MAIN_PHASE_2) {
            if (currentPlayer.getBoard().isThereEmptyPlaceMonsterZone()) {
                if (currentPlayer.isSetOrSummonInThisTurn())
                    gameView.printAlreadySetOrSummon();
                else {
                    selectedCard.setZone(Zone.MONSTER_ZONE);
                    selectedCard.setFace(Face.DOWN);
                    selectedCard.setAttackOrDefense(AttackOrDefense.DEFENSE);
                    currentPlayer.setSetOrSummonInThisTurn(true);
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
                if (currentPhase == Phase.MAIN_PHASE_1 || currentPhase == Phase.MAIN_PHASE_2) {
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
                if (getCurrentPhase() == Phase.MAIN_PHASE_1 || getCurrentPhase() == Phase.MAIN_PHASE_2) {
                    Monster monster = (Monster) currentPlayer.getSelectedCard();
                    if (monster.isSetInThisTurn() ||
                            !(monster.getFace() == Face.DOWN && monster.getAttackOrDefense() == AttackOrDefense.DEFENSE))
                        gameView.printCantFlipSummon();
                    else {
                        currentPlayer.setSelectedCard(null);
                        monster.setFace(Face.UP);
                        monster.setAttackOrDefense(AttackOrDefense.ATTACK);
                    }
                } else
                    gameView.printNotInMainPhase();
            } else
                gameView.printCantChangePosition();
        }
    }

    public void showSelectedCard() {
        if (currentPlayer.getSelectedCard() == null)
            gameView.printNoCardSelected();
        else {
            Card card = currentPlayer.getSelectedCard();
            if (card.getPlayer() == getRivalPlayer() && card.getFace() == Face.DOWN)
                gameView.printCardInvisible();
            else gameView.showCard(card);
        }
    }


    public void changeCurrentPlayer() {
        if (currentPlayer == firstPlayer)
            currentPlayer = secondPlayer;
        else
            currentPlayer = firstPlayer;
    }

    public Player getCurrentPlayer() {
        if (currentPlayer == firstPlayer)
            return firstPlayer;
        return secondPlayer;
    }

    public Player getRivalPlayer() {
        if (currentPlayer == firstPlayer)
            return secondPlayer;
        return firstPlayer;
    }

    private void manEaterBugFlipSummon() {
        while (currentPlayer.getSelectedCard() == null) {
            gameView.getOpponentMonsterForKill();
        }
        Monster opponentMonster = (Monster) currentPlayer.getSelectedCard();
        getRivalPlayer().getBoard().getGraveyard().addCard(opponentMonster);
    }


    public Phase getCurrentPhase() {
        return currentPhase;
    }

    public void findWinner() {
        if (checkEnded() != 4) {
            // to complete
        }
    }

    private int checkEnded() {
        if (currentPlayer.getLifePoint() == 0 && getRivalPlayer().getLifePoint() == 0)
            return 3;//draw
        else if (currentPlayer.getLifePoint() != 0)
            return 2;//rival won
        else if (getRivalPlayer().getLifePoint() != 0)
            return 1;//currentPlayer has won
        else {
            if (currentPhase == Phase.DRAW_PHASE && currentPlayer.getBoard().getDeck().getAllCardsInMainDeck().size() == 0)
                return 2;
            else
                return 4;//no winner
        }
    }


    public static boolean checkForDeathAction(Card card) {
        Monster monster = (Monster) card;
        if (card.getCardName().equals("Yomi Ship"))
            yomiShip(monster);
        else if (card.getCardName().equals("Texchanger"))
            texchanger(monster);
        else if (card.getCardName().equals("Exploder Dragon"))
            exploderDragon(monster);
        return false;
    }


    public static void yomiShip(Monster monster) {
        monster
                .getAttacker()
                .getCardOwner()
                .getBoard()
                .getGraveyard()
                .addCard
                        (monster.getAttacker());
    }

    private static void exploderDragon(Monster monster) {
    }

    private static void texchanger(Monster monster) {
    }


}
