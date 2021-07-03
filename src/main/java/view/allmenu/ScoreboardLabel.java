package view.allmenu;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.util.Objects;

public class ScoreboardLabel {
    private static Image image =
            new Image(Objects.requireNonNull(ScoreboardLabel.class.getResource("/scoreboardImage/background.png")).toExternalForm());
    private static String nickName, scoreText, rankText;
    private static Image profileImage;
    @FXML
    public ImageView background;
    @FXML
    private ImageView profile;
    @FXML
    private Label rank;
    @FXML
    private Label name;
    @FXML
    private Label score;
    @FXML
    private AnchorPane parent;

    public AnchorPane getLabel(String nickName, Image profileImage, int score, int rank) {
        ScoreboardLabel.nickName = nickName;
        ScoreboardLabel.profileImage = profileImage;
        scoreText = score + "";
        rankText = rank + "";
        try {
            parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/scoreboard.fxml")));
        } catch (Exception ignored) {
        }
        return parent;
    }

    @FXML
    private void initialize() {
        switch (rankText) {
            case "1":
                parent.setStyle("-fx-background-color:#FFE194");
                break;
            case "2":
                parent.setStyle("-fx-background-color:#A19882");
                break;
            case "3":
                parent.setStyle("-fx-background-color:#BB8760");
                break;
            default:
                background.setImage(image);
                break;
        }
        profile.setImage(profileImage);
        this.rank.setText(rankText);
        this.score.setText(scoreText);
        name.setText(nickName);
    }

}
