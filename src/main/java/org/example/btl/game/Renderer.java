package org.example.btl.game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import org.example.btl.game.bricks.MapBrick;


import java.util.List;
import java.util.Map;

import static org.example.btl.GameApplication.*;

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

    public void renderMap(MapBrick map) {
        if (map == null) return;
        for (Brick brick : map.getBricks()) {
            brick.render(gc);
        }
    }
}
