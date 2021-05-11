package controll;

import view.Menu;
import model.players.User;
import view.allmenu.LoginView;
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
            new User(username, password, nickname);
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

    public void loginFirst(String menuName){
        if (menuName.equalsIgnoreCase("Login")){
            loginView.printLoginFirst();
        } else if (menuName.equalsIgnoreCase("Main")){
            loginView.printLoginFirst();
        } else if (menuName.equalsIgnoreCase("Deck")){
            loginView.printLoginFirst();
        } else if (menuName.equalsIgnoreCase("Shop")){
            loginView.printLoginFirst();
        } else if (menuName.equalsIgnoreCase("Duel")){
            loginView.printLoginFirst();
        } else if (menuName.equalsIgnoreCase("Scoreboard")){
            loginView.printLoginFirst();
        } else if (menuName.equalsIgnoreCase("Profile")){
            loginView.printLoginFirst();
        } else if (menuName.equalsIgnoreCase("Import/Export")){
            loginView.printLoginFirst();
        } else {
            loginView.printInvalidCommand();
        }
    }
}
