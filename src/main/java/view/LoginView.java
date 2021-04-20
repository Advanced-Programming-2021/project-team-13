package view;

import controll.LoginController;

import java.util.regex.Matcher;

public class LoginView {
    private LoginController loginController;
    private String command;

    LoginView() {
        loginController = new LoginController();
    }

    public void printUsernameExists(String username) {

    }

    public void printNicknameExists(String nickname) {

    }

    public void printUserCreated() {

    }

    public void printLoginSuccessful() {

    }

    public void printInvalidUsernameOrPassword() {

    }

    public void showLoginMenu() {

    }

    public void run(String command) {
        if (command.matches(Regex.registerUser))
            registerUser(Regex.getInputMatcher(command, Regex.registerUser));


    }


}