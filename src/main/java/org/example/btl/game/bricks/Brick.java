package org.example.btl.game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Brick extends GameObject {
    protected Image brickImage;
    protected int brickType;
    protected int powerUpType;
    protected int healthBrick;

    public Brick(double x, double y, double width, double height, int brickType, int powerUpType) {
        super(x, y, width, height);
        this.brickType = brickType;
        this.powerUpType = powerUpType;

        switch (brickType) {
            case 1,2,3,4,5:
                this.healthBrick = 1;
                break;
            case 7,8:
                this.healthBrick = 2;
                break;
            case 9:
                this.healthBrick = 999999;
                break;
            default:
                this.healthBrick = 1;
                break;
        }
    }

    public void takeDamage() {
        if (healthBrick > 0 && healthBrick < 999999) {
            healthBrick--;
        }
    }


    public boolean isDestroyed() {
        return healthBrick <= 0;
    }

    public int getBrickType() {
        return brickType;
    }

    public int getPowerUpType() {
        return powerUpType;
    }

    @Override
    public void update() {}

    @Override
    public void render(GraphicsContext gc) {
        if (brickImage != null) {
            gc.drawImage(brickImage, getX(), getY(), getWidth(), getHeight());
        } else {
            System.err.println("Ảnh Brick chưa được tải.");
        }
    }

    protected Image loadBrickImage(String colorName) {
        return loadImage("/org/example/btl/images/bricks/Brick_" + colorName + ".png");
    }
}
