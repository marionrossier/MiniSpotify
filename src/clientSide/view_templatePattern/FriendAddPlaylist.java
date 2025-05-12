package clientSide.view_templatePattern;

import clientSide.player_StatePattern.playlist_player.IPlaylistPlayer;
import clientSide.services.PageService;
import clientSide.services.ToolBoxView;

public class FriendAddPlaylist extends _SimplePageTemplate {
    public FriendAddPlaylist(PageService pageService, IPlaylistPlayer spotifyPlayer, ToolBoxView toolBoxView, int pageId) {
        super(pageService, spotifyPlayer);
        this.toolBoxView = toolBoxView;
        this.pageId = pageId;
        this.isFree = false;
        this.pageTitle = "Link a playlist of a friend";
        this.pageContent = icon.zeroBack + icon.lineBreak +
                "Tip the playlist number to add it to your playlists" + icon.eightMusicPlayer;
        // TODO : implémenter
    }
}
