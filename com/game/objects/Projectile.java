
package com.game.objects;

import java.awt.Color;
import java.awt.Graphics2D;
import static com.game.engine.GameEngine.*;
import java.awt.Graphics;
import java.util.ArrayList;
/*
 * @author Vartan
 */
public class Projectile extends Fighter {
    public double directionAngle = 0;
    public double speed = 4;
    GameObject owner;
    public Projectile(GameObject parent, double x, double y, double angle, double speed) {
        super(x,y);
        this.speed=speed;
        this.owner=parent;
        init();
    }
    public Projectile(GameObject parent, double x, double y, double width, double height, double angle, double speed) {
        super(x,y,width,height);
        this.speed=speed;
        this.owner=parent;
        init();

    }
    public Projectile(GameObject parent, double x, double y, double radius, double angle, double speed) {
        super(x,y,radius);
        this.directionAngle=angle;
        this.speed=speed;
        this.owner=parent;
        init();
    }
    public Projectile(GameObject parent, double x, double y, double radius, double angle, double speed, Color color) {
        super(x,y,radius);
        this.directionAngle=angle;
        setFillColor(color);
        this.speed=speed;
        this.owner=parent;
        init();
    }
    public void init() {
        this.damage=1;
        this.health=1;
    }
    public void onDie() {
    }
    public void update(double dt) {
        System.out.println(getRelativeAngle());
        shiftX(Math.cos(directionAngle)*speed*dt);
        shiftY(Math.sin(directionAngle)*speed*dt);
        if(getX()>17||getY()>10 || getX()<-1 || getY()<-1) {
            die();
            //angle=angle+Math.PI;
            //don't remove it yet because we're looping
        }
        ArrayList<GameObject> gameObjects = getEngine().getGameObjects();
        for(int i=0;i<gameObjects.size();i++) {
            GameObject c = gameObjects.get(i);
            if(c instanceof Obstacle) {
                if(c.intersects(this)) {
                    die();
                }
            }
            if(!(c instanceof Fighter) || this==c || !intersects(c) || c==owner || (c instanceof Projectile))
                continue;
            Fighter f = (Fighter)c;
            f.damage(damage);
            if(f.health<=0)
                f.die();
        }
    }

}
