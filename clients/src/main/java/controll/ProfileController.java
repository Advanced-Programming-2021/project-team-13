package controll;

import view.ViewMaster;
import view.allmenu.ProfileView;

import java.util.ArrayList;

public class ProfileController {
    private final ProfileView profileView;

    public ProfileController(ProfileView profileView) {
        this.profileView = profileView;
    }

    public void changeNickname(String nickname) {
        if (isNewNickname(nickname)) {
           /* ViewMaster.getUser().setNickname(nickname);*/
            profileView.printNicknameChanged();
        } else
            profileView.printNicknameExists(nickname);

    }

    public boolean isNewNickname(String nickname) {
  /*      ArrayList<User> users = User.getAllUsers();
        for (User user : users)
            if (nickname.equals(user.getNickname())) return false;*/
        return true;
    }

    public void changePassword(String currentPassword, String newPassword) {
        if (isPasswordCorrect(currentPassword))
            if (checkNewPassword(newPassword, currentPassword))
                profileView.printSamePassword();
            else {
                /*ViewMaster.getUser().setPassword(newPassword);*/
                profileView.printPasswordChanged();
            }
        else
            profileView.printInvalidPassword();
    }

    public boolean isPasswordCorrect(String currentPassword) {
        /*return currentPassword.equals(ViewMaster.getUser().getPassword());*/return true;
    }

    public boolean checkNewPassword(String newPassword, String currentPassword) {
        return currentPassword.equals(newPassword);
    }
}
