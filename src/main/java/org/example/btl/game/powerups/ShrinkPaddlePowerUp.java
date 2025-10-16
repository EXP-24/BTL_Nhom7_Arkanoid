package org.example.btl.game.powerups;

import javafx.scene.image.Image;
import org.example.btl.game.Paddle;

public class ShrinkPaddlePowerUp extends PowerUp {
    private static final double SCALE_FACTOR = 1.5;

    public ShrinkPaddlePowerUp(double x, double y) {
        super(x, y, "Shrink", 10000);
    }

    @Override
    public void applyEffect(Paddle paddle) {
        if (paddle.getWidth() > 48) {
            paddle.setWidth(paddle.getWidth() / SCALE_FACTOR);
            Image imagePaddleShrink = loadImage("/org/example/btl/images/paddleShrink.png");
            paddle.setImage(imagePaddleShrink);
        }
    }

    @Override
    public void removeEffect(Paddle paddle) {
        if (paddle.getWidth() < 96) {
            paddle.setWidth(paddle.getWidth() * SCALE_FACTOR);
            Image imagePaddleNormal = loadImage("/org/example/btl/images/paddle.png");
            paddle.setImage(imagePaddleNormal);
        }
    }
}
