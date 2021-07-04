package controll;

import model.players.User;
import view.allmenu.RegisterView;

public class RegisterController {
    private final RegisterView registerView;

    public RegisterController(RegisterView registerView) {
        this.registerView = registerView;
    }

    public boolean registerUser(String username, String password, String nickname) {
        if (username != null && password != null && nickname != null) {
            if (User.getUserByUsername(username) != null) {
                registerView.printUsernameExists(username);
                return false;
            }
            if (User.getUserByNickname(nickname) != null) {
                registerView.printNicknameExists(nickname);
                return false;
            }
            new User(username, password, nickname);
            registerView.printUserCreated();
            return true;
        }
        return false;
    }
}
