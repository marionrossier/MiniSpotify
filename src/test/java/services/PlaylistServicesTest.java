package services;

import clientSide.services.*;
import serverSide.entities.*;
import utilsAndFakes.TestHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utilsAndFakes.*;

import java.io.ByteArrayInputStream;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class PlaylistServicesTest{

    private Playlist playlist;
    private TestHelper testHelper;
    private DependencyProvider dependencyProvider;

    public PlaylistServicesTest(){
    }

    @BeforeEach
    void setUp(){
        testHelper = new TestHelper(45000);
        dependencyProvider = testHelper.dependencyProvider;

        // Create test songs
        Song song1 = testHelper.createSong(1, "Song 1", "song1.mp3");
        Song song2 = testHelper.createSong(2, "Song 2", "song2.mp3");
        Song song3 = testHelper.createSong(3, "Song 3", "song3.mp3");

        // Add songs to repository
        dependencyProvider.songLocalRepository.addSong(song1);
        dependencyProvider.songLocalRepository.addSong(song2);
        dependencyProvider.songLocalRepository.addSong(song3);

        // Create a test playlist
        playlist = new Playlist("Test Playlist", PlaylistEnum.PRIVATE);
        playlist.setPlaylistId(1);
        dependencyProvider.playlistLocalRepository.savePlaylist(playlist);
        testHelper.addSongToPlaylist(playlist.getPlaylistId(), song1.getSongId(), dependencyProvider.playlistLocalRepository, dependencyProvider.playlistService);
        testHelper.addSongToPlaylist(playlist.getPlaylistId(), song2.getSongId(), dependencyProvider.playlistLocalRepository, dependencyProvider.playlistService);
        testHelper.addSongToPlaylist(playlist.getPlaylistId(), song3.getSongId(), dependencyProvider.playlistLocalRepository, dependencyProvider.playlistService);

        // Add playlist to repository
        dependencyProvider.playlistLocalRepository.savePlaylist(playlist);

        // Create a test user
        User user = new User("testUsers","email", "testUsers", PlanEnum.FREE);
        user.setUserId(400953820);
        dependencyProvider.userLocalRepository.saveUser(user);

        // Create Cookies_SingeltonPattern instance
        Cookies_SingletonPattern.setInstance(400953820, "tester", "password"); //testUsers

        // Create playlistServices
        dependencyProvider.playlistService = new PlaylistServices(
                dependencyProvider.toolBoxService,
                dependencyProvider.playlistFunctionalitiesService,
                dependencyProvider.temporaryPlaylistService);
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
    public void testRenamePlayList(){
        // Arrange
        String newName = "TESTRename";
        int playlistId = this.playlist.getPlaylistId();

        // Act
        dependencyProvider.playlistService.renamePlayList(playlistId, newName);
        String playlistName = dependencyProvider.playlistLocalRepository.getPlaylistById(1).getName();
        // Assert
        assertEquals(newName, playlistName);
    }

    @Test
    public void testCreateTemporaryPlaylist(){
        //Arrange
        LinkedList <Integer> chosenSongs = new LinkedList<>();
        chosenSongs.add(1);

        //Act
        dependencyProvider.playlistService.createTemporaryPlaylist(chosenSongs, PlaylistEnum.PUBLIC);

        //Assert
        assertEquals("temporaryPlaylist",
                dependencyProvider.playlistLocalRepository.getPlaylistByName("temporaryPlaylist").getName());
    }

    @Test
    public void testAdjustTemporaryPlaylistToNewPlaylist(){
        //Arrange
        LinkedList <Integer> chosenSongs = new LinkedList<>();
        chosenSongs.add(1);
        chosenSongs.add(3);
        dependencyProvider.playlistService.createTemporaryPlaylist(chosenSongs, PlaylistEnum.PUBLIC);
        String playlistName = "new Playlist";
        int temporaryPlaylistLength = dependencyProvider.playlistLocalRepository
                .getPlaylistByName("temporaryPlaylist")
                .getPlaylistSongsListWithId().size();

        //Act
        dependencyProvider.playlistService.adjustTemporaryPlaylistToNewPlaylist(playlistName, PlaylistEnum.PUBLIC);
        int newPlaylistLength = dependencyProvider.playlistLocalRepository
                .getPlaylistByName(playlistName)
                .getPlaylistSongsListWithId().size();

        //Assert
        assertEquals(temporaryPlaylistLength, newPlaylistLength);
    }

    @Test
    public void testReorderSongsInPlaylist() {
        //Arrange
        testHelper.addSongToPlaylist(playlist.getPlaylistId(), 1, dependencyProvider.playlistLocalRepository, dependencyProvider.playlistService);
        testHelper.addSongToPlaylist(playlist.getPlaylistId(), 2, dependencyProvider.playlistLocalRepository, dependencyProvider.playlistService);
        testHelper.addSongToPlaylist(playlist.getPlaylistId(), 3, dependencyProvider.playlistLocalRepository, dependencyProvider.playlistService);

        // Simuler l'entrée utilisateur
        String input = "2\n1\n3\nx\n";
        Scanner testScanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        PlaylistReorderSongService reorderService = new PlaylistReorderSongService(dependencyProvider.toolBoxService, testScanner);
        reorderService.reorderSongsInPlaylist(playlist.getPlaylistId(), dependencyProvider.playlistService);

        Playlist updated = dependencyProvider.playlistLocalRepository.getPlaylistById(playlist.getPlaylistId());
        assertEquals(List.of(2, 1, 3), updated.getPlaylistSongsListWithId());
    }


}