
package com.game;

import java.awt.event.*;
import static com.game.engine.GameEngine.*;
/*
 * @author Vartan
 */
public class InputListener implements MouseListener, MouseMotionListener, KeyListener, FocusListener {
    @Override
    public void mouseClicked(MouseEvent e) {
        getEngine().setMouseLocation(getEngine().getCanvas().getGameLocation(new Location(e.getPoint())));

    }

    @Override
    public void mousePressed(MouseEvent e) {
        getEngine().setMouseLocation(getEngine().getCanvas().getGameLocation(new Location(e.getPoint())));
        //gameObjects.add(new GameObject(mouseLocation.getX(),mouseLocation.getY()));
        getEngine().setMouseDown(e.getButton());
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        getEngine().setMouseLocation(getEngine().getCanvas().getGameLocation(new Location(e.getPoint())));
        getEngine().setMouseUp(e.getButton());
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        getEngine().setMouseLocation(getEngine().getCanvas().getGameLocation(new Location(e.getPoint())));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        getEngine().setMouseLocation(null);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        getEngine().setMouseLocation(getEngine().getCanvas().getGameLocation(new Location(e.getPoint())));
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        getEngine().setMouseLocation(getEngine().getCanvas().getGameLocation(new Location(e.getPoint())));
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        getEngine().setKeyDown(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        getEngine().setKeyUp(e.getKeyCode());
    }

    @Override
    public void focusGained(FocusEvent e) {
        getEngine().setPauseState(false);
    }

    @Override
    public void focusLost(FocusEvent e) {
        getEngine().setPauseState(true);
    }

}
