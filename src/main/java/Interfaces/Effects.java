package Interfaces;

import model.Card;
import model.Player;

public interface Effects {
    boolean conditionCheck(Card playingCard);
    void useAbility(Card playingCard);
    void destroyedEffect(Card playingCard);
}
