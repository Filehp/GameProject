package game;
/**
 * Created by Chris on 11.05.2015.
 */
public class kreisberechnungen {

    public static void main(String[] args) {
        double radWinkel = 180.0/180.0*Math.PI;
        double x_koordinate = Math.cos(radWinkel)*40+150;
        double y_koordinate = Math.sin(radWinkel)*40+50;

        System.out.println(radWinkel);
        System.out.println(x_koordinate);
        System.out.println(y_koordinate);


    }
}
