package view.allmenu;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;


public class D15Controller implements Initializable{
    @FXML
    private Circle c1;
    @FXML
    private Rectangle r1;

    // Animation Data
    private static final Duration TRANSLATE_DURATION = Duration.seconds(0.25);
    private TranslateTransition transition;
    @FXML
    private Circle c2;
    @FXML
    private Rectangle r2;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        transition = createTranslateTransition(c1);


    }

    private TranslateTransition createTranslateTransition(final Circle circle) {

        return new TranslateTransition(TRANSLATE_DURATION, circle);
    }

    @FXML
    private void removeBind(MouseEvent event) {
        if (event.getSource() instanceof Circle) {
            Circle circle = (Circle) event.getSource();
            circle.setOpacity(1);
        }
    }

    @FXML
    private void fillBox(DragEvent event) {
        System.out.println("Drag detected");
        if (event.getGestureTarget() instanceof Rectangle) {
            Rectangle target = (Rectangle) event.getGestureTarget();
            Circle source = (Circle) event.getGestureSource();
            target.setFill(source.getFill());
        }
    }

    @FXML
    private void moveCircle(MouseEvent event) {
        Circle circle = null;
        event.setDragDetect(true);

        if (event.getSource() instanceof Circle) {

            circle = (Circle) event.getSource();

            circle.setOpacity(0.25);
            if (!event.isControlDown()) {
                circle.setCenterX(event.getX());
                circle.setCenterY(event.getY());
            } else {
                transition.setToX(circle.getCenterX());
                transition.setToY(circle.getCenterY());
                transition.playFromStart();
            }
        }


    }

    @FXML
    private void startDrag(MouseEvent event) {
        if (event.getSource() instanceof Circle) {
            Circle source = (Circle) event.getSource();
            source.startFullDrag();
        }
    }

    @FXML
    private void highlightBox(DragEvent event) {
        System.out.println("Highlight box");
        if (event.getGestureSource() instanceof Circle) {
            Circle source = (Circle) event.getGestureSource();
            Rectangle target = (Rectangle) event.getGestureTarget();
            target.setFill(source.getFill());
        }
    }

//    @Override
//    public void start(Stage primaryStage) throws Exception {
//        URL url = getClass().getResource("/fxml/CardCreatorMenu.fxml");
//        Parent parent = FXMLLoader.load(url);
//        Scene scene = new Scene(parent);
//        primaryStage.setScene(scene);
//        primaryStage.show();
//    }
}