package bullshit;

import controll.GameController;
import enums.Phase;
import enums.SummonType;
import enums.Zone;
import model.cards.Card;
import model.cards.Monster;
import model.cards.Trap;
import model.players.Player;
import view.ViewMaster;

public abstract class EffectHandler {
    protected Card card;
    protected static GameController gameController = ViewMaster.getViewMaster().getGameView().getGameController();
    protected EffectHandler nextHandler;

    public EffectHandler(Card card) {
        this.card = card;
    }

    public void setNextHandler(EffectHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    public abstract boolean canActivate();
}

class IsPlayerAttacking extends EffectHandler {

    public IsPlayerAttacking(Card card) {
        super(card);
    }

    @Override
    public boolean canActivate() {
        Player currentPlayer = gameController.getCurrentPlayer();
        if (card.getCardOwner() == currentPlayer && gameController.getCurrentPhase() == Phase.BATTLE_PHASE) {
            if (nextHandler != null) return nextHandler.canActivate();
            else return true;
        } else {
            return false;
        }
    }
}

class HasAnySummonHappendThisTurn extends EffectHandler {
    public HasAnySummonHappendThisTurn(Card card) {
        super(card);
    }

    @Override
    public boolean canActivate() {
        if (gameController.getRivalPlayer().isSummonInThisTurn() || gameController.getCurrentPlayer().isSummonInThisTurn()) {
            if (nextHandler != null) return nextHandler.canActivate();
            else return true;
        } else {
            return false;
        }
    }
}

class HasNormalSummonHappened extends EffectHandler {//check game setters

    public HasNormalSummonHappened(Card card) {
        super(card);
    }

    @Override
    public boolean canActivate() {
        if (((Monster) gameController.getRivalPlayer().getSelectedCard()).getSummonType() == SummonType.NORMAL) {
            if (nextHandler != null) return nextHandler.canActivate();
            else return true;
        } else {
            return false;
        }
    }
}

class HasFlipSummonHappened extends EffectHandler {//check game setters

    public HasFlipSummonHappened(Card card) {
        super(card);
    }

    @Override
    public boolean canActivate() {
        if (((Monster) gameController.getRivalPlayer().getSelectedCard()).getSummonType() == SummonType.FLIP) {
            if (nextHandler != null) return nextHandler.canActivate();
            else return true;
        } else {
            return false;
        }
    }
}

//
//class HasRitualSummonHappened extends EffectHandler{//check game setters
//
//    public HasRitualSummonHappened(Card card){
//        super(card);
//    }
//
//    @Override
//    boolean canActivate() {
//        if (((Monster)gameController.getRivalPlayer().getSelectedCard()).getSummonType() == SummonType.SPECIAL){//to check!!!!
//            if (nextHandler != null) return nextHandler.canActivate();
//            else return true;
//        } else {
//            return false;
//        }
//    }
//}

class HasSpecialSummonHappened extends EffectHandler {//check game setters

    public HasSpecialSummonHappened(Card card) {
        super(card);
    }

    @Override
    public boolean canActivate() {
        if (((Monster) gameController.getRivalPlayer().getSelectedCard()).getSummonType() == SummonType.SPECIAL) {
            if (nextHandler != null) return nextHandler.canActivate();
            else return true;
        } else {
            return false;
        }
    }
}

//
//class HasSpecialSummonMonsterHappened extends EffectHandler{//check game setters
//
//    public HasSpecialSummonMonsterHappened(Card card){
//        super(card);
//    }
//
//    @Override
//    boolean canActivate() {
//        if (((Monster)gameController.getRivalPla){
//            if (nextHandler != null) return nextHandler.canActivate();
//            else return true;
//        } else {
//            return false;
//        }
//    }
//}

class PlayerCanActivateTrap extends EffectHandler {

    public PlayerCanActivateTrap(Card card) {
        super(card);
    }

    @Override
    public boolean canActivate() {
        if (card.getPlayer().isCanActiveTrap() && !((Trap) card).isEffectUsed()) {
            if (nextHandler != null) return nextHandler.canActivate();
            else return true;
        } else {
            return false;
        }
    }
}

class IsCardInGraveyard extends EffectHandler {

    public IsCardInGraveyard(Card card) {
        super(card);
    }

    @Override
    public boolean canActivate() {
        if (card.getZone() == Zone.GRAVEYARD) {
            if (nextHandler != null) return nextHandler.canActivate();
            else return true;
        } else {
            return false;
        }
    }
}

class IsEffectedCardInGraveyard extends EffectHandler{

    public IsEffectedCardInGraveyard(Card card){
        super(card);
    }

    @Override
    public boolean canActivate() {
        if (((Trap)card).getEffectedCard().getZone() == Zone.GRAVEYARD){
            if (nextHandler != null) return nextHandler.canActivate();
            else return true;
        } else {
            return false;
        }
    }
}


