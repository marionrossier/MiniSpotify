package data.jsons;

import serverSide.entities.Playlist;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utilsAndFakes.CommuneMethods;
import utilsAndFakes.Initializer;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlaylistLocalRepoTest{

    private CommuneMethods communeMethods;
    private Initializer initializer;

    public PlaylistLocalRepoTest(){
        communeMethods = new CommuneMethods();
        initializer = communeMethods.initializer;
    }

    @BeforeEach
    void setUp() throws IOException {
    }

    @AfterEach
    void tearDown() {
        initializer.cleanUp();
    }

    @Test
    void savePlaylist_shouldSaveThePlaylist() {
        // Arrange
        Playlist playlist = communeMethods.createTestPlaylist(1, "Test Playlist", initializer.playlistLocalRepository);

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

        Playlist playlist = communeMethods.createTestPlaylist(1, "Test Playlist", initializer.playlistLocalRepository);
        communeMethods.addSongsToPlaylist(playlist, songsId);

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
        Playlist playlistOne = communeMethods.createTestPlaylist(1, "Playlist One", initializer.playlistLocalRepository);
        communeMethods.addSongsToPlaylist(playlistOne,  1,2);
        initializer.playlistLocalRepository.savePlaylist(playlistOne);

        Playlist playlistTwo = communeMethods.createTestPlaylist(2, "Playlist Two", initializer.playlistLocalRepository);
        communeMethods.addSongsToPlaylist(playlistTwo,  3,4);
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
        Playlist playlist = communeMethods.createTestPlaylist(1, "Test Playlist", initializer.playlistLocalRepository);
        communeMethods.addSongsToPlaylist(playlist, 1,2,3);
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
        Playlist playlist = communeMethods.createTestPlaylist(1, "Test Playlist", initializer.playlistLocalRepository);
        communeMethods.addSongsToPlaylist(playlist, 1,2,3);
        initializer.playlistLocalRepository.savePlaylist(playlist);

        // Act
        Playlist result = initializer.playlistLocalRepository.getPlaylistById(999);

        // Assert
        assertNull(result);
    }

    @Test
    void updatePlaylist_shouldModifyThePlaylist() {
        // Arrange
        Playlist playlist = communeMethods.createTestPlaylist(1, "Original Name", initializer.playlistLocalRepository);
        initializer.playlistLocalRepository.savePlaylist(playlist);
        communeMethods.addSongsToPlaylist(playlist, 1,2);

        // Act
        initializer.playlistService.renamePlayList(playlist.getPlaylistId(), "Updated Name");

        // Add more songs
        communeMethods.addSongsToPlaylist(playlist, 3,4);

        // Assert
        Playlist result = initializer.playlistLocalRepository.getPlaylistById(playlist.getPlaylistId());
        assertEquals("Updated Name", result.getName());
        assertEquals(4, result.getPlaylistSongsListWithId().size());
    }

    @Test
    void getPlaylistByName_shouldFindThePlaylist() {
        // Arrange
        Playlist playlist = communeMethods.createTestPlaylist(1, "Test Playlist", initializer.playlistLocalRepository);
        communeMethods.addSongsToPlaylist(playlist, 1,2,3);
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
        Playlist playlist = communeMethods.createTestPlaylist(1, "Test Playlist", initializer.playlistLocalRepository);
        communeMethods.addSongsToPlaylist(playlist, 1,2,3);
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
        Playlist playlist = communeMethods.createTestPlaylist(1, "Test Playlist", initializer.playlistLocalRepository);
        communeMethods.addSongsToPlaylist(playlist, 1,2,3);
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
