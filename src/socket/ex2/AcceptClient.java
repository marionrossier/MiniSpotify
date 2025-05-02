package socket.ex2;

import java.net.Socket;

public class AcceptClient implements Runnable{

    private Socket client;
    private int clientNbr;


    public AcceptClient(Socket client, int clientNbr) {
        this.client = client;
        this.clientNbr = clientNbr;
    }

    public void run (){
        try {
            System.out.println("Client : "+ clientNbr + " accepted.");
            Thread.sleep(3000); //TODO : changer ceci pour envoyer les fichiers MP3 = faire la compression avec le JLayer
            client.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
