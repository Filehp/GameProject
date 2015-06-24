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
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;

import preferences.ScoreDB;

/**
 * Klasse fuer das Scoreboard
 */

public class Scoreboard extends JComponent {

	// Lädt das Buttonbild
	Icon quitIcon = new ImageIcon("resources/Quit.png");

	// Erzeugt den Zurück Button
	private JButton back = new Button(quitIcon);

	// Erzeugt 3 Arrays für jeden Schwierigkeitsgrad
	private ArrayList<ArrayList<String>> nodes;
	private ArrayList<ArrayList<String>> nodes1;
	private ArrayList<ArrayList<String>> nodes2;

	// Erzeugt das Logo für das Scoreboard
	private Image scoreboardLogo;

	// Constructor
	public Scoreboard() {

		// Lädt das Logo
		try {
			scoreboardLogo = ImageIO.read(new File(
					"resources/scoreboardLogo.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		// Erzeugt 3 ScoreDB Objekte für jeden Schwierigkeitsgrad
		ScoreDB db = new ScoreDB(Diff.EASY); // Für EASY
		ScoreDB db1 = new ScoreDB(Diff.MEDIUM); // Für MEDIUM
		ScoreDB db2 = new ScoreDB(Diff.HARD); // Für HARD
		
		// Holt die nodes aus der Datenbank
		nodes = db.seclectScores(Diff.EASY); // Für EASY
		nodes1 = db1.seclectScores(Diff.MEDIUM); // Für MEDIUM
		nodes2 = db2.seclectScores(Diff.HARD); // Für HARD
		
		// Schließt die Datenbanken
		db.close(); 
		db1.close();
		db2.close();

		// Wechselt das Panel zurück zum Hauptmenü
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Game.changePanel("menu", Scoreboard.this); 
			}
		});

	}

