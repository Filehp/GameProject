package menu;

import game.Game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

/**
 *	Die Klasse fuer das Menue zur Auswahl des Levels
 */

public class LevelMenu extends JComponent {

	//Laedt die Bilder fuer die Buttons
	Icon oneIcon = new ImageIcon("resources/Level1.png");
	Icon twoIcon = new ImageIcon("resources/Level2.png");
	Icon threeIcon = new ImageIcon("resources/Level3.png");
	Icon fourIcon = new ImageIcon("resources/Level4.png");
	Icon backIcon = new ImageIcon("resources/Back.png");
	
	private Image logo2 = new ImageIcon(getClass().getResource("/background2.png")).getImage();

	private Button level1 = new Button(oneIcon);
	private Button level2 = new Button(twoIcon);
	private Button level3 = new Button(threeIcon);
	private Button level4 = new Button(fourIcon);
	private Button back = new Button(backIcon);

	//Constructor
	public LevelMenu() {
		
	//Aktion bei Klick eines Buttons
		level1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Game.changePanel("game", LevelMenu.this);

			}
		});
		level2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Game.changePanel("level2", LevelMenu.this);
			}
		});
		level3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Game.changePanel("level3", LevelMenu.this);
			}
		});
		back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Game.changePanel("difficulty", LevelMenu.this);
			}
		});
	}

	//Stellt die Buttons dar
	@Override
	protected void paintComponent(Graphics g) {
		
		//Stellt das Hintergrundlogo dar
		g.drawImage(logo2, 0, 0, null);

		// Setzt die Position und Groesse der Buttons
		level1.setBounds(Game.WIDTH / 2 - Game.WIDTH / 3, Game.HEIGHT / 2 - Game.HEIGHT / 9, Game.WIDTH / 5, Game.HEIGHT / 8);
		level2.setBounds(Game.WIDTH / 2 + Game.WIDTH / 9, Game.HEIGHT / 2 - Game.HEIGHT / 9, Game.WIDTH / 5, Game.HEIGHT / 8);
		level3.setBounds(Game.WIDTH / 2 - Game.WIDTH / 3, Game.HEIGHT / 2 + Game.HEIGHT / 9, Game.WIDTH / 5, Game.HEIGHT / 8);
		level4.setBounds(Game.WIDTH / 2 + Game.WIDTH / 9, Game.HEIGHT / 2 + Game.HEIGHT / 9, Game.WIDTH / 5, Game.HEIGHT / 8);
		back.setBounds(Menu.getButtonX(), Menu.getButtonY() + Game.HEIGHT / 100 * 75 / 2, 
				Menu.getButtonWidth(), Menu.getButtonHeight());

		// Fuegt die Buttons der GUI hinzu
		add(level1);
		add(level2);
		add(level3);
		add(level4);
		add(back);

	}
}