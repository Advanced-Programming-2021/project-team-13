
import controll.ImageLoader;
import controll.json.UserJson;
import javafx.application.Application;
import javafx.stage.Stage;
import model.players.User;
import view.SceneController;
import view.ViewMaster;

public class Main extends Application {

    public static void main(String[] args) {
        new ImageLoader().start();
        UserJson userJson = new UserJson();
        userJson.start();
        try {
            userJson.join();
            launch(args);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ViewMaster.getViewMaster().run();
        primaryStage.setOnHiding(event -> UserJson.update());
        primaryStage.setResizable(false);
    }

    @Override
    public void stop() {
        UserJson.update();
    }
}