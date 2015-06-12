package entity;


import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;


/**
 * Created by Chris on 09.05.2015.
 */
public class Canon  implements Serializable {
    private int missileCounter;
    private int canonX;
    private int canonY;
    private int canonWigth;
    private int canonHeigth;
    private int xPositionMissile;
    private int yPositionMissile;
    private int grenzeLinks;
    private int grenzeRechts;
    private int radiusMissel;
    public ArrayList<Missile> missile=new ArrayList< Missile>();

    //Constructor
    public Canon(int x, int y, int missileCount){
        System.out.println(x + "and"+ y);
        this.canonX = x/100*37;
        this.canonY = y/100*75;
        this.canonWigth = x/100*10;
        this.canonHeigth = x/100*3;
        this.grenzeLinks = x/100*30;
        this.grenzeRechts = x/100*75;
        this.radiusMissel= x/100*3;
        this.missileCounter = missileCount;
    }
    public Canon(int x, int y, int missileCount, boolean multi, int Player){
        if (Player==1) {
            System.out.println(x + "and" + y);
            this.canonX = x / 100 * 37;
            this.canonY = y / 100 * 75;
            this.canonWigth = x / 100 * 10;
            this.canonHeigth = x / 100 * 3;
            this.grenzeLinks = x / 100 * 30;
            this.grenzeRechts = x / 100 * 75;
            this.radiusMissel = x / 100 * 3;
            this.missileCounter = missileCount;
        }
        if (Player==2) {
            System.out.println(x + "and" + y);
            this.canonX = x / 100 * 50;
            this.canonY = y / 100 * 75;
            this.canonWigth = x / 100 * 10;
            this.canonHeigth = x / 100 * 3;
            this.grenzeLinks = x / 100 * 30;
            this.grenzeRechts = x / 100 * 75;
            this.radiusMissel = x / 100 * 3;
            this.missileCounter = missileCount;
        }
    }

    public void canonLoad(Graphics canon) {

        canon.setColor(Color.BLACK);
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

    public int getAbschussPositionX(){ return this.xPositionMissile = this.canonX +(canonWigth/2)-radiusMissel/2; }
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