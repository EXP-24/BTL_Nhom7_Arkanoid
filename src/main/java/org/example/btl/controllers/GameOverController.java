package org.example.btl.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

import static org.example.btl.Config.*;


public class GameOverController {
    @FXML
    private ImageView restartButton;

    @FXML
    private ImageView quitButton;

    private Image restartButtonImage,restartButtonHover;
    private Image quitButtonImage,quitButtonHover;
    private Image mouseImage;
    private GameController gameController;

    @FXML
    public void initialize() {
        mouseImage = loadImage("mouse");
        Platform.runLater(() -> {
            restartButton.getScene().setCursor(new ImageCursor(mouseImage));
        });
        restartButtonImage = loadImage("restart");
        restartButtonHover = loadImage("restartHover");
        quitButtonImage = loadImage("quit");
        quitButtonHover = loadImage("quitHover");

        setHoverEffect(quitButton, quitButtonImage, quitButtonHover);
        setHoverEffect(restartButton, restartButtonImage, restartButtonHover);

        restartButton.setOnMouseClicked(e-> onRestart());
        quitButton.setOnMouseClicked(e -> onQuit());
    }


    private Image loadImage(String filename) {
        return new Image(Objects.requireNonNull(
                getClass().getResourceAsStream("/org/example/btl/images/texts/" + filename + ".png")));
    }

    /** Hiệu ứng đổi hình khi hover */
    private void setHoverEffect(ImageView button, Image normal, Image hover) {
        button.setOnMouseEntered(e -> button.setImage(hover));
        button.setOnMouseExited(e -> button.setImage(normal));
    }
    private void onRestart(){
        try{
            Parent gameRoot = FXMLLoader.load(Objects.requireNonNull(
                    getClass().getResource("/org/example/btl/Game.fxml")));
            Stage stage = (Stage) restartButton.getScene().getWindow();
            stage.setScene(new Scene(gameRoot,MAX_WIDTH,MAX_HEIGHT));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void onQuit(){
        try{
            Parent gameRoot = FXMLLoader.load(Objects.requireNonNull(
                    getClass().getResource("/org/example/btl/Menu.fxml")));
            Stage stage = (Stage) restartButton.getScene().getWindow();
            stage.setScene(new Scene(gameRoot,MAX_WIDTH,MAX_HEIGHT));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setGameController(GameController controller) {
        this.gameController = controller;
    }

    private void onResume() {
        gameController.resumeGame();
    }
}