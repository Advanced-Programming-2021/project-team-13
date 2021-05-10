package controll;

import model.players.Player;
import model.players.User;
import view.Menu;
import view.ViewMaster;
import view.allmenu.GameView;

public class GameWinMenu {
    private final GameView gameView;
    private final int startingRounds;
    private final Player firstPlayer;
    private final Player secondPlayer;
    private final Player startingPlayer;
    public GameWinMenu(GameController gameController){
        this.gameView = gameController.getGameView();
        this.startingRounds = gameController.getStartingRounds();
        this.firstPlayer = gameController.getFirstPlayer();
        this.secondPlayer = gameController.getSecondPlayer();
        this.startingPlayer = gameController.getStartingPlayer();
    }



    public void announceWinner(Player winner){
        if (winner == null){//this is when game is ended draw
            drawState();
        } else {
            winOrLoseState(winner);
        }
    }

    private void drawState() {
        if (startingRounds == 1){

        } else {

        }
    }

    private void winOrLoseState(Player winner) {
        Player loser ;
        if (winner == firstPlayer) loser = secondPlayer;
        else loser = firstPlayer;
        User winnerUser = winner.getUser();
        User loserUser = loser.getUser();
        gameView.printUserWonSingleGame(winnerUser.getUsername() , winner.getWonRounds() , loser.getWonRounds());
        if (startingRounds == 1){
            winnerUser.addWins(1);
            loserUser.addLosts(1);
            winnerUser.addScore(1000);
            winnerUser.addMoney(1000 + winner.getMaxLifePoint());
            loserUser.addMoney(100);
            gameView.printUserWonWholeGame(winnerUser.getUsername() , winner.getWonRounds() , loser.getWonRounds());
            ViewMaster.setCurrentMenu(Menu.MAIN_MENU);
        } else {
            if (winner.getWonRounds() == 2) {
                winnerUser.addWins(1);
                loserUser.addLosts(1);
                winnerUser.addScore(3000);
                winnerUser.addMoney(3000 + 3 * winner.getMaxLifePoint());
                loserUser.addMoney(300);
                gameView.printUserWonWholeGame(winnerUser.getUsername(), winner.getWonRounds(), loser.getWonRounds());
                ViewMaster.setCurrentMenu(Menu.MAIN_MENU);
            } else {
                firstPlayer.renewPlayer();
                secondPlayer.renewPlayer();
                //go to deck to change!!!!!!!!!!
                if (startingPlayer == firstPlayer) {
                    ViewMaster.getViewMaster().setGameView(new GameView(firstPlayer, secondPlayer,secondPlayer , startingRounds));
                } else {
                    ViewMaster.getViewMaster().setGameView(new GameView(firstPlayer , secondPlayer , firstPlayer , startingRounds));
                }
            }
        }
    }
}
