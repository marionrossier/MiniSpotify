import player_commandPattern.SpotifyPlayer;
import player_commandPattern.commands.ICommand;
import player_commandPattern.receivers.SpotifyService;
import view_templatePattern.SpotifyPageFactory;

import java.util.Stack;

public class Spotify {
    public static void startApp(){
        SpotifyService spotifyService = new SpotifyService();

        SpotifyPlayer spotifyPlayer = new SpotifyPlayer(
                spotifyService,
                new Stack<ICommand>());

        SpotifyPageFactory miniSpotify = new SpotifyPageFactory();
        miniSpotify.spotifyPlayer = spotifyPlayer; // Initialisation de SpotifyPlayer dans la factory

        miniSpotify.setUpPages();
        miniSpotify.startLogin();
    }
}
