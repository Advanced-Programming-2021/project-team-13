package Interfaces;

import model.Card;
import model.Player;

public interface Effects extends Canser{
    boolean conditionCheck(Card playingCard);
    void useAbility(Card playingCard);
    void destroyedEffect(Card playingCard);
    boolean destroyedConditionChecker(Card playingCard);
}
