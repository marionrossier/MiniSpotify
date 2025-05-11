package socketSchool.ex1;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(45000);
        Socket client = server.accept();
        String clientFileName = "data";
        String clientExtension = ".png";

        //Utiliser pour recevoir
        DataInputStream din = new DataInputStream(client.getInputStream()); //Regarde ce qui se trouve dans le socket
        FileOutputStream fos = new FileOutputStream("c://MiniSpotify//received//"+clientFileName+clientExtension);

        int bytesRead;
        byte[] buffer = new byte[4096];//taille maximum d'un buffer
        while ((bytesRead = din.read(buffer)) != -1){ //tant qu'il y a encore des éléments dans le socket, j'écris.
            fos.write(buffer, 0, bytesRead);
        }

    }
}
