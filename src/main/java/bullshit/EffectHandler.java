package bullshit;

import controll.GameController;
import enums.SummonType;
import enums.Zone;
import model.Cell;
import model.cards.Card;
import model.cards.Monster;
import model.cards.Spell;
import model.cards.Trap;
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
//
//class IsRivalPlayerAttacking extends EffectHandler {
//
//    public IsRivalPlayerAttacking(Card card) {
//        super(card);
//    }
//
//    @Override
//    public boolean canActivate() {
//        if (gameController) {
//            if (nextHandler != null) return nextHandler.canActivate();
//            else return true;
//        } else {
//            return false;
//        }
//    }
//}
//
//class HasAnySummonHappenedThisTurn extends EffectHandler {
//    public HasAnySummonHappenedThisTurn(Card card) {
//        super(card);
//    }
//
//    @Override
//    public boolean canActivate() {
//        if (gameController.isSummonInThisTurn()) {
//            if (nextHandler != null) return nextHandler.canActivate();
//            else return true;
//        } else {
//            return false;
//        }
//    }
//}

//class IsRivalPlayerAttacking extends EffectHandler {
//
//    public IsRivalPlayerAttacking(Card card) {
//        super(card);
//    }

//
//    public boolean canActivate() {
//        if (gameController.getRivalPlayer()) {
//            if (nextHandler != null) return nextHandler.canActivate();
//            else return true;
//        } else {
//            return false;
//        }
//    }
//}

//class HasAnySummonHappenedThisTurn extends EffectHandler {
//    public HasAnySummonHappenedThisTurn(Card card) {
//        super(card);
//    }

//    @Override
//    public boolean canActivate() {
//        if (gameController.isSummonInThisTurn()) {
//            if (nextHandler != null) return nextHandler.canActivate();
//            else return true;
//        } else {
//            return false;
//        }
//    }
//}

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
        if (card.getCardOwner().isCanActiveTrap() && !card.isActivated() && !((Trap) card).isSetInThisTurn()) {
            if (nextHandler != null) return nextHandler.canActivate();
            else return true;
        } else {
            return false;
        }
    }
}

class IsPlayerMonsterZoneHasEmptyPlace extends EffectHandler {

    public IsPlayerMonsterZoneHasEmptyPlace(Card card) {
        super(card);
    }

    @Override
    public boolean canActivate() {
        boolean emptyCell = false;
        for (Cell cell : card.getCardOwner().getBoard().getMonsters()) {
            if (cell.getCard() == null) {
                emptyCell = true;
                break;
            }
        }
        if (emptyCell) {
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

class IsEffectedCardInGraveyard extends EffectHandler {

    public IsEffectedCardInGraveyard(Card card) {
        super(card);
    }

    @Override
    public boolean canActivate() {
        if (((Trap) card).getEffectedCard().getZone() == Zone.GRAVEYARD) {
            if (nextHandler != null) return nextHandler.canActivate();
            else return true;
        } else {
            return false;
        }
    }
}

class IsAnySpellActive extends EffectHandler {

    public IsAnySpellActive(Card card) {
        super(card);
    }

    @Override
    public boolean canActivate() {
        boolean anySpellActive = false;
        for (Cell cell : gameController.getRivalPlayer().getBoard().getSpellOrTrap()) {
            if (cell.getCard() instanceof Spell && cell.getCard().isActivated()) {
                anySpellActive = true;
                break;
            }
        }
        if (!anySpellActive) {
            for (Cell cell : card.getCardOwner().getBoard().getSpellOrTrap()) {
                if (cell.getCard() != card) {
                    if (cell.getCard() instanceof Spell && cell.getCard().isActivated()) {
                        anySpellActive = true;
                        break;
                    }
                }
            }
        }
        if (anySpellActive) {
            if (nextHandler != null) return nextHandler.canActivate();
            else return true;
        } else {
            return false;
        }
    }
}