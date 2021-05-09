package view.allmenu;
import controll.MainController;
import view.Regex;

import java.util.regex.Matcher;

public class MainView {
    private final MainController mainController;

    public MainView(){
        mainController = new MainController(this);
    }

    public void run(String command) {
        if (command.startsWith("menu enter")) {
           menuEnter(command);
        } else if (command.matches(Regex.EXIT_MENU) || command.matches("user logout"))
            mainController.logout();
        else printInvalidCommand();
    }

    private void menuEnter(String command){
        Matcher menu = Regex.getInputMatcher(command , Regex.ENTER_MENU);
        if (menu.find())
            mainController.enterMenu(menu.group("menuName"));
    }

    public void printMenuNavigationImpossible(){
        System.out.println("menu navigation is not possible");
    }

    public void printInvalidCommand(){
        System.out.println("invalid command");
    }

    public MainController getMainController() {
        return mainController;
    }
}
