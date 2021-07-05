package view.allmenu;

import controll.gameController.DuelController;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.effect.Bloom;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.players.User;
import view.Regex;
import view.ViewMaster;

import java.util.Arrays;
import java.util.Collections;
import java.util.regex.Matcher;

public class DuelView {
    private final DuelController duelController;
    public ImageView rockImg;
    public ImageView paperImg;
    public ImageView scissorsImg;
    public Button playRPS;
    public int numberToReturn=-1;
    public DuelView() {
        duelController = new DuelController(this);
    }

    public void initialize(){
        Bloom glow = new Bloom();
        setBtnEffects(glow,scissorsImg,"/duelMenuPics/rps/scissors.bmp",3);
        setBtnEffects(glow,paperImg,"/duelMenuPics/rps/paper.bmp",1);
        setBtnEffects(glow,rockImg,"/duelMenuPics/rps/rock.bmp",2);
    }

    private void setBtnEffects(Bloom glow, ImageView view, String url,int numberToReturn) {
        Image  image = new Image(url);
        view.setImage(image);
        view.setOnMouseEntered(event -> {
            glow.setThreshold(0.75);
            view.setEffect(glow);
        });
        view.setOnMouseExited(event -> {
            view.setEffect(null);
        });
        view.setOnMouseClicked(event -> {
            Arrays.stream(new ImageView[]{scissorsImg, paperImg, rockImg})
                    .forEach(x->x.setOpacity(1));
            if( Arrays.stream(new ImageView[]{scissorsImg, paperImg, rockImg})
                    .filter(x->x!=view).filter(x->x.getOpacity()==0.4).count()==2)
                Arrays.stream(new ImageView[]{scissorsImg, paperImg, rockImg})
                        .filter(x->x!=view)
                        .forEach(x->x.setOpacity(1));
            else
            Arrays.stream(new ImageView[]{scissorsImg, paperImg, rockImg})
                    .filter(x->x!=view)
                    .forEach(x->x.setOpacity(0.4));
        });

    }

    public void run(String command) {
        if (command.matches(Regex.TWO_PLAYER_DUEL)) {
            Matcher newMatcher = Regex.getInputMatcher(command, Regex.NEW);
            Matcher roundMatcher = Regex.getInputMatcher(command, Regex.ROUNDS);
            Matcher duelerMatcher = Regex.getInputMatcher(command, Regex.SECOND_PLAYER);
            if (newMatcher.find(0) && roundMatcher.find(0) && duelerMatcher.find(0)) {
                int rounds = Integer.parseInt(roundMatcher.group("rounds"));
                String playerUsername = duelerMatcher.group("playerUsername");
                duelController.validateTwoPlayerDuelGame(rounds, playerUsername);
            } else printInvalidCommand();
        } else if (command.matches(Regex.AI_DUEL)) {
            Matcher newMatcher = Regex.getInputMatcher(command, Regex.NEW);
            Matcher roundMatcher = Regex.getInputMatcher(command, Regex.ROUNDS);
            Matcher aiMatcher = Regex.getInputMatcher(command, Regex.AI);
            if (newMatcher.find(0) && roundMatcher.find(0) && aiMatcher.find(0)) {
                int rounds = Integer.parseInt(roundMatcher.group("rounds"));
                duelController.validateAIDuelGame(rounds);
            } else printInvalidCommand();
        } else printInvalidCommand();
    }

    public void printUserNotFound() {
        System.out.println("there is no player with this username");
    }

    public void printNoActiveDeck(String username) {
        System.out.println(username + " has no active deck");
    }

    public void printInvalidDeck(String username) {
        System.out.println(username + "’s deck is invalid");
    }

    public void printInvalidNumberOfRound() {
        System.out.println("number of rounds is not supported");
    }

    public void printInvalidCommand() {
        System.out.println("invalid command");
    }

    public DuelController getDuelController() {
        return duelController;
    }

    public int inputStonePaperScissor(User user) {
        System.out.println("hey " + user.getNickname() + " !\nplease choose one to start Game: stone , paper , scissor");
        if(numberToReturn==0) {
            System.out.println("choose a fuckin thing");
            return 4;
        }
        return numberToReturn;
    }

    public void printEqual() {
        System.out.println("Equal!\ntry again!");
    }

    public void playRPS(ActionEvent event) {
    }
}
