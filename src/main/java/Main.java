
import controll.json.UserJson;
import enums.CardType;
import enums.Face;
import model.cards.Card;
import view.ViewMaster;

public class Main {
    public static void main(String[] args) {
        //new UserJson().loadDataBase();
        ViewMaster.getViewMaster().run();
    }
}