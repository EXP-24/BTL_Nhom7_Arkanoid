package org.example.btl.game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.scene.image.Image;
import org.example.btl.game.Brick;
import org.example.btl.game.bricks.MapBrick;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import static org.example.btl.GameApplication.*;

public class GameManager {
    private Renderer renderer;
    private Paddle paddle;
    private Ball ball;
    private MapBrick map;
    private List<GameObject> objects;
    private Image background;
    private boolean leftPressed = false;
    private boolean rightPressed = false;

    private GraphicsContext gc;

    public GameManager(GraphicsContext gc) {
        this.renderer = new Renderer(gc);
        this.gc = gc;
        initGame();
    }

    private void initGame() {
        paddle = new Paddle(540, 614, 64, 24, 3);
        ball = new Ball(0, 0, 12, 12, 2, -2, 1);
        map = new MapBrick();
        int[][] level1Layout = MapBrick.loadMap("/org/example/btl/Map/Map1.txt");
        background = new Image(getClass().getResource("/org/example/btl/images/background.png").toExternalForm());
        map.createMap(level1Layout, PLAY_AREA_X, PLAY_AREA_Y);
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
            ball.setY(paddle.getY() - 10);
        }
        else {
            ball.update();
            ball.bounceOff();
            if (ball.isColliding(paddle)) {
                ball.bounce(paddle);
            }
        }
    }

    public void checkBrickCollisions() {
        Iterator<Brick> brickIterator = map.getBricks().iterator();
        while (brickIterator.hasNext()) {
            Brick brick = brickIterator.next();
            if (ball.isColliding(brick)) {
                ball.bounce(brick);
                brickIterator.remove();
            }
        }
    }

    public void renderGame() {
        objects = new ArrayList<>();
        objects.add(paddle);
        objects.add(ball);
        renderer.clear();
        renderer.renderBackground(background);
        renderer.renderMap(map);
        renderer.renderAll(objects);
    }
}
