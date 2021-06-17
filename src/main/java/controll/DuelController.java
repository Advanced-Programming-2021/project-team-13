package controll;

import model.Deck;
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
        if (findPlayerToStart(user, rivalUser) == user)
            ViewMaster.getViewMaster().setGameView(new GameView(firstPlayer, secondPlayer, firstPlayer, rounds));
        else
            ViewMaster.getViewMaster().setGameView(new GameView(firstPlayer, secondPlayer, secondPlayer, rounds));
        ViewMaster.setCurrentMenu(Menu.GAME_MENU);
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
