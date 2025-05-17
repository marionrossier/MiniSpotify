package clientSide.repoFront;

import clientSide.services.Cookies_SingletonPattern;
import commun.IAudioRepository;

import java.io.*;
import java.net.Socket;

public class FrontAudioRepo implements IAudioRepository {

    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int SERVER_PORT = 45001;

    @Override
    public InputStream getStream(String fileName) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             DataOutputStream out = new DataOutputStream(socket.getOutputStream());
             DataInputStream in = new DataInputStream(socket.getInputStream())) {

            out.writeUTF("getAudioFile");
            out.writeUTF(String.valueOf(Cookies_SingletonPattern.getInstance().getUserPseudonym()));
            out.writeUTF(Cookies_SingletonPattern.getInstance().getUserPassword());
            out.writeUTF(fileName);
            out.flush();

            int fileSize = in.readInt();
            byte[] fileBytes = new byte[fileSize];
            in.readFully(fileBytes);

            return new ByteArrayInputStream(fileBytes);

        } catch (IOException e) {
            throw new RuntimeException("Failed to load audio stream from server", e);
        }
    }
}