package org.example.btl.game.powerups;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import org.example.btl.game.MovableObject;
import org.example.btl.game.Paddle;
import org.example.btl.game.GameObject;

public abstract class PowerUp extends MovableObject {
    protected String type;
    protected long duration;
    protected Image image;
    private boolean  isActive = false;
    private long activationTime;
    public static final double POWER_UP_WIDTH = 20;
    public static final double POWER_UP_HEIGHT = 20;

    public PowerUp(double x, double y, String type, long duration) {
        super(x, y, POWER_UP_WIDTH, POWER_UP_HEIGHT);
        this.type = type;
        this.duration = duration;
        this.dy = 1;
        String imagePath = "/org/example/btl/images/PowerUp_" + type + ".png";
        this.image = loadImage(imagePath);
    }

    public String getType() {
        return type;
    }

    public void active() {
        this.activationTime = System.currentTimeMillis();
        this.isActive = true;
    }

    public boolean isExpired() {
        if (!isActive) {
            return false;
        }
        return System.currentTimeMillis() - activationTime > duration;
    }

    @Override
    public void update() {
        move();
    }

    @Override
    public void render(GraphicsContext gc) {
        if (image != null) {
            gc.drawImage(image, getX(), getY(), getWidth(), getHeight());
        }
    }

    public abstract void applyEffect(Paddle paddle);

    public abstract void removeEffect(Paddle paddle);
}
