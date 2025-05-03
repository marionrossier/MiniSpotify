package view_templatePattern;

import player_StatePattern.playlist_player.IPlaylistPlayer;

public class PlaylistDeletion extends _SimplePageTemplate {

    public PlaylistDeletion(SpotifyPageFactory spotifyPageFactory, IPlaylistPlayer spotifyPlayer, int pageId) {
        super(spotifyPageFactory, spotifyPlayer);
        this.pageId = pageId;
        this.pageTitle = "Delete Playlist Page";
        this.pageContent = icon.iconNbr(0) + icon.iconBack() + icon.lineBreak +
                "Are you sure you want to delete this playlist?" + icon.lineBreak +
                icon.iconNbr(1) + "YES" + icon.lineBreak +
                icon.iconNbr(2) + "NO";
    }

    @Override
    public void button1() {
        toolbox.getPlaylistServ().deletePlaylist(toolbox.getPlaylistServ().getCurrentPlaylistId());
        System.out.println("Playlist deleted !");
        spotifyPageFactory.playlistDisplay.displayAllPage();
    }

    @Override
    public void button2() {
        System.out.println("Playlist not deleted !");
        spotifyPageFactory.playlistDisplay.displayAllPage();
    }
}
