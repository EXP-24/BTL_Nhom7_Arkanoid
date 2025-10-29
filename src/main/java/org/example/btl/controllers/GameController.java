package org.example.btl.controllers;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.example.btl.game.GameManager;

import java.io.IOException;
import java.util.Objects;

import static org.example.btl.Config.*;

public class GameController {

    @FXML
    private Canvas canvas;

    private GameManager gameManager;
    private GraphicsContext gc;
    private AnimationTimer gameLoop;
    private Stage primaryStage;
    private Scene previousScene;

    @FXML
    public void initialize() {
        gc = canvas.getGraphicsContext2D();
        gameManager = new GameManager(gc, this);

        Image mouseImage = new Image(Objects.requireNonNull(
                getClass().getResourceAsStream("/org/example/btl/images/texts/mouse.png")));
        canvas.setCursor(new ImageCursor(mouseImage));
        canvas.setFocusTraversable(true);
        canvas.setOnKeyPressed(event -> handleKeyPressed(event));
        canvas.setOnKeyReleased(event -> handleKeyRealeased(event));

        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                gameManager.updatePaddle();
                gameManager.updateBall();
                gameManager.checkBrickCollisions();
                gameManager.updatePowerUp();
                gameManager.updateAppliedPowerUp();
                gameManager.renderGame();
            }
        };
        gameLoop.start();
    }

    public void pauseGame() {
        gameLoop.stop();

        primaryStage = (Stage) canvas.getScene().getWindow();
        previousScene = primaryStage.getScene();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/btl/PauseMenu.fxml"));
            Parent pauseMenuRoot = loader.load();

            PauseMenuController pauseMenuController = loader.getController();
            pauseMenuController.setGameController(this);

            Stage stage = (Stage) canvas.getScene().getWindow();
            stage.setScene(new Scene(pauseMenuRoot, MAX_WIDTH, MAX_HEIGHT));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void resumeGame() {
        if (primaryStage != null && previousScene != null) {
            primaryStage.setScene(previousScene);
        }
        gameLoop.start();
    }

    private void handleKeyPressed(KeyEvent event) {
        gameManager.handleKeyPressed(event);
    }

    private void handleKeyRealeased(KeyEvent event) {
        gameManager.handleKeyRealeased(event);
    }
}

