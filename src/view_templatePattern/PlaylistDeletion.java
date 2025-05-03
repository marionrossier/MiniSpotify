package view_templatePattern;

import player_StatePattern.playlist_player.IPlaylistPlayer;
import services.Cookies_SingletonPattern;

public class PlaylistDeletion extends AbstractMenuPage {

    public PlaylistDeletion(SpotifyPageFactory spotifyPageFactory, IPlaylistPlayer spotifyPlayer) {
        super(spotifyPageFactory, spotifyPlayer);
        this.pageTitle = "Delete Playlist Page";
        this.pageContent = icon.iconNbr(0) + icon.iconBack() + icon.lineBreak +
                "Are you sure you want to delete this playlist?" + icon.lineBreak +
                icon.iconNbr(1) + "YES" + icon.lineBreak +
                icon.iconNbr(2) + "NO";
    }

    @Override
    void button0() {
        spotifyPageFactory.playlistDisplay.templateMethode();
    }

    @Override
    void button1() {
        toolbox.getPlaylistServ().deletePlaylist(toolbox.getPlaylistServ().getCurrentPlaylistId());
        System.out.println("Playlist deleted !");
        spotifyPageFactory.playlistDisplay.templateMethode();
    }

    @Override
    void button2() {
        System.out.println("Playlist not deleted !");
        spotifyPageFactory.playlistDisplay.templateMethode();
    }
}
