package menu;

import game.Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;

import preferences.ScoreDB;

/**
 *	Klasse fuer das Scoreboard
 */

public class Scoreboard extends JComponent {

	private static final long serialVersionUID = 1L;
	Icon quitIcon = new ImageIcon("resources/Quit.png");

	private JButton back = new Button(quitIcon);
	private ArrayList<ArrayList<String>> nodes;
	
    private Image scoreboardLogo;

	public Scoreboard() { 
		
		try {
			scoreboardLogo = ImageIO.read(new File("resources/scoreboardLogo.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		ScoreDB db = new ScoreDB(); // Creates the ScoreDB object
		nodes = db.seclectScores(); // Gets nodes from db 
		db.close(); // Closes the data base

		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Game.changePanel("menu", Scoreboard.this); // Switches the
															// JComponent in
															// JPanel
			}
		});

	}

	@Override
	protected void paintComponent(Graphics g) {
		
		g.drawImage(scoreboardLogo, Game.getWindowWidth() / 2 - scoreboardLogo.getWidth(null) / 2, 0, null);

		Graphics2D g2d = (Graphics2D) g;

		// Draws the lines
		g2d.setColor(Color.black);
		g2d.drawLine(Game.getWindowWidth() / 10, Game.getWindowHeight() / 5, Game.getWindowWidth() - Game.getWindowWidth() / 10, Game.getWindowHeight() / 5);
		g2d.drawLine(Game.getWindowWidth() / 5, Game.getWindowHeight() / 6, Game.getWindowWidth() / 5, Game.getWindowHeight() / 2);
		g2d.drawLine(Game.getWindowWidth() / 2, Game.getWindowHeight() / 6, Game.getWindowWidth() / 2, Game.getWindowHeight() / 2);
		g2d.drawLine(Game.getWindowWidth() / 4 * 3, Game.getWindowHeight() / 6, Game.getWindowWidth() / 4 * 3, Game.getWindowHeight() / 2);

		// Draws the strings
		g2d.drawString("Rank", Game.getWindowWidth() / 8, Game.getWindowHeight() / 5 - 5);
		g2d.drawString("Name", Game.getWindowWidth() / 3, Game.getWindowHeight() / 5 - 5);
		g2d.drawString("Time (ms)", Game.getWindowWidth() / 2 + Game.getWindowWidth() / 10, Game.getWindowHeight() / 5 - 5);
		g2d.drawString("Date", Game.getWindowWidth() / 2 + Game.getWindowWidth() / 3, Game.getWindowHeight() / 5 - 5);

		
		  for(int i = 0; i < 15; i++){
		  
		      try{
		    	  
		    	  if(i < nodes.size()){ // Checks if i isn't bigger than nodes size
		    	  g2d.drawString(nodes.size()-i + "", Game.getWindowWidth() / 8, Game.getWindowWidth() / 4 + (nodes.size()-i) * 20); // Draws the position
		    	  g2d.drawString(nodes.get(i).get(0), Game.getWindowWidth() / 3, Game.getWindowWidth() / 4 + (nodes.size()-i) * 20); // Draws the name
		    	  g2d.drawString(nodes.get(i).get(1), Game.getWindowWidth() / 2 + Game.getWindowWidth() / 10, Game.getWindowWidth() / 4 + (nodes.size()-i) * 20); // Draws the score	    	  
		    	  g2d.drawString(nodes.get(i).get(2), Game.getWindowWidth() / 2 + Game.getWindowWidth() / 3 - 30, Game.getWindowWidth() / 4 + (nodes.size()-i) * 20); // Draws the date
		    	  } else break; // If i is bigger than nodes size, break the loop
		    	  
	    	  } catch (IndexOutOfBoundsException e){ // Catches IndexOutOfBoundsException in case "if" dosn't do it's job
	    		  break; // In case of exception, breaks the loop
	    	  }
		 
	}
			back.setBounds(Menu.getButtonX(), Menu.getButtonY() + Game.HEIGHT / 100 * 75 / 2, Menu.getButtonWidth(), Menu.getButtonHeight());

			add(back); 
	}
}
