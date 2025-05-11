package data.jsons;

import fakes.FakeAudioRepository;
import serverSide.entities.MusicGender;
import serverSide.entities.Song;
import serverSide.repositories.ArtistLocalRepository;
import serverSide.repositories.IAudioRepository;
import serverSide.repositories.SongLocalRepository;
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

class SongLocalRepositoryTest {

    private File tempFileSong;
    private SongLocalRepository songLocalRepository;
    private File tempFileArtist;
    private ArtistLocalRepository artistLocalRepository;
    private IAudioRepository audioRepository;
    private CommuneMethods communeMethods = new CommuneMethods();

    @BeforeEach
    void setUp() throws IOException {
        tempFileSong = Files.createTempFile("songs", ".json").toFile();
        songLocalRepository = new SongLocalRepository(tempFileSong.getAbsolutePath());
        tempFileArtist = Files.createTempFile("artist", ".json").toFile();
        artistLocalRepository = new ArtistLocalRepository(tempFileArtist.getAbsolutePath());
        audioRepository = new FakeAudioRepository();
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
        Song song = communeMethods.createTestSong(1, "Test Song", "Test Artist", MusicGender.POP,
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
    void removeSongById_shouldDeleteTheSong() {
        // Arrange
        Song songOne = communeMethods.createTestSong(1, "Song One", "Artist One", MusicGender.POP,
                artistLocalRepository);
        Song songTwo = communeMethods.createTestSong(2, "Song Two", "Artist Two", MusicGender.ROCK,
                artistLocalRepository);
        songLocalRepository.addSong(songOne);
        songLocalRepository.addSong(songTwo);

        // Act
        songLocalRepository.removeSongById(songOne.getSongId());

        // Assert
        List<Song> result = songLocalRepository.getAllSongs();
        assertEquals(1, result.size());
        assertEquals(songTwo.getSongId(), result.get(0).getSongId());
    }

    @Test
    void getSongById_shouldFindTheSong() {
        // Arrange
        Song song = communeMethods.createTestSong(1, "Test Song", "Test Artist", MusicGender.POP,
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
        Song songOne = communeMethods.createTestSong(1, "Love Song", "Artist One", MusicGender.POP,
                artistLocalRepository);
        Song songTwo = communeMethods.createTestSong(2, "Rock Song", "Artist Two", MusicGender.ROCK,
                artistLocalRepository);
        Song songThree = communeMethods.createTestSong(3, "Another Love Song", "Artist Three", MusicGender.POP,
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
        Song songOne = communeMethods.createTestSong(1, "Song One", "John Doe", MusicGender.POP,
                artistLocalRepository);
        Song songTwo = communeMethods.createTestSong(2, "Song Two", "Jane Doe", MusicGender.ROCK,
                artistLocalRepository);
        Song songThree = communeMethods.createTestSong(3, "Song Three", "John Smith", MusicGender.DISCO,
                artistLocalRepository);
        songLocalRepository.addSong(songOne);
        songLocalRepository.addSong(songTwo);
        songLocalRepository.addSong(songThree);

        // Act
        List<Song> result = songLocalRepository.getSongsByArtist("John", artistLocalRepository);

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(s -> artistLocalRepository.getArtistById(s.getArtistId()).getArtistName().equals("John Doe")));
        assertTrue(result.stream().anyMatch(s -> artistLocalRepository.getArtistById(s.getArtistId()).getArtistName().equals("John Smith")));
    }

    @Test
    void getSongsByGender_shouldReturnMatchingSongs() {
        // Arrange
        Song songOne = communeMethods.createTestSong(1, "Pop Song 1", "Artist One", MusicGender.POP,
                artistLocalRepository);
        Song songTwo = communeMethods.createTestSong(2, "Rock Song", "Artist Two", MusicGender.ROCK,
                artistLocalRepository);
        Song songThree = communeMethods.createTestSong(3, "Pop Song 2", "Artist Three", MusicGender.POP,
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
    void getSongFilePath_shouldReturnCorrectPath() {
        // Assert
        assertEquals(tempFileSong.getAbsolutePath(), songLocalRepository.getSongFilePath());
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
