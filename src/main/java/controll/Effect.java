package controll;

import model.cards.Card;

public interface Effect {
    void setCard(Card card);
    void run();
}
