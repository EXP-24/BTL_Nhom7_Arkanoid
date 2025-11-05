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
import org.example.btl.game.lives.LifeManage;
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
    private List<PowerUp> activePowerUps;   // PowerUp đang rơi xuống
    private List<PowerUp> appliedPowerUps;  // PowerUp đã được kích hoạt và đang có hiệu lực
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

    /**
     * Khởi tạo các thành phần chính của game.
     * Paddle, Ball, Level, PowerUp, Mạng sống
     */
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
        //levelManager.setGameWon(true);
    }

    /**
     * Xử lý sự kiện phím nhấn (di chuyển, bắn bóng, tạm dừng).
     */
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

    /**
     * Xử lý sự kiện phím nhả (dừng di chuyển).
     */
    public void handleKeyReleased(KeyEvent event) {
        if (event.getCode() == KeyCode.A) {
            leftPressed = false;
        } else if (event.getCode() == KeyCode.D) {
            rightPressed = false;
        }
    }

    /**
     * Kiểm tra xem level hiện tại đã được phá hết gạch chưa.
     * Nếu có -> reset trạng thái và chuyển sang level tiếp theo.
     */
    private void checkLevelCompletion() {
        if (levelManager.isLevelCleared()) {
            resetLevelState();
            levelManager.nextLevel();
            gameController.updateLevel(levelManager.getCurrentLevel());
        }
    }

    /**
     * Cập nhật vị trí của paddle dựa trên phím điều hướng.
     */
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

    /**
     * Cập nhật trạng thái của tất cả các quả bóng:
     * - Nếu còn dính paddle -> di chuyển theo paddle
     * - Nếu đang bay -> cập nhật vị trí, kiểm tra va chạm
     * - Nếu rơi khỏi màn -> xóa và trừ mạng
     */
    public void updateBall() {
        Iterator<Ball> ballIterator = balls.iterator();
        while (ballIterator.hasNext()){
            Ball currentball = ballIterator.next();
            // Giữ bóng trên paddle khi chưa bắn
            if (currentball.isAttached()) {
                currentball.setX(paddle.getX() + (paddle.getWidth() / 2) - currentball.getWidth()/2);
                currentball.setY(paddle.getY() - 10);
            }
            else {
                currentball.update();
                currentball.bounceOff(); // Va chạm tường
                if (currentball.isColliding(paddle)) {
                    currentball.bounce(paddle);
                    SoundManager.playBounce();
                }
                if (currentball.getY() > PLAY_AREA_Y + PLAY_AREA_HEIGHT) {
                    ballIterator.remove();
                }
            }
        }

        // Nếu không còn bóng nào -> trừ mạng và respawn bóng mới
        if (balls.isEmpty()) {
            lose();
            if (lifeManage.getLives() > 0) {
                Ball newBall = new Ball(paddle.getX() + paddle.getWidth() / 2 - 6,
                        paddle.getY() - 12, 12, 12, 2, -2, 1);
                newBall.setAttached(true);
                balls.add(newBall);
                for (PowerUp powerUp : appliedPowerUps) {
                    powerUp.applyEffect(paddle);
                }
            }
        }
    }

    /**
     * Kiểm tra va chạm giữa bóng và gạch.
     * - Nếu va chạm: gây sát thương, phát âm thanh, tạo PowerUp nếu cần.
     * - Nếu Brick boss (loại 20) vỡ -> thắng game.
     */
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

                        // Nếu là brick Boss → thắng game
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

                    // Nếu brick có PowerUp, tạo mới và thêm vào danh sách
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
                                newPowerUp = new GunPowerUp(brick.getX(), brick.getY(), scoreManager);
                                activePowerUps.add(newPowerUp);
                                break;
                            case 6:
                                newPowerUp = new SlowBallPowerUp(brick.getX(), brick.getY(), balls);
                                activePowerUps.add(newPowerUp);
                                break;
                            case 7:
                                newPowerUp = new TinyBallPowerUp(brick.getX(), brick.getY(), balls);
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

    /**
     * Cập nhật trạng thái các PowerUp đang rơi:
     * - Nếu chạm paddle → kích hoạt hiệu ứng
     * - Nếu rơi khỏi màn → xóa
     */
    public void updatePowerUp() {
        Iterator<PowerUp> powerUpIterator = activePowerUps.iterator();
        while(powerUpIterator.hasNext()) {
            PowerUp powerUp = powerUpIterator.next();
            powerUp.update();

            // Khi paddle bắt được PowerUp
            if (powerUp.isColliding(paddle)) {
                SoundManager.playPowerUpSound();
                boolean effectExist = false;

                // Nếu loại PowerUp này đã có hiệu lực -> chỉ reset thời gian
                for(PowerUp existingEffect : appliedPowerUps) {
                    if (existingEffect.getType().equals(powerUp.getType())) {
                        existingEffect.active();
                        effectExist = true;
                        break;
                    }
                }

                // Nếu là hiệu ứng mới → áp dụng
                if (!effectExist) {
                    powerUp.applyEffect(paddle);
                    powerUp.active();
                    appliedPowerUps.add(powerUp);
                }
                powerUpIterator.remove();
            }

            // Nếu rơi khỏi màn hình
            if (powerUp.getY() > PLAY_AREA_Y + PLAY_AREA_HEIGHT) {
                powerUpIterator.remove();
            }
        }
    }

    /**
     * Cập nhật các PowerUp đang hoạt động.
     * - Nếu hết thời gian -> hủy hiệu ứng
     * - Nếu là GunPowerUp -> cập nhật đạn và PowerUp rơi thêm
     */
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

    /**
     * Xử lý khi mất mạng:
     * - Giảm số mạng
     * - Nếu hết -> Game Over
     */
    public void lose() {
        lifeManage.loseLife();
        // Khi hết mạng, hiện GameOver
        if (lifeManage.getLives() <= 0) {
            scoreManager.saveCurrentScoreToBoard();
            gameController.gameOver();
        }
    }

    /**
     * Reset lại trạng thái khi qua màn:
     * - Xóa PowerUp, reset bóng
     */
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

    /**
     * Render toàn bộ game:
     * - Gọi Renderer để vẽ Map, Paddle, Ball, PowerUp
     * - Nếu thắng → hiện màn Victory
     * - Gọi riêng render() cho GunPowerUp để vẽ đạn
     */
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

        // Vẽ riêng đạn của GunPowerUp
        for (PowerUp powerUp : appliedPowerUps) {
            if (powerUp instanceof GunPowerUp) {
                ((GunPowerUp) powerUp).render(gc);
            }
        }
    }
}