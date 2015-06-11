package newMenu;

import game.testFrame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameResult extends JPanel{
	
	Icon backIcon = new ImageIcon("resources/Back.png");
	private Button back = new Button(backIcon);
	
	private JPanel result = new JPanel();
	private JButton test = new JButton();
	
	
	
	public GameResult() {
		
		//setForeground(Color.BLACK);
		setBackground(new Color(190, 190, 190));
		setBounds(Game.WIDTH / 4, Game.HEIGHT / 3, Game.WIDTH / 2, Game.WIDTH / 2);
		setFocusable(true);
		repaint();
		
	
		
	}

		
		

	
	
	

	


}
