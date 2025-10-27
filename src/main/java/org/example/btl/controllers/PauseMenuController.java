package org.example.btl.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

import static org.example.btl.GameApplication.MAX_HEIGHT;
import static org.example.btl.GameApplication.MAX_WIDTH;

public class PauseMenuController {

    @FXML
    private ImageView resumeButton;
    @FXML
    private ImageView restartButton;
    @FXML
    private ImageView exitButton;

    private Image resumeImage, resumeHover;
    private Image restartImage, restartHover;
    private Image exitImage, exitHover;
    private Image mouseImage;
    private MediaPlayer mediaPlayer;

    @FXML
    public void initialize() {
        mouseImage = loadImage("mouse");
        Platform.runLater(() -> resumeButton.getScene().setCursor(new ImageCursor(mouseImage)));

        resumeImage = loadImage("back");
        resumeHover = loadImage("backHover");
        exitImage = loadImage("exit");
        exitHover = loadImage("exitHover");

        setHoverEffect(resumeButton, resumeImage, resumeHover);
        setHoverEffect(restartButton, restartImage, restartHover);
        setHoverEffect(exitButton, exitImage, exitHover);

        resumeButton.setOnMouseClicked(e -> resumeGame());
        restartButton.setOnMouseClicked(e -> restartGame());
        exitButton.setOnMouseClicked(e -> exitToMenu());

        playPauseMusic();
    }

    private Image loadImage(String name) {
        return new Image(Objects.requireNonNull(
                getClass().getResourceAsStream("/org/example/btl/images/texts/" + name + ".png")));
    }

    private void setHoverEffect(ImageView button, Image normal, Image hover) {
        button.setOnMouseEntered(e -> button.setImage(hover));
        button.setOnMouseExited(e -> button.setImage(normal));
    }

    private void playPauseMusic() {
        try {
            URL soundUrl = getClass().getResource("/org/example/btl/M&S/pause.mp3");
            if (soundUrl != null) {
                mediaPlayer = new MediaPlayer(new Media(soundUrl.toExternalForm()));
                mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
                mediaPlayer.setVolume(0.5);
                mediaPlayer.play();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void stopMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
            mediaPlayer = null;
        }
    }

    private void resumeGame() {
        stopMusic();
        Stage stage = (Stage) resumeButton.getScene().getWindow();
        stage.close();
    }

    private void restartGame() {
        stopMusic();
        try {
            Parent gameRoot = FXMLLoader.load(Objects.requireNonNull(
                    getClass().getResource("/org/example/btl/Game.fxml")));
            Stage mainStage = (Stage) restartButton.getScene().getWindow();
            mainStage.setScene(new Scene(gameRoot, MAX_WIDTH, MAX_HEIGHT));
            mainStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void exitToMenu() {
        stopMusic();
        try {
            Parent menuRoot = FXMLLoader.load(Objects.requireNonNull(
                    getClass().getResource("/org/example/btl/Menu.fxml")));
            Stage mainStage = (Stage) exitButton.getScene().getWindow();
            mainStage.setScene(new Scene(menuRoot, MAX_WIDTH, MAX_HEIGHT));
            mainStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
