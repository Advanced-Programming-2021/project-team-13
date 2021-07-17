package controll;

import model.players.User;

public class ProfileController {

    public String changeNickname(User user, String nickname) {
        if (User.getUserByNickname(nickname) != null)
            return "nickname exists";
        user.setNickname(nickname);
        return "nickname changed";
    }

    public String changePassword(User user , String currentPassword, String newPassword) {
        if (!user.getPassword().equals(currentPassword))
            return "invalid current password";
        else if (user.getPassword().equals(newPassword))
            return "same password";
        user.setPassword(newPassword);
        return "password changed";
    }
}
