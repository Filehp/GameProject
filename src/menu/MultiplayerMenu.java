package menu;

import game.Game;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import multiplayer.MultiplayerServer;

/**
 *	Klasse fuer das Multiplayer Menue
 */

public class MultiplayerMenu extends JComponent implements Runnable {

	//Laedt die Bilder
	Icon backIcon = new ImageIcon("resources/Back.png");
	Icon createIcon = new ImageIcon("resources/Create.png");
	Icon joinIcon = new ImageIcon("resources/Join.png");

	//Erzeugt die Buttons
	private Button back = new Button(backIcon);
	private Button create = new Button(createIcon);
	private Button join = new Button(joinIcon);
	
	//Erzeugt das Textfeld zur Eingabe der IP
	private static JTextField field = new JTextField("Please enter IP to connect");

	//Constructor
	public MultiplayerMenu() {

	//Aktion bei Klick auf einen Button
		back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Game.changePanel("menu", MultiplayerMenu.this);

			}
		});
		
		field.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				field.setText(""); //Löscht den Inhalt des Feldes
			}
		});
		
		create.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new Thread(new MultiplayerMenu()).start();
				Game.changePanel("multiplayerClientHost", MultiplayerMenu.this);



			}
		});
		
		join.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				String adress = field.getText();
                Game.changePanel("multiplayerClientJoin", MultiplayerMenu.this);

			}
		});
	}

	//Stellt die Buttons dar
	@Override
	protected void paintComponent(Graphics g) {

		//Setzt die Position und Groesse der Buttons
		back.setBounds(Menu.getButtonX(), Menu.getButtonY() + Game.HEIGHT / 100 * 75 / 2, Menu.getButtonWidth(), Menu.getButtonHeight());
		create.setBounds(Menu.getButtonX(), Menu.getButtonY() - Game.HEIGHT / 100 * 25 / 2,	Menu.getButtonWidth(), Menu.getButtonHeight());
		join.setBounds(Menu.getButtonX(), Menu.getButtonY() + Game.HEIGHT / 100 * 25 / 2, Menu.getButtonWidth(), Menu.getButtonHeight());
		field.setBounds(Menu.getButtonX(), Menu.getButtonY() + Game.HEIGHT / 100 * 15 / 2, Menu.getButtonWidth(), Menu.getButtonHeight() / 2);

		//Fuegt die Buttons der GUI hinzu
		add(back);
		add(field);
		add(create);
		add(join);

	}

	//Startet den Multiplayer Server
	@Override
	public void run() {
		try {
			new MultiplayerServer(Game.getWindowWidth(), Game.getWindowHeight()).start();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public static String getIp() {
		String ip;
		
		ip = field.getText();
		
		return ip;
		
	}
	
	protected static MaskFormatter createFormatter(String s) {
	    MaskFormatter formatter = null;
	    try {
	        formatter = new MaskFormatter(s);
	    } catch (java.text.ParseException exc) {
	        System.err.println("formatter is bad: " + exc.getMessage());
	        System.exit(-1);
	    }
	    return formatter;
	}
}
