package menu;

import game.Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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

	private JLabel title = new JLabel("Scoreboard");
	private JButton back = new Button(quitIcon);
	private ArrayList<ArrayList<String>> nodes;

	public Scoreboard() { 
		
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

		// title.setFont(, 60);
		title.setBounds(Game.getWindowWidth() / 2 - 35, 50, 100, 50);

		Graphics2D g2d = (Graphics2D) g;

		// Draws the lines
		g2d.setColor(Color.black);
		g2d.drawLine(50, 140, 600, 140);
		g2d.drawLine(125, 110, 125, 450);
		g2d.drawLine(350, 110, 350, 450);
		g2d.drawLine(475, 110, 475, 450);

		// Draws the strings
		g2d.drawString("Rank", 75, 135);
		g2d.drawString("Name", 225, 135);
		g2d.drawString("Time (ms)", 400, 135);
		g2d.drawString("Date", 525, 135);

		
		  for(int i = 0; i < 15; i++){
		  
		      try{
		    	  
		    	  if(i < nodes.size()){ // Checks if i isn't bigger than nodes size
		    	  g2d.drawString(nodes.size()-i + "", 85, 260 - ((i+i) * 10)); // Draws the position
		    	  g2d.drawString(nodes.get(i).get(0), 225, 260 - ((i+i) * 10)); // Draws the name
		    	  g2d.drawString(nodes.get(i).get(1), 400, 260 - ((i+i) * 10)); // Draws the score	    	  
		    	  g2d.drawString(nodes.get(i).get(2), 525, 260 - ((i+i) * 10)); // Draws the date
		    	  } else break; // If i is bigger than nodes size, break the loop
		    	  
	    	  } catch (IndexOutOfBoundsException e){ // Catches IndexOutOfBoundsException in case "if" dosn't do it's job
	    		  break; // In case of exception, breaks the loop
	    	  }
		 

		back.setBounds(Menu.getButtonX(), Menu.getButtonY() + Game.HEIGHT / 100 * 75 / 2, Menu.getButtonWidth(), Menu.getButtonHeight());

		add(title);
		add(back);

	}
	}
}
