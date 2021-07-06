package view.allmenu;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

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
    public void initialize() {
        gridPane.getChildren().clear();
//        for (int i = 0; i < ; i++) {
//
//        }
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        centerPane.getChildren().stream().filter(x -> x instanceof StackPane).forEach(x -> ((StackPane) x).setStyle("-fx-background-image: url('/shopImage/Monsters/AxeRaider.jpg') " +
                ";-fx-background-repeat:no-repeat;-fx-background-size:cover,auto"));
        centerPane.getChildren().stream()
                .filter(x -> x instanceof GridPane).map(x -> (GridPane) x)
                .forEach(x -> x.getChildren()
                        .forEach(a -> a.setStyle("-fx-background-image: url('/shopImage/Monsters/AxeRaider.jpg') " +
                                ";-fx-background-repeat:no-repeat no-repeat;-fx-background-size:cover,auto")));
//        for (Node child : centerPane.getChildren()) {
//            if(child instanceof StackPane) {
//                ImageView bbb = new ImageView(background);
//                ((StackPane) child).getChildren().add(bbb);
//            }
//            if(child instanceof GridPane){
//                for (Node node : (((GridPane) child).getChildren())) {
//                    if(node instanceof StackPane){
//                        ImageView bbb = new ImageView(background);
//                        ((StackPane) node).getChildren().add(bbb);
//                    }
//                }
//            }
//        }
    }
}
