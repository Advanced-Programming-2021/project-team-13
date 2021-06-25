package controll.gameController;

import enums.*;
import model.Cell;
import model.cards.Card;
import model.cards.Monster;
import model.cards.Spell;
import model.cards.Trap;
import model.players.AIPlayer;
import model.players.Player;
import view.ViewMaster;
import view.allmenu.GameView;
import view.allmenu.ShowGraveyardView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class GameController {
    private final GameView gameView;
    private final Player firstPlayer;
    private final Player secondPlayer;
    private final Player startingPlayer;
    private final ArrayList<Integer> notToDrawCardTurns;
    private final ArrayList<Trap> chain;
    private Card attackingCard;
    private Card summonedCard;
    private Player currentPlayer;
    private Phase currentPhase;
    private final int startingRounds;
    private int turnsPlayed;
    private int unitedCalcCurrent;
    private int unitedCalcRival;
    private int differenceInAtkPoints=0;
    private boolean canContinue;
    private boolean isInAttack;
    private boolean normalSummonHappened;
    private boolean specialSummonHappened;
    private boolean flipSummonHappened;
    private boolean ritualSummonHappened;
    private boolean anySummonHappened;


    public static boolean checkForDeathAction(Card card) {
        if (card instanceof Monster) {
            Monster monster = (Monster) card;
            if (card.getCardName().equalsIgnoreCase("Yomi Ship"))
                yomiShip(monster);
            if (card.getCardName().equalsIgnoreCase("Exploder Dragon"))
                if (monster.getAttacker() != null)
                    monster.getAttacker().getCardOwner().getBoard().getGraveyard().addCard(monster.getAttacker());
        }
        return false;
    }

    public static void yomiShip(Monster monster) {
        if (monster.getAttacker() != null)
            monster.getAttacker().getCardOwner().getBoard().getGraveyard().addCard(monster.getAttacker());
    }


    ///////////////////////////////////////////////////we need activation of spells and traps to work;;;;;;DAMN!//////////////////////////++messenger++some field spells++
    public GameController(GameView gameView, Player firstPlayer, Player secondPlayer, Player startingPlayer, int startingRounds) {
        this.gameView = gameView;
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.startingPlayer = startingPlayer;
        this.currentPlayer = startingPlayer;
        this.chain = new ArrayList<>();
//        firstPlayer.getBoard().getDeck().shuffleMainDeck();
//        secondPlayer.getBoard().getDeck().shuffleMainDeck();
        for (Card allCard : firstPlayer.getBoard().getDeck().getAllCards()) {
            allCard.setCardOwner(firstPlayer);
        }
        for (Card allCard : secondPlayer.getBoard().getDeck().getAllCards()) {
            allCard.setCardOwner(secondPlayer);
        }
        for (int i = 0; i < 6; i++) {
            firstPlayer.addCardToHand();
            secondPlayer.addCardToHand();
        }
        this.startingRounds = startingRounds;
        turnsPlayed = 0;
        currentPhase = Phase.DRAW_PHASE;
        notToDrawCardTurns = new ArrayList<>();
        canContinue = true;
        isInAttack = false;
        normalSummonHappened = false;
        specialSummonHappened = false;
        ritualSummonHappened = false;
        anySummonHappened = false;
    }

    public boolean isAnySummonHappened() {
        return anySummonHappened;
    }

    public boolean isNormalSummonHappened() {
        return normalSummonHappened;
    }

    public boolean isRitualSummonHappened() {
        return ritualSummonHappened;
    }

    public boolean isSpecialSummonHappened() {
        return specialSummonHappened;
    }

    public boolean isFlipSummonHappened() {
        return flipSummonHappened;
    }

    public boolean isInAttack() {
        return isInAttack;
    }

    public void setCurrentPhase(Phase currentPhase) {
        this.currentPhase = currentPhase;
    }

    public void addNotToDrawCardTurn(int turn) {
        notToDrawCardTurns.add(turn);
    }

    public ArrayList<Integer> getNotToDrawCardTurns() {
        return notToDrawCardTurns;
    }

    public Player getCurrentPlayer() {
        if (currentPlayer == firstPlayer)
            return firstPlayer;
        return secondPlayer;
    }

    public Card getSummonedCard() {
        return summonedCard;
    }

    public Card getAttackingCard() {
        return attackingCard;
    }

    public void setCanContinue(boolean canContinue) {
        this.canContinue = canContinue;
    }

    public boolean getCanContinue() {
        return canContinue;
    }

    public Player getStartingPlayer() {
        return startingPlayer;
    }

    public Player getFirstPlayer() {
        return firstPlayer;
    }

    public Player getSecondPlayer() {
        return secondPlayer;
    }

    public GameView getGameView() {
        return gameView;
    }

    public int getStartingRounds() {
        return startingRounds;
    }

    public int getTurnsPlayed() {
        return turnsPlayed;
    }

    public void changeCurrentPlayer() {
        if (currentPlayer == firstPlayer)
            currentPlayer = secondPlayer;
        else
            currentPlayer = firstPlayer;
        gameView.playerChanged(currentPlayer);
    }

    public void selectPlayerMonster(int cardAddress) {
        if (monsterSelectionCheck(cardAddress, currentPlayer)) {
            currentPlayer.setSelectedCard(currentPlayer.getBoard().getMonsterByAddress(cardAddress));
            gameView.printCardSelected();
            showSelectedCard();
        }
    }

    public void selectPlayerSpellOrTrap(int cardAddress) {
        if (spellSelectionCheck(cardAddress, currentPlayer)) {
            currentPlayer.setSelectedCard(currentPlayer.getBoard().getSpellOrTrapByAddress(cardAddress));
            gameView.printCardSelected();
            showSelectedCard();
        }
    }

    public void selectOpponentMonster(int cardAddress) {
        if (monsterSelectionCheck(cardAddress, currentPlayer.getRivalPlayer())) {
            currentPlayer.setSelectedCard
                    (currentPlayer.getRivalPlayer().getBoard().getMonsterByAddress(cardAddress));
            gameView.printCardSelected();
            showSelectedCard();
        }
    }

    public void selectOpponentSpellOrTrap(int cardAddress) {
        if (spellSelectionCheck(cardAddress, currentPlayer.getRivalPlayer())) {
            currentPlayer.setSelectedCard
                    (currentPlayer.getRivalPlayer().getBoard().getSpellOrTrapByAddress(cardAddress));
            gameView.printCardSelected();
            showSelectedCard();
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
            showSelectedCard();
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

    public void showSelectedCard() {
        if (currentPlayer.getSelectedCard() == null)
            gameView.printNoCardSelected();
        else {
            Card card = currentPlayer.getSelectedCard();
            if (card.getCardOwner() == currentPlayer.getRivalPlayer() && card.getFace() == Face.DOWN)
                gameView.printCardInvisible();
            else gameView.showCard(card);
        }
    }

    public void findWinner() {
        if (checkEnded() != 4) {
            GameWinMenu gameWinMenu = new GameWinMenu(this);
            if (checkEnded() == 3) {
                gameWinMenu.announceWinner(null);
            } else if (checkEnded() == 2) {
                gameWinMenu.announceWinner(currentPlayer.getRivalPlayer());
            } else {
                gameWinMenu.announceWinner(currentPlayer);
            }
        }
    }

    private int checkEnded() {
        if (currentPlayer.getLifePoint() <= 0 && currentPlayer.getRivalPlayer().getLifePoint() <= 0) {
            currentPlayer.setLifePoint(0);
            currentPlayer.getRivalPlayer().setLifePoint(0);
            return 3;//draw
        } else if (currentPlayer.getLifePoint() <= 0) {
            currentPlayer.setLifePoint(0);
            return 2;//rival won
        } else if (currentPlayer.getRivalPlayer().getLifePoint() <= 0) {
            currentPlayer.getRivalPlayer().setLifePoint(0);
            return 1;//currentPlayer has won
        } else {
            if (currentPhase == Phase.DRAW_PHASE && currentPlayer.getBoard().getDeck().getAllCardsInMainDeck().size() == 0)
                return 2;//rival won
            else
                return 4;//no winner
        }
    }

    public void surrender() {
        if (startingRounds == 1)
            currentPlayer.getRivalPlayer().setWonRounds(1);
        else
            currentPlayer.getRivalPlayer().setWonRounds(2);
        if (currentPlayer.getLifePoint() > currentPlayer.getMaxLifePoint())
            currentPlayer.setMaxLifePoint(currentPlayer.getMaxLifePoint());
        if (currentPlayer.getRivalPlayer().getLifePoint() > currentPlayer.getRivalPlayer().getMaxLifePoint())
            currentPlayer.getRivalPlayer().setMaxLifePoint(currentPlayer.getRivalPlayer().getLifePoint());
        new GameWinMenu(this).announceWinner(currentPlayer.getRivalPlayer());
    }

    public void attack(int monsterNumber) {
        if (!checkMonsterByMonster(monsterNumber)) {
            return;
        }
        Monster rivalMonster = (Monster) currentPlayer.getRivalPlayer()
                .getBoard().getMonsterByAddress(monsterNumber);
        Monster ourMonster = (Monster) currentPlayer.getSelectedCard();
        isInAttack = true;
        ourMonster.setAttackedMonster(rivalMonster);
        rivalMonster.setAttacker(ourMonster);
        ourMonster.setAttackedInThisTurn(true);
        rivalMonster.setHasBeenAttacked(true);//// lioghhhhhhhhhhhhhhhhhhh
        attackingCard = ourMonster;
        checkTrapActivation(); //check for trap activation
        if (canContinue) {
            activateSpecial(ourMonster, rivalMonster);
//            if (!rivalMonster.isAttackable()) {
//                gameView.printCantAttackMonster();
//                return;
//            }
            monsterByMonsterAttack(rivalMonster, ourMonster);
        }
        attackingCard = null;
        ourMonster.setAttackedMonster(null);
        canContinue = true;
        isInAttack = false;
        currentPlayer.setSelectedCard(null);
        checkTrapActivation();
    }

    private void monsterByMonsterAttack(Monster beenAttackedMonster, Monster attackingMonster) {
        if (isSpecialAttack(attackingMonster, beenAttackedMonster))
            return;
        String rivalMonsterName = beenAttackedMonster.getCardName();
        if (beenAttackedMonster.getAttackOrDefense() == AttackOrDefense.ATTACK)
            rivalsOnAttack(beenAttackedMonster, attackingMonster);
        else rivalsOnDefense(beenAttackedMonster, attackingMonster, rivalMonsterName);
        gameView.printMap();
        if (beenAttackedMonster.getCardName().equalsIgnoreCase("Suijin"))/////////////////////// fishyyyyyyyyyyyyy
            attackingMonster.setAttackPointInGame(attackingMonster.getAttackNum());
        equipSpellRid();
        fieldSpellRid(attackingMonster, beenAttackedMonster);
    }


    private boolean isSpecialAttack(Monster ourMonster, Monster rivalMonster) {
        if (ourMonster.getCardName().equals("The Calculator")
                || rivalMonster.getCardName().equalsIgnoreCase("The Calculator")) {
            theCalculator(ourMonster);
            theCalculator(rivalMonster);
            gameView.printMap();
        }
        if (rivalMonster.getCardName().equalsIgnoreCase("Suijin")) {
            if (!rivalMonster.isActiveAbility()) {// rival = suijin butttttttt not active ///// not activated yet
                rivalMonster.setActiveAbility(true);
                ourMonster.setAttackPointInGame(0);
            }
        }
        if (messengerOfPeace(ourMonster)) {
            gameView.printCantAttackBecauseOfMessenger();
            gameView.printMap();
            return true;
        } else if (rivalMonster.getCardName().equalsIgnoreCase("Marshmallon")) {
            marshmallon(ourMonster, rivalMonster);
            gameView.printMap();
            return true;
        }
        return false;
    }

    private void theCalculator(Monster ourMonster) {
        int attackNumOfCalculator = 0;
        for (Cell monster : ourMonster.getCardOwner().getBoard().getMonsters()) {
            Monster monsterCard = (Monster) monster.getCard();
            if (monsterCard != null) {
                if (monsterCard.getFace() == Face.UP && monsterCard != ourMonster)
                    attackNumOfCalculator += monsterCard.getLevel();
            }
        }
        ourMonster.setAttackPointInGame(300 * attackNumOfCalculator+ourMonster.getAttackPointInGame());
    }

    private void marshmallon(Monster ourMonster, Monster rivalMonster) {
        if (rivalMonster.getAttackOrDefense() == AttackOrDefense.ATTACK)
            marshOnAttack(ourMonster, rivalMonster);
        else marshOnDef(ourMonster, rivalMonster);
    }

    private void marshOnAttack(Monster ourMonster, Monster rivalMonster) {
        int attackDiff = ourMonster.getAttackPointInGame() - rivalMonster.getAttackPointInGame();
        if (attackDiff > 0) {
            rivalMonster.getCardOwner().decreaseHealth(attackDiff);
            gameView.printNoCardDestroyedRivalReceivedDamage(attackDiff);
        } else if (attackDiff == 0) {
            if (!ourMonster.getCardName().equals("Marshmallon"))
                ourMonster.getCardOwner().getBoard().getGraveyard().addCard(ourMonster);
            gameView.printNoCardDestroyed();
        } else {
            if (!ourMonster.getCardName().equals("Marshmallon"))
                ourMonster.getCardOwner().getBoard().getGraveyard().addCard(ourMonster);
            ourMonster.getCardOwner().decreaseHealth(-attackDiff);
            gameView.printYourCardIsDestroyed(-attackDiff);
        }
    }

    private void marshOnDef(Monster ourMonster, Monster rivalMonster) {
        int attackDiff = ourMonster.getAttackPointInGame() - rivalMonster.getDefencePointInGame();
        if (rivalMonster.getFace() == Face.DOWN)
            gameView.printOpponentCardsName("Marshmallon");
        if (attackDiff > 0) {
            rivalMonster.getCardOwner().decreaseHealth(attackDiff);
            gameView.printNoCardDestroyedRivalReceivedDamage(attackDiff);
        } else if (attackDiff == 0) {
            gameView.printNoCardDestroyed();
        } else {
            ourMonster.getCardOwner().decreaseHealth(-attackDiff);
            gameView.printNoCardDestroyedYouReceivedDamage(-attackDiff);
        }
        if (rivalMonster.getFace() == Face.DOWN) {
            ourMonster.getCardOwner().decreaseHealth(1000);
            gameView.printYouReceivedDamage(1000);
        }
        rivalMonster.setFace(Face.UP);
    }


    private void rivalsOnAttack(Monster beenAttackedMonster, Monster attackingMonster) {
        int attackDifference = attackingMonster.getAttackPointInGame() - beenAttackedMonster.getAttackPointInGame();
        if (attackingMonster.getCardName().equals("Exploder Dragon") && attackDifference <= 0
                || beenAttackedMonster.getCardName().equals("Exploder Dragon") && attackDifference > 0)
            attackDifference = 0;
        if (attackDifference > 0) {
            currentPlayer.getRivalPlayer().decreaseHealth(attackDifference);
            currentPlayer.getRivalPlayer().getBoard().getGraveyard().addCard(beenAttackedMonster);
            gameView.printOpponentMonsterDestroyed(attackDifference);
        } else if (attackDifference == 0) {
            if (!attackingMonster.getCardName().equals("Marshmallon")) {
                currentPlayer.getBoard().getGraveyard().addCard(attackingMonster);
                gameView.printBothMonstersDestroyed();
            }
            currentPlayer.getRivalPlayer().getBoard().getGraveyard().addCard(beenAttackedMonster);
            if (attackingMonster.getCardName().equals("Marshmallon"))
                gameView.printOpponentMonsterDestroyed(attackDifference);
        } else {
            currentPlayer.decreaseHealth(-attackDifference);
            if (!attackingMonster.getCardName().equals("Marshmallon")) {
                currentPlayer.getBoard().getGraveyard().addCard(attackingMonster);
                gameView.printYourCardIsDestroyed(-attackDifference);
            } else {
                gameView.printNoCardDestroyedYouReceivedDamage(attackDifference);
            }
        }
    }

    private void rivalsOnDefense(Monster beenAttackedMonster, Monster attackingMonster, String rivalMonsterName) {
        int attackDifference = attackingMonster.getAttackPointInGame() - beenAttackedMonster.getDefencePointInGame();
        if (attackingMonster.getCardName().equals("Exploder Dragon") && attackDifference <= 0
                || beenAttackedMonster.getCardName().equals("Exploder Dragon") && attackDifference > 0)
            attackDifference = 0;
        if (attackDifference > 0) {
            currentPlayer.getRivalPlayer().getBoard().getGraveyard().addCard(beenAttackedMonster);
            currentPlayer.getBoard().removeMonsterFromBoard(beenAttackedMonster);
            if (beenAttackedMonster.getFace() == Face.UP)
                gameView.printDefensePositionDestroyed();
            else {
                gameView.printDefensePositionDestroyedHidden(rivalMonsterName);
            }
        }
        else if (attackDifference == 0) {
            if (beenAttackedMonster.getFace() == Face.UP)
                gameView.printNoCardDestroyed();
            else {
                gameView.printNoCardDestroyedHidden(rivalMonsterName);
            }
        } else {
            currentPlayer.decreaseHealth(-attackDifference);
            if (beenAttackedMonster.getFace() == Face.UP)
                gameView.printNoCardDestroyedYouReceivedDamage(-attackDifference);
            else {
                gameView.printNoCardDestroyedYouReceivedDamageHidden(-attackDifference, rivalMonsterName);
            }
        }
        beenAttackedMonster.setFace(Face.UP);
    }

    public void directAttack(boolean isAI) {
        if (checkDirectAttack()
                || isAI) {
            checkTrapActivation();
            if (canContinue) {
                Monster ourMonster = (Monster) currentPlayer.getSelectedCard();
                currentPlayer.getRivalPlayer().decreaseHealth(ourMonster.getAttackNum());
                gameView.printYourOpponentReceivesDamage(ourMonster.getAttackNum());
                gameView.printMap();
                ourMonster.setAttackedInThisTurn(true);
            }
        }
        deselectCard();
    }

    private boolean checkDirectAttack() {
        if (checkBeforeAnyAttack()) return false;
        if (currentPlayer.getRivalPlayer().getBoard().getNumberOfMonsterInBoard() != 0) {
            gameView.printCantAttackDirectly();
            return false;
        }
        return true;
    }

    private boolean checkMonsterByMonster(int rivalMonsterNum) {
        if (checkBeforeAnyAttack()) return false;
        if (currentPlayer.getRivalPlayer().getBoard()
                .getMonsterByAddress(rivalMonsterNum) == null) {
            gameView.printNoCardToAttack();
            return false;
        }
        return true;
    }

    private boolean checkBeforeAnyAttack() {
        if (currentPlayer.getSelectedCard() == null) {
            gameView.printNoCardSelected();
            return true;
        }
        if (!currentPlayer.getBoard().isMonsterOnBoard(currentPlayer.getSelectedCard())) {
            gameView.printCantAttack();
            return true;
        }
        if (currentPhase != Phase.BATTLE_PHASE) {
            gameView.printWrongPhase();
            return true;
        }
        if (hasCardAttacked(currentPlayer.getSelectedCard())) {
            gameView.printAlreadyAttacked();
            return true;
        }
        if (currentPlayer.getSelectedCard().getFace() == Face.DOWN) {
            gameView.printCantAttackFacedDown();
            return true;
        }
        if (((Monster) currentPlayer.getSelectedCard()).getAttackOrDefense() != AttackOrDefense.ATTACK) {
            gameView.printCantAttackItsOnDefense();
            return true;
        }
        return false;
    }

    private boolean hasCardAttacked(Card selectedCard) {
        Monster monster = (Monster) selectedCard;
        return monster.isAttackedInThisTurn();
    }

    private void commandKnight(Monster ourMonster, Monster rivalMonster) {
        checkCommandKnight(ourMonster, currentPlayer);
        checkCommandKnight(rivalMonster, rivalMonster.getCardOwner());
        boolean attackable = true;
        for (Cell monster : rivalMonster.getCardOwner().getBoard().getMonsters()) {
            if (monster.getCard() != null && !monster.getCard().getCardName().equalsIgnoreCase("Command knight")) {
                attackable = false;
                break;
            }
        }
        for (Cell monster : rivalMonster.getCardOwner().getBoard().getMonsters()) {
            if (monster.getCard() != null && monster.getCard().getCardName().equalsIgnoreCase("Command knight")) {
                Monster commandKnight = (Monster) monster.getCard();
                if (commandKnight.getFace() == Face.UP)
                    commandKnight.setAttackable(attackable);
            }
        }

    }

    private void checkCommandKnight(Monster activationMonster, Player player) {
        activationMonster.decreaseAttackPoint(activationMonster.getCommandKnightsActive().size() * 400);
        activationMonster.getCommandKnightsActive().clear();
        for (Cell monster : player.getBoard().getMonsters()) { // fookin cell has a problem nigga!!
            if (monster.getCard() != null)
                if (monster.getCard().getCardName().equalsIgnoreCase("Command knight") &&
                        monster.getCard().getFace() == Face.UP && monster.getCard() != activationMonster) {
                    activationMonster.setCommandKnightsActive((Monster) monster.getCard());
                }
        }
        activationMonster.increaseAttackPoint(activationMonster.getCommandKnightsActive().size() * 400);
    }


    private boolean messengerOfPeace(Monster attacker) {////////////////////////where to activate??????/////////////////
        if (!checkForActive(currentPlayer, "Messenger of peace")
                && !checkForActive(currentPlayer.getRivalPlayer(), "Messenger of peace"))
            return false;
        return attacker.getAttackPointInGame() >= 1500;
    }


    public void activeEffect() {
        if (currentPlayer.getSelectedCard() == null) {
            gameView.printNoCardSelected();
            return;
        }
        if (!(currentPlayer.getSelectedCard() instanceof Spell) && !(currentPlayer.getSelectedCard() instanceof Trap)) {
            gameView.printActiveOnlyForSpellsAndTrap();
        } else if (currentPlayer.getSelectedCard() instanceof Spell) {
            activateSpell();
        } else {
            activateTrap();
        }
    }

    private void activateTrap() {
        Trap trap = (Trap) currentPlayer.getSelectedCard();
        if (trap.isActivated()) {
            gameView.printAlreadyActivated();
            return;
        }
        if (trap.getCardOwner() != currentPlayer && trap.getZone() == Zone.SPELL_TRAP_ZONE) {
            return;
        }
        if (!trap.getTrapAction().startActionCheck.canActivate()) {
            gameView.printPrepsNotDone();
            return;
        }
        trap.setActivated(true);
        trap.setFace(Face.UP);
        chain.add(trap);
        checkTrapActivation();
    }

    private void activateSpell() {
        if ((currentPhase != Phase.MAIN_PHASE_1) && (currentPhase != Phase.MAIN_PHASE_2)) {
            gameView.printCantActiveThisTurn();
            return;
        }
        Spell spell = (Spell) currentPlayer.getSelectedCard();
        if (spellActive(spell)) {//////////////////fishy///////////////////////////
            gameView.printAlreadyActivated();
            return;
        }
        if (spell.getCardName().equalsIgnoreCase("Advanced ritual art"))
            checkRitualSummon(spell);
        else if (spell.getSpellEffect().equalsIgnoreCase("Field")) activateFieldSpell(spell);
        else activateOtherSpells(spell);
    }

    private void activateOtherSpells(Spell spell) {
        if (currentPlayer.getBoard().getNumberOFSpellAndTrapInBoard() == 5) {
            gameView.printSpellZoneIsFull();
            return;
        }
        if (!checkPreparation(currentPlayer.getSelectedCard())) {
            gameView.printPrepsNotDone();
            return;
        }

        if (spell.getType().equals("Equip")) {
            int tries = -1;
            int num = 0;
            String board = "";
            while (tries == -1) {
                gameView.printSelectMonsterFromBoard();
                String answer = gameView.getAnswer();
                if (answer.matches("^(\\d+) ([\\w]+ [\\w]+)$")) {
                    String[] answerSpilt = answer.split(" ");
                    num = Integer.parseInt(answerSpilt[0]);
                    board = answerSpilt[1] + " " + answerSpilt[2];
                    if (board.equals("abort")) {
                        gameView.printAbortedFromEquipSpell();
                        return;
                    }
                    if (checkCorrectEquipInput(num, board, spell))
                        tries = 0;
                } else gameView.printInvalidCommand();

            }
            currentPlayer.getBoard().putSpellAndTrapInBoard(currentPlayer.getSelectedCard());
            if (board.equalsIgnoreCase("our board"))
                spell.setEquippedMonster
                        ((Monster) currentPlayer.getBoard().getMonsterByAddress(num));
            else
                spell.setEquippedMonster((Monster) currentPlayer.getRivalPlayer().getBoard().getMonsterByAddress(num));
            spell.setActivated(true);
            gameView.printSpellActivated();
        } else {
            findEffect(spell);
            spell.setActivated(true);
            gameView.printSpellActivated();
            if (spell.getType().equals("Normal") || spell.getType().equals("Quick-play"))
                currentPlayer.getBoard().getGraveyard().addCard(spell);
        }
        currentPlayer.getCardsInHand().remove(currentPlayer.getSelectedCard());
        spell.setZone(Zone.SPELL_TRAP_ZONE);
        gameView.printMap();
    }

    private boolean checkCorrectEquipInput(int num, String board, Spell spell) {
        Monster monster;
        if (!(0 < num && num < 6) || !(board.equals("our board") || board.equals("rival board"))) {
            gameView.printInvalidSelection();
            return false;
        }
        if (board.equals("our board"))
            monster = (Monster) currentPlayer.getBoard().getMonsterByAddress(num);
        else monster = (Monster) currentPlayer.getRivalPlayer().getBoard().getMonsterByAddress(num);
        if (monster == null) {
            gameView.printNoMonsterOnThisAddress();
            return false;
        }
        if (!checkCanBeEquippedByMonster(monster, spell)) {
            gameView.printThisCardCantBeEquippedByThisType();
            return false;
        }
        return true;
    }

    private boolean checkCanBeEquippedByMonster(Monster monster, Spell spell) {
        if (spell.getCardName().equalsIgnoreCase("Sword of Dark Destruction") &&
                !(monster.getMonsterType().equalsIgnoreCase("spellcaster") ||
                        monster.getMonsterType().equalsIgnoreCase("fiend")))
            return false;
        else return !spell.getCardName().equalsIgnoreCase("Magnum Shield") ||
                monster.getMonsterType().equalsIgnoreCase("Warrior");
    }

    private void activateFieldSpell(Spell spell) {
        if (currentPlayer.getBoard().getFieldSpell().getCard() != null)
            currentPlayer.getBoard().getGraveyard().addCard(currentPlayer.getBoard().getFieldSpell().getCard());
        currentPlayer.getCardsInHand().remove(currentPlayer.getSelectedCard());
        spell.setZone(Zone.FIELD);
        currentPlayer.getBoard().putSpellAndTrapInBoard(currentPlayer.getSelectedCard());
        currentPlayer.getBoard().setFieldSpell(spell);
        spell.setActivated(true);
        gameView.printSpellActivated();
        gameView.printMap();
    }

    private void findEffect(Spell spell) {
        String effectName = spell.getCardName();
        if (effectName.equalsIgnoreCase("Monster Reborn"))
            monsterReborn();
//        else if (effectName.equalsIgnoreCase("Terraforming"))
//            terraforming();
        else if (effectName.equalsIgnoreCase("Pot of Greed"))
            potOfGreed();
        else if (effectName.equalsIgnoreCase("Raigeki"))
            raigeki();
        else if (effectName.startsWith("Harp"))
            harpie();
//        else if (effectName.equalsIgnoreCase("Swords of Revealing Light"))
//            swordsOfRevealingLight();
        else if (effectName.equalsIgnoreCase("Dark Hole"))
            darkHole();
//        else if (effectName.equalsIgnoreCase("Supply Squad"))
//            supplySquad();
//        else if(effectName.equalsIgnoreCase("Ring of Defense"))
//            ringOfDefense();
//        else if (effectName.equalsIgnoreCase("Spell Absorption"))
//            spellAbsorption();
        else if (effectName.equalsIgnoreCase("Messenger of peace"))
            activeMessenger(spell);
        else if (effectName.equalsIgnoreCase("Twin Twisters"))
            twinTwisters();
        else if (effectName.equalsIgnoreCase("Mystical space typhoon"))
            mysticalTyphoon();
    }

    private void potOfGreed() {
        currentPlayer.addCardToHand();
        currentPlayer.addCardToHand();
    }

//    private void terraforming() {
//        gameView.printSelectNum();
//        currentPlayer.addCardToHand();
//    }

    private void twinTwisters() {
        while (true) {
            gameView.printselectCardFromHand(5);
            String answer = gameView.getAnswer();
            if (answer.equals("abort")) {
                gameView.printAborted();
                return;
            }
            if (answer.matches("\\d+") && 0 < Integer.parseInt(answer) && Integer.parseInt(answer) <= 5) {
                currentPlayer.getBoard().getGraveyard()
                        .addCard(currentPlayer.getCardsInHand().get(Integer.parseInt(answer)));
                while (true) {
                    gameView.printSelectSpellOrTrap();
                    String answer2 = gameView.getAnswer();
                    if (answer2.equals("abort")) {
                        gameView.printAborted();
                        return;
                    }
                    if (answer2.matches("\\d+")) {
                        int num1 = Integer.parseInt(answer2);
                        if (checkForTrapOrSpell(num1)) {
                            currentPlayer.getRivalPlayer().getBoard().getGraveyard()
                                    .addCard(currentPlayer.getRivalPlayer().getBoard()
                                            .getSpellOrTrapByAddress(num1));
                            gameView.printSpellDestroyed();
                            while (true) {
                                gameView.printSelectNextSpellOrAbort();
                                String answer3 = gameView.getAnswer();
                                if (answer3.equals("abort")) {
                                    gameView.printAborted();
                                    return;
                                } else if (answer3.matches("\\d+")) {
                                    int num2 = Integer.parseInt(answer3);
                                    if (checkForTrapOrSpell(num2)) {
                                        currentPlayer.getRivalPlayer().getBoard().getGraveyard()
                                                .addCard(currentPlayer.getRivalPlayer().getBoard()
                                                        .getSpellOrTrapByAddress(num2));
                                        gameView.printSpellDestroyed();
                                        return;
                                    }
                                    gameView.printInvalidSelection();
                                } else gameView.printInvalidCommand();
                            }
                        } else gameView.printInvalidSelection();
                    } else gameView.printInvalidCommand();
                }
            } else gameView.printInvalidCommand();
        }
    }

    private void showCardsInHand(Player player) {
        for (int i = 0; i < player.getCardsInHand().size(); i++) {
            gameView.printCardInHand(player.getCardsInHand().get(i), i);
        }
    }


    private void mysticalTyphoon() {
        boolean check = false;
        int num = 1;
        while (!check) {
            if (currentPlayer.getRivalPlayer().getBoard().getFieldSpell().getCard() != null)
                while (true) {
                    gameView.printDoYouWantToDestroyFieldSpell();
                    String yesOrNo = gameView.getAnswer();
                    if (yesOrNo.equalsIgnoreCase("no"))
                        break;
                    else if (yesOrNo.equalsIgnoreCase("yes")) {
                        currentPlayer.getRivalPlayer().getBoard().getGraveyard()
                                .addCard(currentPlayer.getRivalPlayer().getBoard()
                                        .getFieldSpell().getCard());
                        gameView.printSpellDestroyed();
                        return;
                    } else gameView.printInvalidSelection();
                }
            gameView.printSelectSpellOrTrap();
            String answer = gameView.getAnswer();
            if (answer.equalsIgnoreCase("abort")) {
                gameView.printAborted();
                return;
            }
            if (answer.matches("\\d+")) {
                num = Integer.parseInt(answer);
                if (checkForTrapOrSpell(num)) {
                    check = true;
                } else gameView.printInvalidSelection();
            } else gameView.printInvalidCommand();
        }
        currentPlayer.getRivalPlayer().getBoard().getGraveyard()
                .addCard(currentPlayer.getRivalPlayer().getBoard()
                        .getSpellOrTrapByAddress(num));
        gameView.printSpellDestroyed();
    }

    private boolean checkForTrapOrSpell(int number) {
        if (0 > number || number > 5) {
            gameView.printInvalidSelection();
            return false;
        }
        return currentPlayer.getRivalPlayer().getBoard().getSpellOrTrapByAddress(number) != null;
    }

    private void activeMessenger(Spell spell) {
        spell.setActivated(true);
    }

    private void spellAbsorption() {

    }

    private void darkHole() {
        for (Cell monster : currentPlayer.getBoard().getMonsters()) {
            if(monster.getCard()!=null)
            currentPlayer.getBoard().getGraveyard().addCard(monster.getCard());
        }
        for (Cell monster : currentPlayer.getRivalPlayer().getBoard().getMonsters()) {
            if(monster.getCard()!=null)
            currentPlayer.getRivalPlayer().getBoard().getGraveyard().addCard(monster.getCard());
        }
        gameView.printAllMonstersDestroyed();
    }

    private void harpie() {
        for (Cell cell : currentPlayer.getRivalPlayer().getBoard().getSpellOrTrap()) {
            if(cell.getCard()!=null) {
                currentPlayer.getRivalPlayer().getBoard().getGraveyard().addCard(cell.getCard());
            }
        }
        if(currentPlayer.getRivalPlayer().getBoard().getFieldSpell()!=null)
        currentPlayer.getRivalPlayer().getBoard().getGraveyard()
                .addCard(currentPlayer.getRivalPlayer().getBoard().getFieldSpell().getCard());
    }


    private void raigeki() {
        for (Cell monster : currentPlayer.getRivalPlayer().getBoard().getMonsters()) {
            if(monster.getCard()!=null)
            currentPlayer.getRivalPlayer().getBoard().getGraveyard().addCard(monster.getCard());
        }
    }

    private void monsterReborn() { //چقد توابعش سمیه!!!
        gameView.selectGraveyard();
        Player graveyardOwner = null;
        String graveyardName = gameView.getAnswer();
        if (graveyardName.equalsIgnoreCase("our graveyard")) {
            graveyardOwner = currentPlayer;
        } else if (graveyardName.equalsIgnoreCase("rival graveyard")) {
            graveyardOwner = currentPlayer.getRivalPlayer();
        }
        if (graveyardOwner == null) {
            System.out.println("wrong graveyard name");
            return;
        }
        ShowGraveyardView graveyard = new ShowGraveyardView(graveyardOwner);
        graveyard.getShowGraveyardController().showGraveyard();
        gameView.printSelectNum();
        graveyard.getShowGraveyardController().selectCardFromGraveyard(gameView.getNum(), graveyardOwner);
        specialSummon();

    }


    private boolean checkPreparation(Card card) {
/*        if (card.getCardName().equalsIgnoreCase("Twin Twisters"))
            return twinTwistersCheck();
        else if (card.getCardName().equalsIgnoreCase("Messenger of peace"))
            return checkMessenger();
        else if (card.getCardName().equalsIgnoreCase("Supply Squad"))
            return supplySquad();*/
        return true;
    }

    private boolean spellActive(Spell spell) {
        return spell.isActivated();
    }

    private void equipSpellRid() {
        SwordOfDesRid();
        BlackPendantRid();
        UnitedRid();
        magnumShieldRid();
    }


    private void activateSpecial(Monster ourMonster, Monster rivalMonster) {
        commandKnight(ourMonster, rivalMonster);
        fieldSpell(ourMonster, rivalMonster);
        equipSpell();
    }


    private void equipSpell() {
        SwordOfDes();
        BlackPendant();
        United();
        MagnumShield();
    }

    private void MagnumShield() {
        increaseAndDecrease(currentPlayer, "Magnum Shield", 1, 1);
        increaseAndDecrease(currentPlayer.getRivalPlayer(), "Magnum Shield", 1, 1);
    }

    private void United() {
        unitedCalcCurrent = unitedCalculate(currentPlayer);
        unitedCalcRival = unitedCalculate(currentPlayer.getRivalPlayer());
        increaseAndDecrease(currentPlayer, "United We Stand", unitedCalcCurrent, unitedCalcCurrent);
        increaseAndDecrease(currentPlayer.getRivalPlayer(), "United We Stand", unitedCalcRival, unitedCalcRival);
    }


    private void BlackPendant() {
        increaseAndDecrease(currentPlayer, "Black Pendant", 500, 0);
        increaseAndDecrease(currentPlayer.getRivalPlayer(), "Black Pendant", 500, 0);
    }

    private void SwordOfDes() {
        increaseAndDecrease(currentPlayer, "Sword of Dark Destruction", 400, -200);
        increaseAndDecrease(currentPlayer.getRivalPlayer(), "Sword of Dark Destruction", 400, -200);
    }

    private void increaseAndDecrease(Player player, String spellName, int atkAmount, int defAmount) {
        for (Cell cell : player.getBoard().getSpellOrTrap()) {
            if (cell.getCard() != null && cell.getCard().getCardName().equalsIgnoreCase(spellName)) {
                Spell spell = (Spell) cell.getCard();
                if (spellName.equals("Sword of Dark Destruction")) {
                    spell.getEquippedMonster().increaseAttackPoint(atkAmount);
                    spell.getEquippedMonster().increaseDefensePoint(defAmount);
                } else if (spellName.equalsIgnoreCase("Magnum Shield")) {
                    if (spell.getEquippedMonster().getAttackOrDefense() == AttackOrDefense.ATTACK) {
                        spell.getEquippedMonster().increaseAttackPoint
                                (spell.getEquippedMonster().getDefencePointInGame() * atkAmount);
                    } else {
                        spell.getEquippedMonster().increaseDefensePoint
                                (spell.getEquippedMonster().getAttackPointInGame() * defAmount);
                    }
                } else {
                    spell.getEquippedMonster().increaseAttackPoint(atkAmount);
                    spell.getEquippedMonster().increaseDefensePoint(defAmount);
                }
            }
        }
    }

    private int unitedCalculate(Player player) {
        int counter = 0;
        for (Cell monster : player.getBoard().getMonsters()) {
            if (monster.getCard() != null && monster.getCard().getFace() == Face.UP)
                counter++;
        }
        return counter * 800;
    }

    private void SwordOfDesRid() {
        increaseAndDecrease(currentPlayer, "Sword of Dark Destruction", -400, 200);
        increaseAndDecrease(currentPlayer.getRivalPlayer(), "Sword of Dark Destruction", -400, 200);
    }

    private void BlackPendantRid() {
        increaseAndDecrease(currentPlayer, "Black Pendant", -500, 0);
        increaseAndDecrease(currentPlayer.getRivalPlayer(), "Black Pendant", -500, 0);
    }

    private void UnitedRid() {
        increaseAndDecrease(currentPlayer, "United We Stand", -unitedCalcCurrent, -unitedCalcCurrent);
        increaseAndDecrease(currentPlayer.getRivalPlayer(), "United We Stand", -unitedCalcRival, -unitedCalcRival);
    }

    private void magnumShieldRid() {
        increaseAndDecrease(currentPlayer, "Magnum Shield", -1, -1);
        increaseAndDecrease(currentPlayer.getRivalPlayer(), "Magnum Shield", -1, -1);
    }

    private void fieldSpell(Monster ourMonster, Monster rivalMonster) {
        if (ourMonster.getCardOwner().getBoard().getFieldSpell().getCard() != null)
            checkField(ourMonster, rivalMonster);
        if (rivalMonster.getCardOwner().getBoard().getFieldSpell().getCard() != null)
            checkField(rivalMonster, ourMonster);
    }

    private void checkField(Monster ourMonster, Monster rivalMonster) {
        yami(ourMonster, rivalMonster);
        forest(ourMonster, rivalMonster);
        closedForest(ourMonster);
        UMIIRUKA(ourMonster, rivalMonster);
    }

    private void fieldSpellRid(Monster ourMonster, Monster rivalMonster) {
        if (ourMonster.getCardOwner().getBoard().getFieldSpell().getCard() != null) {
            UMIIRUKARid(ourMonster, rivalMonster);
            forestRid(ourMonster, rivalMonster);
            yamiRid(ourMonster, rivalMonster);
        }
        if (rivalMonster.getCardOwner().getBoard().getFieldSpell().getCard() != null) {
            UMIIRUKARid(rivalMonster, ourMonster);
            forestRid(rivalMonster, ourMonster);
            yamiRid(rivalMonster, ourMonster);
        }
    }

    private void UMIIRUKA(Monster monster, Monster rivalMonster) {
        if (monster.getCardOwner().getBoard().getFieldSpell().getCard()
                .getCardName().equalsIgnoreCase("UMIIRUKA")) {
            UMIIRUKAIncrease(monster, 500, -400);
            UMIIRUKAIncrease(rivalMonster, 500, -400);
        }
    }

    private void UMIIRUKAIncrease(Monster monster, int atkAmount, int defAmount) {
        fieldIncreaseAtk(monster.getCardOwner(), "Aqua", atkAmount);
        fieldIncreaseDef(monster.getCardOwner(), "Aqua", defAmount);
    }

    private void UMIIRUKARid(Monster ourMonster, Monster rivalMonster) {
        if (ourMonster.getCardOwner().getBoard().getFieldSpell()
                .getCard().getCardName().equalsIgnoreCase("UMIIRUKA")) {
            UMIIRUKAIncrease(ourMonster, -500, 400);
            UMIIRUKAIncrease(rivalMonster, -500, 400);
        }
    }

    private void closedForest(Monster monster) {
        monster.decreaseAttackPoint(differenceInAtkPoints);
        int prevAtkPoint = monster.getAttackPointInGame();
        if (monster.getCardOwner().getBoard().getFieldSpell()
                .getCard().getCardName().equalsIgnoreCase("Closed Forest")) {
            closedForestIncrease(monster,
                    monster.getCardOwner().getBoard().getGraveyard().getAllCards().size() * 100);
            differenceInAtkPoints= monster.getAttackPointInGame()- prevAtkPoint;
        }
    }


    private void closedForestIncrease(Monster monster, int amount) {
        fieldIncreaseAtk(monster.getCardOwner(),"Beast-Warrior",amount);
    }

    private void forest(Monster monster, Monster rivalMonster) {
        if (monster.getCardOwner().getBoard().getFieldSpell()
                .getCard().getCardName().equalsIgnoreCase("Forest")) {
            forestIncrease(monster, 200);
            forestIncrease(rivalMonster, 200);
        }
    }

    private void forestRid(Monster ourMonster, Monster rivalMonster) {
        if (ourMonster.getCardOwner().getBoard().getFieldSpell()
                .getCard().getCardName().equalsIgnoreCase("Forest")) {
            forestIncrease(ourMonster, -200);
            forestIncrease(rivalMonster, -200);
        }
    }

    private void forestIncrease(Monster monster, int amount) {
        fieldIncreaseAtk(monster.getCardOwner(), "Insect", amount);
        fieldIncreaseAtk(monster.getCardOwner(), "Beast", amount);
        fieldIncreaseAtk(monster.getCardOwner(), "Beast-Warrior", amount);
        fieldIncreaseDef(monster.getCardOwner(), "Insect", amount);
        fieldIncreaseDef(monster.getCardOwner(), "Beast", amount);
        fieldIncreaseDef(monster.getCardOwner(), "Beast-Warrior", amount);
    }

    private void yami(Monster monster, Monster rivalMonster) {
        if (monster.getCardOwner().getBoard().getFieldSpell()
                .getCard().getCardName().equalsIgnoreCase("Yami")) {
            yamiIncrease(monster, 200);
            yamiIncrease(rivalMonster, 200);
        }
    }

    private void yamiRid(Monster ourMonster, Monster rivalMonster) {
        if (ourMonster.getCardOwner().getBoard().getFieldSpell()
                .getCard().getCardName().equalsIgnoreCase("Yami")) {
            yamiIncrease(ourMonster, -200);
            yamiIncrease(rivalMonster, -200);
        }
    }

    private void yamiIncrease(Monster monster, int amount) {
        fieldIncreaseAtk(monster.getCardOwner(), "Fiend", amount);
        fieldIncreaseAtk(monster.getCardOwner(), "Spellcaster", amount);
        fieldIncreaseAtk(monster.getCardOwner(), "Fairy", -amount);
        fieldIncreaseDef(monster.getCardOwner(), "Fiend", amount);
        fieldIncreaseDef(monster.getCardOwner(), "Spellcaster", amount);
        fieldIncreaseDef(monster.getCardOwner(), "Fairy", -amount);
    }

    public void fieldIncreaseAtk(Player player, String monsterType, int amount) {
        for (Cell monster : player.getBoard().getMonsters()) {
            Monster boardMonster = (Monster) monster.getCard();
            if (boardMonster != null)
                if (boardMonster.getMonsterType().equalsIgnoreCase(monsterType))
                    boardMonster.increaseAttackPoint(amount);
        }
    }

    public void fieldIncreaseDef(Player player, String monsterType, int amount) {
        for (Cell monster : player.getBoard().getMonsters()) {
            Monster boardMonster = (Monster) monster.getCard();
            if (boardMonster != null)
                if (boardMonster.getMonsterType().equalsIgnoreCase(monsterType))
                    boardMonster.increaseDefensePoint(amount);
        }
    }


    private boolean checkForActive(Player player, String spellName) {
        for (Cell cell : player.getBoard().getSpellOrTrap()) {
            if (cell.getCard() instanceof Spell) {
                Spell spell = (Spell) cell.getCard();
                if (spell != null && spell.getCardName().equals(spellName) && spell.isActivated())
                    return true;
            }
        }
        return false;
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
        if (cardAddress <= 0 || cardAddress > 5) {
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
        if (cardAddress <= 0 || cardAddress > currentPlayer.getCardsInHand().size()) {
            gameView.printInvalidSelection();
            return false;
        }
        if (currentPlayer.getCardsInHand().get(cardAddress - 1) == null) {
            gameView.printNotFoundCard();
            return false;
        }
        return true;
    }

    public void nextPhase() {
        if (currentPhase == Phase.END_PHASE) {
            currentPhase = Phase.DRAW_PHASE;
            gameView.printCurrentPhase();
            turnsPlayed++;
            if (!notToDrawCardTurns.contains(turnsPlayed) && currentPlayer.getCardsInHand().size() < 6) {
                if (currentPlayer.getBoard().getDeck().getAllCardsInMainDeck().size() != 0) {
                    addCardToHand();
                } else {
                    new GameWinMenu(this).announceWinner(currentPlayer.getRivalPlayer());
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
            if (turnsPlayed == 0) {
                currentPhase = Phase.DRAW_PHASE;
                currentPlayer.setSetOrSummonInThisTurn(false);
                changeCurrentPlayer();
                gameView.printCurrentPhase();
                gameView.printWhoseTurn();
                turnsPlayed++;
            }
            gameView.printCurrentPhase();//to complete
        } else if (currentPhase == Phase.BATTLE_PHASE) {
            currentPhase = Phase.MAIN_PHASE_2;
            gameView.printCurrentPhase();
            //to comp
        } else if (currentPhase == Phase.MAIN_PHASE_2) {
            reset();
            currentPhase = Phase.END_PHASE;
            currentPlayer.setSetOrSummonInThisTurn(false);
            changeCurrentPlayer();
            gameView.printCurrentPhase();
            gameView.printWhoseTurn();
        }
    }

    private void reset() {
        anySummonHappened = false;
        normalSummonHappened = false;
        ritualSummonHappened = false;
        specialSummonHappened = false;
        flipSummonHappened = false;
        for (Cell monster : currentPlayer.getBoard().getMonsters()) {
            if (monster.getCard() != null) {
                Monster monsterInCell = ((Monster) (monster.getCard()));
                if (!monster.getCard().getCardName().equalsIgnoreCase("Suijin"))
                    monsterInCell.setActiveAbility(false);
                monsterInCell.setAttackable(true);
                monsterInCell.setAttacker(null);
                monsterInCell.setHasBeenAttacked(false);
                monsterInCell.setSetInThisTurn(false);
                monsterInCell.setAttackedInThisTurn(false);
                monsterInCell.setHaveChangePositionInThisTurn(false);
                if (monsterInCell.isScanner())
                    checkScannerEffect(monsterInCell, false);
                if (monsterInCell.getCardName().equalsIgnoreCase("Herald Of Creation"))
                    heraldOfCreationEffect(monsterInCell);
            }
        }
    }


    public void checksBeforeSummon() {
        if (currentPlayer.getSelectedCard() == null)
            gameView.printNoCardSelected();
        else {
            if (currentPlayer.getCardsInHand().contains(currentPlayer.getSelectedCard())
                    && currentPlayer.getSelectedCard() instanceof Monster
                    && ((Monster) currentPlayer.getSelectedCard()).getMonsterCardType() != MonsterCardType.RITUAL
                    && !(currentPlayer.getSelectedCard()).getCardName().equalsIgnoreCase("the tricky")) {
                if (currentPhase != Phase.MAIN_PHASE_1 && currentPhase != Phase.MAIN_PHASE_2)
                    gameView.printNotInMainPhase();
                else {
                    if (currentPlayer.getBoard().isThereEmptyPlaceMonsterZone()) {
                        if (currentPlayer.isSetOrSummonInThisTurn())
                            gameView.printAlreadySetOrSummon();
                        else if (currentPlayer.getSelectedCard().getCardName().equalsIgnoreCase("Beast king barbaros"))
                            beatsKingBarbaros((Monster) currentPlayer.getSelectedCard());
                        else if (currentPlayer.getSelectedCard().getCardName().equalsIgnoreCase("Terratiger, the Empowered Warrior"))
                            terratiger((Monster) currentPlayer.getSelectedCard());
                        else
                            summonAndSpecifyTribute();
                    } else gameView.printMonsterZoneFull();
                }
            } else gameView.printCantSummon();
        }
    }

    public void normalSummon(Monster monster, AttackOrDefense position) {
        anySummonHappened = true;
        if (monster.getCardName().equalsIgnoreCase("Mirage Dragon"))
            mirageDragonEffect(monster);
        if (monster.getCardName().equalsIgnoreCase("scanner"))
            if (checkScannerEffect(monster, true)) return;
        summonedCard = monster;
        gameView.printSummonSuccessfully();
        currentPlayer.getCardsInHand().remove(monster);
        monster.setSetInThisTurn(true);
        monster.setZone(Zone.MONSTER_ZONE);
        monster.setFace(Face.UP);
        monster.setAttackOrDefense(position);
        currentPlayer.setSetOrSummonInThisTurn(true);
        currentPlayer.setSelectedCard(null);
        currentPlayer.getBoard().putMonsterInBoard(monster);
        gameView.printMap();
    }

    private boolean checkScannerEffect(Monster monster, boolean summoning) {
        List<Monster> rivalGraveYardMonsters = getCurrentPlayer().getRivalPlayer().getBoard().getGraveyard()
                .getAllCards().stream().filter(e -> e instanceof Monster).map(e -> (Monster) e).collect(Collectors.toList());
        if (rivalGraveYardMonsters.size() == 0) {
            if (summoning) gameView.printCantSummon();
            return true;
        }
        boolean isThereMonsterExceptScanner = false;
        for (Monster m : rivalGraveYardMonsters)
            if (!m.isScanner()) {
                isThereMonsterExceptScanner = true;
                break;
            }
        if (!isThereMonsterExceptScanner) {
            if (summoning) gameView.printCantSummon();
            return true;
        }
        scannerEffect(monster, rivalGraveYardMonsters);
        return false;
    }

    private void scannerEffect(Monster scanner, List<Monster> rivalGraveYardMonsters) {
        Monster monster;
        do {
            int number = gameView.chooseMonsterForSummonScanner(rivalGraveYardMonsters);
            monster = rivalGraveYardMonsters.get(number);
        } while (monster.getCardName().equalsIgnoreCase("scanner"));
        scanner.setAttackPointInGame(monster.getAttackNum());
        scanner.setDefencePointInGame(monster.getDefenseNum());
        scanner.setMonsterAttribute(monster.getMonsterAttribute());
        scanner.setMonsterType(monster.getMonsterType());
        scanner.setLevel(monster.getLevel());
        scanner.setCardName(monster.getCardName());
        scanner.setScanner(true);
        scanner.setActiveAbility(true);
    }

    private void heraldOfCreationEffect(Monster monsterInCell) {
        List<Monster> rivalGraveYardMonster = getCurrentPlayer().getRivalPlayer().getBoard().getGraveyard().getAllCards()
                .stream().filter(e -> e instanceof Monster).map(e -> (Monster) e).filter(e -> e.getLevel() >= 7)
                .collect(Collectors.toList());
        if (rivalGraveYardMonster.size() > 0)
            if (gameView.wantToUseHeraldAbility()) {
                Monster monster;
                int number = gameView.chooseMonsterForHeraldOfCreation(rivalGraveYardMonster);
                monster = rivalGraveYardMonster.get(number);
                getCurrentPlayer().getCardsInHand().add(monster);
            }
    }

    private void mirageDragonEffect(Monster monster) {
        getCurrentPlayer().getRivalPlayer().setCanActiveTrap(false);
        monster.setActiveAbility(true);
    }

    private void terratiger(Monster terratiger) {
        boolean isExist = false;
        if (getCurrentPlayer().getBoard().getNumberOfMonsterInBoard() <= 3)
            for (Card card : currentPlayer.getCardsInHand()) {
                if (card instanceof Monster)
                    if (((Monster) card).getLevel() <= 4) {
                        isExist = true;
                        break;
                    }
            }
        if (isExist)
            gameView.askWantSummonedAnotherMonsterTerratiger();
        terratiger.setActiveAbility(true);
        normalSummon(terratiger, AttackOrDefense.ATTACK);
        checkTrapActivation();
        anySummonHappened = false;
    }

    private void summonAndSpecifyTribute() {
        Monster monster = (Monster) currentPlayer.getSelectedCard();
        int numberOfTribute = monster.howManyTributeNeed();
        if (numberOfTribute != 0) {
            int numberOfMonsterInOurBoard = currentPlayer.getBoard().getNumberOfMonsterInBoard();
            if (numberOfMonsterInOurBoard < numberOfTribute) {
                currentPlayer.setSelectedCard(null);
                gameView.printThereArentEnoughMonsterForTribute();
                return;
            }
            if (!getTribute(numberOfTribute)) {
                gameView.printMap();
                return;
            }
        }
        normalSummonHappened = true;
        normalSummon(monster, AttackOrDefense.ATTACK);
        checkTrapActivation();
        normalSummonHappened = false;
        anySummonHappened = false;
    }

    private void beatsKingBarbaros(Monster monster) {//// hooman:cell is broken- 1400/2/27\\10:33
        if (currentPlayer.getBoard().getNumberOfMonsterInBoard() >= 3) {
            if (gameView.doYouWantTributeBarBaros()) {
                gameView.getTributeForBarbaros();
            } else {
                monster.setAttackPointInGame(1900);
                for (Cell cell : currentPlayer.getRivalPlayer().getBoard().getMonsters()) {
                    currentPlayer.getRivalPlayer().getBoard().getGraveyard().addCard(cell.getCard());
                    cell.setCard(null);
                }
            }
        }
        monster.setActiveAbility(true);
        normalSummonHappened = true;
        normalSummon(monster, AttackOrDefense.ATTACK);
        checkTrapActivation();
        normalSummonHappened = false;
        anySummonHappened = false;
    }

    public boolean checkBarbarosInput(int monsterNumber1, int monsterNumber2, int monsterNumber3) {
        if (monsterNumber1 <= 0 || monsterNumber1 >= 6
                || monsterNumber2 <= 0 || monsterNumber2 >= 6
                || monsterNumber3 <= 0 || monsterNumber3 >= 6) return false;
        Monster monster1 = (Monster) currentPlayer.getBoard().getMonsterByAddress(monsterNumber1);
        Monster monster2 = (Monster) currentPlayer.getBoard().getMonsterByAddress(monsterNumber2);
        Monster monster3 = (Monster) currentPlayer.getBoard().getMonsterByAddress(monsterNumber3);
        if (monster1 == null || monster2 == null || monster3 == null) return false;
        currentPlayer.getBoard().getGraveyard().addCard(monster1);
        currentPlayer.getBoard().getGraveyard().addCard(monster2);
        currentPlayer.getBoard().getGraveyard().addCard(monster3);
        return true;
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
            return false;
        }
        for (Monster monster : tributeMonster) {
            currentPlayer.getBoard().getGraveyard().addCard(monster);
        }
        return true;
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
        if (currentPhase == Phase.MAIN_PHASE_1 || currentPhase == Phase.MAIN_PHASE_2) {
            if (currentPlayer.getSelectedCard() instanceof Spell && ((Spell)currentPlayer.getSelectedCard())
                    .getType().equalsIgnoreCase("field")){
                if (currentPlayer.getBoard().getFieldSpell().getCard() != null){
                    Spell spell = (Spell) currentPlayer.getSelectedCard();
                    spell.setFace(Face.UP);
                    spell.setZone(Zone.FIELD);
                    currentPlayer.getCardsInHand().remove(currentPlayer.getSelectedCard());
                    currentPlayer.setSelectedCard(null);
                    gameView.printMap();
                    checkTrapActivation();
                } else gameView.printSpellZoneIsFull();
            } else if (currentPlayer.getBoard().getNumberOFSpellAndTrapInBoard() < 5) {
                currentPlayer.getBoard().putSpellAndTrapInBoard(currentPlayer.getSelectedCard());
                Card card = currentPlayer.getSelectedCard();
                card.setZone(Zone.SPELL_TRAP_ZONE);
                card.setFace(Face.DOWN);
                if (currentPlayer.getSelectedCard() instanceof Spell)
                    ((Spell) currentPlayer.getSelectedCard()).setSetINThisTurn(true);
                else
                    ((Trap) currentPlayer.getSelectedCard()).setSetTurn(turnsPlayed);
                currentPlayer.getCardsInHand().remove(currentPlayer.getSelectedCard());
                currentPlayer.setSelectedCard(null);
                gameView.printSetSuccessfully();
                gameView.printMap();
                checkTrapActivation();
            } else
                gameView.printSpellZoneIsFull();
        } else
            gameView.printNotInMainPhase();
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
                    currentPlayer.getCardsInHand().remove(selectedCard);
                    currentPlayer.getBoard().putMonsterInBoard(selectedCard);
                    gameView.printMap();
                    checkTrapActivation();
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
                            gameView.printMap();
                            checkTrapActivation();
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
                        currentPlayer.getCardsInHand().remove(monster);
                        monster.setFace(Face.UP);
                        monster.setAttackOrDefense(AttackOrDefense.ATTACK);
                        gameView.printMap();
                        summonedCard = monster;
                        flipSummonHappened = true;
                        anySummonHappened  = true;
                        checkTrapActivation();
                        if (canContinue) {
                            if (monster.getCardName().equalsIgnoreCase("man-eater bug")
                                    || monster.getCardName().equalsIgnoreCase("gate guardian"))
                                manEaterBugFlipSummon(monster);
                            gameView.printFlipSummonSuccessfully();
                        }
                        anySummonHappened = false;
                        flipSummonHappened = false;
                        canContinue = true;
                        summonedCard = null;
                    }
                } else
                    gameView.printNotInMainPhase();
            } else
                gameView.printCantChangePosition();
        }
    }

    private void manEaterBugFlipSummon(Monster manEaterBug) {
        if (manEaterBug.getZone() != Zone.GRAVEYARD) {
            if (currentPlayer.getRivalPlayer().getBoard().getNumberOfMonsterInBoard() > 0) {
                Monster monster = gameView.askDoYouWantKillOneOfRivalMonster();
                if (monster != null) {
                    monster.setActiveAbility(true);
                    monster.getCardOwner().getBoard().getGraveyard().addCard(monster);
                    gameView.printMap();
                }
            }
        }
    }

    public boolean summonCardWithTerratiger(int monsterNumber) {
        if (monsterNumber <= 0 || monsterNumber > 5) return false;
        if (currentPlayer.getCardsInHand().get(monsterNumber - 1) instanceof Monster) {
            Monster monster = (Monster) currentPlayer.getCardsInHand().get(monsterNumber - 1);
            if (monster.getLevel() > 4)
                return false;
            normalSummonHappened = true;
            normalSummon(monster, AttackOrDefense.DEFENSE);
            checkTrapActivation();
            normalSummonHappened = false;
            anySummonHappened = false;
            return true;
        } else return false;
    }

    public Phase getCurrentPhase() {
        return currentPhase;
    }

    public void checksBeforeSpecialSummon(boolean isItHappenBecauseOfCardEffect) {
        if (isItHappenBecauseOfCardEffect || currentPlayer.getSelectedCard().getCardName().equalsIgnoreCase("the tricky")) {
            Monster monster = (Monster) currentPlayer.getSelectedCard();
            if (monster.getCardName().equalsIgnoreCase("the tricky")) {
                if (currentPlayer.getBoard().isThereEmptyPlaceMonsterZone())
                    gameView.getTributeTheTricky();
                else {
                    gameView.printCantSpecialSummon();
                    return;
                }
                specialSummon();
            }
        } else
            gameView.printCantSpecialSummon();
    }

    private void checkRitualSummon(Spell spell) {
        List<Monster> ritualMonster = getCurrentPlayer().getCardsInHand().stream().filter(e -> e instanceof Monster)
                .map(e -> (Monster) e)
                .filter(e -> e.getMonsterCardType() == MonsterCardType.RITUAL).collect(Collectors.toList());
        if (ritualMonster.size() == 0)
            gameView.cantRitualSummon();
        else {
            if (checkLevelForRitualSummon(ritualMonster)) {
                getCurrentPlayer().getBoard().getGraveyard().addCard(spell);
                gameView.getRitualSummonInput();
            } else
                gameView.cantRitualSummon();
        }
    }

    private boolean checkLevelForRitualSummon(List<Monster> ritualMonster) {
        List<Integer> boardMonsterLevel = Arrays.stream(getCurrentPlayer().getBoard().getMonsters()).map(e -> (Monster) e.getCard())
                .filter(Objects::nonNull)
                .filter(e -> e.getMonsterCardType() == MonsterCardType.NORMAL)
                .map(Monster::getLevel)
                .collect(Collectors.toList());
        int total = 1 << boardMonsterLevel.size();
        for (int i = 0; i < total; i++) {
            int sum = 0;
            for (int j = 0; j < boardMonsterLevel.size(); j++)
                if ((i & (1 << j)) != 0)
                    sum += boardMonsterLevel.get(j);
            for (Monster monster : ritualMonster) {
                if (monster.getLevel() == sum)
                    return true;
            }
        }
        return false;
    }

    private void specialSummon() {
        specialSummonHappened = true;
        Monster monster = (Monster) currentPlayer.getSelectedCard();
        summonedCard = monster;
        String position = gameView.getPositionForSpecialSummon();
        normalSummon(monster, position.equalsIgnoreCase("attack") ? AttackOrDefense.ATTACK : AttackOrDefense.DEFENSE);
        checkTrapActivation();
        anySummonHappened = false;
        specialSummonHappened = false;
    }

    public boolean checkTheTrickyInput(int monsterNumber) {
        if (monsterNumber <= 0 || monsterNumber > currentPlayer.getCardsInHand().size()) return false;
        if (!(currentPlayer.getCardsInHand().get(monsterNumber - 1) instanceof Monster)) return false;
        if (currentPlayer.getCardsInHand().get(monsterNumber - 1).getCardName().equalsIgnoreCase("the tricky")) return false;
        Monster monster = (Monster) currentPlayer.getCardsInHand().get(monsterNumber - 1);
        currentPlayer.getBoard().getGraveyard().addCard(monster);
        currentPlayer.getCardsInHand().remove(monster);
        return true;
    }

    public void selectMonsterFromPlayerGraveyard(Player player) {
        ShowGraveyardView showGraveyardView = new ShowGraveyardView(player);
        showGraveyardView.run("show graveyard");
        int monsterNum;
        do {
            showGraveyardView.printSelectCard();
            monsterNum = ViewMaster.scanner.nextInt();
        } while (monsterNum > showGraveyardView.getShowGraveyardController().getMonsterCounter());
        showGraveyardView.getShowGraveyardController().selectCardFromGraveyard(monsterNum, player);
    }

    public void moneyCheat(int amount) {
        currentPlayer.getUser().addMoney(amount);
    }

    public void lifePointCheat(int amount){
        int lp = currentPlayer.getLifePoint();
        currentPlayer.setLifePoint(lp + amount);
    }

    public void setDuelWinnerCheat(String nickName){
        Player winner = null;
        if (currentPlayer.getUser().getNickname().equals(nickName))
            winner = currentPlayer;
        else if (!(currentPlayer.getRivalPlayer() instanceof AIPlayer)){
            if (currentPlayer.getRivalPlayer().getUser().getNickname().equals(nickName))
                winner = currentPlayer.getRivalPlayer();
        }
        if (winner != null){
            GameWinMenu gameWinMenu = new GameWinMenu(this);
            gameWinMenu.announceWinner(winner);
        }
    }

    public void addCardToHand(){
        Card card = currentPlayer.addCardToHand();
        gameView.printCardAddedToHand(card);
    }

    public void playAI() {
        ((AIPlayer) currentPlayer).play(currentPhase, this);
    }

    public boolean checkTributeLevelForRitualSummon(ArrayList<Monster> tributes, Monster ritualMonster) {
        int sum = 0;
        for (Monster tribute : tributes) {
            sum += tribute.getLevel();
        }
        return sum == ritualMonster.getLevel();
    }

    public void ritualSummon(Monster ritualMonster, ArrayList<Monster> tributes, AttackOrDefense position) {
        for (Monster tribute : tributes)
            getCurrentPlayer().getBoard().getGraveyard().addCard(tribute);
        ritualSummonHappened = true;
        ritualMonster.setActiveAbility(true);
        checkTrapActivation();
        normalSummon(ritualMonster, position);
    }

    public ArrayList<Trap> currentPlayerCanActivateTrap() {
        ArrayList<Trap> trapArrayList = new ArrayList<>();
        for (Cell cell : currentPlayer.getBoard().getSpellOrTrap()) {
            addTrap(trapArrayList, cell);
        }
        return trapArrayList;
    }

    public ArrayList<Trap> rivalPlayerCanActivateTrap() {
        ArrayList<Trap> trapArrayList = new ArrayList<>();
        for (Cell cell : currentPlayer.getRivalPlayer().getBoard().getSpellOrTrap()) {
            addTrap(trapArrayList, cell);
        }
        return trapArrayList;
    }

    private void addTrap(ArrayList<Trap> trapArrayList, Cell cell) {
        if (cell.getCard() != null && cell.getCard() instanceof Trap) {
            TrapAction trapAction = ((Trap) cell.getCard()).getTrapAction();
            if (trapAction.startActionCheck.canActivate()) {
                trapArrayList.add((Trap) cell.getCard());
            }
        }
    }

    public void activateRivalPlayerTrap() {
        ArrayList<Trap> trapArrayList = rivalPlayerCanActivateTrap();
        boolean activatedTrap = false;
        if (trapArrayList.size() != 0) {
            changeCurrentPlayer();
            gameView.printChangeTurn();
            activatedTrap = addTrapToChain(trapArrayList);
            changeCurrentPlayer();
            gameView.printChangeTurn();
        }
        if (activatedTrap)
            activateCurrentPlayerTrap();
    }

    public void activateCurrentPlayerTrap() {
        ArrayList<Trap> trapArrayList = currentPlayerCanActivateTrap();
        boolean activatedTrap = false;
        if (trapArrayList.size() != 0) {
            gameView.printChangeTurn();
            activatedTrap = addTrapToChain(trapArrayList);
        }
        if (activatedTrap)
            activateRivalPlayerTrap();
    }

    private boolean addTrapToChain(ArrayList<Trap> trapArrayList) {
        boolean activatedTrap = false;
        if (currentPlayer instanceof AIPlayer){
            for (Trap trap : trapArrayList){
                trap.setActivated(true);
                trap.setFace(Face.UP);
                chain.add(trap);
                activatedTrap = true;
            }
        } else {
            for (Trap trap : trapArrayList) {
                if (gameView.wantToActivateTrap(trap)) {
                    trap.setActivated(true);
                    trap.setFace(Face.UP);
                    chain.add(trap);
                    gameView.printMap();
                    activatedTrap = true;
                }
            }
        }
        return activatedTrap;
    }

    private void runChain() {
        for (int i = chain.size() - 1; i >= 0; i--) {
            if (!(chain.get(i).getTrapAction() instanceof CallOfTheHaunted)) {
                chain.get(i).setActivatedTurn(turnsPlayed);
                chain.get(i).getTrapAction().run();
                chain.remove(i);
            } else {
                if (chain.get(i).isActivated() && chain.get(i).getActivatedTurn() == -1){
                    chain.get(i).setActivatedTurn(turnsPlayed);
                    chain.get(i).getTrapAction().run();
                    continue;
                }
                CallOfTheHaunted callOfTheHaunted = (CallOfTheHaunted) chain.get(i).getTrapAction();
                if (callOfTheHaunted.getEndActionCheck2().canActivate() || callOfTheHaunted.getEndActionCheck1().canActivate()) {
                    callOfTheHaunted.runEnd();
                    chain.remove(i);
                }
            }
        }
    }

    private void checkTrapActivation() {
        activateRivalPlayerTrap();
        runChain();
    }
}
