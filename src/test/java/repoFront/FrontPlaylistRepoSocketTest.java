package repoFront;

import commun.*;
import serverSide.entities.*;
import utilsAndFakes.*;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FrontPlaylistRepoSocketTest extends TestHelper {

    private IPlaylistRepository frontPlaylistRepo;
    private TestHelper testHelper;
    private DependencyProvider dependencyProvider;

    @BeforeEach
    void setup() throws IOException {
        testHelper = new TestHelper();
        dependencyProvider = testHelper.dependencyProvider;
        frontPlaylistRepo = dependencyProvider.frontPlaylistRepo;
        dependencyProvider.populateLocalUsers();
        dependencyProvider.populateLocalPlaylists();

        testHelper.startServer();
    }

    @AfterEach
    void tearDown() {
        dependencyProvider.cleanUp();
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
