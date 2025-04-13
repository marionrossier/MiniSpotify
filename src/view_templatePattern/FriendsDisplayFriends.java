package view_templatePattern;

import services.player_commandPattern.SpotifyPlayer;

public class FriendsDisplayFriends extends AbstractMenuPage{
    public FriendsDisplayFriends(SpotifyPageFactory spotifyPageFactory, SpotifyPlayer spotifyPlayer) {
        super(spotifyPageFactory, spotifyPlayer);
        this.pageTitle = "Display Friends Page";
        this.pageContent = backLineWith0 + lineBreak +
                "Tip the friend's number to add him a playlist";
        //TODO : tu veux dire quoi par là ? Comment tu vois la suite ?
    }
}
