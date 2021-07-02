package view.allMenu;

import controll.ProfileController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import view.Menu;
import view.Regex;
import view.ViewMaster;

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

    public void run(String command) {
        if (command.matches(Regex.CHANGE_NICKNAME))
            changeNickName(Regex.getInputMatcher(command, Regex.CHANGE_NICKNAME));
        else if (command.matches(Regex.CHANGE_PASSWORD1))
            changePassword(Regex.getInputMatcher(command, Regex.CHANGE_PASSWORD1));
        else if (command.matches(Regex.CHANGE_PASSWORD2))
            changePassword(Regex.getInputMatcher(command, Regex.CHANGE_PASSWORD2));
        else if (command.matches(Regex.EXIT_MENU))
            ViewMaster.setCurrentMenu(Menu.MAIN_MENU);
        else
            System.out.println("invalid command");

    }

    @FXML
    private void changePassword() {
        String currentPassword = inputMatcher.group("currentPassword");
        String newPassword = inputMatcher.group("newPassword");
        profileController.changePassword(currentPassword, newPassword);
    }

    @FXML
    private void changeNickName() {
        profileController.changeNickname(inputMatcher.group("nickname"));
    }

}
