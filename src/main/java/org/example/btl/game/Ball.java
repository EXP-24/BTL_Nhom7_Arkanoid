package org.example.btl.game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Ball extends MovableObject {

    private double speed;
    private double directionX;
    private double directionY;
    private Image image;

    public Ball(double x, double y, double width, double height) {
        super(x, y, width, height);
        image = new Image(getClass().getResource("/org/example/btl/images/ball.png").toExternalForm());
    }

    public void update() {

    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(image, getX(), getY(), getHeight(), getWidth());
    }
}
