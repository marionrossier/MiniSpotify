package view_templatePattern;

import player_StatePattern.playlist_player.IPlaylistPlayer;

public class FriendsHomePageTemplate extends _SimplePageTemplate {

    public FriendsHomePageTemplate(SpotifyPageFactory spotifyPageFactory, IPlaylistPlayer spotifyPlayer, int pageId) {
        super(spotifyPageFactory, spotifyPlayer);
        this.pageId = pageId;
        this.pageTitle = "Friends Home Page";
        this.pageContent = icon.iconNbr(0) + icon.iconBack() + icon.lineBreak + //TODO : bien refactorer la classe et ses boutons !
                icon.iconNbr(1) + "Display friends" + icon.lineBreak +
                icon.iconNbr(2) + "Add a friend" + icon.lineBreak +
                icon.iconNbr(3) + "Remove a friend" + icon.lineBreak +
                icon.iconNbr(4) + "Commune Playlists" + icon.lineBreak ;
    }

    @Override
    public void button1() {
        spotifyPageFactory.friendsDisplayFriends.displayAllPage();
    }

    @Override
    public void button2() {
        spotifyPageFactory.friendsCommunePlaylists.displayAllPage();
    }

    @Override
    public void button3() {
        spotifyPageFactory.friendAddAFriend.displayAllPage();
    }

    @Override
    public void button4() {
        spotifyPageFactory.friendAddPlaylist.displayAllPage();
    }
}
