package view.allmenu;

import controll.GameController;
import enums.Phase;
import model.cards.Card;
import model.players.Player;
import view.Regex;
import view.ViewMaster;

import java.util.regex.Matcher;

public class GameView {
    private static Phase currentPhase;
    private GameController phaseController;

    public GameView() {
        phaseController = new GameController(this);
        currentPhase = Phase.DRAW_PHASE;
    }

    public void run(String command) {
        if (command.equals("surrender"))
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
        else if ()
    }

    public void printPhaseName() {
        System.out.println(currentPhase.getPhaseName()); /// needs to be completed!!!
    }


    public void printAddedNewCard(Card card) {
        System.out.println("new card added to the hand : " + card.getCardName());
    }

    public void showTurn() {
        Player nextPlayer = phaseController.getRivalPlayer();
        System.out.println("its " + nextPlayer + "’s turn");
    }

    // public void printErrors(){ looks fishy!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    //
    // }


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

    public String inputMonsterOrSpellName(){
        return ViewMaster.scanner.nextLine().trim();
    }

    public void printIvalidCardName(){
        //to do
    }

}