package controll;

import view.ViewMaster;
import view.allmenu.ProfileView;

import java.io.IOException;

public class ProfileController {
    private final ProfileView profileView;

    public ProfileController(ProfileView profileView) {
        this.profileView = profileView;
    }

    public void changeNickname(String nickname) {
        if (nickname != null) {
            try {
                String message = "change nickname " + nickname + " " + ViewMaster.getCurrentUserToken();
                ViewMaster.dataOutputStream.writeUTF(message);
                ViewMaster.dataOutputStream.flush();
                String[] answer = ViewMaster.dataInputStream.readUTF().split(" ");
                switch (answer[1]) {
                    case "exists":
                        profileView.printNicknameExists(nickname);
                        break;
                    case "changed":
                        profileView.printNicknameChanged();
                        break;
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }

    public void changePassword(String currentPassword, String newPassword) {
        if (currentPassword != null && newPassword != null){
            try {
                String message = "change password " + currentPassword + " " + newPassword + " " + ViewMaster.getCurrentUserToken();
                ViewMaster.dataOutputStream.writeUTF(message);
                ViewMaster.dataOutputStream.flush();
                String answer = ViewMaster.dataInputStream.readUTF();
                switch (answer){
                    case "invalid current password" :
                        profileView.printInvalidPassword();
                        break;
                    case "same password" :
                        profileView.printSamePassword();
                        break;
                    case "password changed" :
                        profileView.printPasswordChanged();
                        break;
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }
}
