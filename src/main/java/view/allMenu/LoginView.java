package view.allMenu;

import controll.LoginController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import view.Menu;
import view.Regex;
import view.ViewMaster;

import java.io.IOException;
import java.util.regex.Matcher;


public class LoginView {

    private final LoginController loginController;
    public Button loginButton;
    public TextField usernameField;
    public PasswordField passwordField;
    public Button registerButton;
    public Button exitButton;
    public Label notifLabel;

    public LoginView() {
        loginController = new LoginController(this);
    }

    public void setLogin() throws IOException {
        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader(LoginView.class.getResource("/fxml/loginMenu.fxml"));
        Parent root = loader.load();
        primaryStage.setResizable(false);
        primaryStage.setTitle("you-gey-oh?:D");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public void goToMainMenu(ActionEvent event) throws IOException {
        if(!login())
            return;
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/mainMenu.fxml"));
        ((Stage)loginButton.getScene().getWindow()).setScene(new Scene(root));
    }

    public void goToRegisterMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/registerMenu.fxml"));
        ((Stage)registerButton.getScene().getWindow()).setScene(new Scene(root));
    }

    public void exit(ActionEvent event) {
        ((Stage)exitButton.getScene().getWindow()).close();
    }

    private boolean register() {
        String username = "";
        String password = "";
        String nickname = "";
        return loginController.registerUser(username, password, nickname);
    }

    private boolean login() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        return loginController.loginUser(username, password);
    }

    public void printUsernameExists(String username) {
        notifLabel.setText("user with username " + username + " already exists");
    }

    public void printNicknameExists(String nickname) {
        notifLabel.setText("user with nickname " + nickname + " already exists");
    }

    public void printUserCreated() {
        notifLabel.setText("user created successfully!");
    }

    public void printLoginSuccessful() {
       notifLabel.setText("user logged in successfully!");
    }

    public void printInvalidUsernameOrPassword() {
        notifLabel.setText("Username and password didn’t match!");
    }


}