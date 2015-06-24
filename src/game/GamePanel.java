package game;

import javax.imageio.ImageIO;
import javax.swing.*;

import preferences.ScoreDB;
import entity.Canon;
import entity.Missile;
import entity.Wheel;
import menu.Button;
import menu.GameResultLose;
import menu.GameResultWin;
import menu.Menu;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *  Klasse, die das Spiel beinhaltet
 */
public class GamePanel extends JPanel implements Runnable {

    /**
     * Aufloesungsvariablen
     */
    static int x;
    static int y;


    /**
     * Variablen fuerr die Zeit und den Timer
     */
    boolean time = true;
    int timer;
    int minuten;
    int sekunden;
    long starttime = System.currentTimeMillis();
    int currentTimeOutput;
    private long currentTime;

    /**
     * Variablen fuer Kanonen und Missile
     */
    private Canon Kanone;
    String direction;
    boolean shotMissile = false;
    double missilespeed = 0;
    int loadBar = 0;
    int missilenumber = 99;
    int missileClipPlayer;
    public ArrayList<Missile> missile = new ArrayList<>();

    /**
     * Rad Variablen
     */
    private Wheel rad;
    boolean startSpokes = true;

    /**
     * Keybindings Variablen
     */
    InputMap im;
    ActionMap am;

    /**
     * Thread Variabel
     */
    private Thread thread;

    /**
     * Variablen für das scorePanel
     */
    private boolean scorePanel = false;
    
    /**
     * Laedt die Bilder
     */
    Icon quitIcon = new ImageIcon("resources/Quit.png");
    Icon quit1Icon = new ImageIcon("resources/Quit1.png");
    Icon replayIcon = new ImageIcon("resources/Replay.png");
    Icon replay2Icon = new ImageIcon("resources/Replay1.png");
    Icon nextLevelIcon = new ImageIcon("resources/NextLevel.png");
    
    /**
     * Erzeugt die Buttons und Labels
     */
    private Button quit = new Button(quitIcon);
    private Button quit2 = new Button(quit1Icon);
    private Button replay = new Button(replayIcon);
    private Button replay2 = new Button(replay2Icon);
    private Button nextLevel = new Button (nextLevelIcon);
    private JLabel yourTime = new JLabel();
    private JLabel yourMissles = new JLabel();
    
    /**
     * Enum fuer den Schwierigkeitsgrad
     */
    public enum Diff {
    	EASY, MEDIUM, HARD
    }
    /**
     * Textfeld und Submit zum Eintragen in das Scoreboard
     */
    private JTextField nameField = new JTextField("What's your name?");
    Icon submitIcon = new ImageIcon("resources/Submit.png");
    private Button submitScore = new Button(submitIcon);
    
	/**
	 * Hintergrundbild
	 */
    private Image background;

