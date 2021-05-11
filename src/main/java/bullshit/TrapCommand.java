package bullshit;

import controll.GameController;
import model.cards.Card;
import model.cards.Trap;
import view.ViewMaster;

public abstract class TrapCommand {
    protected static GameController gameController;
    static {
        gameController = ViewMaster.getViewMaster().getGameView().getGameController();
    }
    protected Trap trap ;

    public TrapCommand(Card card){
        this.trap = (Trap) card;
    }

    public void setCard(Card card) {
        this.trap = (Trap) card;
    }

//    public abstract void execute();

}
