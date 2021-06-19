package view.allmenu;

import controll.gameController.ChangeDeckController;
import model.Deck;
import model.players.Player;
import view.Regex;

import java.util.regex.Matcher;

public class ChangeDeckView {

    private ChangeDeckController changeDeckController;

    public ChangeDeckView(Player player, Deck deck) {
        changeDeckController = new ChangeDeckController(player, deck);
    }

    public void run(String command) {
        command = command.trim();
        if (command.matches("deck add card ([^-]+)")) {
            Matcher addCardMatcher = Regex.getInputMatcher(command, "deck add card ([^-]+)");
            addCardMatcher.find();
            changeDeckController.addCard(addCardMatcher.group(1));
        } else if (command.matches("deck remove card ([^-]+)")) {
            Matcher removeCardMatcher = Regex.getInputMatcher(command, "deck remove card [card name]");
            removeCardMatcher.find();
            changeDeckController.removeCard(removeCardMatcher.group(1));
        } else if (command.matches("exit")) {
            changeDeckController.checkExit();
        }
    }

    public void help() {
        System.out.println("Change Deck Menu");
        System.out.println("all commands : ");
        System.out.println("deck add card [card name]");
        System.out.println("deck remove card [card name]");
        System.out.println("exit");
    }
}
