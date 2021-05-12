package view.allmenu;

import controll.GameController;
import enums.AttackOrDefense;
import enums.Face;
import model.cards.Card;
import model.cards.Monster;
import model.players.Player;
import view.Menu;
import view.Regex;
import view.ViewMaster;

import java.util.regex.Matcher;

public class GameView {
    private final GameController gameController;

    public GameView(Player firstPlayer, Player secondPlayer, Player currentPlayer, int rounds) {
        gameController = new GameController(this, firstPlayer, secondPlayer, currentPlayer, rounds);
    }

    public GameController getGameController() {
        return gameController;
    }

    public void run(String command) {
        if (command.matches(Regex.PLAYER_SELECT) || command.matches(Regex.OPPONENT_SELECT) || command.matches(Regex.FIELD_SELECT))
            selectCard(command);
        else if (command.matches(Regex.DESELECT))
            gameController.deselectCard();
        else if (command.equals("next phase"))
            gameController.nextPhase();
        else if (command.equals("summon"))
            gameController.checksBeforeSummon();
        else if (command.equals("set"))
            gameController.set();
        else if (command.matches(Regex.CHANGE_SET))
            changeSet(Regex.getInputMatcher(command, Regex.CHANGE_SET));
        else if (command.equals("flip-summon"))
            gameController.flipSummon();
        else if (command.matches(Regex.ATTACK))
            attack(Regex.getInputMatcher(command, Regex.ATTACK));
        else if (command.equals("attack direct"))
            directAttack();
        else if (command.equals("show graveyard"))
            showGraveyard(command);
        else if (command.equals("card show --selected"))
            gameController.showSelectedCard();
        else if (command.matches("surrender"))
            gameController.surrender();
    }


    private void showGraveyard(String command) {
        ShowGraveyardMenu showGraveyardMenu = new ShowGraveyardMenu(gameController.getCurrentPlayer());
        ViewMaster.getViewMaster().setShowGraveyardMenu(showGraveyardMenu);
        ViewMaster.setCurrentMenu(Menu.SHOW_GRAVEYARD);
        showGraveyardMenu.run(command);
    }

    private void changeSet(Matcher inputMatcher) {
        inputMatcher.find();
        String position = inputMatcher.group("position");
        gameController.changeSet(position);
    }

    private void surrender() {
    }

//    public void changeTurn() {
//
//    }

    private void directAttack() {
        gameController.directAttack();
    }

    private void attack(Matcher inputMatcher) {
        if (inputMatcher.find()) {
            int monsterNumber = Integer.parseInt(inputMatcher.group(1));
            gameController.attack(monsterNumber);
        }
    }

    public void printMap() {
        Player currentPlayer = gameController.getCurrentPlayer();
        Player rivalPlayer = gameController.getRivalPlayer();
        StringBuilder map = new StringBuilder();
        addRivalMap(rivalPlayer, map);
        addPlayerMap(currentPlayer, map);
        System.out.println(map.toString());
    }

    private void addPlayerMap(Player currentPlayer, StringBuilder map) {
        map.append("\n--------------------------\n");
        if (currentPlayer.getBoard().getFieldSpell() == null)
            map.append("E" + "\t\t\t\t\t\t").append(currentPlayer.getBoard().getGraveyard().getAllCards().size()).append("\n");
        else
            map.append("O" + "\t\t\t\t\t\t").append(currentPlayer.getBoard().getGraveyard().getAllCards().size()).append("\n");
        for (int i = 0; i < 5; i++) {
            addMonsterToMap(currentPlayer, map, i);
        }
        map.append("\n");
        for (int i = 0; i < 5; i++) {
            addSpellToMap(currentPlayer, map, i);
        }
        map.append("\n");
        map.append("\t\t\t\t\t\t").append(currentPlayer.getBoard().getDeck().getAllCardsInMainDeck().size()).append("\n");
        for (int i = 0; i < currentPlayer.getCardsInHand().size(); i++) {
            map.append("\tC");
        }
        map.append(currentPlayer.getUser().getNickname()).append(":").append(currentPlayer.getLifePoint());
    }

    private void addRivalMap(Player rivalPlayer, StringBuilder map) {
        map.append(rivalPlayer.getUser().getNickname()).append(":").append(rivalPlayer.getLifePoint()).append("\n");
        for (int i = 0; i < rivalPlayer.getCardsInHand().size(); i++) {
            map.append("\tC");
        }
        map.append(rivalPlayer.getBoard().getDeck().getAllCardsInMainDeck().size()).append("\n");
        for (int i = 4; i >= 0; i--) {
            addSpellToMap(rivalPlayer, map, i);
        }
        map.append("\n");
        for (int i = 4; i >= 0; i--) {
            addMonsterToMap(rivalPlayer, map, i);
        }
        map.append("\n");
        map.append(rivalPlayer.getBoard().getGraveyard().getAllCards().size()).append("\t\t\t\t\t\t");
        if (rivalPlayer.getBoard().getFieldSpell() == null) map.append("E\n");
        else map.append("O\n");
    }

