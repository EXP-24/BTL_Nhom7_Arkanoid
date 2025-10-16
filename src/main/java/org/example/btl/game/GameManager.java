package org.example.btl.game;

import org.example.btl.game.bricks.MapBrick;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.example.btl.game.powerups.*;
import org.example.btl.lives.Life;
import org.example.btl.game.Brick;
import org.example.btl.lives.LifeManage;

import static org.example.btl.GameApplication.*;

public class GameManager {

    private Renderer renderer;
    private Paddle paddle;
    private Ball ball;
    private MapBrick map;
    private LifeManage lifeManage;
    private List<GameObject> objects;
    private List<PowerUp> activePowerUps;
    private List<PowerUp> appliedPowerUps;
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
        activePowerUps = new ArrayList<>();
        appliedPowerUps = new ArrayList<>();
        lifeManage = new LifeManage(5);
        background = new Image(Objects.requireNonNull(getClass().getResource("/org/example/btl/images/background.png")).toExternalForm());
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
        if (leftPressed && !rightPressed) {
            paddle.startMovingLeft();
        } else if (rightPressed && !leftPressed) {
            paddle.startMovingRight();
        } else {
            paddle.stop();
        }
        paddle.update();
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
        lose();
    }

    public void checkBrickCollisions() {
        Iterator<Brick> brickIterator = map.getBricks().iterator();
        while (brickIterator.hasNext()) {
            Brick brick = brickIterator.next();
            if (ball.isColliding(brick)) {
                ball.bounce(brick);

                if (brick.getBrickType() == 2) {
                    PowerUp newPowerUp;
                    switch (brick.getPowerUpType()) {
                        /*case 7:
                            newPowerUp = new ExpandPaddlePowerUp(brick.getX(), brick.getY());
                            activePowerUps.add(newPowerUp);
                            break;
                        case 8:
                            newPowerUp = new FastBallPowerUp(brick.getX(), brick.getY(), ball);
                            activePowerUps.add(newPowerUp);
                            break;*/
                        case 2:
                            newPowerUp = new ShrinkPaddlePowerUp(brick.getX(), brick.getY());
                            activePowerUps.add(newPowerUp);
                        case 1:
                            newPowerUp = new GunPowerUp(brick.getX(), brick.getY());
                            activePowerUps.add(newPowerUp);
                    }
                }

                brickIterator.remove();
            }
        }
    }

    public void updatePowerUp() {
        Iterator<PowerUp> powerUpIterator = activePowerUps.iterator();
        while(powerUpIterator.hasNext()) {
            PowerUp powerUp = powerUpIterator.next();
            powerUp.update();

            if (powerUp.isColliding(paddle)) {
                boolean effectExist = false;

                for(PowerUp existingEffect : appliedPowerUps) {
                    if (existingEffect.getType().equals(powerUp.getType())) {
                        existingEffect.active();
                        effectExist = true;
                        break;
                    }
                }
                if (!effectExist) {
                    powerUp.applyEffect(paddle);
                    powerUp.active();
                    appliedPowerUps.add(powerUp);
                }
                powerUpIterator.remove();
            }
            if (powerUp.getY() > PLAY_AREA_Y + PLAY_AREA_HEIGHT) {
                powerUpIterator.remove();
            }
        }
    }

    public void updateAppliedPowerUp() {
        Iterator<PowerUp> powerUpIterator = appliedPowerUps.iterator();
        while (powerUpIterator.hasNext()) {
            PowerUp powerUp = powerUpIterator.next();
            if (powerUp.isExpired()) {
                powerUp.removeEffect(paddle);
                powerUpIterator.remove();
            }
        }
    }

    public void lose() {
        lifeManage.loseLife(ball);
    }

    public void renderGame() {
        objects = new ArrayList<>();
        objects.addAll(lifeManage.getLiveIcons());
        objects.add(paddle);
        objects.add(ball);
        objects.addAll(activePowerUps);
        renderer.clear();
        renderer.renderBackground(background);
        renderer.renderMap(map);
        renderer.renderAll(objects);
    }
}
