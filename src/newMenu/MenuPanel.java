package newMenu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MenuPanel extends JPanel{
	
	private JButton btnExit = new JButton("Exit");
    private JButton btnNewGame = new JButton("New Game");
    private BullsEyePanel gamePanel;
    private JFrame parent;

    MenuPanel(JFrame parent) {
        this.gamePanel = new BullsEyePanel(this);
        this.parent = parent;
        this.setBackground(Color.black);
        this.setFocusable(true);
        btnNewGame.addActionListener(new newGameListener());
        btnExit.addActionListener(new exitListener());
        this.add(btnNewGame);
        this.add(btnExit);
        this.setVisible(true);
    }

    public void restore() {
        parent.remove(gamePanel);
        parent.add(this, BorderLayout.NORTH);
        parent.pack();
        parent.setLocationRelativeTo(null);
    }

    private class exitListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            System.exit(0);
        }
    }

    private class newGameListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            parent.remove(MenuPanel.this);
            parent.add(gamePanel, BorderLayout.CENTER);
            parent.pack();
            parent.setLocationRelativeTo(null);
            gamePanel.requestFocus();
        }
    }
}

class BullsEyePanel extends JPanel {

    private MenuPanel menuPanel;

    public BullsEyePanel(MenuPanel menu) {
        this.menuPanel = menu;
        this.setFocusable(true);
        this.addKeyListener(new TAdapter());
        this.setPreferredSize(new Dimension(320, 240)); // placeholder
        this.setVisible(true);
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent code) {
            if (code.getKeyCode() == KeyEvent.VK_ESCAPE) {
                menuPanel.restore();
            }
        }
    }

}
