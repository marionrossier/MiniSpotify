package view_templatePattern;

import services.player_commandPattern.SpotifyPlayer;

public class FriendsAddFriend extends AbstractMenuPage{
    public FriendsAddFriend(PageFactory pageFactory, SpotifyPlayer spotifyPlayer) {
        super(pageFactory, spotifyPlayer);
    }

    @Override
    void displayPage() {
        System.out.println("Add friend");
        System.out.println("Tip pseudo of your friend you want to add: ");

    }

}
