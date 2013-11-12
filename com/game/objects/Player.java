
package com.game.objects;

import com.game.Location;
import java.awt.Color;
import java.awt.Graphics2D;
import static com.game.engine.GameEngine.*;
import com.game.engine.Timer;
import com.sun.xml.internal.bind.v2.runtime.reflect.Accessor;
import java.awt.event.KeyEvent;

/*
 * @author Vartan
 */
public class Player extends Fighter {
    public int worldX = 0;
    public int worldY = 0;
    public int kills = 0;
    Timer actionTimer;
    //Weapon weapon;
    public Player() {
        super(0,0,.5);
        init();
    }
    public void init() {
        damage=1;
        health=10;
        setFillColor(new Color((int)(200*Math.random()),(int)(255*Math.random()),(int)(255*Math.random())));
    //    weapon = new Weapon();
  //      weapon.setTangible(false);
//        addChild(weapon);
        actionTimer = new Timer(200);
    }
/*    @Override
    public void drawImage(Graphics2D g) {
        g.setColor(new Color(0,100,0));
        g.fillRect(0,0,getScreenWidth(),getScreenHeight());
    }*/
    public boolean intersectsObstacle() {
        for(GameObject gameObject:getEngine().getGameObjects()) {
           if(gameObject instanceof Obstacle && intersects(gameObject)){
           //System.out.println(gameObject.toString());
                return true;
           }
        }
        return false;
    }
    public void update(double dt) {
        super.update(dt);

        if(damageTimer.isDone())
            setBorderColor(Color.BLACK);
        else
            setBorderColor(Color.RED);
        double startX = getX();
        double startY = getY();
        double dy=0;
        double dx=0;
        if(getEngine().isKeyDown(KeyEvent.VK_W))
            dy+=-5*dt;
        if(getEngine().isKeyDown(KeyEvent.VK_S))
            dy+=5*dt;
        if(getEngine().isKeyDown(KeyEvent.VK_D))
            dx+=5*dt;
        if(getEngine().isKeyDown(KeyEvent.VK_A))
            dx+=-5*dt;
        for(int i=0;i<10;i++)  {
            setY(startY+dy);
            if(intersectsObstacle()) {
                if(i==9)
                    setY(startY);
                else
                    dy*=.9;
            } else {
                break;
            }
        }

        for(int i=0;i<10;i++)  {
            setX(startX+dx);
            if(intersectsObstacle()) {
                if(i==9)
                    setX(startX);
                else
                    dx*=.9;
            } else {
                break;
            }
        }

        Location mouseLocation = getEngine().getMouseLocation();

        if(getEngine().isMouseDown(1) && actionTimer.isDone()) {
            actionTimer.reset();
            getEngine().addGameObject(new Projectile(this,getX()+.5,getY()+.5,.15,new Location(getX()+.5,getY()+.5).angleTo(getEngine().getMouseLocation()),10, getFillColor()));
        }
  //      if(mouseLocation!=null)
//            weapon.setRelativeAngle(new Location(getX()+.5,getY()+.5).angleTo(getEngine().getMouseLocation()));
    }
    public void onDie() {
        getEngine().setPlayer(new Player());
        getEngine().setGameArea(new GameArea());
        getEngine().getPlayer().setFillColor(new Color((int)(255*Math.random()),(int)(255*Math.random()),(int)(255*Math.random())));
    }
    /*public void drawImage(Graphics2D g) {
        int d = getScreenWidth(); //diameter
        g.setColor(getFillColor());
        g.fillOval(0,0,d,d);
        g.setColor(Color.WHITE);
        g.fillOval(d*5/24,d/3,d/8,d/7);
        g.fillOval(2*d/3,d/3,d/8,d/7);
        g.setColor(Color.BLACK);
        g.drawOval(d*5/24,d/3,d/8,d/7);
        g.drawOval(2*d/3,d/3,d/8,d/7);
        g.setColor(Color.BLACK);
        g.drawOval(0,0,d,d);
    }*/
}
