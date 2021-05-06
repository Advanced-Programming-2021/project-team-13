package controll;

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

    public void runAIGame(int rounds) {
        User user = ViewMaster.getUser();
        if (checkRoundNumber(rounds))
            playsWithAI(user, rounds);
        else
            duelView.printInvalidNumberOfRound();
    }

    private void playsWithAI(User user, int rounds) {
        Player firstPlayer = new Player(user , rounds);
        ViewMaster.setCurrentMenu(Menu.GAME_MENU);
        //secondPlayer = new AIPlayer("AI" , );
        //to complete
    }

    public void validateDuelGame(int rounds, String playerUsername) {
        User user = ViewMaster.getUser();
        User rivalUser = User.getUserByUsername(playerUsername);
        if (rivalUser != null) {
            if (checkBothHaveActiveDeck(user, rivalUser))
                if (checkValidDecks(user, rivalUser))
                    if (checkRoundNumber(rounds))
                        startDuel(user, rivalUser, rounds);
                    else duelView.printInvalidNumberOfRound();
        } else duelView.printUserNotFound();
    }

    private void startDuel(User user, User rivalUser, int rounds) {
        Player firstPlayer = new Player(user , rounds);
        Player secondPlayer = new Player(rivalUser , rounds);
        if (findUserToStart(user , rivalUser) == user)
            ViewMaster.getViewMaster().setGameView(new GameView(firstPlayer,secondPlayer,firstPlayer,rounds));
        else
            ViewMaster.getViewMaster().setGameView(new GameView(firstPlayer,secondPlayer,secondPlayer,rounds));
    }

    private User findUserToStart(User user , User rivalUser){
        User userToStart = null;
        do {
            int userNumber = duelView.inputStonePaperScissor(user);
            int rivalNumber = duelView.inputStonePaperScissor(rivalUser);
            if (userNumber == 3){
                if (rivalNumber == 2)
                    userToStart = rivalUser;
                else if (rivalNumber == 1)
                    userToStart = user;
            } else if (userNumber == 2){
                if (rivalNumber == 1)
                    userToStart = rivalUser;
                else if (rivalNumber == 3)
                    userToStart = user;
            } else if (userNumber == 1){
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

    private boolean checkValidDecks(User user, User rivalUser) {
        if (!user.getActiveDeck().isValid())
            duelView.printInvalidDeck(user.getUsername());
        if (!rivalUser.getActiveDeck().isValid())
            duelView.printInvalidDeck(rivalUser.getUsername());
        return ((user.getActiveDeck().isValid())
                && rivalUser.getActiveDeck().isValid());
    }

    private boolean checkBothHaveActiveDeck(User user, User rivalUser) {
        if (user.getActiveDeck() == null)
            duelView.printNoActiveDeck(user.getUsername());
        if (rivalUser.getActiveDeck() == null)
            duelView.printNoActiveDeck(rivalUser.getUsername());
        return ((user.getActiveDeck() != null)
                && rivalUser.getActiveDeck() != null);
    }

}
