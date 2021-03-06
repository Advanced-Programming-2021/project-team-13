package view;

import controll.GameController;
import enums.Phase;
import model.Card;
import model.Player;
import sun.applet.Main;

import java.util.regex.Matcher;

public class GameView {
    private static Phase currentPhase;
    private GameController phaseController;

    GameView() {
        phaseController = new GameController(this);
        currentPhase = Phase.DRAW_PHASE;
    }

    public void run(String command) {
        if (command.equals("surrender"))
            surrender();
        else if (command.equals("show graveyard"))
            showGraveyard();
        if (currentPhase == Phase.DRAW_PHASE)
            drawPhase(command);
        else if (currentPhase == Phase.STANDBY_PHASE)
            standbyPhase(command);
        else if (currentPhase == Phase.MAIN_PHASE_1)
            mainPhase1(command);
        else if (currentPhase == Phase.BATTLE_PHASE)
            battlePhase(command);
        else if (currentPhase == Phase.MAIN_PHASE_2)
            mainPhase2(command);
        else if (currentPhase == Phase.END_PHASE)
            endPhase(command);
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

    private void standbyPhase(String command) {
    }

    private void mainPhase1(String command) {
        if (command.equals("summon"))
            summon();
        else if (command.equals("set"))
            set();
        else if (command.matches(Regex.SET_POSITION))
            setPosition(Regex.getInputMatcher(command, Regex.SET_POSITION));
        else if (command.equals("flip-summon"))
            flipSummon();
    }


    private void battlePhase(String command) {
        if (command.matches(Regex.ATTACK))
            attack(Regex.getInputMatcher(command, Regex.ATTACK));
        else if (command.equals("attack direct"))
            directAttack();
        //   else if ()
    }

    private void mainPhase2(String command) {
    }

    private void endPhase(String command) {

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
