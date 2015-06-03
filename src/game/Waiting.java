package game;
/**
 * Created by Chris on 05.05.2015.
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Arc2D;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;



    public class Waiting {

        public static void main(String[] args) {
            new Waiting();
        }

        public Waiting() {
            EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    }

                    JFrame frame = new JFrame("Testing");
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setLayout(new BorderLayout());
                    frame.add(new TestPane());
                    frame.pack();
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);
                }
            });
        }

        public class TestPane extends JPanel {

            private Timer paintTimer;
            private float cycle;
            private boolean invert = false;

            public TestPane() {
                paintTimer = new Timer(40, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        cycle += 0.05f;
                        if (cycle > 1f) {
                            cycle = 0f;
                            invert = !invert;
                        }
                        repaint();
                    }
                });
                paintTimer.setRepeats(true);
                setRuning(true);
            }

            public void setRuning(boolean running) {
                if (running) {
                    paintTimer.start();
                } else {
                    paintTimer.stop();
                }
            }

            public boolean isRunning() {
                return paintTimer.isRunning();
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(40, 40);
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (isRunning()) {
                    Graphics2D g2d = (Graphics2D) g.create();
                    g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
                    g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
                    g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
                    g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                    g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                    g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

                    int width = getWidth() - 1;
                    int height = getHeight() - 1;
                    int radius = Math.min(width, height);

                    int x = (width - radius) / 2;
                    int y = (height - radius) / 2;
                    int start = 0;
                    int extent = Math.round(cycle * 360f);

                    if (invert) {
                        start = extent;
                        extent = 360 - extent;
                    }

                    g2d.setColor(Color.RED);
                    g2d.fill(new Arc2D.Float(x, y, radius, radius, start, extent, Arc2D.PIE));
                    g2d.setColor(Color.YELLOW);
                    g2d.draw(new Arc2D.Float(x, y, radius, radius, start, extent, Arc2D.PIE));
                    g2d.dispose();
                }
            }
        }
    }

