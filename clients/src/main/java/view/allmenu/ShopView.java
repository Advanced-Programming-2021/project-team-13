package view.allmenu;

import com.gilecode.yagson.YaGson;
import com.google.common.reflect.TypeToken;
import controll.ShopController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import view.ViewMaster;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Objects;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

public class ShopView {
    private final YaGson yaGson = new YaGson();
    private final Type type = new TypeToken<HashMap<String, Image>>() {
    }.getType();
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

    public void start(Stage primaryStage) {
        try {
            AnchorPane root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/shop.fxml")));
            primaryStage.setScene(new Scene(root, 1280, 720));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        shopController = new ShopController();
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
     /*       if (button.getImage() == allowBuyShadow) {
                ViewMaster.completeSoundEffect();
                Card card = Card.findCardFromCsv(selectedCardName);
                shopController.buyCard(ViewMaster.getUser(), card, selectedCardPrice);
                message.setOpacity(1);
                shopButtonImage(selectedCardPrice);
                messageTimer.play();
            }*/
        });
    }

    private void cardsPlaceInit() {
        cardsPlace.setPadding(new Insets(20, 25, 25, 25));
        cardsPlace.setHgap(25);
        cardsPlace.setVgap(25);
        cardsPlace.setPrefTileWidth(160);
        cardsPlace.setPrefTileHeight(210);
        load();
    }

    private void load() {
        try {
            ViewMaster.dataOutputStream.writeUTF("shop");
            ViewMaster.dataOutputStream.flush();
            String json = decrypt(ViewMaster.dataInputStream.readUTF());
            System.out.println(json);
            HashMap<String, Image> images = yaGson.fromJson(json, type);
            images.forEach((name, image) ->
            {
                if (!name.equalsIgnoreCase("Unknown.jpg")
                        && !name.equalsIgnoreCase("Unknown")
                        && !name.equalsIgnoreCase("createCard.jpg")
                        && !name.equalsIgnoreCase("createCard")) {
                    ImageView imgView = new ImageView(image);
                    imgView.setFitWidth(160);
                    imgView.setFitHeight(210);
                    imgView.setOnMouseClicked(e -> {
              /*          selectedCardName = name;
                        cardImage.setImage(image);
                        String[] detail = shopController.getDetail(name);
                        shopButtonImage(Integer.parseInt(detail[0]));
                        selectedCardPrice = Integer.parseInt(detail[0]);
                        price.setText("Price: " + detail[0]);
                        information.setText(detail[1]);*/
                    });
                    imgView.setOnMouseEntered(e -> setMouseEnterCard(imgView));
                    imgView.setOnMouseExited(e -> setMouseExitCard(imgView));
                    cardsPlace.getChildren().add(imgView);
                }
            });
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private String decrypt(String read) {
        byte[] bytes = read.getBytes(StandardCharsets.UTF_8);
        Inflater inflater = new Inflater();
        byte[] b = new byte[15];
        inflater.setInput(bytes);
        int n = 0;
        try {
            n = inflater.inflate(b);
            return new String(b, 0, n, StandardCharsets.UTF_8);
        } catch (DataFormatException ignored) {
        }
        return null;
    }

    private void shopButtonImage(int price) {
   /*     if (price > ViewMaster.getUser().getMoney())
            button.setImage(notAllowBuy);
        else
            button.setImage(allowBuy);*/
    }

    public void setMouseEnterCard(ImageView imgView) {
        DropShadow dropShadow = new
                DropShadow(BlurType.ONE_PASS_BOX, Color.BLACK, 20, -1000, 0, -10);
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

    public void printMonsterCard
            (int attackNum, int defenseNum, int level, String cardName, String cardDescription, String monsterType) {
        System.out.println("Name: " + cardName + "\nLevel: " + level
                + "\nType: " + monsterType + "\nATK: " + attackNum
                + "\nDEF: " + defenseNum + "\nDescription: " + cardDescription);
    }

    public void printSpellAndTrap(String icon, String cardDescription, String cardName, String type) {
        System.out.println("Name: " + cardName + "Spell"
                + "\nType: " + type + "\nDescription: " + cardDescription);
    }

}
