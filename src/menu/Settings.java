package menu;

import game.Game;
import game.GamePanel.Diff;

import java.awt.Color;
import java.awt.Font;
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
 * Klasse fuer die Einstellungen
 */

public class Settings extends JComponent {

	// Slider für die Musiklautstärke
	private JSlider musicSlider = new JSlider(JSlider.HORIZONTAL, 0, 10, 0);

	// Laedt die Bilder für die Buttons
	Icon okIcon = new ImageIcon("resources/Ok.png");
	Icon quitIcon = new ImageIcon("resources/Quit.png");
	Icon mOnIcon = new ImageIcon("resources/MusicOn.png");
	Icon mOffIcon = new ImageIcon("resources/MusicOff.png");
	Icon reScoreIcon = new ImageIcon("resources/RestartScore.png");

	// Erzeugt die Buttons
	private Button restartScore = new Button(reScoreIcon);
	private Button musicOn = new Button(mOnIcon);
	private Button musicOff = new Button(mOffIcon);
	private Button quit = new Button(quitIcon);
	private Button ok = new Button(okIcon);

	private Graphics2D g2;

	// Holt die musicInstance
	private boolean music;
	private Music musicInstance = Music.getInstance();

	// Einstellungen
	private Prefs pref = new Prefs();

	// Hintergrundbild
	private Image settingsLogo;

	// Constructor
	public Settings() {

		// Laedt das Hintergrundbild
		try {
			settingsLogo = ImageIO.read(new File("resources/settingsLogo.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		// Scoreboard zuruecksetzen
		restartScore.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ScoreDB db = new ScoreDB(Diff.EASY); // Erzeugt das Datenbankobjekt für EASY
				db.clearTable(Diff.EASY); // Löscht den Inhalt der Datenbank
				db.close(); // Schließt die Datenbank
				ScoreDB db1 = new ScoreDB(Diff.MEDIUM); //Erzeugt das Datenbankobjekt für EASYt
				db1.clearTable(Diff.MEDIUM); // Löscht den Inhalt der Datenbank
				db1.close(); // Schließt die Datenbank
				ScoreDB db2 = new ScoreDB(Diff.HARD); // Erzeugt das Datenbankobjekt für EASY
				db2.clearTable(Diff.HARD); // Löscht den Inhalt der Datenbank
				db2.close(); // Schließt die Datenbank

			}
		});

		// Stellt die Musik an 
		musicOn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				switchMusicButtons(false); // Wechselt den Button
				musicInstance.playMusic(); // Startet die Musik
			}
		});

		// Stellt die Musik aus
		musicOff.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				switchMusicButtons(true); // Wechselt den Button
				musicInstance.stopMusic(); // Stoppt die Musik
			}
		});

		// Lautstaerke der Musik anpassen
		musicSlider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				int vol = musicSlider.getValue(); // Momentane Lautstaerke
				musicInstance.changeVol(vol); // Aendert die Lautstaerke
			}
		});

		// Speichert die Einstellung
		ok.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				pref.setMusicVol(musicSlider.getValue()); //Speichert Lautstaerke
				pref.setMusicSwitch(music); // Speichert, ob Musik an oder aus ist
				Game.changePanel("menu", Settings.this); // Zurück zum Menue
			}
		});

		// Zurück Button
		quit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setDefSettings(); // alte Einstellungen
				Game.changePanel("menu", Settings.this); // Zurück zum Menue
			}
		});

	}

	// Stellt die Objekte dar
	@Override
	protected void paintComponent(Graphics g) {

		// Stellt das Hintergrundbild dar
		g.drawImage(settingsLogo,
				Game.getWindowWidth() / 2 - settingsLogo.getWidth(null) / 2, 0, null);

		g2 = (Graphics2D) g;
		g.setFont(new Font("default", Font.BOLD, Game.HEIGHT / 50));

		// Zeichnet den String für die Musiklautstaerke
		g.drawString("Music volume", Game.WIDTH / 9, Game.HEIGHT * 10 / 40);

		// Slider Hintergrund und Position/Groesse
		musicSlider.setBackground(new Color(190, 190, 190));
		musicSlider.setBounds(Game.WIDTH / 9, Game.HEIGHT * 10 / 36, Game.WIDTH / 9 * 7, 20);

		// Buttons Position und Groesse
		restartScore.setBounds(Menu.getButtonX(), Menu.getButtonY(), Menu.getButtonWidth(), Menu.getButtonHeight());
		musicOn.setBounds(Menu.getButtonX(), Menu.getButtonY() - Game.HEIGHT / 100 * 25 / 2, Menu.getButtonWidth(), Menu.getButtonHeight());
		musicOff.setBounds(Menu.getButtonX(), Menu.getButtonY() - Game.HEIGHT / 100 * 25 / 2, Menu.getButtonWidth(), Menu.getButtonHeight());
		quit.setBounds(Game.WIDTH / 9 * 5, Menu.getButtonY() + Game.HEIGHT / 100 * 75 / 2, Menu.getButtonWidth(), Menu.getButtonHeight());
		ok.setBounds(Game.WIDTH / 9, Menu.getButtonY() + Game.HEIGHT / 100 * 75 / 2, Menu.getButtonWidth(), Menu.getButtonHeight());

		// Fuegt die Buttons zur GUI hinzu
		add(musicSlider);
		add(restartScore);
		add(musicOff);
		add(musicOn);
		add(quit);
		add(ok);
		
		g.setFont(new Font("default", Font.BOLD, Game.HEIGHT / 75));
		g.drawString("created by Christopher Brost and Filip Meyer", Game.WIDTH / 99 , Game.HEIGHT  );
	}

	// Default Einstellungen
	private void setDefSettings() {
		musicSlider.setValue(pref.getMusicVol()); // Setzt den Slider
		music = pref.isMusicSwitch(); // Guckt, ob die Musik an ist

		// Wechselt den Musik An/Aus Button
		if (music)
			switchMusicButtons(false);
		else
			switchMusicButtons(true);
	}

	// Wechselt zwischen den An / Aus Buttons
	private void switchMusicButtons(boolean b) {
		if (b) {
			musicOff.setVisible(false); // musicOff unsichtbar
			musicOn.setVisible(true); // musicOn sichtbar
			music = false; // music boolean auf false
		} else {
			musicOff.setVisible(true); // musicOff sichtbar
			musicOn.setVisible(false); // musicOn unsichtbar
			music = true; // music boolean auf false
		}
	}

}
