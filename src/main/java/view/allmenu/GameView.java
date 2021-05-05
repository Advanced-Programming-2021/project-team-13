package view.allmenu;

import controll.GameController;
import enums.Phase;
import model.cards.Card;
import view.Regex;

import java.util.regex.Matcher;

public class GameView {
    private static Phase currentPhase;
    private final GameController gameController;

    public GameView() {
        gameController = new GameController(this);
        currentPhase = Phase.DRAW_PHASE;
    }

    public void run(String command) {
        if (command.startsWith("select"))
            selectOrDeselectCard(command);
        else if (command.equals("surrender"))
            surrender();
        else if (command.equals("show graveyard"))
            showGraveyard();
        else if (command.equals("summon"))
            summon();
        else if (command.equals("set"))
            set();
        else if (command.matches(Regex.SET_POSITION))
            setPosition(Regex.getInputMatcher(command, Regex.SET_POSITION));
        else if (command.equals("flip-summon"))
            flipSummon();
        else if (command.matches(Regex.ATTACK))
            attack(Regex.getInputMatcher(command, Regex.ATTACK));
        else if (command.equals("attack direct"))
            directAttack();
    }

    private void nextPhase(){

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

    private void set() {
    }

    private void summon() {
    }

    private void selectOrDeselectCard(String command){
        if (command.matches(Regex.DESELECT))
            deselectCard();
        else selectCard(command);
    }

    private void deselectCard(){
        gameController.deselectCard();
    }

    private void selectCard(String command) {
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

    public static Phase getCurrentPhase() {
        return currentPhase;
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

    public void printCardSelected(){
        System.out.println("card selected");
    }

    public void printNotFoundCard(){
        System.out.println("no card found in the given position");
    }

    public void printPhaseName() {
        System.out.println(currentPhase.getPhaseName());
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
}
