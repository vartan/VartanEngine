package com.game;

import com.game.objects.GameObject;
import java.awt.*;
import com.game.engine.*;
import static com.game.engine.GameEngine.*;
import com.game.objects.GameArea;
import com.game.objects.Obstacle;
import com.game.objects.Projectile;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.util.ArrayList;

/*
 * @author Vartan
 */
public class GameCanvas extends Canvas {
    Image image;
    Graphics2D buffer;
    int width=getWidth();
    int height=getHeight();
    int numX = 16;
    int numY = 9;
    @Override
    public void update(Graphics g) {
        paint(g);
    }
    @Override
    public void paint(Graphics g) {
        width=getWidth();
        height=getHeight();
        if(image==null||buffer==null) {
            image = createImage(getWidth(),getHeight());
            buffer = (Graphics2D)image.getGraphics();

        } else if(image.getWidth(this) !=getWidth()||image.getHeight(this)!=getHeight()) {
            image = createImage(getWidth(),getHeight());
            buffer = (Graphics2D)image.getGraphics();
        }


        double ds = getTileSize();
        int paddingX = (int)((width-ds*numX)/2);
        int paddingY = (int)((height-ds*numY)/2);
        buffer.setColor(Color.BLACK);
        buffer.fillRect(0,0,width,height);
        GameArea area = GameEngine.getEngine().getGameArea();
        Location areaStart = getEngine().getCanvas().getScreenLocation(area);
        //buffer.drawImage(area.getImage(),(int)areaStart.getX(),(int)areaStart.getY(),null);
        area.repaint(buffer);
        ArrayList<GameObject> gameObjects = getEngine().getGameObjects();
        for(int i=0;i<gameObjects.size();i++) {
            GameObject gameObject = gameObjects.get(i);
            if(gameObject==null)
                continue;

            //Location position = getScreenLocation(gameObject);
            //buffer.drawImage(gameObject.getImage(), (int)position.getX(), (int)position.getY(), null);
            gameObject.repaint(buffer);
            if(gameObject instanceof Obstacle) {
                Path2D bounds = gameObject.getFullBounds();
                bounds.transform(AffineTransform.getScaleInstance(ds, ds));
                buffer.setColor(new Color(1,0,0,(float).5));
                buffer.fill(bounds);
            }
        }

       /* buffer.setColor(Color.BLACK);
        buffer.drawString(GameEngine.getEngine().getFPS()+"fps", 1, 11);
        buffer.drawString("("+GameEngine.getEngine().getPlayer().worldX+","+GameEngine.getEngine().getPlayer().worldY+")", 1, 26);
        buffer.drawString("Kills: "+GameEngine.getEngine().getPlayer().kills, 1, 41);
        buffer.drawString("Local: ("+GameEngine.getEngine().getPlayer().getX()+","+GameEngine.getEngine().getPlayer().getY()+")",1,56);

        buffer.setColor(Color.WHITE);
        buffer.drawString(GameEngine.getEngine().getFPS()+"fps", 0, 10);
        buffer.drawString("("+GameEngine.getEngine().getPlayer().worldX+","+GameEngine.getEngine().getPlayer().worldY+")", 0, 25);
        buffer.drawString("Kills: "+GameEngine.getEngine().getPlayer().kills, 0, 40);
        buffer.drawString("Local: ("+GameEngine.getEngine().getPlayer().getX()+","+GameEngine.getEngine().getPlayer().getY()+")",0,55);
        */
        ((Graphics2D)(g)).setTransform(AffineTransform.getTranslateInstance(paddingX, paddingY));
        g.drawImage(image,0,0,this);

    }
    public Location getScreenLocation(double gameX, double gameY) {
        double ds = getTileSize();
        int paddingX = (int)((width-ds*numX)/2);
        int paddingY = (int)((height-ds*numY)/2);
        return new Location((paddingX+ds*gameX),(paddingY+ds*gameY));
    }
    public Location getScreenLocation(Location gamePosition) {
        return getScreenLocation(gamePosition.getX(),gamePosition.getY());
    }
    public Location getScreenLocation(GameObject object) {
        return getScreenLocation(object.getX(),object.getY());
    }
    public Location getGameLocation(double screenX, double screenY) {
        double ds = getTileSize();
        int paddingX = (int)((width-ds*numX)/2);
        int paddingY = (int)((height-ds*numY)/2);
        return new Location(((screenX-paddingX)/ds),((screenY-paddingY)/ds));
    }
    public Location getGameLocation(Location screenPosition) {
        return getGameLocation(screenPosition.getX(),screenPosition.getY());
    }
    public double getTileSize() {
        double dx = width/numX;
        double dy = height/numY;
        double ds = dx>dy?dy:dx;
        return ds;
    }

}
