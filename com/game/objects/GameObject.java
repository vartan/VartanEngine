package com.game.objects;

import com.game.Location;
import com.game.engine.GameEngine;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.*;

import java.util.ArrayList;

/**
 *
 * @author Vartan
 */
public class GameObject extends Location {
    private GameObject parent;
    private double angle = 0;
    private Color borderColor = Color.BLACK;
    private Color fillColor = Color.WHITE;
    private Shape shape;
    private ArrayList<GameObject> children = new ArrayList<GameObject>();
    private boolean tangible = true;
    //todo:get absolute
    public GameObject(double x, double y, double radius) {
        super(x,y);
        shape = new Ellipse2D.Double(0,0,radius*2,radius*2);
    }
    public GameObject(double x, double y, double width, double height) {
        super(x,y);
        shape = new Rectangle2D.Double(0,0,width,height);
    }
    public void setBorderColor(Color c) {
        borderColor = c;
    }
    public void setShape(Shape s) {
        shape=s;
    }
    public void setFillColor(Color c) {
        fillColor = c;
    }
    public Color getBorderColor() {
        return borderColor;
    }
    public Color getFillColor() {
        return fillColor;
    }
    public Path2D getFullBounds() {
        return getBoundsRecursive(0, new Location(0,0));
    }
    public Area getArea() {
        return new Area(getFullBounds());
    }
    public void setTangible(boolean b) {
        tangible=b;
    }
    public boolean isTangible() {
        return tangible;
    }
    public boolean contains(Location l) {
        Area area = getArea();
        return area.contains(l.getX(),l.getY());
    }
    public boolean intersects(GameObject object) {
        Area area = getArea();
        area.intersect(object.getArea());
        if(!area.isEmpty())
            return true;
        for(GameObject child:children) {
            if(child.isTangible() && child.intersects(object))
                return true;
        }
        return false;
    }
    public Path2D getBoundsRecursive(double theta, Location reference) {
        Path2D bounds = new Path2D.Double();
        double magnitude = Math.sqrt(getX()*getX()+getY()*getY());

        //System.out.println(magnitude);
        double ang = Math.atan2(getY(),getX());
        //ang+=theta%(2*Math.PI);
        //reference.shiftX(ang*magnitude);
        //reference.shiftY(ang*magnitude);
        ang+=theta;

        double dxa = magnitude*Math.cos(ang);
        double dya = magnitude*Math.sin(ang);
        theta+=angle;
        reference.translate(dxa,dya);

        for(GameObject child: children) {
            if(child.isTangible()) {
                Path2D childBounds = child.getBoundsRecursive(theta, (Location)reference.clone());

                //childBounds.transform(AffineTransform.getTranslateInstance(child.getX(), child.getY()));
                bounds.append(childBounds, false);
            }
        }
        bounds.append(getShape(theta),false);
        bounds.transform(AffineTransform.getTranslateInstance(dxa,dya));

        return bounds;
    }
    public Rectangle2D getBindingBox() {
        return null;
    }
    public Path2D getShape() {
        return getShape(angle);
    }
    public Path2D getShape(double theta) {
        Path2D.Double base = new Path2D.Double(shape);
        base.transform(AffineTransform.getRotateInstance(theta));
        return base;

    }
    public void update(double dt) {

        for(GameObject child:children)
            child.update(dt);
    }

    public double getAbsoluteAngle() {
        return parent != null ? parent.getAbsoluteAngle() + getRelativeAngle() : getRelativeAngle();
    }
    public double getRelativeAngle() {
        return angle;
    }
    public void setRelativeAngle(double theta) {
        angle=theta;
    }
    public void rotateClockwise(double theta) {
        angle-=theta;
    }
    public void rotateCounterClockwise(double theta) {
        angle+=theta;
    }
    public void addChild(GameObject s) {
        children.add(s);
        s.parent=this;
    }
    public void removeChild(GameObject s) {
        children.remove(s);
    }
    public void remove() {
        if(parent!=null) {
            parent.removeChild(this);
        }
        ArrayList<GameObject> childrenTemp = (ArrayList<GameObject>)children.clone();
        for(GameObject child:childrenTemp) {
            child.remove();
        }
    }
    public GameObject getParent() {
        return parent;
    }
    public void repaint(Graphics2D g) {
        repaint(g, 0, new Location(0,0));
    }
    public void repaint(Graphics2D g, double theta, Location reference) {
        double magnitude = Math.sqrt(getX()*getX()+getY()*getY());

        //System.out.println(magnitude);
        double ang = Math.atan2(getY(),getX());
        //ang+=theta%(2*Math.PI);
        //reference.shiftX(ang*magnitude);
        //reference.shiftY(ang*magnitude);
        ang+=theta;

        double dxa = magnitude*Math.cos(ang);
        double dya = magnitude*Math.sin(ang);
        double scale = GameEngine.getEngine().getCanvas().getTileSize();

        reference.translate(dxa,dya);

        theta+=angle; // rotate the drawing angle by our relative angle.
        drawImage(g,theta,reference,scale);

        //draw child objects over the parent.
        for(GameObject child:children) {
            child.repaint(g,theta,(Location)reference.clone());
        }

    }
    public void drawImage(Graphics2D g, double theta, Location reference, double scale) {
        Path2D shape = getShape(theta); // gets our draw shape

        shape.transform(AffineTransform.getScaleInstance(scale,scale));
        shape.transform(AffineTransform.getTranslateInstance(reference.getX()*scale, reference.getY()*scale));
        //fill and draw shapes

        g.setColor(getFillColor());
        g.fill(shape);
        g.setColor(getBorderColor());
        g.draw(shape);
    }
    public boolean containsRecursive(GameObject object) {
        if(object.getParent().equals(this))
            return true;
        for(GameObject child:children)
            if(child.containsRecursive(object.getParent()))
                return true;
        return false;
    }
    public boolean containsChild(GameObject object) {
        return object.getParent().equals(this);
    }
    public Location getAbsoluteLocation() {
        Location padding = new Location(0,0);
        if(getParent()!=null) {
            Location parentPadding = getParent().getAbsoluteLocation();

            padding.translate(parentPadding.getX(),parentPadding.getY());
        }
        return padding;
    }
}
