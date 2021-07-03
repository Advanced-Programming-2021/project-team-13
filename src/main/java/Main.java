
import controll.json.UserJson;
import javafx.application.Application;
import javafx.stage.Stage;
import view.SceneController;
import view.ViewMaster;

public class Main extends Application {
    public static void main(String[] args) {
        new UserJson().loadDataBase();
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
//        ViewMaster.getViewMaster().run();
        SceneController.startDeckMenu(primaryStage);
        primaryStage.show();
    }
}