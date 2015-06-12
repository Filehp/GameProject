package multiplayer;

import javax.swing.*;

import entity.Canon;
import entity.Missile;
import entity.Wheel;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

/**
 * Created by Chris on 10.06.2015.
 */
public class MultiplayerLevel extends JPanel implements Runnable, KeyListener {
    static int x;
    static int y;

    boolean time=true;
    int timer;
    int minuten;
    int sekunden;
    long starttime = System.currentTimeMillis();

    String directionPlayer1;
    String directionPlayer2;
    boolean shotMissile = false;
    double missilespeedPlayer1 = 0;
    double missilespeedPlayer2 = 0;
    int loadBar=0;
    int missilnumber = 99;
    int missileClipPlayer1;
    int missileClipPlayer2;
    public ArrayList<Missile> missilePlayer1 = new ArrayList<>();
    public ArrayList<Missile> missilePlayer2 = new ArrayList<>();

    boolean startSpokes = true;

    private Canon KanonePlayer1;
    private Canon KanonePlayer2;
    private Wheel rad;


    public MultiplayerLevel(int x, int y, int missileClip, int time, int startSpokes, int speedWheel) {
        this.setFocusable(true);
        this.addKeyListener(this);
        this.timer = time;
        this.missileClipPlayer1 = missileClip;
        this.missileClipPlayer2 = missileClip;
        KanonePlayer1 = new Canon(x, y, missileClip, true, 1);
        KanonePlayer2 = new Canon(x, y, missileClip, true, 2);
        rad = new Wheel(x, y, startSpokes, speedWheel,true);
        this.x = x;
        this.y = y;

    }


