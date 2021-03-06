package multiplayer;
import entity.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JComponent;

/**
 * Created by Chris on 11.06.2015.
 */
public class MultiplayerServer extends JComponent {
    /**
     * Aufl�sungsvariabeln
     */
    static int x;
    static int y;
    private double faktorX;
    private double faktorY;

    /**
     * Allgemeine Spielvariabeln
     */
    private int playerID;
    Socket socket;
    private boolean allPlayer = false;
    private boolean waitGameStart = true;

    /**
     * Variabeln Player1
     */
    private Canon KanonePlayer1;
    private int victoryPlayer1 = 0;
    private boolean player1Finished;
    private int missileClipPlayer1;
    private  ArrayList<Missile> missilePlayer1 = new ArrayList<>();

    /**
     * Variabeln Player2
     */
    private Canon KanonePlayer2;
    private int victoryPlayer2 = 0;
    private boolean player2Finished;
    private int missileClipPlayer2;
    private  ArrayList<Missile> missilePlayer2 = new ArrayList<>();
    /**
     * Radvariabeln
     */
    private Wheel rad;
    private boolean startSpokes = false;

    /**
     * Constructor f�r den Server es wird die Aufl�sung von Player 1 uebergeben
     */
    public MultiplayerServer(int x, int y){
        this.x = x;
        this.y = y;
    }

    /**
     * steuer die Laufzeit des Servers
     */
    private boolean serverrun = true;

