package view.allMenu;

import controll.DuelController;
import model.players.User;
import view.Regex;
import view.ViewMaster;

import java.util.regex.Matcher;

public class DuelView {
    private final DuelController duelController;

    public DuelView() {
        duelController = new DuelController(this);
    }

    public void run(String command) {
        if (command.matches(Regex.DUEL)) {
            Matcher newMatcher = Regex.getInputMatcher(command, Regex.NEW);
            Matcher roundMatcher = Regex.getInputMatcher(command, Regex.ROUNDS);
            Matcher aiMatcher = Regex.getInputMatcher(command, Regex.AI);
            Matcher duelerMatcher = Regex.getInputMatcher(command, Regex.SECOND_PLAYER);
            if (newMatcher.find(0) && roundMatcher.find(0) && aiMatcher.find(0)) {
                int rounds = Integer.parseInt(roundMatcher.group("rounds"));
                duelController.runAIGame(rounds);
            } else if (newMatcher.find(0) && roundMatcher.find(0) && duelerMatcher.find(0)) {
                int rounds = Integer.parseInt(roundMatcher.group("rounds"));
                String playerUsername = duelerMatcher.group("playerUsername");
                duelController.validateDuelGame(rounds, playerUsername);
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

    public DuelController getDuelController() {
        return duelController;
    }

    public int inputStonePaperScissor(User user) {
        System.out.println("hey " + user.getNickname() + " !\nplease choose one to start Game: stone , paper , scissor");
        int numberToReturn;
        do {
            String input = ViewMaster.scanner.nextLine().trim().toLowerCase();
            switch (input) {
                case "paper":
                    numberToReturn = 1;
                    break;
                case "stone":
                    numberToReturn = 2;
                    break;
                case "scissor":
                    numberToReturn = 3;
                    break;
                default:
                    numberToReturn = 4;
                    System.out.println("please enter correct name again");
            }
        } while (numberToReturn == 4);
        return numberToReturn;
    }

    public void printEqual() {
        System.out.println("Equal!\ntry again!");
    }

}
