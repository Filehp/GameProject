package game;

import javax.swing.*;
import javax.swing.Timer;

import newMenu.Button;
import newMenu.Difficulty;
import newMenu.Game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;

/**
 * Created by Chris on 09.05.2015.
 */
public class testFrame extends JComponent implements Runnable {

    static int x;
    static int y;
    
    private Thread thread;
    private boolean run = false;

    boolean time=true;
    int timer;
    int minuten;
    int sekunden;
    long starttime = System.currentTimeMillis();

    String direction;
    boolean shotMissile = false;
    double missilespeed = 0;
    int loadBar=0;
    int missilnumber = 99;
    int missileClip;
    public ArrayList<Missile> missile = new ArrayList<>();

    boolean startSpokes = true;

    private Canon Kanone;
    private Wheel rad;
    
    private KAdapter key = new KAdapter();
    
    
    public testFrame(int x, int y, int missileClip, int time, int startSpokes, int speedWheel) {
    	
    	Game.panel.setFocusable(false);
    	this.setFocusable(true);
        this.addKeyListener(key);
        this.requestFocusInWindow();
       

        
        start();
        this.timer = time;
        this.missileClip = missileClip;
        Kanone = new Canon(x, y, missileClip);
        rad = new Wheel(x, y, startSpokes, speedWheel);
        this.x = x;
        this.y = y;
        
    }


    @Override
    public void run() {
        while (run) {
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
        Kanone.canonLoad(canon);

        //Kanone bewegen
        if(this.direction!=null) {
            Kanone.moveCanon(this.direction);
        }
        //Geschoss abfeuern

            this.missilnumber = Kanone.shotMissile( canon, missile);
            if (this.missilnumber != 99) {
                //System.out.println(missilnumber);
                missile.remove(this.missilnumber);

        }
        //Kollision abfangen
        if(missile.size()>0) {
            for (int i = 0; i < missile.size(); i++) {
                //Auf Kollision mit rad prüfen

                boolean Kollirad = missile.get(i).collidesWithWheel(rad);

                //Auf kollision mit Speiche prüfen
                boolean Kollispeiche = missile.get(i).coollidesWithSpokes(rad.getSpokesList());

                //Kollision mit Rad
                if (Kollirad && Kollispeiche==false) {
                    //Was soll passieren wenn das Geschoss auf das Rad trifft?

                    //Speiche wird dan der Stelle hinzugefügt
                    rad.addSpokes(missile.get(i).getX());
                    //Geschoss wird entfernt
                    missile.remove(i);


                }else {
                    //Kollision mit Speiche
                    if (Kollispeiche) {
                        //Was soll passieren wenn das Geschoss auf eine Speiche trifft?
                        this.setBackground(Color.RED);

                        System.out.println("Speiche getroffen!");
                        missile.remove(i);
                        
                    }
                }

            }
        }
        //Sieges Bedingung !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        if(this.missileClip==0 && missile.size()==0){
            System.out.println("Du hast gewonnen");

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
    
    

	/**
	 * Key Adapter is used to control the snake
	 */
	private class KAdapter implements KeyListener {
		

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        if(e.getKeyCode()==37){
            direction="left";
            System.out.println("left");
        }
        if(e.getKeyCode()==39){
            direction="right";
        }
        if(e.getKeyCode()==32){
            shotMissile = false;
            missilespeed = (missilespeed+0.5);
            loadBar = (int) missilespeed*10;
            if(missilespeed>=10){
                missilespeed=10;
            }

            System.out.println(missilespeed);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        direction=null;
        if(e.getKeyCode()==32) {
            if (Kanone.getMissileCounter() > 0) {
                missile.add(new Missile(x, Kanone.getAbschussPositionX(), Kanone.getAbschussPositionY(), missilespeed));
                shotMissile = true;
                Kanone.shotedmissile();
                missilespeed = 0;
                missileClip--;
            }
        }

    }
	}
    
	public void start() {
		run = true; // Whenever run is true game is running
		thread = new Thread(this, "Game Loop"); // Creates new thread
		thread.start(); // Starts thread
	}
	
}


