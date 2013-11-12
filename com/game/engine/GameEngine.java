package com.game.engine;
import com.game.*;
import com.game.objects.Fighter;
import com.game.objects.GameArea;
import com.game.objects.GameObject;
import com.game.objects.Monster;
import com.game.objects.Player;
import java.awt.*;
import java.util.ArrayList;

/*
 * @author Vartan
 */
//TODO: fix all timers for pause

public class GameEngine  {
    public static GameEngine instance;
    private GameCanvas canvas;
    private Player player;
    private Container container;
    private Location mouseLocation;
    private boolean[] mouseDown = new boolean[4];
    private InputListener inputListener;
    private ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();
    private ArrayList<Integer> keysDown = new ArrayList<Integer>();
    private GameArea gameArea;
    private ArrayList<GameObject> gameObjectsToRemove = new ArrayList<GameObject>();
    private ArrayList<GameObject> gameObjectsToAdd = new ArrayList<GameObject>();


    private int averageFPS = 0;
    private long currentTime = 0;
    private double timeFix = 0;

    private boolean paused=false;
    private int fps=0;
    private int refreshRate = 30;
    final private int FPS_COUNTER_MAX = 100;
    private long[] time = new long[FPS_COUNTER_MAX];
    private int fpsCounter = 0;
    private long lastClockTime = 0;
    private long timePassed = 0;

    private GameEngine() {
        inputListener = new InputListener();
        canvas = new GameCanvas();
        player = new Player();
    }
    @Override
    public Object clone() throws CloneNotSupportedException {
            throw new CloneNotSupportedException();
    }
    public static synchronized GameEngine getEngine() {
	if(instance==null)
            instance = new GameEngine();
        return instance;
    }

    private void update() {
        updateTime();
        gameArea.update(timeFix);

        if(!getPauseState()) {
            for(int i=0;i<gameObjects.size();i++) {
                GameObject gameObject = gameObjects.get(i);
                if(gameObject!=null) {
                    gameObject.update(timeFix);
                }
            }
            for(int i=0;i<gameObjects.size();i++) {
                GameObject gameObject = gameObjects.get(i);
                if(gameObject instanceof Fighter)
                    ((Fighter)gameObject).updateCombat();
            }
            for(int i=0;i<gameObjectsToRemove.size();i++) {
                int index = gameObjects.indexOf(gameObjectsToRemove.get(i));
                if(index>0) {
                    gameObjectsToRemove.get(i).remove();
                    gameObjects.remove(index);
                }
            }
            gameObjectsToRemove.clear();
            for(int i=0;i<gameObjectsToAdd.size();i++) {
                gameObjects.add(gameObjectsToAdd.get(i));
            }
            gameObjectsToAdd.clear();
        }
    }

    private void updateTime() {
        updateFPS();
        timePassed = (int)(System.currentTimeMillis() - lastClockTime);
        timeFix = (double)timePassed/1000;
        lastClockTime = System.currentTimeMillis();
        fpsPadding();
        currentTime = System.currentTimeMillis();

    }
    private void fpsPadding() {
        int timePadding = (int)(2000.0/refreshRate-timePassed);
        if(timePadding>0) {
            try {Thread.sleep(timePadding);} catch (Exception e){}
        }
    }
    private void updateFPS() {
        time[fpsCounter]=System.currentTimeMillis();
            int timeSince = (int)(time[fpsCounter]-time[(fpsCounter+1)%FPS_COUNTER_MAX]);
            if(timeSince==0)
                fps=1000; //let's not divide by zero
            else
                fps = (FPS_COUNTER_MAX*1000)/timeSince;
        fpsCounter = (fpsCounter+1)%FPS_COUNTER_MAX;
        if(fpsCounter == 0)
            averageFPS = fps;
    }
    public void setPauseState(boolean state) {
        paused=state;
    }
    public boolean getPauseState() {
        return paused;
    }
    public void run() {
        init();
         while(true) {
            update();
            canvas.repaint();
         }//end game loop, should never happen
    }
    public void init() {
         canvas.addMouseListener(inputListener);
         canvas.addMouseMotionListener(inputListener);
         canvas.addKeyListener(inputListener);
         canvas.addFocusListener(inputListener);
         canvas.requestFocus();
         setGameArea(new GameArea());
         lastClockTime = System.currentTimeMillis();
         refreshRate = getRefreshRate();
    }
    public int getRefreshRate() {
    //modified from http://www.java2s.com/Tutorial/Java/0261__2D-Graphics/GettingtheCurrentScreenRefreshRateandNumberofColors.htm
        int refreshRate = 30;
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] gs = ge.getScreenDevices();
        for (int i = 0; i < gs.length; i++) {
          DisplayMode dm = gs[i].getDisplayMode();
           int currentRate = dm.getRefreshRate();
          if (currentRate == DisplayMode.REFRESH_RATE_UNKNOWN) {
            System.out.println("Unknown rate");
          } else if(currentRate>refreshRate) {
              refreshRate = currentRate;
          }
        }
        return refreshRate;
    }
    public long getCurrentTime() {
        return currentTime;
    }
    public int getFPS() {
        return averageFPS;
    }
    public Player getPlayer() {
        return player;
    }
    public void setPlayer(Player p) {
        player=p;
    }
    public Container getContainer() {
        return container;
    }
    public void setContainer(Container c) {
        container = c;
    }
    public Location getMouseLocation() {
        return mouseLocation;
    }
    public void setMouseLocation(Location l) {
        mouseLocation = l;
    }
    public boolean isMouseDown(int mouseButton) {
            return mouseDown[mouseButton];
    }
    public void setMouseDown(int index) {
        mouseDown[index] = true;
    }
    public void setMouseUp(int index) {
        mouseDown[index] = false;
    }
    public InputListener getInputListener() {
        return inputListener;
    }
    public void setInputListener(InputListener i) {
        inputListener=i;
    }
    public ArrayList<GameObject> getGameObjects() {
        return gameObjects;
    }
    public void addGameObject(GameObject i) {
        gameObjectsToAdd.add(i);
        //TODO: add game object
    }
    public void removeGameObject(GameObject i) {
        gameObjectsToRemove.add(i);
        //TODO: remove game object
    }
    public void clearGameObjects() {
        gameObjects.clear();
    }
    public boolean isKeyDown(int i) {
        return keysDown.contains(i);
    }
    public void setKeyDown(int i) {
        if(!isKeyDown(i))
            keysDown.add(i);
    }
    public void setKeyUp(int i) {
        if(isKeyDown(i))
            keysDown.remove(keysDown.indexOf(i));
    }
    public void setGameArea(GameArea area) {
        if(gameArea!=null)
            gameArea.leave();
        gameArea = area;
        area.init();
    }
    public GameArea getGameArea() {
        return gameArea;
    }
    public GameCanvas getCanvas() {
        return canvas;
    }

}


