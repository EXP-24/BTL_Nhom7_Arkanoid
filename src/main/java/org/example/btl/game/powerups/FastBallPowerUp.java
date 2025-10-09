package org.example.btl.game.powerups;

import org.example.btl.game.Paddle;
import org.example.btl.game.Ball;

public class FastBallPowerUp extends PowerUp {
    private static final double SPEED_MULTIPLIER = 1.5;
    private Ball ball;

    public FastBallPowerUp(double x, double y, int duration, Ball ball) {
        super(x, y, "FastBall", duration);
        this.ball = ball;
        setWidth(20);
        setHeight(20);
    }

    @Override
    public void applyEffect(Paddle paddle) {
        if (ball != null) {
            ball.setSpeed(ball.getSpeed() * SPEED_MULTIPLIER);
        }
    }

    @Override
    public void removeEffect(Paddle paddle) {
        if (ball != null) {
            ball.setSpeed(ball.getSpeed() / SPEED_MULTIPLIER);
        }
    }

    @Override
    public void update() {
        setY(getY() + 2);
    }

    @Override
    public void render(javafx.scene.canvas.GraphicsContext gc) {
        gc.setFill(javafx.scene.paint.Color.RED);
        gc.fillOval(getX(), getY(), getWidth(), getHeight());
    }
}
