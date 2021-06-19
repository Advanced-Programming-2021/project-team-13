package controll.gameController;

import model.Deck;
import model.cards.Card;
import model.cards.Trap;
import model.players.Player;
import model.players.User;
import view.Menu;
import view.ViewMaster;
import view.allmenu.DuelView;
import view.allmenu.GameView;

public class DuelController {
    private final DuelView duelView;

    public DuelController(DuelView duelView) {
        this.duelView = duelView;
    }

    public void validateTwoPlayerDuelGame(int rounds, String playerUsername) {
        User user = ViewMaster.getUser();
        User rivalUser = User.getUserByUsername(playerUsername);
        if (rivalUser != null) {
            if (checkUserHasActiveDeck(user) && checkUserHasActiveDeck(rivalUser))
                if (checkUserValidDeckForGame(user) && checkUserValidDeckForGame(rivalUser))
                    if (checkRoundNumber(rounds))
                        startTwoPlayerDuel(user, rivalUser, rounds);
                    else duelView.printInvalidNumberOfRound();
        } else duelView.printUserNotFound();
    }

    public void validateAIDuelGame(int rounds) {
        User user = ViewMaster.getUser();
        if (checkUserHasActiveDeck(user))
            if (checkUserValidDeckForGame(user))
                if (checkRoundNumber(rounds))
                    startAIDuel(rounds);
                else
                    duelView.printInvalidNumberOfRound();
    }

    private void startAIDuel(int rounds) {
        Player player = new Player(ViewMaster.getUser());
        Deck aIDeck = new Deck("AIDeck");
//        ViewMaster.getViewMaster().setGameView(new GameView(player, aiPlayer, player, rounds));
//        ViewMaster.setCurrentMenu(Menu.GAME_MENU);
//        secondPlayer = new AIPlayer("AI" , );
//        to complete
    }

    private void startTwoPlayerDuel(User user, User rivalUser, int rounds) {
        Player firstPlayer = new Player(user);
        Player secondPlayer = new Player(rivalUser);
        firstPlayer.setRivalPlayer(secondPlayer);
        secondPlayer.setRivalPlayer(firstPlayer);
        GameView gameView;
        if (findPlayerToStart(user, rivalUser) == user) {
            gameView = new GameView(firstPlayer, secondPlayer, firstPlayer, rounds);
            GameController gameController = gameView.getGameController();
            TrapAction.setGameController(gameController);
            CardCommand.setGameController(gameController);
            EffectHandler.setGameController(gameController);
        } else {
            gameView = new GameView(firstPlayer, secondPlayer, secondPlayer, rounds);
            GameController gameController = gameView.getGameController();
            TrapAction.setGameController(gameController);
            CardCommand.setGameController(gameController);
            EffectHandler.setGameController(gameController);
        }
        setPlayersTrapActions(user, rivalUser);
        ViewMaster.getViewMaster().setGameView(gameView);
        ViewMaster.setCurrentMenu(Menu.GAME_MENU);
    }

    private void setPlayersTrapActions(User user, User rivalUser) {
        for (int i = 0; i < 2; i++) {
            User users;
            if (i == 0) {
                users = user;
            } else {
                users = rivalUser;
            }
            for (Card card : users.getActiveDeck().getAllCards()) {
                if (card instanceof Trap) {
                    Trap trap = (Trap) card;
                    TrapAction trapAction = TrapAction.allTrapEffects.get(trap.getCardName());
                    if (trapAction instanceof MagicCylinder) {
                        trapAction = new MagicCylinder();
                    } else if (trapAction instanceof CallOfTheHaunted) {
                        trapAction = new CallOfTheHaunted();
                    } else if (trapAction instanceof TimeSeal) {
                        trapAction = new TimeSeal();
                    } else if (trapAction instanceof MagicJammer) {
                        trapAction = new MagicJammer();
                    } else if (trapAction instanceof MindCrush) {
                        trapAction = new MindCrush();
                    } else if (trapAction instanceof NegateAttack) {
                        trapAction = new NegateAttack();
                    } else if (trapAction instanceof TorrentialTribute) {
                        trapAction = new TorrentialTribute();
                    } else {
                        System.out.println("BOOOOOOO ANNNNNNNN");
                    }
                    trapAction.setCard(trap);
                    trap.setTrapAction(trapAction);
                }
            }
        }
    }

    private User findPlayerToStart(User user, User rivalUser) {
        User userToStart = null;
        do {
            int userNumber = duelView.inputStonePaperScissor(user);
            int rivalNumber = duelView.inputStonePaperScissor(rivalUser);
            if (userNumber == 3) {
                if (rivalNumber == 2)
                    userToStart = rivalUser;
                else if (rivalNumber == 1)
                    userToStart = user;
            } else if (userNumber == 2) {
                if (rivalNumber == 1)
                    userToStart = rivalUser;
                else if (rivalNumber == 3)
                    userToStart = user;
            } else if (userNumber == 1) {
                if (rivalNumber == 2)
                    userToStart = user;
                else if (rivalNumber == 3)
                    userToStart = rivalUser;
            }
            if (userToStart == null)
                duelView.printEqual();
        } while (userToStart == null);
        return userToStart;
    }

    private boolean checkRoundNumber(int rounds) {
        return (rounds == 1 || rounds == 3);
    }

    private boolean checkUserValidDeckForGame(User user) {
        if (!user.getActiveDeck().isValid())
            duelView.printInvalidDeck(user.getUsername());
        return (user.getActiveDeck().isValid());
    }

    private boolean checkUserHasActiveDeck(User user) {
        if (user.getActiveDeck() == null)
            duelView.printNoActiveDeck(user.getUsername());
        return (user.getActiveDeck() != null);
    }

}
