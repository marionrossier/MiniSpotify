package clientSide.view_templatePattern;

import clientSide.player_StatePattern.playlist_player.IPlaylistPlayer;
import clientSide.services.PageService;
import clientSide.services.ViewToolBox;

public class FriendRemoveAFriend extends _SimplePageTemplate {
    public FriendRemoveAFriend(PageService pageService, IPlaylistPlayer spotifyPlayer, ViewToolBox viewToolBox, int pageId) {
        super(pageService, spotifyPlayer);
        this.viewToolBox = viewToolBox;
        this.pageId = pageId;
        this.isFree = false;
        this.pageTitle = "Remove Friend Page";
        this.pageContent = icon.zeroBack + icon.lineBreak +
                "Tip the friend's pseudo to remove him" + icon.eightMusicPlayer; //outil de recherche par pseudo
        //TODO : rechercher via UserRepo. les utilisateurs avec ce pseudo ou un bout et les
        // afficher et permettre la selection et donc la suppression.
    }
}
