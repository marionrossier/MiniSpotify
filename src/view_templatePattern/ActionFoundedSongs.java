package view_templatePattern;

import data.entities.Playlist;
import data.entities.User;
import data.jsons.PlaylistRepository;
import data.jsons.UserRepository;
import player_StatePattern.playlist_player.IPlaylistPlayer;
import services.Cookies_SingletonPattern;
import services.PlaylistServices;
import services.SongService;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class ActionFoundedSongs extends AbstractMenuPage {

    PlaylistRepository playlistRepository = new PlaylistRepository();
    Scanner in = new Scanner(System.in);
    SongService songService = new SongService();
    PlaylistServices playlistService = new PlaylistServices();
    UserRepository userRepository = new UserRepository();

    public ActionFoundedSongs(SpotifyPageFactory spotifyPageFactory, IPlaylistPlayer spotifyPlayer) {
        super(spotifyPageFactory, spotifyPlayer);

        this.pageTitle = "Chose your action for the founded songs";
        this.pageContent = backLineWith0 + lineBreak +
                    nb1 + "Play Temporary Playlist" + lineBreak +
                    nb2 + "Add to playlist" + lineBreak +
                    nb3 + "Create a playlist";
    }

    @Override
    void button0() {
        spotifyPageFactory.search.templateMethode();
    }

    @Override
    void button1() {
        List<Integer> songs = Cookies_SingletonPattern.getInstance().getTemporaryPlaylist();
        Playlist temporaryPlaylist = new Playlist("temporaryPlaylist");
        temporaryPlaylist.setPlaylistSongsId((LinkedList<Integer>) Cookies_SingletonPattern.getInstance().getTemporaryPlaylist());

        if (songs != null && !songs.isEmpty()) {
            for (Integer song : songs) {
                spotifyPageFactory.songPlayer.templateMethode();
            }
        } else {
            System.out.println("No songs found to play.");
        }
        spotifyPageFactory.search.templateMethode();
    }

    @Override
    void button2() {
        System.out.println("Select your playlist : ");
        playlistService.printUserPlaylists();
        displayInput();
        int playlistId = playlistService.validationPlaylistChoice();

        playlistService.addSongToPlaylistFromTemporaryPlaylist(playlistId);
    }

    @Override
    void button3() {
        System.out.println("Playlist Name : ");

        Playlist newPlaylist = new Playlist(in.nextLine());
        newPlaylist.setPlaylistSongsId((LinkedList<Integer>) Cookies_SingletonPattern.getInstance().getTemporaryPlaylist());
        User user = userRepository.getUserById(Cookies_SingletonPattern.getInstance().getUserId());
        userRepository.updateAccount(user).addOnePlaylist(newPlaylist.getPlaylistId());

        spotifyPageFactory.playlistHomePage.templateMethode();
    }

}
