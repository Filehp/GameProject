package multiplayer;

import entity.Canon;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Chris on 11.06.2015.
 */
public class MultiplayerServer {

        public static void main(String[] argv) {

            new MultiplayerServer().start();
        }

         void start() {
            System.out.println("Server gestartet.");
            try {
                while(true) {
                    ServerSocket serverSocket = new ServerSocket(5000);
                    Socket socket = serverSocket.accept();

                    OutputStream os = socket.getOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(os);

                    Canon to = new Canon(200,300,15);
                    oos.writeObject(to);
                    to.getXY();

                    InputStream is = socket.getInputStream();
                    ObjectInputStream ois = new ObjectInputStream(is);
                    try {
                        to = (Canon) ois.readObject();
                        to.getXY();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }


                    System.out.println("Läuft!");


                    socket.close();
                    serverSocket.close();
                }
            }
        catch (IOException ex) {
            ex.printStackTrace();
        }

    }


}
