package data.jsons;

import data.entities.Artist;
import data.entities.MusicGender;
import data.entities.Song;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SongRepositoryTest {

    private File tempFile;
    private SongRepository repo;

    @BeforeEach
    void setUp() throws IOException {
        tempFile = Files.createTempFile("songs", ".json").toFile();
        repo = new SongRepository(tempFile.getAbsolutePath());
    }

    @AfterEach
    void tearDown() {
        if (tempFile.exists()) {
            tempFile.delete();
        }
    }

    private Song createTestSong(int id, String title, String artistName, MusicGender gender) {
        Song song = new Song();
        song.setSongId(id);
        song.setTitle(title);
        
        Artist artist = new Artist(artistName);
        artist.setArtistId(100 + id); // Unique ID for artist
        song.setArtist(artist);
        
        song.setAlbum("Test Album");
        song.setDurationSeconds(180);
        song.setGender(gender);
        song.setAudioFilePath("path/to/song" + id + ".mp3");
        
        return song;
    }

    @Test
    void addSong_shouldSaveTheSong() {
        // Arrange
        Song song = createTestSong(1, "Test Song", "Test Artist", MusicGender.POP);

        // Act
        repo.addSong(song);

        // Assert
        List<Song> songs = repo.getAllSongs();
        assertEquals(1, songs.size());
        assertEquals("Test Song", songs.get(0).getTitle());
        assertEquals("Test Artist", songs.get(0).getArtist().getArtistName());
    }

    @Test
    void removeSongById_shouldDeleteTheSong() {
        // Arrange
        Song songOne = createTestSong(1, "Song One", "Artist One", MusicGender.POP);
        Song songTwo = createTestSong(2, "Song Two", "Artist Two", MusicGender.ROCK);
        repo.addSong(songOne);
        repo.addSong(songTwo);

        // Act
        repo.removeSongById(songOne.getSongId());

        // Assert
        List<Song> result = repo.getAllSongs();
        assertEquals(1, result.size());
        assertEquals(songTwo.getSongId(), result.get(0).getSongId());
    }

    @Test
    void getSongById_shouldFindTheSong() {
        // Arrange
        Song song = createTestSong(1, "Test Song", "Test Artist", MusicGender.POP);
        repo.addSong(song);

        // Act
        Song result = repo.getSongById(song.getSongId());

        // Assert
        assertNotNull(result);
        assertEquals("Test Song", result.getTitle());
    }

    @Test
    void getSongsByTitle_shouldReturnMatchingSongs() {
        // Arrange
        Song songOne = createTestSong(1, "Love Song", "Artist One", MusicGender.POP);
        Song songTwo = createTestSong(2, "Rock Song", "Artist Two", MusicGender.ROCK);
        Song songThree = createTestSong(3, "Another Love Song", "Artist Three", MusicGender.POP);
        repo.addSong(songOne);
        repo.addSong(songTwo);
        repo.addSong(songThree);

        // Act
        LinkedList<Song> result = repo.getSongsByTitle("Love");

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(s -> s.getTitle().equals("Love Song")));
        assertTrue(result.stream().anyMatch(s -> s.getTitle().equals("Another Love Song")));
    }

    @Test
    void getSongsByArtist_shouldReturnMatchingSongs() {
        // Arrange
        Song songOne = createTestSong(1, "Song One", "John Doe", MusicGender.POP);
        Song songTwo = createTestSong(2, "Song Two", "Jane Doe", MusicGender.ROCK);
        Song songThree = createTestSong(3, "Song Three", "John Smith", MusicGender.DISCO);
        repo.addSong(songOne);
        repo.addSong(songTwo);
        repo.addSong(songThree);

        // Act
        List<Song> result = repo.getSongsByArtist("John");

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(s -> s.getArtist().getArtistName().equals("John Doe")));
        assertTrue(result.stream().anyMatch(s -> s.getArtist().getArtistName().equals("John Smith")));
    }

    @Test
    void getSongsByGender_shouldReturnMatchingSongs() {
        // Arrange
        Song songOne = createTestSong(1, "Pop Song 1", "Artist One", MusicGender.POP);
        Song songTwo = createTestSong(2, "Rock Song", "Artist Two", MusicGender.ROCK);
        Song songThree = createTestSong(3, "Pop Song 2", "Artist Three", MusicGender.POP);
        repo.addSong(songOne);
        repo.addSong(songTwo);
        repo.addSong(songThree);

        // Act
        List<Song> result = repo.getSongsByGender(MusicGender.POP);

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(s -> s.getGender() == MusicGender.POP));
    }

    @Test
    void getFilePath_shouldReturnCorrectPath() {
        // Assert
        assertEquals(tempFile.getAbsolutePath(), repo.getFilePath());
    }

    @Test
    void getAllSongs_withEmptyRepository_shouldReturnEmptyList() {
        // Act
        List<Song> result = repo.getAllSongs();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
