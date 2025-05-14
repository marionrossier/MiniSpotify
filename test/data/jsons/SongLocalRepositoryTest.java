package data.jsons;

import serverSide.entities.MusicGender;
import serverSide.entities.Song;
import serverSide.repositoriesPattern.IAudioRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utilsAndFakes.CommuneMethods;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SongLocalRepositoryTest extends CommuneMethods{

    private IAudioRepository audioRepository;

    public SongLocalRepositoryTest() throws IOException {
    }

    @BeforeEach
    void setUp(){
    }

    @AfterEach
    void tearDown() {
        if (tempSongsFile.exists()) {
            tempSongsFile.delete();
        }
        if (tempArtistFile.exists()) {
            tempArtistFile.delete();
        }
    }

    @Test
    void addSong_shouldSaveTheSong() {
        // Arrange
        Song song = createTestSong(1, "Test Song", "Test Artist", MusicGender.POP,
                artistLocalRepository);

        // Act
        songLocalRepository.addSong(song);

        // Assert
        List<Song> songs = songLocalRepository.getAllSongs();
        assertEquals(1, songs.size());
        assertEquals("Test Song", songs.get(0).getTitle());
        assertEquals("Test Artist", artistLocalRepository.getArtistById(songs.get(0).getArtistId()).getArtistName());
    }

    @Test
    void getSongById_shouldFindTheSong() {
        // Arrange
        Song song = createTestSong(1, "Test Song", "Test Artist", MusicGender.POP,
                artistLocalRepository);
        songLocalRepository.addSong(song);

        // Act
        Song result = songLocalRepository.getSongById(song.getSongId());

        // Assert
        assertNotNull(result);
        assertEquals("Test Song", result.getTitle());
    }

    @Test
    void getSongsByTitle_shouldReturnMatchingSongs() {
        // Arrange
        Song songOne = createTestSong(1, "Love Song", "Artist One", MusicGender.POP,
                artistLocalRepository);
        Song songTwo = createTestSong(2, "Rock Song", "Artist Two", MusicGender.ROCK,
                artistLocalRepository);
        Song songThree = createTestSong(3, "Another Love Song", "Artist Three", MusicGender.POP,
                artistLocalRepository);
        songLocalRepository.addSong(songOne);
        songLocalRepository.addSong(songTwo);
        songLocalRepository.addSong(songThree);

        // Act
        LinkedList<Song> result = songLocalRepository.getSongsByTitle("Love");

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(s -> s.getTitle().equals("Love Song")));
        assertTrue(result.stream().anyMatch(s -> s.getTitle().equals("Another Love Song")));
    }

    @Test
    void getSongsByArtist_shouldReturnMatchingSongs() {
        // Arrange
        Song songOne = createTestSong(1, "Song One", "John Doe", MusicGender.POP,
                artistLocalRepository);
        Song songTwo = createTestSong(2, "Song Two", "Jane Doe", MusicGender.ROCK,
                artistLocalRepository);
        Song songThree = createTestSong(3, "Song Three", "John Smith", MusicGender.DISCO,
                artistLocalRepository);
        songLocalRepository.addSong(songOne);
        songLocalRepository.addSong(songTwo);
        songLocalRepository.addSong(songThree);

        // Act
        List<Song> result = songLocalRepository.getSongsByArtist("John");

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(s -> artistLocalRepository.getArtistById(s.getArtistId()).getArtistName().equals("John Doe")));
        assertTrue(result.stream().anyMatch(s -> artistLocalRepository.getArtistById(s.getArtistId()).getArtistName().equals("John Smith")));
    }

    @Test
    void getSongsByGender_shouldReturnMatchingSongs() {
        // Arrange
        Song songOne = createTestSong(1, "Pop Song 1", "Artist One", MusicGender.POP,
                artistLocalRepository);
        Song songTwo = createTestSong(2, "Rock Song", "Artist Two", MusicGender.ROCK,
                artistLocalRepository);
        Song songThree = createTestSong(3, "Pop Song 2", "Artist Three", MusicGender.POP,
                artistLocalRepository);
        songLocalRepository.addSong(songOne);
        songLocalRepository.addSong(songTwo);
        songLocalRepository.addSong(songThree);

        // Act
        List<Song> result = songLocalRepository.getSongsByGender(MusicGender.POP);

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(s -> s.getGender() == MusicGender.POP));
    }

    @Test
    void getAllSongs_withEmptyRepository_shouldReturnEmptyList() {
        // Act
        List<Song> result = songLocalRepository.getAllSongs();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
