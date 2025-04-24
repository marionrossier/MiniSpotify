package view_templatePattern;

import data.entities.Playlist;
import data.entities.User;
import data.jsons.PlaylistRepository;
import data.jsons.UserRepository;
import player_commandPattern.SpotifyPlayer;
import services.Cookies_SingeltonPattern;

import java.util.Objects;
import java.util.Scanner;

public class ChoseYourPlaylist extends AbstractMenuPage {

    String firstPageContent;
    UserRepository userRepository = new UserRepository();
    PlaylistRepository playlistRepository = new PlaylistRepository();

    public ChoseYourPlaylist(SpotifyPageFactory spotifyPageFactory, SpotifyPlayer spotifyPlayer) {
        super(spotifyPageFactory, spotifyPlayer);
        this.pageTitle = "Chose Your Playlist Page";
        this.pageContent = backLineWith0 + lineBreak +
                nb1 + "Play Playlist" + lineBreak +
                nb2 + "Rename Playlist" + lineBreak +
                nb3 + "Delete Playlist";
        this.firstPageContent = "Chose a Playlist below or press \"0\" :";
    }

    @Override
    void displayContent(String pageContent) {
        System.out.println(this.firstPageContent);
    }
    @Override
    void displaySpecificContent() {
        User currentUser = userRepository.getUserById(Cookies_SingeltonPattern.getInstance().getUserId()); //232928320
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
    void validateInput() {
        Scanner scanner = new Scanner(System.in);
        User currentUser = userRepository.getUserById(Cookies_SingeltonPattern.getInstance().getUserId());

        int chosenPlaylist = -1;

        while (true) {
            String input = scanner.nextLine();

            if (Objects.equals(input, "0")) {
                spotifyPageFactory.homePagePlaylist.templateMethode();
                return;
            }

            try {
                int inputNumber = Integer.parseInt(input);

                if (inputNumber < 1 || inputNumber > currentUser.getPlaylists().size()) {
                    System.err.println("Invalid Playlist number.");
                    System.out.println("Try again or press \"0\" to go back : ");
                } else {
                    chosenPlaylist = currentUser.getPlaylists().get(inputNumber - 1);
                    break;
                }
            } catch (NumberFormatException e) {
                System.err.println("Invalid input, please enter a number : ");
            }
        }

        Cookies_SingeltonPattern.setCurrentPlaylistId(chosenPlaylist);
        spotifyPageFactory.onPlaylist.templateMethode();
    }

    @Override
    void switchPage(){}
}
