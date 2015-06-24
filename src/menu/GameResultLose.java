package menu;

import game.Game;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * Klasse für das GameOver Panel, welches erscheint, wenn der Spieler verloren hat.
 */

public class GameResultLose extends JPanel {

	private BufferedImage image;

	// Constructor
	public GameResultLose(String str) {

		// Laedt das GameOver Bild für entweder Single- oder Multiplayer
		try {
			if (str == "sp") {
				image = ImageIO.read(new File(".\\resources\\GameOver.png"));
				
				// Maße des Panels
				setBackground(new Color(190, 190, 190));
				setBounds(0, Game.HEIGHT / 3, Game.WIDTH, image.getHeight() * 2);
			}
			if (str == "mp") {
				image = ImageIO.read(new File(".\\resources\\YouLost.png"));
				
				// Maße des Panels
				setBackground(new Color(190, 190, 190));
				setBounds(0, Game.HEIGHT / 3, Game.WIDTH, image.getHeight());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// Stellt das Game Over Bild dar
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, (Game.WIDTH - image.getWidth(null)) / 2, 0, null);

	}

}
