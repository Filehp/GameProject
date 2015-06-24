package menu;

import game.Game;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * Klasse f�r das GameWon Panel, welches erscheint, wenn der Spieler gewonnen hat.
 */

public class GameResultWin extends JPanel {

	private BufferedImage image;

	// Constructor
	public GameResultWin(String str) {

		// Laedt das GameWon Bild f�r entweder Single- oder Multiplayer
		try {
			if (str == "sp") {
				image = ImageIO.read(new File(".\\resources\\Success.png"));

				// Ma�e des Panels
				setBackground(new Color(190, 190, 190));
				setBounds(0, Game.HEIGHT / 3, Game.WIDTH, image.getHeight() * 2);
			}
			if (str == "mp") {
				image = ImageIO.read(new File(".\\resources\\YouWon.png"));

				// Ma�e des Panels
				setBackground(new Color(190, 190, 190));
				setBounds(0, Game.HEIGHT / 3, Game.WIDTH, image.getHeight());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// Stellt das GameWon Bild dar
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, (Game.WIDTH - image.getWidth(null)) / 2, 0, null);

	}

}
