package view.allmenu;


import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import view.Regex;
import view.SceneController;
import view.ViewMaster;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;

public class GameView {
    /*public static MediaPlayer gender;
    public AnchorPane rightPane;
    public StackPane notifStackPane;
    public AnchorPane tributeContainer;
    public TilePane tributeCardsPlace;
    public Label tributeNumber;
    public ImageView cancelTribute;
    public Button graveyardClose;
    public TilePane graveyardTilepane;
    public AnchorPane showGraveyardBack;
    public Label noCardInGraveyard;
    public Label ourPlayerLabel;
    public Label rivalPlayerLabel;
    private GameController gameController;
    public GridPane gridPane;
    public BorderPane motherPane;
    public StackPane centerPane;
    public StackPane rivalGraveyard;
    public StackPane ourField;
    public StackPane rivalField;
    public StackPane ourGraveyard;
    public AnchorPane leftPane;
    public GridPane leftGrid;
    public ImageView selectedCard;
    public ImageView rivalSelectedCard;
    public Circle ourHp;
    public Circle rivalHp;
    public Label rivalHpPoint;
    public Label ourHpPoint;
    public Text text;
    public Cell ourSelectedCell;
    public Cell rivalSelectedCell;
    public Cell ourSelectedSpell;
    public Cell rivalSelectedSpell;
    public Pane ourSelectedPane;
    public Pane rivalSelectedPane;
    public Player firstPlayer;
    public Player secondPlayer;
    public Player ourPlayer;
    public Player rivalPlayer;
    public HBox health;
    public HBox rivalHealth;
    public VBox vBox;
    public VBox buttonBox;
    public CustomSanButtons attack;
    public CustomSanButtons directAttack;
    public ProgressBar progressBar;
    public CustomSanButtons activate;
    private Image backImage;
    private boolean tributePhase = false;
    private boolean killOpponentMonsterPhase = false;
    private boolean isSummoning = false;
    private boolean heartBeatStarted = false;
    private int numberOfOpponentMonster = 0;
    private int numberOfOpponentMonsterNeeded = 0;
    private int numberOfTribute = 0;
    private AnimationTimer animationTimer;

    static {
        gender = new MediaPlayer(new Media(GameView.class.getResource("/gameMusic/gender.mp3").toExternalForm()));
    }

    public GameView() {

    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public void setup(Player firstPlayer, Player secondPlayer, Player currentPlayer, int rounds, Scene scene) {
        scene.setOnKeyPressed(event -> {
            if (event.isControlDown() && event.isShiftDown()) {
                if (event.getCode() == KeyCode.C) {
                    runCheats();
                }
            }
        });
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        ourPlayer = firstPlayer instanceof AIPlayer ? secondPlayer : firstPlayer;
        rivalPlayer = firstPlayer instanceof AIPlayer ? firstPlayer : secondPlayer;
        init();
        gameController = new GameController(this, firstPlayer, secondPlayer, currentPlayer, rounds);
        buttonBox.getChildren().addAll(buttonBoxNodes());
        ColorAdjust colorAdjust = new ColorAdjust();
        activate.setEffect(colorAdjust);
        ourPlayerLabel.setText("Our player : " + ourPlayer.getUser().getNickname());
        rivalPlayerLabel.setText("Rival player : " + ((AIPlayer) rivalPlayer).getNickname());
        ourPlayerLabel.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.ITALIC, 22));
        rivalPlayerLabel.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.ITALIC, 22));
        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (ourField.getChildren().isEmpty() && rivalField.getChildren().isEmpty())
                    changeCenterPanePic("/fields/normal.bmp");
                if (gameController.isAITurn()) {
                    gameController.setAITurn(false);
                    gameController.playAI();
                }
                if (ourPlayer.getLifePoint() <= 3000 && !heartBeatStarted) {
                    ViewMaster.heartbeatSoundEffect();
                    heartBeatStarted = true;
                }
                gameController.findWinner();
                rivalHpPoint.setText(String.valueOf(rivalPlayer.getLifePoint()));
                ourHpPoint.setText(String.valueOf(ourPlayer.getLifePoint()));
                Arrays.stream(new CustomSanButtons[]{attack, directAttack}).forEach(x -> {
                    final ColorAdjust colorAdjust = new ColorAdjust();
                    x.setEffect(colorAdjust);
                    if (gameController.getCurrentPhase() != Phase.BATTLE_PHASE) {
                        colorAdjust.setSaturation(-1);
                        x.setDisable(true);
                    } else {
                        colorAdjust.setSaturation(0);
                        x.setDisable(false);
                    }
                });
                setHpBar();
                if (gameController.getCurrentPhase() != Phase.MAIN_PHASE_1 &&
                        gameController.getCurrentPhase() != Phase.MAIN_PHASE_2) {
                    colorAdjust.setSaturation(-1);
                    activate.setDisable(true);
                } else {
                    colorAdjust.setSaturation(0);
                    activate.setDisable(false);
                }
            }
        };
        animationTimer.start();
        gender.setVolume(0.2);
        gender.setOnEndOfMedia(() -> gender.play());
        gender.play();
    }

    private void runCheats() {
        CustomSanButtons money = new CustomSanButtons("Money", () -> {
            gameController.moneyCheat(5000);
            ViewMaster.btnSoundEffect();
            notifStackPane.setVisible(false);
            deBlur();
        });
        CustomSanButtons hand = new CustomSanButtons("Draw Card", () -> {
            gameController.addCardToHand();
            ViewMaster.btnSoundEffect();
            notifStackPane.setVisible(false);
            deBlur();
        });
        CustomSanButtons winGame = new CustomSanButtons("Win Game", () -> {
            gameController.setDuelWinnerCheat(ourPlayer.getUser().getNickname());
            ViewMaster.btnSoundEffect();
            notifStackPane.setVisible(false);
            deBlur();
        });
        CustomSanButtons lp = new CustomSanButtons("Life Point", () -> {
            gameController.lifePointCheat(1000);
            ViewMaster.btnSoundEffect();
            notifStackPane.setVisible(false);
            deBlur();
        });
        CustomSanButtons close = new CustomSanButtons("Close", () -> {
            ViewMaster.btnSoundEffect();
            notifStackPane.setVisible(false);
            deBlur();
        });
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(money, hand, winGame, lp, close);
        hBox.setSpacing(5);
        Node[] nodes = new Node[]{hBox};
        createNotification("Choose Cheat", nodes);
    }

    private void setHpBar() {
        for (int i = 0; i < health.getChildren().size(); i++) {
            health.getChildren().get(i).setVisible((double) ourPlayer.getLifePoint() / 1000 >= i + 1);
        }
        for (int i = 0; i < rivalHealth.getChildren().size(); i++) {
            rivalHealth.getChildren().get(i).setVisible((double) rivalPlayer.getLifePoint() / 1000 >= i + 1);
        }
    }

    public void init() {
        URL url = getClass().getResource("/gamePics/back.jpg");
        backImage = new Image(url.toExternalForm());
        setupNotifStackPane();
        buttonBox = new VBox(0);
        buttonBox.setTranslateY(350);
        buttonBox.setTranslateX(50);
        rightPane.getChildren().add(buttonBox);
        initHp();
        centerPane.setAlignment(Pos.CENTER);
        leftPane.setStyle("-fx-background-image: url('/gamePics/1.png')" +
                ";-fx-background-size: cover,auto;-fx-background-repeat: no-repeat;");
        rightPane.setStyle("-fx-background-image: url('/gamePics/1.png')" +
                ";-fx-background-size: cover,auto;-fx-background-repeat: no-repeat;");
        initGridPanes();
        centerPane.setStyle("-fx-background-image:url('/fields/normal.bmp'); -fx-background-size: cover,auto;");
        rivalHpPoint.setText(String.valueOf(rivalPlayer.getLifePoint()));
        ourHpPoint.setText(String.valueOf(ourPlayer.getLifePoint()));
        health = new HBox();
        rivalHealth = new HBox();
        Arrays.stream(new HBox[]{rivalHealth, health}).forEach(x -> {
            x.setSpacing(2);
            for (int i = 0; i < 8; i++) {
                Rectangle rectangle = new Rectangle(10, 20, Color.RED);
                rectangle.setEffect(new DropShadow(5, Color.BLACK));
                x.getChildren().add(rectangle);
            }
            x.setStyle("-fx-background-color: black");
            x.setTranslateY(50);

        });
        rivalHealth.setTranslateX(110);
        health.setTranslateX(80);
        rightPane.getChildren().add(rivalHealth);
        leftPane.getChildren().add(health);
    }

    public void changeCenterPanePic(String url) {
        centerPane.setStyle("-fx-background-image:url('" + url + "'); -fx-background-size: cover,auto;");
    }

    private void setupNotifStackPane() {
        vBox = new VBox();
        vBox.setSpacing(30);
        notifStackPane = new StackPane();
        notifStackPane.getChildren().add(vBox);
        vBox.setPrefHeight(300);
        vBox.setPrefWidth(700);
        vBox.setMinHeight(300);
        vBox.setMinWidth(700);
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.setStyle("-fx-background-image: url('/gamePics/notif.jpg');");
        notifStackPane.setMinHeight(300);
        notifStackPane.setMinWidth(700);
        notifStackPane.setPrefHeight(300);
        notifStackPane.setPrefWidth(700);
        notifStackPane.setAlignment(Pos.CENTER);
        notifStackPane.setTranslateY(300);
        notifStackPane.setTranslateX(600);
        notifStackPane.setVisible(false);
        motherPane.getChildren().add(notifStackPane);
    }

    public void createNotification(String text, Node[] nodes) {
        blur();
        notifStackPane.setVisible(true);
        vBox.getChildren().clear();
        this.text = new Text(text);
        this.text.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.ITALIC, 22));
        this.text.setEffect(new Bloom(0.2));
        vBox.getChildren().addAll(new Text(""), this.text);
        vBox.getChildren().addAll(nodes);
    }

    public void printCurrentPhase() {
        CustomSanButtons[] custom = new CustomSanButtons[]{new CustomSanButtons("proceed", () -> {
            ViewMaster.btnSoundEffect();
            notifStackPane.setVisible(false);
            deBlur();
        })};
        createNotification(gameController.getCurrentPhase().getPhaseName(), custom);
    }

    private Node[] buttonBoxNodes() {
        activate = new CustomSanButtons("activate", () -> {
            ViewMaster.btnSoundEffect();
            if (!tributePhase && !killOpponentMonsterPhase)
                gameController.activateSpell();
        });
        attack = new CustomSanButtons("attack", () -> {
            ViewMaster.attackSoundEffect();
            attack();
        });
        directAttack = new CustomSanButtons("direct attack", () -> {
            ViewMaster.attackSoundEffect();
            directAttack();
        });
        return new Node[]{activate, attack
                , directAttack, new CustomSanButtons("next phase", () -> {
            ViewMaster.btnSoundEffect();
            if (!tributePhase && !killOpponentMonsterPhase)
                gameController.nextPhase();
        })
                , new CustomSanButtons("surrender", () -> {
            ViewMaster.btnSoundEffect();
            gameController.surrender();
        })};
    }

    public boolean isTributePhase() {
        return tributePhase;
    }

    public void setTributePhase(boolean tributePhase) {
        this.tributePhase = tributePhase;
    }

    private void initGridPanes() {
        centerPane(ourPlayer, rivalPlayer);
        leftPane();
    }

    private void centerPane(Player ourPlayer, Player rivalPlayer) {
        centerGrid(ourPlayer, rivalPlayer);
        leftGrid(ourPlayer);
        leftPane();

    }

    private void leftPane() {
        leftGrid.setVgap(3.3333);
        leftGrid.setHgap(3.3333);
        fourOtherCards();
        gridPane.setTranslateX(13.3333);
        gridPane.setTranslateY(33.3333);
        gridPane.setHgap(6.6666);
        gridPane.setVgap(20);
    }

    private void centerGrid(Player ourPlayer, Player rivalPlayer) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                StackPane stackPane = new StackPane();
                gridPaneSetup(null, stackPane);
                if (i < 2) {
                    if (i == 0)
                        setSpell(rivalPlayer, j, stackPane);
                    if (i == 1)
                        rivalMonsterCellSetup(rivalPlayer, j, stackPane);
                } else {
                    if (i == 2)
                        ourMonsterCellSetups(ourPlayer, j, stackPane);
                    if (i == 3)
                        setSpell(ourPlayer, j, stackPane);
                }
                gridPane.add(stackPane, j, i);
            }
        }
    }

    private void setSpell(Player player, int j, StackPane stackPane) {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000));
        player.getBoard().getSpellOrTrap()[j].setStackPane(stackPane);
        DropShadow shadow = new DropShadow(0, 0f, 0f, Color.GOLD);
        stackPaneEffect(stackPane, shadow);
        stackPane.setOnMouseClicked(e -> {
            ViewMaster.btnSoundEffect();
            Arrays.stream(player.getBoard().getSpellOrTrap()).map(Cell::getPicture).filter(Objects::nonNull)
                    .forEach(a -> {
                        ((DropShadow) ((Bloom) a.getEffect()).getInput()).setRadius(0);
                    });
            if (shadow.getRadius() == 0) {
                shadow.setRadius(16);
            } else
                shadow.setRadius(0);
            fadeTransition.setFromValue(0);
            fadeTransition.setToValue(1);
            if (player == rivalPlayer) {
                rivalSelectedSpell = Arrays.stream(rivalPlayer.getBoard().getSpellOrTrap())
                        .filter(Objects::nonNull).filter(x -> x.getPicture() == stackPane).findFirst().get();
                fadeTransition.setNode(rivalSelectedCard);
                rivalSelectedCard.setImage(rivalSelectedSpell.getImage());
            } else {
                ourSelectedSpell = Arrays.stream(ourPlayer.getBoard().getSpellOrTrap())
                        .filter(Objects::nonNull).filter(x -> x.getPicture() == stackPane).findFirst().get();
                fadeTransition.setNode(selectedCard);
                selectedCard.setImage(ourSelectedSpell.getCard().getImage());
            }
            fadeTransition.play();
        });
    }

    private void rivalMonsterCellSetup(Player rivalPlayer, int j, StackPane stackPane) {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000));
        rivalPlayer.getBoard().getMonsters()[j].setStackPane(stackPane);
        DropShadow shadow = new DropShadow(0, 0f, 0f, Color.CRIMSON);
        stackPaneEffect(stackPane, shadow);
        stackPane.setOnMouseClicked(e -> {
            ViewMaster.btnSoundEffect();
            if (shadow.getRadius() == 0) {
                Arrays.stream(rivalPlayer.getBoard().getMonsters()).map(Cell::getPicture).forEach(a -> {
                    ((DropShadow) ((Bloom) a.getEffect()).getInput()).setRadius(0);
                });
                shadow.setRadius(16);
            } else
                shadow.setRadius(0);
            rivalSelectedPane = stackPane;
            rivalSelectedCell = Arrays.stream(rivalPlayer.getBoard().getMonsters())
                    .filter(Objects::nonNull).filter(x -> x.getPicture() == stackPane).findFirst().get();
            fadeTransition.setFromValue(0);
            fadeTransition.setToValue(1);
            fadeTransition.setNode(rivalSelectedCard);
            rivalSelectedCard.setImage(rivalSelectedCell.getImage());
            fadeTransition.play();
            if (killOpponentMonsterPhase) {
                if (rivalSelectedCell.getCard() != null) {
                    rivalPlayer.getBoard().getGraveyard().addCard(rivalSelectedCell.getCard());
                    numberOfOpponentMonster++;
                    if (numberOfOpponentMonster == numberOfOpponentMonsterNeeded) {
                        numberOfOpponentMonster = 0;
                        numberOfOpponentMonsterNeeded = 0;
                        killOpponentMonsterPhase = false;
                    }
                }
            }
        });
        stackPane.rotateProperty().set(180);
    }

    private void stackPaneEffect(StackPane stackPane, DropShadow shadow) {
        Bloom bloom = new Bloom(1);
        bloom.setInput(shadow);
        stackPane.setEffect(bloom);
        stackPane.setOnMouseEntered(e -> {
            bloom.setThreshold(0.75);
        });
        stackPane.setOnMouseExited(e -> {
            bloom.setThreshold(1);
        });
    }

    private void ourMonsterCellSetups(Player ourPlayer, int j, StackPane stackPane) {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000));
        ourPlayer.getBoard().getMonsters()[j].setStackPane(stackPane);
        final int x = j;
        DropShadow shadow = new DropShadow(0, 0f, 0f, Color.GREEN);
        stackPaneEffect(stackPane, shadow);
        stackPane.setOnMouseClicked(event -> {
            ViewMaster.btnSoundEffect();
            Arrays.stream(ourPlayer.getBoard().getMonsters()).map(Cell::getPicture).forEach(a -> {
                ((DropShadow) ((Bloom) a.getEffect()).getInput()).setRadius(0);
            });
            if (shadow.getRadius() == 0) {
                shadow.setRadius(16);
            } else
                shadow.setRadius(0);
            if (event.getButton() == MouseButton.PRIMARY)
                gameController.getCurrentPlayer().setSelectedCard(ourPlayer.getBoard().getMonsters()[x].getCard());
            ourSelectedPane = stackPane;
            ourSelectedCell = Arrays.stream(ourPlayer.getBoard().getMonsters())
                    .filter(Objects::nonNull).filter(a -> a.getPicture() == stackPane).findFirst()
                    .get();
            fadeTransition.setFromValue(0);
            fadeTransition.setToValue(1);
            fadeTransition.setNode(selectedCard);
            if (ourSelectedCell != null && ourSelectedCell.getCard() != null)
                selectedCard.setImage(ourSelectedCell.getCard().getImage());
            fadeTransition.play();
            if (tributePhase) {
                if (numberOfTribute < (gameController).getNumberOfTributeNeeded()
                        && ourPlayer.getBoard().getMonsters()[x].getCard() != null) {
                    gameController.tribute(stackPane, x);
                    numberOfTribute++;
                    if (numberOfTribute == (gameController).getNumberOfTributeNeeded()) {
                        numberOfTribute = 0;
                        tributePhase = false;
                        gameController.summonWithTribute();
                    }
                }
            }
            if (event.getButton() == MouseButton.MIDDLE)
                if (gameController
                        .getCurrentPlayer().getSelectedCard() == ourPlayer.getBoard().getMonsters()[x].getCard())
                    changePosition();
            if (event.getButton() == MouseButton.SECONDARY) {
                if (gameController
                        .getCurrentPlayer().getSelectedCard() == ourPlayer.getBoard().getMonsters()[x].getCard())
                    flipSummon();
            }
        });
    }

    private void fourOtherCards() {
        ourGraveyard = new StackPane();
        setupGraveyard(ourGraveyard, ourPlayer);
        rivalGraveyard = new StackPane();
        setupGraveyard(rivalGraveyard, rivalPlayer);
        ourField = new StackPane();
        rivalField = new StackPane();
        rivalField.rotateProperty().set(180);
        rivalGraveyard.rotateProperty().set(180);
        VBox our = new VBox(20, rivalGraveyard, ourField);
        VBox rivals = new VBox(20, rivalField, ourGraveyard);
        Arrays.stream(new VBox[]{our, rivals}).forEach(e -> {
            e.setPrefWidth(93.3333);
            e.setMaxHeight(126.6666);
            e.setMaxWidth(93.3333);
            e.setPrefHeight(126.6666);
        });
        ourPlayer.getBoard().getFieldSpell().setStackPane(ourField);
        rivalPlayer.getBoard().getFieldSpell().setStackPane(rivalField);
        ourPlayer.getBoard().getGraveyard().setStackPane(ourGraveyard);
        rivalPlayer.getBoard().getGraveyard().setStackPane(rivalGraveyard);
        Arrays.stream(new StackPane[]{ourField, rivalField, ourGraveyard, rivalGraveyard}).forEach(x -> {
            DropShadow shadow = new DropShadow(0, 0f, 0f, Color.BLACK);
            stackPaneEffect(x, shadow);
            x.setPrefWidth(93.3333);
            x.setPrefHeight(126.6666);
            ImageView cardImages = new ImageView();
            setEffectsCardImages(cardImages);
            cardImages.setFitWidth(93.3333);
            cardImages.setFitHeight(126.6666);
            x.getChildren().add(cardImages);
            x.setOnMouseClicked(a -> {
                if (shadow.getRadius() == 0) {
                    shadow.setRadius(16);
                } else
                    shadow.setRadius(0);
            });
        });
        our.setTranslateX(-300);
        our.setTranslateY(26.666);
        rivals.setTranslateX(313.3333);
        rivals.setTranslateY(40);
        centerPane.getChildren().addAll(our, rivals);
    }

    private void setupGraveyard(StackPane graveyard, Player player) {
        ImageView imageView = new ImageView(backImage);
        imageView.setOnMouseClicked(event -> {
            ViewMaster.btnSoundEffect();
            openGraveyard(player);
        });
        imageView.setFitWidth(90);
        imageView.setFitHeight(120);
        Label label = new Label();
        label.setOnMouseClicked(event -> {
            ViewMaster.btnSoundEffect();
            openGraveyard(player);
        });
        label.setLayoutX(37.5);
        label.setLayoutY(40);
        label.setStyle("-fx-font-family: 'Comic Sans MS'; -fx-font-size: 25px;-fx-text-fill: white");
        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                label.setText(String.valueOf(player.getBoard().getGraveyard().getAllCards().size()));
            }
        };
        animationTimer.start();
        Group group = new Group();
        group.getChildren().addAll(imageView, label);
        graveyard.getChildren().add(group);
    }

    public void openGraveyard(Player player) {
        ourGraveyard.setDisable(true);
        rivalGraveyard.setDisable(true);
        ourField.setDisable(true);
        rivalField.setDisable(true);
        vBox.setDisable(true);
        leftPane.setDisable(true);
        gridPane.setDisable(true);
        tributeContainer.setDisable(true);
        rightPane.setDisable(true);
        GaussianBlur blur = new GaussianBlur(15);
        leftPane.setEffect(blur);
        gridPane.setEffect(blur);
        tributeContainer.setEffect(blur);
        rightPane.setEffect(blur);
        vBox.setEffect(blur);
        ourGraveyard.setEffect(blur);
        rivalGraveyard.setEffect(blur);
        ourField.setEffect(blur);
        rivalField.setEffect(blur);
        showGraveyardBack.setVisible(true);
        showGraveyardBack.setDisable(false);
        graveyardTilepane.getChildren().clear();
        if (player.getBoard().getGraveyard().getAllCards().size() == 0) {
            noCardInGraveyard.setVisible(true);
        } else {
            for (Card card : player.getBoard().getGraveyard().getAllCards()) {
                ImageView imageView = new ImageView(card.getImage());
                imageView.setFitWidth(90);
                imageView.setFitHeight(120);
                graveyardTilepane.getChildren().add(imageView);
            }
        }
    }

    private void setEffectsCardImages(ImageView view) {
        Bloom glow = new Bloom();
        view.setOnMouseEntered(event -> {
            glow.setThreshold(0.5);
            view.setEffect(glow);
        });
        view.setOnMouseExited(event -> {
            view.setEffect(null);
        });
        view.setOnMouseClicked(e -> {
            ViewMaster.btnSoundEffect();
            FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000));
            if (view.getParent().getRotate() != 180) {
                fadeTransition.setFromValue(0);
                fadeTransition.setToValue(1);
                fadeTransition.setNode(selectedCard);
                fadeTransition.play();
                selectedCard.setImage(view.getImage());
            } else {
                fadeTransition.setFromValue(0);
                fadeTransition.setToValue(1);
                fadeTransition.setNode(rivalSelectedCard);
                fadeTransition.play();
                rivalSelectedCard.setImage(view.getImage());
            }
        });
    }

    private void changePosition() {
        try {
            gameController.changeSet();
        } catch (Exception e) {
            okNotif(e.getMessage(), "proceed");
            System.out.println(e.getMessage());
        }
    }

    private void flipSummon() {
        try {
            gameController.flipSummon();
        } catch (ManEaterBugException e) {
            createNotification(e.getMessage(), new Node[]{
                    new CustomSanButtons("YES", () -> {
                        ViewMaster.btnSoundEffect();
                        killOpponentMonsterPhase = true;
                        numberOfOpponentMonsterNeeded = 1;
                        deBlur();
                        notifStackPane.setVisible(false);
                    }), new CustomSanButtons("NO", () -> {
                ViewMaster.btnSoundEffect();
                notifStackPane.setVisible(false);
                deBlur();
            })
            });
        } catch (Exception e) {
            okNotif(e.getMessage(), "proceed");
            System.out.println(e.getMessage());
        }
    }

    private void leftGrid(Player ourPlayer) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                StackPane stackPane = new StackPane();
                gridPaneSetup(null, stackPane);
                stackPane.setOnMouseClicked(e -> {
                    if (!tributePhase && !killOpponentMonsterPhase) {
                        if (e.getButton() == MouseButton.PRIMARY)
                            if (gameController.getCurrentPlayer().getSelectedCard() != null
                                    && gameController.getCurrentPlayer().getSelectedCard()
                                    == gameController.getCurrentPlayer()
                                    .getCardsInHand().get(gameController.getCurrentPlayer()
                                            .getCardsInHandImage().indexOf(stackPane))) {
                                gameController.getCurrentPlayer()
                                        .setSelectedCard(gameController.getCurrentPlayer()
                                                .getCardsInHand().get(gameController.getCurrentPlayer()
                                                        .getCardsInHandImage().indexOf(stackPane)));
                                if (gameController.getCurrentPlayer().getSelectedCard() instanceof Monster)
                                    monsterSummon();
                                else if (gameController.getCurrentPlayer().getSelectedCard() instanceof Spell)
                                    gameController.activateSpell();
//                            gameController.normalSummon((Monster) gameController.getCurrentPlayer().getSelectedCard(), AttackOrDefense.ATTACK);
                            } else
                                gameController.getCurrentPlayer()
                                        .setSelectedCard(gameController.getCurrentPlayer()
                                                .getCardsInHand().get(gameController.getCurrentPlayer()
                                                        .getCardsInHandImage().indexOf(stackPane)));
                        if (e.getButton() == MouseButton.SECONDARY)
                            if (gameController.getCurrentPlayer().getSelectedCard() != null
                                    && gameController.getCurrentPlayer().getSelectedCard()
                                    == gameController.getCurrentPlayer()
                                    .getCardsInHand().get(gameController.getCurrentPlayer()
                                            .getCardsInHandImage().indexOf(stackPane))) {
                                setMonsterSpellTrap(gameController.getCurrentPlayer().getSelectedCard());
                            }
                    }
                });
                leftGrid.add(stackPane, j, i);
                ourPlayer.getCardsInHandImage().add(stackPane);
            }
        }
    }

    private void setMonsterSpellTrap(Card selectedCard) {
        try {
            gameController.set();
        } catch (Exception e) {
            okNotif(e.getMessage(), "proceed");
            System.out.println(e.getMessage());
        }
    }

    private void monsterSummon() {
        try {
            gameController.checksBeforeSummon();
        } catch (KingBarbarosException e) {
            createNotification(e.getMessage(), new Node[]{
                    new CustomSanButtons("YES", () -> {
                        ViewMaster.btnSoundEffect();
                        gameController.setNumberOfTributeNeeded(3);
                        tributePhase = true;
                        numberOfTribute = 0;
                        notifStackPane.setVisible(false);
                        deBlur();
                    }), new CustomSanButtons("NO", () -> {
                ViewMaster.btnSoundEffect();
                gameController.barbarosNormalSummon();
                notifStackPane.setVisible(false);
                deBlur();
            })
            });
            notifStackPane.setVisible(true);
        } catch (Exception e) {
            okNotif(e.getMessage(), "proceed");
            notifStackPane.setVisible(true);
            System.out.println(e.getMessage());
        }
    }


    private void initHp() {
        ImagePattern hp = new ImagePattern(new Image("/gamePics/hp3.jpg"));
        Arrays.stream(new Circle[]{ourHp, rivalHp}).forEach(e -> {
            e.setFill(hp);
            e.setStroke(Color.RED);
            e.setStrokeWidth(3.3333);
            final RotateTransition[] rotation = new RotateTransition[1];
            setHpRotation(rotation, e);
        });
    }

    private void setHpRotation(RotateTransition[] rotation, Circle hp) {
        hp.setOnMouseEntered(e -> {
            rotation[0] = new RotateTransition(Duration.millis(2000), hp);
            rotation[0].setFromAngle(-30);
            rotation[0].setToAngle(30);
            rotation[0].setAutoReverse(true);
            rotation[0].setCycleCount(Animation.INDEFINITE);
            rotation[0].play();
        });
        hp.setOnMouseExited(e -> {
            hp.setRotate(0);
            rotation[0].stop();
        });
    }

    public void gridPaneSetup(Image background, StackPane stackPane) {
        stackPane.setPrefWidth(93.3333);
        stackPane.setPrefHeight(126.6666);
        ImageView cardImages = new ImageView(background);
        setEffectsCardImages(cardImages);
        cardImages.setFitWidth(93.3333);
        cardImages.setFitHeight(126.6666);
        stackPane.getChildren().add(cardImages);
    }


    public GameController getGameController() {
        return gameController;
    }

    public void run(String command) {
        gameController.findWinner();
    }

    private void handCheat() {
        gameController.addCardToHand();
    }

    private void setWinner(String command) {
        String duelWinner = Regex.getInputMatcher(command, "duel set-winner (\\w+)").group(1);
        gameController.setDuelWinnerCheat(duelWinner);
    }

    private void increaseLp(String command) {
        String amountStr = Regex.getInputMatcher(command, "increase --LP (\\d+)").group(1);
        int amount = Integer.parseInt(amountStr);
        gameController.lifePointCheat(amount);
    }

    private void increaseMoney(String command) {
        String amountString = Regex.getInputMatcher(command, "increase --money (\\d+)").group(1);
        int amount = Integer.parseInt(amountString);
        gameController.moneyCheat(amount);
    }

  *//*  private void changeSet(Matcher inputMatcher) {
        inputMatcher.find();
        String position = inputMatcher.group("position");
        gameController.changeSet(position);
    }*//*

    private void directAttack() {
        gameController.directAttack(false);
    }

    private void attack() {
        if (ourSelectedCell == null || rivalSelectedCell == null) {
            okNotif("you haven't selected yet!", "Ok");
            return;
        }
        gameController.attack(getCellNumber(rivalSelectedCell));
    }

    public int getCellNumber(Cell selected) {
        int num = 0;
        for (int i = 0; i < rivalPlayer.getBoard().getMonsters().length; i++) {
            if (selected == rivalPlayer.getBoard().getMonsters()[i]) {
                num = i;
                break;
            }
        }
        if (num == 0)
            return 5;
        if (num == 1)
            return 3;
        if (num == 2)
            return 1;
        if (num == 3)
            return 2;
        if (num == 4)
            return 4;
        return -19;//////////////this is bullshit
    }

    private void printNotThoseMoves() {
        System.out.println("it’s not your turn to play this kind of moves");
    }

    public void printMap() {
        Player currentPlayer = gameController.getCurrentPlayer();
        Player rivalPlayer = gameController.getCurrentPlayer().getRivalPlayer();
        StringBuilder map = new StringBuilder();
        addRivalMap(rivalPlayer, map);
        addPlayerMap(currentPlayer, map);
        System.out.println(map.toString());
    }

    private void addPlayerMap(Player currentPlayer, StringBuilder map) {
        map.append("\n--------------------------\n");
        if (currentPlayer.getBoard().getFieldSpell().getCard() == null)
            map.append("E" + "\t\t\t\t\t\t").append(currentPlayer.getBoard().getGraveyard().getAllCards().size()).append("\n");
        else
            map.append("O" + "\t\t\t\t\t\t").append(currentPlayer.getBoard().getGraveyard().getAllCards().size()).append("\n");
        for (int i = 0; i < 5; i++) {
            addMonsterToMap(currentPlayer, map, i);
        }
        map.append("\n");
        for (int i = 0; i < 5; i++) {
            addSpellToMap(currentPlayer, map, i);
        }
        map.append("\n");
        map.append("\t\t\t\t\t\t").append(currentPlayer.getBoard().getDeck().getAllCardsInMainDeck().size()).append("\n");
        for (int i = 0; i < currentPlayer.getCardsInHand().size(); i++) {
            map.append("C\t");
        }
        map.append("\n");
        if (currentPlayer instanceof AIPlayer)
            map.append(((AIPlayer) currentPlayer).getNickname()).append(":").append(currentPlayer.getLifePoint());
        else
            map.append(currentPlayer.getUser().getNickname()).append(":").append(currentPlayer.getLifePoint());
    }

    private void addRivalMap(Player rivalPlayer, StringBuilder map) {
        if (rivalPlayer instanceof AIPlayer)
            map.append(((AIPlayer) rivalPlayer).getNickname()).append(":").append(rivalPlayer.getLifePoint()).append("\n");
        else
            map.append(rivalPlayer.getUser().getNickname()).append(":").append(rivalPlayer.getLifePoint()).append("\n");
        for (int i = 0; i < rivalPlayer.getCardsInHand().size(); i++) {
            map.append("\tC");
        }
        map.append("\n");
        map.append(rivalPlayer.getBoard().getDeck().getAllCardsInMainDeck().size()).append("\n");
        for (int i = 4; i >= 0; i--) {
            addSpellToMap(rivalPlayer, map, i);
        }
        map.append("\n");
        for (int i = 4; i >= 0; i--) {
            addMonsterToMap(rivalPlayer, map, i);
        }
        map.append("\n");
        map.append(rivalPlayer.getBoard().getGraveyard().getAllCards().size()).append("\t\t\t\t\t\t");
        if (rivalPlayer.getBoard().getFieldSpell().getCard() == null) map.append("E\n");
        else map.append("O\n");
    }

    private void addMonsterToMap(Player player, StringBuilder map, int i) {
        map.append("\t");
        Monster monster = (Monster) player.getBoard().getMonsters()[i].getCard();
        if (monster == null)
            map.append("E");
        else if (monster.getAttackOrDefense() == AttackOrDefense.ATTACK)
            map.append("OO");
        else if (monster.getAttackOrDefense() == AttackOrDefense.DEFENSE) {
            if (monster.getFace() == Face.UP)
                map.append("DO");
            else
                map.append("DH");
        }
    }

    private void addSpellToMap(Player player, StringBuilder map, int i) {
        map.append("\t");
        if (player.getBoard().getSpellOrTrap()[i].getCard() == null)
            map.append("E");
        else if (player.getBoard().getSpellOrTrap()[i].getCard().getFace() == Face.UP)
            map.append("O");
        else if (player.getBoard().getSpellOrTrap()[i].getCard().getFace() == Face.DOWN)
            map.append("H");
    }

    public void selectCard(String command) {
        if (command.matches(Regex.DESELECT)) {
            gameController.deselectCard();
            return;
        }

        Matcher opponentWithFieldMatcher = Regex.getInputMatcher(command, Regex.OPPONENT_WITH_FIELD);
        Matcher opponentMatcher = Regex.getInputMatcher(command, Regex.OPPONENT);
        if (opponentWithFieldMatcher.find(0)) {
            Matcher monsterMatcher = Regex.getInputMatcher(command, Regex.OPPONENT_MONSTER);
            Matcher spellMatcher = Regex.getInputMatcher(command, Regex.OPPONENT_SPELL);
            if (monsterMatcher.find()) {
                int cardAddress = Integer.parseInt(opponentWithFieldMatcher.group("cardAddress"));
                gameController.selectOpponentMonster(cardAddress);
            } else if (spellMatcher.find()) {
                int cardAddress = Integer.parseInt(opponentWithFieldMatcher.group("cardAddress"));
                gameController.selectOpponentSpellOrTrap(cardAddress);
            }
        } else if (opponentMatcher.find(0)) {
            Matcher fieldMatcher = Regex.getInputMatcher(command, Regex.FIELD);
            if (fieldMatcher.find()) {
                gameController.selectOpponentFieldCard();
            }
        } else {
            Matcher monsterMatcher = Regex.getInputMatcher(command, Regex.PLAYER_MONSTER);
            Matcher spellMatcher = Regex.getInputMatcher(command, Regex.PLAYER_SPELL);
            Matcher handMatcher = Regex.getInputMatcher(command, Regex.PLAYER_HAND);
            Matcher fieldMatcher = Regex.getInputMatcher(command, Regex.FIELD);
            if (monsterMatcher.find()) {
                int cardAddress = Integer.parseInt(monsterMatcher.group("cardAddress"));
                gameController.selectPlayerMonster(cardAddress);
            } else if (spellMatcher.find()) {
                int cardAddress = Integer.parseInt(spellMatcher.group("cardAddress"));
                gameController.selectPlayerSpellOrTrap(cardAddress);
            } else if (handMatcher.find()) {
                int cardAddress = Integer.parseInt(handMatcher.group("cardAddress"));
                gameController.selectPlayerHandCard(cardAddress);
            } else if (fieldMatcher.find()) {
                if (command.matches(Regex.FIELD_SELECT))
                    gameController.selectPlayerFieldCard();
            }
        }
    }

    public void printCardDeselected() {
        System.out.println("card deselected");
    }

    public void printNoCardSelected() {
        okNotif("no card is selected yet", "Ok");
    }


    public void printCardSelected() {
        System.out.println("card selected");
    }

    public void printNotFoundCard() {
        System.out.println("no card found in the given position");
    }

    public void printAddedNewCard(Card card) {
        System.out.println("new card added to the hand : " + card.getCardNameInGame());
    }

    public void printInvalidSelection() {
        System.out.println("invalid selection");
    }

    public void printCantAttack() {
        okNotif("you can’t attack with this card", "Ok");
    }

    public void printWrongPhase() {
        okNotif("you can’t do this action in this phase", "Ok");
    }

    public void printAlreadyAttacked() {
        okNotif("this card already attacked", "Ok");
    }

    public void printNoCardToAttack() {
        System.out.println("there is no card to attack here");
    }

    public void printOpponentMonsterDestroyed(int attackDifference) {
        createNotification("your opponent’s monster is destroyed " +
                "and your opponent receives " + attackDifference + " battle damage", new Node[]{
                new CustomSanButtons("Ok", () -> {
                    ViewMaster.btnSoundEffect();
                    notifStackPane.setVisible(false);
                    deBlur();
                })
        });
    }

    public void printBothMonstersDestroyed() {
        okNotif("both you and your opponent monster cards" +
                " are destroyed and no one receives damage", "Ok");
    }

    public void printYourCardIsDestroyed(int attackDifference) {
        createNotification("Your monster card is destroyed " +
                "and you received " + attackDifference + " battle damage", new Node[]{
                new CustomSanButtons("Ok", () -> {
                    ViewMaster.btnSoundEffect();
                    notifStackPane.setVisible(false);
                    deBlur();
                })
        });
    }

    public void printNoCardDestroyed() {
        okNotif("no card is destroyed", "Ok");
    }

    public void printDefensePositionDestroyed() {
        okNotif("the defense position monster is destroyed", "Ok");
    }

    public void printNoCardDestroyedYouReceivedDamage(int attackDifference) {
        createNotification("no card is destroyed and you" +
                " received " + attackDifference + " battle damage", new Node[]{
                new CustomSanButtons("Ok", () -> {
                    ViewMaster.btnSoundEffect();
                    notifStackPane.setVisible(false);
                    deBlur();
                })
        });
        rivalHpPoint.setText(String.valueOf(rivalPlayer.getLifePoint()));
        ourHpPoint.setText(String.valueOf(ourPlayer.getLifePoint()));
    }

    public void printDefensePositionDestroyedHidden(String rivalMonsterName) {
        printOpponentCardsName(rivalMonsterName);
        printDefensePositionDestroyed();
    }

    public void printNoCardDestroyedHidden(String rivalMonsterName) {
        printOpponentCardsName(rivalMonsterName);
        printNoCardDestroyed();
    }

    public void printNoCardDestroyedYouReceivedDamageHidden(int attackDifference, String rivalMonsterName) {
        printOpponentCardsName(rivalMonsterName);
        printNoCardDestroyedYouReceivedDamage(attackDifference);
    }

    public void printOpponentCardsName(String name) {
        createNotification("opponent’s monster card was " + name, new Node[]{
                new CustomSanButtons("Ok", () -> {
                    notifStackPane.setVisible(false);
                    deBlur();
                })
        });
    }

    public void printYourOpponentReceivesDamage(int attackNum) {
        createNotification("your opponent receives " + attackNum + " battle damage", new Node[]{
                new CustomSanButtons("Ok", () -> {
                    ViewMaster.btnSoundEffect();
                    notifStackPane.setVisible(false);
                    deBlur();
                })
        });
    }

    public void printCantSummon() {
        okNotif("you can’t summon this card", "Ok");
    }

    public void printNotInMainPhase() {
        System.out.println("action not allowed in this phase");
    }

    public void printMonsterZoneFull() {
        okNotif("monster card zone is full", "Ok");
    }

    public void printAlreadySetOrSummon() {
        okNotif("you already summoned/set on this turn", "Ok");
    }

    public void printSummonSuccessfully() {
        okNotif("summoned successfully", "Ok");
    }

    public void printNoMonsterOnThisAddress() {
        okNotif("there no monsters one this address", "Ok");
    }

    public void printThereArentEnoughMonsterForTribute() {
        System.out.println("there are not enough cards for tribute");
    }

    public void printNoMonsterInOneOfThisAddress() {
        System.out.println("there no monsters on one of this addresses");
    }

    public void getTribute() {
        System.out.println("enter tribute number: ");
        try {
            int number = ViewMaster.scanner.nextInt();
            run("select --monster " + number);
        } catch (Exception e) {
        }
    }

    public void printCantSet() {
        okNotif("you can’t set this card", "Ok");
    }

    public void printSetSuccessfully() {
        okNotif("set successfully", "Ok");
    }

    public void printCantChangePosition() {
        System.out.println("you can’t change this card position");
    }

    public void printAlreadyInWantedPosition() {
        System.out.println("this card is already in the wanted position");
    }

    public void printAlreadyChangePositionInThisTurn() {
        System.out.println("you already changed this card position in this turn");
    }

    public void printChangeSetSuccessfully() {
        System.out.println("monster card position changed successfully");
    }

    public void printCantFlipSummon() {
        System.out.println("you can’t flip summon this card");
    }


    private void blur() {
        leftPane.setOpacity(0.3);
        leftPane.setDisable(true);
        rightPane.setDisable(true);
        rightPane.setOpacity(0.3);
        centerPane.setOpacity(0.3);
        centerPane.setDisable(true);
    }

    private void deBlur() {
        leftPane.setOpacity(1);
        leftPane.setDisable(false);
        rightPane.setDisable(false);
        rightPane.setOpacity(1);
        centerPane.setOpacity(1);
        centerPane.setDisable(false);
    }


    public void printUserWonWholeGame(String username, int winnerWonRounds, int loserWonRounds) {
        System.out.println(username + " won the whole game with score: " + winnerWonRounds + "-" + loserWonRounds);
        animationTimer.stop();
        gender.stop();
        ViewMaster.startSong();
        createNotification(username + " won the whole game with " +
                        "\nscore: " + winnerWonRounds + "-" + loserWonRounds,
                new Node[]{new CustomSanButtons("Main Menu", () -> {
                    ViewMaster.btnSoundEffect();
                    try {
                        Stage stage = ((Stage) notifStackPane.getScene().getWindow());
                        System.out.println(stage);
                        SceneController.startMainMenu((Stage) notifStackPane.getScene().getWindow());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                })});
    }

    public void printUserWonSingleGame(String username, int winnerWonRounds, int loserWonRounds) {

        animationTimer.stop();
        gender.stop();
        ViewMaster.startSong();
        System.out.println(username + " won the game and the score is:" + winnerWonRounds + "-" + loserWonRounds);
        createNotification(username + " won the game and " +
                        "\nthe score is:" + winnerWonRounds + "-" + loserWonRounds,
                new Node[]{new CustomSanButtons("Main Menu", () -> {
                    ViewMaster.btnSoundEffect();
                    try {
                        SceneController.startMainMenu((Stage) notifStackPane.getScene().getWindow());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                })});
    }

    public void printWhoseTurn() {
        if (gameController.getCurrentPlayer() instanceof AIPlayer) {
            createNotification("Its " + ((AIPlayer) gameController.getCurrentPlayer())
                    .getNickname() + "’s turn", new Node[]{
                    new CustomSanButtons("Ok", () -> {
                        ViewMaster.btnSoundEffect();
                        notifStackPane.setVisible(false);
                        deBlur();
                    })
            });
        } else {
            createNotification("Its " + gameController.getCurrentPlayer().getUser()
                    .getNickname() + "’s turn", new Node[]{
                    new CustomSanButtons("Ok", () -> {
                        ViewMaster.btnSoundEffect();
                        notifStackPane.setVisible(false);
                        deBlur();
                    })
            });
        }

        notifStackPane.setVisible(true);
        System.out.println();
    }

    public void showCard(Card card) {
        System.out.println(card.toString());
    }

    public void printCardInvisible() {
        System.out.println("card is not visible");
    }

    public void printAttackDisruptedByTaxchanger() {
        System.out.println("Attack Denied By Taxchanger");
    }

    public void printNoCyberseWithAbility() {
        System.out.println("Cant summon Cyberse with ability");
    }

    public void printCantAttackBecauseOfMessenger() {
        System.out.println("Cant attack : Messenger of peace active");
    }

    public void printInvalidCyberseName() {
        System.out.println("invalid Cyberse Name");
    }

    public void printFlipSummonSuccessfully() {
        System.out.println("flip summoned successfully");
    }

    public boolean doesRivalWantCyberse() {
        String answer = ViewMaster.scanner.nextLine();
        if (answer.equals("Yes"))
            return true;
        else if (answer.equals("No"))
            return false;
        System.out.println("invalid answer");
        return false;
    }

    public String getAnswer() {
        return ViewMaster.scanner.nextLine();
    }


    public String getPlace() {
        return ViewMaster.scanner.nextLine();
    }


    public void printInvalidLocation() {
        System.out.println("invalid location");
    }

    public void printDoesntContainCard(String place, String monster) {
        System.out.println(place + " doesnt contain " + monster);
    }

    public void printCantAttackMonster() {
        System.out.println("Cant attack this monster!");
    }

    public void printNoCardDestroyedRivalReceivedDamage(int attackDifference) {
        createNotification("no card is destroyed and rival" +
                " received " + attackDifference + " battle damage", new Node[]{
                new CustomSanButtons("Ok", () -> {
                    ViewMaster.btnSoundEffect();
                    notifStackPane.setVisible(false);
                    deBlur();
                })
        });

    }

    public void printYouReceivedDamage(int amount) {
        createNotification("you received " + amount + " damage", new Node[]{
                new CustomSanButtons("Ok", () -> {
                    ViewMaster.btnSoundEffect();
                    notifStackPane.setVisible(false);
                    deBlur();
                })
        });
    }

    public boolean doYouWantTributeBarBaros() {// :| WTF
        String answer = " ";
        System.out.println("Do You Want Tribute 3 Monsters?(YES|NO)");
        answer = ViewMaster.scanner.nextLine();
        while (!answer.equalsIgnoreCase("yes") && !answer.equalsIgnoreCase("no")) {
            System.out.println("please enter (YES|NO) : ");
            answer = ViewMaster.scanner.nextLine();
        }
        return answer.equalsIgnoreCase("YES");
    }


    public Monster askDoYouWantKillOneOfRivalMonster() {
        System.out.println("Do You Want Kill Rival Monster?(YES|NO)");
        String answer = ViewMaster.scanner.nextLine();
        if (answer.equalsIgnoreCase("yes")) {
            System.out.println("Enter Monster Number: ");
            String number = " ";
            while (!number.equalsIgnoreCase("cancel")) {
                number = ViewMaster.scanner.nextLine();
                if (number.matches("^\\d+$")) {
                    int num = Integer.parseInt(number);
                    if (num > 0 && num < 6) {
                        if (gameController.getCurrentPlayer().getRivalPlayer().getBoard().getMonsterByAddress(num) != null)
                            return (Monster) gameController.getCurrentPlayer().getRivalPlayer().getBoard().getMonsterByAddress(num);
                        else
                            System.out.println("There is no monster in this address");
                    } else
                        System.out.println("Enter Valid Number");
                } else
                    System.out.println("Enter Monster Number, if you want to cancel this Enter \"Cancel\"");
            }
        }
        return null;
    }

    public void getTributeForBarbaros() {
        System.out.println("Enter Monster Numbers In One Line");
        while (true) {
            System.out.println("Enter Monster Numbers");
            String numbers = ViewMaster.scanner.nextLine();
            Matcher matcher = Regex.getInputMatcher(numbers, "(\\d)\\s+(\\d)\\s+(\\d)");
            if (matcher.find()) {
                int m1 = Integer.parseInt(matcher.group(1));
                int m2 = Integer.parseInt(matcher.group(2));
                int m3 = Integer.parseInt(matcher.group(3));
                if (gameController.checkBarbarosInput(m1, m2, m3))
                    return;
                else
                    System.out.println("There Is No Monster In One Of This Address");
            } else System.out.println("Enter Valid Format");
        }
    }

    public void askWantSummonedAnotherMonsterTerratiger() {
        System.out.println("Do You Want Summoned Monster With level less then 5?(YES|NO)");
        String answer = " ";
        while (!answer.equalsIgnoreCase("yes") && !answer.equalsIgnoreCase("no")) {
            answer = ViewMaster.scanner.nextLine();
            if (answer.equalsIgnoreCase("no"))
                return;
            else if (answer.equalsIgnoreCase("yes")) {
                System.out.println("Enter Monster Number");
                while (true) {
                    String number = ViewMaster.scanner.nextLine();
                    if (number.matches("\\d")) {
                        if (gameController.summonCardWithTerratiger(Integer.parseInt(number)))
                            return;
                        else
                            System.out.println("You Can't Summon This Card, If You Want Cancel Enter \"Cancel\"");
                    } else if (number.equalsIgnoreCase("cancel")) return;
                    else
                        System.out.println("Enter Valid Number, If You Want Cancel Enter \"Cancel\"");
                }
            } else
                System.out.println("Enter YES or NO");
        }

    }

    public void printActiveOnlyForSpellsAndTrap() {
        okNotif("activate effect is only for spell cards", "Ok");
    }

    public void printCantActiveThisTurn() {
        okNotif("you can’t activate an effect on this turn", "Ok");
    }

    public void printAlreadyActivated() {
        okNotif("you have already activated this card", "Ok");
    }

    public void printSpellZoneIsFull() {
        okNotif("spell zone is full", "Ok");
    }

    public void printPrepsNotDone() {
        okNotif("preparations of this spell are not done yet", "Ok");
    }

    public void printCantSpecialSummon() {
        okNotif("there is no way you could special summon a monster", "Ok");
    }

    public void getTributeTheTricky() {
        int counter = 0;
        for (int i = 0; i < gameController.getCurrentPlayer().getCardsInHand().size(); i++) {
            Card card = gameController.getCurrentPlayer().getCardsInHand().get(i);
            System.out.println(++counter + ". " + card.getCardNameInGame());
        }
        while (true) {
            System.out.println("Enter Monster Number: ");
            String number = ViewMaster.scanner.nextLine().trim();
            if (number.matches("^\\d$")) {
                if (gameController.checkTheTrickyInput(Integer.parseInt(number)))
                    return;
                else
                    System.out.println("invalid selection , try again!");
            } else if (number.equalsIgnoreCase("cancel")) {
                return;
            } else
                System.out.println("you should special summon right now");
        }
    }

    public String getPositionForSpecialSummon() {
        while (true) {
            System.out.println("Attack Or Defence ?");
            String position = ViewMaster.scanner.nextLine();
            if (position.equalsIgnoreCase("attack")) return "attack";
            else if (position.equalsIgnoreCase("defence")) return "defence";
            else
                System.out.println("you should special summon right now");
        }
    }

    public void cantRitualSummon() {
        okNotif("there is no way you could ritual summon a monster", "Ok");
    }

    public void printSpellActivated() {
        okNotif("spell activated", "Ok");
    }

    public void printSelectSpellOrTrap() {
        System.out.println("select a spell or trap(1-5) :");
    }

    public int getNum() {
        return ViewMaster.scanner.nextInt();
    }

    public void printSelectMonsterFromBoard() {
        okNotif("select a monster from board", "Ok");
    }

    private void okNotif(String message, String ok) {
        createNotification(message, new Node[]{
                new CustomSanButtons(ok, () -> {
                    ViewMaster.btnSoundEffect();
                    notifStackPane.setVisible(false);
                    deBlur();
                })
        });
    }

    public void selectGraveyard() {
        System.out.println("select a Graveyard(our graveyard-rival graveyard");
    }

    public void printSelectNum() {
        System.out.println("select a number");
    }

    public void printselectCardFromHand(int size) {
        System.out.println("select a card from hand(" + size + ") to send to graveyard");
    }

    public void printCardInHand(Card card, int i) {
        System.out.println(card.getCardNameInGame() + " : " + i);
    }

    public void printCantAttackDirectly() {
        okNotif("you can’t attack the opponent directly", "Ok");
    }

    public void printSelectGraveyardHandOrDeck() {
        System.out.println("select (Graveyard-Hand-Deck)");
    }

    public void playerChanged(Player currentPlayer) {
        if (currentPlayer instanceof AIPlayer)
            createNotification("Turn changed.\ncurrent player: " + ((AIPlayer) currentPlayer).getNickname(), new Node[]{
                    new CustomSanButtons("Ok", () -> {
                        ViewMaster.btnSoundEffect();
                        notifStackPane.setVisible(false);
                        deBlur();
                    })
            });
        else
            okNotif("Turn changed.\ncurrent player: " + currentPlayer.getUser().getUsername(), "Ok");
    }

    public void printRitualSummonError() {
        System.out.println("there is no way you could ritual summon a monster");
    }

    public void getRitualSummonInput() {
        String command = "";
        while (true) {
            System.out.println("Enter Ritual Monster Number: ");
            command = ViewMaster.scanner.nextLine();
            try {
                int number = Integer.parseInt(command);
                run("select --hand " + number);
                Monster ritualMonster = (Monster) gameController.getCurrentPlayer().getSelectedCard();
                if (ritualMonster.getMonsterCardType() == MonsterCardType.RITUAL)
                    while (true) {
                        System.out.println("Enter Ritual Monster Tributes Number: \n" +
                                "For Example: 1 2 3 \n" +
                                "For Change Selected Card Enter <Back>");
                        command = ViewMaster.scanner.nextLine();
                        if (command.equalsIgnoreCase("back")) break;
                        ArrayList<Monster> tributes = new ArrayList<>();
                        String[] numbers = command.split("\\s+");
                        if (checkIsInputNumber(numbers)) {
                            for (String num : numbers) {
                                run("select --monster " + num);
                                tributes.add((Monster) gameController.getCurrentPlayer().getSelectedCard());
                            }
                            if (gameController.checkTributeLevelForRitualSummon(tributes, ritualMonster)) {
                                while (!command.equalsIgnoreCase("attack") &&
                                        !command.equalsIgnoreCase("Defence")) {
                                    System.out.println("Defence OR Attack?");
                                    command = ViewMaster.scanner.nextLine();
                                }
                                gameController.ritualSummon(ritualMonster, tributes,
                                        command.equalsIgnoreCase
                                                ("attack")
                                                ? AttackOrDefense.ATTACK : AttackOrDefense.DEFENSE);
                                return;
                            } else
                                System.out.println("selected monsters levels don’t match with ritual monster\n");
                        } else
                            System.out.println("you should ritual summon right now");
                    }
                else
                    System.out.println("you should ritual summon right now");
            } catch (Exception e) {
                System.out.println("you should ritual summon right now");
            }
        }
    }

    private boolean checkIsInputNumber(String[] numbers) {
        for (String number : numbers) {
            if (!number.matches("^\\d+$"))
                return false;
        }
        return true;
    }

    public void printCardAddedToHand(Card card) {
        okNotif("new card added to hand : " + card.getCardNameInGame(), "Ok");
    }

    public int chooseMonsterForSummonScanner(List<Monster> rivalGraveYardMonsters) {
        return chooseFromGraveyard(rivalGraveYardMonsters);
    }

    private int chooseFromGraveyard(List<Monster> rivalGraveYardMonsters) {
        while (true) {
            AtomicInteger i = new AtomicInteger();
            System.out.println("Choose One Of The Monsters To Scanner Be Like It In This Turn :");
            rivalGraveYardMonsters.forEach(e -> System.out.println(
                    (i.incrementAndGet()) + ". " + e.getCardNameInGame() + " : "
                            + e.getCardDescription()));
            String command = ViewMaster.scanner.nextLine();
            try {
                int number = Integer.parseInt(command);
                if (number > rivalGraveYardMonsters.size()
                        || number <= 0)
                    System.out.println("Wrong Number");
                else
                    return number - 1;
            } catch (Exception e) {
                System.out.println("Invalid Input");
            }
        }
    }

    public boolean wantToUseHeraldAbility() {
        while (true) {
            System.out.println("Do You Want To Use <Herald Of Creation> Ability? (YES|NO)");
            String command = ViewMaster.scanner.nextLine();
            if (command.equalsIgnoreCase("YES"))
                return true;
            else if (command.equalsIgnoreCase("NO"))
                return false;
            else
                System.out.println("Invalid Input");
        }
    }

    public int chooseMonsterForHeraldOfCreation(List<Monster> rivalGraveYardMonster) {
        return chooseFromGraveyard(rivalGraveYardMonster);
    }

    public void printChangeTurn() {
        if (gameController.getCurrentPlayer() instanceof AIPlayer)
            createNotification("now it will be " + ((AIPlayer)
                    gameController.getCurrentPlayer()).getNickname() + "’s turn", new Node[]{
                    new CustomSanButtons("Ok", () -> {
                        ViewMaster.btnSoundEffect();
                        notifStackPane.setVisible(false);
                        deBlur();
                    })
            });
        else
            createNotification("now it will be " +
                    gameController.getCurrentPlayer().getUser().getNickname() + "’s turn", new Node[]{
                    new CustomSanButtons("Ok", () -> {
                        ViewMaster.btnSoundEffect();
                        notifStackPane.setVisible(false);
                        deBlur();
                    })
            });
    }

    public boolean wantToActivateTrap(Trap trap) {
        final boolean[] answer = new boolean[1];
        CustomSanButtons yes = new CustomSanButtons("Yes", () -> {
            ViewMaster.btnSoundEffect();
            notifStackPane.setVisible(false);
            deBlur();
        });
        CustomSanButtons no = new CustomSanButtons("No", () -> {
            ViewMaster.btnSoundEffect();
            notifStackPane.setVisible(false);
            deBlur();
            answer[0] = false;
        });
        Node[] nodes = new Node[]{yes, no};
        createNotification("do you want to activate your trap " + trap.getCardNameInGame() + " ?", nodes);
        return answer[0];
    }

    public void printCantAttackFacedDown() {
        okNotif("cant attack because card is faced down", "Ok");
    }

    public void printCantAttackItsOnDefense() {
        okNotif("cant attack because card is on defense", "Ok");
    }

    public void printThisCardCantBeEquippedByThisType() {
        okNotif("this equip spell cant be equipped by this type of monster", "Ok");
    }

    public void printAbortedFromEquipSpell() {
        System.out.println("aborted from placing equip spell");
    }

    public void printAborted() {
        System.out.println("aborted");
    }

    public void printDoYouWantToDestroyFieldSpell() {
        System.out.println("do you want to destroy field spell??");
    }

    public void printSpellDestroyed() {
        okNotif("spell/trap is destroyed", "Ok");
    }

    public void printSelectNextSpellOrAbort() {
        System.out.println("select next trap/spell or abort");
    }

    public void printAllMonstersDestroyed() {
        System.out.println("all monsters destroyed");
    }

    public int getInputForTerraforming(List<Spell> fieldSpells) {
        int counter = 0;
        for (Spell fieldSpell : fieldSpells) {
            System.out.println(++counter + "- " +
                    fieldSpell.getCardNameInGame() + ": " + fieldSpell.getCardDescription());
        }
        while (true) {
            System.out.println("Enter Spell Number");
            String number = ViewMaster.scanner.nextLine().trim();
            try {
                int num = Integer.parseInt(number);
                if (num <= fieldSpells.size())
                    return num - 1;
                else
                    System.out.println("Invalid Number");
            } catch (Exception e) {
                System.out.println("Invalid Input");
            }
        }
    }

    public Node getNodeByRowColumnIndex(final int row, final int column, GridPane gridPane) {
        Node result = null;
        ObservableList<Node> children = gridPane.getChildren();
        for (Node node : children) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
                result = node;
                break;
            }
        }
        return result;
    }

    @FXML
    private void closeGraveyardMenu() {
        ourGraveyard.setDisable(false);
        rivalGraveyard.setDisable(false);
        ourField.setDisable(false);
        rivalField.setDisable(false);
        vBox.setDisable(false);
        leftPane.setDisable(false);
        gridPane.setDisable(false);
        tributeContainer.setDisable(false);
        rightPane.setDisable(false);
        leftPane.setEffect(null);
        gridPane.setEffect(null);
        tributeContainer.setEffect(null);
        rightPane.setEffect(null);
        vBox.setEffect(null);
        ourGraveyard.setEffect(null);
        rivalGraveyard.setEffect(null);
        ourField.setEffect(null);
        rivalField.setEffect(null);
        showGraveyardBack.setVisible(false);
        showGraveyardBack.setDisable(true);
    }

    public void showTrapIsActivating(Trap trap) {
        Cell trapCell = null;
        for (Cell cell : trap.getCardOwner().getBoard().getSpellOrTrap()) {
            if (cell.getCard() == trap) {
                trapCell = cell;
                break;
            }
        }
        assert trapCell != null;
        trapCell.setPictureUP();
    }

    public void setGraveyardCardsOnClick(Player player) {
        for (Node node : graveyardTilepane.getChildren()) {
            if (node instanceof ImageView) {
                ImageView imageView = (ImageView) node;
                imageView.setOnMouseClicked(event -> {
                    for (Card card : player.getBoard().getGraveyard().getAllCards()) {
                        if (card.getImage().equals(imageView.getImage())) {
                            player.setSelectedCard(card);
                            for (Node node1 : graveyardTilepane.getChildren()) {
                                if (node1 instanceof ImageView) {
                                    node1.setOnMouseClicked(null);
                                }
                            }
                            closeGraveyardMenu();
                            break;
                        }
                    }
                });
            }
        }
    }

    public String announceCardNameToRemove() {
        final String[] answer = new String[1];
        TextField textField = new TextField();
        textField.setStyle("-fx-font-family: 'Comic Sans MS'; -fx-font-size: 18px;-fx-background-color: transparent;" +
                "-fx-background-radius: 5;-fx-border-radius: 5;-fx-border-width: 1px");
        CustomSanButtons submit = new CustomSanButtons("Submit", () -> {
            ViewMaster.btnSoundEffect();
            notifStackPane.setVisible(false);
            deBlur();
            answer[0] = textField.getText();
        });
        Node[] nodes = new Node[]{textField, submit};
        createNotification("please enter a card name to remove from rival hand : ", nodes);
        return answer[0];
    }
*/}