
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import view.Menu;
import view.ViewMaster;
import view.allmenu.MainView;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class MainMenuTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final MainView mainView = new MainView();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        ViewMaster.setCurrentMenu(Menu.MAIN_MENU);
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void mainMenuTest1() {
        mainView.run("user create --username nima --nickname Nima --password 1234");
        assertEquals("invalid command\r\n", outContent.toString());
    }

    @Test
    public void mainMenuTest2() {
        mainView.run("user login --password 1234 --username nima");
        assertEquals("invalid command\r\n", outContent.toString());
    }

    @Test
    public void mainMenuTest3() {
        mainView.run("user login --username nima --password 1234 ");
        assertEquals("invalid command\r\n", outContent.toString());
    }

    @Test
    public void mainMenuTest4() {
        mainView.run("menu enter scoreboard");
        assertEquals(ViewMaster.getCurrentMenu(), Menu.SCOREBOARD_MENU);
    }

}
