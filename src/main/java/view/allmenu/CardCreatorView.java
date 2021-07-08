package view.allmenu;

import controll.ImageLoader;
import controll.ShopController;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.cards.Card;
import model.cards.Monster;
import model.csv.MonsterCSV;
import model.players.User;
import view.SceneController;
import view.ViewMaster;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CardCreatorView implements Initializable {
    @FXML
    private TextArea description;
    @FXML
    private AnchorPane firstBack;
    @FXML
    private AnchorPane secondBack;
    @FXML
    private AnchorPane thirdBack;
    @FXML
    private VBox thirdBackVBox;
    @FXML
    private ComboBox<String> effectCombo1;
    @FXML
    private TextField def;
    @FXML
    private TextField att;
    @FXML
    private TextField price;
    @FXML
    private TextField level;
    @FXML
    private TextField cardName;
    @FXML
    private ComboBox<String> effectCombo;
    @FXML
    private VBox checkVBox;
    @FXML
    private ImageView image;

    private int defNum;
    private int attNum;
    private int priceNum;
    private int levelNum;
    private User user;
    private ArrayList<String> monsterNames;
    private MonsterCSV effectingMonster;
    private ArrayList<MonsterCSV> monsterCSVArrayList;
    private List<MonsterCSV> allMonsters;
    private final Image cardImage = ImageLoader.getCardImageByName("createCard");
    private final Image backImage = ImageLoader.getCardImageByName("Unknown");


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        user = ViewMaster.getUser();
        monsterNames = new ArrayList<>();
        monsterCSVArrayList = new ArrayList<>();
        try {
            allMonsters = MonsterCSV.getAllCSVCards();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        image.setImage(cardImage);
        addAnimationToImage();
        addMonsters();
        try {
            allMonsters = MonsterCSV.getAllCSVCards();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        effectComboSetOnAction();
    }

    private void addMonsters() {
        ObservableList<String> observableList = FXCollections.observableArrayList();
        for (MonsterCSV monsterCSV : allMonsters) {
            observableList.add(monsterCSV.getName());
        }
        effectCombo1.setItems(observableList);
        effectCombo.setItems(observableList);
    }

    private void addAnimationToImage() {
        image.setEffect(new DropShadow(20, Color.WHITE));
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(6), event -> {
            if (image.getImage() == cardImage) {
                image.setImage(backImage);
            } else {
                image.setImage(cardImage);
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        RotateTransition rotateTransition = new RotateTransition();
        rotateTransition.setNode(image);
        rotateTransition.setDuration(Duration.seconds(6));
        rotateTransition.setAxis(Rotate.Y_AXIS);
        rotateTransition.setFromAngle(-90);
        rotateTransition.setToAngle(90);
        rotateTransition.setCycleCount(Animation.INDEFINITE);
        rotateTransition.play();
    }

    private void effectComboSetOnAction() {
        effectCombo.setOnAction(event -> {
            String cardName = effectCombo.getValue();
            if (!monsterNames.contains(cardName)) {
                showMonster(cardName);
            }
        });
        effectCombo1.setOnAction(event -> {
            String effectCard = effectCombo1.getValue();
            for (MonsterCSV monsterCSV : allMonsters) {
                if (monsterCSV.getName().equals(effectCard)) {
                    effectingMonster = monsterCSV;
                    break;
                }
            }
        });
    }

    private void showMonster(String cardName) {
        monsterNames.add(cardName);
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(50);
        CheckBox checkBox = new CheckBox();
        checkBox.setSelected(true);
        checkBox.setOnAction(event -> {
            if (!checkBox.isSelected()) {
                checkVBox.getChildren().remove(hBox);
                monsterNames.remove(cardName);
                for (MonsterCSV monsterCSV : monsterCSVArrayList) {
                    if (monsterCSV.getName().equals(cardName)) {
                        monsterCSVArrayList.remove(monsterCSV);
                        break;
                    }
                }
                updateData();
            }
        });
        Label label = getLabel(cardName);
        hBox.getChildren().addAll(label, checkBox);
        checkVBox.getChildren().add(hBox);
        for (MonsterCSV monsterCSV : allMonsters) {
            if (monsterCSV.getName().equals(cardName)) {
                monsterCSVArrayList.add(monsterCSV);
                break;
            }
        }
        updateData();
    }

    private void updateData() {
        int sumLevel = 0;
        int sumMoney = 0;
        int sumAttack = 0;
        int sumDefense = 0;
        int num = monsterCSVArrayList.size();
        for (MonsterCSV monsterCSV : monsterCSVArrayList) {
            sumLevel += monsterCSV.getLevel();
            sumMoney += monsterCSV.getPrice();
            sumAttack += monsterCSV.getAttack();
            sumDefense += monsterCSV.getDefence();
        }
        if (num != 0) {
            levelNum = sumLevel / num;
            defNum = ((sumDefense / num) / 100) * 100;
            priceNum = ((sumMoney / num) / 100) * 100;
            attNum = ((sumAttack / num) / 100) * 100;
        } else {
            levelNum = 0;
            defNum = 0;
            priceNum = 0;
            attNum = 0;
        }
        level.setText(String.valueOf(levelNum));
        price.setText(String.valueOf(priceNum));
        def.setText(String.valueOf(defNum));
        att.setText(String.valueOf(attNum));
    }

    private Label getLabel(String cardName) {
        Label label = new Label(cardName);
        label.setAlignment(Pos.CENTER);
        label.setStyle("-fx-font-family: 'Comic Sans MS'; -fx-font-size: 18px; -fx-text-fill: white");
        return label;
    }

    @FXML
    private void createCard() {
        if (cardName.getText().length() == 0 || cardName.getText() == null) {
            blurBackGround();
            thirdBackVBox.getChildren().clear();
            thirdBackVBox.getChildren().addAll(getLabel("Enter A Name For Card First!"));
            addTimeline();
        } else {
            if (description.getText() == null || description.getText().length() == 0) {
                blurBackGround();
                thirdBackVBox.getChildren().clear();
                thirdBackVBox.getChildren().add(getLabel("Enter A Description For Card!"));
                addTimeline();
            } else {
                if (effectingMonster == null || monsterCSVArrayList.size() == 0) {
                    blurBackGround();
                    thirdBackVBox.getChildren().clear();
                    thirdBackVBox.getChildren().add(getLabel("Choose Monster Effect First!"));
                    addTimeline();
                } else {
                    if (user != null && user.getMoney() >= priceNum) {
                        blurBackGround();
                        thirdBackVBox.getChildren().clear();
                        thirdBackVBox.getChildren().add(getLabel("Card Created And Bought!"));
                        Card card = new Monster(cardName.getText(), null, priceNum, description.getText()
                                , effectingMonster.getMonsterType(), effectingMonster.getCardType()
                                , effectingMonster.getAttribute(), attNum, defNum, levelNum, cardImage);
                        card.setCardNameInGame(effectingMonster.getName());
                        new ShopController(null).buyCard(user, card, priceNum);
                        addTimeline();
                    } else {
                        blurBackGround();
                        thirdBackVBox.getChildren().clear();
                        thirdBackVBox.getChildren().add(getLabel("You Don't Have Enough Money"));
                        addTimeline();
                    }
                }
            }
        }
    }

    @FXML
    private void runMainMenu() throws IOException {
        Stage stage = ((Stage) effectCombo.getScene().getWindow());
        SceneController.startMainMenu(stage);
    }


    public void importCard(ActionEvent actionEvent) {

    }

    public void exportCard(ActionEvent actionEvent) {
    }

    public void addTimeline() {
        KeyFrame keyFrame = new KeyFrame(Duration.millis(3000), event -> undoBlur());
        Timeline timeline = new Timeline(keyFrame);
        timeline.play();
    }

    private void blurBackGround() {
        firstBack.setEffect(new GaussianBlur(20));
        secondBack.setEffect(new GaussianBlur(20));
        thirdBack.setVisible(true);
    }

    private void undoBlur() {
        firstBack.setEffect(null);
        secondBack.setEffect(null);
        thirdBack.setVisible(false);
    }

}
