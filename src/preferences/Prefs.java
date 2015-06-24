package preferences;

import java.util.prefs.Preferences;

/**
 * Klasse fuer die Einstellungen
 */
public class Prefs {

	private Preferences pref;
	private int musicVol;
	private boolean musicSwitch;

	/**
	 * Constructor
	 */
	public Prefs() {
		// Holt die Root
		pref = Preferences.userRoot().node(this.getClass().getName());

		// Holt die Werte und setzt Variablen
		musicVol = pref.getInt("musicVol", 0);
		musicSwitch = pref.getBoolean("musicBool", true);

	}

	/**
	 *  Lautstaerke holen
	 * @return Musiklautstaerke
	 */
	public int getMusicVol() {
		return musicVol;
	}

	/**
	 *  Lautstaerke setzen
	 * @param musicVol
	 */
	public void setMusicVol(int musicVol) {
		this.musicVol = musicVol;
		pref.putInt("musicVol", musicVol); // Puts the music volume value

	}

	/**
	 *  Gucken, ob Musik an/aus ist
	 * @return musicSwitch
	 */
	public boolean isMusicSwitch() {
		return musicSwitch;
	}

	/**
	 *  Musik an/aus
	 * @param musicSwitch
	 */
	public void setMusicSwitch(boolean musicSwitch) {
		this.musicSwitch = musicSwitch;
		pref.putBoolean("musicBool", musicSwitch);
	}

}
