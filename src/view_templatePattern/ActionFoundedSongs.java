package view_templatePattern;

import data.entities.Playlist;
import player_StatePattern.playlist_player.IPlaylistPlayer;

import java.util.LinkedList;
import java.util.Scanner;

public class ActionFoundedSongs extends AbstractMenuPage {

    Scanner in = new Scanner(System.in);

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
        Playlist temporaryPlaylist = toolbox.getPlaylistRepo().getPlaylistByName("temporaryPlaylist");
        LinkedList <Integer> songs = temporaryPlaylist.getPlaylistSongsListWithId();

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
        toolbox.getPrintServ().printUserPlaylists();
        displayInput();
        int playlistId = toolbox.getPlaylistServ().validationPlaylistChoice();

        toolbox.getPlaylistServ().addSongToPlaylistFromTemporaryPlaylist(playlistId);
    }

    @Override
    void button3() {
        System.out.print("Playlist Name : ");

        toolbox.getPlaylistServ().createPlaylistWithTemporaryPlaylist(in.nextLine());

        spotifyPageFactory.playlistHomePage.templateMethode();
    }

}
