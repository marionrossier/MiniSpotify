package clientSide.view_templatePattern;

import clientSide.player_StatePattern.playlist_player.IPlaylistPlayer;
import clientSide.services.PageService;
import clientSide.services.ToolBoxView;

public class FriendsDisplayFriends extends _SimplePageTemplate {
    public FriendsDisplayFriends(PageService pageService, IPlaylistPlayer spotifyPlayer, ToolBoxView toolBoxView, int pageId) {
        super(pageService, spotifyPlayer);
        this.toolBoxView = toolBoxView;
        this.pageId = pageId;
        this.isFree = false;
        this.pageTitle = "Display actual Friends Page";
        this.pageContent = icon.zeroBack + icon.lineBreak +
                "Tip the friend's number to see his playlists and have option to add it" + icon.eightMusicPlayer;
        //TODO : faire aussi la page pour l'ajout d'une playlist a ses propres playlists
    }
}