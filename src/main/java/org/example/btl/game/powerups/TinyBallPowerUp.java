package org.example.btl.game.powerups;

import org.example.btl.game.Ball;
import org.example.btl.game.Paddle;

public class TinyBallPowerUp extends PowerUp {
    private static final double SIZE_MULTIPLIER = 0.7;
    private Ball ball;

    public TinyBallPowerUp(double x, double y, Ball ball) {
        super(x, y, "TinyBall", 10000);
        this.ball = ball;
    }

    @Override
    public void applyEffect(Paddle paddle) {
        if (ball != null && ball.getWidth() > 16) {
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
