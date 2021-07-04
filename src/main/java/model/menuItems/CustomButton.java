package model.menuItems;

import javafx.beans.binding.Bindings;
import javafx.scene.effect.Bloom;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class CustomButton extends StackPane {
    public CustomButton(String name, Runnable action) {
        LinearGradient gradient = new LinearGradient(
                0, 0.5, 1, 0.5, true, CycleMethod.NO_CYCLE,
                new Stop(0.1, Color.web("black", 1)),
                new Stop(1.0, Color.web("black", 0.2)));
        Rectangle bg = new Rectangle(250, 38, gradient);
        Text text = new Text(name);
        text.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 22));
        setOnMouseClicked(e->{action.run(); });
        text.fillProperty().bind(
                Bindings.when(hoverProperty()).then(Color.WHITE).otherwise(Color.GREY)
        );
        text.setEffect(new Bloom(0.2));
        getChildren().addAll(bg, text);
    }
}
