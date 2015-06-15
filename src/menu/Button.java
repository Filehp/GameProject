package menu;

import java.awt.AlphaComposite;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.JButton;

public class Button extends JButton {

	float alpha = .5f;

	private static final long serialVersionUID = 1L;

	public Button(Icon ic) {
		super(ic);

		setFocusPainted(false);
		setBorderPainted(false);

		addMouseListener(new ML());

	}

	@Override
	public void paintComponent(java.awt.Graphics g) {
		super.paintComponent(g);
		java.awt.Graphics2D g2 = (java.awt.Graphics2D) g;
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				alpha));
		super.paintComponent(g2);
	}

	public float getAlpha() {
		return alpha;
	}

	public void setAlpha(float alpha) {
		this.alpha = alpha;
		repaint();
	}

	public class ML extends MouseAdapter {
		public void mouseExited(MouseEvent me) {
			new Thread(new Runnable() {
				public void run() {
					for (float i = 1f; i >= .5f; i -= .03f) {
						setAlpha(i);
						try {
							// Thread.sleep(1);
						} catch (Exception e) {
						}
					}
				}
			}).start();
		}

		public void mouseEntered(MouseEvent me) {
			new Thread(new Runnable() {
				public void run() {
					for (float i = .5f; i <= 1f; i += .03f) {
						setAlpha(i);

						try {
							// Thread.sleep(1);
						} catch (Exception e) {
						}
					}
				}
			}).start();
		}

		public void mousePressed(MouseEvent me) {
			new Thread(new Runnable() {
				public void run() {
					for (float i = 1f; i >= 0.5f; i -= .1f) {
						setAlpha(i);
						try {
							//Thread.sleep(1);
						} catch (Exception e) {
						}
					}
				}
			}).start();
		}
	}
}
