import entities.Playlist;
import entities.Song;
import interfaces_templatePattern.AbstractMenuPage;
import interfaces_templatePattern.Login;
import interfaces_templatePattern.PageFactory;
import interfaces_templatePattern.SongPlayer;
import player_commandPattern.SpotifyPlayer;
import player_commandPattern.commands.ICommand;
import player_commandPattern.recievers.SpotifyService;

import java.io.FileInputStream;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {

        // Initialisation
        Song song1 = new Song("SongTitle1", "SongArtist",
                "SongAlbum", 3.5, "Classical","1",
                "src/ressources/boneyM_Sunny.mp3");


        Song song2 = new Song("SongTitle2", "SongArtist",
                "SongAlbum", 3, "Classical","2",
                "src/ressources/Old Nokia Tune Original Classic [Nokia 3310].mp3");
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
    }
}