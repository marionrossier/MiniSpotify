package clientSide.view_templatePattern;

import clientSide.player_StatePattern.playlist_player.IPlaylistPlayer;
import clientSide.services.PageService;
import clientSide.services.ViewToolBox;

public class FriendInformation extends _SimplePageTemplate {
    public FriendInformation(PageService pageService, IPlaylistPlayer spotifyPlayer, ViewToolBox viewToolBox, int pageId) {
        super(pageService, spotifyPlayer);
        this.viewToolBox = viewToolBox;
        this.pageId = pageId;
        this.isFree = false;
        this.pageTitle = "Friend information Page";
        this.pageContent = icon.zeroBack + icon.lineBreak +
                icon.nbr(1) + "Listen to a playlist" + icon.lineBreak+
                icon.nbr(2) + "Add a playlist to my playlist" + icon.eightMusicPlayer; //TODO : selectionner l'option, puis récuperer le numéro de la playlist
    }
}