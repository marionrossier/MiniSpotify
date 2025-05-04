package services;

import data.entities.PlanEnum;
import data.entities.Playlist;
import data.entities.Song;
import data.entities.User;
import data.jsons.PlaylistRepository;
import data.jsons.SongRepository;
import data.jsons.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import player_StatePattern.file_player.FakeMusicPlayer;
import player_StatePattern.playlist_player.PlaylistPlayer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class PlaylistServicesTest {

    private File songTempFile;
    private File playlistTempFile;
    private File userTempFile;
    private Playlist playlist;
    private PlaylistServices playlistService;

    @BeforeEach
    void setUp() throws IOException {

        // Create temporary files for repositories
        songTempFile = Files.createTempFile("songs", ".json").toFile();
        playlistTempFile = Files.createTempFile("playlists", ".json").toFile();
        userTempFile = Files.createTempFile("users", ".json").toFile();


        // Initialize repositories with temp files
        SongRepository songRepository = new SongRepository(songTempFile.getAbsolutePath());
        UserRepository userRepository = new UserRepository(userTempFile.getAbsolutePath());
        PlaylistRepository playlistRepository = new PlaylistRepository(playlistTempFile.getAbsolutePath());

        playlistService = new PlaylistServices(playlistRepository, userRepository);

        // Create test songs
        Song song1 = createSong(1, "Song 1", "path/to/song1.mp3");
        Song song2 = createSong(2, "Song 2", "path/to/song2.mp3");
        Song song3 = createSong(3, "Song 3", "path/to/song3.mp3");

        // Add songs to repository
        songRepository.addSong(song1);
        songRepository.addSong(song2);
        songRepository.addSong(song3);

        // Create a test playlist
        playlist = new Playlist("Test Playlist");
        playlist.setPlaylistId(1);
        playlistRepository.savePlaylist(playlist);
        this.playlistService.addSong(playlist.getPlaylistId(), song1.getSongId());
        this.playlistService.addSong(playlist.getPlaylistId(), song2.getSongId());
        this.playlistService.addSong(playlist.getPlaylistId(), song3.getSongId());

        // Add playlist to repository
        playlistRepository.savePlaylist(playlist);

        // Create a test user
        User user = new User("testUsers","email", "testUsers", PlanEnum.FREE);
        user.setUserId(400953820);
        userRepository.saveUser(user);

        // Create Cookies_SingeltonPattern instance
        Cookies_SingletonPattern.setInstance(400953820); //testUsers

        // Create a FakeMusicPlayer for testing
        FakeMusicPlayer fakeMusicPlayer = new FakeMusicPlayer();

        // Instantiate the PlaylistPlayer with the fake player and repositories
        PlaylistPlayer playlistPlayer = new PlaylistPlayer(fakeMusicPlayer, songRepository, playlistRepository);

        // Create playlistService
        playlistService = new PlaylistServices(playlistRepository, userRepository);
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
    public void testRenamePlayList(){
        // Arrange
        String newName = "TESTRename";
        int playlistId = this.playlist.getPlaylistId();

        // Act
        playlistService.renamePlayList(playlistId, newName);
        String playlistName = playlistService.playlistRepository.getPlaylistById(1).getPlaylistName();
        // Assert
        assertEquals(newName, playlistName);
    }

    @Test
    public void testDeletePlaylist(){
        // Arrange
        int playlistId = this.playlist.getPlaylistId();

        // Act
        playlistService.deletePlaylist(playlistId);
        Playlist deletedPlaylist = playlistService.playlistRepository.getPlaylistById(playlistId);

        // Assert
        assertNull(deletedPlaylist, "The playlist should be deleted");
    }

    @Test
    public void testCreateTemporaryPlaylist(){
        //Arrange
        LinkedList <Integer> chosenSongs = new LinkedList<>();
        chosenSongs.add(1);

        //Act
        playlistService.createTemporaryPlaylist(chosenSongs);
        int temporaryPlaylistId = playlistService.playlistRepository.getPlaylistByName("temporaryPlaylist").getPlaylistId();
        int firstSongId = playlistService.playlistRepository
                .getPlaylistById(temporaryPlaylistId).getPlaylistSongsListWithId().getFirst();

        //Assert
        assertEquals("temporaryPlaylist",
                playlistService.playlistRepository.getPlaylistByName("temporaryPlaylist").getPlaylistName());
    }

    @Test
    public void testDeleteTemporaryPlaylist(){
        //Arrange
        LinkedList <Integer> chosenSongs = new LinkedList<>();
        chosenSongs.add(1);

        playlistService.createTemporaryPlaylist(chosenSongs);
        //Act
        playlistService.deleteTemporaryPlaylist();
        //Assert
        assertNull(playlistService.playlistRepository.getPlaylistByName("temporaryPlaylist"), "The playlist should be deleted");
    }

    @Test
    public void testCreatePlaylistWithTemporaryPlaylist (){
        //Arrange
        LinkedList <Integer> chosenSongs = new LinkedList<>();
        chosenSongs.add(1);
        chosenSongs.add(3);
        playlistService.createTemporaryPlaylist(chosenSongs);
        String playlistName = "new Playlist";
        int temporaryPlaylistLength = playlistService.playlistRepository
                .getPlaylistByName("temporaryPlaylist")
                .getPlaylistSongsListWithId().size();

        //Act
        playlistService.createPlaylistWithTemporaryPlaylist(playlistName);
        int newPlaylistLength = playlistService.playlistRepository
                .getPlaylistByName(playlistName)
                .getPlaylistSongsListWithId().size();

        //Assert
        assertEquals(temporaryPlaylistLength, newPlaylistLength);
    }

    //TODO : faire ces tests
//    public void removeSongFromPlaylist(int playlistId, int songIndex) {
//        Playlist playlist = playlistRepo.getPlaylistById(playlistId);
//
//        playlistRepo.getPlaylistById(playlist.getPlaylistId())
//                .getPlaylistSongsListWithId().remove(songIndex);
//
//        playlistRepo.savePlaylist(playlist);
//
//    }
//
//    public void addSongToPlaylistFromTemporaryPlaylist(int playlistId) {
//        Playlist temporaryPlaylist = playlistRepo.getPlaylistByName("temporaryPlaylist");
//        Playlist targetPlaylist = playlistRepo.getPlaylistById(playlistId);
//
//        if (targetPlaylist != null && temporaryPlaylist != null) {
//            targetPlaylist.getPlaylistSongsListWithId().addAll(temporaryPlaylist.getPlaylistSongsListWithId());
//            playlistRepo.updatePlaylist(targetPlaylist);
//        } else {
//            System.err.println("Target playlist or temporary playlist not found.");
//        }
//    }
//
//    public void reorderSongsInPlaylist(String playlistName) {/*TODO*/}


}