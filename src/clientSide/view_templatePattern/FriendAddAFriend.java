package clientSide.view_templatePattern;

import clientSide.player_StatePattern.playlist_player.IPlaylistPlayer;
import clientSide.services.PageService;
import clientSide.services.ViewToolBox;

public class FriendAddAFriend extends _SimplePageTemplate {
    public FriendAddAFriend(PageService pageService, IPlaylistPlayer spotifyPlayer, ViewToolBox viewToolBox, int pageId) {
        super(pageService, spotifyPlayer);
        this.viewToolBox = viewToolBox;
        this.pageId = pageId;
        this.pageTitle = "Follow Friend Page";
        this.isFree = false;
        this.pageContent = icon.zeroBack + icon.lineBreak +
                "Tip the friend's pseudo to follow him"  + icon.eightMusicPlayer; //outil de recherche par pseudo
        //TODO : rechercher via UserRepo. les utilisateurs avec ce pseudo ou un bout et les
        // afficher et permettre la selection et donc l'ajout.
    }
}
