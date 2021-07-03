package view.allmenu;

import controll.MainController;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.Bloom;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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
import model.menuItems.CustomButtons;

import java.io.IOException;

public class MainView {
    private final MainController mainController;
    public BorderPane pane;
    private Image background1;
    private Image background2;
    private Image background3;
    private ImageView imageView;

    public MainView() {
        mainController = new MainController(this);
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
        imageView = new ImageView();
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
        backgroundTransition(background1, background2);
        backgroundTransition(background2, background3);
        backgroundTransition(background3, background1);
    }

    private void backgroundTransition(Image backgroundI, Image backgroundII) {
        if(imageView.getImage().equals(backgroundI)) {
            KeyFrame keyFrame1On = new KeyFrame(Duration.seconds(0), new KeyValue(imageView.imageProperty(), backgroundI));
            KeyFrame startFadeOut = new KeyFrame(Duration.seconds(1), new KeyValue(imageView.opacityProperty(), 1.0));
            KeyFrame endFadeOut = new KeyFrame(Duration.seconds(2), new KeyValue(imageView.opacityProperty(), 0.0));
            KeyFrame keyFrame2On = new KeyFrame(Duration.seconds(2), new KeyValue(imageView.imageProperty(), backgroundII));
            KeyFrame endFadeIn = new KeyFrame(Duration.seconds(4), new KeyValue(imageView.opacityProperty(), 1.0));
            Timeline timelineOn = new Timeline(keyFrame1On, startFadeOut, endFadeOut, keyFrame2On, endFadeIn);
            timelineOn.setAutoReverse(true);
            timelineOn.setCycleCount(1);
            timelineOn.play();
        }
    }

    private Node[] setNodes() {
        return new Node[]{
                new CustomButtons("duel", () -> {
                    goToDuelMenu();
                }),
                new CustomButtons("deck", () -> {
                    try {
                        goToDeckMenu();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }),
                new CustomButtons("scoreboard", () -> {
                    try {
                        goToScoreBoard();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }),
                new CustomButtons("profile", () -> {
                    try {
                        goToProfile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }),
                new CustomButtons("shop", () -> {
                    try {
                        goToShop();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }),
                new CustomButtons("import/export", () -> {
                    try {
                        goToImport();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }),
                new CustomButtons("logout", () -> {
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
