package socketSchool.ex2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread {
    public static void main(String[] args) throws IOException {

        int clientNbr = 1;

        try{
            ServerSocket server = new ServerSocket(45000);
            while (true){
                Socket client = server.accept();
                Thread t = new Thread(new AcceptClient(client, clientNbr));
                t.start();
                clientNbr ++;
            }
        }catch (IOException e){
            throw new RuntimeException(e);
            }
        }
}
