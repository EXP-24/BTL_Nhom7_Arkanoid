package org.example.btl.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

import static org.example.btl.GameApplication.MAX_HEIGHT;
import static org.example.btl.GameApplication.MAX_WIDTH;

public class CreditsController {

    @FXML
    private Pane creditsPane;

    private Timeline timeline;
    private MediaPlayer mediaPlayer;

    @FXML
    private void initialize() {
        // Load pixel font
        Font.loadFont(Objects.requireNonNull(
                        getClass().getResource("/org/example/btl/fonts/PixelPurl.ttf"))
                .toExternalForm(), 30);

        // Start playing background music
        playMusic();

        // === CREDIT TEXT ===
        Text creditsText = new Text("""
                GAME: ARKANOID!

                Developed by: TEAM 7

                Leader, Game Logic, Menu & Sound Effects
                Pham Duc Cuong

                Level Designer & Map System Developer
                Do Trong An

                Score & Paddle Power-up System Developer
                Nguyen Tuan Anh

                Pause Menu & Game Over System Developer
                Khuong Tuan Anh

                Course Project:
                Object-Oriented Programming
                Semester: 2025

                Tools & Technologies:
                JavaFX
                Scene Builder
                IntelliJ IDEA
                Aseprite

                Special Thanks:
                Caffeine and Deadline
                """);

        creditsText.setStyle("""
            -fx-font-size: 30px;
            -fx-fill: white;
            -fx-font-family: 'PixelPurl';
        """);
        creditsText.setWrappingWidth(1152);
        creditsText.setTextAlignment(TextAlignment.CENTER);
        creditsText.setLayoutX(0);

        // === FINAL THANKS TEXT ===
        Text thanksText = new Text("THANK YOU FOR PLAYING!");
        thanksText.setStyle("""
            -fx-font-size: 36px;
            -fx-fill: white;
            -fx-font-family: 'PixelPurl';
        """);
        thanksText.setWrappingWidth(1152);
        thanksText.setTextAlignment(TextAlignment.CENTER);
        thanksText.setLayoutX(0);
        thanksText.setLayoutY(MAX_HEIGHT / 2.0);
        thanksText.setVisible(false);

        // === GUIDE TEXT ===
        Text guideText = new Text("Click or Press ENTER to return to Menu");
        guideText.setStyle("""
            -fx-font-size: 20px;
            -fx-fill: gray;
            -fx-font-family: 'PixelPurl';
        """);
        guideText.setWrappingWidth(1152);
        guideText.setTextAlignment(TextAlignment.CENTER);
        guideText.setLayoutX(0);
        guideText.setLayoutY(MAX_HEIGHT - 30);

        creditsPane.getChildren().addAll(creditsText, thanksText, guideText);

        // === SCROLL ANIMATION ===
        Platform.runLater(() -> {
            double paneHeight = creditsPane.getHeight();
            double textHeight = creditsText.getBoundsInLocal().getHeight();
            double startY = paneHeight + 50;
            double endY = -textHeight - 50;

            creditsText.setLayoutY(startY);

            timeline = new Timeline(
                    new KeyFrame(Duration.seconds(0),
                            new KeyValue(creditsText.layoutYProperty(), startY)),
                    new KeyFrame(Duration.seconds(20),
                            new KeyValue(creditsText.layoutYProperty(), endY))
            );

            timeline.setOnFinished(e -> {
                creditsText.setVisible(false);
                thanksText.setVisible(true);
            });

            timeline.play();

            // Key + Mouse handler
            creditsPane.requestFocus();
            creditsPane.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER) {
                    stopMusic();
                    timeline.stop();
                    returnToMenu();
                }
            });

            creditsPane.setOnMouseClicked(e -> {
                stopMusic();
                timeline.stop();
                returnToMenu();
            });
        });
    }

    /** Play background music for credits screen */
    private void playMusic() {
        try {
            URL soundUrl = getClass().getResource("/org/example/btl/M&S/credits.mp3");
            if (soundUrl != null) {
                mediaPlayer = new MediaPlayer(new Media(soundUrl.toExternalForm()));
                mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
                mediaPlayer.setVolume(0.7);
                mediaPlayer.play();
            } else {
                System.err.println("Không tìm thấy file nhạc credits.mp3");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** Stop background music */
    private void stopMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
            mediaPlayer = null;
        }
    }

    /** Return to the main menu */
    private void returnToMenu() {
        try {
            Parent menuRoot = FXMLLoader.load(Objects.requireNonNull(
                    getClass().getResource("/org/example/btl/Menu.fxml")));
            Stage stage = (Stage) creditsPane.getScene().getWindow();
            stage.setScene(new Scene(menuRoot, MAX_WIDTH, MAX_HEIGHT));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
