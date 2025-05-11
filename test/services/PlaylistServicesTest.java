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
    }

    @BeforeEach
    void setUp() throws IOException {
        // Create test songs
        Song song1 = createSong(1, "Song 1", "song1.mp3");
        Song song2 = createSong(2, "Song 2", "song2.mp3");
        Song song3 = createSong(3, "Song 3", "song3.mp3");

        // Add songs to repository
        songLocalRepository.addSong(song1);
        songLocalRepository.addSong(song2);
        songLocalRepository.addSong(song3);

        // Create a test playlist
        playlist = new Playlist("Test Playlist", PlaylistEnum.PRIVATE);
        playlist.setPlaylistId(1);
        playlistLocalRepository.savePlaylist(playlist);
        addSongToPlaylist(playlist.getPlaylistId(), song1.getSongId(), playlistLocalRepository, playlistService);
        addSongToPlaylist(playlist.getPlaylistId(), song2.getSongId(), playlistLocalRepository, playlistService);
        addSongToPlaylist(playlist.getPlaylistId(), song3.getSongId(), playlistLocalRepository, playlistService);

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
                fakeMusicPlayer, audioLocalRepository, songService, playlistService);

        // Create playlistServices
        playlistService = new PlaylistServices(serviceToolBox, playlistFunctionalitiesService, temporaryPlaylistService);
    }

    @AfterEach
    void tearDown() {
        // Delete temporary files
        if (tempSongsFile != null && tempSongsFile.exists()) {
            tempSongsFile.delete();
        }
        if (tempPlaylistsFile != null && tempPlaylistsFile.exists()) {
            tempPlaylistsFile.delete();
        }
    }

    @Test
    public void testRenamePlayList(){
        // Arrange
        String newName = "TESTRename";
        int playlistId = this.playlist.getPlaylistId();

        // Act
        playlistService.renamePlayList(playlistId, newName);
        String playlistName = playlistLocalRepository.getPlaylistById(1).getName();
        // Assert
        assertEquals(newName, playlistName);
    }

    @Test
    public void testDeletePlaylist(){
        // Arrange
        int playlistId = this.playlist.getPlaylistId();

        // Act
        playlistService.deletePlaylist(playlistId);
        Playlist deletedPlaylist = playlistLocalRepository.getPlaylistById(playlistId);

        // Assert
        assertNull(deletedPlaylist, "The playlist should be deleted");
    }

    @Test
    public void testCreateTemporaryPlaylist(){
        //Arrange
        LinkedList <Integer> chosenSongs = new LinkedList<>();
        chosenSongs.add(1);

        //Act
        playlistService.createTemporaryPlaylist(chosenSongs, PlaylistEnum.PUBLIC);
        int temporaryPlaylistId = playlistLocalRepository.getPlaylistByName("temporaryPlaylist").getPlaylistId();
        int firstSongId = playlistLocalRepository
                .getPlaylistById(temporaryPlaylistId).getPlaylistSongsListWithId().getFirst();

        //Assert
        assertEquals("temporaryPlaylist",
                playlistLocalRepository.getPlaylistByName("temporaryPlaylist").getName());
    }

    @Test
    public void testDeleteTemporaryPlaylist(){
        //Arrange
        LinkedList <Integer> chosenSongs = new LinkedList<>();
        chosenSongs.add(1);

        playlistService.createTemporaryPlaylist(chosenSongs, PlaylistEnum.PUBLIC);
        //Act
        playlistService.deleteTemporaryPlaylist();
        //Assert
        assertNull(playlistLocalRepository.getPlaylistByName("temporaryPlaylist"), "The playlist should be deleted");
    }

    @Test
    public void testCreatePlaylistWithTemporaryPlaylist (){
        //Arrange
        LinkedList <Integer> chosenSongs = new LinkedList<>();
        chosenSongs.add(1);
        chosenSongs.add(3);
        playlistService.createTemporaryPlaylist(chosenSongs, PlaylistEnum.PUBLIC);
        String playlistName = "new Playlist";
        int temporaryPlaylistLength = playlistLocalRepository
                .getPlaylistByName("temporaryPlaylist")
                .getPlaylistSongsListWithId().size();

        //Act
        playlistService.createPlaylistWithTemporaryPlaylist(playlistName, PlaylistEnum.PUBLIC);
        int newPlaylistLength = playlistLocalRepository
                .getPlaylistByName(playlistName)
                .getPlaylistSongsListWithId().size();

        //Assert
        assertEquals(temporaryPlaylistLength, newPlaylistLength);
    }

    @Test
    public void testReorderSongsInPlaylist() {
        //Arrange
        addSongToPlaylist(playlist.getPlaylistId(), 1, playlistLocalRepository, playlistService);
        addSongToPlaylist(playlist.getPlaylistId(), 2, playlistLocalRepository, playlistService);
        addSongToPlaylist(playlist.getPlaylistId(), 3, playlistLocalRepository, playlistService);

        // Simuler l'entrée utilisateur
        String input = "2\n1\n3\nx\n";
        Scanner testScanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        PlaylistReorderSongService reorderService = new PlaylistReorderSongService(serviceToolBox, testScanner);
        reorderService.reorderSongsInPlaylist(playlist.getPlaylistId(), playlistService);

        Playlist updated = playlistLocalRepository.getPlaylistById(playlist.getPlaylistId());
        assertEquals(List.of(2, 1, 3), updated.getPlaylistSongsListWithId());
    }


}