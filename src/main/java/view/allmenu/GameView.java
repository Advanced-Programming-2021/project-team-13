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
    }

    private void attack(Matcher inputMatcher) {

    }

    private void flipSummon() {
    }

    private void setPosition(Matcher inputMatcher) {
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

    public void printCardDeselected(){
        System.out.println("card deselected");
    }

    public void printNoCardSelected(){
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

}
