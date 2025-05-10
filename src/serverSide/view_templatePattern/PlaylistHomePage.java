package serverSide.view_templatePattern;

import clientSide.player_StatePattern.playlist_player.IPlaylistPlayer;
import clientSide.services.PageService;

public class PlaylistHomePage extends _SimplePageTemplate {

    public PlaylistHomePage(PageService pageManager, IPlaylistPlayer spotifyPlayer, int pageId) {
        super(pageManager, spotifyPlayer);
        this.pageId = pageId;
        this.pageTitle = "Home Page Playlist";
        this.pageContent =
                icon.backHomePageMusicPlayer + icon.lineBreak +
                icon.iconNbr(1) + "Choose your playlist" + icon.lineBreak +
                icon.iconNbr(2) + "Create a playlist";
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
