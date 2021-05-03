package controll;

import model.Deck;
import model.players.AIPlayer;
import model.players.Player;
import model.players.User;
import view.ViewMaster;
import view.allmenu.DuelView;

public class DuelController {
    private final DuelView duelView;
    private Player firstPlayer ;
    private Player secondPlayer ;

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
        firstPlayer = new Player(user.getUsername() , user.getActiveDeck());
        secondPlayer = new AIPlayer("AI" , );
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
        firstPlayer = new Player(user.getUsername(), user.getActiveDeck());
        secondPlayer = new Player(rivalUser.getUsername(), rivalUser.getActiveDeck());
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

}
