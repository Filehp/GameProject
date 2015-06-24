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

    /**
     * Umegbungsvariabeln
     */
    private int wheelX;
    private int wheelY;
    private int radius;
    private int speed;
    private int numberSpokes;
    private ArrayList <Spokes> spokesList = new ArrayList<Spokes>();

    /**
     * Constructur für Singelplayer
     */
    public Wheel(int x, int y, int numberStartSpokes, int speed){
        wheelX = x/100*52;
        wheelY = y/100*28;
        radius = x/100*12;
        this.numberSpokes = numberStartSpokes;
        this.speed = speed;
    }

    /**
     * Constructur für Multiplayer
     */
    public Wheel(int x, int y, int numberStartSpokes, int speed, boolean multi){
        wheelX = x/100*52;
        wheelY = y/100*28;
        radius = x/100*12;
        this.numberSpokes = numberStartSpokes;
        this.speed = speed;
    }


    /**
     * Laden der Grafik für das Rad
     * Dabei wird unterschieden ob die Startspeichen geladen werden oder nicht
     * dafür muss der Methode true oder false mitgegeben werden
     */
    public void loadWheel(Graphics wheel, boolean startAdd){

        /**
         * Wheel zeichnen
         */
        Graphics2D g2 = (Graphics2D) wheel;
        g2.setPaint(new Color(0, 128, 128));
        Ellipse2D circle = new Ellipse2D.Double();
        circle.setFrameFromCenter(this.wheelX, this.wheelY, this.wheelX + radius, this.wheelY + radius); ///(x-achse, y-achse, RadiusX , RadiusY)
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2.draw(circle);        //zeichnet die ellipse
        g2.fill(circle);        //fuellt die ellipse aus mit der Farbe

        /**
         * ist startAdd true werden die Startspeichen hinzugefügt
         */
        if(startAdd) {
            //addspokes
            addSpokes();
        }

        /**
         * Speichen werden geladen aus der spokesList
         */
        for(Spokes spoke : this.spokesList){
            spoke.loadSpoke(wheel);
        }

    }

    /**
     * Lässt das Rad sich drehen in dem die Speichen neu berechnet werden
     */
    public  void spinWheel(){
        for(Spokes spoke : this.spokesList){
            //System.out.println(spoke.getWinkel());
            double newWinkel = spoke.getWinkel()+this.speed;
            spoke.setWinkel(newWinkel);
            spoke.moveSpoke(newWinkel, this.radius, this.wheelX, this.wheelY);
        }


    }

    /**
     * Fügt Speichen hinzu die beim Start erzeugt werden sollen
     */
    public void addSpokes(){

        double startwinkel = 360/this.numberSpokes;
        double winkelposition=startwinkel;

        for(int i = 0;i<this.numberSpokes;i++ ){
            this.spokesList.add(new Spokes(winkelposition, this.radius, this.wheelX, this.wheelY)) ;
            winkelposition = winkelposition+startwinkel;

        }
    }

    /**
     * Fügt neue Speichen hinzu indem man die X Position der missile die mit
     * dem wheel kollidiert ist übergibt
     */
    public  void addSpokes(int xKollision){
        //Winkel berechen aus XWert wo die Kollision mit dem Wheel stattfand
        int aktuellerWinkel=0;
        if(xKollision>=wheelX){
            aktuellerWinkel = 90 - ((xKollision - wheelX)*(90/radius));
        }else if(xKollision<wheelX){
            aktuellerWinkel = 70 + ((wheelX-xKollision)*(90/radius));
        }

        //System.out.println(aktuellerWinkel);
        //Hinzufügen und erzeugen der neuen Speiche zu spokeslist
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
