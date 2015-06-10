package newMenu;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameResult extends JComponent {
	
	Icon backIcon = new ImageIcon("resources/Back.png");
	private Button back = new Button(backIcon);
	
	private JPanel result = new JPanel();
	
	
	
	public GameResult() {
		result.setBackground(Color.BLACK);
		result.setBounds(Game.WIDTH / 2, Game.HEIGHT / 2, 100, 100);
		result.setOpaque(true);
		
		add(result);
		Game.panel.repaint();
		
		
		/*back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Game.changePanel("menu", GameResult.this);
			}
		});*/
		
		
		
	}

	


}
