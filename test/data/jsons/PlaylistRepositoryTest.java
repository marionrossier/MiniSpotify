package data.jsons;

import serverSide.entities.Playlist;
import serverSide.repositories.PlaylistRepository;
import serverSide.repositories.SongRepository;
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

class PlaylistRepositoryTest {

    private File tempFile;
    private PlaylistRepository playlistRepository;
    private PlaylistServices playlistService;
    private SongRepository songRepository;
    private CommuneMethods communeMethods = new CommuneMethods();

    @BeforeEach
    void setUp() throws IOException {
        tempFile = Files.createTempFile("playlists", ".json").toFile();
        playlistRepository = new PlaylistRepository(tempFile.getAbsolutePath());
        tempFile = Files.createTempFile("songs", ".json").toFile();
        songRepository = new SongRepository(tempFile.getAbsolutePath());
        playlistService = new PlaylistServices(playlistRepository, songRepository);
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
        Playlist playlist = communeMethods.createTestPlaylist(1, "Test Playlist", playlistRepository);

        // Act
        playlistRepository.savePlaylist(playlist);

        // Assert
        List<Playlist> playlists = playlistRepository.getAllPlaylists();
        assertEquals(1, playlists.size());
        assertEquals("Test Playlist", playlists.get(0).getName());
    }

    @Test
    void savePlaylist_shouldAddPlaylistToRepository() {
        // Arrange
        int [] songsId = new int []{1, 2, 3};

        Playlist playlist = communeMethods.createTestPlaylist(1, "Test Playlist", playlistRepository);
        communeMethods.addSongsToPlaylist(playlist, playlistRepository, playlistService, songsId);

        // Act
        playlistRepository.savePlaylist(playlist);

        // Assert
        List<Playlist> playlists = playlistRepository.getAllPlaylists();
        assertEquals(1, playlists.size());
        assertEquals("Test Playlist", playlists.get(0).getName());
    }

    @Test
    void deletePlaylistById_shouldRemoveThePlaylist() {
        // Arrange
        Playlist playlistOne = communeMethods.createTestPlaylist(1, "Playlist One", playlistRepository);
        communeMethods.addSongsToPlaylist(playlistOne, playlistRepository, playlistService, 1,2);
        playlistRepository.savePlaylist(playlistOne);

        Playlist playlistTwo = communeMethods.createTestPlaylist(2, "Playlist Two",playlistRepository);
        communeMethods.addSongsToPlaylist(playlistTwo, playlistRepository, playlistService, 3,4);
        playlistRepository.savePlaylist(playlistTwo);

        // Act
        playlistRepository.deletePlaylistById(playlistOne.getPlaylistId());

        // Assert
        List<Playlist> result = playlistRepository.getAllPlaylists();
        assertEquals(1, result.size());
        assertEquals(playlistTwo.getPlaylistId(), result.get(0).getPlaylistId());
    }

    @Test
    void getPlaylistById_shouldFindThePlaylist() {
        // Arrange
        Playlist playlist = communeMethods.createTestPlaylist(1, "Test Playlist", playlistRepository);
        communeMethods.addSongsToPlaylist(playlist, playlistRepository, playlistService, 1,2,3);
        playlistRepository.savePlaylist(playlist);

        // Act
        Playlist result = playlistRepository.getPlaylistById(playlist.getPlaylistId());

        // Assert
        assertNotNull(result);
        assertEquals("Test Playlist", result.getName());
    }

    @Test
    void getPlaylistById_withNonexistentId_shouldReturnNull() {
        // Arrange
        Playlist playlist = communeMethods.createTestPlaylist(1, "Test Playlist", playlistRepository);
        communeMethods.addSongsToPlaylist(playlist, playlistRepository, playlistService, 1,2,3);
        playlistRepository.savePlaylist(playlist);

        // Act
        Playlist result = playlistRepository.getPlaylistById(999);

        // Assert
        assertNull(result);
    }

    @Test
    void updatePlaylist_shouldModifyThePlaylist() {
        // Arrange
        Playlist playlist = communeMethods.createTestPlaylist(1, "Original Name", playlistRepository);
        playlistRepository.savePlaylist(playlist);
        communeMethods.addSongsToPlaylist(playlist, playlistRepository, playlistService, 1,2);

        // Act
        playlistService.renamePlayList(playlist.getPlaylistId(), "Updated Name");

        // Add more songs
        communeMethods.addSongsToPlaylist(playlist, playlistRepository, playlistService,3,4);

        // Assert
        Playlist result = playlistRepository.getPlaylistById(playlist.getPlaylistId());
        assertEquals("Updated Name", result.getName());
        assertEquals(4, result.getPlaylistSongsListWithId().size());
    }

    @Test
    void getPlaylistByName_shouldFindThePlaylist() {
        // Arrange
        Playlist playlist = communeMethods.createTestPlaylist(1, "Test Playlist", playlistRepository);
        communeMethods.addSongsToPlaylist(playlist, playlistRepository, playlistService, 1,2,3);
        playlistRepository.savePlaylist(playlist);

        // Act
        Playlist result = playlistRepository.getPlaylistByName("Test Playlist");

        // Assert
        assertNotNull(result);
        assertEquals(playlist.getPlaylistId(), result.getPlaylistId());
    }

    @Test
    void getPlaylistByName_shouldBeCaseInsensitive() {
        // Arrange
        Playlist playlist = communeMethods.createTestPlaylist(1, "Test Playlist", playlistRepository);
        communeMethods.addSongsToPlaylist(playlist, playlistRepository, playlistService, 1,2,3);
        playlistRepository.savePlaylist(playlist);

        // Act
        Playlist result = playlistRepository.getPlaylistByName("test PLAYLIST");

        // Assert
        assertNotNull(result);
        assertEquals(playlist.getPlaylistId(), result.getPlaylistId());
    }

    @Test
    void getPlaylistByName_withNonexistentName_shouldReturnNull() {
        // Arrange
        Playlist playlist = communeMethods.createTestPlaylist(1, "Test Playlist", playlistRepository);
        communeMethods.addSongsToPlaylist(playlist, playlistRepository, playlistService,1,2,3);
        playlistRepository.savePlaylist(playlist);

        // Act
        Playlist result = playlistRepository.getPlaylistByName("Nonexistent Playlist");

        // Assert
        assertNull(result);
    }

    @Test
    void getAllPlaylists_withEmptyRepository_shouldReturnEmptyList() {
        // Act
        List<Playlist> result = playlistRepository.getAllPlaylists();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
