package game;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Chris on 10.05.2015.
 */
public class Missile {

    private int x;
    private int y;
    private int radius;
    private double speed;


    public Missile(int x, int y, double speed){
        this.x = x;
        radius = x/100*10;
        this.y = y;
        this.speed = speed;
    }


    public void loadMissile(Graphics missile){

        missile.setColor(Color.GREEN);
        missile.fillOval(this.x, this.y, this.radius, this.radius);
        //missile.setColor(Color.BLACK);
        //missile.drawRect(this.x, this.y, 15, 15);
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
            if (spoke.getRadius()*1.2 >= c) {
                return true;
            }
        }

        return false;
    }
    public int getX(){
        return this.x;
    }

}
