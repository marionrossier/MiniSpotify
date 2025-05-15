package clientSide.repoFront;

import middle.IAudioRepository;

import java.io.*;
import java.net.Socket;

public class FrontAudioRepo implements IAudioRepository {

    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int SERVER_PORT = 45001; // ⚠️ à séparer si tu veux dissocier socket JSON et audio
    private static final String USERNAME = "marion";
    private static final String PASSWORD = "ipmUvIFpi5NU/dhSPJuy49ikJM9yHSWfzKict97V/gU=";

    @Override
    public InputStream getStream(String fileName) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             DataOutputStream out = new DataOutputStream(socket.getOutputStream());
             DataInputStream in = new DataInputStream(socket.getInputStream())) {

            // ✅ Envoie une requête de fichier audio (simple protocole)
            out.writeUTF("getAudioFile");
            out.writeUTF(USERNAME);
            out.writeUTF(PASSWORD);
            out.writeUTF(fileName);
            out.flush();

            // ✅ Lecture du fichier en mémoire
            int fileSize = in.readInt(); // Taille du fichier en octets
            byte[] fileBytes = new byte[fileSize];
            in.readFully(fileBytes);

            return new ByteArrayInputStream(fileBytes);

        } catch (IOException e) {
            throw new RuntimeException("Failed to load audio stream from server", e);
        }
    }
}