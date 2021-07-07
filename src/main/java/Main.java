
import controll.ImageLoader;
import controll.json.UserJson;
import javafx.application.Application;
import javafx.stage.Stage;
import model.players.User;
import view.SceneController;
import view.ViewMaster;

public class Main extends Application {

    public static void main(String[] args) {
        ImageLoader.load();
        new UserJson().loadDataBase();
        User user = User.getUserByUsername("a");
        ViewMaster.setUser(user);
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
//        ViewMaster.getViewMaster().run();
        primaryStage.setOnHiding(event -> new UserJson().update());
        SceneController.startDeckMenu(primaryStage);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    @Override
    public void stop() {
        new UserJson().update();
    }
}