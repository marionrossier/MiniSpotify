package view_templatePattern;

import data.entities.User;
import data.jsons.UserRepository;
import player_commandPattern.SpotifyPlayer;
import services.Cookies_SingeltonPattern;
import services.PlaylistServices;

import java.util.Objects;
import java.util.Scanner;

public class PlaylistChoseList extends AbstractMenuPage {

    UserRepository userRepository = new UserRepository();
    User currentUser = userRepository.getUserById(Cookies_SingeltonPattern.getInstance().getUserId()); //232928320
    PlaylistServices playlistService = new PlaylistServices();

    public PlaylistChoseList(SpotifyPageFactory spotifyPageFactory, SpotifyPlayer spotifyPlayer) {
        super(spotifyPageFactory, spotifyPlayer);
        this.pageTitle = "Chose Your Playlist Page";
        this.pageContent = "Chose a Playlist below or press \"0\"";
    }

    @Override
    void displaySpecificContent() {
        if (currentUser != null && currentUser.getPlaylists() != null) {
            playlistService.printUserPlaylists();
        } else {
            System.out.println("No playlists available.");
        }
    }

    @Override
    void validateInput() {
        //TODO : transformer en méthode dans l'abstract ? si besoin plusieurs fois oui !
        Scanner scanner = new Scanner(System.in);
        User currentUser = userRepository.getUserById(Cookies_SingeltonPattern.getInstance().getUserId());

        int chosenPlaylist = -1;

        while (true) {
            String input = scanner.nextLine();

            if (Objects.equals(input, "0")) {
                spotifyPageFactory.playlistHomePage.templateMethode();
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
        spotifyPageFactory.playlistDisplay.templateMethode();
    }

    @Override
    void switchPage(){}
}
