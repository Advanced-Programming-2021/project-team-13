package controll;

import com.gilecode.yagson.YaGson;
import com.google.common.reflect.TypeToken;
import javafx.scene.image.Image;
import model.players.User;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.zip.GZIPOutputStream;


public class CommandController {
    private static final HashMap<String, User> loggedInUser = new HashMap<>();
    private final LoginController loginController = new LoginController();
    private final RegisterController registerController = new RegisterController();
    private final ScoreboardController scoreboardController = new ScoreboardController();
    private final ShopController shopController = new ShopController();
    private final Type type = new TypeToken<HashMap<String, Image>>(){}.getType();
    public static HashMap<String, User> getLoggedInUser() {
        return loggedInUser;
    }

    public String run(String command) {

        if (command.startsWith("login"))
            return loginController.loginUser(command.split(" ")[1], command.split(" ")[2]);
        else if (command.startsWith("register"))
            return registerController.registerUser(command.split(" ")[1], command.split(" ")[2], command.split(" ")[3]);
        else if (command.startsWith("scoreboard"))
            return scoreboardController.sortAllUsers();
        else if (command.startsWith("shop"))
            return shopController.getImage(type);

        return "";
    }
}
