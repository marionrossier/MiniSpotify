package serverSide.socket;

import serverSide.StockageService;
import serverSide.repoBack.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class AudioSocketServer {

    private final int PORT = 45001;
    private final BackAudioRepo backAudioRepo;
    private final StockageService stockageService = new StockageService();

    public void audioSocketMain() {
        System.out.println("🎵 AudioSocketServer started on port " + PORT);
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(() -> handleAudioRequest(clientSocket)).start();
            }
        } catch (IOException e) {
            System.err.println("❌ Server error: " + e.getMessage());
        }
    }

    public AudioSocketServer(BackAudioRepo backAudioRepo){
        this.backAudioRepo = backAudioRepo;
    }

    private void handleAudioRequest(Socket socket) {
        try (DataInputStream in = new DataInputStream(socket.getInputStream());
             DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {

            String command = in.readUTF();
            if (!"getAudioFile".equals(command)) {
                out.writeInt(0);
                return;
            }

            byte[] bytes = backAudioRepo.handleGetAudioFile(in);
            if (bytes == null) {
                out.writeInt(0);
                return;
            }

            out.writeInt(bytes.length);
            out.write(bytes);

        } catch (IOException e) {
            System.err.println("❌ Audio handler error: " + e.getMessage());
        }
    }
}