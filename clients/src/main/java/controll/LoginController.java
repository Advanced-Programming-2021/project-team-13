package controll;

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
            try {
                String message = "login " + username + " " + password;
                ViewMaster.dataOutputStream.writeUTF(message);
                ViewMaster.dataOutputStream.flush();
                String[] answer = ViewMaster.dataInputStream.readUTF().split(" ");
                switch (answer[0]) {
                    case "success":
                        ViewMaster.setCurrentMenu(Menu.MAIN_MENU);
                        ViewMaster.setCurrentUserToken(answer[1]);
                        loginView.printLoginSuccessful();
                        return true;
                    case "invalid":
                        loginView.printInvalidUsernameOrPassword();
                    default:
                        return false;
                }
            } catch (Exception ignored) {
            }
        }
        return false;
    }
}
