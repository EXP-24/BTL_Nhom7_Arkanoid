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

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.example.btl.game.GameManager;
import org.example.btl.game.ScoreManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.example.btl.Config.*;

public class GameController {

    @FXML
    private Label scoreLabel;

    @FXML
    private Label topScoreLabel;

    @FXML
    private Label score1, score2, score3, score4, score5, score6, score7, score8, score9, score10;

    private Label[] scoreLabels;

    @FXML
    private Label levelLabel;

    @FXML
    private Canvas canvas;

    private GameManager gameManager;
    private GraphicsContext gc;
    private AnimationTimer gameLoop;
    private Stage primaryStage;
    private Scene previousScene;
    private ScoreManager scoreManager;
    private List<Integer> scoreBoard;

    private static final String SCORE_FILE = "/org/example/btl/images/texts/score.txt";

    @FXML
    public void initialize() {
        gc = canvas.getGraphicsContext2D();
        scoreManager = new ScoreManager();
        gameManager = new GameManager(gc, this, scoreManager);

        this.scoreBoard = new ArrayList<>();

        Image mouseImage = new Image(Objects.requireNonNull(
                getClass().getResourceAsStream("/org/example/btl/images/texts/mouse.png")));
        canvas.setCursor(new ImageCursor(mouseImage));
        canvas.setFocusTraversable(true);
        canvas.setOnKeyPressed(event -> handleKeyPressed(event));
        canvas.setOnKeyReleased(event -> handleKeyRealeased(event));

        this.scoreLabels = new Label[]{score1, score2, score3, score4, score5,
                score6, score7, score8, score9, score10};

        updateScoreLabels();
        displayScoresOnBoard();

        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                gameManager.updatePaddle();
                gameManager.updateBall();
                gameManager.checkBrickCollisions();
                gameManager.updatePowerUp();
                gameManager.updateAppliedPowerUp();
                gameManager.renderGame();
                updateScoreLabels();
            }
        };
        gameLoop.start();
    }

    public void updateLevel(int level) {
        levelLabel.setText(String.valueOf(level));
    }

    private void updateScoreLabels() {
        if (scoreLabel != null) {
            scoreLabel.setText("Score: " + scoreManager.getScore());
        }
        if (topScoreLabel != null) {
            topScoreLabel.setText("Top Score: " + scoreManager.getTopScore());
        }
    }

    private void displayScoresOnBoard() {
        List<Integer> scores = scoreManager.getScoreBoard();

        for (int i = 0; i < scoreLabels.length; i++) {
            if (i < scores.size()) {
                scoreLabels[i].setText(String.valueOf(scores.get(i)));
            } else {
                scoreLabels[i].setText("0");
            }
        }
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

