package entity;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * 
 * Created by Chris on 11.05.2015.
 */

public class Wheel implements Serializable {


    private int wheelX;
    private int wheelY;
    private int radius;
    private int speed;
    private  double winkel;
    private int numberSpokes;
    private ArrayList <Spokes> spokesList = new ArrayList<Spokes>();


    public Wheel(int x, int y, int numberStartSpokes, int speed){
        wheelX = x/100*52;
        wheelY = y/100*28;
        radius = x/100*12;
        this.numberSpokes = numberStartSpokes;
        this.speed = speed;
    }
    public Wheel(int x, int y, int numberStartSpokes, int speed, boolean multi){
        wheelX = x/100*52;
        wheelY = y/100*28;
        radius = x/100*12;
        this.numberSpokes = numberStartSpokes;
        this.speed = speed;
    }

    public void loadWheel(Graphics wheel, boolean startAdd){
        //Wheel laden
        Graphics2D g2 = (Graphics2D) wheel;
        g2.setPaint(new Color(0, 128, 128));
        Ellipse2D circle = new Ellipse2D.Double();
        circle.setFrameFromCenter(this.wheelX, this.wheelY, this.wheelX + radius, this.wheelY + radius); ///(x-achse, y-achse, RadiusX , RadiusY)
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2.draw(circle);
        g2.fill(circle);
        if(startAdd) {
            //addspokes
            addSpokes();
        }

        ///loadSpokes
        for(Spokes spoke : this.spokesList){
            spoke.loadSpoke(wheel);
        }

    }

    public  void spinWheel(){
        for(Spokes spoke : this.spokesList){
            //System.out.println(spoke.getWinkel());
            double newWinkel = spoke.getWinkel()+this.speed;
            spoke.setWinkel(newWinkel);
            spoke.moveSpoke(newWinkel, this.radius, this.wheelX, this.wheelY);
        }


    }

    public void addSpokes(){
        double startwinkel = 360/this.numberSpokes;
        double winkelposition=startwinkel;
        for(int i = 0;i<this.numberSpokes;i++ ){
            this.spokesList.add(new Spokes(winkelposition, this.radius, this.wheelX, this.wheelY)) ;
            winkelposition = winkelposition+startwinkel;
            //System.out.println(numberSpokes);
            //System.out.println(startwinkel);
        }
        startwinkel=0;
    }

    public  void addSpokes(int xKollision){
        //Winkel berechen aus X und Y Koodinaten
        // System.out.println(xKollision);
        double radWinkel2 = Math.acos((xKollision-this.wheelX)/radius); //X_koordinate durch die x Koodinate der Kollision nehmen
        double aktuellerWinkel = (radWinkel2*180)/Math.PI;
        //System.out.println(aktuellerWinkel);
        this.spokesList.add(new Spokes(aktuellerWinkel, this.radius, this.wheelX, this.wheelY));

    }

    public void aufloesungAnpassen(double faktorX, double faktorY, boolean operator){
        /**
         * operator bestimmt ob der Faktor multipliziert oder geteilt wird
         * true = miltiplizieren
         * false = teilen
         */

        if(operator==true) {
            this.wheelX = (int) (this.wheelX * faktorX);
            this.wheelY = (int) (this.wheelY * faktorY);
            this.radius = (int) (this.radius * faktorX);
        }
        if(operator==false) {
            this.wheelX = (int) (this.wheelX / faktorX);
            this.wheelY = (int) (this.wheelY / faktorY);
            this.radius = (int) (this.radius / faktorX);
        }
    }

    public int getX(){
        return this.wheelX;
    }
    public int getY(){
        return this.wheelY;
    }
    public int getRadius(){
        return this.radius;
    }
    public ArrayList<Spokes> getSpokesList(){
        return this.spokesList;
    }

}
