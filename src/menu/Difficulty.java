package menu;

import game.Game;

import java.awt.Font;
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
 * Klasse fuer das Schwierigkeitsmenue
 */

public class Difficulty extends JComponent {

	// Laedt die Bilder fuer die Buttons
	Icon easyIcon = new ImageIcon("resources/Easy.png");
	Icon mediumIcon = new ImageIcon("resources/Medium.png");
	Icon hardIcon = new ImageIcon("resources/Hard.png");
	Icon backIcon = new ImageIcon("resources/Back.png");

	// Erzeugt die Menübuttons
	private Button easy = new Button(easyIcon);
	private Button medium = new Button(mediumIcon);
	private Button hard = new Button(hardIcon);
	private Button back = new Button(backIcon);

	// Erzeugt das Hintergrundbild
	private Image logo2;

	// Constructor
	public Difficulty() {

		// Lädt das Hintergrundbild
		try {
			logo2 = ImageIO.read(new File("resources/background2.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		// Aktion bei Klick auf den jeweiligen Button
		easy.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Game.changePanel("levelmenu", Difficulty.this);
			}
		});
		medium.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Game.changePanel("levelmenu1", Difficulty.this);
			}
		});
		hard.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Game.changePanel("levelmenu2", Difficulty.this);
			}
		});
		back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Game.changePanel("menu", Difficulty.this);
			}
		});
	}

	// Stellt die Objekte dar
	@Override
	protected void paintComponent(Graphics g) {

		// Stellt das Hintergrundlogo dar
		g.drawImage(logo2,
				Game.getWindowWidth() / 2 - logo2.getWidth(null) / 2, 0, null);
		g.setFont(new Font("default", Font.BOLD, Game.HEIGHT / 75));
		g.drawString("created by Christopher Brost and Filip Meyer", Game.WIDTH / 99 , Game.HEIGHT  );

		// Setzt die Position und Groesse der Buttons
		easy.setBounds(Menu.getButtonX(), Menu.getButtonY() - Game.HEIGHT / 100
				* 25 / 2, Menu.getButtonWidth(), Menu.getButtonHeight());
		medium.setBounds(Menu.getButtonX(), Menu.getButtonY(),
				Menu.getButtonWidth(), Menu.getButtonHeight());
		hard.setBounds(Menu.getButtonX(), Menu.getButtonY() + Game.HEIGHT / 100
				* 25 / 2, Menu.getButtonWidth(), Menu.getButtonHeight());
		back.setBounds(Menu.getButtonX(), Menu.getButtonY() + Game.HEIGHT / 100
				* 75 / 2, Menu.getButtonWidth(), Menu.getButtonHeight());

		// Fuegt die Buttons der GUI hinzu
		add(easy);
		add(medium);
		add(hard);
		add(back);

	}

}
