package org.example.btl.controllers;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.btl.game.GameManager;

import java.io.IOException;
import java.util.Objects;

public class GameController {

    public Canvas canvas;
    private GameManager gameManager;
    private GraphicsContext gc;
    private AnimationTimer gameLoop;
    private boolean isPaused = false;

    public void initialize() {
        gc = canvas.getGraphicsContext2D();
        gameManager = new GameManager(gc);

        canvas.setFocusTraversable(true);
        canvas.setOnKeyPressed(this::handleKeyPressed);
        canvas.setOnKeyReleased(this::handleKeyReleased);

        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!isPaused) {
                    gameManager.updatePaddle();
                    gameManager.updateBall();
                    gameManager.checkBrickCollisions();
                    gameManager.updatePowerUp();
                    gameManager.updateAppliedPowerUp();
                    gameManager.renderGame();
                }
            }
        };
        gameLoop.start();
    }

    private void handleKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ESCAPE) {
            togglePauseMenu();
        } else {
            gameManager.handleKeyPressed(event);
        }
    }

    private void handleKeyReleased(KeyEvent event) {
        gameManager.handleKeyRealeased(event);
    }

    /** Hiển thị hoặc ẩn menu pause */
    private void togglePauseMenu() {
        if (isPaused) {
            isPaused = false;
        } else {
            isPaused = true;
            openPauseMenu();
        }
    }

    /** Mở giao diện Pause Menu */
    private void openPauseMenu() {
        try {
            Parent pauseRoot = FXMLLoader.load(Objects.requireNonNull(
                    getClass().getResource("/org/example/btl/PauseMenu.fxml")));
            Stage pauseStage = new Stage();
            pauseStage.initModality(Modality.APPLICATION_MODAL);
            pauseStage.setScene(new Scene(pauseRoot, 400, 300));
            pauseStage.setTitle("Paused");
            pauseStage.showAndWait(); // chờ đóng
            isPaused = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
