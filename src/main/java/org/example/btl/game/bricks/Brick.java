package org.example.btl.game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Brick extends GameObject {

    private Image brickImage;

    public Brick(double x, double y, double width, double height) {
        super(x, y, width, height);
        loadImage("/org/example/btl/images/Brick_Blue.jpg");
    }

    private void loadImage(String imagePath) {
        try {
            brickImage = new Image(getClass().getResourceAsStream(imagePath));
        } catch (Exception e) {
            System.err.println("Không thể tải được ảnh tại đường dẫn: " + imagePath);
            brickImage = null;
        }
    }

    @Override
    public void update() {
    }

    @Override
    public void render(GraphicsContext gc) {
            gc.drawImage(brickImage, getX(), getY(), getWidth(), getHeight());

    }
}