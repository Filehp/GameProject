package game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import menu.Difficulty;
import menu.LevelMenu;
import menu.Menu;
import menu.Scoreboard;
import menu.Settings;
import multiplayer.MultiplayerLevel;
import multiplayer.MultiplayerMenu;

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
	
	public static final int WIDTH = Game.getWindowWidth();
	public static final int HEIGHT = Game.getWindowHeight();
	public static final int HEIGHT2 = HEIGHT + 16;
	public static final int SCALE = 1;
	public static final int centerX = WIDTH - (WIDTH / 4);
	public static final int centerY = 0;
	
	public Game() {
			
		panel.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT2 * SCALE)); 
		panel.setBackground(new Color(190, 190, 190)); 
		panel.setLayout(new BorderLayout());
		changePanel("menu");
		
		frame.add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("TITLE");
		frame.setResizable(false);
		frame.setLocation(centerX, centerY);
		frame.pack();
		frame.setVisible(true);
		
		
		/**
		 * Starts the music
		 */
		
		//Music music = new Music();
		//music.playMusic();
	}


	public static void changePanel(String option) {
		switch (option) {
		case "menu":
			panel.add(menuPanel, BorderLayout.CENTER); break;
			
		case "difficulty":
			panel.add(new Difficulty(), BorderLayout.CENTER); break;
			
		case "levelmenu":
			panel.add(new LevelMenu(), BorderLayout.CENTER); break;

		case "multiplayer":
			panel.add(new MultiplayerMenu(), BorderLayout.CENTER);break;

		case "multiplayerLevel":
			MultiplayerLevel multiLevel = new MultiplayerLevel(Game.getWindowWidth(),Game.getWindowHeight(),10,270000,4,1);
			multiLevel.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT2 * SCALE));
			multiLevel.setBackground(new Color(190, 190, 190));
			multiLevel.setLayout(new BorderLayout());
			frame.add(multiLevel);
			new Thread(multiLevel).start();
			break;
		
		case "scoreboard":
			panel.add(new Scoreboard(), BorderLayout.CENTER); break;
			
		case "settings":
			panel.add(new Settings(), BorderLayout.CENTER); break;
			
		case "testFrame":
			panel.add(new GamePanel(Game.getWindowWidth(),Game.getWindowHeight(),10,5000,4,1), BorderLayout.CENTER); break;

		
		}
		
	}
	
	public static void changePanel(String option, JComponent comp){
		panel.remove(comp); 
		panel.revalidate(); 
		changePanel(option); 
		panel.repaint(); 
	}
	
	public static void changePanelToGame (String option, JComponent comp) {
		frame.setVisible(false);
		frame.remove(panel);
		changePanel(option);
		frame.setVisible(true);	


		
	}
	
	public static int getWindowWidth() {
		
		Dimension screen;
		int x;
		
		screen = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		x = (int) (screen.getWidth() / 100 * 40  );
		
		return x;
	}
	
	public static int getWindowHeight() {
		
		Dimension screen;
		int y;
		
		screen = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		y = (int) (screen.getHeight() / 100 * 80 );
		
		return y;
	}

}