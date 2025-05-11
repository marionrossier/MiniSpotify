package clientSide.view_templatePattern;

import clientSide.player_StatePattern.playlist_player.IPlaylistPlayer;
import clientSide.services.PageService;
import clientSide.services.ViewToolBox;

public class FriendsHomePage extends _SimplePageTemplate {

    public FriendsHomePage(PageService pageService, IPlaylistPlayer spotifyPlayer, ViewToolBox viewToolBox, int pageId) {
        super(pageService, spotifyPlayer);
        this.viewToolBox = viewToolBox;
        this.pageId = pageId;
        this.isFree = false;
        this.pageTitle = "Friends Home Page";
        this.pageContent = icon.zeroBack + icon.lineBreak + //TODO : bien refactorer la classe et ses boutons !
                icon.nbr(1) + "Display friends" + icon.lineBreak +
                icon.nbr(2) + "Add a friend" + icon.lineBreak +
                icon.nbr(3) + "Remove a friend" + icon.lineBreak +
                icon.nbr(4) + "Commune Playlists" + icon.lineBreak  + icon.eightMusicPlayer;
    }

    @Override
    public void button1() {
        pageService.friendsDisplayFriends.displayAllPage();
    }

    @Override
    public void button2() {
        pageService.friendsCommunePlaylists.displayAllPage();
    }

    @Override
    public void button3() {
        pageService.friendAddAFriend.displayAllPage();
    }

    @Override
    public void button4() {
        pageService.friendAddPlaylist.displayAllPage();
    }
}
