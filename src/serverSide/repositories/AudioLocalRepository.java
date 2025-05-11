package serverSide.repositories;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class AudioLocalRepository implements IAudioRepository {

    private final String folderPath;

    public AudioLocalRepository() {
        this(System.getProperty("user.home") + "/MiniSpotifyFlorentMarion/songsfiles/");
    }

    public AudioLocalRepository(String folderPath) {
        this.folderPath = folderPath;
    }

    @Override
    public InputStream getStream(String fileName){
        String filePath = folderPath + "/" + fileName;
        try {
            return new BufferedInputStream(new FileInputStream(filePath));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
