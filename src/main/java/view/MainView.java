package view;
import controll.MainController;

public class MainView {
    private MainController mainController;

    public MainView(){
        mainController = new MainController(this);
    }

    public void run(String command) {
    }
}
