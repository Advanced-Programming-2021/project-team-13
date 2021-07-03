package view.allmenu;

import controll.ShopController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.players.User;
import view.Menu;
import view.Regex;
import view.ViewMaster;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Objects;
import java.util.TreeMap;
import java.util.regex.Matcher;

public class ShopView {
    private static Stage stage;
    private Timeline messageTimer;
    private final Image backButtonImage =
            new Image(getClass().getResource("/shopImage/backButton.png").toExternalForm());
    private final Image allowBuy =
            new Image(getClass().getResource("/shopImage/allowBuy.png").toExternalForm());
    private final Image allowBuyShadow =
            new Image(getClass().getResource("/shopImage/allowBuyShadow.png").toExternalForm());
    private final Image notAllowBuy =
            new Image(getClass().getResource("/shopImage/notAllowBuy.png").toExternalForm());
    private ShopController shopController;
    @FXML
    private ImageView cardImage;
    @FXML
    private Label price;
    @FXML
    private Label information;
    @FXML
    private ImageView button;
    @FXML
    private TilePane cardsPlace;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private Label message;
    @FXML
    private ImageView backButton;
    private String selectedCardName;
    private int selectedCardPrice;
    private final FilenameFilter filenameFilter = (dir, name) -> name.endsWith(".jpg");

    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        try {
            AnchorPane root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/shop.fxml")));
            primaryStage.setScene(new Scene(root, 1280, 720));
            primaryStage.show();
        } catch (Exception e) {
        }

    }
    @FXML
    public void initialize() {
        ViewMaster.setUser(new User("", "", ""));
        shopController = new ShopController(this);
        scrollPane.fitToHeightProperty().set(true);
        scrollPane.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        messageTimer = new Timeline(new KeyFrame(Duration.seconds(0.05), event -> {
            message.setOpacity(message.getOpacity() - 0.1);
        }));
        messageTimer.setCycleCount(20);
        cardsPlaceInit();
        createBuyButton();
        backButton.setImage(backButtonImage);
        backButton.setOnMouseClicked(event -> {
            try {
                goToMainMenu();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void goToMainMenu() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainMenu.fxml"));
        ((Stage) backButton.getScene().getWindow()).setScene(new Scene(root));
    }
    private void createBuyButton() {
        button.setOnMouseEntered(e -> {
            if (button.getImage() == allowBuy)
                button.setImage(allowBuyShadow);
        });
        button.setOnMouseExited(e -> {
            if (button.getImage() == allowBuyShadow)
                button.setImage(allowBuy);
        });
        button.setOnMouseClicked(e -> {
            if (button.getImage() == allowBuyShadow) {
                ViewMaster.getUser().addCard(selectedCardName);
                ViewMaster.getUser().getCardsImage().put(selectedCardName, cardImage.getImage());
                ViewMaster.getUser().addMoney(-selectedCardPrice);
                message.setOpacity(1);
                shopButtonImage(selectedCardPrice);
                messageTimer.play();
            }
        });
    }

    private void cardsPlaceInit() {
        cardsPlace.setPadding(new Insets(20, 25, 25, 25));
        cardsPlace.setHgap(25);
        cardsPlace.setVgap(25);
        cardsPlace.setPrefTileWidth(160);
        cardsPlace.setPrefTileHeight(210);
        loadMonster();
        loadTrapAndSpell();
    }

    private void loadTrapAndSpell() {
        BufferedImage image;
        File spellTrap = new File(System.getProperty("user.dir") + "/src/main/resources/shopImage/SpellTrap");
        if (spellTrap.isDirectory())
            for (File file : Objects.requireNonNull(spellTrap.listFiles(filenameFilter))) {
                try {
                    image = ImageIO.read(file);
                    Image fxImage = SwingFXUtils.toFXImage(image, null);
                    ImageView imgView = new ImageView(fxImage);
                    imgView.setFitWidth(160);
                    imgView.setFitHeight(210);
                    imgView.setOnMouseClicked(e -> {
                        selectedCardName = file.getName().replace(".jpg", "");
                        cardImage.setImage(fxImage);
                        String[] detail = shopController.getDetail(file.getName().replace(".jpg", ""));
                        shopButtonImage(Integer.parseInt(detail[0]));
                        selectedCardPrice = Integer.parseInt(detail[0]);
                        price.setText("Price: " + detail[0]);
                        information.setText(detail[1]);
                    });
                    imgView.setOnMouseEntered(e -> setMouseEnterCard(imgView));
                    imgView.setOnMouseExited(e -> setMouseExitCard(imgView));
                    cardsPlace.getChildren().add(imgView);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
    }

    private void loadMonster() {
        BufferedImage image;
        File monsterImg = new File(System.getProperty("user.dir") + "/src/main/resources/shopImage/Monsters");
        if (monsterImg.isDirectory())
            for (File file : Objects.requireNonNull(monsterImg.listFiles(filenameFilter))) {
                try {
                    image = ImageIO.read(file);
                    Image fxImage = SwingFXUtils.toFXImage(image, null);
                    ImageView imgView = new ImageView(fxImage);
                    imgView.setFitWidth(160);
                    imgView.setFitHeight(210);
                    imgView.setOnMouseClicked(e -> {
                        selectedCardName = file.getName().replace(".jpg", "");
                        cardImage.setImage(fxImage);
                        String[] detail = shopController.getDetail(file.getName().replace(".jpg", ""));
                        shopButtonImage(Integer.parseInt(detail[0]));
                        selectedCardPrice = Integer.parseInt(detail[0]);
                        price.setText("Price: " + detail[0]);
                        information.setText(detail[1]);
                    });
                    imgView.setOnMouseEntered(e -> setMouseEnterCard(imgView));
                    imgView.setOnMouseExited(e -> setMouseExitCard(imgView));
                    cardsPlace.getChildren().add(imgView);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
    }

    private void shopButtonImage(int price) {
        if (price > ViewMaster.getUser().getMoney())
            button.setImage(notAllowBuy);
        else
            button.setImage(allowBuy);
    }

    public void setMouseEnterCard(ImageView imgView) {
        DropShadow dropShadow = new DropShadow(BlurType.ONE_PASS_BOX, Color.BLACK, 20, -1000, 0, -10);
        imgView.setFitWidth(175);
        imgView.setFitHeight(225);
        imgView.setEffect(dropShadow);
    }

    public void setMouseExitCard(ImageView imgView) {
        DropShadow dropShadow = new DropShadow(0, Color.BLACK);
        imgView.setFitWidth(160);
        imgView.setFitHeight(210);
        imgView.setEffect(dropShadow);
    }

    public void printMonsterCard(int attackNum, int defenseNum, int level, String cardName, String cardDescription, String monsterType) {
        System.out.println("Name: " + cardName + "\nLevel: " + level
                + "\nType: " + monsterType + "\nATK: " + attackNum
                + "\nDEF: " + defenseNum + "\nDescription: " + cardDescription);
    }

    public void printSpellAndTrap(String icon, String cardDescription, String cardName, String type) {
        System.out.println("Name: " + cardName + "Spell"
                + "\nType: " + type + "\nDescription: " + cardDescription);
    }

}
