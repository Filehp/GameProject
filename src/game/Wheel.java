package game;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;



/**
 * Created by Chris on 11.05.2015.
 */
public class Wheel {

    private int x;
    private int y;
    private int radius;
    private int speed;
    private  double winkel;
    private int numberSpokes;
    private ArrayList <Spokes> spokesList = new ArrayList<Spokes>();


    public Wheel(int x, int y, int numberStartSpokes, int speed){
        this.x = x;
        this.y = y;
        radius = 50;
        this.numberSpokes = numberStartSpokes;
        this.speed = speed;
    }

    public void loadWheel(Graphics wheel, boolean startAdd){
        //Wheel laden
        Graphics2D g2 = (Graphics2D) wheel;
        Ellipse2D circle = new Ellipse2D.Double();
        circle.setFrameFromCenter(this.x, this.y, this.x + radius, this.y + radius); ///(x-achse, y-achse, RadiusX , RadiusY)
        g2.draw(circle);
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
            spoke.moveSpoke(newWinkel, this.radius, this.x, this.y);
        }


    }

    public void addSpokes(){
        double startwinkel = 360/this.numberSpokes;
        double winkelposition=startwinkel;
        for(int i = 0;i<this.numberSpokes;i++ ){
            this.spokesList.add(new Spokes(winkelposition, this.radius, this.x, this.y)) ;
            winkelposition = winkelposition+startwinkel;
            //System.out.println(numberSpokes);
            //System.out.println(startwinkel);
        }
        startwinkel=0;
    }

    public  void addSpokes(int xKollision){
        //Winkel berechen aus X und Y Koodinaten
        System.out.println(xKollision);
        double radWinkel2 = Math.acos((xKollision-this.x)/radius); //X_koordinate durch die x Koodinate der Kollision nehmen
        double aktuellerWinkel = (radWinkel2*180)/Math.PI;
        System.out.println(aktuellerWinkel);
        this.spokesList.add(new Spokes(aktuellerWinkel, this.radius, this.x, this.y));

    }
    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }
    public int getRadius(){
        return this.radius;
    }
    public ArrayList<Spokes> getSpokesList(){
        return this.spokesList;
    }

}
