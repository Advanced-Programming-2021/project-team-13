package view.allmenu;

import controll.gameController.DuelController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.menuItems.CustomButton;
import model.menuItems.CustomSanButtons;
import model.players.Player;
import model.players.User;
import view.ViewMaster;

import java.io.IOException;
import java.util.Arrays;

public class DuelView {
    private final DuelController duelController;
    private static Stage stage = null;
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
    public StackPane notifStackPane;
    public VBox vBox;
    public VBox notif;
    public HBox hBox;
    public HBox rpcHbox;
    public Label notifLabel;
    public Text text;
    public Label rpcNotifLabel;
    private int numberToReturn = -1;
    private int round = 0;

    public DuelView() {
        duelController = new DuelController(this);
    }

    public void initialize() {
        Bloom glow = new Bloom();
        Thread t = new Thread(() -> setBackGround(pane, "/duelMenuPics/moon.gif"));
        t.start();
        new Thread(() -> setBtnEffects(glow, scissorsImg, "/duelMenuPics/rps/scissors.bmp", 3))
                .start();
        new Thread(() -> setBtnEffects(glow, paperImg, "/duelMenuPics/rps/paper.bmp", 1))
                .start();
        new Thread(() -> setBtnEffects(glow, rockImg, "/duelMenuPics/rps/rock.bmp", 2))
                .start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setupNotifStackPane();
        leftPane.getChildren().remove(rpcHbox);
        vBox = new VBox(0, getFirstVboxNodes());
        rightPane.getChildren().add(vBox);
    }

    private void setupNotifStackPane() {
        notif = new VBox();
        notif.setSpacing(30);
        notifStackPane = new StackPane();
        notifStackPane.getChildren().add(notif);
        notif.setPrefHeight(300);
        notif.setPrefWidth(700);
        notif.setMinHeight(300);
        notif.setMinWidth(700);
        notif.setAlignment(Pos.TOP_CENTER);
        notif.setStyle("-fx-background-image: url('/duelMenuPics/notif.gif');-fx-background-size: cover,auto");
        notifStackPane.setMinHeight(300);
        notifStackPane.setMinWidth(700);
        notifStackPane.setPrefHeight(300);
        notifStackPane.setPrefWidth(700);
        notifStackPane.setAlignment(Pos.CENTER);
        notifStackPane.setTranslateY(350);
        notifStackPane.setTranslateX(650);
        notifStackPane.setVisible(false);
        pane.getChildren().add(notifStackPane);
    }

