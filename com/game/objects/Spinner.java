
package com.game.objects;

import java.awt.Color;

/*
 * @author Vartan
 */
public class Spinner extends Obstacle {
    public Spinner(double x, double y) {
        super(x,y);
                rotateClockwise(0.25*Math.PI);

    }
    public void update(double dt) {
        super.update(dt);
        //setFillColor(new Color((int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random()*255)));

    }

}
