package controll;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatMenuController {
    public String sendMessageToAll(String command) {
        Pattern pattern = Pattern.compile("^send message : ---(.*)--- token : (\\S+)$");
        Matcher matcher = pattern.matcher(command);
        if (matcher.find()) {
            String message = matcher.group(1);
            String token = matcher.group(2);

        }
        return "invalid";
    }
}
