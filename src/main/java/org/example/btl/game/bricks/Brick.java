package org.example.btl.game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import org.example.btl.game.GameObject;

import java.security.PublicKey;

public class Brick extends GameObject {

    private Image brickImage;
    private int brickType;
    private int powerUpType;
    public  int healthBrick = 1;

    public Brick(double x, double y, double width, double height, int brickType, int powerUpType) {
        super(x, y, width, height);
        String colorName = "";
        this.brickType = brickType;
        this.powerUpType = powerUpType;
        switch (brickType) {
            case 1:
                colorName = "Blue";
                this.healthBrick = 1;
                break;
            case 2:
                colorName = "Yellow";
                this.healthBrick = 1;
                break;
            case 3:
                colorName = "Green";
                this.healthBrick = 1;
                break;
            case 4:
                colorName = "Purple";
                this.healthBrick = 1;
                break;
            case 5:
                colorName = "Orange";
                this.healthBrick = 1;
                break;
            case 7:
                colorName = "strong1";
                this.healthBrick = 2;
                break;
            case 8:
                colorName = "strong1";
                this.healthBrick = 2;
                break;
            case 9:
                colorName = "unbreak";
                this.healthBrick = 999999;
                break;
            default:
                colorName = "Red";
                this.healthBrick = 1;
                break;
        }


        String imagePath = "/org/example/btl/images/Brick_" + colorName + ".png";
        brickImage = loadImage(imagePath);
    }


    public void takeDamage() {
        if (this.healthBrick > 0 && this.healthBrick < 999999) {
            this.healthBrick--;
        }
    }

    public boolean isDestroyed() {
        return this.healthBrick <= 0;
    }


    public int getBrickType() {
        return brickType;
    }

    public int getPowerUpType() {
        return powerUpType;
    }


    @Override
    public void update() {
    }

    @Override
    public void render(GraphicsContext gc) {
        if (brickImage != null) {
            gc.drawImage(brickImage, getX(), getY(), getWidth(), getHeight());
        }
        else {
            System.err.println("Ảnh chưa được tải.");
        }
    }
}