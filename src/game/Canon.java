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
    private int canonX = this.x/100*37;
    private int canonY = this.y/100*75;
    private int canonWigth = this.x/100*3;
    private int canonHeigth = this.x/100*10;
    private int xPositionMissile;
    private int yPositionMissile;
    private int grenzeLinks = this.x/100*18;
    private int grenzeRechts = this.x/100*75;
    private int radiusMissel= this.x/100*3;
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
        canon.fillRect(this.canonX, this.canonY, canonWigth, canonHeigth);
    }


    public void moveCanon(String direction){
        if(direction.equals("left")){
            this.canonX = this.canonX - 5;
            if(this.canonX <= grenzeLinks){
                this.canonX = grenzeLinks;
            }

        }
        if(direction.equals("right")) {
            this.canonX = this.canonX + 5;
            //System.out.println(this.x);
            if(this.canonX >= (grenzeRechts-canonWigth)){
                this.canonX = grenzeRechts-canonWigth;
            }
            //this.x++;
        }else {
            //donothing
        }
    }

    public int getAbschussPositionX(){
        return this.xPositionMissile = this.canonX +(canonWigth/2)-radiusMissel;

    }
    public int getAbschussPositionY(){
        return this.yPositionMissile = this.canonY -(canonHeigth/2);
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
        System.out.println(this.canonX +" And " + this.canonY);
    }
    public int getMissileCounter(){
        return this.missileCounter;
    }
    public void shotedmissile(){
        this.missileCounter--;
    }
}
