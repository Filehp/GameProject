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
public class testFrame extends JPanel implements Runnable, KeyListener {

	Icon backIcon = new ImageIcon("resources/Back.png");
	private Button back = new Button(backIcon);

    int x = 400;
    int y = 400;

    boolean time=true;
    long starttime = System.currentTimeMillis();

    String direction;
    boolean shotMissile = false;
    double missilespeed = 0;
    int missilnumber = 99;
    public ArrayList<Missile> missile = new ArrayList<>();

    boolean startSpokes = true;
    static Canon Kanone = new Canon(150, 300);
    static Wheel rad = new Wheel(190, 110, 4, 1);
    
    
    public testFrame() {
        this.setFocusable(true);
        this.addKeyListener(this);
        
        back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			
				Game.changePanel("levelmenu", testFrame.this);
			}
		});

    }


    @Override
    public void run() {
        while (time) {
            repaint();
            try {
                Thread.sleep(20);

                long currentTime = System.currentTimeMillis()-starttime;
                if(currentTime>=60000){
                    time = false;
                    System.out.println("Timeout");
                }


            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }


    /*public static void main(String[] args) {
        JFrame frame = new JFrame();
        testFrame panel = new testFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.add(panel);

        new Thread(panel).start();

        Kanone.getXY();
        frame.setVisible(true);

    }*/

    @Override
    public void paintComponent(Graphics canon){
        super.paintComponent(canon);
        
        back.setBounds(Game.WIDTH / 2 - 150, Game.HEIGHT / 2 + 300, 275, 55);
        add(back);

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

            this.missilnumber = Kanone.shotMissile(canon, missile);
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
    }
    
    




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
            this.missilespeed = (missilespeed+0.5);
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
                this.missile.add(new Missile(Kanone.getAbschussPositionX(), Kanone.getAbschussPositionY(), missilespeed));
                shotMissile = true;
                Kanone.shotedmissile();
                missilespeed = 0;
            }
        }

    }
}

