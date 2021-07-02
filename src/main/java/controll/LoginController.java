package controll;

import view.Menu;
import model.players.User;
import view.ViewMaster;
import view.allMenu.LoginView;


public class LoginController {
    private final LoginView loginView;

    public LoginController(LoginView loginView) {
        this.loginView = loginView;
    }

    public boolean registerUser(String username, String password, String nickname) {
        if (username != null && password != null && nickname != null) {
            if (User.getUserByUsername(username) != null) {
                loginView.printUsernameExists(username);
                return false;
            }
            if (User.getUserByNickname(nickname) != null) {
                loginView.printNicknameExists(nickname);
                return false;
            }
            new User(username, password, nickname);
            loginView.printUserCreated();
            return true;
        }return false;
    }

    public boolean loginUser(String username, String password) {
        if (username != null && password != null) {
            User user = User.getUserByUsername(username);
            if (user == null || !user.getPassword().equals(password)) {
                loginView.printInvalidUsernameOrPassword();
                return false;
            }
            loginView.printLoginSuccessful();
            ViewMaster.setUser(user);
            ViewMaster.setCurrentMenu(Menu.MAIN_MENU);
            return true;
        } return false;
    }

}
