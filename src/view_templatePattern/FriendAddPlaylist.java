package view_templatePattern;

import player_StatePattern.playlist_player.IPlaylistPlayer;
import services.PageService;

public class FriendAddPlaylist extends _SimplePageTemplate {
    public FriendAddPlaylist(PageService pageManager, IPlaylistPlayer spotifyPlayer, int pageId) {
        super(pageManager, spotifyPlayer);
        this.pageId = pageId;
        this.pageTitle = "Link a playlist of a friend";
        this.pageContent = icon.iconNbr(0) + icon.iconBack() + icon.lineBreak +
                "Tip the playlist number to add it to your playlists" + icon.goToMusicPlayer;
        // TODO : implémenter
    }
}
