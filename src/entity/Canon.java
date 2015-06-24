package entity;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Die Kanonenklasse
 * Created by Chris on 09.05.2015.
 */
public class Canon  implements Serializable {

    /**
     * Umegebungsvariabel
     */
    private int missileCounter;                     //Menge der GEschosse der Kanone
    private int canonX;                             // X Position der Kanone
    private int canonY;                             // Y Position der Kanone
    private int canonWigth;                         // Breite der Kanone
    private int canonHeigth;                        // Höhe der Kanone
    private int xPositionMissile;                   // X Position der Missile
    private int yPositionMissile;                   // Y Position der Missile
    private int grenzeLinks;                        // Linke Begrenzung der Kanone auf dem Bildschirm
    private int grenzeRechts;                       // REchte Begrenzung der Kanone auf dem Bildschirm
    private int radiusMissile;                      // Radius der Missile
    private Color canonColor;                       // FArbe der Kanone


    /**
     *
     * Constructor
     * wird die berechnete Auflösung übergeben um die Groesse der Kanone
     * zu brechnen. Ausserdem Farbe und Anzahl der Missile
     */

    public Canon(int x, int y, int missileCount){
        System.out.println(x + "and"+ y);
        this.canonX = x/100*48;
        this.canonY = y/100*75;
        this.canonWigth = x/100*10;
        this.canonHeigth = x/100*3;
        this.grenzeLinks = x/100*40;
        this.grenzeRechts = x/100*65;
        this.radiusMissile = x/100*3;
        this.missileCounter = missileCount;
        this.canonColor = Color.BLACK;

    }


    /**
     *
     * Lädt die Grafik für die Kanone Farbe sowie Positon und groesse
     */
    public void canonLoad(Graphics canon) {
        canon.setColor(canonColor);
        canon.fillRect(this.canonX, this.canonY, canonWigth, canonHeigth);
    }

    /**
     *
     * Bewegt die Kanone und bekommt die Richtung übergeben left oder right
     */
    public void moveCanon(String direction){

        /**
         * ist die übergegeben Richtung links werden 5 px von der X Position abgezogen
         */
        if(direction.equals("left")){
            this.canonX = this.canonX - 5;

            // Begrenzung auf der linken Seite
            if(this.canonX <= grenzeLinks){
                this.canonX = grenzeLinks;
            }

        }
        /**
         * ist die übergegeben Richtung links werden 5 px von der X Position zugepackt
         */
        if(direction.equals("right")) {
            this.canonX = this.canonX + 5;

            //Begrenzung auf der rechten Seite
            if(this.canonX >= (grenzeRechts-canonWigth)){
                this.canonX = grenzeRechts-canonWigth;
            }

        }
    }

    /**
     *
     * Getter für die aktuelle Position der Kanone
     * Wichtig für den Abschuss einer Missile!!!!
     */
    public int getAbschussPositionX(){ return this.xPositionMissile = this.canonX +(canonWigth/2)- radiusMissile /2; }
    public int getAbschussPositionY(){return this.yPositionMissile = this.canonY -(canonHeigth/2);}

    /**
     *
     * Abschuss Methode für eine Missile
     */
    public int shotMissile(Graphics missileGrafic, ArrayList<Missile> missile){

            int missileindex = 99;
        /**
         * Lässt die Missile sich bewegen und die neue Position berechnen
         *
         */
            for (Missile missil:missile) {
                missil.loadMissile(missileGrafic);
                int missilePosition = missil.moveMissile();

                /**
                 * verlässt die Missile das fenster wird diese entfernt durch das setzen von index 99
                 */
                if (missilePosition <= 0) {
                   missileindex = missile.indexOf(missil);
                }else {missileindex=99;}
            }

            return missileindex;

    }

    /**
     * Methode zum Anpassen der Auflösung, damit Player 2 richtig angezeigt wird
     */
    public void aufloesungAnpassen(double faktorX, double faktorY, boolean operator){
        /**
         * operator bestimmt ob der Faktor multipliziert oder geteilt wird
         * true = miltiplizieren
         * false = teilen
         * Das wird benötigt, wenn der 2. Player eine anderen Auflösung hat
         */


        /**
         * true ist die Auflösung umwandeln von Player 1 zu Player 2
         */
        if(operator==true) {
            this.canonX = (int) (this.canonX * faktorX);
            this.canonY = (int) (this.canonY * faktorY);
            this.canonWigth = (int) (this.canonWigth * faktorX);
            this.canonHeigth = (int) (this.canonHeigth * faktorX);
            this.grenzeLinks = (int) (this.grenzeLinks * faktorX);
            this.grenzeRechts = (int) (grenzeRechts * faktorX);
            this.radiusMissile = (int) (radiusMissile * faktorX);
        }

        /**
         * false ist die Auflösung umwandeln von Player 2 zu Player 1
         */
        if(operator==false) {
            this.canonX = (int) (this.canonX / faktorX);
            this.canonY = (int) (this.canonY / faktorY);
            this.canonWigth = (int) (this.canonWigth / faktorX);
            this.canonHeigth = (int) (this.canonHeigth / faktorX);
            this.grenzeLinks = (int) (this.grenzeLinks / faktorX);
            this.grenzeRechts = (int) (grenzeRechts / faktorX);
            this.radiusMissile = (int) (radiusMissile / faktorX);
        }
    }

    //Gibt die aktuelle Anzahl an missile wieder
    public int getMissileCounter(){
        return this.missileCounter;
    }

    //Entfernt die geschossenen Missiel aus dem Magazin
    public void shotedmissile(){
        this.missileCounter--;
    }

    //Setz die Farbe der Kanone auf Rot
    public void setColorRed(){
        canonColor=Color.RED;
    }
}