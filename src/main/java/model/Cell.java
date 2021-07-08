package model;

import enums.AttackOrDefense;
import enums.Face;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import model.cards.Card;

public class Cell {
    private static Image unknown = new Image(Cell.class.getResource("/shopImage/Monsters/Unknown.jpg").toExternalForm());
    private Card card;
    private StackPane picture;
    private Image monsterImage;

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
        ImageView cardImages;
        if (face == Face.UP)
            cardImages = new ImageView(picture);
        else
            cardImages = new ImageView(unknown);
        if (attackOrDefense == AttackOrDefense.DEFENSE)
            cardImages.setRotate(90);
        if (attackOrDefense == AttackOrDefense.ATTACK
                && getPicture().getRotate() == 90)
            getPicture().setRotate(0);
        cardImages.setFitWidth(93.3333);
        cardImages.setFitHeight(126.6666);
        this.picture.getChildren().add(cardImages);
    }

    public void setPictureUP() {
        ImageView cardImages = new ImageView(monsterImage);
        cardImages.setFitWidth(93.3333);
        cardImages.setFitHeight(126.6666);
        picture.getChildren().clear();
        picture.getChildren().add(cardImages);
    }
}
