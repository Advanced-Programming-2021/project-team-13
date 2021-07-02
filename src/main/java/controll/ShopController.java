package controll;

import enums.CardType;
import model.csv.MonsterCSV;
import model.csv.SpellTrapCSV;
import model.players.User;
import view.ViewMaster;
import view.allMenu.ShopView;


import java.io.FileNotFoundException;
import java.util.TreeMap;

public class ShopController {
    private final ShopView shopView;
    private TreeMap<String, String> cards = new TreeMap<>();

    public ShopController(ShopView shopView) {
        this.shopView = shopView;
        try {
            MonsterCSV.getNameAndDescription(cards);
        } catch (FileNotFoundException ignored) {
        }
        try {
            SpellTrapCSV.getNameAndDescription(cards);
        } catch (Exception ignored) {
        }
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
        shopView.showAllCards(cards);
    }

    public void showCard(String cardName) {
        MonsterCSV monsterCSV = null;
        try {
            monsterCSV = MonsterCSV.findMonster(cardName);
        } catch (Exception ignore) {
        }
        if (monsterCSV == null) {
            SpellTrapCSV spellTrapCSV = null;
            try {
                spellTrapCSV = SpellTrapCSV.findSpellTrap(cardName);
            } catch (Exception ignore) {
            }
            if (spellTrapCSV == null)
                shopView.printInvalidCard();
            else
                shopView.printSpellAndTrap(spellTrapCSV.getIcon(), spellTrapCSV.getDescription(), spellTrapCSV.getName(),
                        spellTrapCSV.getType() == CardType.SPELL ? "Spell" : "Trap");

        } else
            shopView.printMonsterCard(monsterCSV.getAttack(), monsterCSV.getDefence(), monsterCSV.getLevel(), monsterCSV.getName()
                    , monsterCSV.getDescription(), monsterCSV.getMonsterType());

    }


}

