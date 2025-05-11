package services;

import serverSide.entities.*;
import serverSide.repositories.ArtistRepository;
import serverSide.repositories.PlaylistRepository;
import serverSide.repositories.SongRepository;
import serverSide.repositories.UserRepository;
import clientSide.services.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import clientSide.player_StatePattern.file_player.FakeMusicPlayer;
import clientSide.player_StatePattern.playlist_player.PlaylistPlayer;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class PlaylistServicesTest {

    private File songTempFile;
    private File playlistTempFile;
    private File userTempFile;
    private File artistTempFile;
    private Playlist playlist;
    private PlaylistServices playlistServices;
    private TemporaryPlaylistService temporaryPlaylistService;
    private CommuneMethods communeMethods = new CommuneMethods();
    private SongRepository songRepository;
    private SongService songService;
    private UserRepository userRepository;
    private PlaylistRepository playlistRepository;
    private ArtistRepository artistRepository;

    @BeforeEach
    void setUp() throws IOException {

        // Create temporary files for repositories
        songTempFile = Files.createTempFile("songs", ".json").toFile();
        playlistTempFile = Files.createTempFile("playlists", ".json").toFile();
        userTempFile = Files.createTempFile("users", ".json").toFile();
        artistTempFile = Files.createTempFile("artist", ".json").toFile();


        // Initialize repositories with temp files
        songRepository = new SongRepository(songTempFile.getAbsolutePath());
        userRepository = new UserRepository(userTempFile.getAbsolutePath());
        playlistRepository = new PlaylistRepository(playlistTempFile.getAbsolutePath());
        artistRepository = new ArtistRepository(artistTempFile.getAbsolutePath());

        playlistServices = new PlaylistServices(playlistRepository, userRepository, songRepository);
        songService = new SongService(songRepository);
        temporaryPlaylistService = new TemporaryPlaylistService(playlistRepository, userRepository);

        // Create test songs
        Song song1 = communeMethods.createSong(1, "Song 1", "path/to/song1.mp3");
        Song song2 = communeMethods.createSong(2, "Song 2", "path/to/song2.mp3");
        Song song3 = communeMethods.createSong(3, "Song 3", "path/to/song3.mp3");

        // Add songs to repository
        songRepository.addSong(song1);
        songRepository.addSong(song2);
        songRepository.addSong(song3);

        // Create a test playlist
        playlist = new Playlist("Test Playlist", PlaylistEnum.PRIVATE);
        playlist.setPlaylistId(1);
        playlistRepository.savePlaylist(playlist);
        communeMethods.addSongToPlaylist(playlist.getPlaylistId(), song1.getSongId(), playlistRepository, playlistServices);
        communeMethods.addSongToPlaylist(playlist.getPlaylistId(), song2.getSongId(), playlistRepository, playlistServices);
        communeMethods.addSongToPlaylist(playlist.getPlaylistId(), song3.getSongId(), playlistRepository, playlistServices);

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
        PlaylistPlayer playlistPlayer = new PlaylistPlayer(fakeMusicPlayer, songRepository, playlistRepository, artistRepository);

        // Create playlistServices
        playlistServices = new PlaylistServices(playlistRepository, userRepository, songRepository);
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

    @Test
    public void testRenamePlayList(){
        // Arrange
        String newName = "TESTRename";
        int playlistId = this.playlist.getPlaylistId();

        // Act
        playlistServices.renamePlayList(playlistId, newName);
        String playlistName = playlistServices.playlistRepository.getPlaylistById(1).getName();
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

    @Test
    public void testCreateTemporaryPlaylist(){
        //Arrange
        LinkedList <Integer> chosenSongs = new LinkedList<>();
        chosenSongs.add(1);

        //Act
        playlistServices.createTemporaryPlaylist(chosenSongs, PlaylistEnum.PUBLIC);
        int temporaryPlaylistId = playlistServices.playlistRepository.getPlaylistByName("temporaryPlaylist").getPlaylistId();
        int firstSongId = playlistServices.playlistRepository
                .getPlaylistById(temporaryPlaylistId).getPlaylistSongsListWithId().getFirst();

        //Assert
        assertEquals("temporaryPlaylist",
                playlistServices.playlistRepository.getPlaylistByName("temporaryPlaylist").getName());
    }

    @Test
    public void testDeleteTemporaryPlaylist(){
        //Arrange
        LinkedList <Integer> chosenSongs = new LinkedList<>();
        chosenSongs.add(1);

        playlistServices.createTemporaryPlaylist(chosenSongs, PlaylistEnum.PUBLIC);
        //Act
        playlistServices.deleteTemporaryPlaylist();
        //Assert
        assertNull(playlistServices.playlistRepository.getPlaylistByName("temporaryPlaylist"), "The playlist should be deleted");
    }

    @Test
    public void testCreatePlaylistWithTemporaryPlaylist (){
        //Arrange
        LinkedList <Integer> chosenSongs = new LinkedList<>();
        chosenSongs.add(1);
        chosenSongs.add(3);
        playlistServices.createTemporaryPlaylist(chosenSongs, PlaylistEnum.PUBLIC);
        String playlistName = "new Playlist";
        int temporaryPlaylistLength = playlistServices.playlistRepository
                .getPlaylistByName("temporaryPlaylist")
                .getPlaylistSongsListWithId().size();

        //Act
        playlistServices.createPlaylistWithTemporaryPlaylist(playlistName, PlaylistEnum.PUBLIC);
        int newPlaylistLength = playlistServices.playlistRepository
                .getPlaylistByName(playlistName)
                .getPlaylistSongsListWithId().size();

        //Assert
        assertEquals(temporaryPlaylistLength, newPlaylistLength);
    }

    @Test
    public void testReorderSongsInPlaylist() {
        //Arrange
        communeMethods.addSongToPlaylist(playlist.getPlaylistId(), 1, playlistRepository, playlistServices);
        communeMethods.addSongToPlaylist(playlist.getPlaylistId(), 2, playlistRepository, playlistServices);
        communeMethods.addSongToPlaylist(playlist.getPlaylistId(), 3, playlistRepository, playlistServices);

        // Simuler l'entrée utilisateur
        String input = "2\n1\n3\nx\n";
        Scanner testScanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        PlaylistReorderSongService reorderService = new PlaylistReorderSongService(testScanner);
        reorderService.reorderSongsInPlaylist(playlist.getPlaylistId(), playlistServices);

        Playlist updated = playlistServices.playlistRepository.getPlaylistById(playlist.getPlaylistId());
        assertEquals(List.of(2, 1, 3), updated.getPlaylistSongsListWithId());
    }


}