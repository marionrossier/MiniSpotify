package clientSide.repoFront;

import middle.ISongRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import serverSide.entities.MusicGender;
import serverSide.entities.Song;
import utilsAndFakes.CommuneMethods;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class FrontSongRepoSocketTest extends CommuneMethods {

    static ISongRepository songRepo;
    static CommuneMethods communeMethods;

    public FrontSongRepoSocketTest() throws IOException {
    }

    @BeforeEach
    void setup() throws IOException {
        communeMethods = new CommuneMethods() {};
        songRepo = communeMethods.startServerAndInitRepo(() -> initializer.frontSongRepo);
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
