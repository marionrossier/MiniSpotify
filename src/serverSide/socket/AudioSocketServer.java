package serverSide.socket;

import serverSide.repoBack.BackAudioRepo;
import serverSide.repoLocal.UserLocalRepository;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class AudioSocketServer {

    private final int PORT = 45001;
    private final String AUDIO_FOLDER =
            System.getProperty("user.home") + "/MiniSpotifyFlorentMarion/songsfiles/";

    private final UserLocalRepository userRepo = new UserLocalRepository();
    private final BackAudioRepo backAudioRepo;

    public void main() {
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
                out.writeInt(0); // Auth échouée ou fichier introuvable
                return;
            }

            out.writeInt(bytes.length);
            out.write(bytes);

        } catch (IOException e) {
            System.err.println("❌ Audio handler error: " + e.getMessage());
        }
    }

    private byte[] readAllBytes(File file) throws IOException {
        try (InputStream fileIn = new FileInputStream(file);
             ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {

            byte[] temp = new byte[4096];
            int read;
            while ((read = fileIn.read(temp)) != -1) {
                buffer.write(temp, 0, read);
            }
            return buffer.toByteArray();
        }
    }
}