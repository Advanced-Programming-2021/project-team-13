
import controll.ImageLoader;
import controll.json.UserJson;
import javafx.application.Application;
import javafx.stage.Stage;
import model.players.User;
import view.SceneController;
import view.ViewMaster;

public class Main extends Application {

    public static void main(String[] args) {
        new ImageLoader().load();
        new UserJson().loadDataBase();
        new User("a", "a", "a");
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ViewMaster.getViewMaster().run();
//        SceneController.startDeckMenu(primaryStage);
//        primaryStage.show();
    }

    @Override
    public void stop() {
        new UserJson().update();
    }
}