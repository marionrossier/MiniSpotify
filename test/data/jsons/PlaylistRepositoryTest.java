package data.jsons;

import data.entities.Playlist;
import data.entities.Song;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlaylistRepositoryTest {

    private File tempFile;
    private PlaylistRepository repo;

    @BeforeEach
    void setUp() throws IOException {
        tempFile = Files.createTempFile("playlists", ".json").toFile();
        repo = new PlaylistRepository(tempFile.getAbsolutePath());
    }

    @AfterEach
    void tearDown() {
        if (tempFile.exists()) {
            tempFile.delete();
        }
    }

    private Playlist createTestPlaylist(int id, String name) {
        Playlist playlist = new Playlist(name);
        playlist.setPlaylistId(id);
        return playlist;
    }
    
    private Song createTestSong(int id, String title) {
        Song song = new Song();
        song.setSongId(id);
        song.setTitle(title);
        song.setSeconds(180);
        return song;
    }
    
    private void addSongsToPlaylist(Playlist playlist, int... songIds) {
        for (int id : songIds) {
            playlist.addSong(createTestSong(id, "Song " + id));
        }
    }

    @Test
    void addPlaylist_shouldSaveThePlaylist() {
        // Arrange
        Playlist playlist = createTestPlaylist(1, "Test Playlist");
        addSongsToPlaylist(playlist, 1, 2, 3);

        // Act
        repo.addPlaylist(playlist);

        // Assert
        List<Playlist> playlists = repo.getAllPlaylists();
        assertEquals(1, playlists.size());
        assertEquals("Test Playlist", playlists.get(0).getPlaylistName());
        assertEquals(3, playlists.get(0).getPlaylistSongsListWithId().size());
    }

    @Test
    void savePlaylist_shouldAddPlaylistToRepository() {
        // Arrange
        Playlist playlist = createTestPlaylist(1, "Test Playlist");
        addSongsToPlaylist(playlist, 1, 2, 3);

        // Act
        repo.savePlaylist(playlist);

        // Assert
        List<Playlist> playlists = repo.getAllPlaylists();
        assertEquals(1, playlists.size());
        assertEquals("Test Playlist", playlists.get(0).getPlaylistName());
    }

    @Test
    void deletePlaylistById_shouldRemoveThePlaylist() {
        // Arrange
        Playlist playlistOne = createTestPlaylist(1, "Playlist One");
        addSongsToPlaylist(playlistOne, 1, 2);
        
        Playlist playlistTwo = createTestPlaylist(2, "Playlist Two");
        addSongsToPlaylist(playlistTwo, 3, 4);
        
        repo.addPlaylist(playlistOne);
        repo.addPlaylist(playlistTwo);

        // Act
        repo.deletePlaylistById(playlistOne.getPlaylistId());

        // Assert
        List<Playlist> result = repo.getAllPlaylists();
        assertEquals(1, result.size());
        assertEquals(playlistTwo.getPlaylistId(), result.get(0).getPlaylistId());
    }

    @Test
    void getPlaylistById_shouldFindThePlaylist() {
        // Arrange
        Playlist playlist = createTestPlaylist(1, "Test Playlist");
        addSongsToPlaylist(playlist, 1, 2, 3);
        repo.addPlaylist(playlist);

        // Act
        Playlist result = repo.getPlaylistById(playlist.getPlaylistId());

        // Assert
        assertNotNull(result);
        assertEquals("Test Playlist", result.getPlaylistName());
    }

    @Test
    void getPlaylistById_withNonexistentId_shouldReturnNull() {
        // Arrange
        Playlist playlist = createTestPlaylist(1, "Test Playlist");
        addSongsToPlaylist(playlist, 1, 2, 3);
        repo.addPlaylist(playlist);

        // Act
        Playlist result = repo.getPlaylistById(999);

        // Assert
        assertNull(result);
    }

    @Test
    void updatePlaylist_shouldModifyThePlaylist() {
        // Arrange
        Playlist playlist = createTestPlaylist(1, "Original Name");
        addSongsToPlaylist(playlist, 1, 2);
        repo.addPlaylist(playlist);

        // Act
        playlist.setName("Updated Name");
        // Add more songs
        addSongsToPlaylist(playlist, 3, 4);
        repo.updatePlaylist(playlist);

        // Assert
        Playlist result = repo.getPlaylistById(playlist.getPlaylistId());
        assertEquals("Updated Name", result.getPlaylistName());
        assertEquals(4, result.getPlaylistSongsListWithId().size());
    }

    @Test
    void getPlaylistByName_shouldFindThePlaylist() {
        // Arrange
        Playlist playlist = createTestPlaylist(1, "Test Playlist");
        addSongsToPlaylist(playlist, 1, 2, 3);
        repo.addPlaylist(playlist);

        // Act
        Playlist result = repo.getPlaylistByName("Test Playlist");

        // Assert
        assertNotNull(result);
        assertEquals(playlist.getPlaylistId(), result.getPlaylistId());
    }

    @Test
    void getPlaylistByName_shouldBeCaseInsensitive() {
        // Arrange
        Playlist playlist = createTestPlaylist(1, "Test Playlist");
        addSongsToPlaylist(playlist, 1, 2, 3);
        repo.addPlaylist(playlist);

        // Act
        Playlist result = repo.getPlaylistByName("test PLAYLIST");

        // Assert
        assertNotNull(result);
        assertEquals(playlist.getPlaylistId(), result.getPlaylistId());
    }

    @Test
    void getPlaylistByName_withNonexistentName_shouldReturnNull() {
        // Arrange
        Playlist playlist = createTestPlaylist(1, "Test Playlist");
        addSongsToPlaylist(playlist, 1, 2, 3);
        repo.addPlaylist(playlist);

        // Act
        Playlist result = repo.getPlaylistByName("Nonexistent Playlist");

        // Assert
        assertNull(result);
    }

    @Test
    void getAllPlaylists_withEmptyRepository_shouldReturnEmptyList() {
        // Act
        List<Playlist> result = repo.getAllPlaylists();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
