package player_StatePattern.playlist_player;

import clientSide.services.*;
import utilsAndFakes.CommuneMethods;
import serverSide.entities.PlaylistEnum;
import serverSide.entities.Song;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import serverSide.entities.Playlist;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

public class PlaylistPlayerTest extends CommuneMethods{

    public PlaylistPlayerTest() throws IOException {
        super();
    }

    @BeforeEach
    void setUp() throws IOException {
        // Create Cookies_SingeltonPattern instance
        Cookies_SingletonPattern.setInstance(400953820); //testUsers

        // Create test songs
        Song song1 = createSong(1, "Song 1", "song1.mp3");
        Song song2 = createSong(2, "Song 2", "song2.mp3");
        Song song3 = createSong(3, "Song 3", "song3.mp3");
        
        // Add songs to repository
        initializer.songLocalRepository.addSong(song1);
        initializer.songLocalRepository.addSong(song2);
        initializer.songLocalRepository.addSong(song3);
        
        // Create a test playlist
        Playlist playlist = new Playlist("Test Playlist", PlaylistEnum.PRIVATE);
        playlist.setPlaylistId(1);
        initializer.playlistLocalRepository.savePlaylist(playlist);

        addSongToPlaylist(playlist.getPlaylistId(), song1.getSongId(), initializer.playlistLocalRepository, initializer.playlistService);
        addSongToPlaylist(playlist.getPlaylistId(), song2.getSongId(), initializer.playlistLocalRepository, initializer.playlistService);
        addSongToPlaylist(playlist.getPlaylistId(), song3.getSongId(), initializer.playlistLocalRepository, initializer.playlistService);
    }
    
    @AfterEach
    void tearDown() {
        // Delete temporary files
        if (initializer.tempSongsFile != null && initializer.tempSongsFile.exists()) {
            initializer.tempSongsFile.delete();
        }
        if (initializer.tempPlaylistsFile != null && initializer.tempPlaylistsFile.exists()) {
            initializer.tempPlaylistsFile.delete();
        }
    }

    @Test
    void testPlay() {
        // Act
        initializer.playlistPlayer.play(1, 1);

        // Assert
        assertEquals(1, initializer.playlistPlayer.getCurrentPlaylistId());
        assertEquals(1, initializer.playlistPlayer.getCurrentSongId());
        assertTrue(initializer.fakeMusicPlayer.isPlaying());
        assertEquals("song1.mp3", initializer.fakeMusicPlayer.getCurrentSongFileName());
    }

    @Test
    void testPlayOrPause (){
        // First play a song
        initializer.playlistPlayer.play(1, 1);
        assertTrue(initializer.fakeMusicPlayer.isPlaying());
        initializer.songService.setCurrentSongId(initializer.playlistPlayer.getCurrentSongId());

        // Test pause
        initializer.playlistPlayer.playOrPause(initializer.songService.getCurrentSongId());
        assertFalse(initializer.fakeMusicPlayer.isPlaying());

        // Test resume
        initializer.playlistPlayer.playOrPause(initializer.songService.getCurrentSongId());
        assertTrue(initializer.fakeMusicPlayer.isPlaying());
    }
    @Test
    void testPauseAndResume() {
        // First play a song
        initializer.playlistPlayer.play(1, 1);
        assertTrue(initializer.fakeMusicPlayer.isPlaying());
        initializer.songService.setCurrentSongId(initializer.playlistPlayer.getCurrentSongId());
        
        // Test pause
        initializer.playlistPlayer.pause();
        assertFalse(initializer.fakeMusicPlayer.isPlaying());
        
        // Test resume
        initializer.playlistPlayer.resume(initializer.songService.getCurrentSongId());
        assertTrue(initializer.fakeMusicPlayer.isPlaying());
    }

    @Test
    void testPlayback() {
        // First play a song
        initializer.playlistPlayer.play(1, 2);
        assertEquals("song2.mp3", initializer.fakeMusicPlayer.getCurrentSongFileName());
        
        // Test playback
        initializer.playlistPlayer.playback();
        assertTrue(initializer.fakeMusicPlayer.isPlaying());
        assertEquals("song2.mp3", initializer.fakeMusicPlayer.getCurrentSongFileName());
    }

    @Test
    void testNextInSequentialMode() {
        // Start with sequential mode (default)
        initializer.playlistPlayer.setSequentialMode();
        initializer.playlistPlayer.play(1, 1);
        assertEquals(1, initializer.playlistPlayer.getCurrentSongId());
        
        // Test next song
        initializer.fakeMusicPlayer.triggerSongEnd();
        assertEquals(2, initializer.playlistPlayer.getCurrentSongId());
        assertEquals("song2.mp3", initializer.fakeMusicPlayer.getCurrentSongFileName());
        
        // Test next song again
        initializer.fakeMusicPlayer.triggerSongEnd();
        assertEquals(3, initializer.playlistPlayer.getCurrentSongId());
        assertEquals("song3.mp3", initializer.fakeMusicPlayer.getCurrentSongFileName());

        // Test next song loop
        initializer.fakeMusicPlayer.triggerSongEnd();
        assertEquals(1, initializer.playlistPlayer.getCurrentSongId());
        assertEquals("song1.mp3", initializer.fakeMusicPlayer.getCurrentSongFileName());
    }

