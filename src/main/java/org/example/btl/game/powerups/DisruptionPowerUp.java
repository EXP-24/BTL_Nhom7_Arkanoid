package org.example.btl.game.powerups;

import org.example.btl.game.Ball;
import org.example.btl.game.Paddle;

import java.util.List;

public class DisruptionPowerUp extends PowerUp {
    private List<Ball> balls;
    public DisruptionPowerUp(double x, double y, List<Ball> balls) {
        super(x, y, "Disruption", 0);
        this.balls = balls;
    }

    @Override
    public void applyEffect(Paddle paddle) {
        if (balls == null || balls.isEmpty()) return;

        Ball mainBall = balls.get(0);
        if (balls.size() == 1) {
            Ball ballLeft = cloneBall(mainBall, -0.7, -0.7);
            Ball ballRight = cloneBall(mainBall, 0.7, -0.7);

            balls.add(ballLeft);
            balls.add(ballRight);
        }
    }

    private Ball cloneBall(Ball original, double dirX, double dirY) {
        Ball newBall = new Ball(
                original.getX(),
                original.getY(),
                original.getWidth(),
                original.getHeight(),
                dirX,
                dirY,
                original.getSpeed()
        );
        newBall.setAttached(false);
        return newBall;
    }

    @Override
    public void removeEffect(Paddle paddle) {

    }
}