    private void addMonsterToMap(Player player, StringBuilder map, int i) {
        map.append("\t");
        Monster monster = (Monster) player.getBoard().getMonsters()[i].getCard();
        if (monster == null)
            map.append("E");
        else if (monster.getAttackOrDefense() == AttackOrDefense.ATTACK)
            map.append("OO");
        else if (monster.getAttackOrDefense() == AttackOrDefense.DEFENSE) {
            if (monster.getFace() == Face.UP)
                map.append("DO");
            else
                map.append("DH");
        }
    }

    private void addSpellToMap(Player player, StringBuilder map, int i) {
        map.append("\t");
        if (player.getBoard().getSpellOrTrap()[i].getCard() == null)
            map.append("E");
        else if (player.getBoard().getSpellOrTrap()[i].getCard().getFace() == Face.UP)
            map.append("O");
        else if (player.getBoard().getSpellOrTrap()[i].getCard().getFace() == Face.DOWN)
            map.append("H");
    }

    public void selectCard(String command) {
        Matcher opponentWithFieldMatcher = Regex.getInputMatcher(command, Regex.OPPONENT_WITH_FIELD);
        Matcher opponentMatcher = Regex.getInputMatcher(command, Regex.OPPONENT);
        if (opponentMatcher.find() || opponentWithFieldMatcher.find()) {
            Matcher monsterMatcher = Regex.getInputMatcher(command, Regex.OPPONENT_MONSTER);
            Matcher spellMatcher = Regex.getInputMatcher(command, Regex.OPPONENT_SPELL);
            Matcher fieldMatcher = Regex.getInputMatcher(command, Regex.FIELD);
            if (monsterMatcher.find()) {
                int cardAddress = Integer.parseInt(monsterMatcher.group("cardAddress"));
                gameController.selectOpponentMonster(cardAddress);
            } else if (spellMatcher.find()) {
                int cardAddress = Integer.parseInt(spellMatcher.group("cardAddress"));
                gameController.selectOpponentSpellOrTrap(cardAddress);
            } else if (fieldMatcher.find()) {
                gameController.selectOpponentFieldCard();
            } else printInvalidCommand();
        } else {
            Matcher monsterMatcher = Regex.getInputMatcher(command, Regex.PLAYER_MONSTER);
            Matcher spellMatcher = Regex.getInputMatcher(command, Regex.PLAYER_SPELL);
            Matcher handMatcher = Regex.getInputMatcher(command, Regex.PLAYER_HAND);
            Matcher fieldMatcher = Regex.getInputMatcher(command, Regex.FIELD);
            if (monsterMatcher.find()) {
                int cardAddress = Integer.parseInt(monsterMatcher.group("cardAddress"));
                gameController.selectPlayerMonster(cardAddress);
            } else if (spellMatcher.find()) {
                int cardAddress = Integer.parseInt(spellMatcher.group("cardAddress"));
                gameController.selectPlayerSpellOrTrap(cardAddress);
            } else if (handMatcher.find()) {
                int cardAddress = Integer.parseInt(handMatcher.group("cardAddress"));
                gameController.selectPlayerHandCard(cardAddress);
            } else if (fieldMatcher.find()) {
                if (command.matches(Regex.FIELD_SELECT))
                    gameController.selectPlayerFieldCard();
                else printInvalidCommand();
            } else printInvalidCommand();
        }
    }

    public void printCardDeselected() {
        System.out.println("card deselected");
    }

    public void printNoCardSelected() {
        System.out.println("no card is selected yet");
    }

    public void printInvalidCommand() {
        System.out.println("invalid command");
    }

    public void printCardSelected() {
        System.out.println("card selected");
    }

    public void printNotFoundCard() {
        System.out.println("no card found in the given position");
    }

    public void printAddedNewCard(Card card) {
        System.out.println("new card added to the hand : " + card.getCardName());
    }

    public void printInvalidSelection() {
        System.out.println("invalid selection");
    }

    public void printCantAttack() {
        System.out.println("you can’t attack with this card");
    }

    public void printWrongPhase() {
        System.out.println("you can’t do this action in this phase");
    }

