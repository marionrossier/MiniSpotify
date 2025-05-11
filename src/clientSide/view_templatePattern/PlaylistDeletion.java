package clientSide.view_templatePattern;

import clientSide.player_StatePattern.playlist_player.IPlaylistPlayer;
import clientSide.services.PageService;
import clientSide.services.ViewToolBox;

public class PlaylistDeletion extends _SimplePageTemplate {

    public PlaylistDeletion(PageService pageService, IPlaylistPlayer spotifyPlayer, ViewToolBox viewToolBox, int pageId) {
        super(pageService, spotifyPlayer);
        this.viewToolBox = viewToolBox;
        this.pageId = pageId;
        this.pageTitle = "Delete Playlist Page";
        this.pageContent = icon.zeroBack + icon.lineBreak +
                "Are you sure you want to delete this playlist?" + icon.lineBreak +
                icon.nbr(1) + "YES" + icon.lineBreak +
                icon.nbr(2) + "NO";
    }

    @Override
    public void button1() {
        viewToolBox.getPlaylistServ().deletePlaylist(viewToolBox.getPlaylistServ().getCurrentPlaylistId());
        pageService.playlistHomePage.displayAllPage();
    }

    @Override
    public void button2() {
        System.out.println("Playlist not deleted !");
        pageService.playlistHomePage.displayAllPage();
    }
}
