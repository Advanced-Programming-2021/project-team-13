package view;

import model.players.User;
import view.allmenu.*;
import java.io.IOException;
import java.util.Scanner;

public class ViewMaster {
    public static Scanner scanner = new Scanner(System.in);
    private static ViewMaster viewMaster;
    private static Menu currentMenu;
    private static User user;
    private final LoginView loginView;
    private final ShopView shopView;
    private final ScoreboardLabel scoreboardView;
    private final ProfileView profileView;
    private final MainView mainView;
    private final DeckView deckView;
    private final DuelView duelView;
    private GameView gameView;
    private ShowGraveyardView showGraveyardView;

    private ViewMaster() {
        loginView = new LoginView();
        shopView = new ShopView();
        scoreboardView = new ScoreboardLabel();
        profileView = new ProfileView();
        mainView = new MainView();
        deckView = new DeckView();
        duelView = new DuelView();
        currentMenu = Menu.LOGIN_MENU;
    }

    public static void setUser(User user) {
        ViewMaster.user = user;
    }

    public static User getUser() {
        return user;
    }

    public static Menu getCurrentMenu() {
        return currentMenu;
    }

    public static void setCurrentMenu(Menu currentMenu) {
        ViewMaster.currentMenu = currentMenu;
    }

    public void setGameView(GameView gameView) {
        this.gameView = gameView;
    }

    public DeckView getDeckView() {
        return deckView;
    }

    public GameView getGameView() {
        return gameView;
    }

    public void setShowGraveyardMenu(ShowGraveyardView showGraveyardView) {
        this.showGraveyardView = showGraveyardView;
    }

    public static ViewMaster getViewMaster() {
        if (viewMaster == null)
            viewMaster = new ViewMaster();
        return viewMaster;
    }

    public void run() throws IOException {
        loginView.setLogin();
    }

}
