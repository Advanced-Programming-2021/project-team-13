package view.allmenu;

import controll.DuelController;
import view.Regex;

import java.util.regex.Matcher;

public class DuelView {
    private final DuelController duelController;

    public DuelView() {
        duelController = new DuelController(this);
    }

    public void run(String command) {
        if (command.startsWith("duel")) {
            Matcher newMatcher = Regex.getInputMatcher(command, Regex.NEW);
            Matcher roundMatcher = Regex.getInputMatcher(command, Regex.ROUNDS);
            Matcher aiMatcher = Regex.getInputMatcher(command, Regex.AI);
            Matcher duelerMatcher = Regex.getInputMatcher(command, Regex.SECOND_PLAYER);
            if (aiMatcher.groupCount() == 1 && newMatcher.groupCount() == 1 && roundMatcher.groupCount() == 1) {
                int rounds = Integer.parseInt(roundMatcher.group("rounds"));
                duelController.runAIGame(rounds);
            }
            else if (duelerMatcher.groupCount() == 1 && newMatcher.groupCount() == 1 && roundMatcher.groupCount() == 1){
                int rounds = Integer.parseInt(roundMatcher.group("rounds"));
                String playerUsername = duelerMatcher.group("playerUsername");
                duelController.runDuelGame(rounds , playerUsername);
            } else printInvalidCommand();
        } else printInvalidCommand();
    }

    public void printUserNotFound() {
        System.out.println("there is no player with this username");
    }

    public void printNoActiveDeck(String username) {
        System.out.println(username + " has no active deck");
    }

    public void printInvalidDeck(String username) {
        System.out.println(username + "’s deck is invalid");
    }

    public void printInvalidNumberOfRound() {
        System.out.println("number of rounds is not supported");
    }

    public void printInvalidCommand() {
        System.out.println("invalid command");
    }
}
