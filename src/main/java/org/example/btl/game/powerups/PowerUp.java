package org.example.btl.game.powerups;

import org.example.btl.game.Paddle;
import org.example.btl.game.GameObject;

public abstract class PowerUp extends GameObject {
    protected String type;
    protected int duration;

    public PowerUp(double x, double y, String type, int duration) {
        super(x, y);
        this.type = type;
        this.duration = duration;
    }

    public String getType() {
        return type;
    }

    public int getDuration() {
        return duration;
    }

    public abstract void applyEffect(Paddle paddle);

    public abstract void removeEffect(Paddle paddle);
}
