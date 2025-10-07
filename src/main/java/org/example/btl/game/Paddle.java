package org.example.btl.game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import static org.example.btl.GameApplication.maxWidth;

public class Paddle extends MovableObject {
    private double speed = 3;
    private Image image;

    public Paddle(double x, double y, double width, double height) {
        super(x, y, width, height);
        image = new Image(getClass().getResource("/org/example/btl/images/paddle.png").toExternalForm());
    }

    public void moveLeft() {
        setX(getX() - speed);
        if (getX() < 0) {
            setX(0);
        }
    }

    public void moveRight() {
        if (getX() + getWidth() < maxWidth) {
            setX(getX() + speed);
        }
    }


    public void update() {
        move();
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(image, getX(), getY(), getWidth(), getHeight());
    }
}
