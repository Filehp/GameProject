package game;
/**
 * Created by Chris on 05.05.2015.
 */



import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;




import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;





public class frame extends JFrame implements ActionListener {


    private JButton schliessen;
    private JButton einstellung;
    private JButton info;
    private JButton ende;




    public static void main (String[]args) throws Exception{



        frame frame = new frame("UI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400,400);




        frame.setLayout(null);
        frame.setVisible(true);



    }

    public frame(String title) {

        super(title);

        schliessen = new JButton("Spiel starten");
        schliessen.setBounds(120, 40, 160, 40);
        schliessen.addActionListener(this);
        add(schliessen);

        einstellung = new JButton("Einstellungen");
        einstellung.setBounds(120, 120, 160, 40);
        einstellung.addActionListener(this);
        add(einstellung);


        info = new JButton("Credits");
        info.setBounds(120, 200, 160, 40);
        info.addActionListener(this);
        add(info);


        ende = new JButton("Beenden");
        ende.setBounds(120, 280, 160, 40);
        ende.addActionListener(this);
        add(ende);



    }


    public void actionPerformed(ActionEvent e){

        if (e.getSource() == schliessen){
            fenster();
        }

        if (e.getSource() == info){
            Object[] options = { "OK"};
            JOptionPane.showOptionDialog(null, "Programmiert von JaDaHB1", "Information",

                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,

                    null, options, options[0]);
        }

        if (e.getSource() == einstellung){
            Object[] options = { "OK"};
            JOptionPane.showOptionDialog(null, "Hier können sie Einstellungen vornehmen", "Information",

                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,

                    null, options, options[0]);
        }


        if (e.getSource() == ende){
            System.exit(0);


        }
    }


    public static void fenster(){


        JFrame fenster = new JFrame("Game");
        fenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenster.setSize(650,350);

    }
}







