package controll;

import model.players.User;

import java.util.UUID;

public class RegisterController {
    public synchronized String registerUser(String username, String password, String nickname) {
        if (username != null && password != null && nickname != null) {
            if (User.getUserByUsername(username) != null)
                return "user exist";
            if (User.getUserByNickname(nickname) != null)
                return "nickname exist";
            new User(username, password, nickname);
            return "success";
        }
        return "";
    }
}
