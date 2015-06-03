package newMenu;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

public class MultiplayerMenu extends JComponent {

	private static final long serialVersionUID = 1L;
	Icon backIcon = new ImageIcon("resources/Back.png");

	private Button back = new Button(backIcon);

	public MultiplayerMenu() {

		back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Game.changePanel("menu", MultiplayerMenu.this);

			}
		});
	}

	@Override
	protected void paintComponent(Graphics g) {

		back.setBounds(Game.WIDTH / 2 - 150, Game.HEIGHT / 2 + 300, 275, 55);

		add(back);

	}

}
