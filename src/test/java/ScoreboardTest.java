import model.players.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import view.Menu;
import view.ViewMaster;
import view.allmenu.ScoreboardView;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class ScoreboardTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        ViewMaster.setCurrentMenu(Menu.SCOREBOARD_MENU);
        User user = new User("shahin", "1", "shahin");
        user.addScore(5000);
        user = new User("Hooman", "1", "WhoMan");
        user.addScore(6000);
        user = new User("amir", "1", "Amrossein");
        user.addScore(6000);
        user = new User("hovakhshatara", "1", "hovakhshatara");
        user.addScore(4000);
        user = new User("nima", "1", "NIMA");
        user.addScore(6000);
        user = new User("ebrahim_1379", "1", "ebrahim_1379");
        user.addScore(3000);
        user = new User("the-ultimate-mahD", "1", "the-ultimate-mahD");
        user.addScore(3000);
        user = new User("rostam-dastan", "1", "rostam-dastan");
        user.addScore(1000);
        user = new User("rostam-dastan", "1", "rzostam-dastan");
        user.addScore(1000);
        user = new User("rostam-dastan", "1", "rzzstam-dastan");
        user.addScore(1000);
        user = new User("rostam-dastan", "1", "rzzztam-dastan");
        user.addScore(1000);
        user = new User("rostam-dastan", "1", "ok");
        user.addScore(500);
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @AfterAll
    public void reset() {
        User.getAllUsers().clear();
    }

   /* @Test
    public void scoreboardTest1() {
        new ScoreboardView().run("scoreboard show");
        Assert.assertEquals("1- Amrossein: 6000\r\n" + "1- NIMA: 6000\r\n" + "1- WhoMan: 6000\r\n" + "4- shahin: 5000\r\n" +
                "5- hovakhshatara: 4000\r\n" +
                "6- ebrahim_1379: 3000\r\n" +
                "6- the-ultimate-mahD: 3000\r\n" +
                "8- rostam-dastan: 1000\r\n" +
                "8- rzostam-dastan: 1000\r\n" +
                "8- rzzstam-dastan: 1000\r\n" +
                "8- rzzztam-dastan: 1000\r\n" +
                "12- ok: 500\r\n", outContent.toString());
    }

    @Test
    public void scoreboardTest2() {
        new ScoreboardView().run("menu enter x");
        Assert.assertEquals("invalid command\r\n", outContent.toString());
    }

    @Test
    public void scoreboardTest3() {
        new ScoreboardView().run("menu exit");
        Assert.assertEquals(ViewMaster.getCurrentMenu(), Menu.MAIN_MENU);
    }*/

}
