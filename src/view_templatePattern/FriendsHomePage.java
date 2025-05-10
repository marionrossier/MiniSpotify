package view_templatePattern;

import player_StatePattern.playlist_player.IPlaylistPlayer;
import services.PageService;

public class FriendsHomePage extends _SimplePageTemplate {

    public FriendsHomePage(PageService pageManager, IPlaylistPlayer spotifyPlayer, int pageId) {
        super(pageManager, spotifyPlayer);
        this.pageId = pageId;
        this.isFree = false;
        this.pageTitle = "Friends Home Page";
        this.pageContent = icon.iconNbr(0) + icon.iconBack() + icon.lineBreak + //TODO : bien refactorer la classe et ses boutons !
                icon.iconNbr(1) + "Display friends" + icon.lineBreak +
                icon.iconNbr(2) + "Add a friend" + icon.lineBreak +
                icon.iconNbr(3) + "Remove a friend" + icon.lineBreak +
                icon.iconNbr(4) + "Commune Playlists" + icon.lineBreak  + icon.goToMusicPlayer;
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