    public void createNotification(String text, Node[] nodes) {
        blur();
        notifStackPane.setVisible(true);
        notif.getChildren().clear();
        notifLabel.setText(text);
        notifLabel.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.ITALIC, 30));
        notifLabel.setTextFill(Color.SILVER);
        notifLabel.setEffect(new Bloom(0.2));
        notif.getChildren().addAll(new Text(""), notifLabel);
        for (Node node : nodes) {
            node.resize(150, 75);
        }
        notif.getChildren().addAll(nodes);
    }

    private Node[] getFirstVboxNodes() {
        return new Node[]{
                new CustomButton("AI duel", () -> {
                    ViewMaster.btnSoundEffect();
                    startAIDuel();
                }), new CustomButton("2 Player duel", () -> {
            ViewMaster.btnSoundEffect();
        }), new CustomButton("Back", () -> {
            try {
                ViewMaster.btnSoundEffect();
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
        if (duelController.validateAIDuelGame())
            getRounds();
        else
            createNotification("You Do Not Have Active/Valid Deck", new Node[]{
                    new CustomButton("proceed", () -> {
                        unBlur();
                        notifStackPane.setVisible(false);
                    })
            });
    }

    private void getRounds() {
        vBox.setVisible(false);
        hBox = new HBox(1, new CustomButton("1", () -> {
            round = 1;
            sho();
            vBox.setVisible(true);
            pane.getChildren().remove(hBox);
        }), new CustomButton("3", () -> {
            round = 3;
            sho();
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
            ViewMaster.btnSoundEffect();
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

    public void printUserNotFound() {
        System.out.println("there is no player with this username");
    }

    public void printNoActiveDeck(String username) {
        createNotification(username + " has no active deck", new Node[]{
                new CustomButton("proceed", () -> {
                    ViewMaster.btnSoundEffect();
                    unBlur();
                    notifStackPane.setVisible(false);
                })
        });
    }

    public void printInvalidDeck(String username) {
        createNotification(username + "’s deck is invalid", new Node[]{
                new CustomButton("proceed", () -> {
                    ViewMaster.btnSoundEffect();
                    unBlur();
                    notifStackPane.setVisible(false);
                })
        });
    }

    public int inputStonePaperScissor(User user) {
        sho();
        return numberToReturn;
    }

    private void sho() {
        rightPane.setVisible(false);
        rpc = new AnchorPane();
        rpcNotifLabel = new Label("");
        rpcNotifLabel.setTranslateX(35);
        rpcNotifLabel.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.ITALIC, 18));
        rpcNotifLabel.setStyle("-fx-text-fill:#fa6515");
        rpcNotifLabel.setEffect(new Bloom(0.3));
        VBox box = new VBox(25, rpcNotifLabel, rpcHbox, new CustomButton("Start Game", () -> {
            if (numberToReturn == -1) {
                rpc.setVisible(false);
                createNotification("you haven't chosen yet", new Node[]{
                        new CustomButton("proceed", () -> {
                            ViewMaster.btnSoundEffect();
                            unBlur();
                            rpc.setVisible(true);
                            notifStackPane.setVisible(false);
                        })
                });
            } else {
                int result = duelController.findPlayerToStart(numberToReturn);
                if (result == 0) {
                    rpc.setVisible(false);
                    createNotification("Equal", new Node[]{
                            new CustomButton("Try again", () -> {
                                ViewMaster.btnSoundEffect();
                                unBlur();
                                rpc.setVisible(true);
                                notifStackPane.setVisible(false);
                            })
                    });
                } else if (result == 1) {
                    stopSong();
                    ViewMaster.beginBattleSoundEffect();
                    rpc.setVisible(false);
                    createNotification("You Start The Game", new Node[]{
                    });
                    KeyFrame keyFrame = new KeyFrame(Duration.millis(3000), event ->
                            duelController.startAIDuel(ViewMaster.getUser(), round, 1));
                    Timeline timeline = new Timeline(keyFrame);
                    timeline.setCycleCount(1);
                    timeline.play();
                } else {
                    stopSong();
                    ViewMaster.beginBattleSoundEffect();
                    rpc.setVisible(false);
                    createNotification("AI Start The Game", new Node[]{
                    });
                    KeyFrame keyFrame = new KeyFrame(Duration.millis(3000), event ->
                            duelController.startAIDuel(ViewMaster.getUser(), round, 2));
                    Timeline timeline = new Timeline(keyFrame);
                    timeline.setCycleCount(1);
                    timeline.play();
                }
            }
        }));
        rpc.getChildren().addAll(box);
        rpc.setStyle("-fx-background-color:yellow");
        blur();
        rpc.setTranslateX(1280 - 350);
        rpc.setTranslateY(250);
        pane.getChildren().add(rpc);
    }

    private void stopSong() {
        Arrays.stream(ViewMaster.songs).forEach(MediaPlayer::stop);
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

    public GameView startGame(Player firstPlayer, Player secondPlayer, Player currentPlayer, int rounds)
            throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Game.fxml"));
        if (stage == null)
            stage = (Stage) (pane.getScene().getWindow());
        Scene scene = new Scene(loader.load());
        (stage).setScene(scene);
        ((GameView) loader.getController()).setup(firstPlayer, secondPlayer, currentPlayer, rounds , scene );
        return loader.getController();
    }
}
