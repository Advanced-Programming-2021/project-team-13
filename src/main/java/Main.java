
import controll.json.UserJson;
import javafx.application.Application;
import javafx.stage.Stage;
import model.players.User;
import view.ViewMaster;

public class Main extends Application {
    public static void main(String[] args) {
        new UserJson().loadDataBase();
        new User("a","a","a");
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        ViewMaster.getViewMaster().run();
    }
    @Override
    public void stop(){
        new UserJson().update();
    }
}