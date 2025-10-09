package org.example.btl.game.powerups;

import  org.example.btl.game.Paddle;
import  org.example.btl.game.powerups.PowerUp;


public abstract class ExpandPaddlePowerUp extends PowerUp {
    private static final double SCALE_FACTOR = 1.5;

    public ExpandPaddlePowerUp(int x, int y, int duration) {
        super(x, y, "Expand", duration);
    }

    @Override
    public void applyEffect(Paddle paddle) {
        paddle.setWidth((int) (paddle.getWidth() * SCALE_FACTOR));
    }

    @Override
    public void removeEffect(Paddle paddle) {
        paddle.setWidth((int) (paddle.getWidth() / SCALE_FACTOR));
    }
}

