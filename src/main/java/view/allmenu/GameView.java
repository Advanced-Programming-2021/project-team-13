package view.allmenu;

import controll.GameController;
import enums.Phase;
import model.cards.Card;
import model.players.Player;
import view.Regex;
import view.ViewMaster;

import java.util.regex.Matcher;

public class GameView {
    private final GameController gameController;

    public GameView(Player firstPlayer, Player secondPlayer, Player currentPlayer , int rounds) {
        gameController = new GameController(this,firstPlayer,secondPlayer,currentPlayer,rounds);
    }

    public void run(String command) {
        if (command.startsWith("select"))
            selectOrDeselectCard(command);
        else if (command.equals("surrender"))
            surrender();
        else if (command.equals("show graveyard"))
            showGraveyard();
        else if (command.equals("summon"))
            gameController.summon();
        else if (command.equals("set"))
            gameController.set();
        else if (command.matches(Regex.CHANGE_SET))
            changeSet(Regex.getInputMatcher(command, Regex.CHANGE_SET));
        else if (command.matches(Regex.SET_POSITION))
            setPosition(Regex.getInputMatcher(command, Regex.SET_POSITION));
        else if (command.equals("flip-summon"))
            gameController.flipSummon();
        else if (command.matches(Regex.ATTACK))
            attack(Regex.getInputMatcher(command, Regex.ATTACK));
        else if (command.equals("attack direct"))
            directAttack();
        gameController.checkEnded();
    }

    private void changeSet(Matcher inputMatcher) {
        inputMatcher.find();
        String position = inputMatcher.group("position");
        gameController.changeSet(position);
    }

    private void nextPhase() {

    }


    private void drawPhase(String command) {
        drawCard();
        findOtherCommand(command);

    }

    private void findOtherCommand(String command) {
        //if (command.equals())
    }

    private void drawCard() {
    }

    public void showGraveyard() {
    }

    private void surrender() {
    }

//    public void changeTurn() {
//
//    }

    private void directAttack() {
        gameController.directAttack();
    }

    private void attack(Matcher inputMatcher) {
        if (inputMatcher.find()) {
            int monsterNumber = Integer.parseInt(inputMatcher.group(1));
            gameController.attack(monsterNumber);
        }
    }

    private void flipSummon() {
    }

    private void setPosition(Matcher inputMatcher) {   // y the fuck are these private!@#!@?
    }

    private void selectOrDeselectCard(String command) {
        if (command.matches(Regex.DESELECT))
            deselectCard();
        else selectCard(command);
    }

    private void deselectCard() {
        gameController.deselectCard();
    }

    public void selectCard(String command) {
        Matcher opponentMatcher = Regex.getInputMatcher(command, Regex.OPPONENT);
        Matcher monsterMatcher = Regex.getInputMatcher(command, Regex.MONSTER);
        Matcher spellMatcher = Regex.getInputMatcher(command, Regex.SPELL);
        Matcher fieldMatcher = Regex.getInputMatcher(command, Regex.FIELD);
        Matcher handMatcher = Regex.getInputMatcher(command, Regex.HAND);
        if (handMatcher.groupCount() != 1 && handMatcher.groupCount() != 0) {   // this can be shorter !!!! -- or at least we could extract the method of invalid command!
            printInvalidCommand();
            return;
        } else if (fieldMatcher.groupCount() != 1 && fieldMatcher.groupCount() != 0) {
            printInvalidCommand();
            return;
        } else if (monsterMatcher.groupCount() != 1 && monsterMatcher.groupCount() != 0) {
            printInvalidCommand();
            return;
        } else if (spellMatcher.groupCount() != 1 && spellMatcher.groupCount() != 0) {
            printInvalidCommand();
            return;
        } else if (opponentMatcher.groupCount() != 1 && opponentMatcher.groupCount() != 0) {
            printInvalidCommand();
            return;
        }
        if (monsterMatcher.groupCount() == 1) {
            int cardAddress = Integer.parseInt(monsterMatcher.group("cardAddress"));
            if (opponentMatcher.groupCount() == 1)
                gameController.selectOpponentMonster(cardAddress);
            else gameController.selectPlayerMonster(cardAddress);
        } else if (spellMatcher.groupCount() == 1) {
            int cardAddress = Integer.parseInt(spellMatcher.group("cardAddress"));
            if (opponentMatcher.groupCount() == 1)
                gameController.selectOpponentSpellOrTrap(cardAddress);
            else gameController.selectPlayerSpellOrTrap(cardAddress);
        } else if (fieldMatcher.groupCount() == 1) {
            if (opponentMatcher.groupCount() == 1)
                gameController.selectOpponentFieldCard();
            else gameController.selectPlayerFieldCard();
        } else if (handMatcher.groupCount() == 1) {
            int cardAddress = Integer.parseInt(handMatcher.group("cardAddress"));
            gameController.selectPlayerHandCard(cardAddress);
        } else printInvalidCommand();
    }

