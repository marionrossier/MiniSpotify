package view_templatePattern;

import player_StatePattern.playlist_player.IPlaylistPlayer;

public class PlaylistHomePage extends AbstractMenuPage {

    public PlaylistHomePage(SpotifyPageFactory spotifyPageFactory, IPlaylistPlayer spotifyPlayer) {
        super(spotifyPageFactory, spotifyPlayer);
        this.pageTitle = "Home Page Playlist";
        this.pageContent =
                backLineWith0 + lineBreak +
                nb1 + "Choose your playlist" + lineBreak +
                nb2 + "Create a playlist";
    }

    @Override
    void button1() {
        spotifyPageFactory.playlistChoseList.templateMethode();
    }

    @Override
    void button2() {
        spotifyPageFactory.playlistCreation.templateMethode();
    }

}
