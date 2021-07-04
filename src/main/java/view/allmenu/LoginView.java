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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import model.menuItems.CustomButton;

import java.awt.event.ActionEvent;
import java.io.IOException;


public class LoginView {

    private final LoginController loginController;
    public TextField usernameField;
    public PasswordField passwordField;
    public Label notifLabel;
    public AnchorPane pane;


    public LoginView() {
        loginController = new LoginController(this);
    }

    public void initialize(){
        CustomButton exit = new CustomButton("Quit Game", this::exit);
        CustomButton register= new CustomButton("Register", ()->{
            try {
                goToRegisterMenu();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        CustomButton login = new CustomButton("Login",()->{
            try {
                goToMainMenu();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        usernameField.setFont(Font.font("verdana", FontWeight.BOLD,22));
        passwordField.setFont(Font.font("verdana", FontWeight.BOLD,22));
        exit.setTranslateY(600);
        exit.setTranslateX(100);
        exit.setStyle("-fx-background-color: crimson");
        login.setTranslateX(1280-350);
        login.setTranslateY(200);
        login.setStyle("-fx-background-color:#187c10");
        register.setTranslateY(130);
        register.setTranslateX(200);
        register.setStyle("-fx-background-color:#1F51FF");
        pane.getChildren().addAll(exit,login,register);


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
        ((Stage) pane.getScene().getWindow()).setScene(new Scene(root));
    }

    public void goToRegisterMenu() throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/fxml/RegisterMenu.fxml"));
        ((Stage) pane.getScene().getWindow()).setScene(new Scene(parent));
    }

    public void exit() {
        ((Stage)pane.getScene().getWindow()).close();
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