package controll;

import enums.Menu;
import view.MainView;
import view.ViewMaster;


public class MainController {
    private final MainView mainView;
    public MainController(MainView mainView){
        this.mainView = mainView;
    }

    public void enterMenu(String menuToEnter){
        if (menuToEnter.equalsIgnoreCase("login"))
            mainView.printMenuNavigationImpossible();
        else if (menuToEnter.equalsIgnoreCase("main"))
            mainView.printMenuNavigationImpossible();
        else if (menuToEnter.equalsIgnoreCase("duel"))
            ViewMaster.setCurrentMenu(Menu.DUEL_MENU);
        else if (menuToEnter.equalsIgnoreCase("deck"))
            ViewMaster.setCurrentMenu(Menu.DECK_MENU);
        else if (menuToEnter.equalsIgnoreCase("scoreboard"))
            ViewMaster.setCurrentMenu(Menu.SCOREBOARD_MENU);
        else if (menuToEnter.equalsIgnoreCase("profile"))
            ViewMaster.setCurrentMenu(Menu.PROFILE_MENU);
        else if (menuToEnter.equalsIgnoreCase("shop"))
            ViewMaster.setCurrentMenu(Menu.SHOP_MENU);
        else if (menuToEnter.equalsIgnoreCase("import/export"))
            ViewMaster.setCurrentMenu(Menu.IMPORT_EXPORT_MENU);
        else mainView.printInvalidCommand();
    }

    public void logout() {
        ViewMaster.setCurrentMenu(Menu.LOGIN_MENU);
    }
}
