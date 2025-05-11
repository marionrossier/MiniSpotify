package data.jsons;

import clientSide.services.*;
import serverSide.StockageService;
import serverSide.entities.Playlist;
import serverSide.repositories.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utilsAndFakes.CommuneMethods;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlaylistLocalRepositoryTest extends CommuneMethods{
    public PlaylistLocalRepositoryTest() throws IOException {
    }

    @BeforeEach
    void setUp() throws IOException {
    }

    @AfterEach
    void tearDown() {
        if (tempPlaylistsFile.exists()) {
            tempPlaylistsFile.delete();
        }
        if (tempSongsFile.exists()) {
            tempSongsFile.delete();
        }
        if (tempUsersFile.exists()) {
            tempUsersFile.delete();
        }
    }

    @Test
    void savePlaylist_shouldSaveThePlaylist() {
        // Arrange
        Playlist playlist = createTestPlaylist(1, "Test Playlist", playlistLocalRepository);

        // Act
        playlistLocalRepository.savePlaylist(playlist);

        // Assert
        List<Playlist> playlists = playlistLocalRepository.getAllPlaylists();
        assertEquals(1, playlists.size());
        assertEquals("Test Playlist", playlists.get(0).getName());
    }

    @Test
    void savePlaylist_shouldAddPlaylistToRepository() {
        // Arrange
        int [] songsId = new int []{1, 2, 3};

        Playlist playlist = createTestPlaylist(1, "Test Playlist", playlistLocalRepository);
        addSongsToPlaylist(playlist, songsId);

        // Act
        playlistLocalRepository.savePlaylist(playlist);

        // Assert
        List<Playlist> playlists = playlistLocalRepository.getAllPlaylists();
        assertEquals(1, playlists.size());
        assertEquals("Test Playlist", playlists.get(0).getName());
    }

    @Test
    void deletePlaylistById_shouldRemoveThePlaylist() {
        // Arrange
        Playlist playlistOne = createTestPlaylist(1, "Playlist One", playlistLocalRepository);
        addSongsToPlaylist(playlistOne,  1,2);
        playlistLocalRepository.savePlaylist(playlistOne);

        Playlist playlistTwo = createTestPlaylist(2, "Playlist Two", playlistLocalRepository);
        addSongsToPlaylist(playlistTwo,  3,4);
        playlistLocalRepository.savePlaylist(playlistTwo);

        // Act
        playlistLocalRepository.deletePlaylistById(playlistOne.getPlaylistId());

        // Assert
        List<Playlist> result = playlistLocalRepository.getAllPlaylists();
        assertEquals(1, result.size());
        assertEquals(playlistTwo.getPlaylistId(), result.get(0).getPlaylistId());
    }

    @Test
    void getPlaylistById_shouldFindThePlaylist() {
        // Arrange
        Playlist playlist = createTestPlaylist(1, "Test Playlist", playlistLocalRepository);
        addSongsToPlaylist(playlist, 1,2,3);
        playlistLocalRepository.savePlaylist(playlist);

        // Act
        Playlist result = playlistLocalRepository.getPlaylistById(playlist.getPlaylistId());

        // Assert
        assertNotNull(result);
        assertEquals("Test Playlist", result.getName());
    }

    @Test
    void getPlaylistById_withNonexistentId_shouldReturnNull() {
        // Arrange
        Playlist playlist = createTestPlaylist(1, "Test Playlist", playlistLocalRepository);
        addSongsToPlaylist(playlist, 1,2,3);
        playlistLocalRepository.savePlaylist(playlist);

        // Act
        Playlist result = playlistLocalRepository.getPlaylistById(999);

        // Assert
        assertNull(result);
    }

    @Test
    void updatePlaylist_shouldModifyThePlaylist() {
        // Arrange
        Playlist playlist = createTestPlaylist(1, "Original Name", playlistLocalRepository);
        playlistLocalRepository.savePlaylist(playlist);
        addSongsToPlaylist(playlist, 1,2);

        // Act
        playlistService.renamePlayList(playlist.getPlaylistId(), "Updated Name");

        // Add more songs
        addSongsToPlaylist(playlist, 3,4);

        // Assert
        Playlist result = playlistLocalRepository.getPlaylistById(playlist.getPlaylistId());
        assertEquals("Updated Name", result.getName());
        assertEquals(4, result.getPlaylistSongsListWithId().size());
    }

    @Test
    void getPlaylistByName_shouldFindThePlaylist() {
        // Arrange
        Playlist playlist = createTestPlaylist(1, "Test Playlist", playlistLocalRepository);
        addSongsToPlaylist(playlist, 1,2,3);
        playlistLocalRepository.savePlaylist(playlist);

        // Act
        Playlist result = playlistLocalRepository.getPlaylistByName("Test Playlist");

        // Assert
        assertNotNull(result);
        assertEquals(playlist.getPlaylistId(), result.getPlaylistId());
    }

    @Test
    void getPlaylistByName_shouldBeCaseInsensitive() {
        // Arrange
        Playlist playlist = createTestPlaylist(1, "Test Playlist", playlistLocalRepository);
        addSongsToPlaylist(playlist, 1,2,3);
        playlistLocalRepository.savePlaylist(playlist);

        // Act
        Playlist result = playlistLocalRepository.getPlaylistByName("test PLAYLIST");

        // Assert
        assertNotNull(result);
        assertEquals(playlist.getPlaylistId(), result.getPlaylistId());
    }

    @Test
    void getPlaylistByName_withNonexistentName_shouldReturnNull() {
        // Arrange
        Playlist playlist = createTestPlaylist(1, "Test Playlist", playlistLocalRepository);
        addSongsToPlaylist(playlist, 1,2,3);
        playlistLocalRepository.savePlaylist(playlist);

        // Act
        Playlist result = playlistLocalRepository.getPlaylistByName("Nonexistent Playlist");

        // Assert
        assertNull(result);
    }

    @Test
    void getAllPlaylists_withEmptyRepository_shouldReturnEmptyList() {
        // Act
        List<Playlist> result = playlistLocalRepository.getAllPlaylists();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
