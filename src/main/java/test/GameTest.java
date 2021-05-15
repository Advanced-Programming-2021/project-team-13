package test;

import model.players.Player;
import model.players.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import view.Menu;
import view.ViewMaster;
import view.allmenu.GameView;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class GameTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    Player p1, p2;
    User user, user2;
    GameView gameView;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        ViewMaster.setCurrentMenu(Menu.GAME_MENU);
        user = new User("Nima", "1234", "NIMA");
        user2 = new User("Hooman", "1234", "Nima");
        p1 = new Player(user);
        p2 = new Player(user2);
        gameView = new GameView(p1, p2, p1, 1);
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void battleTest() {

    }

}
