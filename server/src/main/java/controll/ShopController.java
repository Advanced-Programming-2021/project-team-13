package controll;

import com.gilecode.yagson.YaGson;
import model.cards.Card;
import model.csv.MonsterCSV;
import model.csv.SpellTrapCSV;
import model.players.User;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.TreeMap;
import java.util.zip.Deflater;

public class ShopController {
    private final TreeMap<String, String> cards = new TreeMap<>();

    public ShopController() {
    }

    public String[] getDetail(String cardName) {
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

    public void buyCard(User user, Card card, int cardPrice) {
        user.addCard(card);
        user.addMoney(-cardPrice);
    }

    public String getImage(Type type) {
        String s = new YaGson().toJson(ImageLoader.getCardsImage(), type);
        byte[] bytes = s.getBytes(StandardCharsets.UTF_8);
        Deflater deflater = new Deflater();
        deflater.setInput(bytes);
        deflater.finish();
        byte[] d = new byte[100];
        deflater.deflate(d);
        return d.toString();
    }
}

