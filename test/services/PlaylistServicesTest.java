package services;

import utilsAndFakes.CommuneMethods;
import serverSide.entities.*;
import clientSide.services.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utilsAndFakes.FakeMusicPlayer;
import clientSide.player_StatePattern.playlist_player.PlaylistPlayer;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class PlaylistServicesTest extends CommuneMethods{

    private Playlist playlist;

    public PlaylistServicesTest() throws IOException {
        super();
    }

    @BeforeEach
    void setUp() throws IOException {
        // Create test songs
        Song song1 = createSong(1, "Song 1", "song1.mp3");
        Song song2 = createSong(2, "Song 2", "song2.mp3");
        Song song3 = createSong(3, "Song 3", "song3.mp3");

        // Add songs to repository
        initializer.songLocalRepository.addSong(song1);
        initializer.songLocalRepository.addSong(song2);
        initializer.songLocalRepository.addSong(song3);

        // Create a test playlist
        playlist = new Playlist("Test Playlist", PlaylistEnum.PRIVATE);
        playlist.setPlaylistId(1);
        initializer.playlistLocalRepository.savePlaylist(playlist);
        addSongToPlaylist(playlist.getPlaylistId(), song1.getSongId(), initializer.playlistLocalRepository, initializer.playlistService);
        addSongToPlaylist(playlist.getPlaylistId(), song2.getSongId(), initializer.playlistLocalRepository, initializer.playlistService);
        addSongToPlaylist(playlist.getPlaylistId(), song3.getSongId(), initializer.playlistLocalRepository, initializer.playlistService);

        // Add playlist to repository
        initializer.playlistLocalRepository.savePlaylist(playlist);

        // Create a test user
        User user = new User("testUsers","email", "testUsers", PlanEnum.FREE);
        user.setUserId(400953820);
        initializer.userLocalRepository.saveUser(user);

        // Create Cookies_SingeltonPattern instance
        Cookies_SingletonPattern.setInstance(400953820); //testUsers

        // Create a FakeMusicPlayer for testing
        FakeMusicPlayer fakeMusicPlayer = new FakeMusicPlayer();

        // Instantiate the PlaylistPlayer with the fake player and repositories
        PlaylistPlayer playlistPlayer = new PlaylistPlayer(
                fakeMusicPlayer, initializer.audioLocalRepository, initializer.songService, initializer.playlistService);

        // Create playlistServices
        initializer.playlistService = new PlaylistServices(
                initializer.toolBoxService,
                initializer.playlistFunctionalitiesService,
                initializer.temporaryPlaylistService);
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
    public void testRenamePlayList(){
        // Arrange
        String newName = "TESTRename";
        int playlistId = this.playlist.getPlaylistId();

        // Act
        initializer.playlistService.renamePlayList(playlistId, newName);
        String playlistName = initializer.playlistLocalRepository.getPlaylistById(1).getName();
        // Assert
        assertEquals(newName, playlistName);
    }

    @Test
    public void testCreateTemporaryPlaylist(){
        //Arrange
        LinkedList <Integer> chosenSongs = new LinkedList<>();
        chosenSongs.add(1);

        //Act
        initializer.playlistService.createTemporaryPlaylist(chosenSongs, PlaylistEnum.PUBLIC);
        int temporaryPlaylistId = initializer.playlistLocalRepository.getPlaylistByName("temporaryPlaylist").getPlaylistId();
        int firstSongId = initializer.playlistLocalRepository
                .getPlaylistById(temporaryPlaylistId).getPlaylistSongsListWithId().getFirst();

        //Assert
        assertEquals("temporaryPlaylist",
                initializer.playlistLocalRepository.getPlaylistByName("temporaryPlaylist").getName());
    }

    @Test
    public void testAdjustTemporaryPlaylistToNewPlaylist(){
        //Arrange
        LinkedList <Integer> chosenSongs = new LinkedList<>();
        chosenSongs.add(1);
        chosenSongs.add(3);
        initializer.playlistService.createTemporaryPlaylist(chosenSongs, PlaylistEnum.PUBLIC);
        String playlistName = "new Playlist";
        int temporaryPlaylistLength = initializer.playlistLocalRepository
                .getPlaylistByName("temporaryPlaylist")
                .getPlaylistSongsListWithId().size();

        //Act
        initializer.playlistService.adjustTemporaryPlaylistToNewPlaylist(playlistName, PlaylistEnum.PUBLIC);
        int newPlaylistLength = initializer.playlistLocalRepository
                .getPlaylistByName(playlistName)
                .getPlaylistSongsListWithId().size();

        //Assert
        assertEquals(temporaryPlaylistLength, newPlaylistLength);
    }

    @Test
    public void testReorderSongsInPlaylist() {
        //Arrange
        addSongToPlaylist(playlist.getPlaylistId(), 1, initializer.playlistLocalRepository, initializer.playlistService);
        addSongToPlaylist(playlist.getPlaylistId(), 2, initializer.playlistLocalRepository, initializer.playlistService);
        addSongToPlaylist(playlist.getPlaylistId(), 3, initializer.playlistLocalRepository, initializer.playlistService);

        // Simuler l'entrée utilisateur
        String input = "2\n1\n3\nx\n";
        Scanner testScanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        PlaylistReorderSongService reorderService = new PlaylistReorderSongService(initializer.toolBoxService, testScanner);
        reorderService.reorderSongsInPlaylist(playlist.getPlaylistId(), initializer.playlistService);

        Playlist updated = initializer.playlistLocalRepository.getPlaylistById(playlist.getPlaylistId());
        assertEquals(List.of(2, 1, 3), updated.getPlaylistSongsListWithId());
    }


}