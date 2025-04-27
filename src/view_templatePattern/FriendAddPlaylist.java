package view_templatePattern;

import player_StatePattern.playlist_player.IPlaylistPlayer;

public class FriendAddPlaylist extends AbstractMenuPage {
    public FriendAddPlaylist(SpotifyPageFactory spotifyPageFactory, IPlaylistPlayer spotifyPlayer) {
        super(spotifyPageFactory, spotifyPlayer);
        this.pageTitle = "Link a playlist of a friend";
        this.pageContent = backLineWith0 + lineBreak +
                "Tip the playlist number to add it to your playlists";
        // TODO : implémenter
    }
}
