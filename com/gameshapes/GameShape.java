/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gameshapes;

import com.game.Location;
import java.awt.Polygon;
import java.awt.geom.Path2D;
import java.util.ArrayList;

/**
 *
 * @author Vartan
 */
public class GameShape extends Path2D.Double {
    private double angle = 0;
    private GameShape parent = null;
    Location relativeLocation = new Location(0,0);
    private ArrayList<GameShape> children = new ArrayList<GameShape>();

    public GameShape(double[] x, double[] y) {

    }
    public double getAngle() {
        return parent != null ? parent.getAngle() + angle : angle;
    }
    public double getRelativeAngle() {
        return angle;
    }
    public void addChild(GameShape s) {
        children.add(s);
        s.parent=this;
    }
    public void removeChild(GameShape s) {
        children.remove(s);
    }
    public void remove() {
        if(parent!=null)
            parent.removeChild(this);
    }
    public GameShape(int sides, int radius) {
        super();
        double[] circlex = new double[sides];
        double[] circley = new double[sides];
        for(int i=0;i<sides;i++) {
            circlex[i]=(int)(Math.cos(360.0/sides*i*Math.PI/180)*radius);
            circley[i]=(int)(Math.sin(360.0/sides*i*Math.PI/180)*radius);
            lineTo(circlex[i],circley[i]);
        }
    }
}
