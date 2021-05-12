package bullshit;

import model.cards.Card;

public interface Effect {
    void setCard(Card card);
    void setCommands(Card card);
    void run();
}
