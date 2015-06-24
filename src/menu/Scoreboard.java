package menu;

import game.Game;
import game.GamePanel.Diff;

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
	private ArrayList<ArrayList<String>> nodes1;
	private ArrayList<ArrayList<String>> nodes2;
	
    private Image scoreboardLogo;

	public Scoreboard() { 
		
		try {
			scoreboardLogo = ImageIO.read(new File("resources/scoreboardLogo.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		ScoreDB db = new ScoreDB(Diff.EASY); // Creates the ScoreDB object
		ScoreDB db1 = new ScoreDB(Diff.MEDIUM); // Creates the ScoreDB object
		ScoreDB db2 = new ScoreDB(Diff.HARD); // Creates the ScoreDB object
		nodes = db.seclectScores(Diff.EASY); // Gets nodes from db 
		nodes1 = db1.seclectScores(Diff.MEDIUM); // Gets nodes from db 
		nodes2 = db2.seclectScores(Diff.HARD); // Gets nodes from db 
		db.close(); // Closes the data base
		db1.close();
		db2.close();

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
		g2d.drawLine(Game.getWindowWidth() / 10, Game.getWindowHeight() * 10 / 50, Game.getWindowWidth() - Game.getWindowWidth() / 10, Game.getWindowHeight() / 5);
		g2d.drawLine(Game.getWindowWidth() / 10, Game.getWindowHeight() * 10 / 25, Game.getWindowWidth() - Game.getWindowWidth() / 10, Game.getWindowHeight() * 10 / 25);
		g2d.drawLine(Game.getWindowWidth() / 10, Game.getWindowHeight() * 100 / 167, Game.getWindowWidth() - Game.getWindowWidth() / 10, Game.getWindowHeight() * 100 / 167);
		g2d.drawLine(Game.getWindowWidth() / 10, Game.getWindowHeight() * 100 / 125, Game.getWindowWidth() - Game.getWindowWidth() / 10, Game.getWindowHeight() * 100 / 125);
		g2d.drawLine(Game.getWindowWidth() / 6, Game.getWindowHeight() / 6, Game.getWindowWidth() / 6, Game.getWindowHeight() * 100 / 125);
		g2d.drawLine(Game.getWindowWidth() / 3, Game.getWindowHeight() / 6, Game.getWindowWidth() / 3, Game.getWindowHeight() * 100 / 125);
		g2d.drawLine(Game.getWindowWidth() / 2, Game.getWindowHeight() / 6, Game.getWindowWidth() / 2, Game.getWindowHeight() * 100 / 125);
		g2d.drawLine(Game.getWindowWidth() / 4 * 3, Game.getWindowHeight() / 6, Game.getWindowWidth() / 4 * 3, Game.getWindowHeight() * 100 / 125);

		// Draws the strings
		g2d.drawString("Rank", Game.getWindowWidth() / 9, Game.getWindowHeight() * 10 / 52);
		g2d.drawString("Difficulty", Game.getWindowWidth() * 10 / 48, Game.getWindowHeight() * 10 / 52);
		g2d.drawString("Name", Game.getWindowWidth() * 10 / 26, Game.getWindowHeight() * 10 / 52);
		g2d.drawString("Time (ms)", Game.getWindowWidth() * 10 / 17, Game.getWindowHeight() * 10 / 52);
		g2d.drawString("Date", Game.getWindowWidth() * 10 / 13, Game.getWindowHeight() * 10 / 52);

		
		  for(int i = 0; i < 5; i++){
		  
		      try{
		    	  //if (nodes.get(i).get(0).equals("EASY")) {
		    	  if(i < nodes.size()){ // Checks if i isn't bigger than nodes size
		    	  g2d.drawString(i+1 + "", Game.getWindowWidth() / 8, Game.getWindowHeight() * 10 / 41 + (i+i) * 10);; // Draws the position
		    	  g2d.drawString(nodes.get(i).get(0), Game.getWindowWidth() * 10 / 48, Game.getWindowHeight() * 10 / 41 + (i+i) * 10);; // Draws the name
		    	  g2d.drawString(nodes.get(i).get(1), Game.getWindowWidth() * 10 / 26, Game.getWindowHeight() * 10 / 41 + (i+i) * 10);; // Draws the score	    	  
		    	  g2d.drawString(nodes.get(i).get(2), Game.getWindowWidth() * 10 / 17, Game.getWindowHeight() * 10 / 41 + (i+i) * 10);; // Draws the date
		    	  g2d.drawString(nodes.get(i).get(3), Game.getWindowWidth() * 10 / 13, Game.getWindowHeight() * 10 / 41 + (i+i) * 10);; // Draws the date
		    	  } //else break; // If i is bigger than nodes size, break the loop
		    	  //}
	    	  } catch (IndexOutOfBoundsException e){ // Catches IndexOutOfBoundsException in case "if" dosn't do it's job
	    		  break; // In case of exception, breaks the loop
	    	  }
		  }
			  for(int h = 0; h < 5; h++){
				  try{
		    	  
		    	  //if (nodes.get(i).get(0).equals("MEDIUM")) {
		    	  if(h < nodes1.size()){ // Checks if i isn't bigger than nodes size
		    	  g2d.drawString(h+1 + "", Game.getWindowWidth() / 8, Game.getWindowHeight() * 100 / 224 + (h+h) * 10); // Draws the position
		    	  g2d.drawString(nodes1.get(h).get(0), Game.getWindowWidth() * 10 / 48, Game.getWindowHeight() * 100 / 224 + (h+h) * 10); // Draws the name
		    	  g2d.drawString(nodes1.get(h).get(1), Game.getWindowWidth() * 10 / 26, Game.getWindowHeight() * 100 / 224 + (h+h) * 10); // Draws the score	    	  
		    	  g2d.drawString(nodes1.get(h).get(2), Game.getWindowWidth() * 10 / 17, Game.getWindowHeight() * 100 / 224 + (h+h) * 10); // Draws the date
		    	  g2d.drawString(nodes1.get(h).get(3), Game.getWindowWidth() * 10 / 13, Game.getWindowHeight() * 100 / 224 + (h+h) * 10); // Draws the date
		    	  } //else break; // If i is bigger than nodes size, break the loop
		    	  //}
		    	  } catch (IndexOutOfBoundsException e){ // Catches IndexOutOfBoundsException in case "if" dosn't do it's job
		    		  break; // In case of exception, breaks the loop
		    	  }
			  }
				  for(int k = 0; k < 5; k++){
					  try{
		    	  //if (nodes.get(i).get(0).equals("HARD")) {
		    	  if(k < nodes2.size()){ // Checks if i isn't bigger than nodes size
		    	  g2d.drawString(k+1 + "", Game.getWindowWidth() / 8, Game.getWindowHeight() * 100 / 159 + (k+k) * 10); // Draws the position
		    	  g2d.drawString(nodes2.get(k).get(0), Game.getWindowWidth() * 10 / 48, Game.getWindowHeight() * 100 / 159 + (k+k) * 10); // Draws the name
		    	  g2d.drawString(nodes2.get(k).get(1), Game.getWindowWidth() * 10 / 26, Game.getWindowHeight() * 100 / 159 + (k+k) * 10); // Draws the score	    	  
		    	  g2d.drawString(nodes2.get(k).get(2), Game.getWindowWidth() * 10 / 17, Game.getWindowHeight() * 100 / 159 + (k+k) * 10); // Draws the date
		    	  g2d.drawString(nodes2.get(k).get(3), Game.getWindowWidth() * 10 / 13, Game.getWindowHeight() * 100 / 159 + (k+k) * 10); // Draws the date
		    	  } 
		    	  else break; // If i is bigger than nodes size, break the loop
		    	  //}
	    	  } catch (IndexOutOfBoundsException e){ // Catches IndexOutOfBoundsException in case "if" dosn't do it's job
	    		  break; // In case of exception, breaks the loop
	    	  }
		 
	}
			back.setBounds(Menu.getButtonX(), Menu.getButtonY() + Game.HEIGHT / 100 * 75 / 2, Menu.getButtonWidth(), Menu.getButtonHeight());

			add(back); 
	}
}

