package model;

import enums.AttackOrDefense;
import enums.Face;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import model.cards.Card;

public class Cell {
    private static Image unknown = new Image(Cell.class.getResource("/shopImage/Monsters/Unknown.jpg").toExternalForm());
    private Card card;
    private StackPane picture;
    private Image monsterImage;
    ImageView cardImages;
    private ColorAdjust colorAdjust = new ColorAdjust();
    private ColorAdjust flipSummon = new ColorAdjust();
    private Timeline summon = new Timeline(new KeyFrame(Duration.seconds(0.05), e -> {
        colorAdjust.setBrightness(colorAdjust.getBrightness() - 0.05);
    }));

    Cell(Card card) {
        this.card = card;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public StackPane getPicture() {
        return picture;
    }

    public void setStackPane(StackPane picture) {
        this.picture = picture;
    }

    public void setPicture(Image picture, Face face, AttackOrDefense attackOrDefense) {
        monsterImage = picture;
        if (face == Face.UP)
            cardImages = new ImageView(picture);
        else
            cardImages = new ImageView(unknown);
        if (attackOrDefense == AttackOrDefense.DEFENSE)
            cardImages.setRotate(90);
        if (attackOrDefense == AttackOrDefense.ATTACK
                && getPicture().getRotate() == 90)
            getPicture().setRotate(0);
        colorAdjust.setBrightness(1);
        cardImages.setEffect(colorAdjust);
        cardImages.setFitWidth(93.3333);
        cardImages.setFitHeight(126.6666);
        this.picture.getChildren().add(cardImages);
        summon.setCycleCount(20);
        summon.play();
    }

    public void setPictureUP() {
        picture.getChildren().clear();
        ImageView cardImages = new ImageView(monsterImage);

        picture.getChildren().add(cardImages);
        cardImages.setFitWidth(93.3333);
        cardImages.setFitHeight(126.6666);
    }
}
