package newMenu;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Settings extends JComponent{
	
	private static final long serialVersionUID = 1L;

	private JLabel title = new JLabel("Settings");
	private JSlider gameSlider = new JSlider(JSlider.HORIZONTAL, 0, 10 , 0);
	private JSlider musicSlider = new JSlider(JSlider.HORIZONTAL, 0, 10 , 0);
	private JButton restartScore = new JButton("Restart scoreboard");
	private JButton musicOn = new JButton("Turn music on");
	private JButton musicOff = new JButton("Turn music off");
	private JButton exit = new JButton("Exit");
	private JButton ok = new JButton("OK");
	
	private Graphics2D g2;
	
	private boolean music;
	private Music musicInstance = Music.getInstance(); // Gets the music instance 
	
	private Prefs pref = new Prefs();
	
	public Settings() {
		
		 restartScore.addActionListener(new ActionListener() {
				
			@Override
			public void actionPerformed(ActionEvent e) {
				ScoreDB db = new ScoreDB(); // Creates database object
				db.clearTable(); // Clears database
				db.close(); // Closes database
			}
		});
		 
		 musicOn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				switchMusicButtons(false); // Switches buttons 
				musicInstance.playMusic(); // Plays music
			}
		});
		 
		 musicOff.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				switchMusicButtons(true); // Switches buttons
				musicInstance.stopMusic(); // Stops music
			}
		});
		 
		 musicSlider.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				int vol = musicSlider.getValue();	// Gets the slider value			
				musicInstance.changeVol(vol);		// Changes the vol
			}
		});
		 
			ok.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// Sets preferences
					pref.setGameVol(gameSlider.getValue());		
					pref.setMusicVol(musicSlider.getValue());
					pref.setMusicSwitch(music);
					Game.changePanel("menu", Settings.this); // Switches component
				}
			});
			
			 exit.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
						setDefSettings(); // Restarts ui back to old preferences 
						Game.changePanel("menu", Settings.this); // Switches component
				}
			});		 
		
	}
	
	@Override
	protected void paintComponent(Graphics g) {

		g2 = (Graphics2D) g; 
		
		title.setBounds(Game.getWindowWidth() / 2 - 25,Game.getWindowHeight() / 25 ,300,70); // Sets title position	
		
		//Draws volume strings
		g.drawString("Game volume", 70, 112);
	    g.drawString("Music volume", 70, 185);
		
	    // Sets slider's background and position
		gameSlider.setBackground(new Color(190, 190, 190));
		gameSlider.setBounds(70,137,500,20);
		musicSlider.setBackground(new Color(190, 190, 190));
		musicSlider.setBounds(70,200,500,20);
		
		// Sets buttons position
		restartScore.setBounds(100, 240, 250, 50);
		musicOn.setBounds(400, 240, 250, 50);
		musicOff.setBounds(400, 240, 250, 50);
		
		
		// Sets buttons position
		exit.setBounds(Game.WIDTH / 2 + 25, Game.HEIGHT / 2 + 300, 200, 40);
		ok.setBounds(Game.WIDTH / 2 - 225, Game.HEIGHT / 2 + 300, 200, 40);
			    
		// Adds elemements to the ui     	 
		add(title);
		add(gameSlider);
		add(musicSlider);
		add(restartScore);
		add(musicOff);
		add(musicOn);
		add(exit);
		add(ok);
	}
	
	private void setDefSettings(){
		 gameSlider.setValue(pref.getGameVol()); // Sets the gameSlider value
		 musicSlider.setValue(pref.getMusicVol()); // Sets the gameSlider value
		 music = pref.isMusicSwitch(); // Checks if user turned music off 
		 
		 // Switches the buttons according to music boolean
		 if(music) switchMusicButtons(false); 
		 else switchMusicButtons(true);	
	}
	
	 private void switchMusicButtons(boolean b){
		 if(b){
			 musicOff.setVisible(false); // hides the musicOff button
			 musicOn.setVisible(true); // shows the musicOn button
			 music = false; // Sets music boolean to false
		 }
		 else{
			 musicOff.setVisible(true); // shows the musicOff button
			 musicOn.setVisible(false); // hides musicOn button 
			 music = true; // Sets music boolean to true
		 }
	 }
	
	

}
