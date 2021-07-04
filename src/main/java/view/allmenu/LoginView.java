package view.allmenu;

import controll.LoginController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;


public class LoginView {

    private final LoginController loginController;
    public Button loginButton;
    public TextField usernameField;
    public PasswordField passwordField;
    public Button registerButton;
    public Button exitButton;
    public Label notifLabel;
    public AnchorPane pane;


    public LoginView() {
        loginController = new LoginController(this);
    }

    public void initialize() throws IOException {

    }

    public void setLogin() throws IOException {
        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader(LoginView.class.getResource("/fxml/LoginMenu.fxml"));
        Parent root = loader.load();
        primaryStage.setResizable(false);
        primaryStage.setTitle("you-gey-oh?:D");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public void goToMainMenu() throws IOException {
        if (!login())
            return;
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainMenu.fxml"));
        ((Stage) loginButton.getScene().getWindow()).setScene(new Scene(root));
    }

    public void goToRegisterMenu() throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/fxml/RegisterMenu.fxml"));
        ((Stage) registerButton.getScene().getWindow()).setScene(new Scene(parent));
    }

    public void exit() {
        ((Stage)exitButton.getScene().getWindow()).close();
    }


    private boolean login() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        return loginController.loginUser(username, password);
    }

    public void printLoginSuccessful() {
       notifLabel.setText("user logged in successfully!");
    }

    public void printInvalidUsernameOrPassword() {
        notifLabel.setText("Username and password didn’t match!");
    }



}