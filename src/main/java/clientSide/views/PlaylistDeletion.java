package clientSide.views;

import clientSide.player.playlist_player.IPlaylistPlayer;
import clientSide.services.PageService;
import clientSide.services.PrintHelper;
import clientSide.services.ToolBoxView;

import static clientSide.services.PrintHelper.*;

public class PlaylistDeletion extends TemplateSimplePage {

    public PlaylistDeletion(PageService pageService, IPlaylistPlayer spotifyPlayer, ToolBoxView toolBoxView, int pageId) {
        super(pageService, spotifyPlayer);
        this.toolBoxView = toolBoxView;
        this.pageId = pageId;
        this.pageTitle = "Delete Playlist Page";
        this.pageContent = PrintHelper.zeroBack + PrintHelper.lineBreak + PrintHelper.separator + PrintHelper.lineBreak +
                "Are you sure you want to delete this playlist?" + PrintHelper.lineBreak +
                PrintHelper.nbr1 + "YES" + PrintHelper.lineBreak +
                PrintHelper.nbr2 + "NO";
    }

    @Override
    public void button1() {
        toolBoxView.getPlaylistServ().deletePlaylist(toolBoxView.getPlaylistServ().getCurrentPlaylistId());
        pageService.playlistHomePage.displayAllPage();
    }

    @Override
    public void button2() {
        printLNInfo("Playlist not deleted !");
        pageService.playlistHomePage.displayAllPage();
    }
}
