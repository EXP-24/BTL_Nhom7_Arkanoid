package org.example.btl.game;

import org.example.btl.controllers.GameController;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.example.btl.game.powerups.*;
import org.example.btl.game.sounds.SoundManager;
import org.example.btl.lives.LifeManage;
import org.example.btl.game.Brick;

import static org.example.btl.Config.*;

public class GameManager {

    private Renderer renderer;
    private Paddle paddle;
    private Ball ball;
    private List<Ball> balls;
    private LevelManager levelManager;
    private LifeManage lifeManage;
    private List<GameObject> objects;
    private List<PowerUp> activePowerUps;
    private List<PowerUp> appliedPowerUps;
    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private GraphicsContext gc;
    private GameController gameController;
    private ScoreManager scoreManager;

    public GameManager(GraphicsContext gc, GameController gameController, ScoreManager scoreManager) {
        this.renderer = new Renderer(gc);
        this.gc = gc;
        this.gameController = gameController;
        this.scoreManager = scoreManager;
        initGame();
    }

    private void initGame() {
        paddle = new Paddle(540, 614, 64, 24, 3);
        ball = new Ball(0, 0, 12, 12, 2, -2, 1);
        balls = new ArrayList<>();
        balls.add(ball);
        levelManager = new LevelManager();
        levelManager.loadLevel(levelManager.getCurrentLevel());
        activePowerUps = new ArrayList<>();
        appliedPowerUps = new ArrayList<>();
        lifeManage = new LifeManage(START_LIVES);
    }

    public void handleKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.A) {
            leftPressed = true;
        } else if (event.getCode() == KeyCode.D) {
            rightPressed = true;
        } else if (event.getCode() == KeyCode.SPACE) {
            for (Ball b : balls) {
                if (b.isAttached()) {
                    b.setAttached(false);
                }
            }
        } else if (event.getCode() == KeyCode.ESCAPE) {
            gameController.pauseGame();
        }
    }

    public void handleKeyReleased(KeyEvent event) {
        if (event.getCode() == KeyCode.A) {
            leftPressed = false;
        } else if (event.getCode() == KeyCode.D) {
            rightPressed = false;
        }
    }

    private void checkLevelCompletion() {
        if (levelManager.isLevelCleared()) {
            resetLevelState();
            levelManager.nextLevel();
            gameController.updateLevel(levelManager.getCurrentLevel());
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
        Iterator<Ball> ballIterator = balls.iterator();
        while (ballIterator.hasNext()){
            Ball currentball = ballIterator.next();
            if (currentball.isAttached()) {
                currentball.setX(paddle.getX() + (paddle.getWidth() / 2) - currentball.getWidth()/2);
                currentball.setY(paddle.getY() - 10);
            }
            else {
                currentball.update();
                currentball.bounceOff();
                if (currentball.isColliding(paddle)) {
                    currentball.bounce(paddle);
                    SoundManager.playBounce();
                }
                if (currentball.getY() > PLAY_AREA_Y + PLAY_AREA_HEIGHT) {
                    ballIterator.remove();
                }
            }
        }

        if (balls.isEmpty()) {
            lose();
            if (lifeManage.getLives() > 0) {
                Ball newBall = new Ball(paddle.getX() + paddle.getWidth() / 2 - 6,
                        paddle.getY() - 12, 12, 12, 2, -2, 1);
                newBall.setAttached(true);
                balls.add(newBall);
            }
        }
    }

    public void checkBrickCollisions() {
        Iterator<Brick> brickIterator = levelManager.getMap().getBricks().iterator();
        while (brickIterator.hasNext()) {
            Brick brick = brickIterator.next();
            if (brick.isDestroyed()) {
                continue;
            }

            for (Ball ball : balls) {
                if (ball.isColliding(brick)) {
                    ball.bounce(brick);
                    brick.takeDamage();
                    if (brick.isDestroyed()) {
                        SoundManager.playBrickDestroySound();

                        scoreManager.addScore(100);
                        if (brick.getBrickType() == 20) {
                            //khi brick boss vỡ
                            levelManager.setGameWon(true);
                            return;
                        }
                    }
                    else {
                        SoundManager.playBrickHitSound();
                        scoreManager.addScore(50);
                    }

                    if (brick.getBrickType() == 2) {
                        PowerUp newPowerUp;
                        switch (brick.getPowerUpType()) {
                            case 1:
                                newPowerUp = new ShrinkPaddlePowerUp(brick.getX(), brick.getY());
                                activePowerUps.add(newPowerUp);
                                break;
                            case 2:
                                newPowerUp = new ExpandPaddlePowerUp(brick.getX(), brick.getY());
                                activePowerUps.add(newPowerUp);
                                break;
                            case 3:
                                newPowerUp = new TripleBallPowerUp(brick.getX(), brick.getY(), balls);
                                activePowerUps.add(newPowerUp);
                                break;
                            case 4:
                                newPowerUp = new FastBallPowerUp(brick.getX(), brick.getY(), balls);
                                activePowerUps.add(newPowerUp);
                                break;
                            case 5:
                                newPowerUp = new GunPowerUp(brick.getX(), brick.getY());
                                activePowerUps.add(newPowerUp);
                                break;
                        }
                    }
                }
                if (brick.isDestroyed()) {
                    brickIterator.remove();
                    break;
                }
            }
        }
    }

    public void updatePowerUp() {
        Iterator<PowerUp> powerUpIterator = activePowerUps.iterator();
        while(powerUpIterator.hasNext()) {
            PowerUp powerUp = powerUpIterator.next();
            powerUp.update();

            if (powerUp.isColliding(paddle)) {
                SoundManager.playPowerUpSound();
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
            else if (powerUp instanceof GunPowerUp) {
                GunPowerUp gun = (GunPowerUp) powerUp;
                gun.updateWhileActive(paddle, levelManager.getMap().getBricks(), balls);
                activePowerUps.addAll(gun.consumePendingDrops());
            }
        }
    }

    public void lose() {
        lifeManage.loseLife();
        // Khi hết mạng, hiện GameOver
        if (lifeManage.getLives() <= 0) {
            scoreManager.saveCurrentScoreToBoard();
            gameController.gameOver();
        }
    }


    public void resetLevelState() {
        for (PowerUp powerUp : appliedPowerUps) {
            powerUp.removeEffect(paddle);
        }
        appliedPowerUps.clear();
        activePowerUps.clear();
        balls.clear();

        Ball newBall;
        newBall = new Ball(0, 0, 12, 12, 2, -2, 1);
        newBall.setAttached(true);
        balls.add(newBall);
    }

    public void renderGame() {
        if (levelManager.isGameWon()) {
            scoreManager.saveCurrentScoreToBoard();
            gameController.showWinnerScreen();
        }
        objects = new ArrayList<>();
        objects.addAll(lifeManage.getLiveIcons());
        objects.add(paddle);
        objects.addAll(balls);
        objects.addAll(activePowerUps);
        renderer.clear();
        renderer.renderMap(levelManager.getMap());
        renderer.renderAll(objects);
        checkLevelCompletion();

        for (PowerUp powerUp : appliedPowerUps) {
            if (powerUp instanceof GunPowerUp) {
                ((GunPowerUp) powerUp).render(gc);
            }
        }
    }
}