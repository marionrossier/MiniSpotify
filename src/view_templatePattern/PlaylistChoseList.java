package view_templatePattern;

import player_StatePattern.playlist_player.IPlaylistPlayer;
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
        toolbox.getPrintServ().printUserPlaylists(toolbox.getUserServ().getCurrentUserId());
    }

    @Override
    public void validateInput() {
        toolbox.getPlaylistServ().validatePlaylistIdInput(pageService, toolbox.getSongServ());
    }
}
