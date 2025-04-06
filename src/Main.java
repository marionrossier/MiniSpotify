import entities.Playlist;
import entities.Song;
import interfaces_templatePattern.SongPlayer;
import player_commandPattern.SpotifyPlayer;
import player_commandPattern.commands.ICommand;
import player_commandPattern.recievers.SpotifyService;

import java.util.Stack;

public class Main {
    public static void main(String[] args) {

        // Initialisation
        Song song1 = new Song("SongTitle1", "SongArtist",
                "SongAlbum", 3.5, "Classical","1");
        Song song2 = new Song("SongTitle2", "SongArtist",
                "SongAlbum", 3, "Classical","2");
        Playlist allSongs = new Playlist("AllSongs", "1");
        allSongs.addSong(song1);
        allSongs.addSong(song2);

        SongPlayer songPlayer = new SongPlayer(
                new SpotifyPlayer(
                        new SpotifyService(song1, allSongs),
                        new Stack<ICommand>()));


        //Partie test!

        songPlayer.button0();
    }
}