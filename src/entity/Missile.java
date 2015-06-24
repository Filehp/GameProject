package entity;
import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Chris on 10.05.2015.
 */
public class Missile implements Serializable {

    private int x;
    private int y;
    private int radius;
    private double speed;


    public Missile(int x, int positionX, int positionY, double speed){
        this.x = positionX;
        radius = x/100*3 ;
        this.y = positionY;
        this.speed = speed;
    }


    public void loadMissile(Graphics missile){
        missile.setColor(Color.BLACK);
        missile.fillOval(this.x, this.y, this.radius, this.radius);
        //missilePlayer1.setColor(Color.BLACK);
        //missilePlayer1.drawRect(this.x, this.y, 15, 15);
    }

    public int moveMissile(){

            this.y = (int) (this.y - this.speed);

            return this.y;
    }

    public boolean collidesWithWheel(Wheel wheel) {
        double c = Math.sqrt(Math.pow(this.x - wheel.getX(), 2) + Math.pow(this.y - wheel.getY(), 2));
        if (wheel.getRadius()*2 >= c) {
            return true;
        }
        return false;
    }
    public boolean coollidesWithSpokes (ArrayList<Spokes> spokes){
        for(Spokes spoke:spokes){
            double c = Math.sqrt(Math.pow(this.x - spoke.getX(), 2) + Math.pow(this.y - spoke.getY(), 2));
            if (spoke.getRadius()*2 >= c) {
                return true;
            }
        }

        return false;
    }
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
    public int getX(){
        return this.x;
    }

}
