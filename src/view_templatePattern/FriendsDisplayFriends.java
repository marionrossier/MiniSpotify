package view_templatePattern;

import services.player_commandPattern.SpotifyPlayer;

public class FriendsDisplayFriends extends AbstractMenuPage{
    public FriendsDisplayFriends(PageFactory pageFactory, SpotifyPlayer spotifyPlayer) {
        super(pageFactory, spotifyPlayer);
    }

    @Override
    void displayPage() {
        System.out.println("Display friends");
        System.out.print("0) Exit\n" +
                "Tip the friend's number to add him a playlist\n");
    }
}
