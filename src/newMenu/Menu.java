package newMenu;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

public class Menu extends JComponent {

	private static final long serialVersionUID = 1L;
	Icon singleIcon = new ImageIcon("resources/Singleplayer.png");
	Icon multiIcon = new ImageIcon("resources/Multiplayer.png");
	Icon scoreIcon = new ImageIcon("resources/Scoreboard.png");
	Icon settingsIcon = new ImageIcon("resources/Settings.png");
	Icon quitIcon = new ImageIcon("resources/Quit.png");

	// Creating buttons
	private Button singleplayer = new Button(singleIcon);
	private Button multiplayer = new Button(multiIcon);
	private Button scoreboard = new Button(scoreIcon);
	private Button settings = new Button(settingsIcon);
	private Button quit = new Button(quitIcon);
	private Image logo = new ImageIcon(getClass()
			.getResource("/background.png")).getImage();

	// Constructor sets up listeners; switches the panel
	public Menu() {
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

	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage(logo, 0, 0, null); // Draws the logo

		// Sets up the buttons position
		singleplayer.setBounds(Game.WIDTH / 2 - 150, Game.HEIGHT / 2 - 100,
				275, 55);
		multiplayer.setBounds(Game.WIDTH / 2 - 150, Game.HEIGHT / 2, 275, 55);
		scoreboard.setBounds(Game.WIDTH / 2 - 150, Game.HEIGHT / 2 + 100, 275,
				55);
		settings.setBounds(Game.WIDTH / 2 - 150, Game.HEIGHT / 2 + 200, 275, 55);
		quit.setBounds(Game.WIDTH / 2 - 150, Game.HEIGHT / 2 + 300, 275, 55);

		// Adds buttons to the gui
		add(singleplayer);
		add(multiplayer);
		add(scoreboard);
		add(settings);
		add(quit);

	}

}
