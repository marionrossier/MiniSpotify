package view_templatePattern;

import player_StatePattern.playlist_player.IPlaylistPlayer;

public class FriendsHomePage extends AbstractMenuPage {

    public FriendsHomePage(SpotifyPageFactory spotifyPageFactory, IPlaylistPlayer spotifyPlayer) {
        super(spotifyPageFactory, spotifyPlayer);
        this.pageTitle = "Friends Home Page";
        this.pageContent = icon.iconNbr(0) + icon.iconBack() + icon.lineBreak + //TODO : bien refactorer la classe et ses boutons !
                icon.iconNbr(1) + "Display friends" + icon.lineBreak +
                icon.iconNbr(2) + "Add a friend" + icon.lineBreak +
                icon.iconNbr(3) + "Remove a friend" + icon.lineBreak +
                icon.iconNbr(4) + "Commune Playlists" + icon.lineBreak ;
    }

    @Override
    void button1() {
        spotifyPageFactory.friendsDisplayFriends.templateMethode();
    }

    @Override
    void button2() {
        spotifyPageFactory.friendsCommunePlaylists.templateMethode();
    }

    @Override
    void button3() {
        spotifyPageFactory.friendAddAFriend.templateMethode();
    }

    @Override
    void button4() {
        spotifyPageFactory.friendAddPlaylist.templateMethode();
    }
}
