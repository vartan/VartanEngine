
package com.game.objects;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;

/*
 * @author Vartan
 */
public class FloorTile extends GameObject {
    public FloorTile(double x, double y) {
        super(x,y,1,1);
        setBorderColor(new Color(250,250,250));
    }
    /*public void drawImage(Graphics2D g) {
        int w = getScreenWidth();
        int h = getScreenHeight();
       // Stroke newStroke = new BasicStroke(w/10);
        //g.setStroke(newStroke);
        g.setColor(Color.WHITE);
        g.fillRect(0,0,w,h);
        g.setColor(new Color(250,250,255));
        g.fillRect(1,1,w-2,h-2);
        g.setColor(new Color(200,200,200));
        g.drawRect(1,1,w-2,h-2);
       // g.setStroke(new BasicStroke(0));

    }*/

}
