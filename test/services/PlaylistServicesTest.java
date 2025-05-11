package services;

import fakes.FakeAudioRepository;
import serverSide.entities.*;
import serverSide.repositories.*;
import clientSide.services.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import fakes.FakeMusicPlayer;
import clientSide.player_StatePattern.playlist_player.PlaylistPlayer;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.PipedReader;
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
    private TemporaryPlaylistService temporaryPlaylistService;
    private CommuneMethods communeMethods = new CommuneMethods();
    private SongLocalRepository songLocalRepository;
    private UserLocalRepository userLocalRepository;
    private ArtistLocalRepository artistLocalRepository;
    private IAudioRepository audioRepository;
    private PlaylistLocalRepository playlistLocalRepository;
    private PlaylistServices playlistServices;
    private SongService songService;
    private UserService userService;
    private ArtistService artistService;
    private PrintService printService;

    @BeforeEach
    void setUp() throws IOException {

        // Create temporary files for repositories
        songTempFile = Files.createTempFile("songs", ".json").toFile();
        playlistTempFile = Files.createTempFile("playlists", ".json").toFile();
        userTempFile = Files.createTempFile("users", ".json").toFile();
        artistTempFile = Files.createTempFile("artist", ".json").toFile();


        // Initialize repositories with temp files
        songLocalRepository = new SongLocalRepository(songTempFile.getAbsolutePath());
        userLocalRepository = new UserLocalRepository(userTempFile.getAbsolutePath());
        playlistLocalRepository = new PlaylistLocalRepository(playlistTempFile.getAbsolutePath());
        artistLocalRepository = new ArtistLocalRepository(artistTempFile.getAbsolutePath());
        audioRepository = new FakeAudioRepository();

        playlistServices = new PlaylistServices(playlistLocalRepository, userLocalRepository, songLocalRepository);
        songService = new SongService(songLocalRepository);
        userService = new UserService(userLocalRepository);
        artistService = new ArtistService(artistLocalRepository);
        printService = new PrintService(songService,artistService,playlistServices,userService);
        temporaryPlaylistService = new TemporaryPlaylistService(playlistLocalRepository, userLocalRepository);


        // Create test songs
        Song song1 = communeMethods.createSong(1, "Song 1", "song1.mp3");
        Song song2 = communeMethods.createSong(2, "Song 2", "song2.mp3");
        Song song3 = communeMethods.createSong(3, "Song 3", "song3.mp3");

        // Add songs to repository
        songLocalRepository.addSong(song1);
        songLocalRepository.addSong(song2);
        songLocalRepository.addSong(song3);

        // Create a test playlist
        playlist = new Playlist("Test Playlist", PlaylistEnum.PRIVATE);
        playlist.setPlaylistId(1);
        playlistLocalRepository.savePlaylist(playlist);
        communeMethods.addSongToPlaylist(playlist.getPlaylistId(), song1.getSongId(), playlistLocalRepository, playlistServices);
        communeMethods.addSongToPlaylist(playlist.getPlaylistId(), song2.getSongId(), playlistLocalRepository, playlistServices);
        communeMethods.addSongToPlaylist(playlist.getPlaylistId(), song3.getSongId(), playlistLocalRepository, playlistServices);

        // Add playlist to repository
        playlistLocalRepository.savePlaylist(playlist);

        // Create a test user
        User user = new User("testUsers","email", "testUsers", PlanEnum.FREE);
        user.setUserId(400953820);
        userLocalRepository.saveUser(user);

        // Create Cookies_SingeltonPattern instance
        Cookies_SingletonPattern.setInstance(400953820); //testUsers

        // Create a FakeMusicPlayer for testing
        FakeMusicPlayer fakeMusicPlayer = new FakeMusicPlayer();

        // Instantiate the PlaylistPlayer with the fake player and repositories
        PlaylistPlayer playlistPlayer = new PlaylistPlayer(
                fakeMusicPlayer, songLocalRepository, playlistLocalRepository, audioRepository, userLocalRepository);

        // Create playlistServices
        playlistServices = new PlaylistServices(playlistLocalRepository, userLocalRepository, songLocalRepository);
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
        String playlistName = playlistServices.playlistLocalRepository.getPlaylistById(1).getName();
        // Assert
        assertEquals(newName, playlistName);
    }

    @Test
    public void testDeletePlaylist(){
        // Arrange
        int playlistId = this.playlist.getPlaylistId();

        // Act
        playlistServices.deletePlaylist(playlistId);
        Playlist deletedPlaylist = playlistServices.playlistLocalRepository.getPlaylistById(playlistId);

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
        int temporaryPlaylistId = playlistServices.playlistLocalRepository.getPlaylistByName("temporaryPlaylist").getPlaylistId();
        int firstSongId = playlistServices.playlistLocalRepository
                .getPlaylistById(temporaryPlaylistId).getPlaylistSongsListWithId().getFirst();

        //Assert
        assertEquals("temporaryPlaylist",
                playlistServices.playlistLocalRepository.getPlaylistByName("temporaryPlaylist").getName());
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
        assertNull(playlistServices.playlistLocalRepository.getPlaylistByName("temporaryPlaylist"), "The playlist should be deleted");
    }

    @Test
    public void testCreatePlaylistWithTemporaryPlaylist (){
        //Arrange
        LinkedList <Integer> chosenSongs = new LinkedList<>();
        chosenSongs.add(1);
        chosenSongs.add(3);
        playlistServices.createTemporaryPlaylist(chosenSongs, PlaylistEnum.PUBLIC);
        String playlistName = "new Playlist";
        int temporaryPlaylistLength = playlistServices.playlistLocalRepository
                .getPlaylistByName("temporaryPlaylist")
                .getPlaylistSongsListWithId().size();

        //Act
        playlistServices.createPlaylistWithTemporaryPlaylist(playlistName, PlaylistEnum.PUBLIC);
        int newPlaylistLength = playlistServices.playlistLocalRepository
                .getPlaylistByName(playlistName)
                .getPlaylistSongsListWithId().size();

        //Assert
        assertEquals(temporaryPlaylistLength, newPlaylistLength);
    }

    @Test
    public void testReorderSongsInPlaylist() {
        //Arrange
        communeMethods.addSongToPlaylist(playlist.getPlaylistId(), 1, playlistLocalRepository, playlistServices);
        communeMethods.addSongToPlaylist(playlist.getPlaylistId(), 2, playlistLocalRepository, playlistServices);
        communeMethods.addSongToPlaylist(playlist.getPlaylistId(), 3, playlistLocalRepository, playlistServices);

        // Simuler l'entrée utilisateur
        String input = "2\n1\n3\nx\n";
        Scanner testScanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        PlaylistReorderSongService reorderService = new PlaylistReorderSongService(testScanner);
        reorderService.reorderSongsInPlaylist(playlist.getPlaylistId(), playlistServices);

        Playlist updated = playlistServices.playlistLocalRepository.getPlaylistById(playlist.getPlaylistId());
        assertEquals(List.of(2, 1, 3), updated.getPlaylistSongsListWithId());
    }


}