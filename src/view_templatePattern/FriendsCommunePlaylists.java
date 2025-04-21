package view_templatePattern;

import player_commandPattern.SpotifyPlayer;

public class FriendsCommunePlaylists extends AbstractMenuPage{
    public FriendsCommunePlaylists(SpotifyPageFactory spotifyPageFactory, SpotifyPlayer spotifyPlayer) {
        super(spotifyPageFactory, spotifyPlayer);
        this.pageTitle = "Commune Playlists Page";
        this.pageContent = backLineWith0 + lineBreak +
                nb1 + "Listen to a playlist" + lineBreak+
                nb2 + "Remove a playlist"; //TODO : selectionner l'option, puis récuperer le numéro de la playlist
    }
}
