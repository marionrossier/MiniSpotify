package clientSide.repoFront;

import clientSide.services.Cookies_SingletonPattern;
import middle.IPlaylistRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import serverSide.entities.Playlist;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FrontPlaylistRepoSocketTest {

    static IPlaylistRepository repo;
    static Thread serverThread;

    @BeforeAll
    static void startServerAndSetupRepo() {
        try (Socket testSocket = new Socket("127.0.0.1", 45000)) {
            // Le serveur est déjà lancé, on ne fait rien
            System.out.println("✅ Serveur déjà actif.");
        } catch (IOException e) {
            // Le serveur n'est pas lancé, on peut le démarrer
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
                Thread.sleep(1000); // Laisse le temps au serveur de démarrer
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }

        Cookies_SingletonPattern.setUser(232928320);
        repo = new FrontPlaylistRepo();
    }

    @Test
    void getPlaylistById_shouldReturnCorrectPlaylist() {
        // Arrange
        int id = 1940298216;

        // Act
        Playlist playlist = repo.getPlaylistById(id);

        // Assert
        assertNotNull(playlist);
        assertEquals("Girls", playlist.getName());
        assertEquals(12, playlist.getSize());
    }

    @Test
    void getAllPlaylists_shouldContainKnownPlaylist() {
        // Arrange

        // Act
        List<Playlist> playlists = repo.getAllPlaylists();

        // Assert
        assertNotNull(playlists);
        assertTrue(playlists.stream().anyMatch(p -> p.getName().equals("Girls")));
    }

    @Test
    void getPlaylistByName_shouldFindPlaylist() {
        // Arrange
        String name = "Rock Legends";

        // Act
        Playlist playlist = repo.getPlaylistByName(name);

        // Assert
        assertNotNull(playlist);
        assertEquals(546441990, playlist.getPlaylistId());
    }

    @Test
    void getTemporaryPlaylistOfCurrentUser_shouldReturnNull() {
        // Arrange
        int userId = 232928320;

        // Act
        Playlist temp = repo.getTemporaryPlaylistOfCurrentUser(userId);

        // Assert
        assertNotNull(temp); // sauf si "temporaryPlaylist" existe pas
    }
}
