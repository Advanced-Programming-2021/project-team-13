
import controll.json.UserJson;
import enums.CardType;
import enums.Face;
import javafx.application.Application;
import javafx.stage.Stage;
import model.cards.Card;
import view.ViewMaster;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ViewMaster.getViewMaster().run();
    }
}