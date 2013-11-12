package com.game;

import com.game.engine.GameEngine;
import java.applet.Applet;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;
import com.game.engine.*;
import com.game.*;


public class Client extends JFrame{

    public static void main(String[] args) {
        new Client();
    }
    public Client() {
        super("Vartan Game Engine beta");
        GameEngine engine = GameEngine.getEngine();
        engine.setContainer(getContentPane());
        getContentPane().setPreferredSize(new Dimension(480,270));
        System.setProperty("sun.java2d.opengl","True");
        getContentPane().add(GameEngine.getEngine().getCanvas());
        getContentPane().setBackground(Color.BLACK);
        setVisible(true);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        engine.run();
    }

}
