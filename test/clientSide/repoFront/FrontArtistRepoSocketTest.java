package clientSide.repoFront;

import clientSide.services.Cookies_SingletonPattern;
import middle.IArtistRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import serverSide.entities.Artist;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FrontArtistRepoSocketTest {

    static IArtistRepository repo;
    static Thread serverThread;

    @BeforeAll
    static void startServerAndSetupRepo() {
        try {
            new java.net.Socket("127.0.0.1", 45000).close();
            System.out.println("✅ Serveur déjà lancé.");
        } catch (Exception e) {
            serverThread = new Thread(() -> {
                try {
                    serverSide.socket.SocketServer.main(null);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            });
            serverThread.setDaemon(true);
            serverThread.start();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
        Cookies_SingletonPattern.setUser(232928320);
        repo = new FrontArtistRepo();
    }

    @Test
    void getArtistById_shouldReturnExpectedArtist() {
        // Arrange
        int artistId = 960571432;

        // Act
        Artist artist = repo.getArtistById(artistId);

        // Assert
        assertNotNull(artist);
        assertEquals(artistId, artist.getArtistId());
    }

    @Test
    void getArtistByName_shouldReturnExpectedArtist() {
        // Arrange
        String artistName = "Amy Winehouse";

        // Act
        Artist artist = repo.getArtistByName(artistName);

        // Assert
        assertNotNull(artist);
        assertEquals(artistName, artist.getArtistName());
    }

    @Test
    void getAllArtists_shouldIncludeKnownArtist() {
        // Arrange

        // Act
        List<Artist> allArtists = repo.getAllArtists();

        // Assert
        assertNotNull(allArtists);
        assertTrue(allArtists.stream().anyMatch(a -> a.getArtistName().equals("Amy Winehouse")));
    }

    @Test
    void getArtistBySongId_shouldReturnExpectedArtist() {
        // Arrange
        int songId = 1108071776; // Rehab

        // Act
        Artist artist = repo.getArtistBySongId(songId);

        // Assert
        assertNotNull(artist);
        assertTrue(artist.getArtistSongsID().contains(songId));
    }
}
