package view;

import java.util.Scanner;

import enums.Menu;
import model.User;

public class ViewMaster {
    public static Scanner scanner = new Scanner(System.in);
    public static Menu currentMenu;
    public static User user;
    private LoginView loginView;
    private ShopView shopView;
    private ScoreboardView scoreboardView;
    private ProfileView profileView;
    private MainView mainView;
    private DeckView deckView;

    public ViewMaster()
    {
        loginView = new LoginView();
        shopView = new ShopView();
        scoreboardView = new ScoreboardView();
        profileView = new ProfileView();
        mainView = new MainView();
        deckView = new DeckView();
        currentMenu = Menu.LOGIN_MENU;
    }

    public static void setUser(User user) {
        ViewMaster.user = user;
    }

    public static User getUser() {
        return user;
    }

    public void printCurrentMenu() {

    }

    public void run(){
        String command = scanner.nextLine().trim();
        while (currentMenu != Menu.EXIT_MENU)
        {
            if (currentMenu == Menu.LOGIN_MENU)
                loginView.run(command);
            else if (currentMenu == Menu.MAIN_MENU)
                mainView.run(command);
            else if (currentMenu == Menu.SHOP_MENU)
                shopView.run(command);
            else if (currentMenu == Menu.PROFILE_MENU)
                profileView.run(command);
            else if (currentMenu == Menu.SCOREBOARD_MENU)
                scoreboardView.run(command);
            else if (currentMenu == Menu.DECK_MENU)
                deckView.run(command);
            command = scanner.nextLine();

        }

    }
}
