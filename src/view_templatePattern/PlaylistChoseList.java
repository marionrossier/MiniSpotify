package view_templatePattern;

import data.entities.User;
import player_StatePattern.playlist_player.IPlaylistPlayer;
import services.Cookies_SingletonPattern;
import services.PageService;

public class PlaylistChoseList extends _SimplePageTemplate {

    public PlaylistChoseList(PageService pageManager, IPlaylistPlayer spotifyPlayer, int pageId) {
        super(pageManager, spotifyPlayer);
        this.pageId = pageId;
        this.pageTitle = "Chose Your Playlist Page";
        this.pageContent = "Chose a Playlist below or press \"0\" to go back !";
    }

    @Override
    public void displaySpecificContent() {
        User currentUser = toolbox.getUserRepo().getUserById(Cookies_SingletonPattern.getInstance().getUserId()); //232928320

        if (currentUser != null && currentUser.getPlaylists() != null) {
            toolbox.getPrintServ().printUserPlaylists();
        } else {
            System.out.println("No playlists available.");
        }
    }

    @Override
    public void validateInput() {

        int chosenPlaylist = toolbox.getPlaylistServ().validationPlaylistChoice();

        if (chosenPlaylist == 0) {
            pageService.homePage.displayAllPage();
            return;
        }
        Cookies_SingletonPattern.setCurrentPlaylistId(chosenPlaylist);
        Cookies_SingletonPattern.setCurrentSongId(
                toolbox.getPlaylistRepo().getPlaylistById(chosenPlaylist).getPlaylistSongsListWithId().getFirst());
        pageService.playlistDisplay.displayAllPage();
    }
}
