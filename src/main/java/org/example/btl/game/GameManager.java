package org.example.btl.game;

import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.example.btl.game.Brick;
import org.example.btl.game.bricks.MapBrick;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import static org.example.btl.GameApplication.maxHeight;
import static org.example.btl.GameApplication.maxWidth;

public class GameManager {
    private Renderer renderer;
    private Paddle paddle;
    private Ball ball;
    private MapBrick map;
    private List<GameObject> objects;
    private boolean leftPressed = false;
    private boolean rightPressed = false;

    private GraphicsContext gc;

    public GameManager(GraphicsContext gc) {
        this.renderer = new Renderer(gc);
        this.gc = gc;
        initGame();
    }

    private void initGame() {
        paddle = new Paddle(300, 550, 86, 24, 3);
        ball = new Ball(0, 0, 16, 16, 2, -2, 1);
        map = new MapBrick();
        int[][] level1Layout = MapBrick.loadMap("/org/example/btl/Map/Map1.txt");

        map.createMap(level1Layout);
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
        for (Brick brick : map.getBricks()) {
            objects.add(brick);
        }
        renderer.clear();
        renderer.renderAll(objects);
    }
}
