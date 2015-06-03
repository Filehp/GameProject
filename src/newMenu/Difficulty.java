package newMenu;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

public class Difficulty extends JComponent {

	private static final long serialVersionUID = 1L;

	Icon easyIcon = new ImageIcon("resources/Easy.png");
	Icon mediumIcon = new ImageIcon("resources/Medium.png");
	Icon hardIcon = new ImageIcon("resources/Hard.png");
	Icon backIcon = new ImageIcon("resources/Back.png");

	private Button easy = new Button(easyIcon);
	private Button medium = new Button(mediumIcon);
	private Button hard = new Button(hardIcon);
	private Button back = new Button(backIcon);

	public Difficulty() {

		easy.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Game.changePanel("levelmenu", Difficulty.this);
			}
		});
		medium.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Game.changePanel("level", Difficulty.this);
			}
		});
		hard.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Game.changePanel("level", Difficulty.this);
			}
		});
		back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Game.changePanel("menu", Difficulty.this);
			}
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		// g.drawImage(logo, 320, 10, null); // Draws the logo

		// Sets up the buttons position
		easy.setBounds(Game.WIDTH / 2 - 150, Game.HEIGHT / 2 - 100, 275, 55);
		medium.setBounds(Game.WIDTH / 2 - 150, Game.HEIGHT / 2, 275, 55);
		hard.setBounds(Game.WIDTH / 2 - 150, Game.HEIGHT / 2 + 100, 275, 55);
		back.setBounds(Game.WIDTH / 2 - 150, Game.HEIGHT / 2 + 300, 275, 55);

		// Adds buttons to the gui
		add(easy);
		add(medium);
		add(hard);
		add(back);

	}

}
