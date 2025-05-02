package view_templatePattern;

import player_StatePattern.playlist_player.IPlaylistPlayer;

public class FriendInformation extends AbstractMenuPage {
    public FriendInformation(SpotifyPageFactory spotifyPageFactory, IPlaylistPlayer spotifyPlayer) {
        super(spotifyPageFactory, spotifyPlayer);
        this.pageTitle = "Friend information Page";
        this.pageContent = icon.iconNbr(0) + icon.iconBack() + icon.lineBreak +
                icon.iconNbr(1) + "Listen to a playlist" + icon.lineBreak+
                icon.iconNbr(2) + "Add a playlist to my playlist"; //TODO : selectionner l'option, puis récuperer le numéro de la playlist
    }
}