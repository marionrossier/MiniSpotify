package view_templatePattern;

import data.entities.User;
import data.jsons.PlaylistRepository;
import data.jsons.UserRepository;
import player_StatePattern.playlist_player.IPlaylistPlayer;
import services.Cookies_SingletonPattern;
import services.PlaylistServices;

public class PlaylistChoseList extends AbstractMenuPage {

    UserRepository userRepository = new UserRepository();
    PlaylistRepository playlistRepository = new PlaylistRepository();
    PlaylistServices playlistService = new PlaylistServices(playlistRepository);

    public PlaylistChoseList(SpotifyPageFactory spotifyPageFactory, IPlaylistPlayer spotifyPlayer) {
        super(spotifyPageFactory, spotifyPlayer);
        this.pageTitle = "Chose Your Playlist Page";
        this.pageContent = "Chose a Playlist below or press \"0\"";
    }

    @Override
    void displaySpecificContent() {
        User currentUser = userRepository.getUserById(Cookies_SingletonPattern.getInstance().getUserId()); //232928320

        if (currentUser != null && currentUser.getPlaylists() != null) {
            playlistService.printUserPlaylists();
        } else {
            System.out.println("No playlists available.");
        }
    }

    @Override
    void validateInput() {

        int chosenPlaylist = playlistService.validationPlaylistChoice();

        if (chosenPlaylist == 0) {
            spotifyPageFactory.homePage.templateMethode();
            return;
        }
        Cookies_SingletonPattern.setCurrentPlaylistId(chosenPlaylist);
        Cookies_SingletonPattern.setCurrentSongId(playlistRepository.getCurrentSongByPlaylistID(chosenPlaylist));
        spotifyPageFactory.playlistDisplay.templateMethode();
    }

    @Override
    void switchPage(){}
}
