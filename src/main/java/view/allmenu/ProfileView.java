package view.allmenu;

import controll.ProfileController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.menuItems.CustomButton;
import view.ViewMaster;

import java.io.File;
import java.io.IOException;

public class ProfileView {
    private final ProfileController profileController;
    public BorderPane pane;
    @FXML
    private ImageView avatarPic;

    public ProfileView() {
        profileController = new ProfileController(this);
    }

    public void initialize() {
        Circle circle = new Circle(1280 - 120, 150, 100, Color.web("black", 1));
        circle.setFill(new ImagePattern(ViewMaster.getUser().getImage() == null ? new Image("/profile/2.png") : ViewMaster.getUser().getImage()
                , 60, 51, 200, 200, false));
        Image background = new Image(getClass().getResource("/profile/a.jpg").toExternalForm(),
                1280, 720, false, true);
        ImageView imageView = new ImageView(background);
        circle.setOnMouseClicked(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select Profile Image");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image"
                    , "*.png", "*.jpg", "*.jpeg", "*.tif", "*.tiff", "*.bmp", "*.gif"));
            File file = fileChooser.showOpenDialog(pane.getScene().getWindow());
            if (file != null && file.exists() && file.isFile()) {
                Image image = new Image(file.toURI().toString());
                circle.setFill(new ImagePattern(image, 60, 51, 200, 200, false));
                ViewMaster.getUser().setProfileImage(image);
            }
        });
        VBox vbox = new VBox(20, setNodes());
        vbox.setTranslateY(400);
        vbox.setTranslateX(1280 - 300);
        pane.getChildren().addAll(
                imageView,
                vbox,
                circle
        );
    }


    private Node[] setNodes() {
        return new Node[]{
                new CustomButton("Change Nickname", () -> {
                    ViewMaster.btnSoundEffect();
                    changeNickName();
                }),
                new CustomButton("Change Password", () -> {
                    ViewMaster.btnSoundEffect();
                    changePassword();
                }),
                new CustomButton("Back", () -> {
                    try {
                        ViewMaster.btnSoundEffect();
                        goBack();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }),
        };
    }

    public void printNicknameChanged() {
        Alert alert = new Alert(AlertType.INFORMATION, "nickname changed successfully!", ButtonType.OK);
        alert.setHeaderText("Successful");
        alert.showAndWait();
    }

    public void printNicknameExists(String newNickname) {
        Alert alert = new Alert(AlertType.ERROR, "user with nickname " + newNickname +
                " already exists", ButtonType.OK);
        alert.setHeaderText("Failed");
        alert.showAndWait();
    }

    public void printPasswordChanged() {
        Alert alert = new Alert(AlertType.INFORMATION, "password changed successfully!", ButtonType.OK);
        alert.setHeaderText("Successful");
        alert.showAndWait();
    }

    public void printInvalidPassword() {
        Alert alert = new Alert(AlertType.ERROR, "current password is invalid", ButtonType.OK);
        alert.setHeaderText("Failed");
        alert.showAndWait();
    }

    public void printSamePassword() {
        Alert alert = new Alert(AlertType.ERROR, "please enter a new password", ButtonType.OK);
        alert.setHeaderText("Failed");
        alert.showAndWait();
    }

    @FXML
    private void changePassword() {
        TextInputDialog textInputDialog = new TextInputDialog();
        textInputDialog.setTitle("Enter current password");
        textInputDialog.setHeaderText("Current");
        textInputDialog.showAndWait();
        if (textInputDialog.getEditor().getText().isEmpty()) {
            emptyInputError();
            return;
        }
        String currentPassword = textInputDialog.getEditor().getText();
        TextInputDialog textInputDialog2 = new TextInputDialog();
        textInputDialog2.setTitle("Enter new password");
        textInputDialog2.showAndWait();
        String newPassword = textInputDialog2.getEditor().getText();
        profileController.changePassword(currentPassword, newPassword);
    }

    private void emptyInputError() {
        Alert alert = new Alert(AlertType.ERROR, "Empty Input", ButtonType.OK);
        alert.showAndWait();
    }

    @FXML
    private void changeNickName() {
        TextInputDialog textInputDialog = new TextInputDialog();
        textInputDialog.setTitle("Enter new nickName");
        textInputDialog.setHeaderText("new Nickname");
        textInputDialog.showAndWait();
        if (textInputDialog.getEditor().getText().isEmpty()) {
            emptyInputError();
            return;
        }
        profileController.changeNickname(textInputDialog.getEditor().getText());
    }

    public void goBack() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainMenu.fxml"));
        ((Stage) pane.getScene().getWindow()).setScene(new Scene(root));
    }
}