    /**
     * Constructor fuer das Level
     */
    public GamePanel(int x, int y, int missileClip, int time, int startSpokes, int speedWheel,Diff diff) {
        this.setFocusable(true);
        this.x = x;
        this.y = y;
        //Laedt das Hintergrundbild
		try {
			background = ImageIO.read(new File("resources/Hintergrund1.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

        this.timer = time;
        this.missileClipPlayer = missileClip;

        // Laedt Kanone und Rad
        Kanone = new Canon(x, y, missileClip);
        rad = new Wheel(x, y, startSpokes, speedWheel);

        this.im = getInputMap(WHEN_IN_FOCUSED_WINDOW);
        this.am = getActionMap();


        /**
         * Keybindings
         */
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, false), "pressed");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, true), "released");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, false), "left");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, false), "right");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, true), "moveReleased");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, true), "moveReleased");

        /**
         * Keybindings Actionevents
         */
        am.put("left", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /**
                 * Setzt direction auf left um die Kanone nach links zu bewegen
                 */
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
                /**
                 * Setzt direction auf right um die Kanone nach rechts zu bewegen
                 */
                direction = "right";
            }
        });

        /**
         * Halten der Leertaste laedt die Geschwindigkeit
         */
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
            }
        });

        /**
         * Loslassen der Leertaste schiesst die Missile ab
         */
        am.put("released", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                direction = null;
                if (Kanone.getMissileCounter() > 0) {

                    // erzeugt eine neue missile
                    missile.add(new Missile(x, Kanone.getAbschussPositionX(), Kanone.getAbschussPositionY(), missilespeed));
                    shotMissile = true;

                    Kanone.shotedmissile();// entfernt missile aus dem Magazin der kanone

                    missilespeed = 0;       // setzt die Geschwindigkeit auf Null
                    missileClipPlayer--;    // entfernt missile aus dem Magazin des LEvels
                }
            }
        });

        // Buttons in ScorePanel 
        // Startet das jeweilige Level neu
        replay.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (diff.equals(Diff.EASY)) {
				Game.changePanel("gameE", GamePanel.this);
				}
				if (diff.equals(Diff.MEDIUM)) {
				Game.changePanel("gameM", GamePanel.this);
				}
				if (diff.equals(Diff.HARD)) {
				Game.changePanel("gameH", GamePanel.this);
				}
			}
		});
        // Button, um waehrend des Spiel neu zu starten
        replay2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (diff.equals(Diff.EASY)) {
				Game.changePanel("gameE", GamePanel.this);
				}
				if (diff.equals(Diff.MEDIUM)) {
				Game.changePanel("gameM", GamePanel.this);
				}
				if (diff.equals(Diff.HARD)) {
				Game.changePanel("gameH", GamePanel.this);
				}
			}
		});
        // Naechstes Level
        nextLevel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (diff.equals(Diff.EASY)) {
					Game.changePanel("gameM", GamePanel.this);
				}
				if (diff.equals(Diff.MEDIUM)) {
					Game.changePanel("gameH", GamePanel.this);
				}
				if (diff.equals(Diff.HARD)) {
					Game.changePanel("gameS", GamePanel.this);
				}

			}
		});
        // Beendet das Spiel
        quit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Game.changePanel("menu", GamePanel.this);
			}
		});
        // Beendet das Spiel
        quit2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Game.changePanel("menu", GamePanel.this);
			}
		});
        // Eingabefeld fuer den Namen zur Eintragung in Scoreboard
        nameField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				nameField.setText(""); 
			}
		});
        // Schwierigkeit, Name, Zeit und Datum ins Scoreboard eintragen
		submitScore.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				// Wenn das Feld nicht ausgefuellt wurde oder leer ist, Fehler
				if (nameField.getText().trim().equals("What's your name?") || nameField.getText().trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please enter a name and try again.", "Error", JOptionPane.ERROR_MESSAGE);
				} else {
				String name = nameField.getText(); 									// Holt den eingetragenen Namen
				DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); 		// Erstellt ein Datumsformat
				Date date = new Date();												// Erzeugt das Datum
				ScoreDB db = new ScoreDB(diff); 									// Erzeugt die Datenbank 
				db.insertScore(diff, name, currentTime, dateFormat.format(date));	// Fuegt die Daten ein
				db.close();															// Schließt die Datenbank
				Game.changePanel("scoreboard", GamePanel.this); 					// Wechselt zum Scoreboard
			}}
		});

        // Startet den Thread
        start();

    }


	@Override
    public void run() {
        while (time) {
            repaint();
            try {
                Thread.sleep(20);

                //Zeit wird berechnet und geprueft
                currentTime = System.currentTimeMillis() - starttime;
                currentTimeOutput = (int) (timer - currentTime);
                minuten = (int) currentTimeOutput / 1000 / 60;
                sekunden = (int) currentTimeOutput / 1000 % 60;

                if (currentTime >= timer) {
                    gameLost(); // Methode, wenn verloren
                    System.out.println("Timeout");
                    repaint();
                }
                
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

	// Stellt die Objekte dar
    @Override
    public void paintComponent(Graphics canon) {
        super.paintComponent(canon);
        
        // Hintergrundbild
        canon.drawImage(background, x / 2 - background.getWidth(null)/2, 0, null);
        
        // Buttons
        replay2.setBounds(this.x / 100 * 80, this.y / 100 * 94, Menu.getButtonWidth() / 2, Menu.getButtonHeight() / 2);
        quit2.setBounds(this.x / 100 * 80, this.y / 100 * 99, Menu.getButtonWidth() / 2, Menu.getButtonHeight() / 2);
        this.add(quit2);
        this.add(replay2);

        //Rad laden
        rad.loadWheel(canon, startSpokes);
        this.startSpokes = false;

        //Rad bewegen
        rad.spinWheel();

        //Kanone Laden
        Kanone.canonLoad(canon);

        //Kanone bewegen
        if (this.direction != null) {
            Kanone.moveCanon(this.direction);
        }

        //Geschoss abfeuern
        this.missilenumber = Kanone.shotMissile(canon, missile);
        if (this.missilenumber != 99) {
            missile.remove(this.missilenumber);
        }

        //Kollision abfangen
        if (missile.size() > 0) {
            for (int i = 0; i < missile.size(); i++) {
                
            	//Auf Kollision mit rad pruefen
                boolean Kollirad = missile.get(i).collidesWithWheel(rad);

                //Auf kollision mit Speiche pruefen
                boolean Kollispeiche = missile.get(i).coollidesWithSpokes(rad.getSpokesList());

                //Kollision mit Rad
                if (Kollirad && Kollispeiche == false) {
                    //Was soll passieren wenn das Geschoss auf das Rad trifft?

                    //Speiche wird an der Stelle hinzugefuegt
                    rad.addSpokes(missile.get(i).getX());
                    //Geschoss wird entfernt
                    missile.remove(i);


                } else {
                    //Kollision mit Speiche
                    if (Kollispeiche) {
                        //Was soll passieren wenn das Geschoss auf eine Speiche trifft?
                        missile.remove(i); // Geschoss wird entfernt
                        gameLost(); // Verloren
                        
                    }
                }

            }
            // Siegesbedingung 
            if (time == true && this.missileClipPlayer == 0 && missile.size() == 0) {
                System.out.println("Du hast gewonnen");
                gameWon(); // Gewonnen

            }
        }

        //Verbleibende Zeit & Geschosse anzeigen
        canon.setFont(new Font("default", Font.BOLD, this.y / 100 * 3));
        canon.setColor(Color.WHITE);
        if (sekunden >= 10) {
            canon.drawString("0" + minuten + ":" + sekunden + " (" + currentTime + ")", this.x / 100 * 14, this.y / 100 * 88);
        } else {
            canon.drawString("0" + minuten + ":0" + sekunden + " (" + currentTime + ")", this.x / 100 * 14, this.y / 100 * 88);
        }
        canon.drawString("Missles left: " + missileClipPlayer , this.x / 100 * 14, this.y / 100 * 95);
        
        //Schussstaerke anzeigen
        canon.clearRect(this.x / 100 * 64, this.y / 100 * 85, 100, this.y / 100 * 3);
        canon.setColor(new Color(0, 128, 128));
        canon.fillRect(this.x / 100 * 64, this.y / 100 * 85, loadBar, this.y / 100 * 3);

    }
    
    // Starten
    private void start() {
		thread = new Thread(this, "Game Loop"); // Erzeugt neuen Thread
		thread.start(); // Startet den Thread
		
	}
    
    // Stoppen
    private void stop() {
    	time = false; 
    	scorePanel = true; // Setzt scorepanel auf true
    	repaint();

    }
    
    // Methode wenn der Spieler verloren hat
    private void gameLost() {
    	//Stoppt den Thread
    	stop();
    	this.setBackground(Color.RED);
    	
    	//Oeffnet das scorePanel mit Infos und Buttons
    	if (scorePanel) {
        	GameResultLose panel = new GameResultLose("sp");
        	quit.setBounds(Game.WIDTH / 10 * 5, Game.HEIGHT / 10 * 7, Menu.getButtonWidth(), Menu.getButtonHeight());
        	replay.setBounds(Game.WIDTH / 10 , Game.HEIGHT / 10 * 7, Menu.getButtonWidth(), Menu.getButtonHeight());
    		
			yourTime.setBounds(Game.WIDTH / 10, Game.HEIGHT / 100 * 65, Menu.getButtonWidth() * 2, Menu.getButtonHeight());
    		yourTime.setText("You failed after " + currentTime / 1000 + " seconds ("+ currentTime + " ms)");
			yourMissles.setBounds(Game.WIDTH / 10, Game.HEIGHT / 10 * 6, Menu.getButtonWidth(), Menu.getButtonHeight());
			yourMissles.setText("You have " + missileClipPlayer + " missles left.");

			// Fuegt es der GUI hinzu
    		add(yourTime);
    		add(yourMissles);
        	
        	this.add(quit);
        	this.add(replay);
    		
        	this.add(panel);
    	}
    	
    	
    }
    
    // Methode wenn der Spieler gewonnen hat
    private void gameWon() {
    	//Stoppt den Thread
    	stop();
    	this.setBackground(Color.GREEN);
    	
    	//Öffnet das scorePanel mit Infos und Buttons
    	if (scorePanel) {
        	GameResultWin panel = new GameResultWin("sp");
        	submitScore.setBounds(Game.WIDTH / 10 * 5, Game.HEIGHT / 10 * 7, Menu.getButtonWidth(), Menu.getButtonHeight());
        	nextLevel.setBounds(Game.WIDTH / 10 , Game.HEIGHT / 10 * 7, Menu.getButtonWidth(), Menu.getButtonHeight());
    		
			yourTime.setBounds(Game.WIDTH / 10, Game.HEIGHT / 100 * 63, Menu.getButtonWidth(), Menu.getButtonHeight());
    		yourTime.setText("You needed " + currentTime / 1000 + " seconds" + " (" + currentTime + " ms).");

			nameField.setBounds(Game.WIDTH / 10 * 5, Game.HEIGHT / 100 * 65,  Menu.getButtonWidth(), 20);
			
			// Fuegt Objekte der GUI hinzu
			add(submitScore);
			add(nameField);
    		add(yourTime);
        	
        	this.add(quit);
        	this.add(nextLevel);
    		
        	this.add(panel);
    	}
    	
    	
    	
    }
    


}
