package view.allmenu;

import com.talanlabs.avatargenerator.Avatar;
import com.talanlabs.avatargenerator.eightbit.EightBitAvatar;
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
import javafx.stage.Stage;
import model.menuItems.CustomButton;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.IOException;

public class ProfileView {
    private final ProfileController profileController;
    public BorderPane pane;
    private ImageView avatarPic;
    public ProfileView() {
        profileController = new ProfileController(this);
    }

    public void initialize() {
        Circle circle = new Circle(1280-120,150,100, Color.web("black",1));
        circle.setFill(new ImagePattern(new Image("/profile/2.png"),60,51,200,200,false));
        Image background = new Image(getClass().getResource("/profile/a.jpg").toExternalForm(),
                1280, 720, false, true);
        ImageView imageView = new ImageView(background);
        circle.setOnMouseClicked(event -> {
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "JPG & GIF Images", "jpg", "gif");
            chooser.setFileFilter(filter);
            int returnVal = chooser.showOpenDialog(null);
            if(returnVal == JFileChooser.APPROVE_OPTION) {
                System.out.println("You chose to open this file: " +
                        chooser.getSelectedFile().getAbsolutePath());
            }
//            FileDialog dialog = new FileDialog((Frame)null, "Select picture to set as avatar");
//            dialog.setMode(FileDialog.LOAD);
//            dialog.setVisible(true);
//            String file = dialog.getDirectory()+dialog.getFile();
//            System.out.println(file);
//            circle.setFill(new ImagePattern
//                    (new Image(file),60,51,200,200,false));
        });
        VBox vbox = new VBox(20, setNodes());
        vbox.setTranslateY(400);
        vbox.setTranslateX(1280-300);
        pane.getChildren().addAll(
                imageView,
                vbox,
                circle
        );
    }


    private Node[] setNodes() {
        return new Node[]{
                new CustomButton("Change Nickname", this::changeNickName),
                new CustomButton("Change Password", this::changePassword),
                new CustomButton("Back", () -> {
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
