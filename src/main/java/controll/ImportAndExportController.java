package controll;

import com.gilecode.yagson.YaGson;
import model.cards.Card;
import model.players.User;

import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ImportAndExportController {

    public boolean importCard(User user, String cardName) {
        YaGson mapper = new YaGson();
        try {
            String json = new String(Files.readAllBytes(Paths.get(cardName + ".json")));
            Card card = mapper.fromJson(json, Card.class);
            boolean hasCard = false;
            for (Card card1 : user.getAllCards()) {
                if (card1.getCardName().equals(card.getCardName())) {
                    hasCard = true;
                    break;
                }
            }
            if (!hasCard)
                user.addCard(card);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String exportCard(User user, Card card) {
        YaGson mapper = new YaGson();
        if (user.getCardNameToNumber().containsKey(card.getCardName())) {
            card.setImage(null);
            card.setCardOwner(null);
            String json = mapper.toJson(card);
            try {
                FileWriter FW = new FileWriter(card.getCardName() + ".json");
                FW.write(json);
                FW.close();
                return "crated";
            } catch (Exception e) {
                e.printStackTrace();
                return "failed";
            }
        } else {
            return "createCardFirst";
        }
    }
}
