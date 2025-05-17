package clientSide.repoFront;

import commun.IPlaylistRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import serverSide.entities.Playlist;
import utilsAndFakes.CommuneMethods;
import utilsAndFakes.Initializer;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FrontPlaylistRepoSocketTest extends CommuneMethods{

    private IPlaylistRepository frontPlaylistRepo;
    private CommuneMethods communeMethods;
    private Initializer initializer;

    @BeforeEach
    void setup() throws IOException {
        communeMethods = new CommuneMethods();
        initializer = communeMethods.initializer;
        frontPlaylistRepo = initializer.frontPlaylistRepo;
        initializer.populateLocalUsers();
        initializer.populateLocalPlaylists();

        communeMethods.startServer();
    }

    @AfterEach
    void tearDown() {
        initializer.cleanUp();
    }

    @Test
    void getPlaylistById_shouldReturnCorrectPlaylist() {
        // Arrange
        int id = 1940298216;

        // Act
        Playlist playlist = frontPlaylistRepo.getPlaylistById(id);

        // Assert
        assertNotNull(playlist);
        assertEquals("Girls", playlist.getName());
        assertEquals(12, playlist.getSize());
    }

    @Test
    void getAllPlaylists_shouldContainKnownPlaylist() {
        // Arrange

        // Act
        List<Playlist> playlists = frontPlaylistRepo.getAllPlaylists();

        // Assert
        assertNotNull(playlists);
        assertTrue(playlists.stream().anyMatch(p -> p.getName().equals("Girls")));
    }

    @Test
    void getPlaylistByName_shouldFindPlaylist() {
        // Arrange
        String name = "Rock Legends";

        // Act
        Playlist playlist = frontPlaylistRepo.getPlaylistByName(name);

        // Assert
        assertNotNull(playlist);
        assertEquals(546441990, playlist.getPlaylistId());
    }

    @Test
    void getTemporaryPlaylistOfCurrentUser_shouldReturnNull() {
        // Arrange
        int userId = 232928320;

        // Act
        Playlist temp = frontPlaylistRepo.getTemporaryPlaylistOfCurrentUser(userId);

        // Assert
        assertNull(temp);
    }
}
