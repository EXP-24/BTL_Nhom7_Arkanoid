package org.example.btl.game.powerups;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.example.btl.game.Ball;
import org.example.btl.game.Paddle;

import java.util.List;

public class DisruptionPowerUp extends PowerUp {

    private static final double FALL_SPEED = 3.0;
    private static final double SIZE = 20.0;

    public DisruptionPowerUp(double x, double y, int duration) {
        super(x, y, "Disruption", duration);
        setWidth(SIZE);
        setHeight(SIZE);
    }

    @Override
    public void update() {
        // Power-up rơi xuống theo trục Y
        setY(getY() + FALL_SPEED);
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(Color.GOLD);
        gc.fillOval(getX(), getY(), getWidth(), getHeight());
        gc.setFill(Color.BLACK);
        gc.fillText("D", getX() + 6, getY() + 14);
    }

    @Override
    public void applyEffect(Paddle paddle) {
        // Disruption không tác động trực tiếp lên Paddle
    }

    // Khi Paddle nhặt vật phẩm => nhân bóng
    public void applyToBalls(List<Ball> balls) {
        if (balls.isEmpty()) return;

        Ball main = balls.get(0);
        Ball left = main.clone();
        Ball right = main.clone();

        // Điều chỉnh hướng để 3 bóng tách nhau
        left.setDirection(left.getDirectionX() - 1, left.getDirectionY());
        right.setDirection(right.getDirectionX() + 1, right.getDirectionY());

        balls.add(left);
        balls.add(right);
    }

    @Override
    public void removeEffect(Paddle paddle) {
    }
}
