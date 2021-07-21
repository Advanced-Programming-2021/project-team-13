package view.allmenu;

import controll.ChatRoomController;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.menuItems.CustomButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ChatRoomView {
    public AnchorPane leftPane;
    public VBox btnVbox;
    public ScrollPane scrollPane;
    public VBox messageVbox;
    public TextField textBox;
    public Label notifLabel;
    public ImageView sendMessageImage;
    public AnchorPane rightPane;
    private ArrayList<HashMap<String, String>> hashMaps = new ArrayList<>();
    private ArrayList<HashMap<String, String>> marked = new ArrayList<>();
    private final ChatRoomController chatRoomController;

    public ChatRoomView(){
        chatRoomController=new ChatRoomController(this);
    }

    public void initialize() {
        messageVbox.setStyle("-fx-background-image: url('chatRoomPic/w2.jpg')");
        textBox.setStyle("-fx-background-image: url('chatRoomPic/w1.png')");
        leftPane.setStyle("-fx-background-image: url('chatRoomPic/w2.jpg')");
        sendMessageImage.setStyle("-fx-background-color:transparent");
        btnVbox.setSpacing(20);
        sendMessageImage.setOnMouseClicked(e -> sendMessage());
        CustomButton customButton = new CustomButton("back", () -> {
            try {
                goToMainMenu();
            } catch (IOException ignored) {
            }
        });
        customButton.setStyle("-fx-background-color:crimson");
        btnVbox.getChildren().addAll(customButton);
        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                getMessages();
            }
        };
        animationTimer.start();
    }

    private synchronized void getMessages() {
        try {
            chatRoomController.getAllMessages().stream().forEach(x -> {
                        if (!hashMaps.contains(x))
                            hashMaps.add(x);
            }
            );
            hashMaps.stream().forEach(x -> {
                if(!marked.contains(x)) {
                    marked.add(x);
                    String username = x.get("username");
                    String message = x.get("message");
                    StackPane stackPane = new StackPane();
                    Text text = new Text(username + " : " + message);
                    Rectangle bg = new Rectangle(250, 50, Color.WHITE);
                    text.setFont(Font.font("Comic Sans MS", 18));
                    stackPane.getChildren().addAll(bg, text);
                    stackPane.setAlignment(Pos.TOP_LEFT);
                    stackPane.setTranslateX(15);
                    messageVbox.getChildren().add(stackPane);
                }
            });
        } catch (Exception ignored) {

        }
    }

    private synchronized void sendMessage() {
        if (textBox.getText().isEmpty()) {
            notifLabel.setStyle("-fx-text-fill: red");
            notifLabel.setText("Empty message!");
            return;
        }
        String texts = textBox.getText();
        textBox.clear();
        chatRoomController.sendMessageToAll(texts);
    }

    private void goToMainMenu() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainMenu.fxml"));
        ((Stage) leftPane.getScene().getWindow()).setScene(new Scene(root));
    }
}
