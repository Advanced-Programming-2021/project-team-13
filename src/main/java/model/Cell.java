package model;

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

    public void setPicture(StackPane picture) {
        this.picture = picture;
    }
}
