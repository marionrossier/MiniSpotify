package view_templatePattern;

import player_StatePattern.playlist_player.IPlaylistPlayer;
import services.PageService;

public class FriendsCommunePlaylists extends _SimplePageTemplate {
    public FriendsCommunePlaylists(PageService pageManager, IPlaylistPlayer spotifyPlayer, int pageId) {
        super(pageManager, spotifyPlayer);
        this.pageId = pageId;
        this.isFree = false;
        this.pageTitle = "Commune Playlists Page";
        this.pageContent = icon.iconNbr(0) + icon.iconBack() + icon.lineBreak +
                icon.iconNbr(1) + "Listen to a playlist" + icon.lineBreak+
                icon.iconNbr(2) + "Remove a playlist" + icon.goToMusicPlayer; //TODO : selectionner l'option, puis récuperer le numéro de la playlist
    }
}
