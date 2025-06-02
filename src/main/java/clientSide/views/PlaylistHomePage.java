package clientSide.views;

import clientSide.player.playlist_player.IPlaylistPlayer;
import clientSide.services.PageService;
import clientSide.services.PrintHelper;
import clientSide.services.ToolBoxView;

public class PlaylistHomePage extends TemplateSimplePage {

    public PlaylistHomePage(PageService pageService, IPlaylistPlayer spotifyPlayer, ToolBoxView toolBoxView, int pageId) {
        super(pageService, spotifyPlayer);
        this.toolBoxView = toolBoxView;
        this.pageId = pageId;
        this.pageTitle = "Home Page Playlist";
        this.pageContent =
                PrintHelper.backHomePageMusicPlayer + PrintHelper.lineBreak + PrintHelper.separator + PrintHelper.lineBreak +
                PrintHelper.nbr1 + "Choose your playlist" + PrintHelper.lineBreak +
                PrintHelper.nbr2 + "Create a playlist";
    }

    @Override
    public void button1() {
        pageService.playlistChoseList.displayAllPage();
    }

    @Override
    public void button2() {
        pageService.search.displayAllPage();
    }
}
