package view_templatePattern;

import player_StatePattern.playlist_player.IPlaylistPlayer;
import services.PageService;

public class HomePage extends _SimplePageTemplate {

    public HomePage(PageService pageManager, IPlaylistPlayer spotifyPlayer, int pageId) {
        super(pageManager, spotifyPlayer);
        this.pageId = pageId;
        this.pageTitle = "Home Page";
        this.pageContent =
                icon.iconNbr(0) + "Log out" + " | " + icon.goToMusicPlayer + icon.lineBreak +
                icon.iconNbr(1) + "Playlists" + icon.lineBreak +
                icon.iconNbr(2) + "Search" + icon.lineBreak +
                icon.iconNbr(3) + "Friends (TODO)" + icon.iconPremium();
    }

    @Override
    public void button0() {
        System.out.println("Logging you out ...");
        spotifyPlayer.stop();

        toolbox.getUserServ().resetCookie();

        pageService.login.displayAllPage();
    }

    @Override
    public void button1() {
        pageService.playlistHomePage.displayAllPage();
    }

    @Override
    public void button2() {
        pageService.search.displayAllPage();
    }

    @Override
    public void button3() {
        pageService.friendsHomePage.displayAllPage();
    }

    @Override
    public void button9(){ //No action !
    }
}
