package view.allmenu;

import controll.ProfileController;
import view.Menu;
import view.Regex;
import view.ViewMaster;

import java.util.regex.Matcher;

public class ProfileView {
    private final ProfileController profileController;

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

    private void changePassword(Matcher inputMatcher) {
        inputMatcher.find();
        String currentPassword = inputMatcher.group("currentPassword");
        String newPassword = inputMatcher.group("newPassword");
        profileController.changePassword(currentPassword, newPassword);
    }

    private void changeNickName(Matcher inputMatcher) {
        inputMatcher.find();
        profileController.changeNickname(inputMatcher.group("nickname"));
    }

}
