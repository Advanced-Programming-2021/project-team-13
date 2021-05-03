package bullshit.Interfaces;

import model.Card;

public interface Summon extends Canser{
    boolean conditionCheck(Card playingCard);
    void useAbility(Card playingCard);
}
