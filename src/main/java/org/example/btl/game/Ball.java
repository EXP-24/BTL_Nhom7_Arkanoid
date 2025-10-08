package org.example.btl.game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Ball extends MovableObject {

    private double speed;
    private double directionX;
    private double directionY;
    private Image image;

    public Ball(double x, double y, double width, double height,
                double directionX, double directionY, double speed) {
        super(x, y, width, height);
        this.directionX = directionX;
        this.directionY = directionY;
        this.speed = speed;
        image = new Image(getClass().getResource("/org/example/btl/images/ball.png").toExternalForm());
    }

    public double getSpeed() {
        return speed;
    }

    public double getDirectionX() {
        return directionX;
    }

    public double getDirectionY() {
        return directionY;
    }

    public void bounceOff() {

    }

    public void update() {

    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(image, getX(), getY(), getHeight(), getWidth());
    }
}
