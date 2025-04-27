package view_templatePattern;

import player_StatePattern.playlist_player.IPlaylistPlayer;

public class PlaylistDeletion extends AbstractMenuPage {

    public PlaylistDeletion(SpotifyPageFactory spotifyPageFactory, IPlaylistPlayer spotifyPlayer) {
        super(spotifyPageFactory, spotifyPlayer);
        this.pageTitle = "Delete Playlist Page";
        this.pageContent = backLineWith0 + lineBreak +
                "Are you sure you want to delete this playlist?" + lineBreak +
                nb1 + "YES" + lineBreak +
                nb2 + "NO";
    }

    @Override
    void button0() {
        spotifyPageFactory.playlistDisplay.templateMethode();
    }

    @Override
    void button1() {
        //TODO : delete playlist
        System.out.println("Playlist deleted !");
        spotifyPageFactory.playlistDisplay.templateMethode();
    }

    @Override
    void button2() {
        System.out.println("Playlist not deleted !");
        spotifyPageFactory.playlistDisplay.templateMethode();
    }
}
