package player_StatePattern.playlist_player;

import clientSide.services.*;
import fakes.FakeAudioRepository;
import serverSide.entities.PlaylistEnum;
import serverSide.entities.Song;
import clientSide.player_StatePattern.playlist_player.PlaylistPlayer;
import serverSide.repositories.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import fakes.FakeMusicPlayer;
import serverSide.entities.Playlist;
import services.*;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class PlaylistPlayerTest {

    private PlaylistPlayer playlistPlayer;
    private FakeMusicPlayer fakeMusicPlayer;
    private File songTempFile;
    private File playlistTempFile;
    private File artistTempFile;
    private SongLocalRepository songLocalRepository;
    private SongService songService;
    private ArtistService artistService;
    private UserLocalRepository userLocalRepository;
    private UserService userService;
    private PlaylistLocalRepository playlistLocalRepository;
    private PlaylistServices playlistServices;
    private ArtistLocalRepository artistLocalRepository;
    private IAudioRepository audioRepository;
    private CommuneMethods communeMethods = new CommuneMethods();
    private PrintService printService;

    @BeforeEach
    void setUp() throws IOException {
        // Create Cookies_SingeltonPattern instance
        Cookies_SingletonPattern.setInstance(400953820); //testUsers

        // Create temporary files for repositories
        songTempFile = Files.createTempFile("songs", ".json").toFile();
        playlistTempFile = Files.createTempFile("playlists", ".json").toFile();
        artistTempFile = Files.createTempFile("artist",".json").toFile();

        // Initialize repositories with temp files
        songLocalRepository = new SongLocalRepository(songTempFile.getAbsolutePath());
        songService = new SongService(songLocalRepository);
        playlistLocalRepository = new PlaylistLocalRepository(playlistTempFile.getAbsolutePath());
        playlistServices = new PlaylistServices(playlistLocalRepository, userLocalRepository, songLocalRepository);
        userLocalRepository = new UserLocalRepository();
        userService = new UserService(userLocalRepository);
        artistService = new ArtistService(artistLocalRepository);
        artistLocalRepository = new ArtistLocalRepository(artistTempFile.getAbsolutePath());
        audioRepository = new FakeAudioRepository();
        printService = new PrintService(songService, artistService, playlistServices, userService);

        // Create test songs
        Song song1 = createSong(1, "Song 1", "song1.mp3");
        Song song2 = createSong(2, "Song 2", "song2.mp3");
        Song song3 = createSong(3, "Song 3", "song3.mp3");
        
        // Add songs to repository
        songLocalRepository.addSong(song1);
        songLocalRepository.addSong(song2);
        songLocalRepository.addSong(song3);
        
        // Create a test playlist
        Playlist playlist = new Playlist("Test Playlist", PlaylistEnum.PRIVATE);
        playlist.setPlaylistId(1);
        playlistLocalRepository.savePlaylist(playlist);

        communeMethods.addSongToPlaylist(playlist.getPlaylistId(), song1.getSongId(), playlistLocalRepository, playlistServices);
        communeMethods.addSongToPlaylist(playlist.getPlaylistId(), song2.getSongId(), playlistLocalRepository, playlistServices);
        communeMethods.addSongToPlaylist(playlist.getPlaylistId(), song3.getSongId(), playlistLocalRepository, playlistServices);
        
        // Create a FakeMusicPlayer for testing
        fakeMusicPlayer = new FakeMusicPlayer();
        
        // Instantiate the PlaylistPlayer with the fake player and repositories
        playlistPlayer = new PlaylistPlayer(fakeMusicPlayer, songLocalRepository, playlistLocalRepository,
                audioRepository, userLocalRepository);
    }
    
    @AfterEach
    void tearDown() {
        // Delete temporary files
        if (songTempFile != null && songTempFile.exists()) {
            songTempFile.delete();
        }
        if (playlistTempFile != null && playlistTempFile.exists()) {
            playlistTempFile.delete();
        }
    }
    
    private Song createSong(int id, String title, String fileName) {
        Song song = new Song();
        song.setSongId(id);
        song.setTitle(title);
        song.setAudioFileName(fileName);
        return song;
    }

    @Test
    void testPlay() {
        // Act
        playlistPlayer.play(1, 1);

        // Assert
        assertEquals(1, playlistPlayer.getCurrentPlaylistId());
        assertEquals(1, playlistPlayer.getCurrentSongId());
        assertTrue(fakeMusicPlayer.isPlaying());
        assertEquals("song1.mp3", fakeMusicPlayer.getCurrentSongFileName());
    }

    @Test
    void testPlayOrPause (){
        // First play a song
        playlistPlayer.play(1, 1);
        assertTrue(fakeMusicPlayer.isPlaying());
        songService.setCurrentSongId(playlistPlayer.getCurrentSongId());

        // Test pause
        playlistPlayer.playOrPause(songService.getCurrentSongId());
        assertFalse(fakeMusicPlayer.isPlaying());

        // Test resume
        playlistPlayer.playOrPause(songService.getCurrentSongId());
        assertTrue(fakeMusicPlayer.isPlaying());
    }
    @Test
    void testPauseAndResume() {
        // First play a song
        playlistPlayer.play(1, 1);
        assertTrue(fakeMusicPlayer.isPlaying());
        songService.setCurrentSongId(playlistPlayer.getCurrentSongId());
        
        // Test pause
        playlistPlayer.pause();
        assertFalse(fakeMusicPlayer.isPlaying());
        
        // Test resume
        playlistPlayer.resume(songService.getCurrentSongId());
        assertTrue(fakeMusicPlayer.isPlaying());
    }

    @Test
    void testPlayback() {
        // First play a song
        playlistPlayer.play(1, 2);
        assertEquals("song2.mp3", fakeMusicPlayer.getCurrentSongFileName());
        
        // Test playback
        playlistPlayer.playback();
        assertTrue(fakeMusicPlayer.isPlaying());
        assertEquals("song2.mp3", fakeMusicPlayer.getCurrentSongFileName());
    }

    @Test
    void testNextInSequentialMode() {
        // Start with sequential mode (default)
        playlistPlayer.setSequentialMode();
        playlistPlayer.play(1, 1);
        assertEquals(1, playlistPlayer.getCurrentSongId());
        
        // Test next song
        fakeMusicPlayer.triggerSongEnd();
        assertEquals(2, playlistPlayer.getCurrentSongId());
        assertEquals("song2.mp3", fakeMusicPlayer.getCurrentSongFileName());
        
        // Test next song again
        fakeMusicPlayer.triggerSongEnd();
        assertEquals(3, playlistPlayer.getCurrentSongId());
        assertEquals("song3.mp3", fakeMusicPlayer.getCurrentSongFileName());

        // Test next song loop
        fakeMusicPlayer.triggerSongEnd();
        assertEquals(1, playlistPlayer.getCurrentSongId());
        assertEquals("song1.mp3", fakeMusicPlayer.getCurrentSongFileName());
    }

    @Test
    void testPrevious() {
        // Play a song and advance to the next one
        playlistPlayer.play(1, 1);
        fakeMusicPlayer.triggerSongEnd();
        fakeMusicPlayer.triggerSongEnd();
        fakeMusicPlayer.triggerSongEnd();
        assertEquals(1, playlistPlayer.getCurrentSongId());
        
        // Test previous
        playlistPlayer.previous();
        assertEquals(3, playlistPlayer.getCurrentSongId());
        assertEquals("song3.mp3", fakeMusicPlayer.getCurrentSongFileName());

        // Test previous
        playlistPlayer.previous();
        assertEquals(2, playlistPlayer.getCurrentSongId());
        assertEquals("song2.mp3", fakeMusicPlayer.getCurrentSongFileName());

        // Test previous
        playlistPlayer.previous();
        assertEquals(1, playlistPlayer.getCurrentSongId());
        assertEquals("song1.mp3", fakeMusicPlayer.getCurrentSongFileName());
    }

    @Test
    void testPreviousWithEmptyHistory() {
        // Just starting playing
        playlistPlayer.play(1, 1);
        
        // Test previous with no history
        playlistPlayer.previous();
        // Should still be on song 1
        assertEquals(1, playlistPlayer.getCurrentSongId());
    }

    @Test
    void testShuffleMode() {
        // Enable shuffle mode
        playlistPlayer.setShuffleMode();
        playlistPlayer.play(1, 1);
        
        // Since shuffle is random, we'll just verify that next() doesn't crash
        // and returns a valid song in the playlist
        fakeMusicPlayer.triggerSongEnd();
        int songId = playlistPlayer.getCurrentSongId();
        assertTrue(songId >= 1 && songId <= 3, "Shuffle should return a valid song ID");
        
        // Do another next and ensure we get a valid song path
        fakeMusicPlayer.triggerSongEnd();
        assertNotNull(fakeMusicPlayer.getCurrentSongFileName());
    }

    @Test
    void testRepeatMode() {
        // Enable repeat mode
        playlistPlayer.setRepeatMode();
        playlistPlayer.play(1, 2);
        assertEquals(2, playlistPlayer.getCurrentSongId());
        
        // In repeat mode, next should play the same song again
        fakeMusicPlayer.triggerSongEnd();
        assertEquals(2, playlistPlayer.getCurrentSongId());
        assertEquals("song2.mp3", fakeMusicPlayer.getCurrentSongFileName());

        // In repeat mode, next should play the same song again
        fakeMusicPlayer.triggerSongEnd();
        assertEquals(2, playlistPlayer.getCurrentSongId());
        assertEquals("song2.mp3", fakeMusicPlayer.getCurrentSongFileName());
    }

    @Test
    void testAutoAdvanceWhenSongEnds() {
        playlistPlayer.setSequentialMode();
        playlistPlayer.play(1, 1);
        
        // Simulate song ending (which should trigger next())
        fakeMusicPlayer.triggerSongEnd();
        
        // Should auto-advance to song 2
        assertEquals(2, playlistPlayer.getCurrentSongId());
        assertEquals("song2.mp3", fakeMusicPlayer.getCurrentSongFileName());
    }

    @Test
    void testSetModes() {
        // Test switching between modes
        playlistPlayer.play(1, 1);
        
        // Test sequential mode
        playlistPlayer.setSequentialMode();
        fakeMusicPlayer.triggerSongEnd();
        assertEquals(2, playlistPlayer.getCurrentSongId());
        
        // Test repeat mode
        playlistPlayer.setRepeatMode();
        fakeMusicPlayer.triggerSongEnd();
        assertEquals(2, playlistPlayer.getCurrentSongId());
        
        // Test shuffle mode
        playlistPlayer.setShuffleMode();
        fakeMusicPlayer.triggerSongEnd();
        // We can't assert exact song ID due to randomness, but we can verify it's a valid song
        assertNotNull(fakeMusicPlayer.getCurrentSongFileName());
    }
}
