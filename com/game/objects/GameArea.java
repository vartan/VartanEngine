package com.game.objects;
import com.game.Location;
import static com.game.engine.GameEngine.*;
import java.awt.Color;
import java.awt.Graphics2D;
/*
 * @author Vartan
 */
public class GameArea extends GameObject {
    int colorSeed = 100;
    public GameArea() {
        super(0,0,16,9);
    }
    public void init() {
        colorSeed = (int)(100+Math.random()*155);
        for(int x=0;x<16;x++)
            for(int y=0;y<9;y++)
                getEngine().addGameObject(new FloorTile(x,y));
        getEngine().addGameObject(getEngine().getPlayer());
        getEngine().addGameObject(new Monster(8,5));
        getEngine().addGameObject(new Obstacle(2,2));
        getEngine().addGameObject(new Obstacle(2,3));
        getEngine().addGameObject(new Obstacle(2,4));
        getEngine().addGameObject(new Spinner(5,4));

    }

    public void update(double dt) {
        Player player = getEngine().getPlayer();
        Location center = new Location(player.getX()+.5, player.getY()+.5);
        if(!contains(center)) //if we don't contain the player's center point
            getEngine().setGameArea(new GameArea());
    }
    public void leave() {
        Player player = getEngine().getPlayer();
        Location center = new Location(player.getX()+.5, player.getY()+.5);
        if(center.getX()<=0) {
            player.worldX--;
            player.setLocation(15.49,player.getY());
        } else if(center.getX()>=16) {
            player.worldX++;
            player.setLocation(-.49,player.getY());
        } else if(center.getY()<=0) {
            player.worldY++;
            player.setLocation(player.getX(),8.4);
        } else if(center.getY()>=9) {
            player.worldY--;
            player.setLocation(player.getX(),-.49);
        } else {
            System.out.println("Unhandled GameArea.leave(). Most likely death.");
        }
        getEngine().clearGameObjects();


    }
    public void drawImage(Graphics2D g) {/*
        int numX = 16;
        int numY = 9;
        double width = getScreenWidth();
        double height = getScreenHeight();
        double dx = width/numX;
        double dy = height/numY;
        double ds = dx>dy?dy:dx;
        for(int i=0;i<numX;i++) {
            int lineX = (int)(ds*i);
            for(int h=0;h<numY;h++) {
                g.setColor(new Color((int)(colorSeed*i/numX),(int)(colorSeed*i/numX*h/numY),(int)(colorSeed*h/numY)));
                int lineY = (int)(ds*h);
                g.fillRect(lineX,lineY,(int)ds,(int)ds);
            }
        }*/
    }

}
