package view_templatePattern;

import data.entities.User;
import data.jsons.UserRepository;
import player_commandPattern.SpotifyPlayer;
import services.Cookies_SingeltonPattern;
import services.PlaylistServices;

public class PlaylistChoseList extends AbstractMenuPage {

    UserRepository userRepository = new UserRepository();
    PlaylistServices playlistService = new PlaylistServices();

    public PlaylistChoseList(SpotifyPageFactory spotifyPageFactory, SpotifyPlayer spotifyPlayer) {
        super(spotifyPageFactory, spotifyPlayer);
        this.pageTitle = "Chose Your Playlist Page";
        this.pageContent = "Chose a Playlist below or press \"0\"";
    }

    @Override
    void displaySpecificContent() {
        User currentUser = userRepository.getUserById(Cookies_SingeltonPattern.getInstance().getUserId()); //232928320

        if (currentUser != null && currentUser.getPlaylists() != null) {
            playlistService.printUserPlaylists();
        } else {
            System.out.println("No playlists available.");
        }
    }

    @Override
    void validateInput() {

        int chosenPlaylist = playlistService.validationPlaylistChoice();

        Cookies_SingeltonPattern.setCurrentPlaylistId(chosenPlaylist);
        spotifyPageFactory.playlistDisplay.templateMethode();
    }

    @Override
    void switchPage(){}
}
