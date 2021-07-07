package model.menuItems;

import javafx.scene.effect.Bloom;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class CustomSanButtons extends StackPane {
    public CustomSanButtons(String name,Runnable runnable){
        Image img=new Image("/gamePics/sugoroku_btn_s.dds.png"
                ,75,120,false,true);
        ImageView imageView = new ImageView(img);
        Text text = new Text(name);
        text.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.ITALIC, 22));
        text.setEffect(new Bloom(0.3));
        setOnMouseEntered(e->imageView.setEffect(new ColorAdjust(-0.72,0,0,0)));
        setOnDragExited(e->imageView.setEffect(null));
        setOnMouseEntered(e->runnable.run());
        getChildren().addAll(imageView,text);
    }
}
