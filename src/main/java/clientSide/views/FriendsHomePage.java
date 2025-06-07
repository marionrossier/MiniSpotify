package clientSide.views;

import clientSide.player.playlist_player.IPlaylistPlayer;
import clientSide.services.PageService;
import clientSide.services.PrintHelper;
import clientSide.services.ToolBoxView;

public class FriendsHomePage extends TemplateSimplePage {

    public FriendsHomePage(PageService pageService, IPlaylistPlayer spotifyPlayer, ToolBoxView toolBoxView, int pageId) {
        super(pageService, spotifyPlayer);
        this.toolBoxView = toolBoxView;
        this.pageId = pageId;
        this.isFree = false;
        this.pageTitle = "Friends Home Page";
        this.pageContent = PrintHelper.backHomePageMusicPlayer + "\n" + PrintHelper.separator + "\n" +
                PrintHelper.b1 + "Display friends" + "\n" +
                PrintHelper.b2 + "Search friend";
    }

    @Override
    public void button1() {
        pageService.friendsDisplayFriends.displayAllPage();
    }

    @Override
    public void button2() {
        pageService.friendSearch.displayAllPage();
    }
}
