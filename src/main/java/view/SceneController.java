package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class SceneController {

    public static void startDeckMenu(Stage primaryStage) throws IOException {
        URL url = SceneController.class.getResource("/fxml/DeckMenu.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent);
        primaryStage.setScene(scene);
    }

    public static void startMainMenu(Stage primaryStage) throws IOException {
        URL url = SceneController.class.getResource("/fxml/MainMenu.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent);
        primaryStage.setScene(scene);
    }
    public static void startGameMenu(Stage primaryStage) throws IOException{
        URL url = SceneController.class.getResource("/fxml/Game.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent);
        primaryStage.setScene(scene);
    }
}
