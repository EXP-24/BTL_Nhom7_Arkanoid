package org.example.btl.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.example.btl.game.sounds.MusicManager;

import java.io.IOException;
import java.util.Objects;

public class WinnerScreenController {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    public void initialize() {
        MusicManager.playMusic("11 Journey's End.mp3", true);
        Platform.runLater(() -> {
            if (anchorPane != null) {
                anchorPane.requestFocus();
                anchorPane.setOnKeyPressed(event -> {
                    if (event.getCode() == KeyCode.ENTER) {
                        loadCreditScreen();
                    }
                });
            } else {
                System.err.println("LỖI: anchorPane BỊ NULL! KIỂM TRA LẠI FX:ID TRONG FXML!");
            }
        });
    }

    private void loadCreditScreen() {
        MusicManager.stopMusic();
        try {
            Parent creditRoot = FXMLLoader.load(Objects.requireNonNull(
                    getClass().getResource("/org/example/btl/Credits.fxml")));

            Stage stage = (Stage) anchorPane.getScene().getWindow();
            stage.setScene(new Scene(creditRoot));

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Lỗi: Không thể tải Credits.fxml.");
        }
    }
}