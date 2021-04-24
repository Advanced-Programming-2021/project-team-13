package view;

import controll.LoginController;
import enums.Menu;
import model.User;


public class LoginView {

    private LoginController loginController;

    LoginView() {
        loginController = new LoginController(this);
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
        System.out.println("Username or password didn’t match!");
    }

    private void printLoginFirst() {
        System.out.println("please login first");
    }

    public void run(String command) {
        if (command.matches(Regex.ENTER_MENU))   // needs to be tested !!!! fishy!!!!
            printLoginFirst();
        else if (command.equals("menu exit"))
            ViewMaster.setCurrentMenu(Menu.EXIT_MENU);
        else if (command.matches(Regex.REGISTER))
            register(command);
        else if (command.matches(Regex.LOGIN))
            login(command);
        else printInvalidCommand();
    }

    private void register(String command) {
        String username = Regex.findUsername(command);
        String password = Regex.findPassword(command);
        String nickname = Regex.findNickname(command);
        loginController.registerUser(username, password, nickname);
    }

    private void login(String command) {
        String username = Regex.findUsername(command);
        String password = Regex.findPassword(command);
        loginController.loginUser(username, password);
    }


}