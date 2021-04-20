package view;
import controll.ProfileController;

public class ProfileView {
    private ProfileController profileController;
    private String command;

    ProfileView(){
        profileController = new ProfileController();
    }
    public void printNicknameChanged() {

    }

    public void printNicknameExists(String newNickname) {

    }

    public void printPasswordChanged() {

    }

    public void printInvalidPassword() {

    }

    public void printSamePassword() {

    }

    public void run(String command) {
    }
}
