package controll;

import enums.Phase;
import model.Player;
import view.GameView;

public class GameController {
    private GameView gameView;
    private Player p1;
    private Player p2;
    private Phase currentPhase;
    private String command;

    public GameController(GameView gameView) {
        this.gameView = gameView;
    }

    public Player getOurPlayer() {
        return p1;
    }

    public Player getRivalPlayer() {
        return p2;
    }

    public void run(String command) {

    }
}
