package menu;

import game.Game;
import game.GamePanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Klasse für das GameOver Panel
 */

public class GameResultLose extends JPanel{
	
	private BufferedImage image;
	private Graphics2D g;
	
	//Constructor
	public GameResultLose(String str) {
		
		//Laedt das GameOver Bild
			try {
				if(str == "sp") {
					image = ImageIO.read(new File (".\\resources\\GameOver.png"));
					//Maße des Panels
					setBackground(new Color(190, 190, 190));
					setBounds(0, Game.HEIGHT / 3, Game.WIDTH, image.getHeight() * 2);
				}
				if (str == "mp") {
					image = ImageIO.read(new File (".\\resources\\YouLost.png"));
					//Maße des Panels
					setBackground(new Color(190, 190, 190));
					setBounds(0, Game.HEIGHT / 3, Game.WIDTH, image.getHeight());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

	}
	//Stellt das Game Over Bild dar
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, (Game.WIDTH - image.getWidth(null)) / 2, 0, null);
		
	
		
	}


}
