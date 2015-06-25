package entity;
import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Chris on 10.05.2015.
 */
public class Missile implements Serializable {

    private int x;                      // X Position der Missile
    private int y;                      // Y Position der Missile
    private int radius;                 // radius der Missile
    private double speed;               // Geschwindigkeit der Missile

    /**
     *
     * Constructur
     * Übergegben werden x Auflösung und die X und Y Position der Kanone sowie
     * die Geschwindigkeit mit der die Missile abgefeuert wird
     */
    public Missile(int x, int positionX, int positionY, double speed){
        this.x = positionX;
        this.y = positionY;
        radius = x/100*3;
        this.speed = speed;
    }

    /**
     * Lädt die Grafik für die Missile
     *
     */
    public void loadMissile(Graphics missile){
        missile.setColor(Color.BLACK);
        missile.fillOval(this.x, this.y, this.radius, this.radius);
    }

    /**
     *
     * Bewegt die Missile entsprechend der GEschwindigkeit
     */
    public int moveMissile(){
            this.y = (int) (this.y - this.speed);
            return this.y;
    }

    /**
     * Kollision mit dem Rad
     */
    public boolean collidesWithWheel(Wheel wheel) {
        /**
         * Berechung ob eine Kollision zwischen dem wheel und der missile stattgefunden hat
         */
        double c = Math.sqrt(Math.pow(this.x - wheel.getX(), 2) + Math.pow(this.y - wheel.getY(), 2));
        if (wheel.getRadius()*2 >= c) {
            return true;
        }

        /**
         * gibt false zuruek sollte keine Kollision stattgefunden haben
         */
        return false;
    }


    /**
     * Kollision mit dem Speichen
     */
    public boolean coollidesWithSpokes (ArrayList<Spokes> spokes){
        /**
         * Berechung ob eine Kollision zwischen einer Speiche und der missile stattgefunden hat
         */
        for(Spokes spoke:spokes){
            double c = Math.sqrt(Math.pow(this.x - spoke.getX(), 2) + Math.pow(this.y - spoke.getY(), 2));
            if (spoke.getRadius()*1.6 >= c) {
                return true;
            }
        }
        return false;

    }

    /**
     * Anpassung der Auflösung für MUltiplayer
     * siehe auch Class canon methode aufloesungAnpassen
     */
    public void aufloesungAnpassen(double faktorX, double faktorY, boolean operator){

        /**
         * operator bestimmt ob der Faktor multipliziert oder geteilt wird
         * true = miltiplizieren
         * false = teilen
         */

        if(operator==true) {
            this.x = (int) (this.x * faktorX);
            this.radius = (int) (this.radius * faktorX);
            this.y = (int) (this.y * faktorY);
        }
        if(operator==false) {
            this.x = (int) (this.x / faktorX);
            this.radius = (int) (this.radius / faktorX);
            this.y = (int) (this.y / faktorY);
        }
    }

    /**
     * Gibt die X position der Missile aus
     */
    public int getX(){
        return this.x;
    }

}
