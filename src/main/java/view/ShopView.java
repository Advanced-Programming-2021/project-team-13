package view;

import controll.ShopController;
import model.Card;

import java.util.ArrayList;
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

    public void showAllCards(ArrayList<Card> allCards) {
        for (Card card : allCards) {
            System.out.println(card.getCardName() + ":" + card.getPrice());
        }
    }

    public void run(String command) {
        if (command.matches(Regex.BUY_CARD))
            buyCard(Regex.getInputMatcher(command, Regex.BUY_CARD));
        else if (command.matches(Regex.SHOP_SHOW_ALL))
            shopController.sortAllCards();
        else
            System.out.println("invalid command");
    }

    private void buyCard(Matcher inputMatcher) {
        String cardName = inputMatcher.group("cardName");
        shopController.buyCard(cardName);
    }
}
