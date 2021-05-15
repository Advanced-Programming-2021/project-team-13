package controll.json;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
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
            String json = mapper.toJson(User.getAllUsers(), User.class);
            jsonFile = new FileWriter("UserJson.txt");
            jsonFile.write(json);
            jsonFile.close();
        } catch (Exception ignored) {
        }
    }

    public void loadDataBase() {
        String json = "";
        try {
            YaGson mapper = new YaGson();
            json = new String(Files.readAllBytes(Paths.get("UserJson.txt")));
            Type type = new TypeToken<ArrayList<User>>() {
            }.getType();
            User.setAllUsers(mapper.fromJson(json, type));

        } catch (IOException ignored) {
        }
    }

}