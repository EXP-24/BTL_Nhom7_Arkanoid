package org.example.btl.game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import static org.example.btl.GameApplication.maxHeight;
import static org.example.btl.GameApplication.maxWidth;


import java.util.List;

public class Renderer {

    private GraphicsContext gc;

    public Renderer(GraphicsContext gc) {
        this.gc = gc;
    }

    public void clear() {
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
    }

    public void renderAll(List<GameObject> objects) {
        for (GameObject object : objects) {
            object.render(gc);
        }
    }

    public void renderBackground(Image background) {
        if (background != null) {
            gc.drawImage(background, 0, 0, maxWidth, maxHeight);
        } else {
            System.err.println("Ảnh nền chưa được tải, đang vẽ nền đen mặc định.");
        }
    }
}
