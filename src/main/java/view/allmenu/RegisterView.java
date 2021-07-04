package view.allmenu;

import controll.RegisterController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.menuItems.CustomButton;

import java.io.IOException;

public class RegisterView {
    private final RegisterController registerController;
    public TextField regUsernameField;
    public PasswordField regPasswordField;
    public TextField regNicknameField;
    public Label regNotifLabel;
    public AnchorPane pane;

    public RegisterView() {
        registerController = new RegisterController(this);
    }

    public void initialize() {
        StackPane notif=new StackPane(regNotifLabel);
        notif.setPrefHeight(50);
        notif.setStyle("-fx-background-color: black");
        notif.setTranslateX(1280-370);
        notif.setTranslateY(50);
        VBox vBox = new VBox(20,
                getNodes());
        vBox.setTranslateX(1280 - 300);
        vBox.setTranslateY(450);
        VBox fields = new VBox(45,regNicknameField,regUsernameField,regPasswordField);
        fields.prefHeight(200);
        fields.prefWidth(300);
        fields.setTranslateX(1280 -280);
        fields.setTranslateY(150);
        fields.setStyle("-fx-fit-to-height: true");
        pane.getChildren().addAll(vBox,fields,notif);
    }

    private Node[] getNodes() {
        return new Node[]{new CustomButton("Register", this::register),
                new CustomButton("Back", () -> {
                    try {
                        goBack();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                })};
    }

    @FXML
    private boolean register() {
        String username = regUsernameField.getText();
        String password = regPasswordField.getText();
        String nickname = regNicknameField.getText();
        if (username.isEmpty() || password.isEmpty() || nickname.isEmpty()) {
            regNotifLabel.setText("please fill all needed required fields");
            return false;
        }
        return registerController.registerUser(username, password, nickname);
    }

    public void goBack() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/LoginMenu.fxml"));
        ((Stage) pane.getScene().getWindow()).setScene(new Scene(root));
    }

    public void printUsernameExists(String username) {
        regNotifLabel.setStyle("-fx-fill: red");
        regNotifLabel.setText("user with username " + username + " already exists");
    }

    public void printNicknameExists(String nickname) {
        regNotifLabel.setStyle("-fx-fill: red");
        regNotifLabel.setText("user with nickname " + nickname + " already exists");
    }

    public void printUserCreated() {
        regNotifLabel.setStyle("-fx-text-fill: green ");
        regNotifLabel.setText("user created successfully!");
    }
}
