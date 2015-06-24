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
 *	Die Klasse fuer das Menue zur Auswahl des Levels
 */

public class LevelMenu extends JComponent {

	//Laedt die Bilder fuer die Buttons
	Icon oneIcon = new ImageIcon("resources/Level1.png");
	Image twoIcon;
	Image threeIcon;
	Image fourIcon;
	Icon backIcon = new ImageIcon("resources/Back.png");
	
	//private Image logo2 = new ImageIcon(getClass().getResource("/background2.png")).getImage();

	private Button level1 = new Button(oneIcon);
	//private Button level2 = new Button(twoIcon);
	//private Button level3 = new Button(threeIcon);
	//private Button level4 = new Button(fourIcon);
	private Button back = new Button(backIcon);
	private Image logo2;

	//Constructor
	public LevelMenu(String difficulty) {
		
		try {
			twoIcon = ImageIO.read(new File("resources/ComingSoon.png"));
			threeIcon = ImageIO.read(new File("resources/ComingSoon.png"));
			fourIcon = ImageIO.read(new File("resources/ComingSoon.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		if (difficulty == "gameE") {
			try {
				logo2 = ImageIO.read(new File("resources/background2easy.png"));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	//Aktion bei Klick eines Buttons
		level1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Game.changePanel("gameE", LevelMenu.this);

			}
		});
		/*level2.addActionListener(new ActionListener() {

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
		});*/
		back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Game.changePanel("difficulty", LevelMenu.this);
			}
		});
		}
		
		if (difficulty == "gameM") {
			try {
				logo2 = ImageIO.read(new File("resources/background2med.png"));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	//Aktion bei Klick eines Buttons
		level1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Game.changePanel("gameM", LevelMenu.this);

			}
		});
		/*level2.addActionListener(new ActionListener() {

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
		});*/
		back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Game.changePanel("difficulty", LevelMenu.this);
			}
		});
		}
		
		if (difficulty == "gameH") {
			
			try {
				logo2 = ImageIO.read(new File("resources/background2hard.png"));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
	//Aktion bei Klick eines Buttons
		level1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Game.changePanel("gameH", LevelMenu.this);

			}
		});
		/*level2.addActionListener(new ActionListener() {

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
		});*/
		back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Game.changePanel("difficulty", LevelMenu.this);
			}
		});
		}
	}

	//Stellt die Buttons dar
	@Override
	protected void paintComponent(Graphics g) {
		
		//Stellt das Hintergrundlogo dar
		g.drawImage(logo2, Game.getWindowWidth() / 2 - logo2.getWidth(null) / 2, 0, null);

		// Setzt die Position und Groesse der Buttons
		level1.setBounds(Game.WIDTH / 4 - oneIcon.getIconWidth() / 2, Game.HEIGHT / 2 - Game.HEIGHT / 9, oneIcon.getIconWidth(), oneIcon.getIconHeight());
		g.drawImage(twoIcon, Game.WIDTH / 2 + (Game.WIDTH / 4 - oneIcon.getIconWidth() / 2), Game.HEIGHT / 2 - Game.HEIGHT / 9, null);
		g.drawImage(threeIcon, Game.WIDTH / 4 - oneIcon.getIconWidth() / 2, Game.HEIGHT / 2 + Game.HEIGHT / 9, null);
		g.drawImage(fourIcon,Game.WIDTH / 2 + (Game.WIDTH / 4 - oneIcon.getIconWidth() / 2), Game.HEIGHT / 2 + Game.HEIGHT / 9, null);
		back.setBounds(Menu.getButtonX(), Menu.getButtonY() + Game.HEIGHT / 100 * 75 / 2, 
				Menu.getButtonWidth(), Menu.getButtonHeight());

		// Fuegt die Buttons der GUI hinzu 
		add(level1);

		add(back);

	}
}