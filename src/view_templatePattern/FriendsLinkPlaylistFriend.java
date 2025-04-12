package view_templatePattern;

import services.player_commandPattern.SpotifyPlayer;

public class FriendsLinkPlaylistFriend extends AbstractMenuPage{
    public FriendsLinkPlaylistFriend(PageFactory pageFactory, SpotifyPlayer spotifyPlayer) {
        super(pageFactory, spotifyPlayer);
    }

    @Override
    void displayPage() {
        System.out.println("Link a playlist to a friend");
        System.out.println("Tip the playlist you would like to link: ");
    }
}