	// Stellt die Objekte dar
	@Override
	protected void paintComponent(Graphics g) {

		// Stellt das Logo dar
		g.drawImage(scoreboardLogo,
				Game.getWindowWidth() / 2 - scoreboardLogo.getWidth(null) / 2,
				0, null);

		g.setFont(new Font("default", Font.BOLD, Game.HEIGHT / 50));
		Graphics2D g2d = (Graphics2D) g;

		// Zeichnet die Linien für die Tabelle
		g2d.setColor(Color.black);
		// Horizontal
		g2d.drawLine(Game.getWindowWidth() / 10, Game.getWindowHeight() * 10 / 50, Game.getWindowWidth() - Game.getWindowWidth() / 10, Game.getWindowHeight() / 5);
		g2d.drawLine(Game.getWindowWidth() / 10, Game.getWindowHeight() * 10 / 25, Game.getWindowWidth() - Game.getWindowWidth() / 10, Game.getWindowHeight() * 10 / 25);
		g2d.drawLine(Game.getWindowWidth() / 10, Game.getWindowHeight() * 100 / 167, Game.getWindowWidth()- Game.getWindowWidth() / 10, Game.getWindowHeight() * 100 / 167);
		g2d.drawLine(Game.getWindowWidth() / 10, Game.getWindowHeight() * 100 / 125, Game.getWindowWidth()- Game.getWindowWidth() / 10, Game.getWindowHeight() * 100 / 125);
		// Vertikal
		g2d.drawLine(Game.getWindowWidth() / 6, Game.getWindowHeight() / 6, Game.getWindowWidth() / 6, Game.getWindowHeight() * 100 / 125); 
		g2d.drawLine(Game.getWindowWidth() / 3, Game.getWindowHeight() / 6, Game.getWindowWidth() / 3, Game.getWindowHeight() * 100 / 125); 
		g2d.drawLine(Game.getWindowWidth() / 2, Game.getWindowHeight() / 6, Game.getWindowWidth() / 2, Game.getWindowHeight() * 100 / 125);
		g2d.drawLine(Game.getWindowWidth() / 4 * 3, Game.getWindowHeight() / 6, Game.getWindowWidth() / 4 * 3, Game.getWindowHeight() * 100 / 125);

		// Zeichnet die Spaltenbezeichnungen
		g2d.drawString("Rank", Game.getWindowWidth() / 10, Game.getWindowHeight() * 10 / 52);
		g2d.drawString("Difficulty", Game.getWindowWidth() * 10 / 48, Game.getWindowHeight() * 10 / 52);
		g2d.drawString("Name", Game.getWindowWidth() * 10 / 26, Game.getWindowHeight() * 10 / 52);
		g2d.drawString("Time (ms)", Game.getWindowWidth() * 10 / 17, Game.getWindowHeight() * 10 / 52);
		g2d.drawString("Date", Game.getWindowWidth() * 10 / 13, Game.getWindowHeight() * 10 / 52);

		g.setFont(new Font("default", Font.PLAIN, Game.HEIGHT / 60));
		for (int i = 0; i < 5; i++) { // Für maximal 5 Eintragungen

			try {
				if (i < nodes.size()) { // Überprüft, ob i kleiner als die Größe des Arrays ist
					g2d.drawString(i + 1 + "", Game.getWindowWidth() / 8, Game.getWindowHeight() * 10 / 41 + (i + i) * 10); // Rank
					g2d.drawString(nodes.get(i).get(0),Game.getWindowWidth() * 10 / 48, Game.getWindowHeight() * 10 / 41 + (i + i) * 10); // Schwierigkeitsgrad
					g2d.drawString(nodes.get(i).get(1), Game.getWindowWidth() * 10 / 26, Game.getWindowHeight() * 10 / 41 + (i + i) * 10); // Name
					g2d.drawString(nodes.get(i).get(2), Game.getWindowWidth() * 10 / 17, Game.getWindowHeight() * 10 / 41 + (i + i) * 10); // Zeit
					g2d.drawString(nodes.get(i).get(3), Game.getWindowWidth() * 10 / 13, Game.getWindowHeight() * 10 / 41 + (i + i) * 10); // Datum
				}  else break; // Wenn i größer als die Arraygröße ist wird der Loop gestoppt
					
			} catch (IndexOutOfBoundsException e) { // Fängt zur Sicherheit IndexOutOfBoundsExceptions
				break; // Loop wird gestoppt
			}
		}
		for (int h = 0; h < 5; h++) { // Für maximal 5 Eintragungen
			
			try {
				if (h < nodes1.size()) { // Überprüft, ob i kleiner als die Größe des Arrays ist
					g2d.drawString(h + 1 + "", Game.getWindowWidth() / 8, Game.getWindowHeight() * 100 / 224 + (h + h) * 10); // Rank
					g2d.drawString(nodes1.get(h).get(0), Game.getWindowWidth() * 10 / 48, Game.getWindowHeight() * 100 / 224 + (h + h) * 10); // Schwierigkeitsgrad
					g2d.drawString(nodes1.get(h).get(1), Game.getWindowWidth() * 10 / 26, Game.getWindowHeight() * 100 / 224 + (h + h) * 10); // Name
					g2d.drawString(nodes1.get(h).get(2), Game.getWindowWidth() * 10 / 17, Game.getWindowHeight() * 100 / 224 + (h + h) * 10); // Zeit
					g2d.drawString(nodes1.get(h).get(3), Game.getWindowWidth() * 10 / 13, Game.getWindowHeight() * 100 / 224 + (h + h) * 10); // Datum
				} else break; // Wenn i größer als die Arraygröße ist wird der Loop gestoppt

			} catch (IndexOutOfBoundsException e) { // Fängt zur Sicherheit IndexOutOfBoundsExceptions
				break; // Loop wird gestoppt
			}
		}
		for (int k = 0; k < 5; k++) { // Für maximal 5 Eintragungen
			
			try {
				if (k < nodes2.size()) { // Überprüft, ob i kleiner als die Größe des Arrays ist
					g2d.drawString(k + 1 + "", Game.getWindowWidth() / 8, Game.getWindowHeight() * 100 / 159 + (k + k) * 10); // Rank
					g2d.drawString(nodes2.get(k).get(0), Game.getWindowWidth() * 10 / 48, Game.getWindowHeight() * 100 / 159 + (k + k) * 10); // Schwierigkeitsgrad
					g2d.drawString(nodes2.get(k).get(1), Game.getWindowWidth() * 10 / 26, Game.getWindowHeight() * 100 / 159 + (k + k) * 10); // Name
					g2d.drawString(nodes2.get(k).get(2), Game.getWindowWidth() * 10 / 17, Game.getWindowHeight() * 100 / 159 + (k + k) * 10); // Zeit
					g2d.drawString(nodes2.get(k).get(3), Game.getWindowWidth() * 10 / 13, Game.getWindowHeight() * 100 / 159 + (k + k) * 10); // Datum
				} else break; // Wenn i größer als die Arraygröße ist wird der Loop gestoppt

			} catch (IndexOutOfBoundsException e) { // Fängt zur Sicherheit IndexOutOfBoundsExceptions
				break; // Loop wird gestoppt
				}

		}
		// Setzt Position und Größe des Button
		back.setBounds(Menu.getButtonX(), Menu.getButtonY() + Game.HEIGHT / 100
				* 75 / 2, Menu.getButtonWidth(), Menu.getButtonHeight());

		// Fügt ihn der GUI hinzu
		add(back);
		
		
		g.setFont(new Font("default", Font.BOLD, Game.HEIGHT / 75));
		g.drawString("created by Christopher Brost and Filip Meyer", Game.WIDTH / 99 , Game.HEIGHT  );
	}
}
