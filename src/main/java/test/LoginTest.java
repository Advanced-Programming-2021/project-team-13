package test;

import controll.LoginController;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.Menu;
import view.ViewMaster;
import view.allmenu.LoginView;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class LoginTest {
    LoginView loginView = new LoginView();
    LoginController loginController = new LoginController(loginView);
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();


    @BeforeEach
    public void checkLogin(){
        loginController.registerUser("ali","ali","ali");
    }

    @Test
    public void checkLoginOk(){
        loginController.loginUser("ali" , "ali");
        loginController.registerUser("mamad" , "ali" , "ali");
        Assertions.assertEquals(Menu.MAIN_MENU, ViewMaster.getCurrentMenu());
    }

    @Test
    public void checkLoginForNotCreated(){
        System.setOut(new PrintStream(outContent));
        loginController.loginUser("mamad" , "ali");
        Assertions.assertEquals("Username and password didn’t match!" , outContent);
    }

}
