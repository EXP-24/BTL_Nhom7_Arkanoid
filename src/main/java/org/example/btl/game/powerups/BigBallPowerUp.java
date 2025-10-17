package org.example.btl.game.powerups;

import org.example.btl.game.Ball;
import org.example.btl.game.Paddle;

public class BigBallPowerUp extends PowerUp {
    private static final double SIZE_MULTIPLIER = 1.5;
    private Ball ball;

    public BigBallPowerUp(double x, double y, Ball ball) {
        super(x, y, "BigBall", 10000);
        this.ball = ball;
    }

    @Override
    public void applyEffect(Paddle paddle) {
        if (ball != null && ball.getWidth() < 96) {
            double newWidth = ball.getWidth() * SIZE_MULTIPLIER;
            double newHeight = ball.getHeight() * SIZE_MULTIPLIER;
            ball.setWidth(newWidth);
            ball.setHeight(newHeight);
        }
    }

    @Override
    public void removeEffect(Paddle paddle) {
        if (ball != null) {
            double oldWidth = ball.getWidth() / SIZE_MULTIPLIER;
            double oldHeight = ball.getHeight() / SIZE_MULTIPLIER;
            ball.setWidth(oldWidth);
            ball.setHeight(oldHeight);
        }
    }
}