    @Test
    void testPrevious() {
        // Play a song and advance to the next one
        initializer.playlistPlayer.play(1, 1);
        initializer.fakeMusicPlayer.triggerSongEnd();
        initializer.fakeMusicPlayer.triggerSongEnd();
        initializer.fakeMusicPlayer.triggerSongEnd();
        assertEquals(1, initializer.playlistPlayer.getCurrentSongId());
        
        // Test previous
        initializer.playlistPlayer.previous();
        assertEquals(3, initializer.playlistPlayer.getCurrentSongId());
        assertEquals("song3.mp3", initializer.fakeMusicPlayer.getCurrentSongFileName());

        // Test previous
        initializer.playlistPlayer.previous();
        assertEquals(2, initializer.playlistPlayer.getCurrentSongId());
        assertEquals("song2.mp3", initializer.fakeMusicPlayer.getCurrentSongFileName());

        // Test previous
        initializer.playlistPlayer.previous();
        assertEquals(1, initializer.playlistPlayer.getCurrentSongId());
        assertEquals("song1.mp3", initializer.fakeMusicPlayer.getCurrentSongFileName());
    }

    @Test
    void testPreviousWithEmptyHistory() {
        // Just starting playing
        initializer.playlistPlayer.play(1, 1);
        
        // Test previous with no history
        initializer.playlistPlayer.previous();
        // Should still be on song 1
        assertEquals(1, initializer.playlistPlayer.getCurrentSongId());
    }

    @Test
    void testShuffleMode() {
        // Enable shuffle mode
        initializer.playlistPlayer.setShuffleMode();
        initializer.playlistPlayer.play(1, 1);
        
        // Since shuffle is random, we'll just verify that next() doesn't crash
        // and returns a valid song in the playlist
        initializer.fakeMusicPlayer.triggerSongEnd();
        int songId = initializer.playlistPlayer.getCurrentSongId();
        assertTrue(songId >= 1 && songId <= 3, "Shuffle should return a valid song ID");
        
        // Do another next and ensure we get a valid song path
        initializer.fakeMusicPlayer.triggerSongEnd();
        assertNotNull(initializer.fakeMusicPlayer.getCurrentSongFileName());
    }

    @Test
    void testRepeatMode() {
        // Enable repeat mode
        initializer.playlistPlayer.setRepeatMode();
        initializer.playlistPlayer.play(1, 2);
        assertEquals(2, initializer.playlistPlayer.getCurrentSongId());
        
        // In repeat mode, next should play the same song again
        initializer.fakeMusicPlayer.triggerSongEnd();
        assertEquals(2, initializer.playlistPlayer.getCurrentSongId());
        assertEquals("song2.mp3", initializer.fakeMusicPlayer.getCurrentSongFileName());

        // In repeat mode, next should play the same song again
        initializer.fakeMusicPlayer.triggerSongEnd();
        assertEquals(2, initializer.playlistPlayer.getCurrentSongId());
        assertEquals("song2.mp3", initializer.fakeMusicPlayer.getCurrentSongFileName());
    }

    @Test
    void testAutoAdvanceWhenSongEnds() {
        initializer.playlistPlayer.setSequentialMode();
        initializer.playlistPlayer.play(1, 1);
        
        // Simulate song ending (which should trigger next())
        initializer.fakeMusicPlayer.triggerSongEnd();
        
        // Should auto-advance to song 2
        assertEquals(2, initializer.playlistPlayer.getCurrentSongId());
        assertEquals("song2.mp3", initializer.fakeMusicPlayer.getCurrentSongFileName());
    }

    @Test
    void testSetModes() {
        // Test switching between modes
        initializer.playlistPlayer.play(1, 1);
        
        // Test sequential mode
        initializer.playlistPlayer.setSequentialMode();
        initializer.fakeMusicPlayer.triggerSongEnd();
        assertEquals(2, initializer.playlistPlayer.getCurrentSongId());
        
        // Test repeat mode
        initializer.playlistPlayer.setRepeatMode();
        initializer.fakeMusicPlayer.triggerSongEnd();
        assertEquals(2, initializer.playlistPlayer.getCurrentSongId());
        
        // Test shuffle mode
        initializer.playlistPlayer.setShuffleMode();
        initializer.fakeMusicPlayer.triggerSongEnd();
        // We can't assert exact song ID due to randomness, but we can verify it's a valid song
        assertNotNull(initializer.fakeMusicPlayer.getCurrentSongFileName());
    }
}
