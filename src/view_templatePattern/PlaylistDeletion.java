package view_templatePattern;

import player_StatePattern.playlist_player.IPlaylistPlayer;
import services.PageService;

public class PlaylistDeletion extends _SimplePageTemplate {

    public PlaylistDeletion(PageService pageManager, IPlaylistPlayer spotifyPlayer, int pageId) {
        super(pageManager, spotifyPlayer);
        this.pageId = pageId;
        this.pageTitle = "Delete Playlist Page";
        this.pageContent = icon.iconNbr(0) + icon.iconBack() + icon.lineBreak +
                "Are you sure you want to delete this playlist?" + icon.lineBreak +
                icon.iconNbr(1) + "YES" + icon.lineBreak +
                icon.iconNbr(2) + "NO";
    }

    @Override
    public void button1() {
        toolbox.getPlaylistServ().deletePlaylist(toolbox.getPlaylistServ().getCurrentPlaylistId());
        pageService.playlistHomePage.displayAllPage();
    }

    @Override
    public void button2() {
        System.out.println("Playlist not deleted !");
        pageService.playlistHomePage.displayAllPage();
    }
}
