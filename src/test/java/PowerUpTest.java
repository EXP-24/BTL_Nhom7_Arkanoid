package org.example.btl.game.powerups; // <-- Nhớ đổi package cho đúng

import org.example.btl.GameApplication;
import org.example.btl.game.Paddle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import javafx.stage.Stage;

import static org.junit.jupiter.api.Assertions.*;

public class PowerUpTest extends ApplicationTest {
    private static class MockPowerUp extends PowerUp {
        public MockPowerUp(double x, double y, String type, long duration) {
            super(x, y, type, duration);
        }
        @Override
        public void applyEffect(Paddle paddle) {
        }

        @Override
        public void removeEffect(Paddle paddle) {
        }
    }

    private MockPowerUp powerUp;

    @Override
    public void start(Stage stage) {
    }

    @BeforeEach
    void setUp() {
        powerUp = new MockPowerUp(100, 150, "TestType", 2000);
    }

    @Test
    void testConstructorAndGetters() {
        assertEquals(100, powerUp.getX(), "X position phải là 100");
        assertEquals(150, powerUp.getY(), "Y position phải là 150");
        assertEquals("TestType", powerUp.getType(), "Type phải là 'TestType'");
        assertEquals(1, powerUp.getDy(), "Vận tốc rơi (dy) phải là 1");
    }

    @Test
    void testUpdateMovesPowerUpDown() {
        double initialY = powerUp.getY(); // Y = 150
        powerUp.update(); // Hàm này gọi move()
        assertEquals(initialY + 1, powerUp.getY(), "PowerUp nên di chuyển xuống 1 pixel");
    }

    @Test
    void testActivationAndExpirationLogic() throws InterruptedException {
        MockPowerUp shortPowerUp = new MockPowerUp(0, 0, "Short", 50);
        assertFalse(shortPowerUp.isExpired(), "Không nên hết hạn trước khi kích hoạt");
        shortPowerUp.active();
        assertFalse(shortPowerUp.isExpired(), "Không nên hết hạn ngay sau khi kích hoạt");
        Thread.sleep(60);
        assertTrue(shortPowerUp.isExpired(), "Phải hết hạn sau khi chờ 60ms");
    }

    @Test
    void testIsExpiredIsFalseIfNeverActivated() throws InterruptedException {
        MockPowerUp neverActive = new MockPowerUp(0, 0, "Never", 50);
        Thread.sleep(60);
        assertFalse(neverActive.isExpired(), "Không thể hết hạn nếu chưa bao giờ được kích hoạt");
    }
}