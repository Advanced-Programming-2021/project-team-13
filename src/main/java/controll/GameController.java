package controll;

import enums.*;
import model.Cell;
import model.cards.Card;
import model.cards.Monster;
import model.cards.Spell;
import model.players.Player;
import view.allmenu.GameView;

import java.util.ArrayList;

public class GameController {
    private final GameView gameView;
    private final Player firstPlayer;
    private final Player secondPlayer;
    private final Player startingPlayer;
    private Player currentPlayer;
    private Phase currentPhase;
    private final int startingRounds;
    private int turnsPlayed;
    private int unitedCalcCurrent;
    private int unitedCalcRival;

    ///////////////////////////////////////////////////we need activation of spells and traps to work;;;;;;DAMN!//////////////////////////++messenger++some field spells++
    public GameController(GameView gameView, Player firstPlayer, Player secondPlayer, Player startingPlayer, int startingRounds) {
        this.gameView = gameView;
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.startingPlayer = startingPlayer;
        this.currentPlayer = startingPlayer;
        firstPlayer.getBoard().getDeck().shuffleMainDeck();
        secondPlayer.getBoard().getDeck().shuffleMainDeck();
        for (int i = 0; i < 6; i++) {
            firstPlayer.addCardToHand();
            secondPlayer.addCardToHand();
        }
        this.startingRounds = startingRounds;
        turnsPlayed = 0;
        currentPhase = Phase.DRAW_PHASE;
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

    public void changeCurrentPlayer() {
        if (currentPlayer == firstPlayer)
            currentPlayer = secondPlayer;
        else
            currentPlayer = firstPlayer;
    }

    public void selectPlayerMonster(int cardAddress) {// no regex error!!! // are these handled or not!!??
        if (monsterSelectionCheck(cardAddress, currentPlayer)) {
            currentPlayer.setSelectedCard(currentPlayer.getBoard().getMonsterByAddress(cardAddress));
            gameView.printCardSelected();
        }
    }

    public void selectPlayerSpellOrTrap(int cardAddress) {
        if (spellSelectionCheck(cardAddress, currentPlayer)) {
            currentPlayer.setSelectedCard(currentPlayer.getBoard().getSpellOrTrapByAddress(cardAddress));
            gameView.printCardSelected();
        }
    }

    public void selectOpponentMonster(int cardAddress) {
        if (monsterSelectionCheck(cardAddress, getRivalPlayer())) {
            currentPlayer.setSelectedCard
                    (getRivalPlayer().getBoard().getMonsterByAddress(cardAddress));
            gameView.printCardSelected();
        }
    }

    public void selectOpponentSpellOrTrap(int cardAddress) {
        if (spellSelectionCheck(cardAddress, getRivalPlayer())) {
            currentPlayer.setSelectedCard
                    (getRivalPlayer().getBoard().getSpellOrTrapByAddress(cardAddress));
            gameView.printCardSelected();
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
            if (card.getPlayer() == getRivalPlayer() && card.getFace() == Face.DOWN)
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
        if (currentPlayer.getLifePoint() == 0 && getRivalPlayer().getLifePoint() == 0)
            return 3;//draw
        else if (currentPlayer.getLifePoint() != 0)
            return 2;//rival won
        else if (getRivalPlayer().getLifePoint() != 0)
            return 1;//currentPlayer has won
        else {
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
        GameWinMenu gameWinMenu = new GameWinMenu(this);
        gameWinMenu.announceWinner(getRivalPlayer());
    }

    public void attack(int monsterNumber) {
        if (checkAttack() && checkRivalMonster(monsterNumber)) {
            Monster rivalMonster = (Monster) getRivalPlayer()
                    .getBoard().getMonsterByAddress(monsterNumber);
            Monster ourMonster = (Monster) currentPlayer.getSelectedCard();
            rivalMonster.setAttacker(ourMonster);
            activateSpecial(ourMonster, rivalMonster);//////////////////////sorting of which comes first is a problem we have to check!
            if (isSpecialAttack(ourMonster, rivalMonster))
                return;
            if (!rivalMonster.isAttackable()) {
                gameView.printCantAttackMonster();
                return;
            }
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
            equipSpellRid();
        }
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
        if ((currentPhase != Phase.MAIN_PHASE_1) || (currentPhase != Phase.MAIN_PHASE_2)) { // wtf is this things problem?????????!
            gameView.printCantActiveThisTurn();
            return;
        }
        Spell spell = (Spell) currentPlayer.getSelectedCard();
        if (spellActive(spell)) {//////////////////fishy///////////////////////////
            gameView.printAlreadyActivated();
            return;
        }
        if (spell.getSpellEffect() != SpellEffect.FEILD) {
            
        } else {

        }
    }

    private boolean spellActive(Spell spell) {
        for (Cell cell : currentPlayer.getBoard().getSpellOrTrap()) {
            if (cell.getCard() == spell) return true;
        }
        return false;
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
            if (cell.getCard().getCardName().equals(spellName)) {
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
            if (monster.getCard().getFace() == Face.UP)
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
        yami(ourMonster);
        yami(rivalMonster);
        forest(ourMonster);
        forest(rivalMonster);
        closedForest(ourMonster);
        closedForest(ourMonster);
        UMIIRUKA(ourMonster);
        UMIIRUKA(rivalMonster);
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
        checkCommandKnight(ourMonster);
        checkCommandKnight(rivalMonster);
        boolean attackable = true;
        for (Cell monster : rivalMonster.getCardOwner().getBoard().getMonsters()) {
            if (!monster.getCard().getCardName().equals("Command knight")) {
                attackable = false;
                break;
            }
        }
        if (attackable) return;
        for (Cell monster : rivalMonster.getCardOwner().getBoard().getMonsters()) {
            if (monster.getCard().getCardName().equals("Command knight")) {
                Monster commandKnight = (Monster) monster.getCard();
                if (commandKnight.getFace() == Face.UP)
                    commandKnight.setAttackable(false);
            }
        }

    }

    private void checkCommandKnight(Monster activationMonster) {
        activationMonster.decreaseAttackPoint(activationMonster.getCommandKnightsActive().size() * 400);
        activationMonster.getCommandKnightsActive().clear();
        for (Cell monster : activationMonster.getCardOwner().getBoard().getMonsters()) {
            if (monster.getCard().getCardName().equals("Command knight") &&
                    monster.getCard().getFace() == Face.UP && monster.getCard() != activationMonster) {
                activationMonster.setCommandKnightsActive((Monster) monster.getCard());
            }
        }
        activationMonster.increaseAttackPoint(activationMonster.getCommandKnightsActive().size() * 400);
    }

    private boolean isSpecialAttack(Monster ourMonster, Monster rivalMonster) {
        if (MessengerOfPeace(ourMonster)) {
            gameView.printCantAttackBecauseOfMessenger();
            return true;
        } else if (rivalMonster.getCardName().equals("Marshmallon")) {
            marshmallon(ourMonster, rivalMonster);
            return true;
        } else if (rivalMonster.getCardName().equals("Texchanger")) {
            if (!rivalMonster.isAttackedInThisTurn())
                return false;
            texchanger(rivalMonster);
            return true;
        } else if (rivalMonster.getCardName().equals("Exploder Dragon")) {
            exploderDragon(ourMonster, rivalMonster);
            return true;
        } else if (ourMonster.getCardName().equals("The Calculator")) {/// think we need to make it first(it can be denied by messenger)
            theCalculator(ourMonster); // we just need some calculations !!!!!!! thats all
            return false;
        }
        return false;
    }

    private boolean MessengerOfPeace(Monster attacker) {////////////////////////where to activate??????/////////////////
        if (!checkForActive(currentPlayer, "Messenger of peace")
                && !checkForActive(getRivalPlayer(), "Messenger of peace"))
            return false;
        return attacker.getAttackPointInGame() >= 1500;
    }

    private boolean checkForActive(Player player, String spellName) {
        for (Cell cell : player.getBoard().getSpellOrTrap()) {
            Spell spell = (Spell) cell.getCard();
            if (spell.getCardName().equals(spellName) && spell.isActive())
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

    private void texchanger(Monster rivalMonster) {///// needs summon nigga !!!
        if (!rivalMonster.isAttackedInThisTurn() || !rivalMonster.hasUsedAbilityThisTurn())
            return;
        rivalMonster.setUsedAbilityThisTurn(true);
        gameView.printAttackDisruptedByTaxchanger();
        if (!gameView.doesRivalWantCyberse())
            return;
        String cyberse = gameView.getCyberse();
        if (!checkCyberse(cyberse))
            return;
        String fromWhere = gameView.getPlace();
        if (fromWhere.equals("Graveyard")) {
            for (Card allCard : rivalMonster.getCardOwner().getBoard().getGraveyard().getAllCards()) {
                if (allCard.getCardName().equals(cyberse))
                    rivalMonster.getCardOwner();
            }
            gameView.printDoesntContainCard("Graveyard", rivalMonster.getCardName());
        } else if (fromWhere.equals("Hand")) {
/*            if () {

            }*/
            gameView.printDoesntContainCard("Hand", rivalMonster.getCardName());
        } else if (fromWhere.equals("Deck")) {
            gameView.printDoesntContainCard("Deck", rivalMonster.getCardName());
        } else
            gameView.printInvalidLocation();
    }

    private boolean checkCyberse(String cyberse) {
        if (cyberse.equals("Bitron"))
            return true;
        else if (cyberse.equals("Texchanger")) {
            gameView.printNoCyberseWithAbility();
            return false;
        } else if (cyberse.equals("Leotron"))
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

    public void directAttack() {  // somehow same as attack only diff is rival card number!!!
        if (checkAttack()) {
            Monster ourMonster = (Monster) currentPlayer.getSelectedCard();
            getRivalPlayer().decreaseHealth(ourMonster.getAttackNum());
            gameView.printYourOpponentReceivesDamage(ourMonster.getAttackNum());
        }
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
            if (turnsPlayed == 0 || turnsPlayed == 1) {
                if (currentPlayer.getBoard().getDeck().getAllCardsInMainDeck().size() != 0)
                    currentPlayer.addCardToHand();
                else {

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
            gameView.printCurrentPhase();//to complete
        } else if (currentPhase == Phase.BATTLE_PHASE) {
            currentPhase = Phase.MAIN_PHASE_2;
            gameView.printCurrentPhase();
            //to comp
        } else {
            currentPhase = Phase.END_PHASE;
            currentPlayer.setSetOrSummonInThisTurn(false);
            changeCurrentPlayer();
            gameView.printCurrentPhase();
            gameView.printWhoseTurn();
        }
    }

    public void checksBeforeSummon() { //need some change, SOME EFFECTIVE MONSTER CAN NOT NORMAL SUMMON!
        if (currentPlayer.getSelectedCard() == null)
            gameView.printNoCardSelected();
        else {
            if (currentPlayer.getCardsInHand().contains(currentPlayer.getSelectedCard())
                    && currentPlayer.getSelectedCard() instanceof Monster
                    && ((Monster) currentPlayer.getSelectedCard()).getMonsterCardType() != MonsterCardType.RITUAL
                    && !(currentPlayer.getSelectedCard()).getCardName().equalsIgnoreCase("")) {
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
        monster.setSetInThisTurn(true);
        monster.setZone(Zone.MONSTER_ZONE);
        monster.setFace(Face.UP);
        monster.setAttackOrDefense(AttackOrDefense.ATTACK);
        currentPlayer.setSetOrSummonInThisTurn(true);
        currentPlayer.setSelectedCard(null);
        currentPlayer.getBoard().putMonsterInBoard(monster);
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
            if (!getTribute(numberOfTribute))
                return;
        }
        normalSummon(monster);
    }

    private void beatsKingBarbaros(Monster monster) {
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
                currentPlayer.setSelectedCard(null);
                currentPlayer.setSetOrSummonInThisTurn(true);
                gameView.printSetSuccessfully();
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
                    currentPlayer.getBoard().putMonsterInBoard(selectedCard);
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
                        if (monster.getCardName().equalsIgnoreCase("man eater bug")
                                || monster.getCardName().equalsIgnoreCase("gate guardian"))
                            manEaterBugFlipSummon();
                        currentPlayer.setSelectedCard(null);
                        monster.setFace(Face.UP);
                        monster.setAttackOrDefense(AttackOrDefense.ATTACK);
                        gameView.printFlipSummonSuccessfully();
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
            monster.setZone(Zone.MONSTER_ZONE);
            monster.setFace(Face.UP);
            monster.setAttackOrDefense(AttackOrDefense.DEFENSE);
            currentPlayer.getBoard().putMonsterInBoard(monster);
            return true;
        } else return false;
    }

    public Phase getCurrentPhase() {
        return currentPhase;
    }

    public static boolean checkForDeathAction(Card card) {
        Monster monster = (Monster) card;
        if (card.getCardName().equals("Yomi Ship"))
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

}
