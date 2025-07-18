package repoFront;

import common.entities.Playlist;
import common.repository.IPlaylistRepository;
import utilsAndFakes.*;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FrontPlaylistRepoSocketTest {

    private IPlaylistRepository frontPlaylistRepo;
    private TestHelper testHelper;
    private DependencyProvider dependencyProvider;

    @BeforeEach
    void setup() throws IOException {
        testHelper = new TestHelper(45003);
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
        assertEquals(12, playlist.getPlaylistSongsListWithId().size());
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
