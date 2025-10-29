package org.example.btl.controllers;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.btl.game.GameManager;
import org.example.btl.game.ScoreManager;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.example.btl.GameApplication.MAX_HEIGHT;
import static org.example.btl.GameApplication.MAX_WIDTH;

public class GameController {

    @FXML
    private Canvas canvas;

    @FXML
    private Label scoreLabel;

    @FXML
    private Label topScoreLabel;

    @FXML
    private Label score1, score2, score3, score4, score5, score6, score7, score8, score9, score10;

    @FXML
    private Label levelLabel;

    private Label[] scoreLabels;

    private GameManager gameManager;
    private ScoreManager scoreManager;
    private GraphicsContext gc;
    private boolean isWinnerScreenActive;
    private final List<Integer> scoreBoard = new ArrayList<>();

    private static final String SCORE_FILE = "score.txt";

    @FXML
    public void initialize() {
        gc = canvas.getGraphicsContext2D();
        scoreManager = new ScoreManager();

        gameManager = new GameManager(gc, this, scoreManager);

        this.scoreLabels = new Label[]{score1, score2, score3, score4, score5,
                score6, score7, score8, score9, score10};

        canvas.setCursor(Cursor.NONE);

        canvas.setFocusTraversable(true);
        canvas.setOnKeyPressed(event -> handleKeyPressed(event));
        canvas.setOnKeyReleased(event -> handleKeyRealeased(event));

        updateScoreLabels();
        displayScoresOnBoard();

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!gameManager.win()) {
                    gameManager.updatePaddle();
                    gameManager.updateBall();
                    gameManager.checkBrickCollisions();
                    gameManager.updatePowerUp();
                    gameManager.updateAppliedPowerUp();
                    gameManager.renderGame();
                    updateScoreLabels();
                }
                else if (!isWinnerScreenActive) {
                    isWinnerScreenActive = true;
                    scoreManager.saveCurrentScoreToBoard();
                    gameManager.renderGame();
                    displayScoresOnBoard();
                    updateScoreLabels();
                } else {
                    gameManager.renderGame();
                }
            }
        }.start();
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

    public void backToMenu() {
        try {
            Parent menuRoot = FXMLLoader.load(Objects.requireNonNull(
                    getClass().getResource("/org/example/btl/Menu.fxml")));
            Stage stage = (Stage) canvas.getScene().getWindow();
            stage.setScene(new Scene(menuRoot, MAX_WIDTH, MAX_HEIGHT));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleKeyPressed(KeyEvent event) {
        gameManager.handleKeyPressed(event);
    }
    private void handleKeyRealeased(KeyEvent event) {
        gameManager.handleKeyRealeased(event);
    }
}

