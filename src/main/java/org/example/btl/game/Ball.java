package org.example.btl.game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

import static org.example.btl.Config.*;


public class Ball extends MovableObject {

    private double speed;
    private double directionX;
    private double directionY;
    private Image image;
    private double oldX;
    private double oldY;

    private boolean attached = true;

    public Ball(double x, double y, double width, double height,
                double directionX, double directionY, double speed) {
        super(x, y, width, height);
        this.directionX = directionX;
        this.directionY = directionY;
        this.speed = speed;
        image = loadImage("/org/example/btl/images/powerups/ball.png");

        updateVelocity();
    }

    public void updateVelocity() {
        this.dx = this.directionX * this.speed;
        this.dy = this.directionY * this.speed;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
        updateVelocity();
    }

    public double getDirectionX() {
        return directionX;
    }

    public double getDirectionY() {
        return directionY;
    }

    public double getOldX() {
        return oldX;
    }

    public double getOldY() {
        return oldY;
    }

    //Kiem tra ball nam tren Paddle khong
    public boolean isAttached() {
        return attached;
    }

    public void setAttached(boolean attached) {
        this.attached = attached;
    }

    /**
     * Xử lý bóng nảy lại khi chạm biên màn chơi.
     * - Nếu chạm mép trái/phải: đảo hướng X
     * - Nếu chạm mép trên: đảo hướng Y
     * - Nếu rơi xuống dưới: không nảy (được xử lý trong GameManager)
     */
    public void bounceOff() {
        if (getX() <= PLAY_AREA_X) {
            setX(PLAY_AREA_X);
            directionX *= -1;
        } else if (getX() + getWidth() >= PLAY_AREA_X + PLAY_AREA_WIDTH) {
            setX(PLAY_AREA_X + PLAY_AREA_WIDTH - getWidth());
            directionX *= -1;
        } else if (getY() + getHeight() >= PLAY_AREA_Y + PLAY_AREA_HEIGHT) {
            return;
        } else if (getY() <= PLAY_AREA_Y) {
            setY(PLAY_AREA_Y);
            directionY *= -1;
        }
        updateVelocity();
    }

    /**
     * Xử lý va chạm giữa bóng và đối tượng khác (Paddle hoặc Brick).
     * - Nếu va chạm Paddle: luôn nảy lên trên.
     * - Nếu va chạm Brick:
     *   + Dựa vào vị trí va chạm (trên/dưới hoặc trái/phải) để đảo hướng phù hợp.
     */
    public void bounce(@NotNull GameObject object) {
        Rectangle ballBounds = getRec();
        Rectangle objectBounds = object.getRec();

        if (!ballBounds.intersects(objectBounds)) {
            return;
        }

        // Nếu là Paddle -> bóng bật ngược lên
        if (object instanceof Paddle) {
            setY(object.getY() - getHeight() - 5);
            directionY = -Math.abs(directionY);
            updateVelocity();
            return;
        }

        // Nếu là Brick -> xác định hướng va chạm để đảo đúng trục
        double ballOldBottom = getOldY() + getHeight();

        // Va từ trên xuống hoặc từ dưới lên
        if (ballOldBottom <= object.getY() || getOldY() >= object.getY() + object.getHeight()) {
            setY(getOldY());
            directionY *= -1;
        } else { // Va bên trái hoặc phải
            if (getX() < object.getX()) {
                setX(getOldX() - 5);
            } else {
                setX(getOldX() + 5);
            }
            directionX *= -1;
        }
        updateVelocity();
    }

    /**
     * Cập nhật vị trí bóng mỗi khung hình.
     * Lưu lại tọa độ cũ (oldX, oldY) để phục vụ tính toán va chạm chính xác.
     */
    @Override
    public void update() {
        oldX = getX();
        oldY = getY();
        move();
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(image, getX(), getY(), getHeight(), getWidth());
    }
}
