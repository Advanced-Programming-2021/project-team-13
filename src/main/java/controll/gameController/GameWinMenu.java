package controll.gameController;

import model.players.AIPlayer;
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

    public GameWinMenu(GameController gameController) {
        this.gameView = gameController.getGameView();
        this.startingRounds = gameController.getStartingRounds();
        this.firstPlayer = gameController.getFirstPlayer();
        this.secondPlayer = gameController.getSecondPlayer();
        this.startingPlayer = gameController.getStartingPlayer();
    }


    public void announceWinner(Player winner) {
        if (winner == null) {
            drawState();
        } else {
            winOrLoseState(winner);
        }
    }

    private void drawState() {
        if (startingRounds == 1) {

        } else {

        }
    }

    private void winOrLoseState(Player winner) {
        Player loser;
        if (winner == firstPlayer) loser = secondPlayer;
        else loser = firstPlayer;
        winner.addWonRounds(1);
        if (winner instanceof AIPlayer || loser instanceof AIPlayer) {
            withAiWin(winner, loser);
        } else {
            noAiWin(winner, loser);
        }
    }

    private void withAiWin(Player winner, Player loser) {
        User user;
        if (winner instanceof AIPlayer && loser instanceof AIPlayer) {
            ViewMaster.setCurrentMenu(Menu.MAIN_MENU);
            return;
        }
        if (winner instanceof AIPlayer) {
            user = loser.getUser();
            if (startingRounds == 1) {
                user.addLosts(1);
                user.addMoney(100);
                gameView.printUserWonSingleGame(((AIPlayer) loser).getNickname(),
                        winner.getWonRounds(), loser.getWonRounds());
            } else {
                if (winner.getWonRounds() == 2) {
                    user.addLosts(1);
                    user.addMoney(300);
                    gameView.printUserWonSingleGame(((AIPlayer) winner).getNickname(),
                            winner.getWonRounds(), loser.getWonRounds());
                    ViewMaster.setCurrentMenu(Menu.MAIN_MENU);
                } else {
                    loser.renewPlayer();
                    ((AIPlayer) winner).renewPlayer(loser.getUser().getActiveDeck());
                    runNewGame();
                }
            }
        } else {
            user = winner.getUser();
            if (startingRounds == 1) {
                user.addWins(1);
                user.addMoney(1000 + winner.getMaxLifePoint());
                gameView.printUserWonWholeGame(user.getUsername(), winner.getWonRounds(), loser.getWonRounds());
            } else {
                if (winner.getWonRounds() == 2) {
                    user.addWins(1);
                    user.addMoney(3000 + 3 * winner.getMaxLifePoint());
                    gameView.printUserWonWholeGame(user.getUsername(), winner.getWonRounds(), loser.getWonRounds());
                    ViewMaster.setCurrentMenu(Menu.MAIN_MENU);
                } else {
                    winner.renewPlayer();
                    ((AIPlayer) loser).renewPlayer(winner.getUser().getActiveDeck());
                    runNewGame();
                }
            }
        }
    }

    private void noAiWin(Player winner, Player loser) {
        User winnerUser = winner.getUser();
        User loserUser = loser.getUser();
        gameView.printUserWonSingleGame(winnerUser.getUsername(), winner.getWonRounds(), loser.getWonRounds());
        if (startingRounds == 1) {
            winnerUser.addWins(1);
            loserUser.addLosts(1);
            winnerUser.addScore(1000);
            winnerUser.addMoney(1000 + winner.getMaxLifePoint());
            loserUser.addMoney(100);
            gameView.printUserWonWholeGame(winnerUser.getUsername(), winner.getWonRounds(), loser.getWonRounds());
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
                runNewGame();
            }
        }
    }

    private void runNewGame() {
        GameView gameView;
        if (startingPlayer == firstPlayer) {
            gameView = new GameView(firstPlayer, secondPlayer, secondPlayer, startingRounds);
        } else {
            gameView = new GameView(firstPlayer, secondPlayer, firstPlayer, startingRounds);
        }
        ViewMaster.getViewMaster().setGameView(gameView);
        GameController gameController = gameView.getGameController();
        TrapAction.setGameController(gameController);
        CardCommand.setGameController(gameController);
        EffectHandler.setGameController(gameController);
    }
}
