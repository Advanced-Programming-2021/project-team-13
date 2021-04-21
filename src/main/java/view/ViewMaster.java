package view;

import java.util.Scanner;

import enums.Menu;
import model.User;

public class ViewMaster {
    public static Scanner scanner = new Scanner(System.in);
    private static Menu currentMenu;
    private static User user;
    private final LoginView loginView;
    private final ShopView shopView;
    private final ScoreboardView scoreboardView;
    private final ProfileView profileView;
    private final MainView mainView;
    private final DeckView deckView;

    public ViewMaster() {
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
        System.out.println(currentMenu.getMenuName());
    }

    public static Menu getCurrentMenu() {
        return currentMenu;
    }

    public static void setCurrentMenu(Menu currentMenu) {
        ViewMaster.currentMenu = currentMenu;
    }

    public void run() {
        String command;
        while (currentMenu != Menu.EXIT_MENU) {
            command = scanner.nextLine().trim();
            if (command.matches(Regex.SHOW_MENU))
                printCurrentMenu();
            if (currentMenu == Menu.LOGIN_MENU)
                loginView.run(command);
            else if (currentMenu == Menu.MAIN_MENU)
                mainView.run(command);
            else if (currentMenu == Menu.SHOP_MENU)
                shopView.run(command);
            else if (currentMenu == Menu.PROFILE_MENU)
                profileView.run(command);
            else if (currentMenu == Menu.DECK_MENU)
                deckView.run(command);
            command = scanner.nextLine().trim();

        }
    }
}
