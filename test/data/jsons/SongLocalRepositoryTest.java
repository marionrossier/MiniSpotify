package data.jsons;

import serverSide.entities.MusicGender;
import serverSide.entities.Song;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utilsAndFakes.CommuneMethods;
import utilsAndFakes.Initializer;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SongLocalRepositoryTest {

    private CommuneMethods communeMethods;
    private Initializer initializer;

    @BeforeEach
    void setUp(){
        communeMethods = new CommuneMethods();
        initializer = communeMethods.initializer;
    }

    @AfterEach
    void tearDown() {
        if (initializer.tempSongsFile.exists()) {
            initializer.tempSongsFile.delete();
        }
        if (initializer.tempArtistFile.exists()) {
            initializer.tempArtistFile.delete();
        }
    }

    @Test
    void addSong_shouldSaveTheSong() {
        // Arrange
        Song song = communeMethods.createTestSong(1, "Test Song", "Test Artist", MusicGender.POP,
                initializer.artistLocalRepository);

        // Act
        initializer.songLocalRepository.addSong(song);

        // Assert
        List<Song> songs = initializer.songLocalRepository.getAllSongs();
        assertEquals(1, songs.size());
        assertEquals("Test Song", songs.get(0).getTitle());
        assertEquals("Test Artist", initializer.artistLocalRepository.getArtistById(songs.get(0).getArtistId()).getArtistName());
    }

    @Test
    void getSongById_shouldFindTheSong() {
        // Arrange
        Song song = communeMethods.createTestSong(1, "Test Song", "Test Artist", MusicGender.POP,
                initializer.artistLocalRepository);
        initializer.songLocalRepository.addSong(song);

        // Act
        Song result = initializer.songLocalRepository.getSongById(song.getSongId());

        // Assert
        assertNotNull(result);
        assertEquals("Test Song", result.getTitle());
    }

    @Test
    void getSongsByTitle_shouldReturnMatchingSongs() {
        // Arrange
        Song songOne = communeMethods.createTestSong(1, "Love Song", "Artist One", MusicGender.POP,
                initializer.artistLocalRepository);
        Song songTwo = communeMethods.createTestSong(2, "Rock Song", "Artist Two", MusicGender.ROCK,
                initializer.artistLocalRepository);
        Song songThree = communeMethods.createTestSong(3, "Another Love Song", "Artist Three", MusicGender.POP,
                initializer.artistLocalRepository);
        initializer.songLocalRepository.addSong(songOne);
        initializer.songLocalRepository.addSong(songTwo);
        initializer.songLocalRepository.addSong(songThree);

        // Act
        LinkedList<Song> result = initializer.songLocalRepository.getSongsByTitle("Love");

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(s -> s.getTitle().equals("Love Song")));
        assertTrue(result.stream().anyMatch(s -> s.getTitle().equals("Another Love Song")));
    }

    @Test
    void getSongsByArtist_shouldReturnMatchingSongs() {
        // Arrange
        Song songOne = communeMethods.createTestSong(1, "Song One", "John Doe", MusicGender.POP,
                initializer.artistLocalRepository);
        Song songTwo = communeMethods.createTestSong(2, "Song Two", "Jane Doe", MusicGender.ROCK,
                initializer.artistLocalRepository);
        Song songThree = communeMethods.createTestSong(3, "Song Three", "John Smith", MusicGender.DISCO,
                initializer.artistLocalRepository);
        initializer.songLocalRepository.addSong(songOne);
        initializer.songLocalRepository.addSong(songTwo);
        initializer.songLocalRepository.addSong(songThree);

        // Act
        List<Song> result = initializer.songLocalRepository.getSongsByArtist("John");

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(s -> initializer.artistLocalRepository.getArtistById(s.getArtistId()).getArtistName().equals("John Doe")));
        assertTrue(result.stream().anyMatch(s -> initializer.artistLocalRepository.getArtistById(s.getArtistId()).getArtistName().equals("John Smith")));
    }

    @Test
    void getSongsByGender_shouldReturnMatchingSongs() {
        // Arrange
        Song songOne = communeMethods.createTestSong(1, "Pop Song 1", "Artist One", MusicGender.POP,
                initializer.artistLocalRepository);
        Song songTwo = communeMethods.createTestSong(2, "Rock Song", "Artist Two", MusicGender.ROCK,
                initializer.artistLocalRepository);
        Song songThree = communeMethods.createTestSong(3, "Pop Song 2", "Artist Three", MusicGender.POP,
                initializer.artistLocalRepository);
        initializer.songLocalRepository.addSong(songOne);
        initializer.songLocalRepository.addSong(songTwo);
        initializer.songLocalRepository.addSong(songThree);

        // Act
        List<Song> result = initializer.songLocalRepository.getSongsByGender(MusicGender.POP);

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(s -> s.getGender() == MusicGender.POP));
    }

    @Test
    void getAllSongs_withEmptyRepository_shouldReturnEmptyList() {
        // Act
        List<Song> result = initializer.songLocalRepository.getAllSongs();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
