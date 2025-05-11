package clientSide.view_templatePattern;

import clientSide.player_StatePattern.playlist_player.IPlaylistPlayer;
import clientSide.services.PageService;
import clientSide.services.ViewToolBox;

public class FriendsCommunePlaylists extends _SimplePageTemplate {
    public FriendsCommunePlaylists(PageService pageService, IPlaylistPlayer spotifyPlayer, ViewToolBox viewToolBox, int pageId) {
        super(pageService, spotifyPlayer);
        this.viewToolBox = viewToolBox;
        this.pageId = pageId;
        this.isFree = false;
        this.pageTitle = "Commune Playlists Page";
        this.pageContent = icon.zeroBack + icon.lineBreak +
                icon.nbr(1) + "Listen to a playlist" + icon.lineBreak+
                icon.nbr(2) + "Remove a playlist" + icon.eightMusicPlayer; //TODO : selectionner l'option, puis récuperer le numéro de la playlist
    }
}
