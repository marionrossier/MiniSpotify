package clientSide.view_templatePattern;

import clientSide.player_StatePattern.playlist_player.IPlaylistPlayer;
import clientSide.services.PageService;
import clientSide.services.ViewToolBox;

public class HomePage extends _SimplePageTemplate {

    public HomePage(PageService pageService, IPlaylistPlayer spotifyPlayer, ViewToolBox viewToolBox, int pageId) {
        super(pageService, spotifyPlayer);
        this.viewToolBox = viewToolBox;
        this.pageId = pageId;
        this.pageTitle = "Home Page";
        this.pageContent =
                icon.nbr(0) + "Log out" + " | " + icon.eightMusicPlayer + icon.lineBreak +
                icon.nbr(1) + "Playlists" + icon.lineBreak +
                icon.nbr(2) + "Search" + icon.lineBreak +
                icon.nbr(3) + "Friends (TODO)" + icon.premium();
    }

    @Override
    public void button0() {
        System.out.println("Logging you out ...");
        spotifyPlayer.stop();

        viewToolBox.getUserServ().resetCookie();

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
