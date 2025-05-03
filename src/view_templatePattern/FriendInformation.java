package view_templatePattern;

import player_StatePattern.playlist_player.IPlaylistPlayer;

public class FriendInformation extends _SimplePageTemplate {
    public FriendInformation(SpotifyPageFactory spotifyPageFactory, IPlaylistPlayer spotifyPlayer, int pageId) {
        super(spotifyPageFactory, spotifyPlayer);
        this.pageId = pageId;
        this.pageTitle = "Friend information Page";
        this.pageContent = icon.iconNbr(0) + icon.iconBack() + icon.lineBreak +
                icon.iconNbr(1) + "Listen to a playlist" + icon.lineBreak+
                icon.iconNbr(2) + "Add a playlist to my playlist"; //TODO : selectionner l'option, puis récuperer le numéro de la playlist
    }
}