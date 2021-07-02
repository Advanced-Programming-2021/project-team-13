package view;

import controll.json.UserJson;
import model.players.AIPlayer;
import model.players.User;
import view.allmenu.*;

import java.util.Scanner;

public class ViewMaster {
    public static Scanner scanner = new Scanner(System.in);
    private static ViewMaster viewMaster;
    private static Menu currentMenu;
    private static User user;
    private final LoginView loginView;
    private final ShopView shopView;
    private final ScoreboardView scoreboardView;
    private final ProfileView profileView;
    private final MainView mainView;
    private final DeckView deckView;
    private final DuelView duelView;
    private final ImportAndExportView importAndExportView;
    private GameView gameView;
    private ShowGraveyardView showGraveyardView;

    private ViewMaster() {
        importAndExportView = new ImportAndExportView();
        loginView = new LoginView();
        shopView = new ShopView();
        scoreboardView = new ScoreboardView();
        profileView = new ProfileView();
        mainView = new MainView();
        deckView = new DeckView();
        duelView = new DuelView();
        currentMenu = Menu.LOGIN_MENU;
    }

    public static void setScanner(Scanner scanner) {
        ViewMaster.scanner = scanner;
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

    public GameView getGameView() {
        return gameView;
    }

    public DeckView getDeckView() {
        return deckView;
    }

    public void setShowGraveyardMenu(ShowGraveyardView showGraveyardView) {
        this.showGraveyardView = showGraveyardView;
    }

    public static ViewMaster getViewMaster() {
        if (viewMaster == null)
            viewMaster = new ViewMaster();
        return viewMaster;
    }

    public void run() {
        String command;
        while (currentMenu != Menu.EXIT_MENU) {
            if (currentMenu == Menu.GAME_MENU &&
                    gameView.getGameController().getCurrentPlayer() instanceof AIPlayer) {
                gameView.getGameController().playAI();
                continue;
            }
            command = scanner.nextLine().trim();
            if (command.equalsIgnoreCase("ai vs ai"))
                duelView.getDuelController().startAIDuelAI(3);
            if (command.matches(Regex.SHOW_MENU))
                printCurrentMenu();
            else if (command.matches(Regex.ENTER_MENU) && currentMenu != Menu.MAIN_MENU)
                System.out.println("menu navigation is not possible");
            else if (currentMenu == Menu.LOGIN_MENU)
                loginView.run(command);
            else if (currentMenu == Menu.MAIN_MENU)
                mainView.run(command);
            else if (currentMenu == Menu.SCOREBOARD_MENU)
                scoreboardView.run(command);
            else if (currentMenu == Menu.SHOP_MENU)
                shopView.run(command);
            else if (currentMenu == Menu.PROFILE_MENU)
                profileView.run(command);
            else if (currentMenu == Menu.DECK_MENU)
                deckView.run(command);
            else if (currentMenu == Menu.DUEL_MENU)
                duelView.run(command);
            else if (currentMenu == Menu.GAME_MENU)
                gameView.run(command);
            else if (currentMenu == Menu.SHOW_GRAVEYARD)
                showGraveyardView.run(command);
            else if (currentMenu == Menu.IMPORT_EXPORT_MENU)
                importAndExportView.run(command);
        }
        new UserJson().update();
    }

    public void printCurrentMenu() {
        System.out.println(currentMenu.getMenuName());
    }

}
