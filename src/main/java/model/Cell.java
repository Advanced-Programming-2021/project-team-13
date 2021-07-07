package model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import model.cards.Card;

public class Cell {
    private Card card;
    private StackPane picture;

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

    public void setPicture(Image picture) {
        ImageView cardImages = new ImageView(picture);
        cardImages.setFitWidth(93.3333);
        cardImages.setFitHeight(126.6666);
        this.picture.getChildren().add(cardImages);
    }
}
