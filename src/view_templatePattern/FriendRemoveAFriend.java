package view_templatePattern;

import player_StatePattern.playlist_player.IPlaylistPlayer;

public class FriendRemoveAFriend extends _SimplePageTemplate {
    public FriendRemoveAFriend(SpotifyPageFactory spotifyPageFactory, IPlaylistPlayer spotifyPlayer, int pageId) {
        super(spotifyPageFactory, spotifyPlayer);
        this.pageId = pageId;
        this.pageTitle = "Remove Friend Page";
        this.pageContent = icon.iconNbr(0) + icon.iconBack() + icon.lineBreak +
                "Tip the friend's pseudo to remove him"; //outil de recherche par pseudo
        //TODO : rechercher via UserRepo. les utilisateurs avec ce pseudo ou un bout et les
        // afficher et permettre la selection et donc la suppression.
    }
}
