package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class SceneController {

    private static void loadUrl(Stage primaryStage, URL url) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent);
        URL cursor = SceneController.class.getResource("/cardCreatorImages/mouse4.png");
        Image image = new Image(cursor.toExternalForm());
        scene.setCursor(new ImageCursor(image));
        primaryStage.setScene(scene);
    }

    public static void startMainMenu(Stage primaryStage) throws IOException {
        URL url = SceneController.class.getResource("/fxml/MainMenu.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent);
        primaryStage.setScene(scene);
    }

    public static void startDeckMenu(Stage primaryStage) throws IOException {
        URL url = SceneController.class.getResource("/fxml/DeckMenu.fxml");
        loadUrl(primaryStage, url);
    }

    public static void startCardCreator(Stage primaryStage) throws IOException {
        URL url = SceneController.class.getResource("/fxml/CardCreatorMenu.fxml");
        loadUrl(primaryStage, url);
    }

    public static void startDuelMenu(Stage primaryStage) throws IOException {
        URL url = SceneController.class.getResource("/fxml/DuelMenu.fxml");
        loadUrl(primaryStage, url);
    }
}
