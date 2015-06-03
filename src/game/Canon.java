package game;


import java.awt.*;
import java.util.ArrayList;


/**
 * Created by Chris on 09.05.2015.
 */
public class Canon {
    public int missileCounter;
    private int x;
    private int y;
    private int canonWigth = 80;
    private int canonHeigth = 50;
    private int xPositionMissile;
    private int yPositionMissile;
    public ArrayList<Missile> missile=new ArrayList< Missile>();

    //Constructor
    public Canon(int x, int y, int missleCounter){
        this.x = x;
        this.y = y;
        this.missileCounter = missileCounter;
    }
    public Canon(int x, int y){
        this.x = x;
        this.y = y;
        this.missileCounter = 15;
    }

    public void canonLoad(Graphics canon) {

        canon.setColor(Color.RED);
        canon.fillRect(this.x, this.y, canonWigth, canonHeigth);
    }


    public void moveCanon(String direction){
        if(direction.equals("left")){
            this.x = this.x - 5;
            if(this.x <= 75){
                this.x = 75;
            }
            //this.x--;
        }
        if(direction.equals("right")) {
            this.x = this.x + 5;
            //System.out.println(this.x);
            if(this.x >= (300-canonWigth)){                  /// nochmal nachrechne warum -15 !!!!
                this.x = 300-canonWigth;
            }
            //this.x++;
        }else {
            //donothing
        }
    }

    public int getAbschussPositionX(){
        return this.xPositionMissile = this.x +(canonWigth/2)-10; //-10 ist der Radius der Missile

    }
    public int getAbschussPositionY(){
        return this.yPositionMissile = this.y -(canonHeigth/2);
    }

    public int shotMissile(Graphics missileGrafic, ArrayList<Missile> missile){
            int missilenumber = 99;
            for (Missile missil:missile) {
                missil.loadMissile(missileGrafic);
                int missilePosition = missil.moveMissile();
                if (missilePosition <= 0) {
                   missilenumber = missile.indexOf(missil);
                }else {missilenumber=99;}
            }

            return missilenumber;



    }

    public void getXY(){
        System.out.println(this.x +" And " + this.y);
    }
    public int getMissileCounter(){
        return this.missileCounter;
    }
    public void shotedmissile(){
        this.missileCounter--;
    }
}
