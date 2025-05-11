package data.jsons;

import serverSide.entities.MusicGender;
import serverSide.entities.Song;
import serverSide.repositories.ArtistRepository;
import serverSide.repositories.SongRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.CommuneMethods;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SongRepositoryTest {

    private File tempFileSong;
    private SongRepository songRepository;
    private File tempFileArtist;
    private ArtistRepository artistRepository;
    private CommuneMethods communeMethods = new CommuneMethods();

    @BeforeEach
    void setUp() throws IOException {
        tempFileSong = Files.createTempFile("songs", ".json").toFile();
        songRepository = new SongRepository(tempFileSong.getAbsolutePath());
        tempFileArtist = Files.createTempFile("artist", ".json").toFile();
        artistRepository = new ArtistRepository(tempFileArtist.getAbsolutePath());
    }

    @AfterEach
    void tearDown() {
        if (tempFileSong.exists()) {
            tempFileSong.delete();
        }
        if (tempFileArtist.exists()) {
            tempFileArtist.delete();
        }
    }

    @Test
    void addSong_shouldSaveTheSong() {
        // Arrange
        Song song = communeMethods.createTestSong(1, "Test Song", "Test Artist", MusicGender.POP, artistRepository);

        // Act
        songRepository.addSong(song);

        // Assert
        List<Song> songs = songRepository.getAllSongs();
        assertEquals(1, songs.size());
        assertEquals("Test Song", songs.get(0).getTitle());
        assertEquals("Test Artist", artistRepository.getArtistById(songs.get(0).getArtistId()).getArtistName());
    }

    @Test
    void removeSongById_shouldDeleteTheSong() {
        // Arrange
        Song songOne = communeMethods.createTestSong(1, "Song One", "Artist One", MusicGender.POP, artistRepository);
        Song songTwo = communeMethods.createTestSong(2, "Song Two", "Artist Two", MusicGender.ROCK, artistRepository);
        songRepository.addSong(songOne);
        songRepository.addSong(songTwo);

        // Act
        songRepository.removeSongById(songOne.getSongId());

        // Assert
        List<Song> result = songRepository.getAllSongs();
        assertEquals(1, result.size());
        assertEquals(songTwo.getSongId(), result.get(0).getSongId());
    }

    @Test
    void getSongById_shouldFindTheSong() {
        // Arrange
        Song song = communeMethods.createTestSong(1, "Test Song", "Test Artist", MusicGender.POP, artistRepository);
        songRepository.addSong(song);

        // Act
        Song result = songRepository.getSongById(song.getSongId());

        // Assert
        assertNotNull(result);
        assertEquals("Test Song", result.getTitle());
    }

    @Test
    void getSongsByTitle_shouldReturnMatchingSongs() {
        // Arrange
        Song songOne = communeMethods.createTestSong(1, "Love Song", "Artist One", MusicGender.POP, artistRepository);
        Song songTwo = communeMethods.createTestSong(2, "Rock Song", "Artist Two", MusicGender.ROCK, artistRepository);
        Song songThree = communeMethods.createTestSong(3, "Another Love Song", "Artist Three", MusicGender.POP, artistRepository);
        songRepository.addSong(songOne);
        songRepository.addSong(songTwo);
        songRepository.addSong(songThree);

        // Act
        LinkedList<Song> result = songRepository.getSongsByTitle("Love");

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(s -> s.getTitle().equals("Love Song")));
        assertTrue(result.stream().anyMatch(s -> s.getTitle().equals("Another Love Song")));
    }

    @Test
    void getSongsByArtist_shouldReturnMatchingSongs() {
        // Arrange
        Song songOne = communeMethods.createTestSong(1, "Song One", "John Doe", MusicGender.POP, artistRepository);
        Song songTwo = communeMethods.createTestSong(2, "Song Two", "Jane Doe", MusicGender.ROCK, artistRepository);
        Song songThree = communeMethods.createTestSong(3, "Song Three", "John Smith", MusicGender.DISCO, artistRepository);
        songRepository.addSong(songOne);
        songRepository.addSong(songTwo);
        songRepository.addSong(songThree);

        // Act
        List<Song> result = songRepository.getSongsByArtist("John", artistRepository);

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(s -> artistRepository.getArtistById(s.getArtistId()).getArtistName().equals("John Doe")));
        assertTrue(result.stream().anyMatch(s -> artistRepository.getArtistById(s.getArtistId()).getArtistName().equals("John Smith")));
    }

    @Test
    void getSongsByGender_shouldReturnMatchingSongs() {
        // Arrange
        Song songOne = communeMethods.createTestSong(1, "Pop Song 1", "Artist One", MusicGender.POP, artistRepository);
        Song songTwo = communeMethods.createTestSong(2, "Rock Song", "Artist Two", MusicGender.ROCK, artistRepository);
        Song songThree = communeMethods.createTestSong(3, "Pop Song 2", "Artist Three", MusicGender.POP, artistRepository);
        songRepository.addSong(songOne);
        songRepository.addSong(songTwo);
        songRepository.addSong(songThree);

        // Act
        List<Song> result = songRepository.getSongsByGender(MusicGender.POP);

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(s -> s.getGender() == MusicGender.POP));
    }

    @Test
    void getFilePath_shouldReturnCorrectPath() {
        // Assert
        assertEquals(tempFileSong.getAbsolutePath(), songRepository.getFilePath());
    }

    @Test
    void getAllSongs_withEmptyRepository_shouldReturnEmptyList() {
        // Act
        List<Song> result = songRepository.getAllSongs();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
