package org.example.btl.game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import static org.example.btl.GameApplication.maxHeight;
import static org.example.btl.GameApplication.maxWidth;

public class Ball extends MovableObject {

    private double speed;
    private double directionX;
    private double directionY;
    private Image image;
    private boolean attached = true;

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

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getDirectionX() {
        return directionX;
    }

    public double getDirectionY() {
        return directionY;
    }

    //Kiem tra ball nam tren Paddle khong
    public boolean isAttached() {
        return attached;
    }

    public void setAttached(boolean attached) {
        this.attached = attached;
    }

    //Ball va cham voi gioi han man hinh
    public void bounceOff() {
        if (getX() <= 0 || getX() + getWidth() >= maxWidth ) {
            directionX *= -1;
        }
        if (getY() <= 0 || getY() + getHeight() >= maxHeight) {
            directionY *= -1;
        }
    }

    public void update() {
        setX(getX() + directionX * speed);
        setY(getY() + directionY * speed);
    }

    @Override

    public void render(GraphicsContext gc) {
        gc.drawImage(image, getX(), getY(), getHeight(), getWidth());
    }
    public Ball clone() {
        Ball copy = new Ball(getX(), getY(), getWidth(), getHeight(), directionX, directionY, speed);
        copy.setAttached(false);
        return copy;
    }
    public void setDirection(double dx, double dy) {
        this.directionX = dx;
        this.directionY = dy;
    }

}
