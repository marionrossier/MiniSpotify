import data.entities.*;
import data.storage.*;
import services.player_commandPattern.SpotifyPlayer;
import services.player_commandPattern.commands.ICommand;
import services.player_commandPattern.receivers.SpotifyService;
import view_templatePattern.*;

import java.util.Stack;

public class Main {
    public static void main(String[] args) {

//        SongRepository songRepository = new SongRepository();
//        PlaylistRepository playlistRepository = new PlaylistRepository();
//
//        Song song1 = songRepository.findSongById(1);
//        Playlist allSongs = playlistRepository.findPlaylistByName("AllSongs");
//
//        SpotifyService spotifyService = new SpotifyService(
//                song1.getSongId(),
//                allSongs.getPlaylistId());
//
//        SpotifyPlayer spotifyPlayer = new SpotifyPlayer(
//                spotifyService,
//                new Stack<ICommand>());
//
//        SongPlayer songPlayer = new SongPlayer(
//                new SpotifyPageFactory(),
//                spotifyPlayer);


        //Partie test!

        SpotifyPageFactory miniSpotify = new SpotifyPageFactory();
        miniSpotify.setUpPages();
        miniSpotify.startLogin();
    }
}