    @Override
    public void run() {
        while (time) {
            repaint();
            try {
                Thread.sleep(20);

                //Zeit wird berechnet und geprüft
                long currentTime = System.currentTimeMillis()-starttime;
                int currentTimeOutput = (int) (timer - currentTime);
                minuten = (int) currentTimeOutput/1000/60;
                sekunden = (int) currentTimeOutput/1000%60;
                if(currentTime>=timer){
                    time = false;
                    System.out.println("Timeout");
                }


            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }


    @Override
    public void paintComponent(Graphics canon){
        super.paintComponent(canon);

        //Rad laden
        rad.loadWheel(canon, startSpokes);
        this.startSpokes = false;
        //Rad bewegen
        rad.spinWheel();
        //KAnone Laden
        KanonePlayer1.canonLoad(canon);
        KanonePlayer2.canonLoad(canon);
        //KanonePlayer1 bewegen
        if(this.directionPlayer1 !=null) {
            KanonePlayer1.moveCanon(this.directionPlayer1);

        }
        if(this.directionPlayer2 !=null) {
            KanonePlayer2.moveCanon(this.directionPlayer2);

        }

        //Geschoss abfeuern

        this.missilnumber = KanonePlayer1.shotMissile( canon, missilePlayer1);
        if (this.missilnumber != 99) {
            //System.out.println(missilnumber);
            missilePlayer1.remove(this.missilnumber);

        }
        this.missilnumber = KanonePlayer2.shotMissile( canon, missilePlayer2);
        if (this.missilnumber != 99) {
            //System.out.println(missilnumber);
            missilePlayer2.remove(this.missilnumber);

        }
        //Kollision abfangen Player1
        if(missilePlayer1.size()>0) {
            for (int i = 0; i < missilePlayer1.size(); i++) {
                //Auf Kollision mit rad prüfen

                boolean Kollirad = missilePlayer1.get(i).collidesWithWheel(rad);

                //Auf kollision mit Speiche prüfen
                boolean Kollispeiche = missilePlayer1.get(i).coollidesWithSpokes(rad.getSpokesList());

                //Kollision mit Rad
                if (Kollirad && Kollispeiche==false) {
                    //Was soll passieren wenn das Geschoss auf das Rad trifft?

                    //Speiche wird dan der Stelle hinzugefügt
                    rad.addSpokes(missilePlayer1.get(i).getX());
                    //Geschoss wird entfernt
                    missilePlayer1.remove(i);


                }else {
                    //Kollision mit Speiche
                    if (Kollispeiche) {
                        //Was soll passieren wenn das Geschoss auf eine Speiche trifft?
                        this.setBackground(Color.RED);

                        System.out.println("Speiche getroffen! Player2 gewinnt");
                        missilePlayer1.remove(i);
                    }
                }

            }
        }
        //Kollision abfangen Player2
        if(missilePlayer2.size()>0) {
            for (int i = 0; i < missilePlayer2.size(); i++) {
                //Auf Kollision mit rad prüfen

                boolean Kollirad = missilePlayer2.get(i).collidesWithWheel(rad);

                //Auf kollision mit Speiche prüfen
                boolean Kollispeiche = missilePlayer2.get(i).coollidesWithSpokes(rad.getSpokesList());

                //Kollision mit Rad
                if (Kollirad && Kollispeiche==false) {
                    //Was soll passieren wenn das Geschoss auf das Rad trifft?

                    //Speiche wird dan der Stelle hinzugefügt
                    rad.addSpokes(missilePlayer2.get(i).getX());
                    //Geschoss wird entfernt
                    missilePlayer2.remove(i);


                }else {
                    //Kollision mit Speiche
                    if (Kollispeiche) {
                        //Was soll passieren wenn das Geschoss auf eine Speiche trifft?
                        this.setBackground(Color.RED);

                        System.out.println("Speiche getroffen! Player1 gewinnt");
                        missilePlayer1.remove(i);
                    }
                }

            }
        }
        //Sieges Bedingung !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        if(this.missileClipPlayer1 ==0 && missilePlayer1.size()==0){
            System.out.println("Du hast gewonnen Player1");
            System.out.println("Du hast verloren Player2");
        }
        if(this.missileClipPlayer2 ==0 && missilePlayer2.size()==0){
            System.out.println("Du hast gewonnen Player2");
            System.out.println("Du hast verloren Player1");
        }
        //Verbleibende Zeit anzeigen
        canon.setFont(new Font("default", Font.BOLD, this.y/100*3));
        if(sekunden>=10) {
            canon.drawString("0"+minuten + ":" + sekunden, this.x/100*14, this.y/100*88);
        }else{
            canon.drawString("0" +minuten + ":0" + sekunden, this.x/100*14, this.y/100*88);
        }
        //Schussstärke anzeigen
        canon.clearRect(this.x/100*64,this.y/100*85,this.x/100*18,this.y/100*3);
        canon.setColor(new Color(0, 128, 128));
        canon.fillRect(this.x/100*64,this.y/100*85,loadBar,this.y/100*3);

    }






    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        if(e.getKeyCode()==37){
            directionPlayer1 ="left";
            System.out.println("left");
        }
        if(e.getKeyCode()==39){
            directionPlayer1 ="right";
        }
        if(e.getKeyCode()==96){
            shotMissile = false;
            this.missilespeedPlayer1 = (missilespeedPlayer1 +0.5);
            this.loadBar = (int) this.missilespeedPlayer1 *10;
            if(missilespeedPlayer1 >=10){
                missilespeedPlayer1 =10;
            }
        }
        if(e.getKeyCode()==65){
            directionPlayer2 ="left";
            System.out.println("left");
        }
        if(e.getKeyCode()==68){
            directionPlayer2 ="right";
        }
        if(e.getKeyCode()==32){
            shotMissile = false;
            this.missilespeedPlayer2 = (missilespeedPlayer2 +0.5);
            if(missilespeedPlayer2 >=10){
                missilespeedPlayer2 =10;
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        directionPlayer1 =null;
        if(e.getKeyCode()==96) {
            if (KanonePlayer1.getMissileCounter() > 0) {
                this.missilePlayer1.add(new Missile(this.x, KanonePlayer1.getAbschussPositionX(), KanonePlayer1.getAbschussPositionY(), missilespeedPlayer1));
                shotMissile = true;
                KanonePlayer1.shotedmissile();
                missilespeedPlayer1 = 0;
                this.missileClipPlayer1--;
            }
        }
        directionPlayer2 =null;
        if(e.getKeyCode()==32) {
            if (KanonePlayer2.getMissileCounter() > 0) {
                this.missilePlayer2.add(new Missile(this.x, KanonePlayer2.getAbschussPositionX(), KanonePlayer2.getAbschussPositionY(), missilespeedPlayer2));
                shotMissile = true;
                KanonePlayer2.shotedmissile();
                missilespeedPlayer2 = 0;
                this.missileClipPlayer2--;
            }
        }

    }
}
