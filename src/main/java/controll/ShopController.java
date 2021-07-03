package controll;

import enums.CardType;
import model.csv.MonsterCSV;
import model.csv.SpellTrapCSV;
import model.players.User;
import view.ViewMaster;
import view.allmenu.ShopView;


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
    public String[] getDetail(String cardName) {
        MonsterCSV monsterCSV = null;
        try {
            monsterCSV = MonsterCSV.findMonster(cardName);
        } catch (Exception ignore) {
        }
        if (monsterCSV == null) {
            SpellTrapCSV spellTrapCSV = null;
            try {
                spellTrapCSV = SpellTrapCSV.findSpellTrap(cardName);
                assert spellTrapCSV != null;
                String detail = spellTrapCSV.getPrice() + "-" + "Name: " + spellTrapCSV.getName() +
                        "\nType: " + spellTrapCSV.getType() + "\nIcon: " + spellTrapCSV.getIcon();
                return detail.split("-");
            } catch (Exception ignore) {
            }
        } else {
            String detail = monsterCSV.getPrice() + "-" + "Name: " + monsterCSV.getName() +
                    "\nLevel: " + monsterCSV.getLevel() +
                    "\nType: " + monsterCSV.getCardType() +
                    "\nAttack: " + monsterCSV.getAttack() + "\nDefense: " + monsterCSV.getDefence();
            return detail.split("-");
        }
        return null;
    }
}

