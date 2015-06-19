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
 *	Klasse fuer das Schwierigkeitsmenue
 */

public class Difficulty extends JComponent {

	//Laedt die Bilder fuer die Buttons
	Icon easyIcon = new ImageIcon("resources/Easy.png");
	Icon mediumIcon = new ImageIcon("resources/Medium.png");
	Icon hardIcon = new ImageIcon("resources/Hard.png");
	Icon backIcon = new ImageIcon("resources/Back.png");
	
	//private Image logo2 = new ImageIcon(getClass().getResource("/background2.png")).getImage();

	private Button easy = new Button(easyIcon);
	private Button medium = new Button(mediumIcon);
	private Button hard = new Button(hardIcon);
	private Button back = new Button(backIcon);
	private Image logo2;

	//Constructor
	public Difficulty() {
		
		try {
			logo2 = ImageIO.read(new File("resources/background2.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	//Aktion bei Klick auf Button
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

	//Stellt die Buttons dar
	@Override
	protected void paintComponent(Graphics g) {
		
		//Stellt das Hintergrundlogo dar
		g.drawImage(logo2, 0, 0, null); 

		//Setzt die Position und Groesse der Buttons
		easy.setBounds(Menu.getButtonX(), Menu.getButtonY() - Game.HEIGHT / 100 * 25 / 2, Menu.getButtonWidth(), Menu.getButtonHeight());
		medium.setBounds(Menu.getButtonX(), Menu.getButtonY(), Menu.getButtonWidth(), Menu.getButtonHeight());
		hard.setBounds(Menu.getButtonX(), Menu.getButtonY() + Game.HEIGHT / 100 * 25 / 2, Menu.getButtonWidth(), Menu.getButtonHeight());
		back.setBounds(Menu.getButtonX(), Menu.getButtonY() + Game.HEIGHT / 100 * 75 / 2, Menu.getButtonWidth(), Menu.getButtonHeight());

		//Fuegt die Buttons der GUI hinzu
		add(easy);
		add(medium);
		add(hard);
		add(back);

	}

}
