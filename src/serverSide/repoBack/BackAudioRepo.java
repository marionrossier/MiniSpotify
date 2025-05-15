package serverSide.repoBack;

import serverSide.entities.User;
import serverSide.repoLocal.UserLocalRepository;

import java.io.*;
import java.util.Optional;

public class BackAudioRepo {

    private static final String AUDIO_FOLDER =
            System.getProperty("user.home") + "/MiniSpotifyFlorentMarion/songsfiles/";
    private static final UserLocalRepository userRepo = new UserLocalRepository();

    public static byte[] handleGetAudioFile(DataInputStream in) throws IOException {
        String username = in.readUTF();
        String password = in.readUTF();
        String fileName = in.readUTF();

        Optional<User> user = userRepo.authenticate(username, password);
        if (user.isEmpty()) {
            return null;
        }

        File file = new File(AUDIO_FOLDER + "/" + fileName);
        if (!file.exists()) {
            return null;
        }

        return readAllBytes(file);
    }

    private static byte[] readAllBytes(File file) throws IOException {
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