package clientSide.repoFront;

import middle.IPlaylistRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import serverSide.entities.Playlist;
import utilsAndFakes.CommuneMethods;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FrontPlaylistRepoSocketTest {

    static IPlaylistRepository playlistRepo;
    static CommuneMethods commune;

    public FrontPlaylistRepoSocketTest() throws IOException {
    }

    @BeforeAll
    static void setup() throws IOException {
        commune = new CommuneMethods() {};
        playlistRepo = commune.startServerAndInitRepo(FrontPlaylistRepo::new);
    }

    @Test
    void getPlaylistById_shouldReturnCorrectPlaylist() {
        // Arrange
        int id = 1940298216;

        // Act
        Playlist playlist = playlistRepo.getPlaylistById(id);

        // Assert
        assertNotNull(playlist);
        assertEquals("Girls", playlist.getName());
        assertEquals(12, playlist.getSize());
    }

    @Test
    void getAllPlaylists_shouldContainKnownPlaylist() {
        // Arrange

        // Act
        List<Playlist> playlists = playlistRepo.getAllPlaylists();

        // Assert
        assertNotNull(playlists);
        assertTrue(playlists.stream().anyMatch(p -> p.getName().equals("Girls")));
    }

    @Test
    void getPlaylistByName_shouldFindPlaylist() {
        // Arrange
        String name = "Rock Legends";

        // Act
        Playlist playlist = playlistRepo.getPlaylistByName(name);

        // Assert
        assertNotNull(playlist);
        assertEquals(546441990, playlist.getPlaylistId());
    }

    @Test
    void getTemporaryPlaylistOfCurrentUser_shouldReturnNull() {
        // Arrange
        int userId = 232928320;

        // Act
        Playlist temp = playlistRepo.getTemporaryPlaylistOfCurrentUser(userId);

        // Assert
        assertNotNull(temp); // sauf si "temporaryPlaylist" existe pas
    }
}
