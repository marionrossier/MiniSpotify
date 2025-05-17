package jsons;

import serverSide.entities.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utilsAndFakes.*;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlaylistLocalRepoTest{

    private final TestHelper testHelper;
    private final DependencyProvider dependencyProvider;

    public PlaylistLocalRepoTest(){
        testHelper = new TestHelper();
        dependencyProvider = testHelper.dependencyProvider;
    }

    @BeforeEach
    void setUp() throws IOException {
    }

    @AfterEach
    void tearDown() {
        dependencyProvider.cleanUp();
    }

    @Test
    void savePlaylist_shouldSaveThePlaylist() {
        // Arrange
        Playlist playlist = testHelper.createTestPlaylist(1, "Test Playlist", dependencyProvider.playlistLocalRepository);

        // Act
        dependencyProvider.playlistLocalRepository.savePlaylist(playlist);

        // Assert
        List<Playlist> playlists = dependencyProvider.playlistLocalRepository.getAllPlaylists();
        assertEquals(1, playlists.size());
        assertEquals("Test Playlist", playlists.get(0).getName());
    }

    @Test
    void savePlaylist_shouldAddPlaylistToRepository() {
        // Arrange
        int [] songsId = new int []{1, 2, 3};

        Playlist playlist = testHelper.createTestPlaylist(1, "Test Playlist", dependencyProvider.playlistLocalRepository);
        testHelper.addSongsToPlaylist(playlist, songsId);

        // Act
        dependencyProvider.playlistLocalRepository.savePlaylist(playlist);

        // Assert
        List<Playlist> playlists = dependencyProvider.playlistLocalRepository.getAllPlaylists();
        assertEquals(1, playlists.size());
        assertEquals("Test Playlist", playlists.get(0).getName());
    }

    @Test
    void deletePlaylistById_shouldRemoveThePlaylist() {
        // Arrange
        Playlist playlistOne = testHelper.createTestPlaylist(1, "Playlist One", dependencyProvider.playlistLocalRepository);
        testHelper.addSongsToPlaylist(playlistOne,  1,2);
        dependencyProvider.playlistLocalRepository.savePlaylist(playlistOne);

        Playlist playlistTwo = testHelper.createTestPlaylist(2, "Playlist Two", dependencyProvider.playlistLocalRepository);
        testHelper.addSongsToPlaylist(playlistTwo,  3,4);
        dependencyProvider.playlistLocalRepository.savePlaylist(playlistTwo);

        // Act
        dependencyProvider.playlistLocalRepository.deletePlaylistById(playlistOne.getPlaylistId());

        // Assert
        List<Playlist> result = dependencyProvider.playlistLocalRepository.getAllPlaylists();
        assertEquals(1, result.size());
        assertEquals(playlistTwo.getPlaylistId(), result.get(0).getPlaylistId());
    }

    @Test
    void getPlaylistById_shouldFindThePlaylist() {
        // Arrange
        Playlist playlist = testHelper.createTestPlaylist(1, "Test Playlist", dependencyProvider.playlistLocalRepository);
        testHelper.addSongsToPlaylist(playlist, 1,2,3);
        dependencyProvider.playlistLocalRepository.savePlaylist(playlist);

        // Act
        Playlist result = dependencyProvider.playlistLocalRepository.getPlaylistById(playlist.getPlaylistId());

        // Assert
        assertNotNull(result);
        assertEquals("Test Playlist", result.getName());
    }

    @Test
    void getPlaylistById_withNonexistentId_shouldReturnNull() {
        // Arrange
        Playlist playlist = testHelper.createTestPlaylist(1, "Test Playlist", dependencyProvider.playlistLocalRepository);
        testHelper.addSongsToPlaylist(playlist, 1,2,3);
        dependencyProvider.playlistLocalRepository.savePlaylist(playlist);

        // Act
        Playlist result = dependencyProvider.playlistLocalRepository.getPlaylistById(999);

        // Assert
        assertNull(result);
    }

    @Test
    void updatePlaylist_shouldModifyThePlaylist() {
        // Arrange
        Playlist playlist = testHelper.createTestPlaylist(1, "Original Name", dependencyProvider.playlistLocalRepository);
        dependencyProvider.playlistLocalRepository.savePlaylist(playlist);
        testHelper.addSongsToPlaylist(playlist, 1,2);

        // Act
        dependencyProvider.playlistService.renamePlayList(playlist.getPlaylistId(), "Updated Name");

        // Add more songs
        testHelper.addSongsToPlaylist(playlist, 3,4);

        // Assert
        Playlist result = dependencyProvider.playlistLocalRepository.getPlaylistById(playlist.getPlaylistId());
        assertEquals("Updated Name", result.getName());
        assertEquals(4, result.getPlaylistSongsListWithId().size());
    }

    @Test
    void getPlaylistByName_shouldFindThePlaylist() {
        // Arrange
        Playlist playlist = testHelper.createTestPlaylist(1, "Test Playlist", dependencyProvider.playlistLocalRepository);
        testHelper.addSongsToPlaylist(playlist, 1,2,3);
        dependencyProvider.playlistLocalRepository.savePlaylist(playlist);

        // Act
        Playlist result = dependencyProvider.playlistLocalRepository.getPlaylistByName("Test Playlist");

        // Assert
        assertNotNull(result);
        assertEquals(playlist.getPlaylistId(), result.getPlaylistId());
    }

    @Test
    void getPlaylistByName_shouldBeCaseInsensitive() {
        // Arrange
        Playlist playlist = testHelper.createTestPlaylist(1, "Test Playlist", dependencyProvider.playlistLocalRepository);
        testHelper.addSongsToPlaylist(playlist, 1,2,3);
        dependencyProvider.playlistLocalRepository.savePlaylist(playlist);

        // Act
        Playlist result = dependencyProvider.playlistLocalRepository.getPlaylistByName("test PLAYLIST");

        // Assert
        assertNotNull(result);
        assertEquals(playlist.getPlaylistId(), result.getPlaylistId());
    }

    @Test
    void getPlaylistByName_withNonexistentName_shouldReturnNull() {
        // Arrange
        Playlist playlist = testHelper.createTestPlaylist(1, "Test Playlist", dependencyProvider.playlistLocalRepository);
        testHelper.addSongsToPlaylist(playlist, 1,2,3);
        dependencyProvider.playlistLocalRepository.savePlaylist(playlist);

        // Act
        Playlist result = dependencyProvider.playlistLocalRepository.getPlaylistByName("Nonexistent Playlist");

        // Assert
        assertNull(result);
    }

    @Test
    void getAllPlaylists_withEmptyRepository_shouldReturnEmptyList() {
        // Act
        List<Playlist> result = dependencyProvider.playlistLocalRepository.getAllPlaylists();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
