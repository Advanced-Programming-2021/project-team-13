
import model.players.User;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterAll;
import view.Menu;
import view.ViewMaster;
import view.allMenu.LoginView;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class LoginMenuTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    LoginView loginView = new LoginView();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);

    }

    @AfterAll
    public void reset() {
        User.getAllUsers().clear();
    }

    @org.junit.Test
    public void loginMenuTest11() {
        assertNotNull(ViewMaster.getUser());
    }

    @org.junit.Test
    public void loginMenuTest12() {
        assertEquals(ViewMaster.getCurrentMenu(), Menu.MAIN_MENU);
    }


}
