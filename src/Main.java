import datas.entities.Playlist;
import datas.entities.Song;
import view_templatePattern.PageFactory;
import view_templatePattern.SongPlayer;
import services.player_commandPattern.SpotifyPlayer;
import services.player_commandPattern.commands.ICommand;
import services.player_commandPattern.receivers.SpotifyService;

import java.util.Stack;

public class Main {
    public static void main(String[] args) {

        // Initialisation
        Song song1 = new Song("SongTitle1", "SongArtist",
                "SongAlbum", 3.5, "Classical","1",
                "src/datas/songsfiles/boneyM_Sunny.mp3");


        Song song2 = new Song("SongTitle2", "SongArtist",
                "SongAlbum", 3, "Classical","2",
                "src/datas/songsfiles/Old Nokia Tune Original Classic [Nokia 3310].mp3");
        Playlist allSongs = new Playlist("AllSongs", "1");
        allSongs.addSong(song1);
        allSongs.addSong(song2);

        SpotifyService spotifyService = new SpotifyService(song1, allSongs);
        SpotifyPlayer spotifyPlayer = new SpotifyPlayer(
                spotifyService,
                new Stack<ICommand>());
        SongPlayer songPlayer = new SongPlayer(
                new PageFactory(),
                spotifyPlayer);


        //Partie test!

        PageFactory pages = new PageFactory();
        pages.setUpPages();
        pages.startLogin();
    }
}