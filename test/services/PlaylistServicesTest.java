package services;

import data.entities.Playlist;
import data.entities.Song;
import data.jsons.PlaylistRepository;
import data.jsons.SongRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import player_StatePattern.file_player.FakeMusicPlayer;
import player_StatePattern.playlist_player.PlaylistPlayer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

class PlaylistServicesTest {

    private PlaylistPlayer playlistPlayer;
    private FakeMusicPlayer fakeMusicPlayer;
    private File songTempFile;
    private File playlistTempFile;
    private Playlist playlist;
    private PlaylistServices playlistServices;

    @BeforeEach
    void setUp() throws IOException {
        // Create Cookies_SingeltonPattern instance
        Cookies_SingletonPattern.setInstance(400953820); //testUsers

        // Create temporary files for repositories
        songTempFile = Files.createTempFile("songs", ".json").toFile();
        playlistTempFile = Files.createTempFile("playlists", ".json").toFile();

        // Initialize repositories with temp files
        SongRepository songRepository = new SongRepository(songTempFile.getAbsolutePath());
        PlaylistRepository playlistRepository = new PlaylistRepository(playlistTempFile.getAbsolutePath());

        // Create test songs
        Song song1 = createSong(1, "Song 1", "path/to/song1.mp3");
        Song song2 = createSong(2, "Song 2", "path/to/song2.mp3");
        Song song3 = createSong(3, "Song 3", "path/to/song3.mp3");

        // Add songs to repository
        songRepository.addSong(song1);
        songRepository.addSong(song2);
        songRepository.addSong(song3);

        // Create a test playlist
        this.playlist = new Playlist("Test Playlist");
        this.playlist.setPlaylistId(1);
        this.playlist.addSong(song1);
        this.playlist.addSong(song2);
        this.playlist.addSong(song3);

        // Add playlist to repository
        playlistRepository.addPlaylist(playlist);

        // Create a FakeMusicPlayer for testing
        fakeMusicPlayer = new FakeMusicPlayer();

        // Instantiate the PlaylistPlayer with the fake player and repositories
        playlistPlayer = new PlaylistPlayer(fakeMusicPlayer, songRepository, playlistRepository);

        // Create playlistService
        playlistServices = new PlaylistServices(playlistRepository);
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

    private Song createSong(int id, String title, String path) {
        Song song = new Song();
        song.setSongId(id);
        song.setTitle(title);
        song.setAudioFilePath(path);
        return song;
    }

    @Test
    public void testEditPlayListName (){
        // Arrange
        String newName = "TESTRename";
        int playlistId = this.playlist.getPlaylistId();

        // Act
        playlistServices.editPlayListName(playlistId, newName);
        String playlistName = playlistServices.playlistRepository.getPlaylistById(1).getPlaylistName();
        // Assert
        assertEquals(newName, playlistName);
    }

    @Test
    public void testDeletePlaylist(){
        // Arrange
        int playlistId = this.playlist.getPlaylistId();

        // Act
        playlistServices.deletePlaylist(playlistId);
        Playlist deletedPlaylist = playlistServices.playlistRepository.getPlaylistById(playlistId);

        // Assert
        assertNull(deletedPlaylist, "The playlist should be deleted");
    }

}