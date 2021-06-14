package view.allMenu;

import controll.ShowGraveyardController;
import model.cards.Card;
import model.players.Player;
import view.Menu;
import view.ViewMaster;

public class ShowGraveyardView {
    private final ShowGraveyardController showGraveyardController;

    public ShowGraveyardView(Player currentPlayer) {
        showGraveyardController = new ShowGraveyardController(this, currentPlayer);
    }

    public ShowGraveyardController getShowGraveyardController() {
        return showGraveyardController;
    }

    public void run(String command) {
        if (command.equals("show graveyard")) {
            showGraveyardController.showGraveyard();
        } else if (command.equals("back")) {
            ViewMaster.setCurrentMenu(Menu.GAME_MENU);
        } else printInvalidCommand();
    }

    public void printInvalidCommand() {
        System.out.println("invalid command");
    }

    public void printGraveyardEmpty() {
        System.out.println("graveyard empty");
    }

    public void printCard(Card card, int number) {
        System.out.println(number + ". " + card.toString());
    }

    public void printSelectCard(){
        System.out.print("Please select the number of the card you want to return: ");
    }
}
