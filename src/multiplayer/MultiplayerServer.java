package multiplayer;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Chris on 11.06.2015.
 */
public class MultiplayerServer {
    static int x;
    static int y;

    private boolean time=true;
    private int timer = 270000;
    private int minuten;
    private int sekunden;
    private long starttime;

    private int playerID;



    private int loadBar=0;
    private int missilnumber = 99;
    private int missileClipPlayer1;
    private int missileClipPlayer2;
    private  ArrayList<Missile> missilePlayer1 = new ArrayList<>();
    private  ArrayList<Missile> missilePlayer2 = new ArrayList<>();

    private boolean startSpokes = false;

    private Canon KanonePlayer1;
    private Canon KanonePlayer2;
    private Wheel rad;

    double faktorX;
    double faktorY;
    Socket socket;

    public MultiplayerServer(int x, int y){
        this.x = x;
        this.y = y;
    }


    public static void main(String[] argv) throws IOException {

            new MultiplayerServer(200,300).start();
        }

         void start() throws IOException {
             System.out.println("Server gestartet.");
             rad = new Wheel(this.x,this.y,4,2);
             rad.addSpokes();
             starttime=System.currentTimeMillis();


             try {
                 while (true) {


                     //Rad bewegen auf Player 1 begrenzen
                     rad.spinWheel();


                     InputStream is = null;
                     OutputStream os = null;
                     ServerSocket serverSocket = new ServerSocket(5000);
                     socket = serverSocket.accept();

                     //Daten empfangen

                     is = socket.getInputStream();
                     ObjectInputStream ois = new ObjectInputStream(is);
                     playerID = (int)ois.readObject();

                     if(playerID==2){
                        int  p2x = (int)ois.readObject();
                        int  p2y = (int)ois.readObject();

                         this.faktorX = this.x / p2x;
                         this.faktorY = this.y / p2y;
                     }else{
                         //just read
                         int  p2x = (int)ois.readObject();
                         int  p2y = (int)ois.readObject();
                     }

                     if(playerID==1) {
                         KanonePlayer1 = (Canon) ois.readObject();
                         missilePlayer1 = (ArrayList) ois.readObject();
                         missileClipPlayer1 = (int)ois.readObject();

                     }else{
                         KanonePlayer2 = (Canon) ois.readObject();
                         KanonePlayer2.aufloesungAnpassen(this.faktorX, this.faktorY, true);
                         missilePlayer2 = (ArrayList) ois.readObject();
                         for(Missile missile: missilePlayer2){
                             missile.aufloesungAnpassen(this.faktorX, this.faktorY, true);
                         }
                         missileClipPlayer2 = (int)ois.readObject();
                     }
                     System.out.println("Daten empfangen");





                     //Zeit wird berechnet und geprüft
                     long currentTime = System.currentTimeMillis() - starttime;
                     int currentTimeOutput = (int) (timer - currentTime);
                     minuten = (int) currentTimeOutput / 1000 / 60;
                     sekunden = (int) currentTimeOutput / 1000 % 60;
                     if (currentTime >= timer) {
                         time = false;
                         System.out.println("Timeout");
                     }

                    //Sieges bedingung
                     if (this.missileClipPlayer1 == 0 && missilePlayer1.size() == 0) {
                         System.out.println("Du hast gewonnen Player1");
                         System.out.println("Du hast verloren Player2");
                     }
                     if (this.missileClipPlayer2 == 0 && missilePlayer2.size() == 0) {
                         System.out.println("Du hast gewonnen Player2");
                         System.out.println("Du hast verloren Player1");
                     }

                     //Berechnungen Kollision

                     //Kollision abfangen Player1
                     if (missilePlayer1.size() > 0) {
                         for (int i = 0; i < missilePlayer1.size(); i++) {
                             //Auf Kollision mit rad prüfen

                             boolean Kollirad = missilePlayer1.get(i).collidesWithWheel(rad);

                             //Auf kollision mit Speiche prüfen
                             boolean Kollispeiche = missilePlayer1.get(i).coollidesWithSpokes(rad.getSpokesList());

                             //Kollision mit Rad
                             if (Kollirad && Kollispeiche == false) {
                                 //Was soll passieren wenn das Geschoss auf das Rad trifft?

                                 //Speiche wird dan der Stelle hinzugefügt
                                 rad.addSpokes(missilePlayer1.get(i).getX());
                                 //Geschoss wird entfernt
                                 missilePlayer1.remove(i);


                             } else {
                                 //Kollision mit Speiche
                                 if (Kollispeiche) {
                                     //Was soll passieren wenn das Geschoss auf eine Speiche trifft?
                                     //this.setBackground(Color.RED);

                                     System.out.println("Speiche getroffen! Player2 gewinnt");
                                     missilePlayer1.remove(i);
                                 }
                             }

                         }
                     }
                     //Kollision abfangen Player2
                     if (missilePlayer2.size() > 0) {
                         for (int i = 0; i < missilePlayer2.size(); i++) {
                             //Auf Kollision mit rad prüfen

                             boolean Kollirad = missilePlayer2.get(i).collidesWithWheel(rad);

                             //Auf kollision mit Speiche prüfen
                             boolean Kollispeiche = missilePlayer2.get(i).coollidesWithSpokes(rad.getSpokesList());

                             //Kollision mit Rad
                             if (Kollirad && Kollispeiche == false) {
                                 //Was soll passieren wenn das Geschoss auf das Rad trifft?

                                 //Speiche wird dan der Stelle hinzugefügt
                                 rad.addSpokes(missilePlayer2.get(i).getX());
                                 //Geschoss wird entfernt
                                 missilePlayer2.remove(i);


                             } else {
                                 //Kollision mit Speiche
                                 if (Kollispeiche) {
                                     //Was soll passieren wenn das Geschoss auf eine Speiche trifft?


                                     System.out.println("Speiche getroffen! Player1 gewinnt");
                                     missilePlayer1.remove(i);
                                 }
                             }

                         }


                         }


                     //Daten senden
                     os = socket.getOutputStream();
                     ObjectOutputStream oos = new ObjectOutputStream(os);

                     oos.writeObject(rad);
                     oos.writeObject(minuten);
                     oos.writeObject(sekunden);

                     //oos.writeObject(new String("another object from the client"));


                     System.out.println("Daten senden");



                     //Verbindungen schließen
                     oos.close();
                     os.close();
                     is.close();
                     socket.close();
                     serverSocket.close();


                     System.out.println("Läuft");
                     }

                 }
                 catch(IOException ex){
                     ex.printStackTrace();
                 } catch (ClassNotFoundException e) {
                 e.printStackTrace();
             }


         }

}

