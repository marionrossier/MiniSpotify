package clientSide.views;

import clientSide.player.playlist_player.IPlaylistPlayer;
import clientSide.services.PageService;
import clientSide.services.ToolBoxView;

public class FriendsHomePage extends TemplateSimplePage {

    public FriendsHomePage(PageService pageService, IPlaylistPlayer spotifyPlayer, ToolBoxView toolBoxView, int pageId) {
        super(pageService, spotifyPlayer);
        this.toolBoxView = toolBoxView;
        this.pageId = pageId;
        this.isFree = false;
        this.pageTitle = "Friends Home Page";
        this.pageContent = icon.zeroBack + " | " + icon.nineHomepage + icon.lineBreak + icon.separator + icon.lineBreak +
                icon.nbr1() + "Display friends" + icon.lineBreak +
                icon.nbr2() + "Search friend" + icon.lineBreak;
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
