package clientSide.view_templatePattern;

import clientSide.player_StatePattern.playlist_player.IPlaylistPlayer;
import clientSide.services.PageService;
import clientSide.services.ToolBoxView;

import static clientSide.services.PrintHelper.*;

public class HomePage extends TemplateSimplePage {

    public HomePage(PageService pageService, IPlaylistPlayer spotifyPlayer, ToolBoxView toolBoxView, int pageId) {
        super(pageService, spotifyPlayer);
        this.toolBoxView = toolBoxView;
        this.pageId = pageId;
        this.pageTitle = "Home Page";
        this.pageContent =
                icon.nbr0() + "Log out" + " | " + icon.eightMusicPlayer + icon.lineBreak +
                icon.nbr1() + "Playlists" + icon.lineBreak +
                icon.nbr2() + "Search song" + icon.lineBreak +
                icon.nbr3() + "Friends" + icon.premium();
    }

    @Override
    public void button0() {
        printLNWhite("Logging you out ...");
        spotifyPlayer.stop();

        toolBoxView.getUserServ().resetCookie();

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
