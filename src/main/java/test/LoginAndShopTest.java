package test;

import controll.LoginController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.Menu;
import view.ViewMaster;
import view.allmenu.LoginView;
import view.allmenu.MainView;

import java.io.ByteArrayOutputStream;

public class LoginAndShopTest {
    LoginView loginView = new LoginView();
    LoginController loginController = new LoginController(loginView);
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();


    @BeforeEach
    public void checkLogin(){
        loginController.registerUser("ali","ali","ali");
        loginController.loginUser("ali" , "ali");
    }

    @Test
    public void checkLoginOk(){
        loginController.registerUser("mamad" , "ali" , "ali");
        Assertions.assertEquals(Menu.MAIN_MENU, ViewMaster.getCurrentMenu());
    }

    @Test
    public void checkLogout(){

        Assertions.assertEquals("Username and password didn’t match!" , outContent);
    }

    @Test
    public void checkMenuEntrance(){

    }
}
