
package com.game.objects;

import com.game.engine.GameEngine;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Stroke;
import java.awt.geom.Path2D;

/*
 * @author Vartan
 */
public class Obstacle extends GameObject {

    public Obstacle(double x, double y) {
        super(x,y, 1,1);
        GameObject tri = new GameObject(0,0,0,0);
        double[] xpoints = {0,.5,0};
        double[] ypoints = {1,.5,1};

        Path2D.Double triangle = new Path2D.Double();
        triangle.moveTo(0,0);
        triangle.lineTo(.5,.5);
        triangle.lineTo(0,1);
        triangle.lineTo(0,0);
        tri.setShape(triangle);
        GameObject tri2 = (GameObject)tri.clone();
        tri2.rotateClockwise(Math.PI);
        tri2.setLocation(1,1);
        setFillColor(new Color(250,250,250));
        tri.setBorderColor(Color.gray);
        tri2.setBorderColor(Color.gray);
        tri2.addChild(new GameObject(.3,.3,.1,.1));
        //tri.setTangible(false);
        //tri2.setTangible(false);
                addChild(tri);
                addChild(tri2);
                addChild(new GameObject(.25,.25,.5,.5));

    }
    public Obstacle(double x, double y, double width) {
        super(x,y,1,1);
    }
    public void update(double dt) {
        super.update(dt);
        if(intersects(GameEngine.getEngine().getPlayer()))
            setBorderColor(Color.RED);
        else
            setBorderColor(Color.BLACK);
    }
/*    public void drawImage(Graphics2D g) {
        int w = getScreenWidth();
        int h = getScreenHeight();
        Stroke newStroke = new BasicStroke(w/10);
        Color light = new Color(250,250,250);
        Color dark = new Color(150,150,150);

        g.setColor(light);
        g.fillRect(0,0,w,h);

        g.setColor(dark);
        g.drawRect(0,0,w,h);
        g.drawLine(0,0,w,h);
        g.drawLine(w,0,0,h);
        g.setColor(light);
        g.fillRect(w/4+1,w/4+1,w/2-1,w/2-1);
        g.setColor(dark);

        g.drawRect(w/4+1,w/4+1,w/2-1,w/2-1);
        g.drawRect(1,1,w-2,h-2);
        g.setColor(Color.WHITE);
        g.drawRect(0,0,w,h);
    }
*/
}
