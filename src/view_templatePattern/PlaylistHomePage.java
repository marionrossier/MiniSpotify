package view_templatePattern;

import player_StatePattern.playlist_player.IPlaylistPlayer;
import services.PageService;

public class PlaylistHomePage extends _SimplePageTemplate {

    public PlaylistHomePage(PageService pageManager, IPlaylistPlayer spotifyPlayer, int pageId) {
        super(pageManager, spotifyPlayer);
        this.pageId = pageId;
        this.pageTitle = "Home Page Playlist";
        this.pageContent =
                icon.iconNbr(0) + icon.iconBack() + icon.lineBreak +
                icon.iconNbr(1) + "Choose your playlist" + icon.lineBreak +
                icon.iconNbr(2) + "Create a playlist" + icon.goToMusicPlayer;
    }

    @Override
    public void button1() {
        pageService.playlistChoseList.displayAllPage();
    }

    @Override
    public void button2() {
        pageService.playlistCreation.displayAllPage();
    }

}
