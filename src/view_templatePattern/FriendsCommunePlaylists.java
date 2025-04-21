package view_templatePattern;

import player_commandPattern.SpotifyPlayer;

public class FriendsCommunePlaylists extends AbstractMenuPage{
    public FriendsCommunePlaylists(SpotifyPageFactory spotifyPageFactory, SpotifyPlayer spotifyPlayer) {
        super(spotifyPageFactory, spotifyPlayer);
        this.pageTitle = "Commune Playlists Page";
        this.pageContent = backLineWith0 + lineBreak +
                "Tip the playlist's number to play it";

        //TODO : tu veux dire quoi par là ? Comment tu vois la suite ?

    }
}
