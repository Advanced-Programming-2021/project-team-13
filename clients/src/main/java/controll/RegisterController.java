package controll;

import view.ViewMaster;
import view.allmenu.RegisterView;

public class RegisterController {
    private final RegisterView registerView;

    public RegisterController(RegisterView registerView) {
        this.registerView = registerView;
    }

    public boolean registerUser(String username, String password, String nickname) {
        if (username != null && password != null && nickname != null) {
            try {
                String message = "register " + username + " " + password + " " + nickname;
                ViewMaster.dataOutputStream.writeUTF(message);
                ViewMaster.dataOutputStream.flush();
                String answer = ViewMaster.dataInputStream.readUTF();
                switch (answer) {
                    case "success":
                        registerView.printUserCreated();
                        return true;
                    case "user exist":
                        registerView.printUsernameExists(username);
                    case "nickname exist":
                        registerView.printNicknameExists(nickname);
                    default:
                        return false;
                }
            } catch (Exception ignored) {

            }
        }
        return false;
    }
}
