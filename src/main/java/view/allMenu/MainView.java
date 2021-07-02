package view.allMenu;

import controll.MainController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MainView {
    private final MainController mainController;
    public Button duelButton;
    public Button deckButton;
    public Button scoreboardButton;
    public Button profileButton;
    public Button shopButton;
    public Button importButton;
    public Button logoutButton;

    public MainView() {
        mainController = new MainController(this);
    }

    public void printMenuNavigationImpossible() {//?????????????????????????
        System.out.println("menu navigation is not possible");
    }

    public void goToDuelMenu(ActionEvent event) {

    }

    public void goToDeckMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/LoginMenu.fxml"));
        ((Stage) logoutButton.getScene().getWindow()).setScene(new Scene(root));
    }

    public void goToScoreBoard(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/LoginMenu.fxml"));
        ((Stage) logoutButton.getScene().getWindow()).setScene(new Scene(root));
    }

    public void goToProfile(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/ProfileMenu.fxml"));
        ((Stage) logoutButton.getScene().getWindow()).setScene(new Scene(root));
    }

    public void goToShop(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/LoginMenu.fxml"));
        ((Stage) logoutButton.getScene().getWindow()).setScene(new Scene(root));
    }

    public void goToImport(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/LoginMenu.fxml"));
        ((Stage) logoutButton.getScene().getWindow()).setScene(new Scene(root));
    }

    public void goToLoginMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/LoginMenu.fxml"));
        ((Stage) logoutButton.getScene().getWindow()).setScene(new Scene(root));
    }


}
