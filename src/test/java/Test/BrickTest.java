package org.example.btl.game.bricks;

import org.example.btl.game.Brick;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import javafx.stage.Stage;

import static org.junit.jupiter.api.Assertions.*;
public class BrickTest extends ApplicationTest {

    private Brick standardBrick;
    private Brick strongBrick;
    private Brick indestructibleBrick;

    @Override
    public void start(Stage stage) {
        // Để trống
    }

    @BeforeEach
    void setUp() {
        // Arrange (Chuẩn bị) các loại gạch khác nhau

        // Gạch thường, 1 máu
        standardBrick = new Brick(100, 100, 50, 20, 1, 1);

        // Gạch "cứng", 2 máu
        strongBrick = new Brick(200, 100, 50, 20, 8, 2);

        // Gạch "bất tử"
        indestructibleBrick = new Brick(300, 100, 50, 20, 9, 0);
    }

    @Test
    void testConstructorAndGetters() {
        assertEquals(1, standardBrick.getBrickType());
        assertEquals(1, standardBrick.getPowerUpType());
    }

    @Test
    void testStandardBrickIsDestroyedAfterOneHit() {
        assertFalse(standardBrick.isDestroyed());
        standardBrick.takeDamage();
        assertTrue(standardBrick.isDestroyed());
    }

    @Test
    void teststrongBrickIsDestroyedAfterTwoHits() {
        assertFalse(strongBrick.isDestroyed());
        strongBrick.takeDamage();
        strongBrick.takeDamage();
        assertTrue(strongBrick.isDestroyed());
    }

    @Test
    void testIndestructibleBrickNeverDies() {
        assertFalse(indestructibleBrick.isDestroyed());
        // ban 100 vien dan vao gach khong the pha huy
        for (int i = 0; i < 100; i++) {
            indestructibleBrick.takeDamage();
        }
        assertFalse(indestructibleBrick.isDestroyed());
    }

    @Test
    void testTakeDamageDoesNothingWhenAlreadyDestroyed() {
        standardBrick.takeDamage();
        assertTrue(standardBrick.isDestroyed());
        standardBrick.takeDamage();

        assertTrue(standardBrick.isDestroyed());
    }
}