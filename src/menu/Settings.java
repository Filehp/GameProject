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

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import preferences.Music;
import preferences.Prefs;
import preferences.ScoreDB;

/**
 *	Klasse fuer die Einstellungen
 */

public class Settings extends JComponent{
	
	private static final long serialVersionUID = 1L;

	private JSlider musicSlider = new JSlider(JSlider.HORIZONTAL, 0, 10 , 0);
	
	Icon okIcon = new ImageIcon("resources/Ok.png");
	Icon quitIcon = new ImageIcon("resources/Quit.png");
	Icon mOnIcon = new ImageIcon("resources/MusicOn.png");
	Icon mOffIcon = new ImageIcon("resources/MusicOff.png");
	Icon reScoreIcon = new ImageIcon("resources/RestartScore.png");
	private Button restartScore = new Button(reScoreIcon);
	private Button musicOn = new Button(mOnIcon);
	private Button musicOff = new Button(mOffIcon);
	private Button quit = new Button(quitIcon);
	private Button ok = new Button(okIcon);
	
	private Graphics2D g2;
	
	private boolean music;
	private Music musicInstance = Music.getInstance(); // Gets the music instance 
	
	private Prefs pref = new Prefs();
	
    private Image SettingsLogo;
	
	public Settings() {
		
		try {
			SettingsLogo = ImageIO.read(new File("resources/SettingsLogo.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
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
					pref.setMusicVol(musicSlider.getValue());
					pref.setMusicSwitch(music);
					Game.changePanel("menu", Settings.this); // Switches component
				}
			});
			
			 quit.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
						setDefSettings(); // Restarts ui back to old preferences 
						Game.changePanel("menu", Settings.this); // Switches component
				}
			});		 
		
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		
		g.drawImage(SettingsLogo, Game.getWindowWidth() / 2 - SettingsLogo.getWidth(null) / 2, 0, null);

		g2 = (Graphics2D) g; 
	
		
		//Draws volume string
	    g.drawString("Music volume", 70, 185);
		
	    // Sets slider's background and position
		musicSlider.setBackground(new Color(190, 190, 190));
		musicSlider.setBounds(70,200,500,20);
		
		// Sets buttons position
		restartScore.setBounds(Menu.getButtonX(), Menu.getButtonY(), Menu.getButtonWidth(), Menu.getButtonHeight());
		musicOn.setBounds(Menu.getButtonX(), Menu.getButtonY() - Game.HEIGHT / 100 * 25 / 2, Menu.getButtonWidth(), Menu.getButtonHeight());
		musicOff.setBounds(Menu.getButtonX(), Menu.getButtonY() - Game.HEIGHT / 100 * 25 / 2, Menu.getButtonWidth(), Menu.getButtonHeight());
		
		
		// Sets buttons position
		quit.setBounds(Game.WIDTH / 9 * 5, Game.HEIGHT / 2 + 300, Menu.getButtonWidth(), Menu.getButtonHeight());
		ok.setBounds(Game.WIDTH / 9 , Game.HEIGHT / 2 + 300, Menu.getButtonWidth(), Menu.getButtonHeight());
			    
		// Adds elemements to the ui     	 
		add(musicSlider);
		add(restartScore);
		add(musicOff);
		add(musicOn);
		add(quit);
		add(ok);
	}
	
	private void setDefSettings(){
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
