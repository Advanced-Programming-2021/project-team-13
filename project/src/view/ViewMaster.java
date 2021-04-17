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

    public static void setUser(User user) {
        ViewMaster.user = user;
    }

    public static User getUser() {
        return user;
    }

    public void printCurrentMenu() {

    }

    public void run(){

    }
}
