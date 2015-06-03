package newMenu;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

public class LevelMenu extends JComponent {

	private static final long serialVersionUID = 1L;

	Icon oneIcon = new ImageIcon("resources/Level1.png");
	Icon twoIcon = new ImageIcon("resources/Level2.png");
	Icon threeIcon = new ImageIcon("resources/Level3.png");
	Icon fourIcon = new ImageIcon("resources/Level4.png");
	Icon backIcon = new ImageIcon("resources/Back.png");

	private Button level1 = new Button(oneIcon);
	private Button level2 = new Button(twoIcon);
	private Button level3 = new Button(threeIcon);
	private Button level4 = new Button(fourIcon);
	private Button back = new Button(backIcon);

	public LevelMenu() {
		level1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Game.changePanelToGame("testFrame", LevelMenu.this);

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

	@Override
	protected void paintComponent(Graphics g) {
		// g.drawImage(logo, 320, 10, null); // Draws the logo

		// Sets up the buttons position
		level1.setBounds(Game.WIDTH / 2 - 200, Game.HEIGHT / 2 - 100, 150, 100);
		level2.setBounds(Game.WIDTH / 2 + 50, Game.HEIGHT / 2 - 100, 150, 100);
		level3.setBounds(Game.WIDTH / 2 - 200, Game.HEIGHT / 2 + 100, 150, 100);
		level4.setBounds(Game.WIDTH / 2 + 50, Game.HEIGHT / 2 + 100, 150, 100);
		back.setBounds(Game.WIDTH / 2 - 150, Game.HEIGHT / 2 + 300, 300, 55);

		// Adds buttons to the gui
		add(level1);
		add(level2);
		add(level3);
		add(level4);
		add(back);

	}
}
