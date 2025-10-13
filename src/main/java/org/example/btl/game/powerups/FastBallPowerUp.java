package org.example.btl.game.powerups;

import org.example.btl.game.GameObject;
import org.example.btl.game.Paddle;
import org.example.btl.game.Ball;

public class FastBallPowerUp extends PowerUp {
    private static final double SPEED_MULTIPLIER = 1.5;
    private Ball ball;

    public FastBallPowerUp(double x, double y, Ball ball) {
        super(x, y, "FastBall", 10000);
        this.ball = ball;
    }

    @Override
    public void applyEffect(Paddle paddle) {
        if (ball != null && ball.getSpeed() < 1.5) {
            ball.setSpeed(ball.getSpeed() * SPEED_MULTIPLIER);
        }
    }

    @Override
    public void removeEffect(Paddle paddle) {
        if (ball != null && ball.getSpeed() > 1) {
            ball.setSpeed(ball.getSpeed() / SPEED_MULTIPLIER);
        }
    }
}
