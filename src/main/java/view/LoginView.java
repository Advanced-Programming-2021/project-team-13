package view;

import controll.LoginController;
import enums.Menu;
import model.User;


public class LoginView {

    private LoginController loginController;

    LoginView() {
        loginController = new LoginController();
    }

    public void printUsernameExists(String username) {
        System.out.println("user with username " + username + " already exists");
    }

    public void printInvalidCommand() {
        System.out.println("invalid command");
    }

    public void printNicknameExists(String nickname) {
        System.out.println("user with nickname " + nickname + " already exists");
    }

    public void printUserCreated() {
        System.out.println("user created successfully!");
    }

    public void printLoginSuccessful() {
        System.out.println("user logged in successfully!");
    }

    public void printInvalidUsernameOrPassword() {
        System.out.println("Username or password didn’t match!\n");
    }

    public void showLoginMenu() {

    }

    public void run(String command) {
        if (command.matches(Regex.REGISTER))
            registerUser(command);
        else if (command.matches(Regex.LOGIN))
            loginUser(command);
        else printInvalidCommand();
    }

    public void registerUser(String command) {
        String username = Regex.findUsername(command);
        String password = Regex.findPassword(command);
        String nickname = Regex.findNickname(command);
        if (username != null && password != null && nickname != null) {
            if (User.getUserByUsername(username) != null) {
                printUsernameExists(username);
                return;
            }
            if (User.getUserByNickname(nickname) != null) {
                printNicknameExists(nickname);
                return;
            }
            User newUser = new User(username, password, nickname);
            printLoginSuccessful();
        } else printInvalidCommand();
    }

    public void loginUser(String command) {
        String username = Regex.findUsername(command);
        String password = Regex.findPassword(command);
        if (username != null && password != null) {
            User user = User.getUserByUsername(username);
            if (user == null || !user.getPassword().equals(password)) {
                printInvalidUsernameOrPassword();
                return;
            }
            printLoginSuccessful();
            ViewMaster.setCurrentMenu(Menu.MAIN_MENU);
            printLoginSuccessful();
        } else printInvalidCommand();
    }


}