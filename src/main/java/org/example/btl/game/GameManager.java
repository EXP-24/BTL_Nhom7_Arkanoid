package org.example.btl.game;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class GameManager {
    private Renderer renderer;
    private Paddle paddle;
    private Ball ball;
    private List<GameObject> objects;
    private boolean leftPressed = false;
    private boolean rightPressed = false;

    public GameManager(GraphicsContext gc) {
        this.renderer = new Renderer(gc);
        initGame();
    }

    private void initGame() {
        paddle = new Paddle(350, 550, 96, 32);
        ball = new Ball(350, 526, 24, 24);
    }

    public void handleKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.A) {
            leftPressed = true;
        } else if (event.getCode() == KeyCode.D) {
            rightPressed = true;
        }
    }

    public void handleKeyRealesed(KeyEvent event) {
        if (event.getCode() == KeyCode.A) {
            leftPressed = false;
        } else if (event.getCode() == KeyCode.D) {
            rightPressed = false;
        }
    }

    public void updateGame() {
        if (leftPressed) {
            paddle.moveLeft();
        }
        if (rightPressed) {
            paddle.moveRight();
        }
    }

    public void renderGame() {
        objects = new ArrayList<>();
        objects.add(paddle);
        objects.add(ball);
        renderer.clear();
        renderer.renderAll(objects);
    }
}
