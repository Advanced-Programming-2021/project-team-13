package controll;

import model.csv.MonsterCSV;
import model.csv.SpellTrapCSV;
import model.players.User;
import view.allmenu.ShopView;
import view.ViewMaster;

import java.io.FileNotFoundException;
import java.util.TreeMap;

public class ShopController {
    private final ShopView shopView;
    private TreeMap<String, String> cards = null;

    public ShopController(ShopView shopView) {
        this.shopView = shopView;
    }

    public void buyCard(String cardName) {
        MonsterCSV monsterCard = null;
        SpellTrapCSV spellOrTrap = null;
        try {
            monsterCard = MonsterCSV.findMonster(cardName);
            spellOrTrap = SpellTrapCSV.findSpellTrap(cardName);
        } catch (FileNotFoundException e) {
        }
        if (monsterCard == null
                && spellOrTrap == null) {
            shopView.printInvalidCard();
            return;
        }
        if (monsterCard != null) buy(monsterCard.getPrice(), cardName);
        else buy(spellOrTrap.getPrice(), cardName);
    }

    private void buy(int price, String cardName) {
        User user = ViewMaster.getUser();
        if (user.getMoney() < price) shopView.printNotEnoughMoney();
        else {
            user.addMoney(-price);
            user.addCard(cardName);
        }
    }

    public void sortAllCards() {
        if (cards == null) {
            cards = new TreeMap<>();
            try {
                MonsterCSV.getNameAndDescription(cards);

            } catch (FileNotFoundException e) {
            }
            try {
                SpellTrapCSV.getNameAndDescription(cards);
            } catch (Exception e) {
            }
        }
        shopView.showAllCards(cards);
    }
}

