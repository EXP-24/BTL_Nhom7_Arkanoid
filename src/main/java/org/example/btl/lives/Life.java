package org.example.btl.lives;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import org.example.btl.game.GameObject;

public class Life extends GameObject {

    private Image image;

    public Life(double x, double y, double width, double height) {
        super(x, y, width, height);
        image = loadImage("/org/example/btl/images/life1.png");
    }

    @Override
    public void update(){
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(image, getX(), getY(), getWidth(), getHeight());
    }
}
