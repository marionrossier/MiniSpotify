package jsons;

import serverSide.entities.*;
import utilsAndFakes.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SongLocalRepositoryTest {

    private TestHelper testHelper;
    private DependencyProvider dependencyProvider;

    @BeforeEach
    void setUp(){
        testHelper = new TestHelper(45000);
        dependencyProvider = testHelper.dependencyProvider;
    }

    @AfterEach
    void tearDown() {
        if (dependencyProvider.tempSongsFile.exists()) {
            dependencyProvider.tempSongsFile.delete();
        }
        if (dependencyProvider.tempArtistFile.exists()) {
            dependencyProvider.tempArtistFile.delete();
        }
    }

    @Test
    void addSong_shouldSaveTheSong() {
        // Arrange
        Song song = testHelper.createTestSong(1, "Test Song", "Test Artist", MusicGender.POP,
                dependencyProvider.artistLocalRepository);

        // Act
        dependencyProvider.songLocalRepository.addSong(song);

        // Assert
        List<Song> songs = dependencyProvider.songLocalRepository.getAllSongs();
        assertEquals(1, songs.size());
        assertEquals("Test Song", songs.get(0).getTitle());
        assertEquals("Test Artist", dependencyProvider.artistLocalRepository.getArtistById(songs.get(0).getArtistId()).getArtistName());
    }

    @Test
    void getSongById_shouldFindTheSong() {
        // Arrange
        Song song = testHelper.createTestSong(1, "Test Song", "Test Artist", MusicGender.POP,
                dependencyProvider.artistLocalRepository);
        dependencyProvider.songLocalRepository.addSong(song);

        // Act
        Song result = dependencyProvider.songLocalRepository.getSongById(song.getSongId());

        // Assert
        assertNotNull(result);
        assertEquals("Test Song", result.getTitle());
    }

    @Test
    void getSongsByTitle_shouldReturnMatchingSongs() {
        // Arrange
        Song songOne = testHelper.createTestSong(1, "Love Song", "Artist One", MusicGender.POP,
                dependencyProvider.artistLocalRepository);
        Song songTwo = testHelper.createTestSong(2, "Rock Song", "Artist Two", MusicGender.ROCK,
                dependencyProvider.artistLocalRepository);
        Song songThree = testHelper.createTestSong(3, "Another Love Song", "Artist Three", MusicGender.POP,
                dependencyProvider.artistLocalRepository);
        dependencyProvider.songLocalRepository.addSong(songOne);
        dependencyProvider.songLocalRepository.addSong(songTwo);
        dependencyProvider.songLocalRepository.addSong(songThree);

        // Act
        LinkedList<Song> result = dependencyProvider.songLocalRepository.getSongsByTitle("Love");

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(s -> s.getTitle().equals("Love Song")));
        assertTrue(result.stream().anyMatch(s -> s.getTitle().equals("Another Love Song")));
    }

    @Test
    void getSongsByArtist_shouldReturnMatchingSongs() {
        // Arrange
        Song songOne = testHelper.createTestSong(1, "Song One", "John Doe", MusicGender.POP,
                dependencyProvider.artistLocalRepository);
        Song songTwo = testHelper.createTestSong(2, "Song Two", "Jane Doe", MusicGender.ROCK,
                dependencyProvider.artistLocalRepository);
        Song songThree = testHelper.createTestSong(3, "Song Three", "John Smith", MusicGender.DISCO,
                dependencyProvider.artistLocalRepository);
        dependencyProvider.songLocalRepository.addSong(songOne);
        dependencyProvider.songLocalRepository.addSong(songTwo);
        dependencyProvider.songLocalRepository.addSong(songThree);

        // Act
        List<Song> result = dependencyProvider.songLocalRepository.getSongsByArtist("John");

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(s -> dependencyProvider.artistLocalRepository.getArtistById(s.getArtistId()).getArtistName().equals("John Doe")));
        assertTrue(result.stream().anyMatch(s -> dependencyProvider.artistLocalRepository.getArtistById(s.getArtistId()).getArtistName().equals("John Smith")));
    }

    @Test
    void getSongsByGender_shouldReturnMatchingSongs() {
        // Arrange
        Song songOne = testHelper.createTestSong(1, "Pop Song 1", "Artist One", MusicGender.POP,
                dependencyProvider.artistLocalRepository);
        Song songTwo = testHelper.createTestSong(2, "Rock Song", "Artist Two", MusicGender.ROCK,
                dependencyProvider.artistLocalRepository);
        Song songThree = testHelper.createTestSong(3, "Pop Song 2", "Artist Three", MusicGender.POP,
                dependencyProvider.artistLocalRepository);
        dependencyProvider.songLocalRepository.addSong(songOne);
        dependencyProvider.songLocalRepository.addSong(songTwo);
        dependencyProvider.songLocalRepository.addSong(songThree);

        // Act
        List<Song> result = dependencyProvider.songLocalRepository.getSongsByGender(MusicGender.POP);

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(s -> s.getGender() == MusicGender.POP));
    }

    @Test
    void getAllSongs_withEmptyRepository_shouldReturnEmptyList() {
        // Act
        List<Song> result = dependencyProvider.songLocalRepository.getAllSongs();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
