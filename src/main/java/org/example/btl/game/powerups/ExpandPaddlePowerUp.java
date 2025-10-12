package org.example.btl.game.powerups;

import javafx.scene.image.Image;
import  org.example.btl.game.Paddle;
import  org.example.btl.game.powerups.PowerUp;


public class ExpandPaddlePowerUp extends PowerUp {
    private static final double SCALE_FACTOR = 1.5;

    public ExpandPaddlePowerUp(double x, double y) {
        super(x, y, "Expand", 0);
    }

    @Override
    public void applyEffect(Paddle paddle) {
        if (paddle.getWidth() < 96) { // Set a max width
            paddle.setWidth(paddle.getWidth() * SCALE_FACTOR);
            Image imagePaddleExpland = loadImage("/org/example/btl/images/paddleExpland.png");
            paddle.setImage(imagePaddleExpland);
        }
    }

    @Override
    public void removeEffect(Paddle paddle) {
        paddle.setWidth((paddle.getWidth() / SCALE_FACTOR));
    }
}
