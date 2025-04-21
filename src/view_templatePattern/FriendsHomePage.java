package view_templatePattern;

import player_commandPattern.SpotifyPlayer;

public class FriendsHomePage extends AbstractMenuPage{

    public FriendsHomePage(SpotifyPageFactory spotifyPageFactory, SpotifyPlayer spotifyPlayer) {
        super(spotifyPageFactory, spotifyPlayer);
        this.pageTitle = "Friends Home Page";
        this.pageContent = backLineWith0 + lineBreak +
                nb1 + "Display friends" + lineBreak +
                nb2 + "See commune playlists" + lineBreak +
                nb3 + "Add a friend" + lineBreak +
                nb4 + "Link a playlist to a friend";
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
        spotifyPageFactory.friendsFollowFriend.templateMethode();
    }

    @Override
    void button4() {
        spotifyPageFactory.friendsLinkPlaylistFriend.templateMethode();
    }
}
