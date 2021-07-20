import controll.ImageLoader;
import javafx.application.Application;
import javafx.stage.Stage;
import view.ViewMaster;

public class ClientMain extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        new ImageLoader().start();
        ViewMaster.getViewMaster().run();
        primaryStage.setResizable(false);
    }

    @Override
    public void stop() throws Exception {
        ViewMaster.dataOutputStream.close();
    }
}