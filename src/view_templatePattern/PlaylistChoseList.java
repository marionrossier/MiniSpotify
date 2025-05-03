package view_templatePattern;

import data.entities.User;
import player_StatePattern.playlist_player.IPlaylistPlayer;
import services.Cookies_SingletonPattern;

public class PlaylistChoseList extends AbstractMenuPage {

    public PlaylistChoseList(SpotifyPageFactory spotifyPageFactory, IPlaylistPlayer spotifyPlayer) {
        super(spotifyPageFactory, spotifyPlayer);
        this.pageTitle = "Chose Your Playlist Page";
        this.pageContent = "Chose a Playlist below or press \"0\"";
    }

    @Override
    void displaySpecificContent() {
        User currentUser = toolbox.getUserRepo().getUserById(Cookies_SingletonPattern.getInstance().getUserId()); //232928320

        if (currentUser != null && currentUser.getPlaylists() != null) {
            toolbox.getPrintServ().printUserPlaylists();
        } else {
            System.out.println("No playlists available.");
        }
    }

    @Override
    void validateInput() {

        int chosenPlaylist = toolbox.getPlaylistServ().validationPlaylistChoice();

        if (chosenPlaylist == 0) {
            spotifyPageFactory.homePage.templateMethode();
            return;
        }
        Cookies_SingletonPattern.setCurrentPlaylistId(chosenPlaylist);
        Cookies_SingletonPattern.setCurrentSongId(
                toolbox.getPlaylistRepo().getPlaylistById(chosenPlaylist).getPlaylistSongsListWithId().getFirst());
        spotifyPageFactory.playlistDisplay.templateMethode();
    }

    @Override
    void switchPage(){}
}
