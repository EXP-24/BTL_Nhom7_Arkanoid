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

public class MenuController {

    @FXML
    private ImageView playButton;

    @FXML
    private ImageView collectionButton;

    @FXML
    private ImageView exitButton;

    @FXML
    private ImageView creditsButton;

    private Image playButtonImage, playButtonHover;
    private Image exitButtonImage, exitButtonHover;
    private Image collectionButtonImage, collectionButtonHover;
    private Image creditsButtonImage, creditsButtonHover;
    private Image mouseImage;

    private MediaPlayer mediaPlayer; // Thêm biến để quản lý nhạc

    @FXML
    public void initialize() {
        // Nạp hình con trỏ chuột
        mouseImage = loadImage("mouse");
        Platform.runLater(() -> {
            playButton.getScene().setCursor(new ImageCursor(mouseImage));
        });

        // Nạp hình cho các nút
        playButtonImage = loadImage("start");
        playButtonHover = loadImage("startHover");
        exitButtonImage = loadImage("exit");
        exitButtonHover = loadImage("exitHover");
        collectionButtonImage = loadImage("collection");
        collectionButtonHover = loadImage("collectionHover");
        creditsButtonImage = loadImage("credits");
        creditsButtonHover = loadImage("creditsHover");

        // Hiệu ứng hover
        setHoverEffect(playButton, playButtonImage, playButtonHover);
        setHoverEffect(exitButton, exitButtonImage, exitButtonHover);
        setHoverEffect(collectionButton, collectionButtonImage, collectionButtonHover);
        setHoverEffect(creditsButton, creditsButtonImage, creditsButtonHover);

        // Gán sự kiện click
        playButton.setOnMouseClicked(e -> {
            stopMusic();
            startGame();
        });
        exitButton.setOnMouseClicked(e -> {
            stopMusic();
            System.exit(0);
        });
        collectionButton.setOnMouseClicked(e -> {
            stopMusic();
            openCollection();
        });
        creditsButton.setOnMouseClicked(e -> {
            stopMusic();
            openCredits();
        });

        // Phát nhạc menu khi mở
        playMenuMusic();
    }

    /** Tải hình ảnh theo tên */
    private Image loadImage(String filename) {
        return new Image(Objects.requireNonNull(
                getClass().getResourceAsStream("/org/example/btl/images/texts/" + filename + ".png")));
    }

    /** Hiệu ứng đổi hình khi hover */
    private void setHoverEffect(ImageView button, Image normal, Image hover) {
        button.setOnMouseEntered(e -> button.setImage(hover));
        button.setOnMouseExited(e -> button.setImage(normal));
    }

    /** Phát nhạc nền menu */
    private void playMenuMusic() {
        try {
            URL soundUrl = getClass().getResource("/org/example/btl/M&S/menu.mp3");
            if (soundUrl != null) {
                mediaPlayer = new MediaPlayer(new Media(soundUrl.toExternalForm()));
                mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Lặp vô hạn
                mediaPlayer.setVolume(0.7);
                mediaPlayer.play();
            } else {
                System.err.println("Không tìm thấy file nhạc menu.mp3");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** Dừng nhạc */
    private void stopMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
            mediaPlayer = null;
        }
    }

    /** Mở game */
    private void startGame() {
        try {
            Parent gameRoot = FXMLLoader.load(Objects.requireNonNull(
                    getClass().getResource("/org/example/btl/Game.fxml")));
            Stage stage = (Stage) playButton.getScene().getWindow();
            stage.setScene(new Scene(gameRoot, MAX_WIDTH, MAX_HEIGHT));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Mở phần bộ sưu tập */
    private void openCollection() {
        try {
            Parent collectionRoot = FXMLLoader.load(Objects.requireNonNull(
                    getClass().getResource("/org/example/btl/Collections.fxml")));
            Stage stage = (Stage) collectionButton.getScene().getWindow();
            stage.setScene(new Scene(collectionRoot, MAX_WIDTH, MAX_HEIGHT));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Mở phần Credits */
    private void openCredits() {
        try {
            Parent creditsRoot = FXMLLoader.load(Objects.requireNonNull(
                    getClass().getResource("/org/example/btl/Credits.fxml")));
            Stage stage = (Stage) creditsButton.getScene().getWindow();
            stage.setScene(new Scene(creditsRoot, MAX_WIDTH, MAX_HEIGHT));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
