package org.example.btl.game.powerups;

import org.example.btl.game.Paddle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PowerUpTest {

    // PowerUp giả lập chỉ để test logic
    private static class MockPowerUp extends PowerUp {
        public MockPowerUp(double x, double y, String type, long duration) {
            super(x, y, type, duration);
        }

        @Override
        public void applyEffect(Paddle paddle) {
            // Không làm gì
        }

        @Override
        public void removeEffect(Paddle paddle) {
            // Không làm gì
        }
    }

    private MockPowerUp powerUp;

    @BeforeEach
    void setUp() {
        powerUp = new MockPowerUp(100, 150, "Gun", 2000);
    }

    @Test
    void testConstructorAndGetters() {
        assertEquals(100, powerUp.getX(), "X position phải là 100");
        assertEquals(150, powerUp.getY(), "Y position phải là 150");
        assertEquals("Gun", powerUp.getType(), "Type phải là 'Gun'");
        assertEquals(1, powerUp.getDy(), "Vận tốc rơi (dy) phải là 1");
    }

    @Test
    void testUpdateMovesPowerUpDown() {
        double initialY = powerUp.getY();
        powerUp.update(); // Gọi move()
        assertEquals(initialY + 1, powerUp.getY(), "PowerUp nên di chuyển xuống 1 pixel");
    }

    @Test
    void testActivationAndExpirationLogic() throws InterruptedException {
        MockPowerUp shortPowerUp = new MockPowerUp(0, 0, "Short", 50);
        assertFalse(shortPowerUp.isExpired(), "Chưa active → không hết hạn");
        shortPowerUp.active();
        assertFalse(shortPowerUp.isExpired(), "Ngay sau khi active → không hết hạn");
        Thread.sleep(60);
        assertTrue(shortPowerUp.isExpired(), "Sau khi chờ > duration → phải hết hạn");
    }

    @Test
    void testIsExpiredIsFalseIfNeverActivated() throws InterruptedException {
        MockPowerUp neverActive = new MockPowerUp(0, 0, "Never", 50);
        Thread.sleep(60);
        assertFalse(neverActive.isExpired(), "Chưa active → không thể hết hạn");
    }
}
