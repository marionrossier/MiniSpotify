package clientSide.repoFront;

import middle.IAudioRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utilsAndFakes.CommuneMethods;

import java.io.*;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

public class FrontAudioRepoSocketTest extends CommuneMethods {

    static IAudioRepository audioRepo;
    static Thread audioServerThread;

    private static final String TEST_FILE_NAME = "Believer - Imagine Dragons - Evolve - 2017 - Pop rock _ Alt rock - 0324.mp3";
    private static final File SOURCE_FILE = new File(System.getProperty("user.home") +
            "/MiniSpotifyFlorentMarion/songsfiles/" + TEST_FILE_NAME);

    public FrontAudioRepoSocketTest() throws IOException {
    }

    @BeforeAll
    static void setup() throws IOException {

        try (Socket socket = new Socket("127.0.0.1", 45001)) {
            System.out.println("✅ Audio server already running.");
        } catch (IOException e) {
            audioServerThread = new Thread(() -> {
                try {
                    audioSocketServer.main();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            });
            audioServerThread.setDaemon(true);
            audioServerThread.start();
            try {
                Thread.sleep(1000); // attendre le démarrage
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }

        audioRepo = new FrontAudioRepo();
    }

    @Test
    void getStream_shouldReturnAudioFileMatchingSource() throws IOException {
        // Arrange
        assertTrue(SOURCE_FILE.exists(), "⚠️ Le fichier test_audio.mp3 doit exister localement.");

        try (InputStream expected = new FileInputStream(SOURCE_FILE);
             InputStream actual = audioRepo.getStream(TEST_FILE_NAME)) {

            assertNotNull(actual, "❌ Le flux retourné est nul");

            byte[] expectedBytes = expected.readAllBytes();
            byte[] actualBytes = actual.readAllBytes();

            // Assert
            assertArrayEquals(expectedBytes, actualBytes, "❌ Le contenu du fichier reçu est différent !");
        }
    }
}