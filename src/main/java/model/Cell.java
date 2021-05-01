package model;

public class Cell {
    private Card card;
    Cell(Card card){
        this.card =card;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }
}
