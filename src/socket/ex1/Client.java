package socket.ex1;

import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {

        Socket client = new Socket("127.0.0.1", 45000);
        System.out.println("Good");
        String clientFileName = "data";
        String clientExtension = ".png";

        //Utiliser pour recevoir
        DataOutputStream dos = new DataOutputStream(client.getOutputStream()); //Regarde ce qui se trouve dans le socket
        FileInputStream fis = new FileInputStream("c://MiniSpotify//received//"+clientFileName+clientExtension);


        int bytesRead;
        byte[] buffer = new byte[4096];//taille maximum d'un buffer
        while ((bytesRead = fis.read(buffer)) != -1){ //tant qu'il y a encore des éléments dans le file, j'écris.
            dos.write(buffer, 0, bytesRead);
        }

    }
}
