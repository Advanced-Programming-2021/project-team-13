package bullshit;

import controll.GameController;
import model.cards.Card;
import model.cards.Trap;
import view.ViewMaster;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class TrapAction implements Effect {
    protected static GameController gameController;
    protected static HashMap<String, TrapAction> allTrapEffects;

    static {
        gameController = ViewMaster.getViewMaster().getGameView().getGameController();
        setAllTrapEffects();
    }

    protected Trap trap;
    protected EffectHandler startActionCheck;
    protected ArrayList<TrapCommand> trapCommands;


    private static void setAllTrapEffects() {
        allTrapEffects = new HashMap<>();//complete this!!
        allTrapEffects.put("Call of The Haunted", new CallOfTheHaunted());
    }

    public void setStartActionCheck(EffectHandler startActionCheck) {
        this.startActionCheck = startActionCheck;
    }

    public EffectHandler getStartActionCheck() {
        return startActionCheck;
    }
}

class CallOfTheHaunted extends TrapAction {

    private EffectHandler endActionCheck1;
    private EffectHandler endActionCheck2;
    private ArrayList<TrapCommand> endCommands;

    public void setEndActionCheck1(EffectHandler endActionCheck1) {
        this.endActionCheck1 = endActionCheck1;
    }

    public void setEndActionCheck2(EffectHandler endActionCheck2) {
        this.endActionCheck2 = endActionCheck2;
    }

    @Override
    public void setCard(Card card) {
        this.trap = (Trap) card;
        EffectHandler effectHandler = new PlayerCanActivateTrap(card);
        EffectHandler effectHandler1 = new IsCardInGraveyard(card);
        EffectHandler effectHandler2 = new IsEffectedCardInGraveyard(card);
        setStartActionCheck(effectHandler);
        setEndActionCheck1(effectHandler1);
        setEndActionCheck2(effectHandler2);
        endCommands = new ArrayList<>();
    }

    @Override
    public void setCommands(Card card) {

    }

    @Override
    public void run() {
        if (startActionCheck.canActivate()){

        }
    }
}
