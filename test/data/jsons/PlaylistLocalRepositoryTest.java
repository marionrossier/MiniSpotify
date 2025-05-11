package data.jsons;

import serverSide.entities.Playlist;
import serverSide.repositories.PlaylistLocalRepository;
import serverSide.repositories.SongLocalRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.CommuneMethods;
import clientSide.services.PlaylistServices;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlaylistLocalRepositoryTest {

    private File tempFile;
    private PlaylistLocalRepository playlistLocalRepository;
    private PlaylistServices playlistService;
    private SongLocalRepository songLocalRepository;
    private CommuneMethods communeMethods = new CommuneMethods();

    @BeforeEach
    void setUp() throws IOException {
        tempFile = Files.createTempFile("playlists", ".json").toFile();
        playlistLocalRepository = new PlaylistLocalRepository(tempFile.getAbsolutePath());
        tempFile = Files.createTempFile("songs", ".json").toFile();
        songLocalRepository = new SongLocalRepository(tempFile.getAbsolutePath());
        playlistService = new PlaylistServices(playlistLocalRepository, songLocalRepository);
    }

    @AfterEach
    void tearDown() {
        if (tempFile.exists()) {
            tempFile.delete();
        }
    }

    @Test
    void savePlaylist_shouldSaveThePlaylist() {
        // Arrange
        Playlist playlist = communeMethods.createTestPlaylist(1, "Test Playlist", playlistLocalRepository);

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

        Playlist playlist = communeMethods.createTestPlaylist(1, "Test Playlist", playlistLocalRepository);
        communeMethods.addSongsToPlaylist(playlist, playlistLocalRepository, playlistService, songsId);

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
        Playlist playlistOne = communeMethods.createTestPlaylist(1, "Playlist One", playlistLocalRepository);
        communeMethods.addSongsToPlaylist(playlistOne, playlistLocalRepository, playlistService, 1,2);
        playlistLocalRepository.savePlaylist(playlistOne);

        Playlist playlistTwo = communeMethods.createTestPlaylist(2, "Playlist Two", playlistLocalRepository);
        communeMethods.addSongsToPlaylist(playlistTwo, playlistLocalRepository, playlistService, 3,4);
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
        Playlist playlist = communeMethods.createTestPlaylist(1, "Test Playlist", playlistLocalRepository);
        communeMethods.addSongsToPlaylist(playlist, playlistLocalRepository, playlistService, 1,2,3);
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
        Playlist playlist = communeMethods.createTestPlaylist(1, "Test Playlist", playlistLocalRepository);
        communeMethods.addSongsToPlaylist(playlist, playlistLocalRepository, playlistService, 1,2,3);
        playlistLocalRepository.savePlaylist(playlist);

        // Act
        Playlist result = playlistLocalRepository.getPlaylistById(999);

        // Assert
        assertNull(result);
    }

    @Test
    void updatePlaylist_shouldModifyThePlaylist() {
        // Arrange
        Playlist playlist = communeMethods.createTestPlaylist(1, "Original Name", playlistLocalRepository);
        playlistLocalRepository.savePlaylist(playlist);
        communeMethods.addSongsToPlaylist(playlist, playlistLocalRepository, playlistService, 1,2);

        // Act
        playlistService.renamePlayList(playlist.getPlaylistId(), "Updated Name");

        // Add more songs
        communeMethods.addSongsToPlaylist(playlist, playlistLocalRepository, playlistService,3,4);

        // Assert
        Playlist result = playlistLocalRepository.getPlaylistById(playlist.getPlaylistId());
        assertEquals("Updated Name", result.getName());
        assertEquals(4, result.getPlaylistSongsListWithId().size());
    }

    @Test
    void getPlaylistByName_shouldFindThePlaylist() {
        // Arrange
        Playlist playlist = communeMethods.createTestPlaylist(1, "Test Playlist", playlistLocalRepository);
        communeMethods.addSongsToPlaylist(playlist, playlistLocalRepository, playlistService, 1,2,3);
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
        Playlist playlist = communeMethods.createTestPlaylist(1, "Test Playlist", playlistLocalRepository);
        communeMethods.addSongsToPlaylist(playlist, playlistLocalRepository, playlistService, 1,2,3);
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
        Playlist playlist = communeMethods.createTestPlaylist(1, "Test Playlist", playlistLocalRepository);
        communeMethods.addSongsToPlaylist(playlist, playlistLocalRepository, playlistService,1,2,3);
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
