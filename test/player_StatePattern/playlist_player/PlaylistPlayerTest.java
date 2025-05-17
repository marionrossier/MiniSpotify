package player_StatePattern.playlist_player;

import clientSide.services.*;
import utilsAndFakes.TestHelper;
import serverSide.entities.PlaylistEnum;
import serverSide.entities.Song;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import serverSide.entities.Playlist;
import utilsAndFakes.DependencyProvider;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

public class PlaylistPlayerTest{

    private TestHelper testHelper;
    private DependencyProvider dependencyProvider;

    public PlaylistPlayerTest() {
    }

    @BeforeEach
    void setUp() throws IOException {
        testHelper = new TestHelper();
        dependencyProvider = testHelper.dependencyProvider;

        // Create Cookies_SingeltonPattern instance
        Cookies_SingletonPattern.setInstance(400953820, "tester", "password"); //testUsers

        // Create test songs
        Song song1 = testHelper.createSong(1, "Song 1", "song1.mp3");
        Song song2 = testHelper.createSong(2, "Song 2", "song2.mp3");
        Song song3 = testHelper.createSong(3, "Song 3", "song3.mp3");
        
        // Add songs to repository
        dependencyProvider.songLocalRepository.addSong(song1);
        dependencyProvider.songLocalRepository.addSong(song2);
        dependencyProvider.songLocalRepository.addSong(song3);
        
        // Create a test playlist
        Playlist playlist = new Playlist("Test Playlist", PlaylistEnum.PRIVATE);
        playlist.setPlaylistId(1);
        dependencyProvider.playlistLocalRepository.savePlaylist(playlist);

        testHelper.addSongToPlaylist(playlist.getPlaylistId(), song1.getSongId(), dependencyProvider.playlistLocalRepository, dependencyProvider.playlistService);
        testHelper.addSongToPlaylist(playlist.getPlaylistId(), song2.getSongId(), dependencyProvider.playlistLocalRepository, dependencyProvider.playlistService);
        testHelper.addSongToPlaylist(playlist.getPlaylistId(), song3.getSongId(), dependencyProvider.playlistLocalRepository, dependencyProvider.playlistService);
    }
    
    @AfterEach
    void tearDown() {
        // Delete temporary files
        if (dependencyProvider.tempSongsFile != null && dependencyProvider.tempSongsFile.exists()) {
            dependencyProvider.tempSongsFile.delete();
        }
        if (dependencyProvider.tempPlaylistsFile != null && dependencyProvider.tempPlaylistsFile.exists()) {
            dependencyProvider.tempPlaylistsFile.delete();
        }
    }

    @Test
    void testPlay() {
        // Act
        dependencyProvider.playlistPlayer.play(1, 1);

        // Assert
        assertEquals(1, dependencyProvider.playlistPlayer.getCurrentPlaylistId());
        assertEquals(1, dependencyProvider.playlistPlayer.getCurrentSongId());
        assertTrue(dependencyProvider.fakeMusicPlayer.isPlaying());
        assertEquals("song1.mp3", dependencyProvider.fakeMusicPlayer.getCurrentSongFileName());
    }

    @Test
    void testPlayOrPause (){
        // First play a song
        dependencyProvider.playlistPlayer.play(1, 1);
        assertTrue(dependencyProvider.fakeMusicPlayer.isPlaying());
        dependencyProvider.songService.setCurrentSongId(dependencyProvider.playlistPlayer.getCurrentSongId());

        // Test pause
        dependencyProvider.playlistPlayer.playOrPause(dependencyProvider.songService.getCurrentSongId());
        assertFalse(dependencyProvider.fakeMusicPlayer.isPlaying());

        // Test resume
        dependencyProvider.playlistPlayer.playOrPause(dependencyProvider.songService.getCurrentSongId());
        assertTrue(dependencyProvider.fakeMusicPlayer.isPlaying());
    }
    @Test
    void testPauseAndResume() {
        // First play a song
        dependencyProvider.playlistPlayer.play(1, 1);
        assertTrue(dependencyProvider.fakeMusicPlayer.isPlaying());
        dependencyProvider.songService.setCurrentSongId(dependencyProvider.playlistPlayer.getCurrentSongId());
        
        // Test pause
        dependencyProvider.playlistPlayer.pause();
        assertFalse(dependencyProvider.fakeMusicPlayer.isPlaying());
        
        // Test resume
        dependencyProvider.playlistPlayer.resume(dependencyProvider.songService.getCurrentSongId());
        assertTrue(dependencyProvider.fakeMusicPlayer.isPlaying());
    }

    @Test
    void testPlayback() {
        // First play a song
        dependencyProvider.playlistPlayer.play(1, 2);
        assertEquals("song2.mp3", dependencyProvider.fakeMusicPlayer.getCurrentSongFileName());
        
        // Test playback
        dependencyProvider.playlistPlayer.playback();
        assertTrue(dependencyProvider.fakeMusicPlayer.isPlaying());
        assertEquals("song2.mp3", dependencyProvider.fakeMusicPlayer.getCurrentSongFileName());
    }

