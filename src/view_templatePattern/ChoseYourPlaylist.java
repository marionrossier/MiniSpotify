package view_templatePattern;

import data.entities.Playlist;
import data.entities.User;
import data.jsons.PlaylistRepository;
import data.jsons.UserRepository;
import player_commandPattern.SpotifyPlayer;
import services.Cookies_SingeltonPattern;

import java.util.Scanner;

public class ChoseYourPlaylist extends AbstractMenuPage {

    String firstPageContent;
    UserRepository userRepository = new UserRepository();

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
        User currentUser = userRepository.getUserById(Cookies_SingeltonPattern.getInstance().getUserId()); //232928320
        PlaylistRepository playlistRepository = new PlaylistRepository();
        if (currentUser != null && currentUser.getPlaylists() != null) {
            int i = 1;
            for (int playlistId : currentUser.getPlaylists()) {
                Playlist playlist = playlistRepository.getPlaylistById(playlistId);
                if (playlist != null) {
                    System.out.println(i + ". " + playlist.getPlaylistName());
                    i++;
                }
            }
        } else {
            System.out.println("No playlists available.");
        }
    }

    @Override
    void validateInput(){
        Scanner scanner = new Scanner(System.in);
        User currentUser = userRepository.getUserById(Cookies_SingeltonPattern.getInstance().getUserId());
        int chosenPlaylist = currentUser.getPlaylists().get(scanner.nextInt()-1);
        Cookies_SingeltonPattern.setCurrentPlaylistId(chosenPlaylist);

        //TODO : faire méthode pour gérer les mauvaises saisies

        spotifyPageFactory.onPlaylist.templateMethode();
    }

    @Override
    void switchPage(){}
}
