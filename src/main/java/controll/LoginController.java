package controll;

import enums.Menu;
import model.User;
import view.LoginView;
import view.Regex;
import view.ViewMaster;

public class LoginController {
    private final LoginView loginView;

    public LoginController(LoginView loginView) {
        this.loginView = loginView;
    }

    public void registerUser(String username, String password, String nickname) {
        if (username != null && password != null && nickname != null) {
            if (User.getUserByUsername(username) != null) {
                loginView.printUsernameExists(username);
                return;
            }
            if (User.getUserByNickname(nickname) != null) {
                loginView.printNicknameExists(nickname);
                return;
            }
            User newUser = new User(username, password, nickname);
            loginView.printUserCreated();
        } else loginView.printInvalidCommand();
    }

    public void loginUser(String username, String password) {
        if (username != null && password != null) {
            User user = User.getUserByUsername(username);
            if (user == null || !user.getPassword().equals(password)) {
                loginView.printInvalidUsernameOrPassword();
                return;
            }
            loginView.printLoginSuccessful();
            ViewMaster.setUser(user);
            ViewMaster.setCurrentMenu(Menu.MAIN_MENU);
        } else loginView.printInvalidCommand();
    }

//    public User newValidUsername(String username) {
//
//    }
//
//    public boolean checkUserPassword(User user, String password) {
//
//    }
}
