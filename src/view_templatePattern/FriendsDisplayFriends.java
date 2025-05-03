package view_templatePattern;

import player_StatePattern.playlist_player.IPlaylistPlayer;

public class FriendsDisplayFriends extends _SimplePageTemplate {
    public FriendsDisplayFriends(SpotifyPageFactory spotifyPageFactory, IPlaylistPlayer spotifyPlayer, int pageId) {
        super(spotifyPageFactory, spotifyPlayer);
        this.pageId = pageId;
        this.pageTitle = "Display actual Friends Page";
        this.pageContent = icon.iconNbr(0) + icon.iconBack() + icon.lineBreak +
                "Tip the friend's number to see his playlists and have option to add it";
        //TODO : faire aussi la page pour l'ajout d'une playlist a ses propres playlists
    }
}