package view.allmenu;

import controll.gameController.DuelController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import model.menuItems.CustomButton;
import model.players.User;
import view.Regex;

import java.io.IOException;
import java.util.Arrays;

public class DuelView {
    private final DuelController duelController;
    public ImageView rockImg;
    public ImageView paperImg;
    public ImageView scissorsImg;
    public Button playRPS;
    public BorderPane pane;
    public AnchorPane rightPane;
    public AnchorPane leftPane;
    public AnchorPane downPane;
    public AnchorPane upPane;
    public AnchorPane rpc;
    public VBox vBox;
    public HBox hBox;
    public HBox rpcHbox;
    public Label notifLabel;
    public Label rpcNotifLabel;
    private int numberToReturn = -1;

    public DuelView() {
        duelController = new DuelController(this);
    }

    public void initialize() {
//        rockImg=new ImageView();
//        paperImg=new ImageView();
//        scissorsImg=new ImageView();
        leftPane.getChildren().remove(rpcHbox);
        Bloom glow = new Bloom();
        setBtnEffects(glow, scissorsImg, "/duelMenuPics/rps/scissors.bmp", 3);
        setBtnEffects(glow, paperImg, "/duelMenuPics/rps/paper.bmp", 1);
        setBtnEffects(glow, rockImg, "/duelMenuPics/rps/rock.bmp", 2);
        setBackGround(pane, "/duelMenuPics/moon.gif", 1);
        vBox = new VBox(0, getFirstVboxNodes());
        rightPane.getChildren().add(vBox);
    }

    private Node[] getFirstVboxNodes() {
        return new Node[]{
                new CustomButton("AI duel", () -> {
                    startAIDuel();
                }), new CustomButton("2 Player duel", () -> {

        }), new CustomButton("Back", () -> {
            try {
                goBack();
            } catch (IOException e) {
                e.printStackTrace();
            }
        })
        };
    }

    private void goBack() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainMenu.fxml"));
        ((Stage) pane.getScene().getWindow()).setScene(new Scene(root));
    }

    public void startAIDuel() {
        getRounds();
    }

    private void getRounds() {
        vBox.setVisible(false);
        hBox = new HBox(1, new CustomButton("1", () -> {
//            duelController.validateAIDuelGame(1);
            sho();
            vBox.setVisible(true);
            pane.getChildren().remove(hBox);
        }), new CustomButton("3", () -> {
//            duelController.validateAIDuelGame(3);
            vBox.setVisible(true);
            pane.getChildren().remove(hBox);
        }));
        hBox.setTranslateX(440);
        hBox.setTranslateY(275);
        pane.getChildren().add(hBox);

    }

    private void setBackGround(BorderPane pane, String url,double opacity) {
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
//        }
        }
    }

    public void printUserNotFound() {
        System.out.println("there is no player with this username");
    }

    public void printNoActiveDeck(String username) {
        notifLabel.setText(username + " has no active deck");
    }

    public void printInvalidDeck(String username) {
        notifLabel.setText(username + "’s deck is invalid");
    }

    public DuelController getDuelController() {
        return duelController;
    }

    public int inputStonePaperScissor(User user) {
        sho();
        return numberToReturn;
    }

    private void sho() {
        rpc = new AnchorPane();
        rpcNotifLabel = new Label("");
        rpcNotifLabel.setTranslateX(35);
        rpcNotifLabel.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.ITALIC, 18));
        rpcNotifLabel.setStyle("-fx-text-fill:#fa6515");
        rpcNotifLabel.setEffect(new Bloom(0.3));
        VBox box = new VBox(25, rpcNotifLabel, rpcHbox, new CustomButton("Start Game", () -> {
            if (numberToReturn == -1)
                rpcNotifLabel.setText("you haven't chosen yet");
            else {
                System.out.println(numberToReturn);
                pane.getChildren().remove(rpc);
                unBlur();
            }
        }));
        rpc.getChildren().addAll(box);
        rpc.setStyle("-fx-background-color:yellow");
        blur();
        rpc.setTranslateX(1280 - 350);
        rpc.setTranslateY(250);
        pane.getChildren().add(rpc);
    }

    private void unBlur() {
        rightPane.setEffect(null);
        rightPane.setDisable(false);
        downPane.setEffect(null);
        downPane.setDisable(false);
        leftPane.setEffect(null);
        leftPane.setDisable(false);
        upPane.setEffect(null);
        upPane.setDisable(false);
    }

    private void blur() {
        rightPane.setEffect(new GaussianBlur(15));
        rightPane.setDisable(true);
        downPane.setEffect(new GaussianBlur(15));
        downPane.setDisable(true);
        leftPane.setEffect(new GaussianBlur(15));
        leftPane.setDisable(true);
        upPane.setEffect(new GaussianBlur(15));
        upPane.setDisable(true);
    }

    public void printEqual() {
        rpcNotifLabel.setText("Equal!\ntry again!");
    }

}
