package view_templatePattern;

import services.player_commandPattern.SpotifyPlayer;

public class FriendsFollowFriend extends AbstractMenuPage{
    public FriendsFollowFriend(SpotifyPageFactory spotifyPageFactory, SpotifyPlayer spotifyPlayer) {
        super(spotifyPageFactory, spotifyPlayer);
        this.pageTitle = "Follow Friend Page";
        this.pageContent = backLineWith0 + lineBreak +
                "Tip the friend's number to follow him";
        //TODO : tu penses implémenter comment ?
    }
}
