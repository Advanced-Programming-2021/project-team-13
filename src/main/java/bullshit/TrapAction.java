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
    protected ArrayList<CardCommand> cardCommands;


    private static void setAllTrapEffects() {
        allTrapEffects = new HashMap<>();//complete this!!
        allTrapEffects.put("Call of The Haunted", new CallOfTheHaunted());
        allTrapEffects.put("Time Seal" , new TimeSeal());
        allTrapEffects.put("Magic Jammer" , new MagicJammer());
    }

    public void setTrapCommands(ArrayList<CardCommand> cardCommands) {
        this.cardCommands = cardCommands;
    }

    public void setStartActionCheck(EffectHandler startActionCheck) {
        this.startActionCheck = startActionCheck;
    }
}

class  CallOfTheHaunted extends TrapAction {

    private EffectHandler endActionCheck1;
    private EffectHandler endActionCheck2;
    private ArrayList<CardCommand> endCommands;

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
        cardCommands = new ArrayList<>();
        CardCommand cardCommand = new BringMonsterBackFromGraveYardToBoard(card);
        CardCommand cardCommand1 = new SetEffectedMonster(card);
        cardCommands.add(cardCommand);
        cardCommands.add(cardCommand1);
        setTrapCommands(cardCommands);
        endCommands = new ArrayList<>();
        CardCommand cardCommand2 = new SendCardToGraveyard(card);
        CardCommand cardCommand3 = new SendEffectedCardToGraveyard(card);
        endCommands.add(cardCommand3);
        endCommands.add(cardCommand2);
    }

    @Override
    public void run() {
        if (startActionCheck.canActivate()) {
            trap.setActivated(true);
            for (CardCommand cardCommand : cardCommands)
                cardCommand.execute();
        } else if (endActionCheck1.canActivate() || endActionCheck2.canActivate()) {
            for (CardCommand cardCommand : endCommands)
                cardCommand.execute();
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
        CardCommand cardCommand = new ChooseCardFromHandToSacrifice(card);
        CardCommand cardCommand1 = new FindActiveSpell(card);
        CardCommand cardCommand2 = new SendCardToGraveyard(card);
        CardCommand cardCommand3 = new SendEffectedCardToGraveyard(card);
        cardCommands = new ArrayList<>();
        cardCommands.add(cardCommand);
        cardCommands.add(cardCommand1);
        cardCommands.add(cardCommand2);
        cardCommands.add(cardCommand3);
    }

    @Override
    public void run() {
        if (startActionCheck.canActivate()){
            for (CardCommand cardCommand : cardCommands) {
                cardCommand.execute();
            }
        }
    }
}

class TimeSeal extends TrapAction {

    @Override
    public void setCard(Card card) {
        this.trap = (Trap) card;
        EffectHandler effectHandler = new PlayerCanActivateTrap(card);
        setStartActionCheck(effectHandler);
        CardCommand cardCommand = new SetRivalPlayerCannotDrawCardNextTurn(card);
        CardCommand cardCommand1 = new SendCardToGraveyard(card);
        cardCommands = new ArrayList<>();
        cardCommands.add(cardCommand);
        cardCommands.add(cardCommand1);
    }

    @Override
    public void run() {
        if (startActionCheck.canActivate()){
            for (CardCommand cardCommand : cardCommands)
                cardCommand.execute();
        }
    }
}