	public void start() throws IOException {
             System.out.println("Server gestartet.");


             try {
                 while (serverrun) {

                     /**
                      * Server wird gestartet
                      */
                     InputStream is = null;
                     OutputStream os = null;

                     /**
                      * Socket erzeugen
                      */
                     ServerSocket serverSocket = new ServerSocket(5000);
                     socket = serverSocket.accept();

                     /**Daten empfangen
                      * es wird unterschieden anhand der PlayerID ob Daten von
                      * Player 1 oder 2 kommen und gesendt werden.
                      */
                     is = socket.getInputStream();
                     ObjectInputStream ois = new ObjectInputStream(is);
                     playerID = (int)ois.readObject();

                     if(playerID==2){
                        allPlayer = true;
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

                     if(playerID==1) {
                         player1Finished = (boolean) ois.readObject();
                     }else {
                         player2Finished = (boolean) ois.readObject();
                     }
                     System.out.println("Daten empfangen");

                     /**
                      * GameStart: das Spiel startet wenn gameStart auf true und allPlayer ebenfalls auf true ist
                      */
                    if (waitGameStart && allPlayer) {
                        rad = new Wheel(this.x, this.y, 4, 2);
                        rad.addSpokes();
                        waitGameStart=false;
                    }
                     if(allPlayer) {
                         KanonePlayer2.setColorRed();
                         //Rad bewegen auf Player 1 begrenzen
                         rad.spinWheel();

                         //Siegesbedingung
                         if (this.missileClipPlayer1 == 0 && missilePlayer1.size() == 0) {
                             /**
                              * 1 = SPiel gewonnen, -1 = Speil verloren, 0 = Spiel la�ft
                              */
                             this.victoryPlayer1 = 1;
                             this.victoryPlayer2 = -1;
                             //serverrun = false;

                         }
                         if (this.missileClipPlayer2 == 0 && missilePlayer2.size() == 0) {
                             /**
                              * 1 = SPiel gewonnen, -1 = Speil verloren, 0 = Spiel la�ft
                              */
                             this.victoryPlayer1 = -1; //Spieler 1 verliert
                             this.victoryPlayer2 = 1;  //Spieler 2 gewinnt
                            // serverrun = false; //Server wird gestoppt

                         }

                         //Berechnungen Kollision

                         //Kollision abfangen Player1
                         if (missilePlayer1.size() > 0) {
                             for (int i = 0; i < missilePlayer1.size(); i++) {
                                 //Auf Kollision mit rad pr�fen

                                 boolean Kollirad = missilePlayer1.get(i).collidesWithWheel(rad);

                                 //Auf kollision mit Speiche pr�fen
                                 boolean Kollispeiche = missilePlayer1.get(i).coollidesWithSpokes(rad.getSpokesList());

                                 //Kollision mit Rad
                                 if (Kollirad && Kollispeiche == false) {
                                     //Was soll passieren wenn das Geschoss auf das Rad trifft?

                                     //Speiche wird dan der Stelle hinzugef�gt
                                     rad.addSpokes(missilePlayer1.get(i).getX());
                                     //Geschoss wird entfernt
                                     missilePlayer1.remove(i);


                                 } else {
                                     //Kollision mit Speiche
                                     if (Kollispeiche) {
                                         //Was soll passieren wenn das Geschoss auf eine Speiche trifft?
                                         //this.setBackground(Color.RED);
                                         /**
                                          * 1 = SPiel gewonnen, -1 = Speil verloren, 0 = Spiel la�ft
                                          */
                                         this.victoryPlayer1 = -1;
                                         this.victoryPlayer2 = 1;
                                         //serverrun = false;
                                         System.out.println("Speiche getroffen! Player2 gewinnt");
                                         missilePlayer1.remove(i);
                                     }
                                 }

                             }
                         }
                         //Kollision abfangen Player2
                         if (missilePlayer2.size() > 0) {
                             for (int i = 0; i < missilePlayer2.size(); i++) {
                                 //Auf Kollision mit rad pr�fen

                                 boolean Kollirad = missilePlayer2.get(i).collidesWithWheel(rad);

                                 //Auf kollision mit Speiche pr�fen
                                 boolean Kollispeiche = missilePlayer2.get(i).coollidesWithSpokes(rad.getSpokesList());

                                 //Kollision mit Rad
                                 if (Kollirad && Kollispeiche == false) {
                                     //Was soll passieren wenn das Geschoss auf das Rad trifft?

                                     //Speiche wird dan der Stelle hinzugef�gt
                                     rad.addSpokes(missilePlayer2.get(i).getX());
                                     //Geschoss wird entfernt
                                     missilePlayer2.remove(i);


                                 } else {
                                     //Kollision mit Speiche
                                     if (Kollispeiche) {
                                         //Was soll passieren wenn das Geschoss auf eine Speiche trifft?
                                         /**
                                          * 1 = SPiel gewonnen, -1 = Speil verloren, 0 = Spiel la�ft
                                          */
                                         this.victoryPlayer1 = 1;
                                         this.victoryPlayer2 = -1;
                                         //serverrun = false;
                                         System.out.println("Speiche getroffen! Player1 gewinnt");
                                         missilePlayer2.remove(i);
                                     }
                                 }

                             }


                         }

                     }

                     /**
                      *  Daten senden
                      */

                     os = socket.getOutputStream();
                     ObjectOutputStream oos = new ObjectOutputStream(os);
                     if (playerID == 1) {
                         oos.writeObject(rad);
                     } else {
                         rad.aufloesungAnpassen(faktorX, faktorY, false);
                         oos.writeObject(rad);
                         rad.aufloesungAnpassen(faktorX, faktorY, true);
                     }

                     if (playerID == 1) {
                         oos.writeObject(KanonePlayer2);
                         oos.writeObject(missilePlayer1);
                         oos.writeObject(missilePlayer2);
                     } else {
                         KanonePlayer1.aufloesungAnpassen(faktorX, faktorY, false);
                         oos.writeObject(KanonePlayer1);
                         KanonePlayer1.aufloesungAnpassen(faktorX, faktorY, true);
                         for (Missile missile : missilePlayer2) {
                             missile.aufloesungAnpassen(this.faktorX, this.faktorY, false);
                         }
                         oos.writeObject(missilePlayer2);
                         for (Missile missile : missilePlayer2) {
                             missile.aufloesungAnpassen(this.faktorX, this.faktorY, true);
                         }

                         for (Missile missile : missilePlayer1) {
                             missile.aufloesungAnpassen(this.faktorX, this.faktorY, false);
                         }
                         oos.writeObject(missilePlayer1);
                         for (Missile missile : missilePlayer1) {
                             missile.aufloesungAnpassen(this.faktorX, this.faktorY, true);
                         }
                     }
                     oos.writeObject(waitGameStart);
                     if (playerID == 1) {
                         oos.writeObject(victoryPlayer1);
                     }else{
                         oos.writeObject(victoryPlayer2);
                     }
                     System.out.println("Daten senden");



                     //Verbindungen schlie�en
                     oos.close();
                     os.close();
                     is.close();
                     socket.close();
                     serverSocket.close();

                     /**
                      * Server runterfahren (beenden der Schleife)
                      */
                     if (player1Finished && player2Finished){
                         //serverrun = false;
                     }

                     }

                 }
                 catch(IOException ex){
                     ex.printStackTrace();
                 } catch (ClassNotFoundException e) {
                 e.printStackTrace();
             }


         }

}

