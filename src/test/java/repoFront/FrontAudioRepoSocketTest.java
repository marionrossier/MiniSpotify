package repoFront;

import clientSide.services.*;
import common.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utilsAndFakes.*;

import java.io.*;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

public class FrontAudioRepoSocketTest {

    private IAudioRepository audioRepo;
    private Thread audioServerThread;
    private TestHelper testHelper;
    private DependencyProvider dependencyProvider;

    private final String TEST_FILE_NAME = "Believer - Imagine Dragons - Evolve - 2017 - Pop rock _ Alt rock - 0324.mp3";
    private final File SOURCE_FILE = new File(System.getProperty("user.home") +
            "/MiniSpotifyFlorentMarion/songsfiles/" + TEST_FILE_NAME);

    @BeforeEach
    void setup() {
        testHelper = new TestHelper(45000);
        dependencyProvider = testHelper.dependencyProvider;
        dependencyProvider.populateLocalUsers();
        Cookies_SingletonPattern.setInstance(232928320, "marion", "hash");

        try (Socket socket = new Socket("127.0.0.1", 45001)) {
            System.out.println("✅ Audio server already running.");
        } catch (IOException e) {
            audioServerThread = new Thread(() -> {
                try {
                    dependencyProvider.audioSocketServer.audioSocketMain();
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
        audioRepo = dependencyProvider.frontAudioRepo;
    }

    @AfterEach
    void tearDown() {
        dependencyProvider.cleanUp();
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