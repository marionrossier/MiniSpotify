package demo;

import data.entities.Playlist;
import data.jsons.PlaylistRepository;
import data.jsons.SongRepository;
import player_StatePattern.file_player.MusicPlayer;
import player_StatePattern.file_player.IMusicPlayer;
import player_StatePattern.playlist_player.IPlaylistPlayer;
import player_StatePattern.playlist_player.PlaylistPlayer;
import services.Cookies_SingletonPattern;
// import player_commandPattern.file_player.MusicPlayer; // Quand tu voudras le vrai Player

import java.util.Scanner;

public class PlaylistPlayerDemo {

    public static void main(String[] args) {

        PlaylistRepository playlistRepository = new PlaylistRepository();
        SongRepository songRepository = new SongRepository();

        Playlist playlist = playlistRepository.getAllPlaylists().get(0);

        IMusicPlayer musicPlayer = new MusicPlayer();
        IPlaylistPlayer playlistPlayer = new PlaylistPlayer(musicPlayer, songRepository, playlistRepository);
        playlistPlayer.play(playlist.getPlaylistId(), playlist.getPlaylistSongsListWithId().get(0));
        Cookies_SingletonPattern.setCurrentSongId(playlist.getPlaylistSongsListWithId().get(0));

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        for (int songId : playlist.getPlaylistSongsListWithId()) {
            System.out.println(songRepository.getSongById(songId).getSongName());
        }

        System.out.println("=== MiniSpotify Playlist Player ===");
        System.out.println("Commandes : pause, resume, next, previous, shuffle, repeat, sequential, playback, quit");

        while (running) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();
            String[] parts = input.split("\\s+");

            if (parts.length == 0) continue;

            String command = parts[0].toLowerCase();

            switch (command) {

                case "pause":
                    playlistPlayer.pause();
                    break;

                    //TODO : ajuster... pour pouvoir donner un currentSongID à resume().
//                case "resume":
//                    playlistPlayer.resume();
//                    break;

                case "next":
                    playlistPlayer.next();
                    break;

                case "previous":
                    playlistPlayer.previous();
                    break;

                case "shuffle":
                    playlistPlayer.setShuffleMode();
                    System.out.println("Mode aléatoire activé.");
                    break;

                case "repeat":
                    playlistPlayer.setRepeatMode();
                    System.out.println("Mode répétition activé.");
                    break;

                case "sequential":
                    playlistPlayer.setSequentialMode();
                    System.out.println("Mode séquentiel activé.");
                    break;

                case "playback":
                    playlistPlayer.playback();
                    break;

                case "quit":
                    System.out.println("Arrêt du lecteur...");
                    running = false;
                    break;

                default:
                    System.out.println("Commande inconnue.");
                    break;
            }
        }

        scanner.close();
        musicPlayer.stop();
    }
}
