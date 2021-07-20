package controll;

import model.players.User;

import java.util.HashMap;


public class CommandController {
    private static final HashMap<String, User> loggedInUser = new HashMap<>();
    private final LoginController loginController = new LoginController();
    private final RegisterController registerController = new RegisterController();
    private final ScoreboardController scoreboardController = new ScoreboardController();
    private final ShopController shopController = new ShopController();

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
        else if (command.startsWith("money"))
            return String.valueOf(loggedInUser.get(command.split(" ")[1]).getMoney());
        else if (command.startsWith("shop"))
            return shopController.getDetail(command.split(" ")[1]);
        else if (command.startsWith("buy"))
            return shopController.buy(command.split(" "));
        return "";
    }
}
