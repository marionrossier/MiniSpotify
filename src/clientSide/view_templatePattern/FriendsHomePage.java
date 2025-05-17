package clientSide.view_templatePattern;

import clientSide.player_StatePattern.playlist_player.IPlaylistPlayer;
import clientSide.services.PageService;
import clientSide.services.ToolBoxView;

public class FriendsHomePage extends TemplateSimplePage {

    public FriendsHomePage(PageService pageService, IPlaylistPlayer spotifyPlayer, ToolBoxView toolBoxView, int pageId) {
        super(pageService, spotifyPlayer);
        this.toolBoxView = toolBoxView;
        this.pageId = pageId;
        this.isFree = false;
        this.pageTitle = "Friends Home Page";
        this.pageContent = icon.zeroBack + icon.nineHomepage + icon.lineBreak +
                icon.nbr(1) + "Display friends" + icon.lineBreak +
                icon.nbr(2) + "Search friend" + icon.lineBreak;
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
