package data.jsons;

import serverSide.entities.Playlist;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utilsAndFakes.CommuneMethods;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BackPlaylistRepoTest extends CommuneMethods{

    public BackPlaylistRepoTest() throws IOException {
        super();
    }

    @BeforeEach
    void setUp() throws IOException {
    }

    @AfterEach
    void tearDown() {
        if (initializer.tempPlaylistsFile.exists()) {
            initializer.tempPlaylistsFile.delete();
        }
        if (initializer.tempSongsFile.exists()) {
            initializer.tempSongsFile.delete();
        }
        if (initializer.tempUsersFile.exists()) {
            initializer.tempUsersFile.delete();
        }
    }

    @Test
    void savePlaylist_shouldSaveThePlaylist() {
        // Arrange
        Playlist playlist = createTestPlaylist(1, "Test Playlist", initializer.playlistLocalRepository);

        // Act
        initializer.playlistLocalRepository.savePlaylist(playlist);

        // Assert
        List<Playlist> playlists = initializer.playlistLocalRepository.getAllPlaylists();
        assertEquals(1, playlists.size());
        assertEquals("Test Playlist", playlists.get(0).getName());
    }

    @Test
    void savePlaylist_shouldAddPlaylistToRepository() {
        // Arrange
        int [] songsId = new int []{1, 2, 3};

        Playlist playlist = createTestPlaylist(1, "Test Playlist", initializer.playlistLocalRepository);
        addSongsToPlaylist(playlist, songsId);

        // Act
        initializer.playlistLocalRepository.savePlaylist(playlist);

        // Assert
        List<Playlist> playlists = initializer.playlistLocalRepository.getAllPlaylists();
        assertEquals(1, playlists.size());
        assertEquals("Test Playlist", playlists.get(0).getName());
    }

    @Test
    void deletePlaylistById_shouldRemoveThePlaylist() {
        // Arrange
        Playlist playlistOne = createTestPlaylist(1, "Playlist One", initializer.playlistLocalRepository);
        addSongsToPlaylist(playlistOne,  1,2);
        initializer.playlistLocalRepository.savePlaylist(playlistOne);

        Playlist playlistTwo = createTestPlaylist(2, "Playlist Two", initializer.playlistLocalRepository);
        addSongsToPlaylist(playlistTwo,  3,4);
        initializer.playlistLocalRepository.savePlaylist(playlistTwo);

        // Act
        initializer.playlistLocalRepository.deletePlaylistById(playlistOne.getPlaylistId());

        // Assert
        List<Playlist> result = initializer.playlistLocalRepository.getAllPlaylists();
        assertEquals(1, result.size());
        assertEquals(playlistTwo.getPlaylistId(), result.get(0).getPlaylistId());
    }

    @Test
    void getPlaylistById_shouldFindThePlaylist() {
        // Arrange
        Playlist playlist = createTestPlaylist(1, "Test Playlist", initializer.playlistLocalRepository);
        addSongsToPlaylist(playlist, 1,2,3);
        initializer.playlistLocalRepository.savePlaylist(playlist);

        // Act
        Playlist result = initializer.playlistLocalRepository.getPlaylistById(playlist.getPlaylistId());

        // Assert
        assertNotNull(result);
        assertEquals("Test Playlist", result.getName());
    }

    @Test
    void getPlaylistById_withNonexistentId_shouldReturnNull() {
        // Arrange
        Playlist playlist = createTestPlaylist(1, "Test Playlist", initializer.playlistLocalRepository);
        addSongsToPlaylist(playlist, 1,2,3);
        initializer.playlistLocalRepository.savePlaylist(playlist);

        // Act
        Playlist result = initializer.playlistLocalRepository.getPlaylistById(999);

        // Assert
        assertNull(result);
    }

    @Test
    void updatePlaylist_shouldModifyThePlaylist() {
        // Arrange
        Playlist playlist = createTestPlaylist(1, "Original Name", initializer.playlistLocalRepository);
        initializer.playlistLocalRepository.savePlaylist(playlist);
        addSongsToPlaylist(playlist, 1,2);

        // Act
        initializer.playlistService.renamePlayList(playlist.getPlaylistId(), "Updated Name");

        // Add more songs
        addSongsToPlaylist(playlist, 3,4);

        // Assert
        Playlist result = initializer.playlistLocalRepository.getPlaylistById(playlist.getPlaylistId());
        assertEquals("Updated Name", result.getName());
        assertEquals(4, result.getPlaylistSongsListWithId().size());
    }

    @Test
    void getPlaylistByName_shouldFindThePlaylist() {
        // Arrange
        Playlist playlist = createTestPlaylist(1, "Test Playlist", initializer.playlistLocalRepository);
        addSongsToPlaylist(playlist, 1,2,3);
        initializer.playlistLocalRepository.savePlaylist(playlist);

        // Act
        Playlist result = initializer.playlistLocalRepository.getPlaylistByName("Test Playlist");

        // Assert
        assertNotNull(result);
        assertEquals(playlist.getPlaylistId(), result.getPlaylistId());
    }

    @Test
    void getPlaylistByName_shouldBeCaseInsensitive() {
        // Arrange
        Playlist playlist = createTestPlaylist(1, "Test Playlist", initializer.playlistLocalRepository);
        addSongsToPlaylist(playlist, 1,2,3);
        initializer.playlistLocalRepository.savePlaylist(playlist);

        // Act
        Playlist result = initializer.playlistLocalRepository.getPlaylistByName("test PLAYLIST");

        // Assert
        assertNotNull(result);
        assertEquals(playlist.getPlaylistId(), result.getPlaylistId());
    }

    @Test
    void getPlaylistByName_withNonexistentName_shouldReturnNull() {
        // Arrange
        Playlist playlist = createTestPlaylist(1, "Test Playlist", initializer.playlistLocalRepository);
        addSongsToPlaylist(playlist, 1,2,3);
        initializer.playlistLocalRepository.savePlaylist(playlist);

        // Act
        Playlist result = initializer.playlistLocalRepository.getPlaylistByName("Nonexistent Playlist");

        // Assert
        assertNull(result);
    }

    @Test
    void getAllPlaylists_withEmptyRepository_shouldReturnEmptyList() {
        // Act
        List<Playlist> result = initializer.playlistLocalRepository.getAllPlaylists();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
