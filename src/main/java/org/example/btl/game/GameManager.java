package org.example.btl.game;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.canvas.GraphicsContext;

public class GameManager {
    private Renderer renderer;
    private Paddle paddle;
    private Ball ball;
    private List<GameObject> objects;

    public GameManager(GraphicsContext gc) {
        this.renderer = new Renderer(gc);
        initGame();
    }

    private void initGame() {
        paddle = new Paddle(350, 550, 96, 32);
        ball = new Ball(350, 526, 24, 24);
    }

    public void renderGame() {
        objects = new ArrayList<>();
        objects.add(paddle);
        objects.add(ball);
        renderer.renderALL(objects);
    }
}
