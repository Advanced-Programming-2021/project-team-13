package view.allMenu;

import controll.ProfileController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import view.Menu;
import view.Regex;
import view.ViewMaster;

import java.util.Optional;

public class ProfileView {
    private final ProfileController profileController;
    public Button changeNicknameBtn;
    public Button changePasswordBtn;


    public ProfileView() {
        profileController = new ProfileController(this);
    }

    public void printNicknameChanged() {
        System.out.println("nickname changed successfully!");
    }

    public void printNicknameExists(String newNickname) {
        System.out.println("user with nickname " + newNickname + " already exists");

    }

    public void printPasswordChanged() {
        System.out.println("password changed successfully!");
    }

    public void printInvalidPassword() {
        System.out.println("current password is invalid");
    }

    public void printSamePassword() {
        System.out.println("please enter a new password");
    }

    @FXML
    private void changePassword() {
        TextInputDialog textInputDialog = new TextInputDialog();
        textInputDialog.setTitle("Enter current password");
        textInputDialog.setHeaderText("Current");
        textInputDialog.showAndWait();
        if(textInputDialog.getEditor().getText().isEmpty()){
            Alert alert = new Alert(AlertType.ERROR,"Empty Input",ButtonType.OK);
            alert.showAndWait();
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

    @FXML
    private void changeNickName() {
//        profileController.changeNickname(inputMatcher.group("nickname"));
    }

}
