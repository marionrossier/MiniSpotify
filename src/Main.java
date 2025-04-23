import player_commandPattern.SpotifyPlayer;
import player_commandPattern.commands.ICommand;
import player_commandPattern.receivers.SpotifyService;
import view_templatePattern.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {
//        try {
//            FileInputStream audioFile = new FileInputStream("src/data/jsons/song.json");
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//
//        try {
//            FileInputStream audioFile = new FileInputStream("resources/songsfiles/Rehab - Amy Winehouse - Back to Black - 2006 - Soul _ R&B - 0334.mp3");
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }

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