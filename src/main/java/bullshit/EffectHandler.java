package bullshit;

import controll.GameController;
import enums.Phase;
import model.cards.Card;
import model.players.Player;
import view.ViewMaster;

public abstract class EffectHandler {
    protected Card card;
    protected static GameController gameController = ViewMaster.getViewMaster().getGameView().getGameController();
    protected EffectHandler nextHandler;
    public EffectHandler (Card card){
        this.card = card;
    }

    public void setNextHandler(EffectHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    abstract boolean canActivate();
}

class IsPlayerAttacking extends EffectHandler{

    public IsPlayerAttacking(Card card){
        super(card);
    }

    @Override
    boolean canActivate() {
        Player currentPlayer = gameController.getCurrentPlayer();
        if (card.getCardOwner() == currentPlayer && gameController.getCurrentPhase() == Phase.BATTLE_PHASE){
            if (nextHandler != null) return nextHandler.canActivate();
            else return true;
        } else {
            return false;
        }
    }
}

class HasNormalSummonHappened extends EffectHandler{//check game setters

    public HasNormalSummonHappened(Card card){
        super(card);
    }

    @Override
    boolean canActivate() {
        if (gameController.isHasNormalSummonHappened()){
            if (nextHandler != null) return nextHandler.canActivate();
            else return true;
        } else {
            return false;
        }
    }
}

class HasFlipSummonHappened extends EffectHandler{//check game setters

    public HasFlipSummonHappened(Card card){
        super(card);
    }

    @Override
    boolean canActivate() {
        if (gameController.isHasFlipSummonHappened()){
            if (nextHandler != null) return nextHandler.canActivate();
            else return true;
        } else {
            return false;
        }
    }
}


class HasRitualSummonHappened extends EffectHandler{//check game setters

    public HasRitualSummonHappened(Card card){
        super(card);
    }

    @Override
    boolean canActivate() {
        if (gameController.isHasRitualSummonHappened()){
            if (nextHandler != null) return nextHandler.canActivate();
            else return true;
        } else {
            return false;
        }
    }
}

class HasSpecialSummonHappened extends EffectHandler{//check game setters

    public HasSpecialSummonHappened(Card card){
        super(card);
    }

    @Override
    boolean canActivate() {
        if (gameController.isHasSpecialSummonHappened()){
            if (nextHandler != null) return nextHandler.canActivate();
            else return true;
        } else {
            return false;
        }
    }
}


class HasSpecialSummonMonsterHappened extends EffectHandler{//check game setters

    public HasSpecialSummonMonsterHappened(Card card){
        super(card);
    }

    @Override
    boolean canActivate() {
        if (gameController.isHasSpecialSummonMonsterHappened()){
            if (nextHandler != null) return nextHandler.canActivate();
            else return true;
        } else {
            return false;
        }
    }
}


