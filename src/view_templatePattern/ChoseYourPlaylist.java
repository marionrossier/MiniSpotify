package view_templatePattern;

import data.entities.Playlist;
import data.entities.User;
import data.jsons.PlaylistRepository;
import data.jsons.UserRepository;
import player_commandPattern.SpotifyPlayer;
import services.Cookies_SingeltonPattern.CookiePlaylist;
import services.Cookies_SingeltonPattern.CookieUser;

import java.util.Scanner;

public class ChoseYourPlaylist extends AbstractMenuPage {

    String firstPageContent;

    public ChoseYourPlaylist(SpotifyPageFactory spotifyPageFactory, SpotifyPlayer spotifyPlayer) {
        super(spotifyPageFactory, spotifyPlayer);
        this.pageTitle = "Chose Your Playlist Page";
        this.pageContent = backLineWith0 + lineBreak +
                nb1 + "Play Playlist" + lineBreak +
                nb2 + "Rename Playlist" + lineBreak +
                nb3 + "Delete Playlist";
        this.firstPageContent = "Chose a Playlist below :";
    }

    @Override
    void displayContent(String pageContent) {
        System.out.println(this.firstPageContent);
    }
    @Override
    void displaySpecificContent() {
        UserRepository userRepository = new UserRepository();
        User currentUser = userRepository.findUserById(CookieUser.getInstance().getId()); //232928320
        Scanner scanner = new Scanner(System.in);

        PlaylistRepository playlistRepository = new PlaylistRepository();

        if (currentUser != null && currentUser.getPlaylists() != null) {
            int i = 1;
            for (Integer playlistId : currentUser.getPlaylists()) {
                Playlist playlist = playlistRepository.findPlaylistById(playlistId);
                if (playlist != null) {
                    System.out.println(i + ". " + playlist.getPlaylistName());
                    i++;
                }
            }
        } else {
            System.out.println("No playlists available.");
        }
        displayInput();
        CookiePlaylist.setInstance(scanner.nextInt());

        spotifyPageFactory.onPlaylist.templateMethode();
    }
}
