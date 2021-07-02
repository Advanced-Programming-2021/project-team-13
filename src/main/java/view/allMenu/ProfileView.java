package view.allMenu;

import controll.ProfileController;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import view.Menu;
import view.Regex;
import view.ViewMaster;

import java.io.IOException;
import java.util.Optional;

public class ProfileView {
    private final ProfileController profileController;
    public Button changeNicknameBtn;
    public Button changePasswordBtn;
    public Button backBtn;


    public ProfileView() {
        profileController = new ProfileController(this);
    }

    public void printNicknameChanged() {
        Alert alert = new Alert(AlertType.INFORMATION,"nickname changed successfully!",ButtonType.OK);
        alert.setHeaderText("Successful");
        alert.showAndWait();
    }

    public void printNicknameExists(String newNickname) {
        Alert alert = new Alert(AlertType.ERROR,"user with nickname " + newNickname +
                " already exists",ButtonType.OK);
        alert.setHeaderText("Failed");
        alert.showAndWait();
    }

    public void printPasswordChanged() {
        Alert alert = new Alert(AlertType.INFORMATION,"password changed successfully!",ButtonType.OK);
        alert.setHeaderText("Successful");
        alert.showAndWait();
    }

    public void printInvalidPassword() {
        Alert alert = new Alert(AlertType.ERROR,"current password is invalid",ButtonType.OK);
        alert.setHeaderText("Failed");
        alert.showAndWait();
    }

    public void printSamePassword() {
        Alert alert = new Alert(AlertType.ERROR,"please enter a new password",ButtonType.OK);
        alert.setHeaderText("Failed");
        alert.showAndWait();
    }

    @FXML
    private void changePassword() {
        TextInputDialog textInputDialog = new TextInputDialog();
        textInputDialog.setTitle("Enter current password");
        textInputDialog.setHeaderText("Current");
        textInputDialog.showAndWait();
        if(textInputDialog.getEditor().getText().isEmpty()) {
            emptyInputError();
            return;
        }
        String currentPassword=textInputDialog.getEditor().getText();
        TextInputDialog textInputDialog2 = new TextInputDialog();
        textInputDialog2.setTitle("Enter new password");
        textInputDialog2.showAndWait();
        String newPassword = textInputDialog2.getEditor().getText();
        profileController.changePassword(currentPassword, newPassword);
//        if (!newPassword.matches("\\w+")) {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Operation failed");
//            alert.setContentText("invalid password format");
//            alert.showAndWait();
//            return;
//        }
//        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
//                "are you sure?", ButtonType.OK, ButtonType.CANCEL);
//        Optional<ButtonType> result = alert.showAndWait();
//        if (result.isPresent() && result.get() == ButtonType.OK) {
//            System.out.println(currentUser.getUsername());
//            currentUser.setPassword(newPassword);
//            Alert confirmedAlert = new Alert(Alert.AlertType.INFORMATION);
//            confirmedAlert.setTitle("Operation successful");
//            confirmedAlert.setContentText("password changed successfully");
//            confirmedAlert.showAndWait();
//        }
    }

    private void emptyInputError() {
        Alert alert = new Alert(AlertType.ERROR,"Empty Input",ButtonType.OK);
        alert.showAndWait();
    }

    @FXML
    private void changeNickName() {
        TextInputDialog textInputDialog = new TextInputDialog();
        textInputDialog.setTitle("Enter new nickName");
        textInputDialog.setHeaderText("new Nickname");
        textInputDialog.showAndWait();
        if(textInputDialog.getEditor().getText().isEmpty()) {
            emptyInputError();
            return;
        }
        profileController.changeNickname(textInputDialog.getEditor().getText());
    }

    public void goBack(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainMenu.fxml"));
        ((Stage)backBtn.getScene().getWindow()).setScene(new Scene(root));
    }
}
