import player_commandPattern.SpotifyPlayer;
import player_commandPattern.commands.ICommand;
import player_commandPattern.receivers.SpotifyService;
import view_templatePattern.*;

import java.util.Stack;

public class Main {
    public static void main(String[] args) {

        // Initialisation des services nécessaires
        SpotifyService spotifyService = new SpotifyService();

        // Création de l'instance SpotifyPlayer
        SpotifyPlayer spotifyPlayer = new SpotifyPlayer(
                spotifyService,
                new Stack<ICommand>());

        // Création de l'instance SpotifyPageFactory avec SpotifyPlayer
        SpotifyPageFactory miniSpotify = new SpotifyPageFactory();
        miniSpotify.spotifyPlayer = spotifyPlayer; // Initialisation de SpotifyPlayer dans la factory

        // Configuration des pages
        miniSpotify.setUpPages();

        // Démarrage de l'application
        miniSpotify.startLogin();
    }
}