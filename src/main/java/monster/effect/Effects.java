package monster.effect;

import model.cards.Monster;

public interface Effects {
    boolean canActive(Monster monster);

    void active(Monster monster);

    boolean canDeActive(Monster monster);

    void deActive(Monster monster);
}
