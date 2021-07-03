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
import javafx.stage.Stage;
import model.menuItems.CustomButtons;

import java.io.IOException;

public class ProfileView {
    private final ProfileController profileController;
    //    public Button changeNicknameBtn;
//    public Button changePasswordBtn;
//    public Button backBtn;
    public BorderPane pane;
    private Image background;
    private ImageView imageView;

    public ProfileView() {
        profileController = new ProfileController(this);
    }

    public void initialize() {
        background = new Image(getClass().getResource("/profile/a.jpg").toExternalForm(),
                1280, 720, false, true);
        imageView=new ImageView(background);
        VBox vbox = new VBox(20, setNodes());
        vbox.setTranslateY(400);
        vbox.setTranslateX(1280-300);
        pane.getChildren().addAll(
                imageView,
                vbox
        );
    }


    private Node[] setNodes() {
        return new Node[]{
                new CustomButtons("Change Nickname", this::changeNickName),
                new CustomButtons("Change Password", this::changePassword),
                new CustomButtons("Back", () -> {
                    try {
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
        Alert alert = new Alert(AlertType.INFORMATION,"password changed successfully!",ButtonType.OK);
        alert.setHeaderText("Successful");
        alert.showAndWait();
    }

    public void printInvalidPassword() {
        Alert alert = new Alert(AlertType.ERROR,"current password is invalid",ButtonType.OK);
        alert.setHeaderText("Failed");
        alert.showAndWait();
    }

    public void printSamePassword() {
        Alert alert = new Alert(AlertType.ERROR,"please enter a new password",ButtonType.OK);
        alert.setHeaderText("Failed");
        alert.showAndWait();
    }

    @FXML
    private void changePassword() {
        TextInputDialog textInputDialog = new TextInputDialog();
        textInputDialog.setTitle("Enter current password");
        textInputDialog.setHeaderText("Current");
        textInputDialog.showAndWait();
        if(textInputDialog.getEditor().getText().isEmpty()) {
            emptyInputError();
            return;
        }
        String currentPassword=textInputDialog.getEditor().getText();
        TextInputDialog textInputDialog2 = new TextInputDialog();
        textInputDialog2.setTitle("Enter new password");
        textInputDialog2.showAndWait();
        String newPassword = textInputDialog2.getEditor().getText();
        profileController.changePassword(currentPassword, newPassword);
    }

    private void emptyInputError() {
        Alert alert = new Alert(AlertType.ERROR,"Empty Input",ButtonType.OK);
        alert.showAndWait();
    }

    @FXML
    private void changeNickName() {
        TextInputDialog textInputDialog = new TextInputDialog();
        textInputDialog.setTitle("Enter new nickName");
        textInputDialog.setHeaderText("new Nickname");
        textInputDialog.showAndWait();
        if(textInputDialog.getEditor().getText().isEmpty()) {
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
