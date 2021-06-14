package controll;

import enums.*;
import model.Cell;
import model.cards.Card;
import model.cards.Monster;
import model.cards.Spell;
import model.players.AIPlayer;
import model.players.Player;
import view.ViewMaster;
import view.allmenu.GameView;
import view.allmenu.ShowGraveyardView;

import java.util.ArrayList;

public class GameController {
    private final GameView gameView;
    private final Player firstPlayer;
    private final Player secondPlayer;
    private final Player startingPlayer;
    private final ArrayList<Integer> notToDrawCardTurns;
    private Player currentPlayer;
    private Phase currentPhase;
    private final int startingRounds;
    private int turnsPlayed;
    private int unitedCalcCurrent;
    private int unitedCalcRival;
    private boolean canContinueAttack;
    private boolean isInAttack;

    ///////////////////////////////////////////////////we need activation of spells and traps to work;;;;;;DAMN!//////////////////////////++messenger++some field spells++
    public GameController(GameView gameView, Player firstPlayer, Player secondPlayer, Player startingPlayer, int startingRounds) {
        this.gameView = gameView;
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.startingPlayer = startingPlayer;
        this.currentPlayer = startingPlayer;
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

    public Player getRivalPlayer() {
        if (currentPlayer == firstPlayer)
            return secondPlayer;
        return firstPlayer;
    }

    public void setCanContinueAttack(boolean canContinueAttack){
        this.canContinueAttack = canContinueAttack;
    }

    public boolean getCanContinueAttack(){
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
        if (monsterSelectionCheck(cardAddress, getRivalPlayer())) {
            currentPlayer.setSelectedCard
                    (getRivalPlayer().getBoard().getMonsterByAddress(cardAddress));
            gameView.printCardSelected();
            showSelectedCard();////////////////////////////////////////this needsssssssssss to be reversed !!!
        }
    }

    public void selectOpponentSpellOrTrap(int cardAddress) {
        if (spellSelectionCheck(cardAddress, getRivalPlayer())) {
            currentPlayer.setSelectedCard
                    (getRivalPlayer().getBoard().getSpellOrTrapByAddress(cardAddress));
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
        if (fieldSpellCheck(getRivalPlayer())) {
            currentPlayer.setSelectedCard
                    (getRivalPlayer().getBoard().getFieldSpell().getCard());
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
            if (card.getCardOwner() == getRivalPlayer() && card.getFace() == Face.DOWN)
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
                gameWinMenu.announceWinner(getRivalPlayer());
            } else {
                gameWinMenu.announceWinner(currentPlayer);
            }
        }
    }

    private int checkEnded() {
        if (currentPlayer.getLifePoint() <= 0 && getRivalPlayer().getLifePoint() <= 0) {
            currentPlayer.setLifePoint(0);
            getRivalPlayer().setLifePoint(0);
            return 3;//draw
        } else if (currentPlayer.getLifePoint() <= 0){
            currentPlayer.setLifePoint(0);
            return 2;//rival won
        } else if (getRivalPlayer().getLifePoint() <= 0) {
            getRivalPlayer().setLifePoint(0);
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
            getRivalPlayer().setWonRounds(1);
        else
            getRivalPlayer().setWonRounds(2);
        if (currentPlayer.getLifePoint() > currentPlayer.getMaxLifePoint())
            currentPlayer.setMaxLifePoint(currentPlayer.getMaxLifePoint());
        if (getRivalPlayer().getLifePoint() > getRivalPlayer().getMaxLifePoint())
            getRivalPlayer().setMaxLifePoint(getRivalPlayer().getLifePoint());
        new GameWinMenu(this).announceWinner(getRivalPlayer());
    }

    public void attack(int monsterNumber) {
        if (checkAttack() && checkRivalMonster(monsterNumber)) {
            Monster rivalMonster = (Monster) getRivalPlayer()
                    .getBoard().getMonsterByAddress(monsterNumber);
            Monster ourMonster = (Monster) currentPlayer.getSelectedCard();
            if (!rivalMonster.isAttackable()) {
                gameView.printCantAttackMonster();
                return;
            }
            isInAttack = true;
            rivalMonster.setAttacker(ourMonster);
            rivalMonster.setHasBeenAttacked(true);//// lioghhhhhhhhhhhhhhhhhhh
            activateSpecial(ourMonster, rivalMonster);//////////////////////sorting of which comes first is a problem we have to check!
            if (isSpecialAttack(ourMonster, rivalMonster))
                return;
            String rivalMonsterName = rivalMonster.getCardName();
            ourMonster.setAttackedInThisTurn(true);
            if (rivalMonster.getAttackOrDefense() == AttackOrDefense.ATTACK) {
                int attackDifference = ourMonster.getAttackPointInGame() - rivalMonster.getAttackPointInGame();
                if (attackDifference > 0) {
                    getRivalPlayer().decreaseHealth(attackDifference);
                    getRivalPlayer().getBoard().getGraveyard().addCard(rivalMonster);
                    gameView.printOpponentMonsterDestroyed(attackDifference);
                } else if (attackDifference == 0) {
                    currentPlayer.getBoard().getGraveyard().addCard(ourMonster);
                    getRivalPlayer().getBoard().getGraveyard().addCard(rivalMonster);
                    gameView.printBothMonstersDestroyed();
                } else {
                    currentPlayer.decreaseHealth(-attackDifference);
                    currentPlayer.getBoard().getGraveyard().addCard(ourMonster);
                    gameView.printYourCardIsDestroyed(-attackDifference);
                }
            } else {
                int attackDifference = ourMonster.getAttackPointInGame() - rivalMonster.getDefencePointInGame();
                if (attackDifference > 0) {
                    getRivalPlayer().getBoard().getGraveyard().addCard(rivalMonster);
                    currentPlayer.getBoard().removeMonsterFromBoard(rivalMonster);
                    if (rivalMonster.getFace() == Face.UP)
                        gameView.printDefensePositionDestroyed();
                    else {
                        gameView.printDefensePositionDestroyedHidden(rivalMonsterName);  // if card was hidden theres another thing to show
                    }
                } else if (attackDifference == 0) {
                    if (rivalMonster.getFace() == Face.UP)
                        gameView.printNoCardDestroyed();
                    else {                                                          // does card turn after being attacked????????????
                        gameView.printNoCardDestroyedHidden(rivalMonsterName);
                    }
                } else {
                    currentPlayer.decreaseHealth(-attackDifference);
                    if (rivalMonster.getFace() == Face.UP)
                        gameView.printNoCardDestroyedYouReceivedDamage(-attackDifference);
                    else {
                        gameView.printNoCardDestroyedYouReceivedDamageHidden(-attackDifference, rivalMonsterName);
                    }
                }
                rivalMonster.setFace(Face.UP);
            }
            gameView.printMap();
            if (ourMonster.getAttacker().getCardName().equals("Suijin"))/////////////////////// fishyyyyyyyyyyyyy
                ourMonster.setAttackPointInGame(rivalMonster.getAttackNum());
            equipSpellRid();
        }
        isInAttack = false;
        deselectCard();
    }

    public void activeEffect() {
        if (currentPlayer.getSelectedCard() == null) {
            gameView.printNoCardSelected();
            return;
        }
        if (!(currentPlayer.getSelectedCard() instanceof Spell)) {
            gameView.printActiveOnlyForSpells();
            return;
        }
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
                    spell.setEquippedMonster((Monster) getRivalPlayer().getBoard().getMonsterByAddress(num));
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
        getRivalPlayer().getBoard().getGraveyard()
                .addCard(getRivalPlayer().getBoard()
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
        for (Cell monster : getRivalPlayer().getBoard().getMonsters()) {
            getRivalPlayer().getBoard().getGraveyard().addCard(monster.getCard());
        }
    }

    private void harpie() {
        for (Cell cell : getRivalPlayer().getBoard().getSpellOrTrap()) {
            getRivalPlayer().getBoard().getGraveyard().addCard(cell.getCard());
        }
        getRivalPlayer().getBoard().getGraveyard()
                .addCard(getRivalPlayer().getBoard().getFieldSpell().getCard());
    }

    private void changeOfHeart() {
//        gameView.printSelectRivalMonster();
//        Monster rivalMonster = getRivalPlayer().getBoard().getMonsterByAddress(gameView.getNum());
//        currentPlayer.getExtraCards.add(rivalMonster);

    }

    private void raigeki() {
        for (Cell monster : getRivalPlayer().getBoard().getMonsters()) {
            getRivalPlayer().getBoard().getGraveyard().addCard(monster.getCard());
        }
    }

    private void monsterReborn() { //چقد توابعش سمیه!!!
        gameView.selectGraveyard();
        Player graveyardOwner = null;
        String graveyardName = gameView.getAnswer();
        if (graveyardName.equalsIgnoreCase("our graveyard")) {
            graveyardOwner = currentPlayer;
        } else if (graveyardName.equalsIgnoreCase("rival graveyard")) {
            graveyardOwner = getRivalPlayer();
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
        increaseAndDecrease(getRivalPlayer(), "Magnum Shield", 1, 1);
    }

    private void United() {///////////////////////////////////////fishyyyyyyyyyyyyyyy///////////////////////
        unitedCalcCurrent = unitedCalculate(currentPlayer);
        unitedCalcRival = unitedCalculate(getRivalPlayer());
        increaseAndDecrease(currentPlayer, "United We Stand", unitedCalcCurrent, unitedCalcCurrent);
        increaseAndDecrease(getRivalPlayer(), "United We Stand", unitedCalcRival, unitedCalcRival);
    }


    private void BlackPendant() {
        increaseAndDecrease(currentPlayer, "Black Pendant", 500, 0);
        increaseAndDecrease(getRivalPlayer(), "Black Pendant", 500, 0);
    }

    private void SwordOfDes() {
        increaseAndDecrease(currentPlayer, "Sword of Dark Destruction", 400, -200);
        increaseAndDecrease(getRivalPlayer(), "Sword of Dark Destruction", 400, -200);
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
        increaseAndDecrease(getRivalPlayer(), "Sword of Dark Destruction", -400, 200);
    }

    private void BlackPendantRid() {
        increaseAndDecrease(currentPlayer, "Black Pendant", -500, 0);
        increaseAndDecrease(getRivalPlayer(), "Black Pendant", -500, 0);
    }

    private void UnitedRid() {///////////////////////////////////////fishyyyyyyyyyyyyyyy///////////////////////
        increaseAndDecrease(currentPlayer, "United We Stand", -unitedCalcCurrent, -unitedCalcCurrent);
        increaseAndDecrease(getRivalPlayer(), "United We Stand", -unitedCalcRival, -unitedCalcRival);
    }

    private void magnumShieldRid() {
        increaseAndDecrease(currentPlayer, "Magnum Shield", -1, -1);
        increaseAndDecrease(getRivalPlayer(), "Magnum Shield", -1, -1);
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
        checkCommandKnight(rivalMonster, getRivalPlayer());
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
                && !checkForActive(getRivalPlayer(), "Messenger of peace"))
            return false;
        return attacker.getAttackPointInGame() >= 1500;
    }

    private boolean checkForActive(Player player, String spellName) {
        for (Cell cell : player.getBoard().getSpellOrTrap()) {
            Spell spell = (Spell) cell.getCard();
            if (spell != null && spell.getCardName().equals(spellName) && spell.isActivated())
                return true;
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
        if (checkAttack()
                || isAI) {
            Monster ourMonster = (Monster) currentPlayer.getSelectedCard();
            getRivalPlayer().decreaseHealth(ourMonster.getAttackNum());
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
        if (cardAddress <= 0 || cardAddress > 6) {
            gameView.printInvalidSelection();
            return false;
        }
        if (currentPlayer.getCardsInHand().get(cardAddress - 1) == null) {
            gameView.printNotFoundCard();
            return false;
        }
        return true;
    }

    private boolean checkAttack() {
        if (currentPlayer.getSelectedCard() == null) {
            gameView.printNoCardSelected();
            return false;
        }
        if (!currentPlayer.getBoard().isMonsterOnBoard(currentPlayer.getSelectedCard())) {
            gameView.printCantAttack();
            return false;
        }
        if (currentPhase != Phase.BATTLE_PHASE) {
            gameView.printWrongPhase();
            return false;
        }
        if (hasCardAttacked(currentPlayer.getSelectedCard())) {
            gameView.printAlreadyAttacked();
            return false;
        }
        if (getRivalPlayer().getBoard().getNumberOfMonsterInBoard() != 0) {
            gameView.printCantAttackDirectly();
            return false;
        }
        return true;
    }

    private boolean checkRivalMonster(int rivalMonsterNum) {
        if (getRivalPlayer().getBoard()
                .getMonsterByAddress(rivalMonsterNum) == null) {
            gameView.printNoCardToAttack();
            return false;
        }
        return true;
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
                if (currentPlayer.getBoard().getDeck().getAllCardsInMainDeck().size() != 0)
                    currentPlayer.addCardToHand();
                else {
                    new GameWinMenu(this).announceWinner(getRivalPlayer());
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
        for (Cell monster : currentPlayer.getBoard().getMonsters()) {
            if (monster.getCard() != null) {
                if (!monster.getCard().getCardName().equalsIgnoreCase("Suijin"))
                    ((Monster) (monster.getCard())).setActiveAbility(false);
                ((Monster) (monster.getCard())).setAttackable(true);
                ((Monster) (monster.getCard())).setAttacker(null);
                ((Monster) (monster.getCard())).setHasBeenAttacked(false);
                ((Monster) (monster.getCard())).setSetInThisTurn(false);
                ((Monster) (monster.getCard())).setAttackedInThisTurn(false);
                ((Monster) (monster.getCard())).setHaveChangePositionInThisTurn(false);
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

    public void normalSummon(Monster monster) {
        gameView.printSummonSuccessfully();
        currentPlayer.getCardsInHand().remove(monster);
        monster.setSetInThisTurn(true);
        monster.setZone(Zone.MONSTER_ZONE);
        monster.setFace(Face.UP);
        monster.setAttackOrDefense(AttackOrDefense.ATTACK);
        currentPlayer.setSetOrSummonInThisTurn(true);
        currentPlayer.setSelectedCard(null);
        currentPlayer.getBoard().putMonsterInBoard(monster);
        gameView.printMap();
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
        normalSummon(terratiger);
    }

    private void summonAndSpecifyTribute() {
        Monster monster = (Monster) currentPlayer.getSelectedCard();
        if (monster.getCardName().equalsIgnoreCase("mirage dragon")) { // it should be TRUE in attack function when it dies;///hooman: name is right???!?!(upperCase first word!)
            getRivalPlayer().setCanActiveTrap(false);
            monster.setActiveAbility(true);
        }
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
        normalSummon(monster);
    }

    private void beatsKingBarbaros(Monster monster) {//// hooman:cell is broken- 1400/2/27\\10:33
        if (currentPlayer.getBoard().getNumberOfMonsterInBoard() >= 3) {
            if (gameView.doYouWantTributeBarBaros()) {
                gameView.getTributeForBarbaros();
            } else {
                monster.setAttackPointInGame(1900);
                for (Cell cell : getRivalPlayer().getBoard().getMonsters()) {
                    getRivalPlayer().getBoard().getGraveyard().addCard(cell.getCard());
                    cell.setCard(null);
                }
            }
        }
        monster.setActiveAbility(true);
        normalSummon(monster);
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
                currentPlayer.getCardsInHand().remove(currentPlayer.getSelectedCard());
                currentPlayer.setSelectedCard(null);
                currentPlayer.setSetOrSummonInThisTurn(true);
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
        if (getRivalPlayer().getBoard().getNumberOfMonsterInBoard() > 0) {
            Monster monster = gameView.askDoYouWantKillOneOfRivalMonster();
            if (monster != null) {
                monster.setActiveAbility(true);
                getRivalPlayer().getBoard().getGraveyard().addCard(monster);
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
            gameView.printSummonSuccessfully();
            monster.setSetInThisTurn(true);
            currentPlayer.getCardsInHand().remove(monster);
            monster.setZone(Zone.MONSTER_ZONE);
            monster.setFace(Face.UP);
            monster.setAttackOrDefense(AttackOrDefense.DEFENSE);
            currentPlayer.getBoard().putMonsterInBoard(monster);
            gameView.printMap();
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
        boolean isThereRitualMonster = false;
        for (Card card : currentPlayer.getCardsInHand()) {
            if (card instanceof Monster)
                if (((Monster) card).getMonsterCardType() == MonsterCardType.RITUAL) {
                    isThereRitualMonster = true;
                    break;
                }
        }
        if (isThereRitualMonster) {
        } else
            gameView.cantRitualSummon();
    }

    private void specialSummon() {
        Monster monster = (Monster) currentPlayer.getSelectedCard();
        String position = gameView.getPositionForSpecialSummon();
        if (position.equalsIgnoreCase("attack"))
            monster.setAttackOrDefense(AttackOrDefense.ATTACK);
        else
            monster.setAttackOrDefense(AttackOrDefense.DEFENSE);
        monster.setSetInThisTurn(true);
        monster.setZone(Zone.MONSTER_ZONE);
        monster.setFace(Face.UP);
        currentPlayer.setSelectedCard(null);
        currentPlayer.getBoard().putMonsterInBoard(monster);
        gameView.printMap();
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

//    public void addCardToHandCheat(String cardName){
//        Card card = currentPlayer.
//    }
}
