package view.allmenu;

import controll.LoginController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import model.menuItems.CustomButton;
import view.ViewMaster;

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
        StackPane stackPane = new StackPane(notifLabel);
        stackPane.prefWidth(50);
        notifLabel.setFont(Font.font("verdana",FontWeight.BOLD,18));
        notifLabel.setTextFill(Paint.valueOf("red"));
        stackPane.setStyle("-fx-background-color: black");
        stackPane.setTranslateX(1280-420);
        stackPane.setTranslateY(250);
//        stackPane.maxWidth(400);
//        stackPane.maxHeight(400);
        CustomButton exit = new CustomButton("Quit Game", ()->{
            ViewMaster.btnSoundEffect();
            exit();
        });
        CustomButton register= new CustomButton("Register", ()->{
            try {
                ViewMaster.btnSoundEffect();
                goToRegisterMenu();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        CustomButton login = new CustomButton("Login",()->{
            try {
                ViewMaster.btnSoundEffect();
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
        pane.getChildren().addAll(exit,login,register,stackPane);


    }

    public void setLogin() throws IOException {
        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader(LoginView.class.getResource("/fxml/LoginMenu.fxml"));
        Parent root = loader.load();
        primaryStage.setResizable(false);
        primaryStage.setTitle("yo-gi-oh");
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
        notifLabel.setStyle("-fx-fill: red");
        notifLabel.setText("Username and password didn’t match!");
    }



}