
package com.game.objects;

import com.game.Location;
import java.awt.Color;
import java.awt.Graphics2D;
import static com.game.engine.GameEngine.*;
import com.game.engine.Timer;
/*
 * @author Vartan
 */
public class Monster extends Fighter {
    Color pupilColor = Color.BLUE;
    Color eyeColor = Color.WHITE;
    Timer actionTimer;
    public Monster(double x, double y) {
        super(x,y,.5);
        pupilColor = new Color((int)(255*Math.random()),(int)(255*Math.random()),(int)(255*Math.random()));
        GameObject pupil = new GameObject(.25,.25,.25);
        pupil.setFillColor(pupilColor);
        addChild(pupil);
        actionTimer = new Timer(400);
    }
    @Override
    public void update(double dt) {
        if(actionTimer.isDone()) {
            actionTimer.reset();
            Projectile bullet = new Projectile(this,getX()+.25,getY()+.25,.25,new Location(getX()+.5,getY()+.5).angleTo(getEngine().getPlayer())+Math.random()*.5-.25,10, pupilColor);
            bullet.health=10;
            getEngine().addGameObject(bullet);
        }
        if(damageTimer.isDone())
            setBorderColor(Color.BLACK);
        else
            setBorderColor(Color.RED);
    }
  /*  public void drawImage(Graphics2D g) {
        int d = getScreenWidth();
        setColor(eyeColor);
        g.setColor(getColor());
        g.fillOval(0,0, getScreenWidth(),getScreenHeight());
        setColor(Color.RED);
        g.setColor(getColor());
        for(int i=0;i<6;i++) {
            g.drawLine((int)(d/2+d/3*Math.cos(Math.PI/3*i)),(int)(d/2+d/3*Math.sin(Math.PI/3*i)),(int)(d/2 + (double)d/2.5*Math.cos(Math.PI/3*i)),(int)(d/2 +(double)d/2.5*Math.sin(Math.PI/3*i)));
        }
        setColor(pupilColor);
        g.setColor(getColor());
        g.fillOval(d/3,d/3,d/3,d/3);
        g.setColor(Color.BLACK);
        g.drawOval(d/3,d/3,d/3,d/3);
        g.drawOval(0,0, getScreenWidth(),getScreenHeight());

    }*/
    public void onDie() {
        getEngine().getPlayer().kills++;
        System.out.println("blargh?");
        //gameObjects.add(new Monster(Math.random()*16,Math.random()*9));
    }

}
