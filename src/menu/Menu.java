package menu;

import game.Game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

/**
 *  Klasse fuer das Hauptmenue
 */

public class Menu extends JComponent {

	//Laedt die Bilder der Buttons
	Icon singleIcon = new ImageIcon("resources/Singleplayer.png");
	Icon multiIcon = new ImageIcon("resources/Multiplayer.png");
	Icon scoreIcon = new ImageIcon("resources/Scoreboard.png");
	Icon settingsIcon = new ImageIcon("resources/Settings.png");
	Icon quitIcon = new ImageIcon("resources/Quit.png");

	//Laedt das Hintergrundlogo
	//private Image logo = new ImageIcon(getClass().getResource("/background.png")).getImage();



	// Erstellt die Buttons
	private Button singleplayer = new Button(singleIcon);
	private Button multiplayer = new Button(multiIcon);
	private Button scoreboard = new Button(scoreIcon);
	private Button settings = new Button(settingsIcon);
	private Button quit = new Button(quitIcon);

	private Image logo;
	// Constructor
	public Menu() {

		try {
			logo = ImageIO.read(new File("resources/background.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	//Aktion bei Klick auf einen Button
		singleplayer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Game.changePanel("difficulty", Menu.this);
			}
		});
		multiplayer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Game.changePanel("multiplayer", Menu.this);
			}
		});
		scoreboard.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Game.changePanel("scoreboard", Menu.this);
			}
		});
		settings.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Game.changePanel("settings", Menu.this);
			}
		});
		quit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
	}

	//Stellt die Buttons und das Logo dar
	@Override
	protected void paintComponent(Graphics g) {
		
		//Stellt das Hintergrundlogo dar
		g.drawImage(logo, 0, 0, null); // Draws the logo

		// Setzt die Position und Groesse der Buttons		
		singleplayer.setBounds(getButtonX(), getButtonY() - Game.HEIGHT / 100 * 25 / 2, 
				getButtonWidth(), getButtonHeight());
		multiplayer.setBounds(getButtonX(), getButtonY(), 
				getButtonWidth(), getButtonHeight());
		scoreboard.setBounds(getButtonX(), getButtonY() + Game.HEIGHT / 100 * 25 / 2, 
				getButtonWidth(), getButtonHeight());
		settings.setBounds(getButtonX(), getButtonY() + Game.HEIGHT / 100 * 25, 
				getButtonWidth(), getButtonHeight());
		quit.setBounds(getButtonX(), getButtonY() + Game.HEIGHT / 100 * 75 / 2, 
				getButtonWidth(), getButtonHeight());

		//Fuegt die Buttons der GUI hinzu
		add(singleplayer);
		add(multiplayer);
		add(scoreboard);
		add(settings);
		add(quit);
	}
	
	//Holt die Button X Position
	public static int getButtonX() {
		int x = Game.WIDTH / 2 - Game.WIDTH / 100 * 20;
		return x;		
	}
	
	//Holt die Button Y Position
	public static int getButtonY() {
		int y = Game.HEIGHT / 2;
		return y;		
	}
	
	//Holt die Buttonbreite
	public static int getButtonWidth() {
		int width = Game.WIDTH / 100 * 20 * 2;
		return width;		
	}
	
	//Holt die Buttonhoehe
	public static int getButtonHeight() {
		int height = Game.HEIGHT / 13;
		return height;		
	}

}
