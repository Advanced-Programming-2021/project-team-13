package controll;

import org.json.JSONObject;
import view.ViewMaster;
import view.allmenu.ChatRoomView;

import java.util.HashMap;
import java.util.Map;

public class ChatRoomController {
    private ChatRoomView chatRoomView;
    public ChatRoomController(ChatRoomView chatRoomView){
        this.chatRoomView = chatRoomView;
    }

    public void sendMessageToAll(String message) {
        try {
            String command = "send message : ---"+message+"--- token : "+ViewMaster.getCurrentUserToken();
            ViewMaster.dataOutputStream.writeUTF(command);
            ViewMaster.dataOutputStream.flush();
        }catch (Exception e){

        }
    }
}
