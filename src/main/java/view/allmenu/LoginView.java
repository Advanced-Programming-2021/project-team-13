package view.allmenu;

import controll.LoginController;
import view.Menu;
import view.Regex;
import view.ViewMaster;

import java.util.regex.Matcher;


public class LoginView {

    private final LoginController loginController;

    public LoginView() {
        loginController = new LoginController(this);
    }

    public void run(String command) {
        if (command.matches(Regex.ENTER_MENU))   // needs to be tested !!!! fishy!!!!
            loginFirst(command);
        else if (command.equals("menu exit"))
            ViewMaster.setCurrentMenu(Menu.EXIT_MENU);
        else if (command.matches(Regex.REGISTER))
            register(command);
        else if (command.matches(Regex.LOGIN))
            login(command);
        else printInvalidCommand();
    }

    private void loginFirst(String command){
        Matcher matcher = Regex.getInputMatcher(command , Regex.ENTER_MENU);
        if (matcher.find()) {
            String menuName = matcher.group("menuName");
            loginController.loginFirst(menuName);
        }
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
        System.out.println("Username and password didn’t match!");
    }

    public void printLoginFirst() {
        System.out.println("please login first");
    }
}