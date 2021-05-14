package view.allmenu;

import controll.ShopController;
import view.Menu;
import view.Regex;
import view.ViewMaster;

import java.util.TreeMap;
import java.util.regex.Matcher;

public class ShopView {
    private final ShopController shopController;

    public ShopView() {
        shopController = new ShopController(this);
    }

    public void printNotEnoughMoney() {
        System.out.println("not enough money");
    }

    public void printInvalidCard() {
        System.out.println("there is no card with this name");
    }

    public void showAllCards(TreeMap<String, String> allCards) {
        for (String name : allCards.keySet()) {
            System.out.println(name + ":" + allCards.get(name));
        }
    }

    public void run(String command) {
        if (command.matches(Regex.BUY_CARD))
            buyCard(Regex.getInputMatcher(command, Regex.BUY_CARD));
        else if (command.matches(Regex.SHOP_SHOW_ALL))
            shopController.sortAllCards();
        else if (command.matches("card show (\\w+)"))
            showCard(command);
        else if (command.matches(Regex.EXIT_MENU))
            ViewMaster.setCurrentMenu(Menu.MAIN_MENU);
        else
            System.out.println("invalid command");
    }

    private void showCard(String command) {
        Matcher matcher = Regex.getInputMatcher(command, "card show (\\w+)");
        matcher.find();
        String cardName = matcher.group(1);
        shopController.showCard(cardName);

    }

    private void buyCard(Matcher inputMatcher) {
        inputMatcher.find();
        String cardName = inputMatcher.group("cardName");
        shopController.buyCard(cardName);
    }

    public void printMonsterCard(int attackNum, int defenseNum, int level, String cardName, String cardDescription, String monsterType) {
        System.out.println("Name: " + cardName + "\nLevel: " + level
                + "\nType: " + monsterType + "\nATK: " + attackNum
                + "\nDEF: " + defenseNum + "Description: " + cardDescription);
    }

    public void printSpellAndTrap(String icon, String cardDescription, String cardName, String type) {
        System.out.println("Name: " + cardName + "Spell"
                + "\nType: " + type + "Description: " + cardDescription);
    }
}
