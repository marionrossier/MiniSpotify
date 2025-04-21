package view_templatePattern;

import player_commandPattern.SpotifyPlayer;

public class FriendsLinkPlaylistFriend extends AbstractMenuPage{
    public FriendsLinkPlaylistFriend(SpotifyPageFactory spotifyPageFactory, SpotifyPlayer spotifyPlayer) {
        super(spotifyPageFactory, spotifyPlayer);
        this.pageTitle = "Link a playlist to a friend Page";
        this.pageContent = backLineWith0 + lineBreak +
                "Tip the friend's number to link a playlist";
        // TODO : tu veux dire quoi par là ?
    }
}
