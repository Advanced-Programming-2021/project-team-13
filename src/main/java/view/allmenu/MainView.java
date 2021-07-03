package view.allmenu;

import controll.MainController;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class MainView {
    private final MainController mainController;
    public BorderPane pane;
    public AnimationTimer animationTimer;
    private Image background1;
    private Image background2;
    private Image background3;
    private ImageView imageView;
    public MainView() {
        mainController = new MainController(this);
    }

    private static class MenuItem extends StackPane {
        MenuItem(String name, Runnable action) {
            LinearGradient gradient = new LinearGradient(
                    0, 0.5, 1, 0.5, true, CycleMethod.NO_CYCLE,
                    new Stop(0.1, Color.web("black", 0.75)),
                    new Stop(1.0, Color.web("black", 0.15)));
            Rectangle bg = new Rectangle(250, 35, gradient);
            Text text = new Text(name);
            text.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 22));
            setOnMouseClicked(e->{action.run(); });
            text.fillProperty().bind(
                    Bindings.when(hoverProperty()).then(Color.WHITE).otherwise(Color.GREY)
            );
            getChildren().addAll(bg, text);
        }
    }

    public void initialize() {

       background1 = new Image(getClass().getResource("/mainMenuImages/1.jpg").toExternalForm(),
                1280, 720, false, true);
        background2 = new Image(getClass().getResource("/mainMenuImages/2.jpg").toExternalForm(),
                1280, 720, false, true);
        background3 = new Image(getClass().getResource("/mainMenuImages/3.jpg").toExternalForm(),
                1280, 720, false, true);
        VBox box = new VBox(20,
                setNodes()
        );
        box.setTranslateX(1280 - 300);
        box.setTranslateY(200);
        box.setBackground(new Background(new BackgroundFill(
                Color.web("black", 0.6), null, null)
        ));
        imageView=new ImageView();
        imageView.setImage(background1);
        pane.getChildren().addAll(
                imageView,
                box);
        timelineHandler();
    }

    public void timelineHandler(){
                Timeline tenSecondsWonder = new Timeline(
                        new KeyFrame(Duration.seconds(10),
                                new EventHandler<ActionEvent>() {

                                    @Override
                                    public void handle(ActionEvent event) {
                                        changeBackground();
                                    }
                                }));
                tenSecondsWonder.setCycleCount(Timeline.INDEFINITE);
                tenSecondsWonder.play();
    }

    private void changeBackground() {
        if(imageView.getImage().equals(background1))
            imageView.setImage(background2);
        else if(imageView.getImage().equals(background2))
            imageView.setImage(background3);
        else if(imageView.getImage().equals(background3))
            imageView.setImage(background1);
    }

    private Node[] setNodes() {
        return new Node[]{new MenuItem("duel", () -> {
            goToDuelMenu();
        }),
                new MenuItem("deck", () -> {
                    try {
                        goToDeckMenu();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }),
                new MenuItem("scoreboard", () -> {
                    try {
                        goToScoreBoard();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }),
                new MenuItem("profile", () -> {
                    try {
                        goToProfile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }),
                new MenuItem("shop", () -> {
                    try {
                        goToShop();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }),
                new MenuItem("import/export", () -> {
                    try {
                        goToImport();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }),
                new MenuItem("logout", () -> {
                    try {
                        goToLoginMenu();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                })};
    }


    public void printMenuNavigationImpossible() {//?????????????????????????
        System.out.println("menu navigation is not possible");
    }

    public void goToDuelMenu() {

    }

    public void goToDeckMenu() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/LoginMenu.fxml"));
        ((Stage) pane.getScene().getWindow()).setScene(new Scene(root));
    }

    public void goToScoreBoard() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/LoginMenu.fxml"));
        ((Stage) pane.getScene().getWindow()).setScene(new Scene(root));
    }

    public void goToProfile() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/ProfileMenu.fxml"));
        ((Stage) pane.getScene().getWindow()).setScene(new Scene(root));
    }

    public void goToShop() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/LoginMenu.fxml"));
        ((Stage) pane.getScene().getWindow()).setScene(new Scene(root));
    }

    public void goToImport() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/LoginMenu.fxml"));
        ((Stage) pane.getScene().getWindow()).setScene(new Scene(root));
    }

    public void goToLoginMenu() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/LoginMenu.fxml"));
        ((Stage) pane.getScene().getWindow()).setScene(new Scene(root));
    }


    public void fade(MouseEvent mouseEvent) {

    }

}
