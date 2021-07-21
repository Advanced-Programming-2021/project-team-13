package controll;


import com.gilecode.yagson.com.google.gson.Gson;
import com.gilecode.yagson.com.google.gson.GsonBuilder;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatMenuController {
    public synchronized String sendMessageToAll(String command) {
        Pattern pattern = Pattern.compile("^send message : ---(.*)--- token : (\\S+)$");
        Matcher matcher = pattern.matcher(command);
        if (matcher.find()) {
            String message = matcher.group(1);
            String token = matcher.group(2);
            HashMap<String, String> messages = new HashMap<>();
            messages.put("username",CommandController.getLoggedInUser().get(token).getUsername());
            messages.put("message"
                    , message);
            CommandController.messages.add(messages);
            return "successful";
        }
        return "invalid";
    }

    public synchronized String getAllMessages() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        return gson.toJson(CommandController.messages);
    }
}
