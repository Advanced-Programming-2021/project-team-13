package controll;

import model.Deck;
import model.players.AIPlayer;
import model.players.Player;
import model.players.User;
import view.ViewMaster;
import view.allmenu.DuelView;

public class DuelController {
    DuelView duelView;

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
        Player p1 = new Player(user, user.getActiveDeck());
        AIPlayer AI = new AIPlayer();
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
        Player p1 = new Player(user, user.getActiveDeck());
        Player p2 = new Player(rivalUser, rivalUser.getActiveDeck());

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

}
