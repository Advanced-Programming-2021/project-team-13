package controll;

import model.players.User;

import java.util.UUID;


public class LoginController {
    public String loginUser(String username, String password) {
        if (username != null && password != null) {
            User user = User.getUserByUsername(username);
            if (user == null || !user.getPassword().equals(password))
                return "invalid";
            String token = UUID.randomUUID().toString();
            synchronized (CommandController.getLoggedInUser()) {
                CommandController.getLoggedInUser().put(token, user);
            }
            return "success" + " " + token;
        }
        return "";
    }
}