    public void printAlreadyAttacked() {
        System.out.println("this card already attacked");
    }

    public void printNoCardToAttack() {
        System.out.println("there is no card to attack here");
    }

    public void printOpponentMonsterDestroyed(int attackDifference) {
        System.out.println("your opponent’s monster is destroyed " +
                "and your opponent receives " + attackDifference + " battle damage");
    }

    public void printBothMonstersDestroyed() {
        System.out.println("both you and your opponent monster cards" +
                " are destroyed and no one receives damage");
    }

    public void printYourCardIsDestroyed(int attackDifference) {
        System.out.println("Your monster card is destroyed " +
                "and you received " + attackDifference + " battle damage");
    }

    public void printNoCardDestroyed() {
        System.out.println("no card is destroyed");
    }

    public void printDefensePositionDestroyed() {
        System.out.println("the defense position monster is destroyed");
    }

    public void printNoCardDestroyedYouReceivedDamage(int attackDifference) {
        System.out.println("no card is destroyed and you" +
                " received " + attackDifference + " battle damage");
    }

    public void printDefensePositionDestroyedHidden(String rivalMonsterName) {
        printOpponentCardsName(rivalMonsterName);
        printDefensePositionDestroyed();
    }

    public void printNoCardDestroyedHidden(String rivalMonsterName) {
        printOpponentCardsName(rivalMonsterName);
        printNoCardDestroyed();
    }

    public void printNoCardDestroyedYouReceivedDamageHidden(int attackDifference, String rivalMonsterName) {
        printOpponentCardsName(rivalMonsterName);
        printNoCardDestroyedYouReceivedDamage(attackDifference);
    }

    public void printOpponentCardsName(String name) {
        System.out.println("opponent’s monster card was " + name);
    }

    public void printYourOpponentReceivesDamage(int attackNum) {
        System.out.println("your opponent receives " + attackNum + " battle damage");
    }

    public void printCantSummon() {
        System.out.println("you can’t summon this card");
    }

    public void printNotInMainPhase() {
        System.out.println("action not allowed in this phase");
    }

    public void printMonsterZoneFull() {
        System.out.println("monster card zone is full");
    }

    public void printAlreadySetOrSummon() {
        System.out.println("you already summoned/set on this turn");
    }

    public void printSummonSuccessfully() {
        System.out.println("summoned successfully");
    }

    public void printNoMonsterOnThisAddress() {
        System.out.println("there no monsters one this address");
    }

    public void printThereArentEnoughMonsterForTribute() {
        System.out.println("there are not enough cards for tribute");
    }

    public void printNoMonsterInOneOfThisAddress() {
        System.out.println("there no monsters on one of this addresses");
    }

    public void getTribute() {
        int number = ViewMaster.scanner.nextInt();
        run("select --monster " + number);
    }

    public void printCantSet() {
        System.out.println("you can’t set this card");
    }

    public void printSetSuccessfully() {
        System.out.println("set successfully");
    }

    public void printCantChangePosition() {
        System.out.println("you can’t change this card position");
    }

    public void printAlreadyInWantedPosition() {
        System.out.println("this card is already in the wanted position");
    }

    public void printAlreadyChangePositionInThisTurn() {
        System.out.println("you already changed this card position in this turn");
    }

    public void printChangeSetSuccessfully() {
        System.out.println("doesHaveChangePositionInThisTurn");
    }

    public void printCantFlipSummon() {
        System.out.println("you can’t flip summon this card");
    }

    public void printCurrentPhase() {
        System.out.println(gameController.getCurrentPhase().getPhaseName());
    }

    public void printUserWonWholeGame(String username, int winnerWonRounds, int loserWonRounds) {
        System.out.println(username + " won the whole game with score: " + winnerWonRounds + "-" + loserWonRounds);
    }

    public void printUserWonSingleGame(String username, int winnerWonRounds, int loserWonRounds) {
        System.out.println(username + " won the game and the score is:" + winnerWonRounds + "-" + loserWonRounds);
    }

    public void printWhoseTurn() {
        System.out.println("Its " + gameController.getCurrentPlayer().getUser().getNickname() + "’s turn");
    }

    public void showCard(Card card) {
        System.out.println(card.toString());
    }

    public void printCardInvisible() {
        System.out.println("card is not visible");
    }

    public void printAttackDisruptedByTaxchanger() {
        System.out.println("Attack Denied By Taxchanger");
    }

    public void printNoCyberseWithAbility() {
        System.out.println("Cant summon Cyberse with ability");
    }

    public void printInvalidCyberseName() {
        System.out.println("invalid Cyberse Name");
    }

