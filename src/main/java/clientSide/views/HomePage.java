package clientSide.views;

import clientSide.player.playlist_player.IPlaylistPlayer;
import clientSide.services.PageService;
import clientSide.services.PrintHelper;
import clientSide.services.ToolBoxView;

import static clientSide.services.PrintHelper.*;

public class HomePage extends TemplateSimplePage {

    public HomePage(PageService pageService, IPlaylistPlayer spotifyPlayer, ToolBoxView toolBoxView, int pageId) {
        super(pageService, spotifyPlayer);
        this.toolBoxView = toolBoxView;
        this.pageId = pageId;
        this.pageTitle = "Home Page";
        this.pageContent =
                PrintHelper.b0 + "[LOG OUT]" + " | " + PrintHelper.eightMusicPlayer + "\n" +
                        PrintHelper.separator + "\n" +
                PrintHelper.b1 + "Playlists" + "\n" +
                PrintHelper.b2 + "Search song or playlist" + "\n" +
                PrintHelper.b3 + "Friends" + PrintHelper.PREMIUM;
    }

    @Override
    public void button0() {
        printLNInfo("Logging you out ...");
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
