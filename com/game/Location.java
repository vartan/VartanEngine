
package com.game;

import java.awt.Point;

/*
 * @author Vartan
 * @description This would normally be a point class, but point has some
 * funky double compatibility
 */
public class Location implements Cloneable {
    private double x = 0;
    private double y = 0;
    public Location(double x, double y) {
        this.x=x;
        this.y=y;
    }
    public Location(Point p) {
        this.x=p.x;
        this.y=p.y;
    }
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public void setX(double newX) {
        x = newX;
    }
    public void setY(double newY) {
        y = newY;
    }
    public void shiftX(double delta) {
        x+=delta;
    }
    public void shiftY(double delta) {
        y+=delta;
    }
    public void setLocation(double newX, double newY) {
        x = newX;
        y = newY;
    }
    public void translate(double deltaX, double deltaY) {
        x+=deltaX;
        y+=deltaY;
    }
    public double distanceTo(Location l) {
        double dx = x-l.getX();
        double dy = y-l.getY();
        return Math.sqrt(dx*dx+dy*dy);
    }
    public double angleTo(Location l) {
        double dx = l.getX()-x;
        double dy = l.getY()-y;
        return Math.atan2(dy,dx);
    }
    public Object clone() {
        try {
        return (Location)super.clone();
        } catch(CloneNotSupportedException e ){
            e.printStackTrace();
        }
        return null;
    }
}
