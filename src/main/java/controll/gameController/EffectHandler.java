package controll.gameController;

import enums.Phase;
import enums.Zone;
import model.Cell;
import model.Graveyard;
import model.cards.Card;
import model.cards.Monster;
import model.cards.Spell;
import model.cards.Trap;
import model.players.Player;

public abstract class EffectHandler {
    protected static GameController gameController;

    public static void setGameController(GameController gameController) {
        EffectHandler.gameController = gameController;
    }

    protected Card card;
    protected EffectHandler nextHandler;

    public EffectHandler(Card card) {
        this.card = card;
    }

    public void setNextHandler(EffectHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    public abstract boolean canActivate();
}

class HasAnySummonHappenedThisTurn extends EffectHandler {
    public HasAnySummonHappenedThisTurn(Card card) {
        super(card);
    }

    @Override
    public boolean canActivate() {
        if (gameController.isAnySummonHappened()) {
            if (nextHandler != null) return nextHandler.canActivate();
            else return true;
        } else {
            return false;
        }
    }
}

class HasNormalOrFlipSummonHappened extends EffectHandler {

    public HasNormalOrFlipSummonHappened(Card card) {
        super(card);
    }

    @Override
    public boolean canActivate() {
        if (gameController.isNormalSummonHappened() || gameController.isFlipSummonHappened()) {
            if (nextHandler != null) return nextHandler.canActivate();
            else return true;
        } else {
            return false;
        }
    }
}

class HasFlipSummonHappened extends EffectHandler {

    public HasFlipSummonHappened(Card card) {
        super(card);
    }

    @Override
    public boolean canActivate() {
        if (gameController.isFlipSummonHappened()) {
            if (nextHandler != null) return nextHandler.canActivate();
            else return true;
        } else {
            return false;
        }
    }
}

class HasSpecialSummonHappened extends EffectHandler {

    public HasSpecialSummonHappened(Card card) {
        super(card);
    }

    @Override
    public boolean canActivate() {
        if (gameController.isSpecialSummonHappened()) {
            if (nextHandler != null) return nextHandler.canActivate();
            else return true;
        } else {
            return false;
        }
    }
}

class IsInAttack extends EffectHandler {

    public IsInAttack(Card card) {
        super(card);
    }

    @Override
    public boolean canActivate() {
        if (gameController.isInAttack() && gameController.getCurrentPhase() == Phase.BATTLE_PHASE) {
            if (nextHandler != null)
                return nextHandler.canActivate();
            else return true;
        } else {
            return false;
        }
    }
}

class PlayerCanActivateTrap extends EffectHandler {

    public PlayerCanActivateTrap(Card card) {
        super(card);
    }

    @Override
    public boolean canActivate() {
        if (card.getCardOwner().isCanActiveTrap() && !card.isActivated() && ((Trap) card).getSetTurn() != gameController.getTurnsPlayed()) {
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
        for (Cell cell : gameController.getCurrentPlayer().getRivalPlayer().getBoard().getSpellOrTrap()) {
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

class GraveYardHasMonsterIn extends EffectHandler {

    public GraveYardHasMonsterIn(Card card) {
        super(card);
    }

    @Override
    public boolean canActivate() {
        Graveyard graveyard = card.getCardOwner().getBoard().getGraveyard();
        if (graveyard.hasMonster()) {
            if (nextHandler != null)
                return nextHandler.canActivate();
            else return true;
        } else {
            return false;
        }
    }
}

class HasSummonedMonsterPowerMoreThan1000 extends EffectHandler {

    public HasSummonedMonsterPowerMoreThan1000(Card card) {
        super(card);
    }

    @Override
    public boolean canActivate() {
        if (gameController.getSummonedCard() != null &&
                gameController.getSummonedCard() instanceof Monster &&
                ((Monster) gameController.getSummonedCard()).getAttackNum() > 1000 &&
                gameController.getSummonedCard().getCardOwner().getRivalPlayer() == card.getCardOwner()) {
            if (nextHandler != null)
                return nextHandler.canActivate();
            else return true;
        } else {
            return false;
        }
    }
}

class EnemyHasAnyCardInHand extends EffectHandler {

    public EnemyHasAnyCardInHand (Card card){
        super(card);
    }

    @Override
    public boolean canActivate() {
        Player rival = card.getCardOwner().getRivalPlayer();
        if (rival.getCardsInHand().size() != 0){
            if (nextHandler != null)
                return nextHandler.canActivate();
            else return true;
        } else return false;
    }
}