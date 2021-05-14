package bullshit;

import controll.GameController;
import enums.AttackOrDefense;
import enums.Zone;
import model.Cell;
import model.Graveyard;
import model.cards.Card;
import model.cards.Monster;
import model.cards.Spell;
import model.cards.Trap;
import view.ViewMaster;


public abstract class CardCommand {
    protected static GameController gameController;

    static {
        gameController = ViewMaster.getViewMaster().getGameView().getGameController();
    }

    protected Trap trap;

    public CardCommand(Card card) {
        this.trap = (Trap) card;
    }

    public void setCard(Card card) {
        this.trap = (Trap) card;
    }

    public abstract void execute();

}

class BringMonsterBackFromGraveYardToBoard extends CardCommand {

    public BringMonsterBackFromGraveYardToBoard(Card card) {
        super(card);
    }

    @Override
    public void execute() {
        gameController.selectMonsterFromGraveyard();
        Monster monster = (Monster) gameController.getCurrentPlayer().getSelectedCard();
        monster.setZone(Zone.MONSTER_ZONE);
        monster.setAttackedInThisTurn(false);
        monster.setAttackable(true);
        monster.setAttackOrDefense(AttackOrDefense.ATTACK);
        monster.getCardOwner().getBoard().putMonsterInBoard(monster);
    }
}

class SetEffectedMonster extends CardCommand {

    public SetEffectedMonster(Card card) {
        super(card);
    }

    @Override
    public void execute() {
        Card effectedCard = gameController.getCurrentPlayer().getSelectedCard();
        trap.setEffectedCard(effectedCard);
        gameController.getCurrentPlayer().setSelectedCard(null);
    }
}

class SendEffectedCardToGraveyard extends CardCommand {

    public SendEffectedCardToGraveyard(Card card) {
        super(card);
    }

    @Override
    public void execute() {
        Graveyard graveyard = trap.getCardOwner().getBoard().getGraveyard();
        graveyard.addCard(trap.getEffectedCard());
    }
}

class SendCardToGraveyard extends CardCommand {

    public SendCardToGraveyard(Card card) {
        super(card);
    }

    @Override
    public void execute() {
        Graveyard graveyard = trap.getCardOwner().getBoard().getGraveyard();
        graveyard.addCard(trap);
    }
}

class SetRivalPlayerCannotDrawCardNextTurn extends CardCommand {

    public SetRivalPlayerCannotDrawCardNextTurn(Card card) {
        super(card);
    }

    @Override
    public void execute() {
        int currentTurn = gameController.getTurnsPlayed();
        gameController.addNotToDrawCardTurn(currentTurn + 1);
    }
}

class FindActiveSpell extends CardCommand {

    public FindActiveSpell(Card card) {
        super(card);
    }

    @Override
    public void execute() {
        Spell effectedCard = null;
        for (Cell cell : trap.getCardOwner().getBoard().getSpellOrTrap()) {
            if (cell.getCard() instanceof Spell && cell.getCard().isActivated()) {
                effectedCard = (Spell) cell.getCard();
                break;
            }
        }
        if (effectedCard == null) {
            for (Cell cell : gameController.getRivalPlayer().getBoard().getSpellOrTrap()) {
                if (trap != cell.getCard())
                    if (cell.getCard() instanceof Spell && cell.getCard().isActivated()) {
                        effectedCard = (Spell) cell.getCard();
                        break;
                    }
            }
        }
        trap.setEffectedCard(effectedCard);
    }
}

class ChooseCardFromHandToSacrifice extends CardCommand{

    public ChooseCardFromHandToSacrifice(Card card){
        super(card);
    }

    @Override
    public void execute() {
        int number;
        do {
            System.out.print("please enter a correct number to choose from your hand : ");
            number = ViewMaster.scanner.nextInt();
        } while (number > gameController.getCurrentPlayer().getCardsInHand().size() || number <= 0);
        gameController.selectPlayerHandCard(number);
        Card card = gameController.getCurrentPlayer().getSelectedCard();
        gameController.getCurrentPlayer().getBoard().getGraveyard().addCard(card);
        gameController.getCurrentPlayer().setSelectedCard(null);
    }
}