    public void printCardDeselected() {
        System.out.println("card deselected");
    }

    public void printNoCardSelected() {
        System.out.println("no card is selected yet");
    }

    public void printInvalidCommand() {
        System.out.println("invalid command");
    }

    public void printCardSelected() {
        System.out.println("card selected");
    }

    public void printNotFoundCard() {
        System.out.println("no card found in the given position");
    }

    public void printAddedNewCard(Card card) {
        System.out.println("new card added to the hand : " + card.getCardName());
    }

    public void printInvalidSelection() {
        System.out.println("invalid selection");
    }

    public void printCantAttack() {
        System.out.println("you can’t attack with this card");
    }

    public void printWrongPhase() {
        System.out.println("you can’t do this action in this phase");
    }

    public void printAlreadyAttacked() {
        System.out.println("this card already attacked");
    }

    public void printNoCardToAttack() {
        System.out.println("there is no card to attack here");
    }

    public void printOpponentMonsterDestroyed(int attackDifference) {
        System.out.println("your opponent’s monster is destroyed " +
                "and your opponent receives " + attackDifference + " battle damage");
    }

    public void printBothMonstersDestroyed() {
        System.out.println("both you and your opponent monster cards" +
                " are destroyed and no one receives damage");
    }

    public void printYourCardIsDestroyed(int attackDifference) {
        System.out.println("Your monster card is destroyed " +
                "and you received " + attackDifference + " battle damage");
    }

    public void printNoCardDestroyed() {
        System.out.println("no card is destroyed");
    }

    public void printDefensePositionDestroyed() {
        System.out.println("the defense position monster is destroyed");
    }

    public void printNoCardDestroyedYouReceivedDamage(int attackDifference) {
        System.out.println("no card is destroyed and you" +
                " received " + attackDifference + " battle damage");
    }

    public void printDefensePositionDestroyedHidden(String rivalMonsterName) {
        printOpponentCardsName(rivalMonsterName);
        printDefensePositionDestroyed();
    }

    public void printNoCardDestroyedHidden(String rivalMonsterName) {
        printOpponentCardsName(rivalMonsterName);
        printNoCardDestroyed();
    }

    public void printNoCardDestroyedYouReceivedDamageHidden(int attackDifference, String rivalMonsterName) {
        printOpponentCardsName(rivalMonsterName);
        printNoCardDestroyedYouReceivedDamage(attackDifference);
    }

    private void printOpponentCardsName(String name) {
        System.out.println("opponent’s monster card was " + name);
    }

    public void printYourOpponentReceivesDamage(int attackNum) {
        System.out.println("your opponent receives " + attackNum + " battle damage");
    }

    public void printCantSummon() {
        System.out.println("you can’t summon this card");
    }

    public void printNotInMainPhase() {
        System.out.println("action not allowed in this phase");
    }

    public void printMonsterZoneFull() {
        System.out.println("monster card zone is full");
    }

    public void printAlreadySetOrSummon() {
        System.out.println("you already summoned/set on this turn");
    }

    public void printSummonSuccessfully() {

    }

    public void printNoMonsterOnThisAddress() {
        System.out.println("there no monsters one this address");
    }

    public void printThereArentEnoughMonsterForTribute() {
        System.out.println("there are not enough cards for tribute");
    }

    public void printNoMonsterInOneOfThisAddress() {
        System.out.println("there no monsters on one of this addresses");
    }

    public void getTribute() {
        int number = ViewMaster.scanner.nextInt();
        run("select --monster " + number);
    }

    public void printCantSet() {
        System.out.println("you can’t set this card");
    }

    public void printSetSuccessfully() {
        System.out.println("set successfully");
    }

    public void printCantChangePosition() {
        System.out.println("you can’t change this card position");
    }

    public void printAlreadyInWantedPosition() {
        System.out.println("this card is already in the wanted position");
    }

    public void printAlreadyChangePositionInThisTurn() {
        System.out.println("you already changed this card position in this turn");
    }

    public void printChangeSetSuccessfully() {
        System.out.println("doesHaveChangePositionInThisTurn");
    }

    public void printCantFlipSummon() {
        System.out.println("you can’t flip summon this card");
    }

}
