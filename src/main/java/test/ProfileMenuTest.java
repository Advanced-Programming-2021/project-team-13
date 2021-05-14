package test;

import model.players.User;
import org.junit.*;
import view.Menu;
import view.ViewMaster;
import view.allmenu.ProfileView;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class ProfileMenuTest {
    ProfileView profileView = new ProfileView();
    User user, user2;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        ViewMaster.setCurrentMenu(Menu.PROFILE_MENU);
        user = new User("Nima", "1234", "NIMA");
        user2 = new User("Nima", "1234", "Nima");
        ViewMaster.setUser(user);
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void profileMenuTest1() {
        profileView.run("profile change --nickname Nima");
        Assert.assertEquals("user with nickname Nima already exists\r\n", outContent.toString());
    }

    @Test
    public void profileMenuTest2() {
        profileView.run("profile change --nickname NiMa");
        Assert.assertEquals("nickname changed successfully!\r\n", outContent.toString());
    }

    @Test
    public void profileMenuTest3() {
        profileView.run("profile change --nickname NiMa");
        Assert.assertEquals(user.getNickname(), "NiMa");
    }

    @Test
    public void profileMenuTest4() {
        profileView.run("profile change --password --current 123 --new 56");
        Assert.assertEquals("current password is invalid\r\n", outContent.toString());
    }

    @Test
    public void profileMenuTest5() {
        profileView.run("profile change --password --new 56 --current 123");
        Assert.assertEquals("current password is invalid\r\n", outContent.toString());
    }

    @Test
    public void profileMenuTest6() {
        profileView.run("profile change --password --new 1234 --current 1234");
        Assert.assertEquals("please enter a new password\r\n", outContent.toString());
    }

    @Test
    public void profileMenuTest7() {
        profileView.run("profile change --password --current 1234 --new 1234");
        Assert.assertEquals("please enter a new password\r\n", outContent.toString());
    }

    @Test
    public void profileMenuTest8() {
        profileView.run("profile change --password --new HomanAmir1234 --current 1234");
        Assert.assertEquals("password changed successfully!\r\n", outContent.toString());
    }

    @Test
    public void profileMenuTest9() {
        profileView.run("profile change --password --new HomanAmir1234 --current 1234");
        Assert.assertEquals(user.getPassword(), "HomanAmir1234");
    }

    @Test
    public void profileMenuTest10() {
        profileView.run("profile change --password  --new HomanAmir1234 --current 1234");
        Assert.assertEquals("invalid command\r\n", outContent.toString());
    }

    @Test
    public void profileMenuTest11() {
        profileView.run("profile change --password --new HomanAmir1234  --current 1234");
        Assert.assertEquals("invalid command\r\n", outContent.toString());
    }

    @Test
    public void profileMenuTest12() {
        profileView.run("profile change  --password --new HomanAmir1234  --current 1234");
        Assert.assertEquals("invalid command\r\n", outContent.toString());
    }

    @Test
    public void profileMenuTest13() {
        profileView.run("profile change  --password --new Homan Amir1234  --current 1234");
        Assert.assertEquals("invalid command\r\n", outContent.toString());
    }

    @Test
    public void profileMenuTest14() {
        profileView.run("profile change --p --n HomanAmir1234 --c 1234");
        Assert.assertEquals("password changed successfully!\r\n", outContent.toString());
    }

    @Test
    public void profileMenuTest15() {
        profileView.run("menu exit");
        Assert.assertEquals(ViewMaster.getCurrentMenu(), Menu.MAIN_MENU);
    }

    @Test
    public void profileMenuTest16() {
        Assert.assertEquals(ViewMaster.getCurrentMenu(), Menu.PROFILE_MENU);
    }

}
