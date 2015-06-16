package menu;

import game.Game;
import multiplayer.MultiplayerClient;
import multiplayer.MultiplayerServer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JTextField;

public class MultiplayerMenu extends JComponent implements Runnable {

	private static final long serialVersionUID = 1L;
	Icon backIcon = new ImageIcon("resources/Back.png");
	Icon createIcon = new ImageIcon("resources/Create.png");
	Icon joinIcon = new ImageIcon("resources/Join.png");

	private Button back = new Button(backIcon);
	private Button create = new Button(createIcon);
	private Button join = new Button(joinIcon);
	
	private JTextField field = new JTextField("Please enter IP to connect");

	public MultiplayerMenu() {

		back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Game.changePanel("menu", MultiplayerMenu.this);

			}
		});
		
		field.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				field.setText(""); // Löscht den Inhalt des Feldes
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

	@Override
	protected void paintComponent(Graphics g) {

		back.setBounds(Menu.getButtonX(), Menu.getButtonY() + Game.HEIGHT / 100 * 75 / 2, Menu.getButtonWidth(), Menu.getButtonHeight());
		create.setBounds(Menu.getButtonX(), Menu.getButtonY() - Game.HEIGHT / 100 * 25 / 2,	Menu.getButtonWidth(), Menu.getButtonHeight());
		join.setBounds(Menu.getButtonX(), Menu.getButtonY() + Game.HEIGHT / 100 * 25 / 2, Menu.getButtonWidth(), Menu.getButtonHeight());
		field.setBounds(Menu.getButtonX(), Menu.getButtonY() + Game.HEIGHT / 100 * 15 / 2, Menu.getButtonWidth(), Menu.getButtonHeight() / 2);

		add(back);
		add(field);
		add(create);
		add(join);

	}

	@Override
	public void run() {
		try {
			new MultiplayerServer(Game.getWindowWidth(), Game.getWindowHeight()).start();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
