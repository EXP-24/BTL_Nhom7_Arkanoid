package org.example.btl.game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Brick extends GameObject {

    private Image brickImage;
    private int brickType;
    private int powerUpType;

    public Brick(double x, double y, double width, double height, int brickType, int powerUpType) {
        super(x, y, width, height);
        String colorName = "";
        this.brickType = brickType;
        this.powerUpType = powerUpType;
        switch (brickType) {
            case 1:
                colorName = "Blue";
                break;
            case 2:
                colorName = "Yellow";
                break;
            case 3:
                colorName = "Green";
                break;
            case 4:
                colorName = "Purple";
                break;
            case 5:
                colorName = "Orange";
                break;
            default:
                colorName = "Red";
                break;
        }


        String imagePath = "/org/example/btl/images/Brick_" + colorName + ".png";
        brickImage = loadImage(imagePath);
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