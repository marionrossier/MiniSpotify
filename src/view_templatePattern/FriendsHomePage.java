package view_templatePattern;

import services.player_commandPattern.SpotifyPlayer;

public class FriendsHomePage extends AbstractMenuPage{

    public FriendsHomePage(PageFactory pageFactory, SpotifyPlayer spotifyPlayer) {
        super(pageFactory, spotifyPlayer);
    }

    @Override
    void displayPage() {
        System.out.println("Friends");
        System.out.print("0) Exit\n" +
                "1) display friends\n" +
                "2) see commune playlists\n" +
                "3) add a friend\n" +
                "4) link a playlist to a friend\n");
        super.displayPage();
    }

    @Override
    void button1() {
        pageFactory.friendsDisplayFriends.displayPage();
    }

    @Override
    void button2() {
        pageFactory.friendsCommunePlaylists.displayPage();
    }

    @Override
    void button3() {
        pageFactory.friendsAddFriend.displayPage();
    }

    @Override
    void button4() {
        pageFactory.friendsLinkPlaylistFriend.displayPage();
    }
}
