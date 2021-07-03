package view.allmenu;

import com.sun.istack.internal.NotNull;
import controll.LoginController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;


public class LoginView {

    private final LoginController loginController;
    public Button loginButton;
    public TextField usernameField;
    public PasswordField passwordField;
    public Button registerButton;
    public Button exitButton;
    public Label notifLabel;
    public Button regButton;
    public TextField regUsernameField;
    public PasswordField regPasswordField;
    public TextField regNicknameField;
    public Button backButton;
    public Label regNotifLabel;
    public AnchorPane pane;

    public LoginView() {
        loginController = new LoginController(this);
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

    public void goToMainMenu(ActionEvent event) throws IOException {
        if(!login())
            return;
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainMenu.fxml"));
        ((Stage)loginButton.getScene().getWindow()).setScene(new Scene(root));
    }

    public void goToRegisterMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/RegisterMenu.fxml"));
        ((Stage)registerButton.getScene().getWindow()).setScene(new Scene(root));
    }

    public void exit(ActionEvent event) {
        ((Stage)exitButton.getScene().getWindow()).close();
    }

    @FXML
    private boolean register(ActionEvent event) {
        String username = regUsernameField.getText();
        String password = regPasswordField.getText();
        String nickname = regNicknameField.getText();
        if(username.isEmpty()||password.isEmpty()||nickname.isEmpty()){
            regNotifLabel.setText("please fill all needed required fields");
            return false;
        }
        return loginController.registerUser(username, password, nickname);
    }

    public void goBack(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/LoginMenu.fxml"));
        ((Stage)backButton.getScene().getWindow()).setScene(new Scene(root));
    }

    private boolean login() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        return loginController.loginUser(username, password);
    }

    public void printUsernameExists(String username) {
        regNotifLabel.setText("user with username " + username + " already exists");
    }

    public void printNicknameExists(String nickname) {
        regNotifLabel.setText("user with nickname " + nickname + " already exists");
    }

    public void printUserCreated() {
        regNotifLabel.setStyle("-fx-text-fill: green ");
        regNotifLabel.setText("user created successfully!");
    }

    public void printLoginSuccessful() {
       notifLabel.setText("user logged in successfully!");
    }

    public void printInvalidUsernameOrPassword() {
        notifLabel.setText("Username and password didn’t match!");
    }



}