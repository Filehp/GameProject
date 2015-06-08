package game;
import java.awt.*;
import java.awt.geom.Ellipse2D;


/**
 * Created by Chris on 11.05.2015.
 */
public class Spokes {
    double winkel;
    int radius=8;
    double x_koordinateStart;
    double y_koordinateStart;
    double x_koordinateEnd;
    double y_koordinateEnd;

    public Spokes(double winkel, int radius, int xWheel, int yWheel){
        double positionsWinkel = winkel / 180.0 * Math.PI;
        this.x_koordinateStart = Math.cos(positionsWinkel) * (radius) + xWheel;
        this.y_koordinateStart = Math.sin(positionsWinkel) * (radius) + yWheel;
        this.x_koordinateEnd = Math.cos(positionsWinkel) * (radius * 2) + xWheel;
        this.y_koordinateEnd = Math.sin(positionsWinkel) * (radius * 2) + yWheel;
        this.winkel=winkel;

    }

    public void loadSpoke(Graphics spoke) {
        Graphics2D g2 = (Graphics2D) spoke;
        g2.setPaint(new Color(0, 128, 128)); // a dull blue-green
        g2.setStroke(new BasicStroke(3));
        g2.drawLine((int) this.x_koordinateStart, (int) this.y_koordinateStart, (int) this.x_koordinateEnd, (int) this.y_koordinateEnd);

        Ellipse2D circle = new Ellipse2D.Double();
        circle.setFrameFromCenter((int) this.x_koordinateEnd, (int) this.y_koordinateEnd, (int) this.x_koordinateEnd + this.radius, (int) this.y_koordinateEnd + this.radius); ///(x-achse, y-achse, RadiusX , RadiusY)
        g2.fill(circle);
        g2.draw(circle);
    }



    public void moveSpoke( double winkel, int radius, int xWheel, int yWheel){
        double positionsWinkel = winkel / 180.0 * Math.PI;
        this.x_koordinateStart = Math.cos(positionsWinkel) * (radius) + xWheel;
        this.y_koordinateStart = Math.sin(positionsWinkel) * (radius) + yWheel;
        this.x_koordinateEnd = Math.cos(positionsWinkel) * (radius * 2) + xWheel;
        this.y_koordinateEnd = Math.sin(positionsWinkel) * (radius * 2) + yWheel;



    }

    public double getWinkel(){
        return this.winkel;
    }
    public void setWinkel(double newWinkel){
       this.winkel = newWinkel;
    }
    public int getX(){
        return (int) this.x_koordinateEnd;
    }
    public int getY(){
        return (int) this.y_koordinateEnd;
    }
    public int getRadius(){
        return  this.radius;
    }
    }


