package org.example.btl.controllers;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import org.example.btl.game.GameManager;


public class GameController {

    @FXML
    private Canvas canvas;
    private GameManager gameManager;

    private GraphicsContext gc;

    @FXML
    public void initialize() {
        gc = canvas.getGraphicsContext2D();
        gameManager = new GameManager(gc);

        gameManager.renderGame();
    }
}

