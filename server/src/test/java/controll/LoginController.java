package controll;

import model.players.User;
import view.Menu;
import view.ViewMaster;
import view.allmenu.LoginView;


public class LoginController {
    private final LoginView loginView;

    public LoginController(LoginView loginView) {
        this.loginView = loginView;
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
        }
        return false;
    }
}
