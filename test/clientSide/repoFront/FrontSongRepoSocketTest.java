package clientSide.repoFront;

import middle.ISongRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import serverSide.entities.MusicGender;
import serverSide.entities.Song;
import utilsAndFakes.CommuneMethods;
import utilsAndFakes.Initializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class FrontSongRepoSocketTest {

    private ISongRepository songRepo;
    private CommuneMethods communeMethods;
    private Initializer initializer;


    @BeforeEach
    void setup() {
        communeMethods = new CommuneMethods();
        initializer = communeMethods.initializer;
        songRepo = initializer.frontSongRepo;
        initializer.populateLocalUsers();
        initializer.populateLocalArtist();
        initializer.populateLocalSong();

        communeMethods.startServer();
    }

    @AfterEach
    void tearDown() {
        initializer.cleanUp();
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
        ArrayList<serverSide.entities.Song> songs = songRepo.getAllSongs();

        // Assert
        assertNotNull(songs);
        assertFalse(songs.isEmpty());
    }
}
