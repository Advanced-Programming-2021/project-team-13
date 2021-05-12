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

    public void setTrapCommands(ArrayList<TrapCommand> trapCommands) {
        this.trapCommands = trapCommands;
    }

    public void setStartActionCheck(EffectHandler startActionCheck) {
        this.startActionCheck = startActionCheck;
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
        EffectHandler effectHandler3 = new IsPlayerMonsterZoneHasEmptyPlace(card);
        effectHandler.setNextHandler(effectHandler3);
        setStartActionCheck(effectHandler);
        setEndActionCheck1(effectHandler1);
        setEndActionCheck2(effectHandler2);
        trapCommands = new ArrayList<>();
        TrapCommand trapCommand = new BringMonsterBackFromGraveYardToBoard(card);
        TrapCommand trapCommand1 = new SetEffectedMonster(card);
        trapCommands.add(trapCommand);
        trapCommands.add(trapCommand1);
        setTrapCommands(trapCommands);
        endCommands = new ArrayList<>();
        TrapCommand trapCommand2 = new SendCardToGraveyard(card);
        TrapCommand trapCommand3 = new SendMonsterToGraveyard(card);
        endCommands.add(trapCommand3);
        endCommands.add(trapCommand2);
    }

    @Override
    public void run() {
        if (startActionCheck.canActivate()) {
            trap.setActivated(true);
            for (TrapCommand trapCommand : trapCommands)
                trapCommand.execute();
        } else if (endActionCheck1.canActivate() || endActionCheck2.canActivate()) {
            for (TrapCommand trapCommand : endCommands)
                trapCommand.execute();
        }
    }
}

class MagicJammer extends TrapAction {

    @Override
    public void setCard(Card card) {
        this.trap = (Trap) card;
        EffectHandler effectHandler = new IsAnySpellActive(card);
        EffectHandler effectHandler1 = new PlayerCanActivateTrap(card);
        effectHandler1.setNextHandler(effectHandler);
        setStartActionCheck(effectHandler1);
        TrapCommand trapCommand
    }

    @Override
    public void run() {
        if (startActionCheck.canActivate()){
            for (TrapCommand trapCommand : trapCommands) {
                trapCommand.execute();
            }
        }
    }
}

class TimeSeal extends TrapAction {

    @Override
    public void setCard(Card card) {
        this.trap = (Trap) card;
        EffectHandler effectHandler = new PlayerCanActivateTrap(card);

    }
}
