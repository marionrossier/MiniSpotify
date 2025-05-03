package view_templatePattern;

import player_StatePattern.playlist_player.IPlaylistPlayer;

public class FriendAddPlaylist extends _SimplePageTemplate {
    public FriendAddPlaylist(SpotifyPageFactory spotifyPageFactory, IPlaylistPlayer spotifyPlayer, int pageId) {
        super(spotifyPageFactory, spotifyPlayer);
        this.pageId = pageId;
        this.pageTitle = "Link a playlist of a friend";
        this.pageContent = icon.iconNbr(0) + icon.iconBack() + icon.lineBreak +
                "Tip the playlist number to add it to your playlists";
        // TODO : implémenter
    }
}
