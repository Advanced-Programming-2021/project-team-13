package controll.gameController;

import enums.AttackOrDefense;
import enums.Zone;
import model.Cell;
import model.Graveyard;
import model.cards.Card;
import model.cards.Monster;
import model.cards.Spell;
import model.cards.Trap;
import model.players.AIPlayer;
import model.players.Player;
import view.ViewMaster;

import java.util.ArrayList;


public abstract class CardCommand {
    protected static GameController gameController;

    public static void setGameController(GameController gameController) {
        CardCommand.gameController = gameController;
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

    TrapAction trapAction;

    public BringMonsterBackFromGraveYardToBoard(Card card , TrapAction trapAction) {
        super(card);
        this.trapAction = trapAction;
    }

    @Override
    public void execute() {
        if (trap.getCardOwner() instanceof AIPlayer) {
            ArrayList<Monster> monsters = new ArrayList<>();
            for (Card card : trap.getCardOwner().getBoard().getGraveyard().getAllCards()){
                if (card instanceof Monster)
                    monsters.add((Monster) card);
            }
            Monster monster = monsters.get(0);
            for (Monster monster1 : monsters){
                if (monster1.getAttackNum() > monster.getAttackNum())
                    monster = monster1;
            }
            trap.getCardOwner().setSelectedCard(monster);
        } else gameController.selectMonsterFromPlayerGraveyard(trap.getCardOwner());
        Monster monster = (Monster) trap.getCardOwner().getSelectedCard();
        Graveyard graveyard = trap.getCardOwner().getBoard().getGraveyard();
        graveyard.removeCard(monster);
        trapAction.getTrap().setEffectedCard(monster);
        monster.setZone(Zone.MONSTER_ZONE);
        monster.setAttackedInThisTurn(false);
        monster.setAttackable(true);
        monster.setAttackOrDefense(AttackOrDefense.ATTACK);
        monster.getCardOwner().getBoard().putMonsterInBoard(monster);
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
        if (currentTurn % 2 == trap.getSetTurn() % 2){ //activated in his turn
            gameController.addNotToDrawCardTurn(currentTurn + 1);
        } else {
            gameController.addNotToDrawCardTurn(currentTurn + 2);
        }
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
            for (Cell cell : gameController.getCurrentPlayer().getRivalPlayer().getBoard().getSpellOrTrap()) {
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

class ChooseCardFromHandToSacrifice extends CardCommand {

    public ChooseCardFromHandToSacrifice(Card card) {
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

class AnnounceCardNameToRemove extends CardCommand {

    public AnnounceCardNameToRemove(Card card) {
        super(card);
    }

    @Override
    public void execute() {
        System.out.print("please enter a card name to remove from rival hand : ");
        String cardName = ViewMaster.scanner.nextLine().trim();
        boolean containsCard = false;
        for (Card card : gameController.getCurrentPlayer().getRivalPlayer().getCardsInHand()) {
            if (card.getCardName().equalsIgnoreCase(cardName)) {
                containsCard = true;
                break;
            }
        }
        if (containsCard) {
            ArrayList<Card> newRivalHand = new ArrayList<>();
            for (Card card : gameController.getCurrentPlayer().getRivalPlayer().getCardsInHand()) {
                if (!card.getCardName().equalsIgnoreCase(cardName)) newRivalHand.add(card);
                else gameController.getCurrentPlayer().getRivalPlayer().getBoard().getGraveyard().addCard(card);
            }
            gameController.getCurrentPlayer().getRivalPlayer().setCardsInHand(newRivalHand);
            ArrayList<Card> newRivalDeck = new ArrayList<>();
            for (Card card : gameController.getCurrentPlayer().getRivalPlayer().getBoard().getDeck().getAllCardsInMainDeck()) {
                if (!card.getCardName().equalsIgnoreCase(cardName)) newRivalDeck.add(card);
                else gameController.getCurrentPlayer().getRivalPlayer().getBoard().getGraveyard().addCard(card);
            }
            gameController.getCurrentPlayer().getRivalPlayer().getBoard().getDeck().setAllCardsInMainDeck(newRivalDeck);
        } else {
            new ChooseCardFromHandToSacrifice(trap).execute();
        }
    }
}

class SetCannotContinueAttack extends CardCommand {

    public SetCannotContinueAttack(Card card) {
        super(card);
    }

    @Override
    public void execute() {
        gameController.setCanContinue(false);
    }
}

class ReverseAttack extends CardCommand {

    public ReverseAttack(Card card) {
        super(card);
    }

    @Override
    public void execute() {
        Monster attackingMonster = (Monster) gameController.getAttackingCard();
        Player player = attackingMonster.getCardOwner();
        player.decreaseHealth(attackingMonster.getAttackPointInGame());
    }
}

class GoNextPhase extends CardCommand {

    public GoNextPhase(Card card) {
        super(card);
    }

    @Override
    public void execute() {
        gameController.nextPhase();
    }
}

class DestroyAllMonsters extends CardCommand {

    public DestroyAllMonsters(Card card) {
        super(card);
    }

    @Override
    public void execute() {
        Graveyard firstPlayerGraveyard = gameController.getFirstPlayer().getBoard().getGraveyard();
        Graveyard secondPlayerGraveyard = gameController.getSecondPlayer().getBoard().getGraveyard();
        for (Cell cell : gameController.getFirstPlayer().getBoard().getMonsters()){
            if (cell.getCard() != null){
                firstPlayerGraveyard.addCard(cell.getCard());
            }
        }
        for (Cell cell : gameController.getSecondPlayer().getBoard().getMonsters()){
            if (cell.getCard() != null){
                secondPlayerGraveyard.addCard(cell.getCard());
            }
        }
    }
}

class DestroyEnemyAttackingMonsters extends CardCommand{

    public DestroyEnemyAttackingMonsters(Card card){
        super(card);
    }

    @Override
    public void execute() {
        Player rivalPlayer = trap.getCardOwner().getRivalPlayer();
        Graveyard graveyard = trap.getCardOwner().getRivalPlayer().getBoard().getGraveyard();
        for (Cell cell : rivalPlayer.getBoard().getMonsters()){
            if (cell.getCard() != null){
                if (((Monster)cell.getCard()).getAttackOrDefense() == AttackOrDefense.ATTACK){
                    graveyard.addCard(cell.getCard());
                }
            }
        }
    }
}

class DestroySummonedCard extends CardCommand{

    public DestroySummonedCard(Card card){
        super(card);
    }

    @Override
    public void execute() {
        Card card = gameController.getSummonedCard();
        Graveyard graveyard = card.getCardOwner().getBoard().getGraveyard();
        graveyard.addCard(card);
    }
}