    @Test
    void testNextInSequentialMode() {
        // Start with sequential mode (default)
        dependencyProvider.playlistPlayer.setSequentialMode();
        dependencyProvider.playlistPlayer.play(1, 1);
        assertEquals(1, dependencyProvider.playlistPlayer.getCurrentSongId());
        
        // Test next song
        dependencyProvider.fakeMusicPlayer.triggerSongEnd();
        assertEquals(2, dependencyProvider.playlistPlayer.getCurrentSongId());
        assertEquals("song2.mp3", dependencyProvider.fakeMusicPlayer.getCurrentSongFileName());
        
        // Test next song again
        dependencyProvider.fakeMusicPlayer.triggerSongEnd();
        assertEquals(3, dependencyProvider.playlistPlayer.getCurrentSongId());
        assertEquals("song3.mp3", dependencyProvider.fakeMusicPlayer.getCurrentSongFileName());

        // Test next song loop
        dependencyProvider.fakeMusicPlayer.triggerSongEnd();
        assertEquals(1, dependencyProvider.playlistPlayer.getCurrentSongId());
        assertEquals("song1.mp3", dependencyProvider.fakeMusicPlayer.getCurrentSongFileName());
    }

    @Test
    void testPrevious() {
        // Play a song and advance to the next one
        dependencyProvider.playlistPlayer.play(1, 1);
        dependencyProvider.fakeMusicPlayer.triggerSongEnd();
        dependencyProvider.fakeMusicPlayer.triggerSongEnd();
        dependencyProvider.fakeMusicPlayer.triggerSongEnd();
        assertEquals(1, dependencyProvider.playlistPlayer.getCurrentSongId());
        
        // Test previous
        dependencyProvider.playlistPlayer.previous();
        assertEquals(3, dependencyProvider.playlistPlayer.getCurrentSongId());
        assertEquals("song3.mp3", dependencyProvider.fakeMusicPlayer.getCurrentSongFileName());

        // Test previous
        dependencyProvider.playlistPlayer.previous();
        assertEquals(2, dependencyProvider.playlistPlayer.getCurrentSongId());
        assertEquals("song2.mp3", dependencyProvider.fakeMusicPlayer.getCurrentSongFileName());

        // Test previous
        dependencyProvider.playlistPlayer.previous();
        assertEquals(1, dependencyProvider.playlistPlayer.getCurrentSongId());
        assertEquals("song1.mp3", dependencyProvider.fakeMusicPlayer.getCurrentSongFileName());
    }

    @Test
    void testPreviousWithEmptyHistory() {
        // Just starting playing
        dependencyProvider.playlistPlayer.play(1, 1);
        
        // Test previous with no history
        dependencyProvider.playlistPlayer.previous();
        // Should still be on song 1
        assertEquals(1, dependencyProvider.playlistPlayer.getCurrentSongId());
    }

    @Test
    void testShuffleMode() {
        // Enable shuffle mode
        dependencyProvider.playlistPlayer.setShuffleMode();
        dependencyProvider.playlistPlayer.play(1, 1);
        
        // Since shuffle is random, we'll just verify that next() doesn't crash
        // and returns a valid song in the playlist
        dependencyProvider.fakeMusicPlayer.triggerSongEnd();
        int songId = dependencyProvider.playlistPlayer.getCurrentSongId();
        assertTrue(songId >= 1 && songId <= 3, "Shuffle should return a valid song ID");
        
        // Do another next and ensure we get a valid song path
        dependencyProvider.fakeMusicPlayer.triggerSongEnd();
        assertNotNull(dependencyProvider.fakeMusicPlayer.getCurrentSongFileName());
    }

    @Test
    void testRepeatMode() {
        // Enable repeat mode
        dependencyProvider.playlistPlayer.setRepeatMode();
        dependencyProvider.playlistPlayer.play(1, 2);
        assertEquals(2, dependencyProvider.playlistPlayer.getCurrentSongId());
        
        // In repeat mode, next should play the same song again
        dependencyProvider.fakeMusicPlayer.triggerSongEnd();
        assertEquals(2, dependencyProvider.playlistPlayer.getCurrentSongId());
        assertEquals("song2.mp3", dependencyProvider.fakeMusicPlayer.getCurrentSongFileName());

        // In repeat mode, next should play the same song again
        dependencyProvider.fakeMusicPlayer.triggerSongEnd();
        assertEquals(2, dependencyProvider.playlistPlayer.getCurrentSongId());
        assertEquals("song2.mp3", dependencyProvider.fakeMusicPlayer.getCurrentSongFileName());
    }

    @Test
    void testAutoAdvanceWhenSongEnds() {
        dependencyProvider.playlistPlayer.setSequentialMode();
        dependencyProvider.playlistPlayer.play(1, 1);
        
        // Simulate song ending (which should trigger next())
        dependencyProvider.fakeMusicPlayer.triggerSongEnd();
        
        // Should auto-advance to song 2
        assertEquals(2, dependencyProvider.playlistPlayer.getCurrentSongId());
        assertEquals("song2.mp3", dependencyProvider.fakeMusicPlayer.getCurrentSongFileName());
    }

    @Test
    void testSetModes() {
        // Test switching between modes
        dependencyProvider.playlistPlayer.play(1, 1);
        
        // Test sequential mode
        dependencyProvider.playlistPlayer.setSequentialMode();
        dependencyProvider.fakeMusicPlayer.triggerSongEnd();
        assertEquals(2, dependencyProvider.playlistPlayer.getCurrentSongId());
        
        // Test repeat mode
        dependencyProvider.playlistPlayer.setRepeatMode();
        dependencyProvider.fakeMusicPlayer.triggerSongEnd();
        assertEquals(2, dependencyProvider.playlistPlayer.getCurrentSongId());
        
        // Test shuffle mode
        dependencyProvider.playlistPlayer.setShuffleMode();
        dependencyProvider.fakeMusicPlayer.triggerSongEnd();
        // We can't assert exact song ID due to randomness, but we can verify it's a valid song
        assertNotNull(dependencyProvider.fakeMusicPlayer.getCurrentSongFileName());
    }
}
