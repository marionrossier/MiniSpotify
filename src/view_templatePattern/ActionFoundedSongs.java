package view_templatePattern;

import data.entities.Playlist;
import data.entities.User;
import data.jsons.PlaylistRepository;
import data.jsons.UserRepository;
import player_StatePattern.playlist_player.IPlaylistPlayer;
import services.Cookies_SingletonPattern;
import services.PlaylistServices;
import services.PrintService;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class ActionFoundedSongs extends AbstractMenuPage {

    Scanner in = new Scanner(System.in);
    PlaylistRepository playlistRepository = new PlaylistRepository();
    PlaylistServices playlistService = new PlaylistServices(playlistRepository);
    UserRepository userRepository = new UserRepository();
    PrintService printService = new PrintService();

    public ActionFoundedSongs(SpotifyPageFactory spotifyPageFactory, IPlaylistPlayer spotifyPlayer) {
        super(spotifyPageFactory, spotifyPlayer);

        this.pageTitle = "Chose your action for the founded songs";
        this.pageContent = icon.iconNbr(0) + icon.iconBack() + icon.lineBreak +
                    icon.iconNbr(1) + "Play Temporary Playlist" + icon.lineBreak +
                    icon.iconNbr(2) + "Add to playlist" + icon.lineBreak +
                    icon.iconNbr(3) + "Create a playlist";
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

        Cookies_SingletonPattern.setCurrentPlaylistId(temporaryPlaylist.getPlaylistId());

        if (songs != null && !songs.isEmpty()) {
                spotifyPageFactory.songPlayer.templateMethode();
        } else {
            System.out.println("No songs found to play.");
        }
        spotifyPageFactory.search.templateMethode();
    }

    @Override
    void button2() {
        System.out.println("Select your playlist : ");
        printService.printUserPlaylists();
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
