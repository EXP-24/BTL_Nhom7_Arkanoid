package org.example.btl.game;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.canvas.GraphicsContext;

import java.awt.*;

public abstract class GameObject {

    private double x;
    private double y;
    private double width;
    private double height;

    public GameObject(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public GameObject(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, (int) width, (int) height);
    }

    public boolean isColliding(GameObject orther) {
        return getBounds().intersects(orther.getBounds());
    }

    public Image loadImage(String imagePath) {
        Image image;
        try {
            image = new Image(getClass().getResourceAsStream(imagePath));
        } catch (Exception e) {
            System.err.println("Không thể tải được ảnh tại đường dẫn: " + imagePath);
            image = null;
        }
        return image;
    }

    public abstract void update();
    public abstract void render(GraphicsContext gc);
}
