package view_templatePattern;

import player_commandPattern.SpotifyPlayer;

public class FriendInformation extends AbstractMenuPage{
    public FriendInformation(SpotifyPageFactory spotifyPageFactory, SpotifyPlayer spotifyPlayer) {
        super(spotifyPageFactory, spotifyPlayer);
        this.pageTitle = "Friend information Page";
        this.pageContent = backLineWith0 + lineBreak +
                nb1 + "Listen to a playlist" + lineBreak+
                nb2 + "Add a playlist to my playlist"; //TODO : selectionner l'option, puis récuperer le numéro de la playlist
    }
}