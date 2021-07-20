package controll;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerController {
    private final CommandController commandController = new CommandController();
    private ServerSocket serverSocket;

    {
        try {
            serverSocket = new ServerSocket(1111);
        } catch (IOException ignored) {
        }
    }

    public void run() {
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                new Thread(() -> {
                    try {
                        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                        while (true) {
                            String input = dataInputStream.readUTF();
                            System.out.println(input);
                            if (input.startsWith("logout")) {
                                CommandController.getLoggedInUser().remove(input.split(" ")[1]);
                                dataOutputStream.writeUTF("");
                                dataOutputStream.flush();
                                break;
                            }
                            String output = commandController.run(input);
                            System.out.println(output);
                            dataOutputStream.writeUTF(output);
                            dataOutputStream.flush();
                        }
                        dataOutputStream.close();
                        dataInputStream.close();
                    } catch (Exception ignored) {
                    }
                }).start();
            } catch (Exception ignored) {
            }
        }
    }
}
