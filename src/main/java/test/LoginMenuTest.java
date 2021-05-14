package test;


import model.players.User;
import org.junit.After;
import org.junit.Before;
import view.Menu;
import view.ViewMaster;
import view.allmenu.LoginView;

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

    @org.junit.Test
    public void loginMenuTest1() {
        loginView.run("menu enter shop");
        assertEquals("please login first\r\n", outContent.toString());
    }

    @org.junit.Test
    public void loginMenuTest2() {
        loginView.run("menu show-current");
        assertEquals("invalid command\r\n", outContent.toString());
    }

    @org.junit.Test
    public void loginMenuTest3() {
        loginView.run("user login --username nima --password 123");
        assertEquals("Username and password didn’t match!\r\n", outContent.toString());
    }

    @org.junit.Test
    public void loginMenuTest3Half() {
        loginView.run("user login --username n-sd --password 123");
        assertEquals("invalid command\r\n", outContent.toString());
    }

    @org.junit.Test
    public void loginMenuTest4() {
        loginView.run("user create --username nima --nickname Nima --password 1234");
        assertEquals("user created successfully!\r\n", outContent.toString());
    }

    @org.junit.Test
    public void loginMenuTest5() {
        loginView.run("user create --username nima --nickname Nima --password 1234");
        assertEquals("user with username nima already exists\r\n", outContent.toString());
    }

    @org.junit.Test
    public void loginMenuTest6() {
        loginView.run("user create --u nima --n Nima --p 1234");
        assertEquals("user with username nima already exists\r\n", outContent.toString());
    }

    @org.junit.Test
    public void loginMenuTest7() {
        loginView.run("user create --n Nima --u nimsa --p 1234");
        assertEquals("user with nickname Nima already exists\r\n", outContent.toString());
    }

    @org.junit.Test
    public void loginMenuTest8() {
        loginView.run("user create --p 1234 --n Nima --u nimsa");
        assertEquals("user with nickname Nima already exists\r\n", outContent.toString());
    }

    @org.junit.Test
    public void loginMenuTest9() {
        loginView.run("user create --n Nima --p 1234 --u nimsa");
        assertEquals("user with nickname Nima already exists\r\n", outContent.toString());
    }

    @org.junit.Test
    public void loginMenuTest10() {
        loginView.run("user login --username nima --password 1234");
        assertEquals("user logged in successfully!\r\n", outContent.toString());
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
