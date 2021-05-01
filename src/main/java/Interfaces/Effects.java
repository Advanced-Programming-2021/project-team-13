package Interfaces;

import model.Card;
import model.Cell;
import model.Player;

public interface Effects {
    boolean conditionCheck(Card playingCard, Cell cell);

    void useAbility(Card playingCard);

    default Player getCardOwner(Card playingCard) {
        return playingCard.getPlayer();
    }
}
