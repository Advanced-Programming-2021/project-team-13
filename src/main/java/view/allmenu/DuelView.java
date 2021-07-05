package view.allmenu;

import controll.gameController.DuelController;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.effect.Bloom;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import model.menuItems.CustomButton;
import model.players.User;
import view.Regex;

import java.util.Arrays;

public class DuelView {
    private final DuelController duelController;
    public ImageView rockImg;
    public ImageView paperImg;
    public ImageView scissorsImg;
    public Button playRPS;
    public BorderPane pane;
    public AnchorPane rightPane;
    public VBox vBox;
    public HBox hBox;
    private int numberToReturn = -1;
    private int rounds;
    public DuelView() {
        duelController = new DuelController(this);
    }

    public void initialize() {
        Bloom glow = new Bloom();
        setBtnEffects(glow, scissorsImg, "/duelMenuPics/rps/scissors.bmp", 3);
        setBtnEffects(glow, paperImg, "/duelMenuPics/rps/paper.bmp", 1);
        setBtnEffects(glow, rockImg, "/duelMenuPics/rps/rock.bmp", 2);
        setBackGround(pane, "/duelMenuPics/moon.gif");
        vBox = new VBox(20, getFirstVboxNodes());
        rightPane.getChildren().add(vBox);
    }

    private Node[] getFirstVboxNodes() {
        return new Node[]{
                new CustomButton("AI duel", () -> {
                    startAIDuel();
                }), new CustomButton("2 Player duel", () -> {

        })
        };
    }

    public void startAIDuel() {
       getRounds();
//        duelController.validateAIDuelGame(rounds);
    }

    private void getRounds() {
        vBox.setVisible(false);
        hBox = new HBox(1, new CustomButton("1", () -> {
            rounds=1;
            vBox.setVisible(true);
            pane.getChildren().remove(hBox);
        }), new CustomButton("3", () -> {
            rounds=3;
            vBox.setVisible(true);
            pane.getChildren().remove(hBox);
        }));
        hBox.setTranslateX(440);
        hBox.setTranslateY(275);
        pane.getChildren().add(hBox);

    }

    private void setBackGround(BorderPane pane, String url) {
        Image background = new Image(url, 1280, 720, false, true);
        pane.setBackground(new Background(new BackgroundImage(background, BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
    }

    private void setBtnEffects(Bloom glow, ImageView view, String url, int numberToReturn) {
        Image image = new Image(url);
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
                    .forEach(x -> x.setOpacity(1));
            if (Arrays.stream(new ImageView[]{scissorsImg, paperImg, rockImg})
                    .filter(x -> x != view).filter(x -> x.getOpacity() == 0.4).count() == 2)
                Arrays.stream(new ImageView[]{scissorsImg, paperImg, rockImg})
                        .filter(x -> x != view)
                        .forEach(x -> x.setOpacity(1));
            else {
                Arrays.stream(new ImageView[]{scissorsImg, paperImg, rockImg})
                        .filter(x -> x != view)
                        .forEach(x -> x.setOpacity(0.4));
            }
            this.numberToReturn = numberToReturn;
        });

    }

    public void run(String command) {
        if (command.matches(Regex.TWO_PLAYER_DUEL)) {
//            Matcher newMatcher = Regex.getInputMatcher(command, Regex.NEW);
//            Matcher roundMatcher = Regex.getInputMatcher(command, Regex.ROUNDS);
//            Matcher duelerMatcher = Regex.getInputMatcher(command, Regex.SECOND_PLAYER);
//            if (newMatcher.find(0) && roundMatcher.find(0) && duelerMatcher.find(0)) {
//            int rounds = Integer.parseInt(roundMatcher.group("rounds"));
//            String playerUsername = duelerMatcher.group("playerUsername");
//            duelController.validateTwoPlayerDuelGame(rounds, playerUsername);
//            } else printInvalidCommand();
//        } else if (command.matches(Regex.AI_DUEL)) {
//            Matcher newMatcher = Regex.getInputMatcher(command, Regex.NEW);
//            Matcher roundMatcher = Regex.getInputMatcher(command, Regex.ROUNDS);
//            Matcher aiMatcher = Regex.getInputMatcher(command, Regex.AI);
//            if (newMatcher.find(0) && roundMatcher.find(0) && aiMatcher.find(0)) {
//                int rounds = Integer.parseInt(roundMatcher.group("rounds"));

//            } else printInvalidCommand();
//        } else printInvalidCommand();
        }
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
