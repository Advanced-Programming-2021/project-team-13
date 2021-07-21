package controll;
import model.players.User;

import java.util.HashMap;


public class CommandController {
    private static final HashMap<String, User> loggedInUser = new HashMap<>();
    private final LoginController loginController = new LoginController();
    private final RegisterController registerController = new RegisterController();
    private final ScoreboardController scoreboardController = new ScoreboardController();
    private final ProfileController profileController = new ProfileController();
    private final ShopController shopController = new ShopController();
    private final ChatMenuController chatMenuController = new ChatMenuController();
    public static HashMap<String, User> getLoggedInUser() {
        return loggedInUser;
    }

    public String run(String command) {
        String[] commands = command.split(" ");
        if (command.startsWith("login"))
            return loginController.loginUser(command.split(" ")[1], command.split(" ")[2]);
        else if (command.startsWith("register"))
            return registerController.registerUser(command.split(" ")[1], command.split(" ")[2], command.split(" ")[3]);
        else if (command.startsWith("scoreboard"))
            return scoreboardController.sortAllUsers();
        else if (command.startsWith("money"))
            return String.valueOf(loggedInUser.get(command.split(" ")[1]).getMoney());
        else if (command.startsWith("send message"))
            return chatMenuController.sendMessageToAll(command);
        else if (command.startsWith("shop"))
            return shopController.getDetail(command.split(" ")[1]);
        else if (command.startsWith("buy"))
            return shopController.buy(command.split(" "));
        else if (command.startsWith("change password"))
            return profileController.changePassword(loggedInUser.get(commands[4]), commands[2], commands[3]);
        else if (command.startsWith("change nickname"))
            return profileController.changeNickname(loggedInUser.get(commands[3]), commands[2]);
        return "";
    }

}
