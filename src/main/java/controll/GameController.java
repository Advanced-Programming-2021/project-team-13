package controll;

import enums.Phase;
import model.players.Player;
import view.allmenu.GameView;

public class GameController {
    private GameView gameView;
    private Player p1;
    private Player p2;
    private Phase currentPhase;
    private String command;
    private TrapEffect trapEffect;

    public GameController(GameView gameView) {
        this.gameView = gameView;
        this.trapEffect = new TrapEffect(this);
    }

    public GameController() {
    }

    public GameView getGameView() {
        return gameView;
    }

    public Player getOurPlayer() {
        return p1;
    }

    public Player getRivalPlayer() {
        return p2;
    }

    public void run(String command) {

    }

    public Phase getCurrentPhase() {
        return currentPhase;
    }

    public void setCurrentPhase(Phase currentPhase) {
        this.currentPhase = currentPhase;
    }
}
