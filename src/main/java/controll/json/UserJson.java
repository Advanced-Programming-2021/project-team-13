package controll.json;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import model.cards.Card;
import model.cards.Trap;
import model.players.User;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class UserJson {

    public void update() {
        YaGson mapper = new YaGson();
        FileWriter jsonFile;
        try {
//            for (User user : User.getAllUsers()) {
//                for (Card card : user.getCards()) {
//                    card.setCardOwner(null);
//                    if (card instanceof Trap) {
//                        ((Trap) card).setTrapAction(null);
//                    }
//                }
//            }
            String json = mapper.toJson(User.getAllUsers(), User.class);
            jsonFile = new FileWriter("src/main/resources/Users.json");
            jsonFile.write(json);
            jsonFile.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadDataBase() {
        String json;
        try {
            YaGson mapper = new YaGson();
            json = new String(Files.readAllBytes(Paths.get("src/main/resources/Users.json")));
            Type type = new TypeToken<ArrayList<User>>() {
            }.getType();
            User.setAllUsers(mapper.fromJson(json, type));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
