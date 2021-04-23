package controll;

import CSV.MonsterCSV;
import CSV.SpellTrapCSV;
import model.Card;
import model.User;
import view.ShopView;
import view.ViewMaster;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeMap;

public class ShopController {
    private final ShopView shopView;
    public ShopController(ShopView shopView) {
        this.shopView = shopView;
    }

    public void buyCard(String cardName) {
        Card card = Card.findCardByName(cardName);
        if (card == null) {
            shopView.printInvalidCard();
            return;
        }
        User user = ViewMaster.getUser();
        if (user.getMoney() < card.getPrice()) shopView.printNotEnoughMoney();
        else {
            user.addMoney(-card.getPrice());
            user.addCard(card);
        }
    }

    public void sortAllCards() {
        ArrayList<Card> allCards = Card.getAllCards();
        allCards.sort(Comparator.comparing(Card::getCardName));
        shopView.showAllCards(allCards);
    }
}
