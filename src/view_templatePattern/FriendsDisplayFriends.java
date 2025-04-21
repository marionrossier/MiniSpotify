package view_templatePattern;

import player_commandPattern.SpotifyPlayer;

public class FriendsDisplayFriends extends AbstractMenuPage{
    public FriendsDisplayFriends(SpotifyPageFactory spotifyPageFactory, SpotifyPlayer spotifyPlayer) {
        super(spotifyPageFactory, spotifyPlayer);
        this.pageTitle = "Display actual Friends Page";
        this.pageContent = backLineWith0 + lineBreak +
                "Tip the friend's number to see his playlists and have option to add it";
        //TODO : faire aussi la page pour l'ajout d'une playlist a ses propres playlists
    }
}