import model.players.User;
import org.junit.After;
import org.junit.Before;
import view.Menu;
import view.ViewMaster;
import view.allMenu.ShopView;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static junit.framework.TestCase.assertEquals;

public class ShopMenuTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    ShopView shopView = new ShopView();
    User user;


    @Before
    public void setUpStreams() {
        ViewMaster.getViewMaster();
        System.setOut(new PrintStream(outContent));
        ViewMaster.setCurrentMenu(Menu.SHOP_MENU);
        user = new User("Amir", "1", "amrossein");
        ViewMaster.setUser(user);
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        User.getAllUsers().clear();
    }

    @org.junit.Test
    public void testInvalidBuy() {
        shopView.run("shop buy");
        assertEquals("invalid command\r\n", outContent.toString());
    }

    @org.junit.Test
    public void testInvalidCard() {
        shopView.run("shop buy helloTA");
        assertEquals("there is no card with this name\r\n", outContent.toString());
    }

    @org.junit.Test
    public void testNotEnoughAmountMoney() {
        user.setMoney(0);
        shopView.run("shop buy Call Of The Haunted");
        assertEquals("not enough money\r\n", outContent.toString());
    }

    @org.junit.Test
    public void testShowAll() {
        shopView.run("shop show --all");
//        WTF????
    }
}

