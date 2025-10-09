package org.example.btl.game;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.example.btl.game.Brick;
import static org.example.btl.GameApplication.maxHeight;
import static org.example.btl.GameApplication.maxWidth;

public class GameManager {
    private Renderer renderer;
    private Paddle paddle;
    private Ball ball;
    private Brick brick;
    private List<GameObject> objects;
    private boolean leftPressed = false;
    private boolean rightPressed = false;

    public GameManager(GraphicsContext gc) {
        this.renderer = new Renderer(gc);
        initGame();
    }

    private void initGame() {
        paddle = new Paddle(300, 550, 86, 24, 3);
        ball = new Ball(0, 0, 16, 16, 2, -2, 1);
        brick = new Brick(0, 0, 32, 16);
    }

    public void handleKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.A) {
            leftPressed = true;
        } else if (event.getCode() == KeyCode.D) {
            rightPressed = true;
        } else if (event.getCode() == KeyCode.SPACE) {
            ball.setAttached(false);
        }
    }

    public void handleKeyRealesed(KeyEvent event) {
        if (event.getCode() == KeyCode.A) {
            leftPressed = false;
        } else if (event.getCode() == KeyCode.D) {
            rightPressed = false;
        }
    }

    public void updatePaddle() {
        if (leftPressed) {
            paddle.moveLeft();
        }
        if (rightPressed) {
            paddle.moveRight();
        }
    }

    public void updateBall() {
        if (ball.isAttached()) {
            ball.setX(paddle.getX() + (paddle.getWidth() / 2) - ball.getWidth()/2);
            ball.setY(paddle.getY() - 14);
        }
        else {
            ball.update();
            ball.bounceOff();
        }
    }

    public void renderGame() {
        objects = new ArrayList<>();
        objects.add(paddle);
        objects.add(ball);
        objects.add(brick);
        renderer.clear();
        renderer.renderAll(objects);
    }
}
