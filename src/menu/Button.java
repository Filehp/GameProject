package menu;

import java.awt.AlphaComposite;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.JButton;

/**
 * Klasse fuer die Buttons in den Menues
 */

public class Button extends JButton {

	// Transparenz
	float alpha = .5f;

	// Constructor
	public Button(Icon ic) {
		super(ic);

		setFocusPainted(false);
		setBorderPainted(false);

		addMouseListener(new ML());
	}

	// Stellt die Buttons und Transparenz dar
	@Override
	public void paintComponent(java.awt.Graphics g) {
		super.paintComponent(g);
		java.awt.Graphics2D g2 = (java.awt.Graphics2D) g;
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				alpha));
		super.paintComponent(g2);
	}

	// Bekommt die derzeitige Transparenz der Buttons
	public float getAlpha() {
		return alpha;
	}

	// Setzt eine Transparenz der Buttons
	public void setAlpha(float alpha) {
		this.alpha = alpha;
		repaint();
	}

	// Maus Aktionen
	public class ML extends MouseAdapter {

		// Wenn die Maus den Button verlaesst
		public void mouseExited(MouseEvent me) {

			// Hover over Effekt, Transparenz
			new Thread(new Runnable() {
				public void run() {
					for (float i = 1f; i >= .5f; i -= .03f) {
						setAlpha(i);
					}
				}
			}).start();
		}

		// Wenn die Maus einen Button beruehrt
		public void mouseEntered(MouseEvent me) {

			// Hover over Effekt, Transparenz
			new Thread(new Runnable() {
				public void run() {

					for (float i = .5f; i <= 1f; i += .03f) {
						setAlpha(i);
					}
				}
			}).start();
		}

		// Wenn die Maus geklickt wird
		public void mousePressed(MouseEvent me) {

			// Hover over Effekt, Transparenz
			new Thread(new Runnable() {
				public void run() {
					for (float i = 1f; i >= 0.5f; i -= .1f) {
						setAlpha(i);
					}
				}
			}).start();
		}
	}
}
