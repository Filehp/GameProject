package game;

import javax.swing.*;


import newMenu.Game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.*;

/**
 * Created by Chris on 09.05.2015.
 */
public class testFrame extends JPanel implements Runnable {

    static int x;
    static int y;

    boolean time = true;
    int timer;
    int minuten;
    int sekunden;
    long starttime = System.currentTimeMillis();

    String direction;
    boolean shotMissile = false;
    double missilespeed = 0;
    int loadBar = 0;
    int missilnumber = 99;
    int missileClipPlayer;
    public ArrayList<Missile> missile = new ArrayList<>();

    boolean startSpokes = true;

    private Canon Kanone;
    private Wheel rad;
    InputMap im;
    ActionMap am;
    
    private Thread thread;
    private boolean run = false;

    public testFrame(int x, int y, int missileClip, int time, int startSpokes, int speedWheel) {
        this.setFocusable(true);
        this.timer = time;
        this.missileClipPlayer = missileClip;

        Kanone = new Canon(x, y, missileClip);
        rad = new Wheel(x, y, startSpokes, speedWheel);

        this.x = x;
        this.y = y;

        start();

        this.im = getInputMap(WHEN_IN_FOCUSED_WINDOW);
        this.am = getActionMap();

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, false), "pressed");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, true), "released");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, false), "left");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, false), "right");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, true), "moveReleased");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, true), "moveReleased");

        am.put("left", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                direction = "left";
            }
        });
        am.put("moveReleased", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                direction = null;
            }
        });
        am.put("right", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                direction = "right";
            }
        });

        am.put("pressed", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shotMissile = false;
                missilespeed = (missilespeed + 0.5);
                loadBar = (int) missilespeed * 10;
                if (missilespeed >= 10) {
                    missilespeed = 10;
                }

                System.out.println(missilespeed);

                System.out.println("Pressed");
            }
        });

        am.put("released", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                direction = null;
                if (Kanone.getMissileCounter() > 0) {
                    missile.add(new Missile(x, Kanone.getAbschussPositionX(), Kanone.getAbschussPositionY(), missilespeed));
                    shotMissile = true;
                    Kanone.shotedmissile();
                    missilespeed = 0;
                    missileClipPlayer--;
                }
                System.out.println("released");
            }
        });

    }


	@Override
    public void run() {
        while (time) {
            repaint();
            try {
                Thread.sleep(20);



                //Zeit wird berechnet und geprüft
                long currentTime = System.currentTimeMillis() - starttime;
                int currentTimeOutput = (int) (timer - currentTime);
                minuten = (int) currentTimeOutput / 1000 / 60;
                sekunden = (int) currentTimeOutput / 1000 % 60;
                if (currentTime >= timer) {
                    time = false;
                    System.out.println("Timeout");
                }


            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }


    @Override
    public void paintComponent(Graphics canon) {
        super.paintComponent(canon);
        

        //Rad laden
        rad.loadWheel(canon, startSpokes);
        this.startSpokes = false;
        //Rad bewegen
        rad.spinWheel();
        //KAnone Laden
        Kanone.canonLoad(canon);

        //Kanone bewegen
        if (this.direction != null) {
            Kanone.moveCanon(this.direction);
            this.direction=null;
        }
        //Geschoss abfeuern

        this.missilnumber = Kanone.shotMissile(canon, missile);
        if (this.missilnumber != 99) {
            //System.out.println(missilnumber);
            missile.remove(this.missilnumber);

        }
        //Kollision abfangen
        if (missile.size() > 0) {
            for (int i = 0; i < missile.size(); i++) {
                //Auf Kollision mit rad prüfen

                boolean Kollirad = missile.get(i).collidesWithWheel(rad);

                //Auf kollision mit Speiche prüfen
                boolean Kollispeiche = missile.get(i).coollidesWithSpokes(rad.getSpokesList());

                //Kollision mit Rad
                if (Kollirad && Kollispeiche == false) {
                    //Was soll passieren wenn das Geschoss auf das Rad trifft?

                    //Speiche wird dan der Stelle hinzugefügt
                    rad.addSpokes(missile.get(i).getX());
                    //Geschoss wird entfernt
                    missile.remove(i);


                } else {
                    //Kollision mit Speiche
                    if (Kollispeiche) {
                        //Was soll passieren wenn das Geschoss auf eine Speiche trifft?
                        this.setBackground(Color.RED);

                        System.out.println("Speiche getroffen!");
                        missile.remove(i);
                        Game.changePanel("gameResult"); //displays result
                        stop(); //stops the thread
                        
                    }
                }

            }
        }
        //Sieges Bedingung !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        if (this.missileClipPlayer == 0 && missile.size() == 0) {
            System.out.println("Du hast gewonnen");

        }
        //Verbleibende Zeit anzeigen
        canon.setFont(new Font("default", Font.BOLD, this.y / 100 * 3));
        if (sekunden >= 10) {
            canon.drawString("0" + minuten + ":" + sekunden, this.x / 100 * 14, this.y / 100 * 88);
        } else {
            canon.drawString("0" + minuten + ":0" + sekunden, this.x / 100 * 14, this.y / 100 * 88);
        }
        //Schussstärke anzeigen
        canon.clearRect(this.x / 100 * 64, this.y / 100 * 85, this.x / 100 * 18, this.y / 100 * 3);
        canon.setColor(new Color(0, 128, 128));
        canon.fillRect(this.x / 100 * 64, this.y / 100 * 85, loadBar, this.y / 100 * 3);
        

		

    }
    
    private void start() {
    	run = true;
		thread = new Thread(this, "Game Loop"); // Creates new thread
		thread.start(); // Starts thread
		
	}
    private void stop() {
    	run = false;
    	time = false;

    }
    


}









