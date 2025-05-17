package clientSide.view_templatePattern;

import clientSide.player_StatePattern.playlist_player.IPlaylistPlayer;
import clientSide.services.PageService;
import clientSide.services.ToolBoxView;

public class PlaylistHomePage extends TemplateSimplePage {

    public PlaylistHomePage(PageService pageService, IPlaylistPlayer spotifyPlayer, ToolBoxView toolBoxView, int pageId) {
        super(pageService, spotifyPlayer);
        this.toolBoxView = toolBoxView;
        this.pageId = pageId;
        this.pageTitle = "Home Page Playlist";
        this.pageContent =
                icon.backHomePageMusicPlayer + icon.lineBreak +
                icon.nbr(1) + "Choose your playlist" + icon.lineBreak +
                icon.nbr(2) + "Create a playlist";
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
