package view.allmenu;

import controll.gameController.GameController;
import enums.AttackOrDefense;
import enums.Face;
import enums.MonsterCardType;
import model.cards.Card;
import model.cards.Monster;
import model.cards.Trap;
import model.players.AIPlayer;
import model.players.Player;
import view.Menu;
import view.Regex;
import view.ViewMaster;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
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
        else if (command.matches("surrender")) {
            gameController.surrender();
            return;
        } else if (command.equals("active effect"))
            gameController.activeEffect();
        else if (command.matches("increase --money \\d+"))
            increaseMoney(command);
        else if (command.matches("increase --LP \\d+"))
            increaseLp(command);
        else if (command.matches("duel set-winner \\w+"))
            setWinner(command);
        else if (command.equals("select --hand --force"))
            handCheat();
        else if (command.equals("special summon"))
            gameController.checksBeforeSpecialSummon(false);
        else if (command.equals("phase"))
            printCurrentPhase();
        else if (command.equals("map"))
            printMap();
        else System.out.println("invalid command");
        gameController.findWinner();
    }

    private void handCheat() {
        gameController.addCardToHand();
    }

    private void setWinner(String command) {
        String duelWinner = Regex.getInputMatcher(command, "duel set-winner (\\w+)").group(1);
        gameController.setDuelWinnerCheat(duelWinner);
    }

    private void increaseLp(String command) {
        String amountStr = Regex.getInputMatcher(command, "increase --LP (\\d+)").group(1);
        int amount = Integer.parseInt(amountStr);
        gameController.lifePointCheat(amount);
    }

    private void increaseMoney(String command) {
        String amountString = Regex.getInputMatcher(command, "increase --money (\\d+)").group(1);
        int amount = Integer.parseInt(amountString);
        gameController.moneyCheat(amount);
    }

    public void showGraveyard(String command) {
        ShowGraveyardView showGraveyardView = new ShowGraveyardView(gameController.getCurrentPlayer());
        ViewMaster.getViewMaster().setShowGraveyardMenu(showGraveyardView);
        ViewMaster.setCurrentMenu(Menu.SHOW_GRAVEYARD);
        showGraveyardView.run(command);
    }

    private void changeSet(Matcher inputMatcher) {
        inputMatcher.find();
        String position = inputMatcher.group("position");
        gameController.changeSet(position);
    }

    private void directAttack() {
        gameController.directAttack(false);
    }

    private void attack(Matcher inputMatcher) {
        if (inputMatcher.find()) {
            int monsterNumber = Integer.parseInt(inputMatcher.group(1));
            gameController.attack(monsterNumber);
        }
    }

    private void printNotThoseMoves() {
        System.out.println("it’s not your turn to play this kind of moves");
    }

    public void printMap() {
        Player currentPlayer = gameController.getCurrentPlayer();
        Player rivalPlayer = gameController.getCurrentPlayer().getRivalPlayer();
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
            map.append("C\t");
        }
        map.append("\n");
        if (currentPlayer instanceof AIPlayer)
            map.append(((AIPlayer) currentPlayer).getNickname()).append(":").append(currentPlayer.getLifePoint());
        else
            map.append(currentPlayer.getUser().getNickname()).append(":").append(currentPlayer.getLifePoint());
    }

    private void addRivalMap(Player rivalPlayer, StringBuilder map) {
        if (rivalPlayer instanceof AIPlayer)
            map.append(((AIPlayer) rivalPlayer).getNickname()).append(":").append(rivalPlayer.getLifePoint()).append("\n");
        else
            map.append(rivalPlayer.getUser().getNickname()).append(":").append(rivalPlayer.getLifePoint()).append("\n");
        for (int i = 0; i < rivalPlayer.getCardsInHand().size(); i++) {
            map.append("\tC");
        }
        map.append("\n");
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
        if (command.matches(Regex.DESELECT)) {
            gameController.deselectCard();
            return;
        }
        Matcher opponentWithFieldMatcher = Regex.getInputMatcher(command, Regex.OPPONENT_WITH_FIELD);
        Matcher opponentMatcher = Regex.getInputMatcher(command, Regex.OPPONENT);
        if (opponentWithFieldMatcher.find(0)) {
            Matcher monsterMatcher = Regex.getInputMatcher(command, Regex.OPPONENT_MONSTER);
            Matcher spellMatcher = Regex.getInputMatcher(command, Regex.OPPONENT_SPELL);
            if (monsterMatcher.find()) {
                int cardAddress = Integer.parseInt(opponentWithFieldMatcher.group("cardAddress"));
                gameController.selectOpponentMonster(cardAddress);
            } else if (spellMatcher.find()) {
                int cardAddress = Integer.parseInt(opponentWithFieldMatcher.group("cardAddress"));
                gameController.selectOpponentSpellOrTrap(cardAddress);
            } else printInvalidCommand();
        } else if (opponentMatcher.find(0)) {
            Matcher fieldMatcher = Regex.getInputMatcher(command, Regex.FIELD);
            if (fieldMatcher.find()) {
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
        System.out.println("enter tribute number: ");
        try {
            int number = ViewMaster.scanner.nextInt();
            run("select --monster " + number);
        } catch (Exception e) {
        }
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
        System.out.println("monster card position changed successfully");
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
        if (gameController.getCurrentPlayer() instanceof AIPlayer)
            System.out.println("Its " + ((AIPlayer) gameController.getCurrentPlayer()).getNickname() + "’s turn");
        else
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

    public void printCantAttackBecauseOfMessenger() {
        System.out.println("Cant attack : Messenger of peace active");
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

    public String getAnswer() {
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
                        if (gameController.getCurrentPlayer().getRivalPlayer().getBoard().getMonsterByAddress(num) != null)
                            return (Monster) gameController.getCurrentPlayer().getRivalPlayer().getBoard().getMonsterByAddress(num);
                        else
                            System.out.println("There is no monster in this address");
                    } else
                        System.out.println("Enter Valid Number");
                } else
                    System.out.println("Enter Monster Number, if you want to cancel this Enter \"Cancel\"");
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
            } else System.out.println("Enter Valid Format");
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

    public void printActiveOnlyForSpellsAndTrap() {
        System.out.println("activate effect is only for spell cards.");
    }

    public void printCantActiveThisTurn() {
        System.out.println("you can’t activate an effect on this turn");
    }

    public void printAlreadyActivated() {
        System.out.println("you have already activated this card");
    }

    public void printSpellZoneIsFull() {
        System.out.println("spell zone is full");
    }

    public void printPrepsNotDone() {
        System.out.println("preparations of this spell are not done yet");
    }

    public void printCantSpecialSummon() {
        System.out.println("there is no way you could special summon a monster");
    }

    public void getTributeTheTricky() {
        int counter = 0;
        for (int i = 0; i < gameController.getCurrentPlayer().getCardsInHand().size(); i++) {
            Card card = gameController.getCurrentPlayer().getCardsInHand().get(i);
            if (!card.getCardName().equalsIgnoreCase("the tricky"))
                System.out.println(++counter + ". " + card.getCardName());
        }
        while (true) {
            System.out.println("Enter Monster Number: ");
            String number = ViewMaster.scanner.nextLine().trim();
            if (number.matches("^\\d$")) {
                if (gameController.checkTheTrickyInput(Integer.parseInt(number)))
                    return;
                else
                    System.out.println("invalid selection , try again!");
            } else if (number.equalsIgnoreCase("cancel")) {
                return;
            } else
                System.out.println("you should special summon right now");
        }
    }

    public String getPositionForSpecialSummon() {
        while (true) {
            System.out.println("Attack Or Defence ?");
            String position = ViewMaster.scanner.nextLine();
            if (position.equalsIgnoreCase("attack")) return "attack";
            else if (position.equalsIgnoreCase("defence")) return "defence";
            else
                System.out.println("you should special summon right now");
        }
    }

    public void cantRitualSummon() {
        System.out.println("there is no way you could ritual summon a monster");
    }

    public void printSpellActivated() {
        System.out.println("spell activated");
    }

    public void printSelectSpellOrTrap() {
        System.out.println("select a spell or trap(1-5) :");
    }

    public int getNum() {
        return ViewMaster.scanner.nextInt();
    }

    public void printSelectMonsterFromBoard() {
        System.out.println("select a monster(1-5) from board(our board-rival board)");
    }

    public void selectGraveyard() {
        System.out.println("select a Graveyard(our graveyard-rival graveyard");
    }

    public void printSelectNum() {
        System.out.println("select a number");
    }

    public void printselectCardFromHand(int size) {
        System.out.println("select a card from hand(" + size + ") to send to graveyard");
    }

    public void printCardInHand(Card card, int i) {
        System.out.println(card.getCardName() + " : " + i);
    }

    public void printCantAttackDirectly() {
        System.out.println("you can’t attack the opponent directly");
    }

    public void printSelectGraveyardHandOrDeck() {
        System.out.println("select (Graveyard-Hand-Deck)");
    }

    public void playerChanged(Player currentPlayer) {
        if (currentPlayer instanceof AIPlayer)
            System.out.println("Turn changed.\ncurrent player: " + ((AIPlayer) currentPlayer).getNickname());//// added this to show changed turn
        else
            System.out.println("Turn changed.\ncurrent player: " + currentPlayer.getUser().getUsername());
    }

    public void printRitualSummonError() {
        System.out.println("there is no way you could ritual summon a monster");
    }

    public void getRitualSummonInput() {
        String command = "";
        while (true) {
            System.out.println("Enter Ritual Monster Number: ");
            command = ViewMaster.scanner.nextLine();
            try {
                int number = Integer.parseInt(command);
                run("select --hand " + number);
                Monster ritualMonster = (Monster) gameController.getCurrentPlayer().getSelectedCard();
                if (ritualMonster.getMonsterCardType() == MonsterCardType.RITUAL)
                    while (true) {
                        System.out.println("Enter Ritual Monster Tributes Number: \n" +
                                "For Example: 1 2 3 \n" +
                                "For Change Selected Card Enter <Back>");
                        command = ViewMaster.scanner.nextLine();
                        if (command.equalsIgnoreCase("back")) break;
                        ArrayList<Monster> tributes = new ArrayList<>();
                        String[] numbers = command.split("\\s+");
                        if (checkIsInputNumber(numbers)) {
                            for (String num : numbers) {
                                run("select --monster " + num);
                                tributes.add((Monster) gameController.getCurrentPlayer().getSelectedCard());
                            }
                            if (gameController.checkTributeLevelForRitualSummon(tributes, ritualMonster)) {
                                while (!command.equalsIgnoreCase("attack") &&
                                        !command.equalsIgnoreCase("Defence")) {
                                    System.out.println("Defence OR Attack?");
                                    command = ViewMaster.scanner.nextLine();
                                }
                                gameController.ritualSummon(ritualMonster, tributes,
                                        command.equalsIgnoreCase("attack") ? AttackOrDefense.ATTACK : AttackOrDefense.DEFENSE);
                                return;
                            } else
                                System.out.println("selected monsters levels don’t match with ritual monster\n");
                        } else
                            System.out.println("you should ritual summon right now");
                    }
                else
                    System.out.println("you should ritual summon right now");
            } catch (Exception e) {
                System.out.println("you should ritual summon right now");
            }
        }
    }

    private boolean checkIsInputNumber(String[] numbers) {
        for (String number : numbers) {
            if (!number.matches("^\\d+$"))
                return false;
        }
        return true;
    }

    public void printCardAddedToHand(Card card) {
        System.out.println("new card added to hand : " + card.getCardName());
    }

    public int chooseMonsterForSummonScanner(List<Monster> rivalGraveYardMonsters) {
        while (true) {
            AtomicInteger i = new AtomicInteger();
            System.out.println("Choose One Of The Monsters To Scanner Be Like It In This Turn :");
            rivalGraveYardMonsters.forEach(e -> System.out.println((i.incrementAndGet()) + ". " + e.getCardName() + " : "
                    + e.getCardDescription()));
            String command = ViewMaster.scanner.nextLine();
            try {
                int number = Integer.parseInt(command);
                if (number > rivalGraveYardMonsters.size()
                        || number <= 0)
                    System.out.println("Wrong Number");
                else
                    return number - 1;
            } catch (Exception e) {
                System.out.println("Invalid Input");
            }
        }
    }

    public boolean wantToUseHeraldAbility() {
        while (true) {
            System.out.println("Do You Want To Use <Herald Of Creation> Ability? (YES|NO)");
            String command = ViewMaster.scanner.nextLine();
            if (command.equalsIgnoreCase("YES"))
                return true;
            else if (command.equalsIgnoreCase("NO"))
                return false;
            else
                System.out.println("Invalid Input");
        }
    }

    public int chooseMonsterForHeraldOfCreation(List<Monster> rivalGraveYardMonster) {
        while (true) {
            AtomicInteger i = new AtomicInteger();
            System.out.println("Choose One Of The Monsters To Scanner Be Like It In This Turn :");
            rivalGraveYardMonster.forEach(e -> System.out.println((i.incrementAndGet()) + ". " + e.getCardName() + " : "
                    + e.getCardDescription()));
            String command = ViewMaster.scanner.nextLine();
            try {
                int number = Integer.parseInt(command);
                if (number > rivalGraveYardMonster.size()
                        || number <= 0)
                    System.out.println("Wrong Number");
                else
                    return number - 1;
            } catch (Exception e) {
                System.out.println("Invalid Input");
            }
        }
    }

    public void printChangeTurn() {
        if (gameController.getCurrentPlayer() instanceof AIPlayer)
            System.out.println("now it will be " + ((AIPlayer) gameController.getCurrentPlayer()).getNickname() + "’s turn");
        else
            System.out.println("now it will be " + gameController.getCurrentPlayer().getUser().getNickname() + "’s turn");
        printMap();
    }

    public boolean wantToActivateTrap(Trap trap) {
        System.out.println("do you want to activate your trap " + trap.getCardName() + " ?(YES/NO)");
        while (true) {
            String yesOrNo = ViewMaster.scanner.nextLine().trim();
            if (yesOrNo.equalsIgnoreCase("yes")) {
                return true;
            } else if (yesOrNo.equalsIgnoreCase("no")) {
                return false;
            } else {
                printInvalidCommand();
            }
        }
    }

    public void printCantAttackFacedDown() {
        System.out.println("cant attack because card is faced down");
    }

    public void printCantAttackItsOnDefense() {
        System.out.println("cant attack because card is on defense");
    }
}
