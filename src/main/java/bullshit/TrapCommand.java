package bullshit;

import controll.GameController;
import enums.AttackOrDefense;
import model.Cell;
import model.cards.Card;
import model.cards.Monster;
import model.cards.Trap;
import view.ViewMaster;

public abstract class TrapCommand {
    protected static GameController gameController;

    static {
        gameController = ViewMaster.getViewMaster().getGameView().getGameController();
    }

    protected Trap trap;

    public TrapCommand(Card card) {
        this.trap = (Trap) card;
    }

    public void setCard(Card card) {
        this.trap = (Trap) card;
    }

    public abstract void execute();

}

class BringMonsterBackFromGraveYard extends TrapCommand{

    public BringMonsterBackFromGraveYard(Card card){
        super(card);
    }

    @Override
    public void execute() {
        gameController.selectMonsterFromGraveyard();
        Monster monster = (Monster) gameController.getCurrentPlayer().getSelectedCard();
        monster.setAttackedInThisTurn(false);
        monster.setAttackable(true);
        monster.setAttackOrDefense(AttackOrDefense.ATTACK);
        for (Cell cell : gameController.getCurrentPlayer().getBoard().getMonsters()) {
            if (cell.getCard() == null) {
                cell.setCard(monster);
                break;
            }
        }
    }
}

class SetEffectedMonster extends TrapCommand{

    public SetEffectedMonster(Card card){
        super(card);
    }

    @Override
    public void execute() {
        Card effectedCard = gameController.getCurrentPlayer().getSelectedCard();
        trap.setEffectedCard(effectedCard);
        gameController.getCurrentPlayer().setSelectedCard(null);
    }
}
