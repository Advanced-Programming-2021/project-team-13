package view.allmenu;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.effect.Bloom;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.players.Player;
import model.players.User;

public class FookingTest extends Application {
    public GridPane gridPane;
    public BorderPane motherPane;
    public AnchorPane centerPane;
    public StackPane stackPane;

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(LoginView.class.getResource("/fxml/Game.fxml"));
        primaryStage.setResizable(false);
        primaryStage.setTitle("you-gey-oh?:D");
        primaryStage.setScene(new Scene( loader.load()));
        primaryStage.show();
    }
    public void initialize(){
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        String url= "/shopImage/Monsters/AxeRaider.jpg";
        Image background = new Image(url,90,124,false,true);
        for (Node child : centerPane.getChildren()) {
            if(child instanceof StackPane) {
                ImageView bbb = new ImageView(background);
                ((StackPane) child).getChildren().add(bbb);
            }
            if(child instanceof GridPane){
                for (Node node : (((GridPane) child).getChildren())) {
                    if(node instanceof StackPane){
                        ImageView bbb = new ImageView(background);
                        ((StackPane) node).getChildren().add(bbb);
                    }
                }
            }
        }
    }
}
