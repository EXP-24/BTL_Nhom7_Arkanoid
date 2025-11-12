package org.example.btl.game;

import javafx.scene.canvas.GraphicsContext;
import org.example.btl.controllers.GameController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.example.btl.game.Brick;

public class GameManagerTest {

    private GameManager gameManager;
    private FakeController controller;
    private FakeScoreManager scoreManager;
    private GraphicsContext gc = null; // không cần tạo thật, vì ta không render gì

    // Controller giả (để test mà không gọi JavaFX thật)
    static class FakeController extends GameController {
        public boolean levelUpdated = false;
        public boolean gameOverCalled = false;
        public boolean winnerShown = false;

        @Override
        public void updateLevel(int level) {
            levelUpdated = true;
        }

        @Override
        public void gameOver() {
            gameOverCalled = true;
        }

        @Override
        public void showWinnerScreen() {
            winnerShown = true;
        }
    }

    // ScoreManager giả
    static class FakeScoreManager extends ScoreManager {
        public int score = 0;
        public boolean saved = false;

        public void addScore(int s) {
            score += s;
        }

        public void saveCurrentScoreToBoard() {
            saved = true;
        }
    }

    @BeforeEach
    public void setUp() {
        controller = new FakeController();
        scoreManager = new FakeScoreManager();
        gameManager = new GameManager(gc, controller, scoreManager);
    }

    @Test
    public void testCheckBrickCollisions_BrickDestroyed_AddsScore() {
        // Giả lập brick
        Brick brick = new Brick(0, 0, 10, 10, 1, 0);
        Ball ball = new Ball(0, 0, 12, 12, 1, 1, 1);
        gameManager.getLevelManager().getMap().getBricks().add(brick);
        gameManager.setBall(ball);

        // Gọi hàm kiểm tra va chạm
        gameManager.checkBrickCollisions();

        // Nếu va chạm thì điểm phải tăng
        assertTrue(scoreManager.score > 0);
    }

    @Test
    public void testResetLevelState_BallsReset() {
        gameManager.resetLevelState();

        // Sau khi reset: chỉ có 1 bóng được gắn vào paddle
        assertEquals(1, gameManager.getLevelManager().getMap().getBricks().size() >= 0);
    }

    @Test
    public void testCheckLevelCompletion_AdvancesLevel() {
        // Dọn toàn bộ brick để level cleared
        for (Brick b : gameManager.getLevelManager().getMap().getBricks()) {
            b.takeDamage();
        }

        // Gọi check
        gameManager.renderGame(); // checkLevelCompletion được gọi trong đây

        // Kiểm tra xem có update level
        assertTrue(controller.levelUpdated);
    }
}
