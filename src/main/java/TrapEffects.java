import bullshit.Effect;
import bullshit.EffectHandler;
import controll.GameController;
import model.cards.Card;
import model.cards.Trap;
import view.ViewMaster;

public abstract class TrapEffects implements Effect {
    protected static GameController gameController;
    static {
        gameController = ViewMaster.getViewMaster().getGameView().getGameController();
    }
    protected Trap trap ;
    protected EffectHandler effectHandler;

    public void setEffectHandler(EffectHandler effectHandler) {
        this.effectHandler = effectHandler;
    }

    @Override
    public void setCard(Card card) {
        this.trap = (Trap) card;
    }
}


