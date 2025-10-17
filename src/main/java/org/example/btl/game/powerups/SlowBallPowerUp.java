package org.example.btl.game.powerups;

import org.example.btl.game.Ball;
import org.example.btl.game.Paddle;

public class SlowBallPowerUp extends PowerUp {
    private static final double SPEED_MULTIPLIER = 0.7;
    private Ball ball;

    public SlowBallPowerUp(double x, double y, Ball ball) {
        super(x, y, "SlowBall", 10000);
        this.ball = ball;
    }

    @Override
    public void applyEffect(Paddle paddle) {
        if (ball != null && ball.getSpeed() > 0.8) {
            ball.setSpeed(ball.getSpeed() * SPEED_MULTIPLIER);
        }
    }

    @Override
    public void removeEffect(Paddle paddle) {
        if (ball != null) {
            ball.setSpeed(ball.getSpeed() / SPEED_MULTIPLIER);
        }
    }
}
