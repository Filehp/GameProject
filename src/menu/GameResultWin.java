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
 * Klasse für das GameWon Panel
 */

public class GameResultWin extends JPanel{
	
	private BufferedImage image;
	private Graphics2D g; 
	private JLabel yourTime = new JLabel();

	//Constructor
	public GameResultWin(String str) {
		
		//Laedt das GameWon Bild
			try {
				if(str == "sp") {
					image = ImageIO.read(new File (".\\resources\\Success.png"));
				}
				if (str == "mp") {
					image = ImageIO.read(new File (".\\resources\\YouWon.png"));
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			//Maße des Panels
			setBackground(new Color(190, 190, 190));
			setBounds(0, Game.HEIGHT / 3, Game.WIDTH, image.getHeight() * 2);
		

	}
	//Stellt das GameWon Bild dar	
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, (Game.WIDTH - image.getWidth(null)) / 2, 0, null);

		
		
	   

		
	
		
	}

}
