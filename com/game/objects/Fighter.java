
package com.game.objects;
import com.game.engine.GameEngine;
import static com.game.engine.GameEngine.*;
import com.game.engine.Timer;
import java.awt.Color;
/*
 * @author Vartan
 */
public class Fighter extends GameObject {
    public double health=10;
    public double damage=1;
    public Timer damageTimer;
    public Fighter(double x,double y,double radius) {
        super(x,y,radius);
        damageTimer = new Timer(500);
    }
    public Fighter(double x, double y) {
        super(x,y,1,1);
        damageTimer = new Timer(500);
    }
    public Fighter(double x, double y, double width, double height) {
        super(x,y,width,height);
        damageTimer = new Timer(500);

    }

    public void updateCombat() {

    }
    public void damage(double d) {
        if(!invincible()) {
            damageTimer.reset();
            health-=d;
        }
    }
    public void update(double dt) {
        super.update(dt);
        if(invincible())
            setBorderColor(Color.RED);
        else
            setBorderColor(Color.BLACK);
    }
    public boolean invincible() {
        return !damageTimer.isDone();
    }
    public void onDie() {
    }
    public void die() {
        onDie();
        GameEngine.getEngine().removeGameObject(this);

    }


}
