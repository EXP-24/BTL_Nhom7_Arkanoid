package org.example.btl.game.powerups;

import javafx.scene.image.Image;
import org.example.btl.game.Ball;
import org.example.btl.game.Paddle;

import java.util.List;

public class MagnetPowerUp extends PowerUp {
    private List<Ball> balls;

    public MagnetPowerUp(double x, double y, List<Ball> balls) {
        super(x, y, "Magnet", 10000); // Hiệu ứng kéo dài 10 giây
        this.balls = balls;
    }

    @Override
    public void applyEffect(Paddle paddle) {
        Image magnetImage = loadImage("/org/example/btl/images/paddleMagnet.png");
        paddle.setImage(magnetImage);
    }

    @Override
    public void removeEffect(Paddle paddle) {
        // Khi hết hiệu ứng, đổi lại hình paddle gốc
        Image normalImage = loadImage("/org/example/btl/images/paddle.png");
        paddle.setImage(normalImage);
    }

    /**
     * Gọi hàm này trong GameManager khi bóng chạm paddle.
     * Nếu hiệu ứng Magnet đang hoạt động, bóng sẽ dính vào paddle.
     */
    public void attractBalls(Paddle paddle) {
        if (balls == null) return;

        for (Ball ball : balls) {
            double ballBottom = ball.getY() + ball.getHeight();
            boolean isColliding =
                    ballBottom >= paddle.getY() &&
                            ball.getX() + ball.getWidth() >= paddle.getX() &&
                            ball.getX() <= paddle.getX() + paddle.getWidth();

            if (isColliding && !ball.isAttached()) {
                // Dính bóng vào paddle
                ball.setAttached(true);
                ball.setY(paddle.getY() - ball.getHeight());
                ball.setX(paddle.getX() + paddle.getWidth() / 2 - ball.getWidth() / 2);
            }
        }
    }
}
