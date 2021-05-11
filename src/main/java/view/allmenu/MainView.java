package view.allmenu;
import controll.MainController;
import view.Regex;

import java.util.regex.Matcher;

public class MainView {
    private final MainController mainController;

    public MainView(){
        mainController = new MainController(this);
    }

    public void printMenuNavigationImpossible(){
        System.out.println("menu navigation is not possible");
    }

    public void printInvalidCommand(){
        System.out.println("invalid command");
    }

    public void run(String command) {
        if (command.startsWith("menu enter")) {
            Matcher menu = Regex.getInputMatcher(command , Regex.ENTER_MENU);
            mainController.enterMenu(menu.group("menuName"));
        } else if (command.matches(Regex.EXIT_MENU) || command.matches("user logout"))
            mainController.logout();
        else printInvalidCommand();
    }
}
