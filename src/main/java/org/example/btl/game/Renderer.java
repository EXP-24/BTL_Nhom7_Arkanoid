package org.example.btl.game;

import javafx.scene.canvas.GraphicsContext;
import java.util.List;

public class Renderer {

    private GraphicsContext gc;

    public Renderer(GraphicsContext gc) {
        this.gc = gc;
    }

    public void clear() {
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
    }

    public void renderALL(List<GameObject> objects) {
        for (GameObject object : objects) {
            object.render(gc);
        }
    }
}
