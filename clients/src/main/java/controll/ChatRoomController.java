package controll;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import view.ViewMaster;
import view.allmenu.ChatRoomView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChatRoomController {
    private ChatRoomView chatRoomView;
    public ChatRoomController(ChatRoomView chatRoomView){
        this.chatRoomView = chatRoomView;
    }

    public synchronized void sendMessageToAll(String message) {
        try {
            String command = "send message : ---"+message+"--- token : "+ViewMaster.getCurrentUserToken();
            ViewMaster.dataOutputStream.writeUTF(command);
            ViewMaster.dataOutputStream.flush();
        }catch (Exception e){
        }
    }
    public synchronized ArrayList<HashMap<String, String>> getAllMessages(){
        try {
            String command = "get messages";
            ViewMaster.dataOutputStream.writeUTF(command);
            ViewMaster.dataOutputStream.flush();
            String input = ViewMaster.dataInputStream.readUTF();
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<HashMap<String, String>>>() {
            }.getType();
            return gson.fromJson(input,type);
        }catch (Exception e){
        }
        return null;
    }
}
