package multiplayer;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

import entity.*;
import game.Game;
import game.GamePanel;
import game.GamePanel.Diff;
import menu.*;
import menu.Button;
import menu.Menu;

/**
 * Created by Chris on 11.06.2015.
 */
public class MultiplayerClient extends JPanel implements Runnable{
    /**
     * Aufl�sungsvariabeln
     */
    static int x;
    static int y;

    String adress;
    private int playerID;



    boolean startSpokes = true;

    private Canon KanonePlayer1;
    private String directionPlayer1;
    private boolean shotMissile = false;
    private double missilespeedPlayer1 = 0;
    private int loadBar=0;                          //LadeBalken fuer die Gschwindigkeit
    private int missilnumber = 99;
    private int missileClipPlayer1;
    private ArrayList<Missile> missilePlayer1 = new ArrayList<>();

    /**
     * Variabeln Player 2
     */
    private Canon KanonePlayer2;
    private ArrayList<Missile> missilePlayer2 = new ArrayList<>();

    /**
     * Rad Variabeln
     */
    private Wheel rad;


    /**
     * Allgemeine Game Variablen
     */
    boolean time=true;
    private boolean waitGameStart;
    private Thread thread;

    private int victory;
    private boolean finishedGame=false;

    /**
     * Variabeln fuer das scorePanel
     */
    
    private boolean scorePanel;
    Icon quit1Icon = new ImageIcon("resources/Quit1.png");
    Icon quitIcon = new ImageIcon("resources/Quit.png");
    Icon replayIcon = new ImageIcon("resources/Replay.png");
    private Button quit2 = new Button(quit1Icon);
    private Button quit = new Button(quitIcon);
    private Button replay = new Button(replayIcon);
    private JLabel yourMissles = new JLabel();
    
    private Image background;

    /**
     * Keybindingsvariabeln
     */
    InputMap im;
    ActionMap am;

    /**
     * Constructor fuer den MultiplayerClient
     */
    public MultiplayerClient(int x, int y, int missileClip, int playerID, String adress) {
    	scorePanel=false;
    	
		try {
			background = ImageIO.read(new File("resources/Hintergrund1.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

        this.setFocusable(true);
        this.playerID = playerID;
        this.missileClipPlayer1 = missileClip;
        KanonePlayer1 = new Canon(x, y, missileClip);
        this.x = x;
        this.y = y;
        this.adress = adress;
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
        
      //Buttons in ScorePanel 
        replay.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Game.changePanel("multiplayerClientHost", MultiplayerClient.this);
		}});
        
        quit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Game.changePanel("menu", MultiplayerClient.this);
			}
		});		
        quit2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Game.changePanel("menu", MultiplayerClient.this);
            }
        });
        

        //Starts the thread
        start();
    }

    @Override
    public void run() {
        while (time) {
            repaint();
            try {
                Thread.sleep(85);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                //Verbindung zum MultiplayerServer
                InputStream is = null;
                OutputStream os = null;
                ObjectOutputStream oos = null;
                ObjectInputStream ois = null;
                Socket socket = null;

                socket = new Socket(this.adress, 5000);

                //Daten senden
                os = socket.getOutputStream();
                oos = new ObjectOutputStream(os);

                oos.writeObject(playerID);
                oos.writeObject(this.x);
                oos.writeObject(this.y);
                oos.writeObject(KanonePlayer1);
                oos.writeObject(missilePlayer1);
                oos.writeObject(missileClipPlayer1);
                oos.writeObject(finishedGame);
                System.out.println("Daten gesendet");

                os.flush();

                //Daten empfangen
                is = socket.getInputStream();
                ois = new ObjectInputStream(is);

                rad = (Wheel) ois.readObject();
                KanonePlayer2 = (Canon) ois.readObject();
                missilePlayer1 = (ArrayList) ois.readObject();
                missilePlayer2 = (ArrayList) ois.readObject();
                waitGameStart = (boolean) ois.readObject();
                victory = (int) ois.readObject();


                is.close();
                os.close();

                socket.close();


            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            //Sieges Bedingung !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            /**
             * 1 = SPiel gewonnen, -1 = Speil verloren, 0 = Spiel la�ft
             */
            if(this.victory==1){
                System.out.println("Sieg");
                gameWon();
                repaint();

            }else if(this.victory==-1){
                System.out.println("verloren");
                gameLost();
                repaint();
            }
            }
    }

    @Override
    public void paintComponent(Graphics canon) {
        super.paintComponent(canon);
        
        canon.drawImage(background, x / 2 - background.getWidth(null)/2, 0, null);
        quit2.setBounds(this.x / 100 * 80, this.y / 100 * 99, Menu.getButtonWidth() / 2, Menu.getButtonHeight() / 2);
        this.add(quit2);
        
        if(this.waitGameStart==true) {
        	canon.setColor(Color.WHITE);
        	canon.drawString("Warte auf zweiten Spieler...", Game.WIDTH * 10 / 25, this.y / 100 * 99);
        }

        if(this.waitGameStart==false) {
        	
            KanonePlayer2.setColorRed();

            //Rad laden
            rad.spinWheel();
            rad.loadWheel(canon, startSpokes);
            this.startSpokes = false;

            //KAnone Laden
            KanonePlayer1.canonLoad(canon);
            KanonePlayer2.canonLoad(canon);

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

            this.missilnumber = KanonePlayer2.shotMissile(canon, missilePlayer2);
            /*if (this.missilnumber != 99) {
                //System.out.println(missilnumber);
                missilePlayer2.remove(this.missilnumber);
            }*/

            //verbleibende Geschosse anzeigen
            canon.setFont(new Font("default", Font.BOLD, this.y / 100 * 3));
            canon.setColor(Color.WHITE);
            canon.drawString("Missles left: " + missileClipPlayer1 , this.x / 100 * 14, this.y / 100 * 95);;

            //Schussst�rke anzeigen
            canon.clearRect(this.x / 100 * 64, this.y / 100 * 85, 100, this.y / 100 * 3);
            canon.setColor(new Color(0, 128, 128));
            canon.fillRect(this.x / 100 * 64, this.y / 100 * 85, loadBar, this.y / 100 * 3);
        }




    }
    private void start() {
        thread = new Thread(this, "Game Loop"); // Creates new thread
        thread.start(); // Starts thread

    }
    private void stop() {
        this.time = false;
        scorePanel = true; //open scorepanel
        repaint();

    }
    private void gameLost() {
    	//Stoppt den Thread
    	stop();
    	this.setBackground(Color.RED);
    	
    	//�ffnet das scorePanel mit Replay oder Quit
    	if (scorePanel) {
        	GameResultLose panel = new GameResultLose("mp");
        	this.add(panel);
    	}
    }

    private void gameWon() {
    	//Stoppt den Thread
    	stop();
    	this.setBackground(Color.GREEN);
    	
    	//�ffnet das scorePanel mit Replay, Quit und Submit
    	if (scorePanel) {
        	GameResultWin panel = new GameResultWin("mp");
    		
        	this.add(panel);
    	}

    }



}
