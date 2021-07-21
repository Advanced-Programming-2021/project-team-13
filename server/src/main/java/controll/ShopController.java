package controll;

import model.csv.MonsterCSV;
import model.csv.SpellTrapCSV;
import model.players.User;

import java.util.TreeMap;

public class ShopController {
    private final TreeMap<String, String> cards = new TreeMap<>();

    public ShopController() {
    }

    public String getDetail(String cardName) {
        MonsterCSV monsterCSV = null;
        try {
            monsterCSV = MonsterCSV.findMonster(cardName);
        } catch (Exception ignore) {
        }
        if (monsterCSV == null) {
            SpellTrapCSV spellTrapCSV;
            try {
                spellTrapCSV = SpellTrapCSV.findSpellTrap(cardName);
                assert spellTrapCSV != null;
                return spellTrapCSV.getPrice() + "-" + "Name: " + spellTrapCSV.getName() +
                        "\nType: " + spellTrapCSV.getType() + "\nIcon: " + spellTrapCSV.getIcon();
            } catch (Exception ignore) {
            }
        } else {
            return monsterCSV.getPrice() + "-" + "Name: " + monsterCSV.getName() +
                    "\nLevel: " + monsterCSV.getLevel() +
                    "\nType: " + monsterCSV.getCardType() +
                    "\nAttack: " + monsterCSV.getAttack() + "\nDefense: " + monsterCSV.getDefence();
        }
        return null;
    }


    public String buy(String[] command) {
        User user = CommandController.getLoggedInUser().get(command[2]);
        String cardName = command[1];
        int cardPrice = Integer.parseInt(command[3]);
        user.setMoney(user.getMoney() - cardPrice);
        if (user.getCardNameToNumber().containsKey(cardName))
            user.getCardNameToNumber().put(cardName, user.getCardNameToNumber().get(cardName) + 1);
        else
            user.getCardNameToNumber().put(cardName, 1);
        return "success";
    }
}

