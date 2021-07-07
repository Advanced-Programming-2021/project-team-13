package controll.gameController;

import model.PlayerDeck;
import model.cards.Card;
import model.cards.Trap;
import model.players.AIPlayer;
import model.players.Player;
import model.players.User;
import view.Menu;
import view.ViewMaster;
import view.allmenu.*;

import java.util.Random;

public class DuelController {
    private final DuelView duelView;
    private Random random = new Random();

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
        } else duelView.printUserNotFound();
    }

    /*    public void validateAIDuelGame(int rounds) {
            User user = ViewMaster.getUser();
            if (checkUserHasActiveDeck(user))
                if (checkUserValidDeckForGame(user))
                    if (checkRoundNumber(rounds))
                        startAIDuel(user, rounds);
        }*/

    public boolean validateAIDuelGame() {
        User user = ViewMaster.getUser();
        if (checkUserHasActiveDeck(user))
            return checkUserValidDeckForGame(user);
        return false;
    }

    private void startDuel(Player firstPlayer, Player secondPlayer, int rounds) {
        firstPlayer.setRivalPlayer(secondPlayer);
        secondPlayer.setRivalPlayer(firstPlayer);
        setPlayersTrapActions(firstPlayer);
        setPlayersTrapActions(secondPlayer);
        GameView gameView;
        gameView = new GameView(firstPlayer, secondPlayer, firstPlayer, rounds);
        GameController gameController = gameView.getGameController();
        TrapAction.setGameController(gameController);
        CardCommand.setGameController(gameController);
        EffectHandler.setGameController(gameController);
        ViewMaster.getViewMaster().setGameView(gameView);
        ViewMaster.setCurrentMenu(Menu.GAME_MENU);
    }

    public void startAIDuel(User user, int rounds, int result) {
        Player firstPlayer = new Player(user);
        AIPlayer aiPlayer = new AIPlayer(new PlayerDeck(user.getActiveDeck()));
        aiPlayer.setNickname("Abbas BooAzaar");
        if (result == 1)
            startDuel(firstPlayer, aiPlayer, rounds);
        else
            startDuel(aiPlayer, firstPlayer, rounds);
    }

    private void startTwoPlayerDuel(User user, User rivalUser, int rounds) {
        Player firstPlayer = new Player(user);
        Player secondPlayer = new Player(rivalUser);
        startDuel(firstPlayer, secondPlayer, rounds);
    }

    private void setPlayersTrapActions(Player player) {
        for (int i = 0; i < player.getBoard().getDeck().getAllCards().size(); i++) {
            Card card = player.getBoard().getDeck().getAllCards().get(i);
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
                } else if (trapAction instanceof MirrorForce) {
                    trapAction = new MirrorForce();
                } else if (trapAction instanceof TrapHole) {
                    trapAction = new TrapHole();
                }
                trap.setActivatedTurn(-1);
                trap.setTrapAction(trapAction);
                trapAction.setCard(trap);
            }
        }
    }

    /*private Player findPlayerToStart(Player player, Player rivalPlayer) {
        Player userToStart = null;
        Random random = new Random();
        do {
            int playerNumber;
            int rivalNumber;
            if (player instanceof AIPlayer) {
                int number = random.nextInt() % 3;
                playerNumber = number + 1;
            } else playerNumber = duelView.inputStonePaperScissor(player.getUser());
            if (rivalPlayer instanceof AIPlayer) {
                int number = random.nextInt(3) % 3;
                rivalNumber = number + 1;
            } else rivalNumber = duelView.inputStonePaperScissor(rivalPlayer.getUser());
            if (playerNumber == 3) {
                if (rivalNumber == 2)
                    userToStart = rivalPlayer;
                else if (rivalNumber == 1)
                    userToStart = player;
            } else if (playerNumber == 2) {
                if (rivalNumber == 1)
                    userToStart = rivalPlayer;
                else if (rivalNumber == 3)
                    userToStart = player;
            } else if (playerNumber == 1) {
                if (rivalNumber == 2)
                    userToStart = player;
                else if (rivalNumber == 3)
                    userToStart = rivalPlayer;
            }
            if (userToStart == null)
                duelView.printEqual();
        } while (userToStart == null);
        return userToStart;
    }*/
    // sang = 2 kaqaz = 1 gheichi = 3
    public int findPlayerToStart(int playerNumber) {
        int rivalNumber = random.nextInt() % 3 + 1;
        if (rivalNumber == playerNumber) return 0;
        if (rivalNumber == 1 && playerNumber == 2) return 2;
        if (rivalNumber == 1 && playerNumber == 3) return 1;
        if (rivalNumber == 2 && playerNumber == 1) return 1;
        if (rivalNumber == 2 && playerNumber == 3) return 2;
        else return 1;
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
