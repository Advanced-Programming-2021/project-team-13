package controll.gameController;

import enums.*;
import model.Cell;
import model.cards.Card;
import model.cards.Monster;
import model.cards.Spell;
import model.cards.Trap;
import model.players.AIPlayer;
import model.players.Player;
import view.ViewMaster;
import view.allmenu.GameView;
import view.allmenu.ShowGraveyardView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class GameController {
    private final GameView gameView;
    private final Player firstPlayer;
    private final Player secondPlayer;
    private final Player startingPlayer;
    private final ArrayList<Integer> notToDrawCardTurns;
    private final ArrayList<Trap> chain;
    private Card attackingCard;
    private Card summonedCard;
    private Player currentPlayer;
    private Phase currentPhase;
    private final int startingRounds;
    private int turnsPlayed;
    private int unitedCalcCurrent;
    private int unitedCalcRival;
    private boolean canContinueAttack;
    private boolean isInAttack;
    private boolean normalSummonHappened;
    private boolean specialSummonHappened;
    private boolean ritualSummonHappened;
    private boolean anySummonHappened;

    ///////////////////////////////////////////////////we need activation of spells and traps to work;;;;;;DAMN!//////////////////////////++messenger++some field spells++
    public GameController(GameView gameView, Player firstPlayer, Player secondPlayer, Player startingPlayer, int startingRounds) {
        this.gameView = gameView;
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.startingPlayer = startingPlayer;
        this.currentPlayer = startingPlayer;
        this.chain = new ArrayList<>();
        firstPlayer.getBoard().getDeck().shuffleMainDeck();
        secondPlayer.getBoard().getDeck().shuffleMainDeck();
        for (Card allCard : firstPlayer.getBoard().getDeck().getAllCards()) {
            allCard.setCardOwner(firstPlayer);
        }
        for (Card allCard : secondPlayer.getBoard().getDeck().getAllCards()) {
            allCard.setCardOwner(secondPlayer);
        }
        for (int i = 0; i < 6; i++) {
            firstPlayer.addCardToHand();
            secondPlayer.addCardToHand();
        }
        this.startingRounds = startingRounds;
        turnsPlayed = 0;
        currentPhase = Phase.DRAW_PHASE;
        notToDrawCardTurns = new ArrayList<>();
        canContinueAttack = true;
        isInAttack = false;
        normalSummonHappened = false;
        specialSummonHappened = false;
        ritualSummonHappened = false;
        anySummonHappened = false;
    }

    public boolean isAnySummonHappened() {
        return anySummonHappened;
    }

    public boolean isNormalSummonHappened() {
        return normalSummonHappened;
    }

    public boolean isRitualSummonHappened() {
        return ritualSummonHappened;
    }

    public boolean isSpecialSummonHappened() {
        return specialSummonHappened;
    }

    public boolean isInAttack() {
        return isInAttack;
    }

    public void setCurrentPhase(Phase currentPhase) {
        this.currentPhase = currentPhase;
    }

    public void addNotToDrawCardTurn(int turn) {
        notToDrawCardTurns.add(turn);
    }

    public ArrayList<Integer> getNotToDrawCardTurns() {
        return notToDrawCardTurns;
    }

    public Player getCurrentPlayer() {
        if (currentPlayer == firstPlayer)
            return firstPlayer;
        return secondPlayer;
    }

    public Card getAttackingCard() {
        return attackingCard;
    }

    public void setCanContinueAttack(boolean canContinueAttack) {
        this.canContinueAttack = canContinueAttack;
    }

    public boolean getCanContinueAttack() {
        return canContinueAttack;
    }

    public Player getStartingPlayer() {
        return startingPlayer;
    }

    public Player getFirstPlayer() {
        return firstPlayer;
    }

    public Player getSecondPlayer() {
        return secondPlayer;
    }

    public GameView getGameView() {
        return gameView;
    }

    public int getStartingRounds() {
        return startingRounds;
    }

    public int getTurnsPlayed() {
        return turnsPlayed;
    }

    public void changeCurrentPlayer() {
        if (currentPlayer == firstPlayer)
            currentPlayer = secondPlayer;
        else
            currentPlayer = firstPlayer;
        gameView.playerChanged(currentPlayer);
    }

    public void selectPlayerMonster(int cardAddress) {// no regex error!!! // are these handled or not!!??
        if (monsterSelectionCheck(cardAddress, currentPlayer)) {
            currentPlayer.setSelectedCard(currentPlayer.getBoard().getMonsterByAddress(cardAddress));
            gameView.printCardSelected();
            showSelectedCard();////////////////////////////////////////this needsssssssssss to be reversed !!!
        }
    }

    public void selectPlayerSpellOrTrap(int cardAddress) {
        if (spellSelectionCheck(cardAddress, currentPlayer)) {
            currentPlayer.setSelectedCard(currentPlayer.getBoard().getSpellOrTrapByAddress(cardAddress));
            gameView.printCardSelected();
            showSelectedCard();////////////////////////////////////////this needsssssssssss to be reversed !!!
        }
    }

    public void selectOpponentMonster(int cardAddress) {
        if (monsterSelectionCheck(cardAddress, currentPlayer.getRivalPlayer())) {
            currentPlayer.setSelectedCard
                    (currentPlayer.getRivalPlayer().getBoard().getMonsterByAddress(cardAddress));
            gameView.printCardSelected();
            showSelectedCard();////////////////////////////////////////this needsssssssssss to be reversed !!!
        }
    }

    public void selectOpponentSpellOrTrap(int cardAddress) {
        if (spellSelectionCheck(cardAddress, currentPlayer.getRivalPlayer())) {
            currentPlayer.setSelectedCard
                    (currentPlayer.getRivalPlayer().getBoard().getSpellOrTrapByAddress(cardAddress));
            gameView.printCardSelected();
            showSelectedCard();////////////////////////////////////////this needsssssssssss to be reversed !!!
        }
    }

    public void selectPlayerFieldCard() {
        if (fieldSpellCheck(currentPlayer)) {
            currentPlayer.setSelectedCard(currentPlayer.getBoard().getFieldSpell().getCard());
            gameView.printCardSelected();
        }
    }

    public void selectOpponentFieldCard() {
        if (fieldSpellCheck(currentPlayer.getRivalPlayer())) {
            currentPlayer.setSelectedCard
                    (currentPlayer.getRivalPlayer().getBoard().getFieldSpell().getCard());
            gameView.printCardSelected();
        }
    }

    public void selectPlayerHandCard(int cardAddress) {
        if (handCardCheck(cardAddress)) {
            currentPlayer.setSelectedCard(currentPlayer.getCardsInHand().get(cardAddress - 1));
            gameView.printCardSelected();
            showSelectedCard();////////////////////////////////////////this needsssssssssss to be reversed !!!
        }
    }

    public void deselectCard() {
        if (currentPlayer.getSelectedCard() == null) {
            gameView.printNoCardSelected();
            return;
        }
        currentPlayer.setSelectedCard(null);
        gameView.printCardDeselected();
    }

    public void showSelectedCard() {
        if (currentPlayer.getSelectedCard() == null)
            gameView.printNoCardSelected();
        else {
            Card card = currentPlayer.getSelectedCard();
            if (card.getCardOwner() == currentPlayer.getRivalPlayer() && card.getFace() == Face.DOWN)
                gameView.printCardInvisible();
            else gameView.showCard(card);
        }
    }


    public void findWinner() {
        if (checkEnded() != 4) {
            GameWinMenu gameWinMenu = new GameWinMenu(this);
            if (checkEnded() == 3) {
                gameWinMenu.announceWinner(null);
            } else if (checkEnded() == 2) {
                gameWinMenu.announceWinner(currentPlayer.getRivalPlayer());
            } else {
                gameWinMenu.announceWinner(currentPlayer);
            }
        }
    }

    private int checkEnded() {
        if (currentPlayer.getLifePoint() <= 0 && currentPlayer.getRivalPlayer().getLifePoint() <= 0) {
            currentPlayer.setLifePoint(0);
            currentPlayer.getRivalPlayer().setLifePoint(0);
            return 3;//draw
        } else if (currentPlayer.getLifePoint() <= 0) {
            currentPlayer.setLifePoint(0);
            return 2;//rival won
        } else if (currentPlayer.getRivalPlayer().getLifePoint() <= 0) {
            currentPlayer.getRivalPlayer().setLifePoint(0);
            return 1;//currentPlayer has won
        } else {
            if (currentPhase == Phase.DRAW_PHASE && currentPlayer.getBoard().getDeck().getAllCardsInMainDeck().size() == 0)
                return 2;//rival won
            else
                return 4;//no winner
        }
    }

    public void surrender() {
        if (startingRounds == 1)
            currentPlayer.getRivalPlayer().setWonRounds(1);
        else
            currentPlayer.getRivalPlayer().setWonRounds(2);
        if (currentPlayer.getLifePoint() > currentPlayer.getMaxLifePoint())
            currentPlayer.setMaxLifePoint(currentPlayer.getMaxLifePoint());
        if (currentPlayer.getRivalPlayer().getLifePoint() > currentPlayer.getRivalPlayer().getMaxLifePoint())
            currentPlayer.getRivalPlayer().setMaxLifePoint(currentPlayer.getRivalPlayer().getLifePoint());
        new GameWinMenu(this).announceWinner(currentPlayer.getRivalPlayer());
    }

    public void attack(int monsterNumber) {
        if (checkMonsterByMonster(monsterNumber)) {
            Monster rivalMonster = (Monster) currentPlayer.getRivalPlayer()
                    .getBoard().getMonsterByAddress(monsterNumber);
            Monster ourMonster = (Monster) currentPlayer.getSelectedCard();
            if (!rivalMonster.isAttackable()) {
                gameView.printCantAttackMonster();
                return;
            }
            isInAttack = true;
            ourMonster.setAttackedMonster(rivalMonster);
            rivalMonster.setAttacker(ourMonster);
            attackingCard = ourMonster;
            //check for enemy trap activation
            if (canContinueAttack) {
                monsterByMonsterAttack(rivalMonster, ourMonster);
            }
            attackingCard = null;
            ourMonster.setAttackedMonster(null);
            canContinueAttack = true;
            isInAttack = false;
            currentPlayer.setSelectedCard(null);
        }
    }

    private void monsterByMonsterAttack(Monster beenAttackedMonster, Monster attackingMonster) {
        beenAttackedMonster.setHasBeenAttacked(true);//// lioghhhhhhhhhhhhhhhhhhh
        activateSpecial(attackingMonster, beenAttackedMonster);//////////////////////sorting of which comes first is a problem we have to check!
        if (isSpecialAttack(attackingMonster, beenAttackedMonster))
            return;
        String rivalMonsterName = beenAttackedMonster.getCardName();
        attackingMonster.setAttackedInThisTurn(true);
        if (beenAttackedMonster.getAttackOrDefense() == AttackOrDefense.ATTACK) {
            int attackDifference = attackingMonster.getAttackPointInGame() - beenAttackedMonster.getAttackPointInGame();
            if (attackDifference > 0) {
                currentPlayer.getRivalPlayer().decreaseHealth(attackDifference);
                currentPlayer.getRivalPlayer().getBoard().getGraveyard().addCard(beenAttackedMonster);
                gameView.printOpponentMonsterDestroyed(attackDifference);
            } else if (attackDifference == 0) {
                currentPlayer.getBoard().getGraveyard().addCard(attackingMonster);
                currentPlayer.getRivalPlayer().getBoard().getGraveyard().addCard(beenAttackedMonster);
                gameView.printBothMonstersDestroyed();
            } else {
                currentPlayer.decreaseHealth(-attackDifference);
                currentPlayer.getBoard().getGraveyard().addCard(attackingMonster);
                gameView.printYourCardIsDestroyed(-attackDifference);
            }
        } else {
            int attackDifference = attackingMonster.getAttackPointInGame() - beenAttackedMonster.getDefencePointInGame();
            if (attackDifference > 0) {
                currentPlayer.getRivalPlayer().getBoard().getGraveyard().addCard(beenAttackedMonster);
                currentPlayer.getBoard().removeMonsterFromBoard(beenAttackedMonster);
                if (beenAttackedMonster.getFace() == Face.UP)
                    gameView.printDefensePositionDestroyed();
                else {
                    gameView.printDefensePositionDestroyedHidden(rivalMonsterName);  // if card was hidden theres another thing to show
                }
            } else if (attackDifference == 0) {
                if (beenAttackedMonster.getFace() == Face.UP)
                    gameView.printNoCardDestroyed();
                else {                                                          // does card turn after being attacked????????????
                    gameView.printNoCardDestroyedHidden(rivalMonsterName);
                }
            } else {
                currentPlayer.decreaseHealth(-attackDifference);
                if (beenAttackedMonster.getFace() == Face.UP)
                    gameView.printNoCardDestroyedYouReceivedDamage(-attackDifference);
                else {
                    gameView.printNoCardDestroyedYouReceivedDamageHidden(-attackDifference, rivalMonsterName);
                }
            }
            beenAttackedMonster.setFace(Face.UP);
        }
        gameView.printMap();
        if (attackingMonster.getAttacker().getCardName().equals("Suijin"))/////////////////////// fishyyyyyyyyyyyyy
            attackingMonster.setAttackPointInGame(beenAttackedMonster.getAttackNum());
        equipSpellRid();
    }

    public void activeEffect() {
        if (currentPlayer.getSelectedCard() == null) {
            gameView.printNoCardSelected();
            return;
        }
        if (!(currentPlayer.getSelectedCard() instanceof Spell) && !(currentPlayer.getSelectedCard() instanceof Trap)) {
            gameView.printActiveOnlyForSpellsAndTrap();
        } else if (currentPlayer.getSelectedCard() instanceof Spell) {
            activateSpell();
        } else {
            activateTrap();
        }
    }

    private void activateTrap() {
        Trap trap = (Trap) currentPlayer.getSelectedCard();
        if (trap.isActivated()) {
            gameView.printAlreadyActivated();
            return;
        }
        if (trap.getCardOwner() != currentPlayer && trap.getZone() == Zone.SPELL_TRAP_ZONE) {
            return;
        }
        if (!trap.getTrapAction().startActionCheck.canActivate()) {
            gameView.printPrepsNotDone();
            return;
        }
        trap.setActivated(true);
        trap.setFace(Face.UP);
        chain.add(trap);
        checkTrapActivation();
    }

    private void activateSpell() {
        if ((currentPhase != Phase.MAIN_PHASE_1) && (currentPhase != Phase.MAIN_PHASE_2)) { // wtf is this things problem?????????!
            gameView.printCantActiveThisTurn();
            return;
        }
        Spell spell = (Spell) currentPlayer.getSelectedCard();

        if (spellActive(spell)) {//////////////////fishy///////////////////////////
            gameView.printAlreadyActivated();
            return;
        }
        if (spell.getCardName().equalsIgnoreCase("Advanced ritual art"))
            checkRitualSummon(spell);
        else if (!spell.getSpellEffect().equalsIgnoreCase("Field")) {
            currentPlayer.getBoard().getGraveyard().addCard(currentPlayer.getBoard().getFieldSpell().getCard());
            currentPlayer.getBoard().setFieldSpell(spell);
            spell.setActivated(true);
            gameView.printSpellActivated();
        } else {
            if (currentPlayer.getBoard().getNumberOFSpellAndTrapInBoard() == 5) {
                gameView.printSpellZoneIsFull();
                return;
            }
            if (!checkPreparation(currentPlayer.getSelectedCard())) {
                gameView.printPrepsNotDone();
                return;
            }
            currentPlayer.getBoard().putSpellAndTrapInBoard(currentPlayer.getSelectedCard());
            gameView.printSpellActivated();
            if (spell.getType().equals("Equip")) {
                gameView.printSelectMonsterFromBoard();
                int num = gameView.getNum();
                String board = gameView.getAnswer();
                if (board.equalsIgnoreCase("our board"))
                    spell.setEquippedMonster
                            ((Monster) currentPlayer.getBoard().getMonsterByAddress(num));
                else if (board.equalsIgnoreCase("rival board"))
                    spell.setEquippedMonster((Monster) currentPlayer.getRivalPlayer().getBoard().getMonsterByAddress(num));
                spell.setActivated(true);
            } else {
                findEffect(spell);
                spell.setActivated(true);
                gameView.printMap();
                if (spell.getType().equals("Normal") || spell.getType().equals("Quick-play"))
                    currentPlayer.getBoard().getGraveyard().addCard(spell);
            }
        }
    }

    private void findEffect(Spell spell) { ////////these are not complete but hell of a ride !!!
        String effectName = spell.getCardName();
        if (effectName.equalsIgnoreCase("Monster Reborn"))
            monsterReborn();
        else if (effectName.equalsIgnoreCase("Terraforming"))
            terraforming();
        else if (effectName.equalsIgnoreCase("Pot of Greed"))
            potOfGreed();
        else if (effectName.equalsIgnoreCase("Raigeki"))
            raigeki();
        else if (effectName.equalsIgnoreCase("Change of Heart"))
            changeOfHeart();
        else if (effectName.equalsIgnoreCase("Harpie’s Feather Duster"))
            harpie();
//        else if (effectName.equalsIgnoreCase("Swords of Revealing Light"))
//            swordsOfRevealingLight();
        else if (effectName.equalsIgnoreCase("Dark Hole"))
            darkHole();
//        else if (effectName.equalsIgnoreCase("Supply Squad"))
//            supplySquad();
//        else if(effectName.equalsIgnoreCase("Ring of Defense"))
//            ringOfDefense();
        else if (effectName.equalsIgnoreCase("Spell Absorption"))
            spellAbsorption();
        else if (effectName.equalsIgnoreCase("Messenger of peace"))
            activeMessenger(spell);
        else if (effectName.equalsIgnoreCase("Twin Twisters"))
            twinTwisters(spell);
        else if (effectName.equalsIgnoreCase("Mystical space typhoon"))
            mysticalTyphoon();
    }

    private void potOfGreed() {/////fishyyyyyyy////////
        currentPlayer.addCardToHand();
        currentPlayer.addCardToHand();
    }

    private void terraforming() {//////////needs completion!!!
        showDeckInGame(currentPlayer); //// this needs completion!
        gameView.printSelectNum();
        currentPlayer.addCardToHand();
    }

    private void showDeckInGame(Player currentPlayer) {

    }

    private void twinTwisters(Spell spell) {//////not complete !!!!!#crap!!
        showCardsInHand(spell.getCardOwner());
        gameView.printSelectNum();
        currentPlayer.getBoard().getGraveyard()
                .addCard(currentPlayer.getCardsInHand().get(gameView.getNum()));
    }

    private void showCardsInHand(Player player) {
        for (int i = 0; i < player.getCardsInHand().size(); i++) {
            gameView.printCardInHand(player.getCardsInHand().get(i), i);
        }
    }


    private void mysticalTyphoon() {//////// just for other player??????? can we destroy our own spell??
        gameView.printSelectSpellOrTrap();
        currentPlayer.getRivalPlayer().getBoard().getGraveyard()
                .addCard(currentPlayer.getRivalPlayer().getBoard()
                        .getSpellOrTrapByAddress(gameView.getNum()));
    }

    private void activeMessenger(Spell spell) {
        spell.setActivated(true);
    }

    private void spellAbsorption() {

    }

    private void darkHole() {
        for (Cell monster : currentPlayer.getBoard().getMonsters()) {
            currentPlayer.getBoard().getGraveyard().addCard(monster.getCard());
        }
        for (Cell monster : currentPlayer.getRivalPlayer().getBoard().getMonsters()) {
            currentPlayer.getRivalPlayer().getBoard().getGraveyard().addCard(monster.getCard());
        }
    }

    private void harpie() {
        for (Cell cell : currentPlayer.getRivalPlayer().getBoard().getSpellOrTrap()) {
            currentPlayer.getRivalPlayer().getBoard().getGraveyard().addCard(cell.getCard());
        }
        currentPlayer.getRivalPlayer().getBoard().getGraveyard()
                .addCard(currentPlayer.getRivalPlayer().getBoard().getFieldSpell().getCard());
    }

    private void changeOfHeart() {
//        gameView.printSelectRivalMonster();
//        Monster rivalMonster = getRivalPlayer().getBoard().getMonsterByAddress(gameView.getNum());
//        currentPlayer.getExtraCards.add(rivalMonster);

    }

    private void raigeki() {
        for (Cell monster : currentPlayer.getRivalPlayer().getBoard().getMonsters()) {
            currentPlayer.getRivalPlayer().getBoard().getGraveyard().addCard(monster.getCard());
        }
    }

    private void monsterReborn() { //چقد توابعش سمیه!!!
        gameView.selectGraveyard();
        Player graveyardOwner = null;
        String graveyardName = gameView.getAnswer();
        if (graveyardName.equalsIgnoreCase("our graveyard")) {
            graveyardOwner = currentPlayer;
        } else if (graveyardName.equalsIgnoreCase("rival graveyard")) {
            graveyardOwner = currentPlayer.getRivalPlayer();
        }
        if (graveyardOwner == null) {
            System.out.println("wrong graveyard name");
            return;
        }
        ShowGraveyardView graveyard = new ShowGraveyardView(graveyardOwner);
        graveyard.getShowGraveyardController().showGraveyard();
        gameView.printSelectNum();
        graveyard.getShowGraveyardController().selectCardFromGraveyard(gameView.getNum(), graveyardOwner);
        specialSummon();

    }


    private boolean checkPreparation(Card card) {
/*        if (card.getCardName().equalsIgnoreCase("Twin Twisters"))
            return twinTwistersCheck();
        else if (card.getCardName().equalsIgnoreCase("Messenger of peace"))
            return checkMessenger();
        else if (card.getCardName().equalsIgnoreCase("Supply Squad"))
            return supplySquad();*/
        return true;
    }

    private boolean spellActive(Spell spell) {
        return spell.isActivated();
    }

    private void equipSpellRid() {
        SwordOfDesRid();
        BlackPendantRid();
        UnitedRid();
        magnumShieldRid();
    }


    private void activateSpecial(Monster ourMonster, Monster rivalMonster) {
        commandKnight(ourMonster, rivalMonster);
        fieldSpell(ourMonster, rivalMonster);
        equipSpell();
    }


    private void equipSpell() {
        SwordOfDes();
        BlackPendant();
        United();
        MagnumShield();
    }

    private void MagnumShield() {
        increaseAndDecrease(currentPlayer, "Magnum Shield", 1, 1);
        increaseAndDecrease(currentPlayer.getRivalPlayer(), "Magnum Shield", 1, 1);
    }

    private void United() {///////////////////////////////////////fishyyyyyyyyyyyyyyy///////////////////////
        unitedCalcCurrent = unitedCalculate(currentPlayer);
        unitedCalcRival = unitedCalculate(currentPlayer.getRivalPlayer());
        increaseAndDecrease(currentPlayer, "United We Stand", unitedCalcCurrent, unitedCalcCurrent);
        increaseAndDecrease(currentPlayer.getRivalPlayer(), "United We Stand", unitedCalcRival, unitedCalcRival);
    }


    private void BlackPendant() {
        increaseAndDecrease(currentPlayer, "Black Pendant", 500, 0);
        increaseAndDecrease(currentPlayer.getRivalPlayer(), "Black Pendant", 500, 0);
    }

    private void SwordOfDes() {
        increaseAndDecrease(currentPlayer, "Sword of Dark Destruction", 400, -200);
        increaseAndDecrease(currentPlayer.getRivalPlayer(), "Sword of Dark Destruction", 400, -200);
    }

    private void increaseAndDecrease(Player player, String spellName, int atkAmount, int defAmount) {
        for (Cell cell : player.getBoard().getSpellOrTrap()) {
            if (cell.getCard() != null && cell.getCard().getCardName().equals(spellName)) {
                Spell spell = (Spell) cell.getCard();
                if (spellName.equals("Sword of Dark Destruction")) {
                    if (spell.getEquippedMonster().getMonsterType().equals("Fiend") ||
                            spell.getEquippedMonster().getMonsterType().equals("Spellcaster")) {
                        spell.getEquippedMonster().increaseAttackPoint(atkAmount);
                        spell.getEquippedMonster().increaseAttackPoint(defAmount);
                    }
                } else if (spellName.equals("Magnum Shield")) {
                    if (spell.getEquippedMonster().getMonsterType().equals("Warrior")) {
                        if (spell.getEquippedMonster().getAttackOrDefense() == AttackOrDefense.ATTACK) {
                            spell.getEquippedMonster().increaseDefensePoint
                                    (spell.getEquippedMonster().getDefencePointInGame() * atkAmount);
                        } else {
                            spell.getEquippedMonster().increaseDefensePoint
                                    (spell.getEquippedMonster().getAttackPointInGame() * defAmount);
                        }
                    }
                } else {
                    spell.getEquippedMonster().increaseAttackPoint(atkAmount);
                    spell.getEquippedMonster().increaseAttackPoint(defAmount);
                }
            }
        }
    }

    private int unitedCalculate(Player player) {
        int counter = 0;
        for (Cell monster : player.getBoard().getMonsters()) {
            if (monster.getCard() != null && monster.getCard().getFace() == Face.UP)
                counter++;
        }
        return counter * 800;
    }

    private void SwordOfDesRid() {
        increaseAndDecrease(currentPlayer, "Sword of Dark Destruction", -400, 200);
        increaseAndDecrease(currentPlayer.getRivalPlayer(), "Sword of Dark Destruction", -400, 200);
    }

    private void BlackPendantRid() {
        increaseAndDecrease(currentPlayer, "Black Pendant", -500, 0);
        increaseAndDecrease(currentPlayer.getRivalPlayer(), "Black Pendant", -500, 0);
    }

    private void UnitedRid() {///////////////////////////////////////fishyyyyyyyyyyyyyyy///////////////////////
        increaseAndDecrease(currentPlayer, "United We Stand", -unitedCalcCurrent, -unitedCalcCurrent);
        increaseAndDecrease(currentPlayer.getRivalPlayer(), "United We Stand", -unitedCalcRival, -unitedCalcRival);
    }

    private void magnumShieldRid() {
        increaseAndDecrease(currentPlayer, "Magnum Shield", -1, -1);
        increaseAndDecrease(currentPlayer.getRivalPlayer(), "Magnum Shield", -1, -1);
    }

    private void fieldSpell(Monster ourMonster, Monster rivalMonster) {////// problems: all monsters on board!!!! --- Can be shorter?maybe !!!
        checkField(ourMonster);
        checkField(rivalMonster);
    }

    private void checkField(Monster monster) {
        if (monster.getCardOwner().getBoard().getFieldSpell().getCard() == null)
            return;
        yami(monster);
        forest(monster);
        closedForest(monster);
        UMIIRUKA(monster);
    }

    private void UMIIRUKA(Monster monster) {
        if (monster.getFieldSpell().getCardName().equals("UMIIRUKA")) {
            UMIIRUKAIncrease(monster, -500, 400);
            monster.setFieldSpell(null);
        }
        if (!monster.getCardOwner().getBoard().getFieldSpell()
                .getCard().getCardName().equals("UMIIRUKA"))
            return;
        monster.setFieldSpell(monster.getCardOwner().getBoard().getFieldSpell().getCard());
        UMIIRUKAIncrease(monster, 500, -400);
    }

    private void UMIIRUKAIncrease(Monster monster, int atkAmount, int defAmount) {
        fieldIncreaseAtk(monster.getCardOwner(), "Aqua", atkAmount);
        fieldIncreaseDef(monster.getCardOwner(), "Aqua", defAmount);
    }

    private void closedForest(Monster monster) {
        if (monster.getFieldSpell().getCardName().equals("Closed Forest")) {
            closedForestIncrease(monster,
                    monster.getCardOwner().getBoard().getGraveyard().getAllCards().size() * 100);
            monster.setFieldSpell(null);
        }
        if (!monster.getCardOwner().getBoard().getFieldSpell()
                .getCard().getCardName().equals("Closed Forest"))
            return;
        monster.setFieldSpell(monster.getCardOwner().getBoard().getFieldSpell().getCard());
        closedForestIncrease(monster,
                monster.getCardOwner().getBoard().getGraveyard().getAllCards().size() * 100);
    }

    private void closedForestIncrease(Monster monster, int amount) {
        fieldIncreaseAtk(monster.getCardOwner(), "Beast-Type", amount);
    }

    private void forest(Monster monster) {
        if (monster.getFieldSpell().getCardName().equals("Forest")) {
            forestIncrease(monster, -200);
            monster.setFieldSpell(null);
        }
        if (!monster.getCardOwner().getBoard().getFieldSpell()
                .getCard().getCardName().equals("Forest"))
            return;
        monster.setFieldSpell(monster.getCardOwner().getBoard().getFieldSpell().getCard());
        forestIncrease(monster, 200);
    }

    private void forestIncrease(Monster monster, int amount) {
        fieldIncreaseAtk(monster.getCardOwner(), "Insect", amount);
        fieldIncreaseAtk(monster.getCardOwner(), "Beast", amount);
        fieldIncreaseAtk(monster.getCardOwner(), "Beast-Warrior", amount);
        fieldIncreaseDef(monster.getCardOwner(), "Insect", amount);
        fieldIncreaseDef(monster.getCardOwner(), "Beast", amount);
        fieldIncreaseDef(monster.getCardOwner(), "Beast-Warrior", amount);
    }

    private void yami(Monster monster) {
        if (monster.getFieldSpell().getCardName().equals("Yami")) {
            yamiIncrease(monster, -200);
            monster.setFieldSpell(null);
        }
        if (!monster.getCardOwner().getBoard().getFieldSpell()
                .getCard().getCardName().equals("Yami"))
            return;
        monster.setFieldSpell(monster.getCardOwner().getBoard().getFieldSpell().getCard());
        yamiIncrease(monster, 200);
    }

    private void yamiIncrease(Monster monster, int amount) {
        fieldIncreaseAtk(monster.getCardOwner(), "Fiend", amount);
        fieldIncreaseAtk(monster.getCardOwner(), "Spellcaster", amount);
        fieldIncreaseAtk(monster.getCardOwner(), "Fairy", -amount);
        fieldIncreaseDef(monster.getCardOwner(), "Fiend", amount);
        fieldIncreaseDef(monster.getCardOwner(), "Spellcaster", amount);
        fieldIncreaseDef(monster.getCardOwner(), "Fairy", -amount);
    }

    public void fieldIncreaseAtk(Player player, String monsterType, int amount) {
        for (Cell monster : player.getBoard().getMonsters()) {
            Monster boardMonster = (Monster) monster.getCard();
            if (boardMonster.getMonsterType().equals(monsterType))
                boardMonster.increaseAttackPoint(amount);
        }
    }

    public void fieldIncreaseDef(Player player, String monsterType, int amount) {
        for (Cell monster : player.getBoard().getMonsters()) {
            Monster boardMonster = (Monster) monster.getCard();
            if (boardMonster.getMonsterType().equals(monsterType))
                boardMonster.increaseDefensePoint(amount);
        }
    }

    private void commandKnight(Monster ourMonster, Monster rivalMonster) {
        checkCommandKnight(ourMonster, currentPlayer);
        checkCommandKnight(rivalMonster, currentPlayer.getRivalPlayer());
        boolean attackable = true;
        for (Cell monster : rivalMonster.getCardOwner().getBoard().getMonsters()) {
            if (monster.getCard() != null && !monster.getCard().getCardName().equals("Command knight")) {
                attackable = false;
                break;
            }
        }
        if (attackable) return;
        for (Cell monster : rivalMonster.getCardOwner().getBoard().getMonsters()) {
            if (monster.getCard() != null && monster.getCard().getCardName().equals("Command knight")) {
                Monster commandKnight = (Monster) monster.getCard();
                if (commandKnight.getFace() == Face.UP)
                    commandKnight.setAttackable(false);
            }
        }

    }

    private void checkCommandKnight(Monster activationMonster, Player player) {
        activationMonster.decreaseAttackPoint(activationMonster.getCommandKnightsActive().size() * 400);
        activationMonster.getCommandKnightsActive().clear();
        for (Cell monster : player.getBoard().getMonsters()) { // fookin cell has a problem nigga!!
            if (monster.getCard() != null)
                if (monster.getCard().getCardName().equals("Command knight") &&
                        monster.getCard().getFace() == Face.UP && monster.getCard() != activationMonster) {
                    activationMonster.setCommandKnightsActive((Monster) monster.getCard());
                }
        }
        activationMonster.increaseAttackPoint(activationMonster.getCommandKnightsActive().size() * 400);
    }

    private boolean isSpecialAttack(Monster ourMonster, Monster rivalMonster) {
        if (messengerOfPeace(ourMonster)) {
            gameView.printCantAttackBecauseOfMessenger();
            gameView.printMap();
            return true;
        } else if (rivalMonster.getCardName().equals("Marshmallon")) {
            marshmallon(ourMonster, rivalMonster);
            gameView.printMap();
            return true;
        } else if (rivalMonster.getCardName().equals("Texchanger")) {
            if (!rivalMonster.isAttackedInThisTurn())
                return false;
            texchanger(rivalMonster);
            return true;
        } else if (rivalMonster.getCardName().equals("Exploder Dragon")) {
            exploderDragon(ourMonster, rivalMonster);
            gameView.printMap();
            return true;
        } else if (ourMonster.getCardName().equals("The Calculator")) {/// think we need to make it first(it can be denied by messenger)
            theCalculator(ourMonster); // we just need some calculations !!!!!!! thats all
            gameView.printMap();
            return false;
        } else if (rivalMonster.getCardName().equals("Suijin")) {
            if (!rivalMonster.isActiveAbility()) {
                rivalMonster.setActiveAbility(true);
                ourMonster.setAttackPointInGame(0);
            }
            return false;
        }
        return false;
    }

    private boolean messengerOfPeace(Monster attacker) {////////////////////////where to activate??????/////////////////
        if (!checkForActive(currentPlayer, "Messenger of peace")
                && !checkForActive(currentPlayer.getRivalPlayer(), "Messenger of peace"))
            return false;
        return attacker.getAttackPointInGame() >= 1500;
    }

    private boolean checkForActive(Player player, String spellName) {
        for (Cell cell : player.getBoard().getSpellOrTrap()) {
            if (cell.getCard() instanceof Spell) {
                Spell spell = (Spell) cell.getCard();
                if (spell != null && spell.getCardName().equals(spellName) && spell.isActivated())
                    return true;
            }
        }
        return false;
    }

    private void theCalculator(Monster ourMonster) {
        int ourAttackNum = 0;
        for (Cell monster : ourMonster.getCardOwner().getBoard().getMonsters()) {
            Monster monsterCard = (Monster) monster.getCard();
            if (monsterCard.getFace() == Face.UP && !monsterCard.getCardName().equals("The Calculator")) // need this because we cant double the damage if there were 2 ///
                ourAttackNum += monsterCard.getLevel();
        }
        ourMonster.setAttackPointInGame(300 * ourAttackNum);
    }

    private void exploderDragon(Monster ourMonster, Monster rivalMonster) {
        if (rivalMonster.getAttackOrDefense() == AttackOrDefense.ATTACK) {
            int attackDiff = ourMonster.getAttackPointInGame() - rivalMonster.getAttackPointInGame();
            ourMonster.getCardOwner().getBoard().getGraveyard().addCard(ourMonster);
            if (attackDiff >= 0) {
                rivalMonster.getCardOwner().getBoard().getGraveyard().addCard(rivalMonster);
                gameView.printBothMonstersDestroyed();
            } else {
                ourMonster.getCardOwner().decreaseHealth(-attackDiff);
                gameView.printYourCardIsDestroyed(-attackDiff);
            }
        } else {
            if (rivalMonster.getFace() == Face.DOWN)
                gameView.printOpponentCardsName(rivalMonster.getCardName());
            int attackDiff = ourMonster.getAttackPointInGame() - rivalMonster.getDefencePointInGame();
            ourMonster.getCardOwner().getBoard().getGraveyard().addCard(ourMonster);
            if (attackDiff >= 0) {
                rivalMonster.getCardOwner().getBoard().getGraveyard().addCard(rivalMonster);
                gameView.printBothMonstersDestroyed();
            } else {
                ourMonster.getCardOwner().decreaseHealth(-attackDiff);
                gameView.printNoCardDestroyedYouReceivedDamage(-attackDiff);
            }
        }
        rivalMonster.setFace(Face.UP);
    }

    private void texchanger(Monster rivalMonster) {///// lets see....
        if (rivalMonster.isActiveAbility())
            return;
        rivalMonster.setActiveAbility(true);
        gameView.printAttackDisruptedByTaxchanger();
        changeCurrentPlayer();
        if (!gameView.doesRivalWantCyberse())
            return;
        gameView.printSelectGraveyardHandOrDeck();
        String fromWhere = gameView.getAnswer();
        if (fromWhere.equalsIgnoreCase("Graveyard")) {
            ShowGraveyardView graveyard = new ShowGraveyardView(currentPlayer);
            graveyard.getShowGraveyardController().showGraveyard();
            graveyard.getShowGraveyardController().selectCardFromGraveyard(gameView.getNum(), currentPlayer);
            specialSummon();
        } else if (fromWhere.equalsIgnoreCase("Hand")) {
            showCardsInHand(currentPlayer);
            selectPlayerHandCard(gameView.getNum());
            specialSummon();
        } else if (fromWhere.equalsIgnoreCase("Deck")) {
            showDeckInGame(currentPlayer);
        } else
            gameView.printInvalidLocation();
        changeCurrentPlayer();
    }

    private boolean checkCyberse(String cyberse) {
        if (cyberse.equalsIgnoreCase("Bitron"))
            return true;
        else if (cyberse.equalsIgnoreCase("Texchanger")) {
            gameView.printNoCyberseWithAbility();
            return false;
        } else if (cyberse.equalsIgnoreCase("Leotron"))
            return true;
        gameView.printInvalidCyberseName();
        return false;
    }

    private void marshmallon(Monster ourMonster, Monster rivalMonster) {/// does it get attack prints? we'll never know!!
        if (rivalMonster.getAttackOrDefense() == AttackOrDefense.ATTACK) {
            int attackDiff = ourMonster.getAttackPointInGame() - rivalMonster.getAttackPointInGame();
            if (attackDiff > 0) {
                rivalMonster.getCardOwner().decreaseHealth(attackDiff);
                gameView.printNoCardDestroyedRivalReceivedDamage(attackDiff);
            } else if (attackDiff == 0) {
                rivalMonster.getCardOwner().getBoard().getGraveyard().addCard(rivalMonster);
                gameView.printNoCardDestroyed();
            } else {
                ourMonster.getCardOwner().decreaseHealth(-attackDiff);
                gameView.printYourCardIsDestroyed(-attackDiff);
            }
            gameView.printYouReceivedDamage(1000);
        } else {
            int attackDiff = ourMonster.getAttackPointInGame() - rivalMonster.getDefencePointInGame();
            if (attackDiff > 0) {
                if (rivalMonster.getFace() == Face.DOWN) {
                    gameView.printOpponentCardsName("Marshmallon");
                    rivalMonster.getCardOwner().decreaseHealth(attackDiff);
                }
            } else if (attackDiff == 0) {
                if (rivalMonster.getFace() == Face.DOWN)
                    gameView.printOpponentCardsName("Marshmallon");

            } else {
                if (rivalMonster.getFace() == Face.DOWN)
                    gameView.printOpponentCardsName("Marshmallon");
                ourMonster.getCardOwner().decreaseHealth(-attackDiff);
            }
            if (rivalMonster.getFace() == Face.DOWN)
                ourMonster.getCardOwner().decreaseHealth(1000);
            rivalMonster.setFace(Face.UP);
        }
    }

    public void directAttack(boolean isAI) {  // somehow same as attack only diff is rival card number!!!
        if (checkDirectAttack()
                || isAI) {
            Monster ourMonster = (Monster) currentPlayer.getSelectedCard();
            currentPlayer.getRivalPlayer().decreaseHealth(ourMonster.getAttackNum());
            gameView.printYourOpponentReceivesDamage(ourMonster.getAttackNum());
            gameView.printMap();
            ourMonster.setAttackedInThisTurn(true);
        }
        deselectCard();
    }

    private boolean monsterSelectionCheck(int cardAddress, Player player) {
        if (cardAddress <= 0 || cardAddress > 5) {
            gameView.printInvalidSelection();
            return false;
        }
        if (player.getBoard().getMonsterByAddress(cardAddress) == null) {
            gameView.printNotFoundCard();
            return false;
        }
        return true;
    }

    private boolean spellSelectionCheck(int cardAddress, Player player) {
        if (cardAddress <= 0 || cardAddress > 4) {
            gameView.printInvalidSelection();
            return false;
        }
        if (player.getBoard().getSpellOrTrapByAddress(cardAddress) == null) {
            gameView.printNotFoundCard();
            return false;
        }
        return true;
    }

    private boolean fieldSpellCheck(Player player) {
        if (player.getBoard().getFieldSpell().getCard() == null) {
            gameView.printNotFoundCard();
            return false;
        }
        return true;
    }

    private boolean handCardCheck(int cardAddress) {
        if (cardAddress <= 0 || cardAddress > currentPlayer.getCardsInHand().size()) {
            gameView.printInvalidSelection();
            return false;
        }
        if (currentPlayer.getCardsInHand().get(cardAddress - 1) == null) {
            gameView.printNotFoundCard();
            return false;
        }
        return true;
    }

    private boolean checkDirectAttack() {
        if (checkBeforeAnyAttack()) return false;
        if (currentPlayer.getRivalPlayer().getBoard().getNumberOfMonsterInBoard() != 0) {
            gameView.printCantAttackDirectly();
            return false;
        }
        return true;
    }

    private boolean checkMonsterByMonster(int rivalMonsterNum) {
        if (checkBeforeAnyAttack()) return false;
        if (currentPlayer.getRivalPlayer().getBoard()
                .getMonsterByAddress(rivalMonsterNum) == null) {
            gameView.printNoCardToAttack();
            return false;
        }
        return true;
    }

    private boolean checkBeforeAnyAttack() {
        if (currentPlayer.getSelectedCard() == null) {
            gameView.printNoCardSelected();
            return true;
        }
        if (!currentPlayer.getBoard().isMonsterOnBoard(currentPlayer.getSelectedCard())) {
            gameView.printCantAttack();
            return true;
        }
        if (currentPhase != Phase.BATTLE_PHASE) {
            gameView.printWrongPhase();
            return true;
        }
        if (hasCardAttacked(currentPlayer.getSelectedCard())) {
            gameView.printAlreadyAttacked();
            return true;
        }
        return false;
    }

    private boolean hasCardAttacked(Card selectedCard) {  ////// this need to be completed , maybe a boolean???////////////////////////////////////////////////////////////
        Monster monster = (Monster) selectedCard;
        return monster.isAttackedInThisTurn();
    }

    public void nextPhase() {
        if (currentPhase == Phase.END_PHASE) {
            currentPhase = Phase.DRAW_PHASE;
            gameView.printCurrentPhase();
            turnsPlayed++;
            if (!notToDrawCardTurns.contains(turnsPlayed) && currentPlayer.getCardsInHand().size() < 6) {
                if (currentPlayer.getBoard().getDeck().getAllCardsInMainDeck().size() != 0) {
                    Card card = currentPlayer.addCardToHand();
                    gameView.printCardAddedToHand(card);
                } else {
                    new GameWinMenu(this).announceWinner(currentPlayer.getRivalPlayer());
                }
            }
        } else if (currentPhase == Phase.DRAW_PHASE) {
            currentPhase = Phase.STANDBY_PHASE;
            gameView.printCurrentPhase();
            //do some Effects!!!

        } else if (currentPhase == Phase.STANDBY_PHASE) {
            currentPhase = Phase.MAIN_PHASE_1;
            gameView.printCurrentPhase();
            gameView.printMap();//to complete
        } else if (currentPhase == Phase.MAIN_PHASE_1) {
            currentPhase = Phase.BATTLE_PHASE;
            if (turnsPlayed == 0) {
                currentPhase = Phase.DRAW_PHASE;
                currentPlayer.setSetOrSummonInThisTurn(false);
                changeCurrentPlayer();
                gameView.printCurrentPhase();
                gameView.printWhoseTurn();
                turnsPlayed++;
            }
            gameView.printCurrentPhase();//to complete
        } else if (currentPhase == Phase.BATTLE_PHASE) {
            currentPhase = Phase.MAIN_PHASE_2;
            gameView.printCurrentPhase();
            //to comp
        } else if (currentPhase == Phase.MAIN_PHASE_2) {
            reset();
            currentPhase = Phase.END_PHASE;
            currentPlayer.setSetOrSummonInThisTurn(false);
            changeCurrentPlayer();
            gameView.printCurrentPhase();
            gameView.printWhoseTurn();
        }
    }

    private void reset() {
        anySummonHappened = false;
        normalSummonHappened = false;
        ritualSummonHappened = false;
        specialSummonHappened = false;
        for (Cell monster : currentPlayer.getBoard().getMonsters()) {
            if (monster.getCard() != null) {
                Monster monsterInCell = ((Monster) (monster.getCard()));
                if (!monster.getCard().getCardName().equalsIgnoreCase("Suijin"))
                    monsterInCell.setActiveAbility(false);
                monsterInCell.setAttackable(true);
                monsterInCell.setAttacker(null);
                monsterInCell.setHasBeenAttacked(false);
                monsterInCell.setSetInThisTurn(false);
                monsterInCell.setAttackedInThisTurn(false);
                monsterInCell.setHaveChangePositionInThisTurn(false);
                if (monsterInCell.isScanner())
                    checkScannerEffect(monsterInCell, false);
                if (monsterInCell.getCardName().equalsIgnoreCase("Herald Of Creation"))
                    heraldOfCreationEffect(monsterInCell);
            }
        }
    }


    public void checksBeforeSummon() { //need some change, SOME EFFECTIVE MONSTER CAN NOT NORMAL SUMMON!
        if (currentPlayer.getSelectedCard() == null)
            gameView.printNoCardSelected();
        else {
            if (currentPlayer.getCardsInHand().contains(currentPlayer.getSelectedCard())
                    && currentPlayer.getSelectedCard() instanceof Monster
                    && ((Monster) currentPlayer.getSelectedCard()).getMonsterCardType() != MonsterCardType.RITUAL
                    && !(currentPlayer.getSelectedCard()).getCardName().equalsIgnoreCase("the tricky")) {
                if (currentPhase != Phase.MAIN_PHASE_1 && currentPhase != Phase.MAIN_PHASE_2)
                    gameView.printNotInMainPhase();
                else {
                    if (currentPlayer.getBoard().isThereEmptyPlaceMonsterZone()) {
                        if (currentPlayer.isSetOrSummonInThisTurn())
                            gameView.printAlreadySetOrSummon();
                        else if (currentPlayer.getSelectedCard().getCardName().equalsIgnoreCase("Beast king barbaros"))
                            beatsKingBarbaros((Monster) currentPlayer.getSelectedCard());
                        else if (currentPlayer.getSelectedCard().getCardName().equalsIgnoreCase("Terratiger, the Empowered Warrior"))
                            terratiger((Monster) currentPlayer.getSelectedCard());
                        else
                            summonAndSpecifyTribute();
                    } else gameView.printMonsterZoneFull();
                }
            } else gameView.printCantSummon();
        }
    }

    public void normalSummon(Monster monster, AttackOrDefense position) {
        anySummonHappened = true;
        if (monster.getCardName().equalsIgnoreCase("Mirage Dragon"))
            mirageDragonEffect(monster);
        if (monster.getCardName().equalsIgnoreCase("scanner"))
            if (checkScannerEffect(monster, true)) return;
        summonedCard = monster;
        gameView.printSummonSuccessfully();
        currentPlayer.getCardsInHand().remove(monster);
        monster.setSetInThisTurn(true);
        monster.setZone(Zone.MONSTER_ZONE);
        monster.setFace(Face.UP);
        monster.setAttackOrDefense(position);
        currentPlayer.setSetOrSummonInThisTurn(true);
        currentPlayer.setSelectedCard(null);
        currentPlayer.getBoard().putMonsterInBoard(monster);
        gameView.printMap();
    }

    private boolean checkScannerEffect(Monster monster, boolean summoning) {
        List<Monster> rivalGraveYardMonsters = getCurrentPlayer().getRivalPlayer().getBoard().getGraveyard()
                .getAllCards().stream().filter(e -> e instanceof Monster).map(e -> (Monster) e).collect(Collectors.toList());
        if (rivalGraveYardMonsters.size() == 0) {
            if (summoning) gameView.printCantSummon();
            return true;
        }
        boolean isThereMonsterExceptScanner = false;
        for (Monster m : rivalGraveYardMonsters)
            if (!m.isScanner()) {
                isThereMonsterExceptScanner = true;
                break;
            }
        if (!isThereMonsterExceptScanner) {
            if (summoning) gameView.printCantSummon();
            return true;
        }
        scannerEffect(monster, rivalGraveYardMonsters);
        return false;
    }

    private void scannerEffect(Monster scanner, List<Monster> rivalGraveYardMonsters) {
        Monster monster;
        do {
            int number = gameView.chooseMonsterForSummonScanner(rivalGraveYardMonsters);
            monster = rivalGraveYardMonsters.get(number);
        } while (monster.getCardName().equalsIgnoreCase("scanner"));
        scanner.setAttackPointInGame(monster.getAttackNum());
        scanner.setDefencePointInGame(monster.getDefenseNum());
        scanner.setMonsterAttribute(monster.getMonsterAttribute());
        scanner.setMonsterType(monster.getMonsterType());
        scanner.setLevel(monster.getLevel());
        scanner.setCardName(monster.getCardName());
        scanner.setScanner(true);
        scanner.setActiveAbility(true);
    }

    private void heraldOfCreationEffect(Monster monsterInCell) {
        List<Monster> rivalGraveYardMonster = getCurrentPlayer().getRivalPlayer().getBoard().getGraveyard().getAllCards()
                .stream().filter(e -> e instanceof Monster).map(e -> (Monster) e).filter(e -> e.getLevel() >= 7)
                .collect(Collectors.toList());
        if (rivalGraveYardMonster.size() > 0)
            if (gameView.wantToUseHeraldAbility()) {
                Monster monster;
                int number = gameView.chooseMonsterForHeraldOfCreation(rivalGraveYardMonster);
                monster = rivalGraveYardMonster.get(number);
                getCurrentPlayer().getCardsInHand().add(monster);
            }
    }

    private void mirageDragonEffect(Monster monster) {
        getCurrentPlayer().getRivalPlayer().setCanActiveTrap(false);
        monster.setActiveAbility(true);
    }

    private void terratiger(Monster terratiger) {
        boolean isExist = false;
        if (getCurrentPlayer().getBoard().getNumberOfMonsterInBoard() <= 3)
            for (Card card : currentPlayer.getCardsInHand()) {
                if (card instanceof Monster)
                    if (((Monster) card).getLevel() <= 4) {
                        isExist = true;
                        break;
                    }
            }
        if (isExist)
            gameView.askWantSummonedAnotherMonsterTerratiger();
        terratiger.setActiveAbility(true);
        normalSummon(terratiger, AttackOrDefense.ATTACK);
        checkTrapActivation();
    }

    private void summonAndSpecifyTribute() {
        Monster monster = (Monster) currentPlayer.getSelectedCard();
        int numberOfTribute = monster.howManyTributeNeed();
        if (numberOfTribute != 0) {
            int numberOfMonsterInOurBoard = currentPlayer.getBoard().getNumberOfMonsterInBoard();
            if (numberOfMonsterInOurBoard < numberOfTribute) {
                currentPlayer.setSelectedCard(null);
                gameView.printThereArentEnoughMonsterForTribute();
                return;
            }
            if (!getTribute(numberOfTribute)) {
                gameView.printMap();
                return;
            }
        }
        normalSummonHappened = true;
        normalSummon(monster, AttackOrDefense.ATTACK);
        checkTrapActivation();
    }

    private void beatsKingBarbaros(Monster monster) {//// hooman:cell is broken- 1400/2/27\\10:33
        if (currentPlayer.getBoard().getNumberOfMonsterInBoard() >= 3) {
            if (gameView.doYouWantTributeBarBaros()) {
                gameView.getTributeForBarbaros();
            } else {
                monster.setAttackPointInGame(1900);
                for (Cell cell : currentPlayer.getRivalPlayer().getBoard().getMonsters()) {
                    currentPlayer.getRivalPlayer().getBoard().getGraveyard().addCard(cell.getCard());
                    cell.setCard(null);
                }
            }
        }
        monster.setActiveAbility(true);
        normalSummonHappened = true;
        normalSummon(monster, AttackOrDefense.ATTACK);
        checkTrapActivation();
    }

    public boolean checkBarbarosInput(int monsterNumber1, int monsterNumber2, int monsterNumber3) {
        if (monsterNumber1 <= 0 || monsterNumber1 >= 6
                || monsterNumber2 <= 0 || monsterNumber2 >= 6
                || monsterNumber3 <= 0 || monsterNumber3 >= 6) return false;
        Monster monster1 = (Monster) currentPlayer.getBoard().getMonsterByAddress(monsterNumber1);
        Monster monster2 = (Monster) currentPlayer.getBoard().getMonsterByAddress(monsterNumber2);
        Monster monster3 = (Monster) currentPlayer.getBoard().getMonsterByAddress(monsterNumber3);
        if (monster1 == null || monster2 == null || monster3 == null) return false;
        currentPlayer.getBoard().getGraveyard().addCard(monster1);
        currentPlayer.getBoard().getGraveyard().addCard(monster2);
        currentPlayer.getBoard().getGraveyard().addCard(monster3);
        return true;
    }

    private boolean getTribute(int numberOfTribute) {
        ArrayList<Monster> tributeMonster = new ArrayList<>();
        for (int i = 0; i < numberOfTribute; i++) {
            gameView.getTribute();
            Monster monster = (Monster) currentPlayer.getSelectedCard();
            if (currentPlayer.getSelectedCard() instanceof Monster
                    && !tributeMonster.contains(monster))
                tributeMonster.add(monster);
        }
        if (tributeMonster.size() < numberOfTribute) {
            if (numberOfTribute == 1)
                gameView.printNoMonsterOnThisAddress();
            else
                gameView.printNoMonsterInOneOfThisAddress();
            return false;
        }
        for (Monster monster : tributeMonster) {
            currentPlayer.getBoard().getGraveyard().addCard(monster);
        }
        return true;
    }

    public void set() {
        if (currentPlayer.getSelectedCard() == null)
            gameView.printNoCardSelected();
        else {
            if (currentPlayer.getSelectedCard().getZone() == Zone.IN_HAND) {
                if (currentPlayer.getSelectedCard() instanceof Monster)
                    setMonster((Monster) currentPlayer.getSelectedCard());
                else
                    setSpellAndTrap();
            } else
                gameView.printCantSet();
        }
    }

    private void setSpellAndTrap() {
        if (currentPhase == Phase.MAIN_PHASE_1 || currentPhase == Phase.MAIN_PHASE_2) {
            if (currentPlayer.getBoard().getNumberOFSpellAndTrapInBoard() < 5) {
                currentPlayer.getBoard().putSpellAndTrapInBoard(currentPlayer.getSelectedCard());
                Card card = currentPlayer.getSelectedCard();
                card.setZone(Zone.SPELL_TRAP_ZONE);
                card.setFace(Face.DOWN);
                if (currentPlayer.getSelectedCard() instanceof Spell)
                    ((Spell) currentPlayer.getSelectedCard()).setSetINThisTurn(true);
                else
                    ((Trap) currentPlayer.getSelectedCard()).setSetTurn(turnsPlayed);
                currentPlayer.getCardsInHand().remove(currentPlayer.getSelectedCard());
                currentPlayer.setSelectedCard(null);
                gameView.printSetSuccessfully();
                gameView.printMap();
            } else
                gameView.printSpellZoneIsFull();
        } else
            gameView.printNotInMainPhase();
    }

    private void setMonster(Monster selectedCard) {
        if (currentPhase == Phase.MAIN_PHASE_1 || currentPhase == Phase.MAIN_PHASE_2) {
            if (currentPlayer.getBoard().isThereEmptyPlaceMonsterZone()) {
                if (currentPlayer.isSetOrSummonInThisTurn())
                    gameView.printAlreadySetOrSummon();
                else {
                    selectedCard.setZone(Zone.MONSTER_ZONE);
                    selectedCard.setFace(Face.DOWN);
                    selectedCard.setAttackOrDefense(AttackOrDefense.DEFENSE);
                    currentPlayer.setSetOrSummonInThisTurn(true);
                    currentPlayer.setSelectedCard(null);
                    gameView.printSetSuccessfully();
                    currentPlayer.getCardsInHand().remove(selectedCard);
                    currentPlayer.getBoard().putMonsterInBoard(selectedCard);
                    gameView.printMap();
                }
            } else
                gameView.printMonsterZoneFull();
        } else
            gameView.printNotInMainPhase();
    }

    public void changeSet(String position) {
        if (currentPlayer.getSelectedCard() == null)
            gameView.printNoCardSelected();
        else {
            if (currentPlayer.getSelectedCard() instanceof Monster) {
                if (currentPhase == Phase.MAIN_PHASE_1 || currentPhase == Phase.MAIN_PHASE_2) {
                    Monster monster = (Monster) currentPlayer.getSelectedCard();
                    if (monster.getFace() == Face.UP && monster.getAttackOrDefense() == AttackOrDefense.ATTACK
                            && position.equals("attack"))
                        gameView.printAlreadyInWantedPosition();
                    else if ((monster.getFace() == Face.UP && monster.getAttackOrDefense() == AttackOrDefense.DEFENSE
                            && position.equals("defense")))
                        gameView.printAlreadyInWantedPosition();
                    else {
                        if (monster.getHaveChangePositionInThisTurn())
                            gameView.printAlreadyChangePositionInThisTurn();
                        else {
                            monster.setHaveChangePositionInThisTurn(true);
                            if (position.equals("attack")) monster.setAttackOrDefense(AttackOrDefense.ATTACK);
                            else monster.setAttackOrDefense(AttackOrDefense.DEFENSE);
                            gameView.printChangeSetSuccessfully();
                            currentPlayer.setSelectedCard(null);
                            gameView.printMap();
                        }
                    }

                } else
                    gameView.printNotInMainPhase();
            } else
                gameView.printCantChangePosition();
        }
    }

    public void flipSummon() {
        if (currentPlayer.getSelectedCard() == null)
            gameView.printNoCardSelected();
        else {
            if (currentPlayer.getSelectedCard().getZone() == Zone.MONSTER_ZONE) {
                if (getCurrentPhase() == Phase.MAIN_PHASE_1 || getCurrentPhase() == Phase.MAIN_PHASE_2) {
                    Monster monster = (Monster) currentPlayer.getSelectedCard();
                    if (monster.isSetInThisTurn() ||
                            !(monster.getFace() == Face.DOWN && monster.getAttackOrDefense() == AttackOrDefense.DEFENSE))
                        gameView.printCantFlipSummon();
                    else {
                        if (monster.getCardName().equalsIgnoreCase("man-eater bug")
                                || monster.getCardName().equalsIgnoreCase("gate guardian"))
                            manEaterBugFlipSummon();
                        currentPlayer.setSelectedCard(null);
                        currentPlayer.getCardsInHand().remove(monster);
                        monster.setFace(Face.UP);
                        monster.setAttackOrDefense(AttackOrDefense.ATTACK);
                        gameView.printFlipSummonSuccessfully();
                        gameView.printMap();
                    }
                } else
                    gameView.printNotInMainPhase();
            } else
                gameView.printCantChangePosition();
        }
    }

    private void manEaterBugFlipSummon() {
        if (currentPlayer.getRivalPlayer().getBoard().getNumberOfMonsterInBoard() > 0) {
            Monster monster = gameView.askDoYouWantKillOneOfRivalMonster();
            if (monster != null) {
                monster.setActiveAbility(true);
                currentPlayer.getRivalPlayer().getBoard().getGraveyard().addCard(monster);
                gameView.printMap();
            }
        }
    }

    public boolean summonCardWithTerratiger(int monsterNumber) {
        if (monsterNumber <= 0 || monsterNumber > 5) return false;
        if (currentPlayer.getCardsInHand().get(monsterNumber - 1) instanceof Monster) {
            Monster monster = (Monster) currentPlayer.getCardsInHand().get(monsterNumber - 1);
            if (monster.getLevel() > 4)
                return false;
            normalSummonHappened = true;
            normalSummon(monster, AttackOrDefense.DEFENSE);
            checkTrapActivation();
            return true;
        } else return false;
    }

    public Phase getCurrentPhase() {
        return currentPhase;
    }

    public static boolean checkForDeathAction(Card card) {
        Monster monster = (Monster) card;
        if (card.getCardName().equalsIgnoreCase("Yomi Ship"))
            yomiShip(monster);
        return false;
    }

    public static void yomiShip(Monster monster) {
        monster
                .getAttacker()
                .getCardOwner()
                .getBoard()
                .getGraveyard()
                .addCard
                        (monster.getAttacker());
    }

    public void checksBeforeSpecialSummon(boolean isItHappenBecauseOfCardEffect) {
        if (isItHappenBecauseOfCardEffect || currentPlayer.getSelectedCard().getCardName().equalsIgnoreCase("the tricky")) {
            Monster monster = (Monster) currentPlayer.getSelectedCard();
            if (monster.getCardName().equalsIgnoreCase("the tricky")) {
                if (currentPlayer.getBoard().isThereEmptyPlaceMonsterZone())
                    gameView.getTributeTheTricky();
                else {
                    gameView.printCantSpecialSummon();
                    return;
                }
                specialSummon();
            }
        } else
            gameView.printCantSpecialSummon();
    }

    private void checkRitualSummon(Spell spell) {
        List<Monster> ritualMonster = getCurrentPlayer().getCardsInHand().stream().filter(e -> e instanceof Monster)
                .map(e -> (Monster) e)
                .filter(e -> e.getMonsterCardType() == MonsterCardType.RITUAL).collect(Collectors.toList());
        if (ritualMonster.size() == 0)
            gameView.cantRitualSummon();
        else {
            if (checkLevelForRitualSummon(ritualMonster)) {
                getCurrentPlayer().getBoard().getGraveyard().addCard(spell);
                gameView.getRitualSummonInput();
            } else
                gameView.cantRitualSummon();
        }
    }

    private boolean checkLevelForRitualSummon(List<Monster> ritualMonster) {
        List<Integer> boardMonsterLevel = Arrays.stream(getCurrentPlayer().getBoard().getMonsters()).map(e -> (Monster) e.getCard())
                .filter(Objects::nonNull)
                .filter(e -> e.getMonsterCardType() == MonsterCardType.NORMAL)
                .map(Monster::getLevel)
                .collect(Collectors.toList());
        int total = 1 << boardMonsterLevel.size();
        for (int i = 0; i < total; i++) {
            int sum = 0;
            for (int j = 0; j < boardMonsterLevel.size(); j++)
                if ((i & (1 << j)) != 0)
                    sum += boardMonsterLevel.get(j);
            for (Monster monster : ritualMonster) {
                if (monster.getLevel() == sum)
                    return true;
            }
        }
        return false;
    }

    private void specialSummon() {
        specialSummonHappened = true;
        checkTrapActivation();
        Monster monster = (Monster) currentPlayer.getSelectedCard();
        String position = gameView.getPositionForSpecialSummon();
        normalSummon(monster, position.equalsIgnoreCase("attack") ? AttackOrDefense.ATTACK : AttackOrDefense.DEFENSE);
    }

    public boolean checkTheTrickyInput(int monsterNumber) {
        if (monsterNumber <= 0 || monsterNumber > 5) return false;
        Monster monster = (Monster) currentPlayer.getBoard().getMonsterByAddress(monsterNumber);
        if (monster == null) return false;
        currentPlayer.getBoard().getGraveyard().addCard(monster);
        currentPlayer.getCardsInHand().remove(monster);
        return true;
    }

    public void selectMonsterFromGraveyard() {
        ShowGraveyardView showGraveyardView = new ShowGraveyardView(currentPlayer);
        showGraveyardView.run("show graveyard");
        int monsterNum;
        do {
            showGraveyardView.printSelectCard();
            monsterNum = ViewMaster.scanner.nextInt();
        } while (monsterNum > showGraveyardView.getShowGraveyardController().showGraveyard());
        showGraveyardView.getShowGraveyardController().selectCardFromGraveyard(monsterNum, currentPlayer);
    }

    public void moneyCheat(int amount) {
        currentPlayer.getUser().addMoney(amount);
    }

    public void playAI() {
        ((AIPlayer) currentPlayer).play(currentPhase, this);
    }

    public boolean checkTributeLevelForRitualSummon(ArrayList<Monster> tributes, Monster ritualMonster) {
        int sum = 0;
        for (Monster tribute : tributes) {
            sum += tribute.getLevel();
        }
        return sum == ritualMonster.getLevel();
    }

    public void ritualSummon(Monster ritualMonster, ArrayList<Monster> tributes, AttackOrDefense position) {
        for (Monster tribute : tributes)
            getCurrentPlayer().getBoard().getGraveyard().addCard(tribute);
        ritualSummonHappened = true;
        ritualMonster.setActiveAbility(true);
        checkTrapActivation();
        normalSummon(ritualMonster, position);
    }

    //    public void addCardToHandCheat(String cardName){
//        Card card = currentPlayer.
//    }

    public ArrayList<Trap> currentPlayerCanActivateTrap() {
        ArrayList<Trap> trapArrayList = new ArrayList<>();
        for (Cell cell : currentPlayer.getBoard().getSpellOrTrap()) {
            addTrap(trapArrayList, cell);
        }
        return trapArrayList;
    }

    public ArrayList<Trap> rivalPlayerCanActivateTrap() {
        ArrayList<Trap> trapArrayList = new ArrayList<>();
        for (Cell cell : currentPlayer.getRivalPlayer().getBoard().getSpellOrTrap()) {
            addTrap(trapArrayList, cell);
        }
        return trapArrayList;
    }

    private void addTrap(ArrayList<Trap> trapArrayList, Cell cell) {
        if (cell.getCard() != null && cell.getCard() instanceof Trap) {
            TrapAction trapAction = ((Trap) cell.getCard()).getTrapAction();
            if (trapAction.startActionCheck.canActivate()) {
                trapArrayList.add((Trap) cell.getCard());
            } else if (trapAction instanceof CallOfTheHaunted){
                trapArrayList.add((Trap) cell.getCard());
            }
        }
    }

    public void activateRivalPlayerTrap() {
        ArrayList<Trap> trapArrayList = rivalPlayerCanActivateTrap();
        boolean activatedTrap = false;
        if (trapArrayList.size() != 0) {
            changeCurrentPlayer();
            gameView.printChangeTurn();
            activatedTrap = addTrapToChain(trapArrayList);
            changeCurrentPlayer();
            gameView.printChangeTurn();
        }
        if (activatedTrap)
            activateCurrentPlayerTrap();
    }

    public void activateCurrentPlayerTrap() {
        ArrayList<Trap> trapArrayList = currentPlayerCanActivateTrap();
        boolean activatedTrap = false;
        if (trapArrayList.size() != 0) {
            activatedTrap = addTrapToChain(trapArrayList);
        }
        if (activatedTrap)
            activateRivalPlayerTrap();
    }

    private boolean addTrapToChain(ArrayList<Trap> trapArrayList) {
        boolean activatedTrap = false;
        for (Trap trap : trapArrayList) {
            if (gameView.wantToActivateTrap(trap)) {
                trap.setActivated(true);
                trap.setFace(Face.UP);
                chain.add(trap);
                gameView.printMap();
                activatedTrap = true;
            } else if (trap.getTrapAction() instanceof CallOfTheHaunted && trap.isActivated()) {
                chain.add(trap);
            }
        }
        return activatedTrap;
    }

    private void runChain() {
        for (int i = chain.size() - 1; i >= 0; i--) {
            if (chain.get(i).getTrapAction() instanceof CallOfTheHaunted && chain.get(i).isActivated()){
                CallOfTheHaunted callOfTheHaunted = (CallOfTheHaunted) chain.get(i).getTrapAction();
                if (callOfTheHaunted.getEndActionCheck2().canActivate() || callOfTheHaunted.getEndActionCheck1().canActivate()){
                    callOfTheHaunted.runEnd();
                    continue;
                }
            }
            chain.get(i).getTrapAction().run();
            chain.remove(i);
        }
    }

    private void checkTrapActivation(){
        activateRivalPlayerTrap();
        runChain();
    }
}
