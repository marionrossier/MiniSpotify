import data.entities.Playlist;
import data.entities.Song;
import data.storage.PlaylistRepository;
import data.storage.SongRepository;
import view_templatePattern.PageFactory;
import view_templatePattern.SongPlayer;
import services.player_commandPattern.SpotifyPlayer;
import services.player_commandPattern.commands.ICommand;
import services.player_commandPattern.receivers.SpotifyService;

import java.util.Stack;

public class Main {
    public static void main(String[] args) {

        SongRepository songRepository = new SongRepository();
        PlaylistRepository playlistRepository = new PlaylistRepository();

        Song song1 = songRepository.findSongById(1);
        Playlist allSongs = playlistRepository.findPlaylistByName("AllSongs");

        SpotifyService spotifyService = new SpotifyService(
                song1.getSongId(),
                allSongs.getPlaylistId());

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