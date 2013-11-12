
package com.game.objects;

import com.game.engine.GameEngine;
import java.awt.Color;
import java.awt.Graphics2D;
import static com.game.engine.GameEngine.*;
import java.awt.Graphics;
import java.util.ArrayList;
/*
 * @author Vartan
 */
public class Weapon extends Fighter {
    public Weapon() {
        super(.5,1,1.5,.1);
    }
    public void update(double dt) {
        super.update(dt);
        ArrayList<GameObject> gameObjects = getEngine().getGameObjects();
        for(int i=0;i<gameObjects.size();i++) {
            GameObject gameObject = gameObjects.get(i);
            if(gameObject instanceof Fighter && gameObject.intersects(this) && gameObject!=getParent()) {
                Fighter fighter = (Fighter)gameObject;
                fighter.damage(100);

            }
        }
    }

}
