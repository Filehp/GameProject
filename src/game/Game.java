package game;

import game.GamePanel.Diff;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import preferences.Music;
import menu.Difficulty;
import menu.LevelMenu;
import menu.Menu;
import menu.MultiplayerMenu;
import menu.Scoreboard;
import menu.Settings;
import multiplayer.MultiplayerClient;


/**
 * Beinhaltet die Main
 * Erstellt das Grundgeruest mit Frame und Panel
 */

//Main Methode
public class Game {

	public static void main(String[] args) {
		 {
		SwingUtilities.invokeLater(new Runnable()
			{
			@Override
			public void run()
			{
				new Game();
			      }
			    });
		 }
		 }
	
	private static JFrame frame = new JFrame();
	public static JPanel panel = new JPanel();
	public static Menu menuPanel = new Menu();	
	
	// Maﬂe
	public static final int WIDTH = Game.getWindowWidth();
	public static final int HEIGHT = Game.getWindowHeight();
	public static final int HEIGHT2 = HEIGHT + 16;
	public static final int SCALE = 1;
	public static final int centerX = WIDTH - (WIDTH / 4);
	public static final int centerY = 0;
	
	private Music musicInstance = Music.getInstance();
	
	//Constructor
	public Game() {
			
		//Erstellt das Panel
		panel.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT2 * SCALE)); 
		panel.setBackground(new Color(190, 190, 190)); 
		panel.setLayout(new BorderLayout());
		changePanel("menu");
		
		//Erstellt den Frame
		frame.add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Peak!");
		frame.setResizable(false);
		frame.setLocation(centerX, centerY);
		frame.pack();
		frame.setVisible(true);
		
		//Startet die Musik
		musicInstance.getInstance();
		musicInstance.playMusic();
	}

	//Aendert in das jeweilige Panel bzw. Menue/Spiel
	public static void changePanel(String option) {
		switch (option) {
		case "menu":
			panel.add(menuPanel, BorderLayout.CENTER); break;
			
		case "difficulty":
			panel.add(new Difficulty(), BorderLayout.CENTER); break;
			
		case "levelmenu":
			panel.add(new LevelMenu("gameE"), BorderLayout.CENTER); break;
		
		case "levelmenu1":
			panel.add(new LevelMenu("gameM"), BorderLayout.CENTER); break;
		
		case "levelmenu2":
			panel.add(new LevelMenu("gameH"), BorderLayout.CENTER); break;

		case "multiplayer":
			panel.add(new MultiplayerMenu(), BorderLayout.CENTER);break;
		
		case "scoreboard":
			panel.add(new Scoreboard(), BorderLayout.CENTER); break;
			
		case "settings":
			panel.add(new Settings(), BorderLayout.CENTER); break;
		
		/**
		 * Wechselt zum jeweiligen Schwierigkeitsgrad 
		 * Zeit, Anzahl der Geschosse & Speichen und Geschwindigkeit des Levels wird mitgegeben	
		 */
		case "gameE": //Wechselt zum Spiel mit der Schwierigkeit EASY
			panel.add(new GamePanel(Game.getWindowWidth(),Game.getWindowHeight(),15,30000,6,1, Diff.EASY), BorderLayout.CENTER); break;
			
		case "gameM": //Wechselt zum Spiel mit der Schwierigkeit MEDIUM
			panel.add(new GamePanel(Game.getWindowWidth(),Game.getWindowHeight(),15,26000,8,2, Diff.MEDIUM), BorderLayout.CENTER); break;
			
		case "gameH": //Wechselt zum Spiel mit der Schwierigkeit HARD
			panel.add(new GamePanel(Game.getWindowWidth(),Game.getWindowHeight(),10,16000,8,4, Diff.HARD), BorderLayout.CENTER); break;
		
		case "gameS": // Wechselt zum Extra Level
			panel.add(new GamePanel(Game.getWindowWidth(),Game.getWindowHeight(),5,16000,8,6, Diff.HARD), BorderLayout.CENTER); break;
		
		case "multiplayerClientHost": //Multiplayer Host
			panel.add(new MultiplayerClient(Game.getWindowWidth(),Game.getWindowHeight(),10,1, "localhost"), BorderLayout.CENTER); break;
			
		case "multiplayerClientJoin": //Multiplayer Join
			panel.add(new MultiplayerClient(Game.getWindowWidth(),Game.getWindowHeight(),10,2, MultiplayerMenu.getIp()), BorderLayout.CENTER);

		}
		
	}
	
	//Methode zum Menuewechsel
	public static void changePanel(String option, JComponent comp){
		panel.remove(comp); 
		panel.revalidate(); 
		changePanel(option); 
		panel.repaint(); 
	}
	
	//Methode zum Wechsel zum Spiel
	public static void changePanelToGame (String option, JComponent comp) {
		frame.setVisible(false);
		frame.remove(panel);
		changePanel(option);
		frame.setVisible(true);


		
	}
	
	//Berechnet die Fensterbreite
	public static int getWindowWidth() {
		
		Dimension screen;
		int x;
		
		screen = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		x = (int) (screen.getWidth() / 100 * 40  );
		
		return x;
	}
	
	//Berechnet die Fensterhoehe
	public static int getWindowHeight() {
		
		Dimension screen;
		int y;
		
		screen = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		y = (int) (screen.getHeight() / 100 * 80 );
		
		return y;
	}

}
