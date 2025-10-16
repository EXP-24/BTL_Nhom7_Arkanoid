package org.example.btl.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

import static org.example.btl.GameApplication.maxHeight;
import static org.example.btl.GameApplication.maxWidth;

public class MenuController {
    @FXML
    private Canvas canvas;

    @FXML
    private ImageView playButton;
    /*
    @FXML
    private ImageView collectionButton; */

    @FXML
    private ImageView exitButton;

    private Image playButtonImage, playButtonHover;
    private Image exitButtonImage, exitButtonHover;
    //private Image collectionButtonImage, collectionButtonHover;
    private Image mouseImage;

    @FXML
    public void initialize() {
        mouseImage = loadImage("mouse");
        canvas.toFront();
        canvas.setMouseTransparent(true);
        canvas.setCursor(new ImageCursor(mouseImage));
        playButtonImage = loadImage("start");
        playButtonHover = loadImage("startHover");
        exitButtonImage = loadImage("exit");
        exitButtonHover = loadImage("exitHover");

        setHoverEffect(playButton, playButtonImage, playButtonHover);
        setHoverEffect(exitButton, exitButtonImage, exitButtonHover);

        playButton.setOnMouseClicked(e -> startgame());
        exitButton.setOnMouseClicked(e -> System.exit(0));

    }

    public Image loadImage(String filename) {
        return new Image(Objects.requireNonNull(
                getClass().getResourceAsStream("/org/example/btl/images/" + filename +".png")));
    }

    private void setHoverEffect(ImageView button, Image normal, Image hover) {
        button.setOnMouseEntered(e -> button.setImage(hover));
        button.setOnMouseExited(e -> button.setImage(normal));
    }

    private void startgame() {
        try {
            Parent gameroot = FXMLLoader.load(Objects.requireNonNull(
                    getClass().getResource("/org/example/btl/Game.fxml")));
            Stage stage = (Stage) playButton.getScene().getWindow();
            stage.setScene(new Scene(gameroot, maxWidth, maxHeight));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
