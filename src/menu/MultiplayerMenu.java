package menu;

import game.Game;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import multiplayer.MultiplayerServer;

/**
 * Klasse fuer das Multiplayer Menue
 */

public class MultiplayerMenu extends JComponent implements Runnable {

	// Laedt die Bilder
	Icon backIcon = new ImageIcon("resources/Back.png");
	Icon createIcon = new ImageIcon("resources/Create.png");
	Icon joinIcon = new ImageIcon("resources/Join.png");

	// Erzeugt die Buttons
	private Button back = new Button(backIcon);
	private Button create = new Button(createIcon);
	private Button join = new Button(joinIcon);

	// Erzeugt das Hintergrundbild
	private Image logo2;

	// Erzeugt das Textfeld zur Eingabe der IP
	private static JTextField field = new JTextField(
			"Please enter IP to connect");

	// Constructor
	public MultiplayerMenu() {

		// Lädt das Hintergrundbild
		try {
			logo2 = ImageIO.read(new File("resources/background2.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		// Aktionen bei Klick auf einen Button
		
		// Zurück ins Hauptmenü
		back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Game.changePanel("menu", MultiplayerMenu.this);

			}
		});

		// Bei Klick wird der Inhalt des Feldes gelöscht
		field.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				field.setText(""); 
			}
		});

		// Erstellt einen Multiplayer Server
		create.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new Thread(new MultiplayerMenu()).start();
				Game.changePanel("multiplayerClientHost", MultiplayerMenu.this);

			}
		});

		// Tritt nach Eingabe der IP einem Multiplayer Spiel bei
		join.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				// Error Meldung, wenn keine IP eingetragen wurde
				if (field.getText().trim().equals("Please enter IP to connect")
						|| field.getText().trim().isEmpty()) {
					JOptionPane.showMessageDialog(null,
							"Please enter an IP and try again.", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else { // Tritt dem Spiel mit der IP bei
					String adress = field.getText();
					Game.changePanel("multiplayerClientJoin",
							MultiplayerMenu.this);
				}
			}

		});
	}

	// Stellt die Buttons dar
	@Override
	protected void paintComponent(Graphics g) {

		// Stellt das Hintergrundbild dar
		g.drawImage(logo2, Game.WIDTH / 2 - logo2.getWidth(null) / 2, 0, null);
		
		g.setFont(new Font("default", Font.BOLD, Game.HEIGHT / 75));
		g.drawString("created by Christopher Brost and Filip Meyer", Game.WIDTH / 99 , Game.HEIGHT  );

		// Setzt die Position und Groesse der Buttons
		back.setBounds(Menu.getButtonX(), Menu.getButtonY() + Game.HEIGHT / 100
				* 75 / 2, Menu.getButtonWidth(), Menu.getButtonHeight());
		create.setBounds(Menu.getButtonX(), Menu.getButtonY() - Game.HEIGHT
				/ 100 * 25 / 2, Menu.getButtonWidth(), Menu.getButtonHeight());
		join.setBounds(Menu.getButtonX(), Menu.getButtonY() + Game.HEIGHT / 100
				* 25 / 2, Menu.getButtonWidth(), Menu.getButtonHeight());
		field.setBounds(Menu.getButtonX(), Menu.getButtonY() + Game.HEIGHT
				/ 100 * 15 / 2, Menu.getButtonWidth(),
				Menu.getButtonHeight() / 2);

		// Fuegt die Buttons der GUI hinzu
		add(back);
		add(field);
		add(create);
		add(join);

	}

	// Startet den Multiplayer Server
	@Override
	public void run() {
		try {
			new MultiplayerServer(Game.getWindowWidth(), Game.getWindowHeight())
					.start();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	// Methode zum Auslesen der eingetragenen IP
	public static String getIp() {
		String ip;

		ip = field.getText();

		return ip;

	}

}
