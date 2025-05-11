package clientSide.view_templatePattern;

import clientSide.player_StatePattern.playlist_player.IPlaylistPlayer;
import clientSide.services.PageService;
import clientSide.services.ViewToolBox;

public class PlaylistHomePage extends _SimplePageTemplate {

    public PlaylistHomePage(PageService pageService, IPlaylistPlayer spotifyPlayer, ViewToolBox viewToolBox, int pageId) {
        super(pageService, spotifyPlayer);
        this.viewToolBox = viewToolBox;
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
