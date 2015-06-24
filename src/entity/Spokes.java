package entity;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.io.Serializable;


/**
 * Created by Chris on 11.05.2015.
 */
public class Spokes implements Serializable {

    /**
     * Umgebungsvariabeln
     */
    double winkel;
    int radius=8;
    double x_koordinateStart;           //X Position der Speiche an dem Rad
    double y_koordinateStart;           //Y Position der Speiche an dem Rad
    double x_koordinateEnd;             //X Position des Speichenendes für den kreis
    double y_koordinateEnd;             //Y Position des Speichenendes für den kreis


    /**
     * Constructor für die Speichen
     */
    public Spokes(double winkel, int radius, int xWheel, int yWheel){
        double positionsWinkel = winkel / 180.0 * Math.PI;
        this.x_koordinateStart = Math.cos(positionsWinkel) * (radius) + xWheel;
        this.y_koordinateStart = Math.sin(positionsWinkel) * (radius) + yWheel;
        this.x_koordinateEnd = Math.cos(positionsWinkel) * (radius * 2) + xWheel;
        this.y_koordinateEnd = Math.sin(positionsWinkel) * (radius * 2) + yWheel;
        this.winkel=winkel;

    }

    /**
     *
     * erzeugt die Grafik für die Spokes
     */
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


    /**
     * Bewegt die Speiche
     */
    public void moveSpoke( double winkel, int radius, int xWheel, int yWheel){

        /**
         * Komplizierte BErechung für das BEwegen der Speichen oder auch nicht
         */
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


