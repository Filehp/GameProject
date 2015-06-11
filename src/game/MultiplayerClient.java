package game;

import java.io.*;
import java.net.Socket;

/**
 * Created by Chris on 11.06.2015.
 */
public class MultiplayerClient {

    public static void main(String[] argv) {
        new MultiplayerClient().start();
    }
    void start() {

        try {
            Socket socket = new Socket("localhost", 5000);

            InputStream is = socket.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(is);
            try {
                Canon get = (Canon) ois.readObject();
                get.getXY();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }


            OutputStream os = socket.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(os);
            Canon to = new Canon(150,200,15);
            oos.writeObject(to);
            to.getXY();


            oos.close();
            os.close();
            socket.close();


            } catch (IOException ex) {
                ex.printStackTrace();
            }

    }
}
