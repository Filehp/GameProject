package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Chris on 11.06.2015.
 */
public class MultiplayerClient extends JPanel implements Runnable{
    static int x;
    static int y;

    boolean time=true;
    int timer;
    int minuten;
    int sekunden;
    long starttime = System.currentTimeMillis();

    private int playerID;

    String directionPlayer1;

    boolean shotMissile = false;
    double missilespeedPlayer1 = 0;

    int loadBar=0;
    int missilnumber = 99;
    int missileClipPlayer1;

    public ArrayList<Missile> missilePlayer1 = new ArrayList<>();
    public ArrayList<Missile> missilePlayer2 = new ArrayList<>();

    boolean startSpokes = true;

    private Canon KanonePlayer1;
    private Canon KanonePlayer2;
    private Wheel rad;
    InputMap im;
    ActionMap am;



    public MultiplayerClient(int x, int y, int missileClip, int time, int startSpokes, int speedWheel, int playerID) {
        this.setFocusable(true);
        this.playerID = playerID;
        this.missileClipPlayer1 = missileClip;
        KanonePlayer1 = new Canon(x, y, missileClip, true, 1);
        this.x = x;
        this.y = y;

        this.im = getInputMap(WHEN_IN_FOCUSED_WINDOW);
        this.am = getActionMap();

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, false), "pressed");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, true), "released");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, false), "left");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, false), "right");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, true), "moveReleased");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, true), "moveReleased");

        //Keybindings


        am.put("left", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                directionPlayer1 = "left";
            }
        });
        am.put("moveReleased", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                directionPlayer1 = null;
            }
        });
        am.put("right", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                directionPlayer1 = "right";
            }
        });

        am.put("pressed", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shotMissile = false;
                missilespeedPlayer1 = (missilespeedPlayer1 + 0.5);
                loadBar = (int) missilespeedPlayer1 * 10;
                if (missilespeedPlayer1 >= 10) {
                    missilespeedPlayer1 = 10;
                }

                System.out.println(missilespeedPlayer1);

                System.out.println("Pressed");
            }
        });

        am.put("released", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                directionPlayer1 = null;
                if (KanonePlayer1.getMissileCounter() > 0) {
                    missilePlayer1.add(new Missile(x, KanonePlayer1.getAbschussPositionX(), KanonePlayer1.getAbschussPositionY(), missilespeedPlayer1));
                    shotMissile = true;
                    KanonePlayer1.shotedmissile();
                    missilespeedPlayer1 = 0;
                    missileClipPlayer1--;
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

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void paintComponent(Graphics canon) {
        super.paintComponent(canon);



        try {
            //Verbindung zum MultiplayerServer
            InputStream is = null;
            OutputStream os = null;
            Socket socket = new Socket("localhost", 5000);
            //Daten senden

            os = socket.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(os);

            oos.writeObject(playerID);
            oos.writeObject(this.x);
            oos.writeObject(this.y);
            oos.writeObject(KanonePlayer1);
            oos.writeObject(missilePlayer1);
            oos.writeObject(missileClipPlayer1);
            oos.writeObject(new String("another object from the client"));
            oos.writeObject(new String("zweiter String"));
            System.out.println("Daten gesendet");


            //Daten empfangen
            is = socket.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(is);
            rad = (Wheel)ois.readObject();
            minuten = (int)ois.readObject();
            sekunden = (int)ois.readObject();

            is.close();
            oos.close();
            os.close();
            System.out.println("Daten empfangen");
            socket.close();

        //Rad laden
        rad.spinWheel();
        rad.loadWheel(canon, startSpokes);
        this.startSpokes = false;

        //KAnone Laden
        KanonePlayer1.canonLoad(canon);
       // KanonePlayer2.canonLoad(canon);
        //KanonePlayer1 bewegen
        if (this.directionPlayer1 != null) {
            KanonePlayer1.moveCanon(this.directionPlayer1);
        }


        //Geschoss abfeuern
        this.missilnumber = KanonePlayer1.shotMissile(canon, missilePlayer1);
        if (this.missilnumber != 99) {
            //System.out.println(missilnumber);
            missilePlayer1.remove(this.missilnumber);
        }

        /*this.missilnumber = KanonePlayer2.shotMissile(canon, missilePlayer2);
        if (this.missilnumber != 99) {
            //System.out.println(missilnumber);
            missilePlayer2.remove(this.missilnumber);

        }*/

        //Kollision abfangen

        //Sieges Bedingung !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!


        //Verbleibende Zeit anzeigen
        canon.setFont(new Font("default", Font.BOLD, this.y / 100 * 3));
        if (sekunden >= 10) {
            canon.drawString("0" + minuten + ":" + sekunden, this.x / 100 * 14, this.y / 100 * 88);
        } else {
            canon.drawString("0" + minuten + ":0" + sekunden, this.x / 100 * 14, this.y / 100 * 88);
        }

        //Schussst�rke anzeigen
        canon.clearRect(this.x / 100 * 64, this.y / 100 * 85, this.x / 100 * 18, this.y / 100 * 3);
        canon.setColor(new Color(0, 128, 128));
        canon.fillRect(this.x / 100 * 64, this.y / 100 * 85, loadBar, this.y / 100 * 3);



        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }



    }




}