    public void printFlipSummonSuccessfully() {
        System.out.println("flip summoned successfully");
    }


    public boolean doesRivalWantCyberse() {
        String answer = ViewMaster.scanner.nextLine();
        if (answer.equals("Yes"))
            return true;
        else if (answer.equals("No"))
            return false;
        System.out.println("invalid answer");
        return false;
    }

    public String getCyberse() {
        return ViewMaster.scanner.nextLine();
    }


    public String getPlace() {
        return ViewMaster.scanner.nextLine();
    }


    public void printInvalidLocation() {
        System.out.println("invalid location");
    }

    public void printDoesntContainCard(String place, String monster) {
        System.out.println(place + " doesnt contain " + monster);
    }

    public void printCantAttackMonster() {
        System.out.println("Cant attack this monster!");
    }

    public void printNoCardDestroyedRivalReceivedDamage(int attackDifference) {
        System.out.println("no card is destroyed and rival" +
                " received " + attackDifference + " battle damage");
    }

    public void printYouReceivedDamage(int amount) {
        System.out.println("you received " + amount + " damage");
    }

    public boolean doYouWantTributeBarBaros() {// :| WTF
        String answer = " ";
        System.out.println("Do You Want Tribute 3 Monsters?(YES|NO)");
        answer = ViewMaster.scanner.nextLine();
        while (!answer.equalsIgnoreCase("yes") && !answer.equalsIgnoreCase("no")) {
            System.out.println("please enter (YES|NO) : ");
            answer = ViewMaster.scanner.nextLine();
        }
        return answer.equalsIgnoreCase("YES");
    }

    public Monster askDoYouWantKillOneOfRivalMonster() {
        System.out.println("Do You Want Kill Rival Monster?(YES|NO)");
        String answer = ViewMaster.scanner.nextLine();
        if (answer.equalsIgnoreCase("yes")) {
            System.out.println("Enter Monster Number: ");
            String number = " ";
            while (!number.equalsIgnoreCase("cancel")) {
                number = ViewMaster.scanner.nextLine();
                if (number.matches("^\\d+$")) {
                    int num = Integer.parseInt(number);
                    if (num > 0 && num < 6) {
                        if (gameController.getRivalPlayer().getBoard().getMonsterByAddress(num) != null)
                            return (Monster) gameController.getRivalPlayer().getBoard().getMonsterByAddress(num);
                        else
                            System.out.println("There is no monster in this address");
                    } else
                        System.out.println("Enter Valid Number");
                } else
                    System.out.println("Enter Monster Number, if you want cancel this Enter \"Cancel\"");
            }
        }
        return null;
    }

    public void getTributeForBarbaros() {
        System.out.println("Enter Monster Numbers In One Line");
        while (true) {
            System.out.println("Enter Monster Numbers");
            String numbers = ViewMaster.scanner.nextLine();
            Matcher matcher = Regex.getInputMatcher(numbers, "(\\d)\\S+(\\d)\\S+(\\d)");
            if (matcher.find()) {
                int m1 = Integer.parseInt(matcher.group(1));
                int m2 = Integer.parseInt(matcher.group(2));
                int m3 = Integer.parseInt(matcher.group(3));
                if (gameController.checkBarbarosInput(m1, m2, m3))
                    return;
                else
                    System.out.println("There Is No Monster In One Of This Address");
            } else System.out.println("Enter Valid Number");
        }
    }

    public void askWantSummonedAnotherMonsterTerratiger() {
        System.out.println("Do You Want Summoned Monster With level less then 5?(YES|NO)");
        String answer = " ";
        while (!answer.equalsIgnoreCase("yes") && !answer.equalsIgnoreCase("no")) {
            answer = ViewMaster.scanner.nextLine();
            if (answer.equalsIgnoreCase("no"))
                return;
            else if (answer.equalsIgnoreCase("yes")) {
                System.out.println("Enter Monster Number");
                while (true) {
                    String number = ViewMaster.scanner.nextLine();
                    if (number.matches("\\d")) {
                        if (gameController.summonCardWithTerratiger(Integer.parseInt(number)))
                            return;
                        else
                            System.out.println("You Can't Summon This Card, If You Want Cancel Enter \"Cancel\"");
                    } else if (number.equalsIgnoreCase("cancel")) return;
                    else
                        System.out.println("Enter Valid Number, If You Want Cancel Enter \"Cancel\"");
                }
            } else
                System.out.println("Enter YES or NO");
        }

    }

    public void printSpellZoneIsFull() {
        System.out.println("spell card zone is full");
    }
}
