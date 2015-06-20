package preferences;

import java.util.prefs.Preferences;

public class Prefs {
	
	private Preferences pref;
	private int musicVol, gameVol;
	private boolean musicSwitch;
	
	public Prefs(){
		pref = Preferences.userRoot().node(this.getClass().getName()); // Gets the root 
		
		// Gets the values and assigns them to variables 
		musicVol = pref.getInt("musicVol", 0);
		musicSwitch = pref.getBoolean("musicBool", true);
		
	}
	
	public int getMusicVol() { 
		return musicVol;
	}
	
	public void setMusicVol(int musicVol) {
		this.musicVol = musicVol;
		pref.putInt("musicVol", musicVol); // Puts the music volume value 
		
	}
	
	
	public boolean isMusicSwitch() {
		return musicSwitch;
	}
	
	public void setMusicSwitch(boolean musicSwitch) {
		this.musicSwitch = musicSwitch;
		pref.putBoolean("musicBool", musicSwitch); // Puts the game volume value (true or false)
	}

}
