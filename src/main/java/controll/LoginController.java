package controll;

import enums.Menu;
import model.User;
import view.LoginView;
import view.Regex;
import view.ViewMaster;

public class LoginController {
    private LoginView loginView;

    public void registerUser(String command) {
        String username = Regex.findUsername(command);
        String password = Regex.findPassword(command);
        String nickname = Regex.findNickname(command);
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
            loginView.printLoginSuccessful();
        } else loginView.printInvalidCommand();
    }

    public void loginUser(String command) {
        String username = Regex.findUsername(command);
        String password = Regex.findPassword(command);
        if (username != null && password != null) {
            User user = User.getUserByUsername(username);
            if (user == null || !user.getPassword().equals(password)) {
                loginView.printInvalidUsernameOrPassword();
                return;
            }
            loginView.printLoginSuccessful();
            ViewMaster.setCurrentMenu(Menu.MAIN_MENU);
            loginView.printLoginSuccessful();
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
