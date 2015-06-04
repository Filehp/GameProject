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

		back.setBounds(Menu.getButtonX(), Menu.getButtonY() + Game.HEIGHT / 100 * 75 / 2, Menu.getButtonWidth(), Menu.getButtonHeight());

		add(back);

	}

}
