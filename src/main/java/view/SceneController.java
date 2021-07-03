package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.allmenu.DeckView;

import java.io.IOException;
import java.net.URL;

public class SceneController {

    public static void startDeckMenu(Stage primaryStage) throws IOException {
        URL url = SceneController.class.getResource("/fxml/DeckMenu.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        Parent parent = fxmlLoader.load();
//        DeckView deckView = fxmlLoader.getController();
//        deckView.init();
        Scene scene = new Scene(parent);
        primaryStage.setScene(scene);
    }
}
