package org.example.btl.game.powerups;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import org.example.btl.game.Bullet;
import org.example.btl.game.Paddle;
import org.example.btl.game.GameManager;

import java.util.ArrayList;
import java.util.List;

public class GunPowerUp extends PowerUp {
    private static final long FIRE_INTERVAL = 300; // thời gian giữa hai viên đạn (ms)
    private long lastFireTime;
    private List<Bullet> bullets;
    private Image bulletImage;

    public GunPowerUp(double x, double y) {
        super(x, y, "Gun", 10000);
        this.bullets = new ArrayList<>();
        this.bulletImage = loadImage("/org/example/btl/images/Bullet.png");// 10 giây
    }

    @Override
    public void applyEffect(Paddle paddle) {
        lastFireTime = System.currentTimeMillis();
    }

    @Override
    public void removeEffect(Paddle paddle) {
        System.out.println("GunPowerUp expired!");
        bullets.clear();
    }

    public void renderBullets(GraphicsContext gc) {
            for (Bullet b : bullets) {
                b.render(gc);
            }
    }

    public  void updateBullets() {
        List<Bullet> toRemove = new ArrayList<>();
        for (Bullet b : bullets) {
            b.update();
            if (!b.isActive()) {
                toRemove.add(b);
            }
        }
        bullets.removeAll(toRemove);
    }

    public void updateWhileActive(Paddle paddle) {
        long now = System.currentTimeMillis();
        if (now - lastFireTime > FIRE_INTERVAL) {
            double xLeft = paddle.getX() + 5;
            double xRight = paddle.getX() + paddle.getWidth() - 10;
            double y = paddle.getY() - 10;

            bullets.add(new Bullet(xLeft, y));
            bullets.add(new Bullet(xRight, y));

            lastFireTime = now;
        }
    }
}
