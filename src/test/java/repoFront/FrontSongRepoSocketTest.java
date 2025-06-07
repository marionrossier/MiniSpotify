package repoFront;

import common.entities.MusicGender;
import common.entities.Song;
import common.repository.ISongRepository;
import utilsAndFakes.*;

import java.util.ArrayList;
import java.util.LinkedList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FrontSongRepoSocketTest {

    private ISongRepository songRepo;
    private TestHelper testHelper;
    private DependencyProvider dependencyProvider;


    @BeforeEach
    void setup() {
        testHelper = new TestHelper(45004);
        dependencyProvider = testHelper.dependencyProvider;
        songRepo = dependencyProvider.frontSongRepo;

        // 🟢 D'abord, populons avant de lancer le serveur !
        dependencyProvider.populateLocalUsers();
        dependencyProvider.populateLocalArtist();
        dependencyProvider.populateLocalSong();

        testHelper.startServer();
    }

    @AfterEach
    void tearDown() {
        dependencyProvider.cleanUp();
        dependencyProvider.socketClient.close(); // 🧹 Ferme la socket
    }

    @Test
    void getSongById_shouldReturnExpectedSong() {
        // Arrange
        int songId = 1108071776;

        // Act
        Song song = songRepo.getSongById(songId);

        // Assert
        assertNotNull(song);
        assertEquals("Rehab", song.getTitle());
    }

    @Test
    void getSongsByTitle_shouldReturnMatchingSongs() {
        // Arrange
        String keyword = "Rehab";

        // Act
        LinkedList<Song> songs = songRepo.getSongsByTitle(keyword);

        // Assert
        assertNotNull(songs);
        assertFalse(songs.isEmpty());
        assertTrue(songs.stream().anyMatch(s -> s.getTitle().contains("Rehab")));
    }

    @Test
    void getSongsByArtist_shouldReturnSongs() {
        // Arrange
        String artistName = "Amy Winehouse";

        // Act
        LinkedList<Song> songs = songRepo.getSongsByArtist(artistName);

        // Assert
        assertNotNull(songs);
        assertFalse(songs.isEmpty());
    }

    @Test
    void getSongsByGender_shouldReturnSongs() {
        // Arrange
        MusicGender gender = MusicGender.SOUL_RNB;

        // Act
        LinkedList<Song> songs = songRepo.getSongsByGender(gender);

        // Assert
        assertNotNull(songs);
        assertFalse(songs.isEmpty());
        assertTrue(songs.stream().allMatch(s -> s.getGender() == gender));
    }

    @Test
    void getAllSongs_shouldReturnList() {
        // Act
        ArrayList<Song> songs = songRepo.getAllSongs();

        // Assert
        assertNotNull(songs);
        assertFalse(songs.isEmpty());
    }
}
