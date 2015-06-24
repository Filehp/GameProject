package preferences;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Klasse fuer die Musik im Hintergrund
 */

public class Music {

	private File f;
	private AudioInputStream sound;
	private DataLine.Info info;
	private Clip clip;
	private FloatControl gainControl;
	private Prefs pref = new Prefs();

	private static Music instance = null;

	/**
	 * Spielt und loopt die Musik
	 */
	public void playMusic() {

		try {
			// Holt die Lautstaerke
			int volMusic = pref.getMusicVol(); 

			// URL zur Datei
			URL url = getClass().getResource("/music.wav"); 
			
			// Konvertiert die Datei von URL zu AudioInputStream
			sound = AudioSystem.getAudioInputStream(url); 

			// Laedt sound 
			info = new DataLine.Info(Clip.class, sound.getFormat());
			clip = (Clip) AudioSystem.getLine(info);
			clip.open(sound);

			// Bereitet den Clip vor die Lautstaerke zu aendern
			gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN); 
			// Setzt die Lautstaerke
			changeVol(volMusic); 

			// Spielt und loopt den Clip
			clip.loop(Clip.LOOP_CONTINUOUSLY);
			clip.start();
		} catch (IOException | UnsupportedAudioFileException
				| LineUnavailableException e) {
			System.out.println("Exception occurred while playing music:");
			e.printStackTrace();
		}
	}

	/**
	 * Stoppt die Musik
	 */
	public void stopMusic() {
		clip.stop();
	}

	/**
	 * Lautstaerke der Musik aendern
	 */
	public void changeVol(int vol) {
		float x = (float) -((100 - (10 * vol)) / 4); // int zu float
		gainControl.setValue(x); // Lautstaerke setzen
	}

	/**
	 * Methode, um eine Instanz dieser Klasse bereitzustellen, damit es einfacher moeglich ist, 
	 * diese Klasse zu benutzen und die Musik zu starten/stoppen
	 */
	public static Music getInstance() {
		if (instance == null)
			instance = new Music(); // Wenn es kein Instanz gibt, wir eine neue erzeugt
		return instance; 
	}

}