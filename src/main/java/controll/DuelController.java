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
    private Player firstPlayer;
    private Player secondPlayer;


    private GameView gameView;

    public DuelController(DuelView duelView) {
        this.duelView = duelView;
    }

    public void runAIGame(int rounds) {
        User user = ViewMaster.getUser();
        if (checkRoundNumber(rounds))
            playsWithAI(user, rounds);
        else
            duelView.printInvalidNumberOfRound();
    }

    private void playsWithAI(User user, int rounds) {
        firstPlayer = new Player(user.getNickname(), user.getActiveDeck(), user);
        ViewMaster.setCurrentMenu(Menu.GAME_MENU);
        //secondPlayer = new AIPlayer("AI" , );
        //to complete
    }

    public void runDuelGame(int rounds, String playerUsername) {
        User user = ViewMaster.getUser();
        User rivalUser = User.getUserByUsername(playerUsername);
        if (rivalUser != null) {
            if (checkBothHaveActiveDeck(user, rivalUser))
                if (validateDecks(user, rivalUser))
                    if (checkRoundNumber(rounds))
                        plays(user, rivalUser, rounds);
                    else
                        duelView.printInvalidNumberOfRound();
        } else
            duelView.printDoesntExistPLayer(playerUsername);

    }

    private void plays(User user, User rivalUser, int rounds) {
        firstPlayer = new Player(user.getNickname(), user.getActiveDeck(), user);
        secondPlayer = new Player(rivalUser.getNickname(), rivalUser.getActiveDeck(), rivalUser);
        firstPlayer.setRivalPlayer(secondPlayer);
        secondPlayer.setRivalPlayer(firstPlayer);
        gameView = new GameView(firstPlayer, secondPlayer, rounds);
        //startGame
    }

    private boolean checkRoundNumber(int rounds) {

        return (rounds == 1 || rounds == 3);
    }

    private boolean validateDecks(User user, User rivalUser) {
        if (!user.getActiveDeck().isValid())
            duelView.printInvalidDeck(user.getUsername());
        if (!rivalUser.getActiveDeck().isValid())
            duelView.printInvalidDeck(rivalUser.getUsername());
        return ((user.getActiveDeck().isValid())
                && rivalUser.getActiveDeck().isValid());
    }

    private boolean checkBothHaveActiveDeck(User user, User rivalUser) {
        Deck deck = user.getActiveDeck();
        if (deck == null)
            duelView.printNoActiveDeck(user.getUsername());
        deck = rivalUser.getActiveDeck();
        if (deck == null)
            duelView.printNoActiveDeck(rivalUser.getUsername());
        return ((user.getActiveDeck() != null)
                && rivalUser.getActiveDeck() != null);
    }

    public GameView getGameView() {
        return gameView;
    }

    public void setGameView(GameView gameView) {
        this.gameView = gameView;
    }

}
