package org.example.btl.game;

public abstract class MovableObject extends GameObject {

    protected double dx;
    protected double dy;

    public MovableObject(double x, double y, double width, double height) {
        super(x, y, width, height);
    }

    public void move() {
        setX(getX() + dx);
        setY(getY() + dy);
    }
}
